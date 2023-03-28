package pl.camp.it.book.store.services.impl;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import pl.camp.it.book.store.model.Book;
import pl.camp.it.book.store.model.OrderPosition;
import pl.camp.it.book.store.services.IOrderPositionService;

import java.util.Optional;

public class OrderPositionServiceImplTest extends ServiceGenericTest {

    @Autowired
    IOrderPositionService orderPositionService;

    @Test
    public void GetOrderPositionByIdTest() {
        int id = 10;
        OrderPosition expectedOrderPosition = generateFakeOrderPosition();
        Mockito
                .when(this.orderPositionDAO.getOrderPositionById(id))
                .thenReturn(Optional.of(expectedOrderPosition));

        Optional<OrderPosition> result = this.orderPositionService.getOrderPositionById(id);

        Mockito
                .verify(this.orderPositionDAO, Mockito.times(1))
                .getOrderPositionById(id);
        Assertions.assertTrue(result.isPresent());
        Assertions.assertSame(expectedOrderPosition, result.get());
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
