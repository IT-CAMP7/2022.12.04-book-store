package pl.camp.it.book.store.model;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class OrderPositionTest {

    @Test
    public void incrementTest() {
        OrderPosition orderPosition = generateFakeOrderPosition();
        int expectedQuantity = 6;

        orderPosition.incrementQuantity();

        Assertions.assertEquals(expectedQuantity, orderPosition.getQuantity());
    }

    @Test
    public void incrementQuantityWhenZeroTest() {
        OrderPosition orderPosition = generateFakeOrderPosition();
        orderPosition.setQuantity(0);
        int expectedQuantity = 1;

        orderPosition.incrementQuantity();

        Assertions.assertEquals(expectedQuantity, orderPosition.getQuantity());
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
        book.setPrice(234.233);
        book.setAuthor("ashjkdgfhjja");
        book.setTitle("askjhdgyhasd");
        book.setQuantity(23456);
        return book;
    }
}
