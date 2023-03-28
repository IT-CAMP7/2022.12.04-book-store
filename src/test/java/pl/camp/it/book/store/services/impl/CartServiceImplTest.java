package pl.camp.it.book.store.services.impl;

import jakarta.annotation.Resource;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import pl.camp.it.book.store.exceptions.NotEnoughBookException;
import pl.camp.it.book.store.model.Book;
import pl.camp.it.book.store.model.OrderPosition;
import pl.camp.it.book.store.services.ICartService;
import pl.camp.it.book.store.session.SessionObject;

import java.util.List;
import java.util.Optional;

public class CartServiceImplTest extends ServiceGenericTest {

    @Autowired
    ICartService cartService;

    @Resource
    SessionObject sessionObject;

    @Test
    public void addNotExistingBookToCartTest() {
        int id = 10;
        this.sessionObject.getCart().clear();
        Mockito
                .when(this.bookDAO.getBookById(id))
                .thenReturn(Optional.empty());

        this.cartService.addBookToCart(id);

        Assertions.assertTrue(this.sessionObject.getCart().isEmpty());
    }

    @Test
    public void addNewBookToCartTest() {
        this.sessionObject.getCart().clear();
        this.sessionObject.getCart().put(20, generateFakeOrderPosition());
        Book book = generateFakeBook();
        book.setId(1);
        Mockito
                .when(this.bookDAO.getBookById(1))
                .thenReturn(Optional.of(book));

        this.cartService.addBookToCart(1);

        Assertions.assertEquals(2, this.sessionObject.getCart().size());
        Assertions.assertNotNull(this.sessionObject.getCart().get(20));
        Assertions.assertNotNull(this.sessionObject.getCart().get(1));
        Assertions.assertEquals(1,
                this.sessionObject.getCart().get(1).getQuantity());
    }

    @Test
    public void addBookWithZeroQuantityToCartTest() {
        this.sessionObject.getCart().clear();
        this.sessionObject.getCart().put(20, generateFakeOrderPosition());
        Book book = generateFakeBook();
        book.setId(1);
        book.setQuantity(0);
        Mockito
                .when(this.bookDAO.getBookById(1))
                .thenReturn(Optional.of(book));

        Assertions.assertThrows(NotEnoughBookException.class,
                () -> this.cartService.addBookToCart(1));
    }

    @Test
    public void addAlreadyAddedBookToCartTest() {
        this.sessionObject.getCart().clear();
        this.sessionObject.getCart().put(20, generateFakeOrderPosition());
        OrderPosition orderPosition = generateFakeOrderPosition();
        orderPosition.setId(11);
        orderPosition.getBook().setId(21);
        this.sessionObject.getCart().put(21, orderPosition);
        Mockito
                .when(this.bookDAO.getBookById(21))
                .thenReturn(Optional.of(orderPosition.getBook()));

        this.cartService.addBookToCart(21);

        Assertions.assertEquals(2, this.sessionObject.getCart().size());
        Assertions.assertNotNull(this.sessionObject.getCart().get(20));
        Assertions.assertNotNull(this.sessionObject.getCart().get(21));
        Assertions.assertEquals(5,
                this.sessionObject.getCart().get(20).getQuantity());
        Assertions.assertEquals(6,
                this.sessionObject.getCart().get(21).getQuantity());
    }

    @Test
    public void addAlreadyAddedBookWithNotEnoughQuantityToCartTest() {
        this.sessionObject.getCart().clear();
        this.sessionObject.getCart().put(20, generateFakeOrderPosition());
        OrderPosition orderPosition = generateFakeOrderPosition();
        orderPosition.setId(11);
        orderPosition.getBook().setId(21);
        orderPosition.getBook().setQuantity(5);
        this.sessionObject.getCart().put(21, orderPosition);
        Mockito
                .when(this.bookDAO.getBookById(21))
                .thenReturn(Optional.of(orderPosition.getBook()));

        Assertions.assertThrows(NotEnoughBookException.class,
                () -> this.cartService.addBookToCart(21));
    }

    @Test
    public void clearCartTest() {
        this.sessionObject.getCart().put(20, generateFakeOrderPosition());
        int expectedCartSize = 0;

        this.cartService.clearCart();

        Assertions.assertEquals(expectedCartSize, this.sessionObject.getCart().size());
    }

    @Test
    public void removeBookFromCart() {
        this.sessionObject.getCart().clear();
        this.sessionObject.getCart().put(20, generateFakeOrderPosition());
        OrderPosition orderPosition = generateFakeOrderPosition();
        orderPosition.setId(11);
        orderPosition.getBook().setId(21);
        this.sessionObject.getCart().put(21, orderPosition);

        this.cartService.removeFromCart(21);

        Assertions.assertEquals(1, this.sessionObject.getCart().size());
        Assertions.assertNotNull(this.sessionObject.getCart().get(20));
        Assertions.assertNull(this.sessionObject.getCart().get(21));
    }

    @Test
    public void getCartTest() {
        this.sessionObject.getCart().clear();
        this.sessionObject.getCart().put(20, generateFakeOrderPosition());
        OrderPosition orderPosition = generateFakeOrderPosition();
        orderPosition.setId(11);
        orderPosition.getBook().setId(21);
        this.sessionObject.getCart().put(21, orderPosition);

        List<OrderPosition> result = this.cartService.getCart();

        Assertions.assertEquals(2, result.size());
        Optional<OrderPosition> firstPosition = result.stream()
                .filter(op -> op.getId() == 10)
                .filter(op -> op.getBook().getId() == 20)
                .findFirst();
        Assertions.assertTrue(firstPosition.isPresent());
        Optional<OrderPosition> secondPosition = result.stream()
                .filter(op -> op.getId() == 11)
                .filter(op -> op.getBook().getId() == 21)
                .findFirst();
        Assertions.assertTrue(secondPosition.isPresent());
    }

    @Test
    public void calculateSumTest() {
        this.sessionObject.getCart().clear();
        this.sessionObject.getCart().put(20, generateFakeOrderPosition());
        OrderPosition orderPosition = generateFakeOrderPosition();
        orderPosition.setQuantity(2);
        orderPosition.getBook().setPrice(55.55);
        this.sessionObject.getCart().put(21, orderPosition);
        double expectedResult = 1111.10;

        double actual = this.cartService.calculateCartSum();

        Assertions.assertEquals(expectedResult, actual, 0.01);
    }

    @Test
    public void calculateEmptyCartSum() {
        this.sessionObject.getCart().clear();
        double expectedResult = 0.0;

        double actual = this.cartService.calculateCartSum();

        Assertions.assertEquals(expectedResult, actual, 0.01);
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
}
