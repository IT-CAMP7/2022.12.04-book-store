package pl.camp.it.book.store.services.impl;

import jakarta.annotation.Resource;
import jdk.jshell.spi.ExecutionControl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;
import org.mockito.internal.matchers.Or;
import org.springframework.beans.factory.annotation.Autowired;
import pl.camp.it.book.store.exceptions.NotEnoughBookException;
import pl.camp.it.book.store.exceptions.PersistOrderException;
import pl.camp.it.book.store.model.Book;
import pl.camp.it.book.store.model.Order;
import pl.camp.it.book.store.model.OrderPosition;
import pl.camp.it.book.store.model.User;
import pl.camp.it.book.store.model.dto.SaveOrderRequest;
import pl.camp.it.book.store.services.IOrderService;
import pl.camp.it.book.store.session.SessionObject;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class OrderServiceImplTest extends ServiceGenericTest {

    @Autowired
    IOrderService orderService;

    @Resource
    SessionObject sessionObject;

    @Test
    public void confirmOrderWhenNotEnoughBookInStoreTest() {
        this.sessionObject.getCart().clear();
        OrderPosition orderPosition = generateFakeOrderPosition();
        orderPosition.setQuantity(10);
        orderPosition.getBook().setQuantity(5);
        this.sessionObject.getCart().put(20, orderPosition);

        Assertions.assertThrows(NotEnoughBookException.class,
                () -> this.orderService.confirmOrder());
        Mockito.verify(this.bookDAO, Mockito.never()).updateBook(ArgumentMatchers.any());
        Mockito.verify(this.orderDAO, Mockito.never()).persistOrder(ArgumentMatchers.any());
    }

    @Test
    public void confirmOrderTest() {
        this.sessionObject.getCart().clear();
        OrderPosition orderPosition = generateFakeOrderPosition();
        orderPosition.setQuantity(5);
        orderPosition.getBook().setQuantity(10);
        this.sessionObject.getCart().put(20, orderPosition);
        OrderPosition orderPosition2 = generateFakeOrderPosition();
        orderPosition2.setQuantity(5);
        orderPosition2.getBook().setQuantity(10);
        orderPosition2.getBook().setId(21);
        this.sessionObject.getCart().put(21, orderPosition2);
        User user = generateFakeUser().get();
        this.sessionObject.setUser(user);
        int expectedCartSize = 0;
        List<OrderPosition> expectedOrderPositions = new ArrayList<>();
        expectedOrderPositions.add(orderPosition);
        expectedOrderPositions.add(orderPosition2);
        Order.State expectedState = Order.State.NEW;
        double expectedTotal = 2000.00;
        int expectedId = 0;
        int expectedBookQuantity = 5;

        this.orderService.confirmOrder();

        ArgumentCaptor<Book> bookArgument = ArgumentCaptor.forClass(Book.class);
        Mockito.verify(this.bookDAO, Mockito.times(2))
                .updateBook(bookArgument.capture());
        Optional<Book> firstBook = bookArgument.getAllValues().stream()
                .filter(book -> book.getId() == 20)
                .findFirst();
        Assertions.assertEquals(expectedBookQuantity, firstBook.get().getQuantity());
        Optional<Book> secondBook = bookArgument.getAllValues().stream()
                .filter(book -> book.getId() == 21)
                .findFirst();
        Assertions.assertEquals(expectedBookQuantity, secondBook.get().getQuantity());

        ArgumentCaptor<Order> orderArgument = ArgumentCaptor.forClass(Order.class);
        Mockito.verify(this.orderDAO, Mockito.times(1))
                .persistOrder(orderArgument.capture());
        Assertions.assertEquals(user, orderArgument.getValue().getUser());
        Assertions.assertEquals(expectedOrderPositions,
                orderArgument.getValue().getPositions());
        Assertions.assertEquals(expectedState, orderArgument.getValue().getState());
        Assertions.assertEquals(expectedTotal, orderArgument.getValue().getTotal(), 0.01);
        Assertions.assertEquals(expectedId, orderArgument.getValue().getId());

        Assertions.assertEquals(expectedCartSize, this.sessionObject.getCart().size());
    }

    @Test
    public void getOrderForCurrentUserTest() {
        this.sessionObject.setUser(generateFakeUser().get());
        List<Order> expectedOrders = new ArrayList<>();
        expectedOrders.add(new Order());
        expectedOrders.add(new Order());
        Mockito.when(this.orderDAO.getOrdersByUserId(10)).thenReturn(expectedOrders);

        List<Order> result = this.orderService.getOrderForCurrentUser();

        Mockito.verify(this.orderDAO, Mockito.times(1))
                .getOrdersByUserId(10);
        Assertions.assertSame(expectedOrders, result);
    }

    @Test
    public void persistOrderTest() {
        Order order = new Order();

        this.orderService.persistOrder(order);

        Mockito.verify(this.orderDAO, Mockito.times(1))
                .persistOrder(order);
    }

    @Test
    public void getOrderByUserIdTest() {
        Order order1 = generateFakeOrder();
        order1.setId(1);
        User user = generateFakeUser().get();
        user.setId(1);
        user.setOrders(new ArrayList<>());
        order1.setUser(user);
        Order order2 = generateFakeOrder();
        order2.setId(2);
        User user2 = generateFakeUser().get();
        user2.setId(1);
        user2.setOrders(new ArrayList<>());
        order2.setUser(user);

        List<Order> expectedOrders = List.of(order1, order2);
        String expectedPassword = "*****";

        Mockito.when(this.orderDAO.getOrdersByUserId(1)).thenReturn(expectedOrders);

        List<Order> result = this.orderService.getOrderByUserId(1);

        Assertions.assertSame(expectedOrders, result);
        for(Order order : result) {
            Assertions.assertNull(order.getUser().getOrders());
            Assertions.assertEquals(expectedPassword, order.getUser().getPassword());
        }
    }

    @Test
    public void persistOrderRequestWhenUserNotExistTest() throws ExecutionControl.NotImplementedException {
        SaveOrderRequest saveOrderRequest = new SaveOrderRequest();
        saveOrderRequest.setUserId(1);
        Mockito.when(this.userDAO.getUserById(1)).thenReturn(Optional.empty());

        Assertions.assertThrows(PersistOrderException.class,
                () -> this.orderService.persistOrder(saveOrderRequest));
    }

    @Test
    public void persistOrderRequestWhenBookNotExistTest() throws ExecutionControl.NotImplementedException {
        SaveOrderRequest saveOrderRequest = new SaveOrderRequest();
        saveOrderRequest.setUserId(10);
        SaveOrderRequest.OrderPosition orderPosition =
                new SaveOrderRequest.OrderPosition(1, 5);
        saveOrderRequest.getOrderPositions().add(orderPosition);
        Mockito.when(this.userDAO.getUserById(10)).thenReturn(generateFakeUser());
        Mockito.when(this.bookDAO.getBookById(1)).thenReturn(Optional.empty());

        Assertions.assertThrows(PersistOrderException.class,
                () -> this.orderService.persistOrder(saveOrderRequest));
    }

    @Test
    public void persistOrderRequestTest() throws ExecutionControl.NotImplementedException {
        SaveOrderRequest saveOrderRequest = new SaveOrderRequest();
        saveOrderRequest.setUserId(10);
        saveOrderRequest.setDate(LocalDateTime.now());
        saveOrderRequest.setState(Order.State.NEW);
        SaveOrderRequest.OrderPosition expectedOrderPosition =
                new SaveOrderRequest.OrderPosition(20, 5);
        saveOrderRequest.getOrderPositions().add(expectedOrderPosition);
        Optional<User> expectedUser = generateFakeUser();
        Mockito.when(this.userDAO.getUserById(10)).thenReturn(expectedUser);
        Book expectedBook = generateFakeBook();
        Mockito.when(this.bookDAO.getBookById(20))
                .thenReturn(Optional.of(expectedBook));
        double expectedTotal = 1000.00;
        String expectedPassword = "*****";

        Order result = this.orderService.persistOrder(saveOrderRequest);

        ArgumentCaptor<Order> orderArgument = ArgumentCaptor.forClass(Order.class);
        Mockito.verify(this.orderDAO, Mockito.times(1))
                .persistOrder(orderArgument.capture());

        Assertions.assertEquals(1, orderArgument.getValue().getPositions().size());
        Assertions.assertSame(expectedBook,
                orderArgument.getValue().getPositions().get(0).getBook());
        Assertions.assertEquals(expectedOrderPosition.getQuantity(),
                orderArgument.getValue().getPositions().get(0).getQuantity());
        Assertions.assertSame(expectedUser.get(), orderArgument.getValue().getUser());
        Assertions.assertEquals(saveOrderRequest.getDate(),
                orderArgument.getValue().getDate());
        Assertions.assertEquals(saveOrderRequest.getState(),
                orderArgument.getValue().getState());
        Assertions.assertEquals(expectedTotal,
                orderArgument.getValue().getTotal(), 0.01);
        Assertions.assertNull(result.getUser().getOrders());
        Assertions.assertEquals(expectedPassword, result.getUser().getPassword());
    }

    @Test
    public void getOrderByIdTest() {
        int id = 1;
        Optional<Order> order = Optional.of(new Order());
        order.get().setId(id);
        Mockito.when(this.orderDAO.getOrderById(id)).thenReturn(order);

        Optional<Order> result = this.orderService.getOrderById(id);

        Mockito.verify(this.orderDAO, Mockito.times(1))
                .getOrderById(id);
        Assertions.assertSame(order, result);
    }

    private OrderPosition generateFakeOrderPosition() {
        OrderPosition orderPosition = new OrderPosition();
        orderPosition.setId(10);
        orderPosition.setQuantity(5);
        orderPosition.setBook(generateFakeBook());

        return orderPosition;
    }

    private Book generateFakeBook() {
        Book book = new Book();
        book.setId(20);
        book.setIsbn("asdfasdfasd");
        book.setPrice(200.00);
        book.setAuthor("ashjkdgfhjja");
        book.setTitle("askjhdgyhasd");
        book.setQuantity(23456);
        return book;
    }

    private Optional<User> generateFakeUser() {
        User user = new User();
        user.setId(10);
        user.setLogin("testUser");
        user.setPassword("fed3b61b26081849378080b34e693d2e");
        user.setName("Imie");
        user.setSurname("Nazwisko");
        user.setRole(User.Role.ADMIN);

        return Optional.of(user);
    }

    private Order generateFakeOrder() {
        Order order = new Order();
        order.setId(2);
        order.setDate(LocalDateTime.now());
        order.setTotal(500.00);
        order.setState(Order.State.NEW);
        order.setPositions(new ArrayList<>());

        return order;
    }
}
