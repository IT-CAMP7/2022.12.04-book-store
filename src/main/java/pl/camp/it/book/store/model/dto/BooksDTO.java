package pl.camp.it.book.store.model.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import pl.camp.it.book.store.model.Book;

import java.util.ArrayList;
import java.util.List;

@ApiModel(value = "BooksList", description = "Object to handle list of books")
public class BooksDTO {
    @ApiModelProperty(value = "List of books")
    private final List<Book> books = new ArrayList<>();

    public List<Book> getBooks() {
        return books;
    }
}
