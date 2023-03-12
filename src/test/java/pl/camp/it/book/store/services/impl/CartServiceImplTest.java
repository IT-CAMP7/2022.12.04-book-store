package pl.camp.it.book.store.services.impl;

import jakarta.annotation.Resource;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import pl.camp.it.book.store.model.Book;
import pl.camp.it.book.store.model.OrderPosition;
import pl.camp.it.book.store.services.ICartService;
import pl.camp.it.book.store.session.SessionObject;

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
