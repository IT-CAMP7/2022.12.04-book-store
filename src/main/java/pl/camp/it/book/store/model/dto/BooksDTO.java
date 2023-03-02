package pl.camp.it.book.store.model.dto;

import lombok.Getter;
import pl.camp.it.book.store.model.Book;

import java.util.ArrayList;
import java.util.List;

@Getter
public class BooksDTO {
    private final List<Book> books = new ArrayList<>();
}
