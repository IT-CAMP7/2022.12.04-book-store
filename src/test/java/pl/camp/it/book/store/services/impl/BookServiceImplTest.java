package pl.camp.it.book.store.services.impl;

import jakarta.annotation.Resource;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import pl.camp.it.book.store.model.Book;
import pl.camp.it.book.store.services.IBookService;
import pl.camp.it.book.store.session.SessionObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class BookServiceImplTest extends ServiceGenericTest {

    @Autowired
    IBookService bookService;

    @Resource
    SessionObject sessionObject;

    @Test
    public void getBooksWithPatternTest() {
        String testPattern = "pattern";
        Mockito.when(this.bookDAO.getBooksByPattern(testPattern))
                .thenReturn(generateFakeBooksList());
        this.sessionObject.setPattern(testPattern);
        int expectedResultSize = 3;

        List<Book> result = this.bookService.getBooks();

        Mockito
                .verify(this.bookDAO, Mockito.times(1))
                .getBooksByPattern(testPattern);
        Assertions.assertEquals(expectedResultSize, result.size());
    }

    @Test
    public void getBooksWithoutPatternTest() {
        Mockito.when(this.bookDAO.getBooks())
                .thenReturn(generateFakeBooksList());
        int expectedResultSize = 3;

        List<Book> result = this.bookService.getBooks();

        Mockito
                .verify(this.bookDAO, Mockito.times(1))
                .getBooks();
        Assertions.assertEquals(expectedResultSize, result.size());
    }

    @Test
    public void getBookByPatternTest() {
        String pattern = "pattern";
        Mockito.when(this.bookDAO.getBooksByPattern(pattern))
                .thenReturn(generateFakeBooksList());
        int expectedResultSize = 3;

        List<Book> result = this.bookService.getBooksByPattern(pattern);

        Assertions.assertEquals(expectedResultSize, result.size());
        Mockito
                .verify(this.bookDAO, Mockito.times(1))
                .getBooksByPattern(pattern);
    }

    @Test
    public void persistUserTest() {
        Book book = generateFakeBook();

        this.bookService.persistBook(book);

        Mockito
                .verify(this.bookDAO, Mockito.times(1))
                .persistBook(book);
    }

    @Test
    public void getBookByIdTest() {
        int id = 20;
        Book expectedBook = generateFakeBook();
        Mockito
                .when(this.bookDAO.getBookById(id))
                .thenReturn(Optional.of(expectedBook));

        Optional<Book> result = this.bookService.getBookById(id);

        Mockito
                .verify(this.bookDAO, Mockito.times(1))
                .getBookById(id);
        Assertions.assertTrue(result.isPresent());
        Assertions.assertSame(expectedBook, result.get());
    }

    @Test
    public void updateBookTest() {
        Book book = generateFakeBook();
        int id = 10;

        this.bookService.updateBook(book, id);

        Assertions.assertEquals(id, book.getId());
        Mockito
                .verify(this.bookDAO, Mockito.times(1))
                .updateBook(book);
    }

    private List<Book> generateFakeBooksList() {
        List<Book> books = new ArrayList<>();

        books.add(new Book(1, "tytul", "Jan Kowalski",
                50.00, 10, "979-00-123-123-5"));
        books.add(new Book(2, "tytul2", "Jan Kowalski",
                50.00, 10, "979-00-123-123-6"));
        books.add(new Book(3, "tytul3", "Jan Kowalski",
                50.00, 10, "979-00-123-123-7"));

        return books;
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
