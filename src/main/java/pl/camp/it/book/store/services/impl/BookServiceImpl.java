package pl.camp.it.book.store.services.impl;

import jakarta.annotation.Resource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import pl.camp.it.book.store.database.IBookDAO;
import pl.camp.it.book.store.model.Book;
import pl.camp.it.book.store.services.IBookService;
import pl.camp.it.book.store.session.SessionObject;

import java.util.List;
import java.util.Optional;

@Component
public class BookServiceImpl implements IBookService {

    @Autowired
    IBookDAO bookDAO;

    @Resource
    SessionObject sessionObject;

    @Override
    public List<Book> getBooks() {
        Optional<String> patternBox = this.sessionObject.getPattern();

        if(patternBox.isEmpty()) {
            return this.bookDAO.getBooks();
        } else {
            return this.bookDAO.getBooksByPattern(patternBox.get());
        }
    }
}
