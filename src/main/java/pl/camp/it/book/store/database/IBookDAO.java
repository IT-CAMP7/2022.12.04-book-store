package pl.camp.it.book.store.database;

import pl.camp.it.book.store.model.Book;

import java.util.List;

public interface IBookDAO {
    List<Book> getBooks();
    List<Book> getBooksByPattern(String pattern);
}
