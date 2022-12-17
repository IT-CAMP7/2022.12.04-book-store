package pl.camp.it.book.store.services;

import pl.camp.it.book.store.model.Book;

import java.util.List;

public interface IBookService {
    List<Book> getBooks();
}
