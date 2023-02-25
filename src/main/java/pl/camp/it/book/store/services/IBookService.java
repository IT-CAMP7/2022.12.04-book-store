package pl.camp.it.book.store.services;

import pl.camp.it.book.store.model.Book;

import java.sql.SQLIntegrityConstraintViolationException;
import java.util.List;
import java.util.Optional;

public interface IBookService {
    List<Book> getBooks();
    List<Book> getBooksByPattern(String pattern);
    void persistBook(Book book);

    Optional<Book> getBookById(int id);
    void updateBook(Book book, int id);
}
