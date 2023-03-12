package pl.camp.it.book.store.model.dto;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import pl.camp.it.book.store.model.Book;
import pl.camp.it.book.store.model.OrderPosition;

public class OrderPositionDTOTest {

    @Test
    public void constructorTest() {
        OrderPosition orderPosition = generateFakeOrderPosition();
        String expectedBook = "http://localhost:8085/api/v1/book/20";

        OrderPositionDTO result = new OrderPositionDTO(orderPosition);

        Assertions.assertEquals(orderPosition.getId(), result.getId());
        Assertions.assertEquals(expectedBook, result.getBook());
        Assertions.assertEquals(orderPosition.getQuantity(), result.getQuantity());
    }

    @Test
    public void nullBookConstructorTest() {
        OrderPosition orderPosition = generateFakeOrderPosition();
        orderPosition.setBook(null);

        OrderPositionDTO result = new OrderPositionDTO(orderPosition);

        Assertions.assertEquals(orderPosition.getId(), result.getId());
        Assertions.assertNull(result.getBook());
        Assertions.assertEquals(orderPosition.getQuantity(), result.getQuantity());
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
