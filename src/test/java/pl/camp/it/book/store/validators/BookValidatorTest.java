package pl.camp.it.book.store.validators;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import pl.camp.it.book.store.exceptions.BookValidationException;
import pl.camp.it.book.store.model.Book;

public class BookValidatorTest {

    @Test
    public void incorrectTitleTest() {
        String title = "tytul";

        Assertions.assertThrows(BookValidationException.class,
                () -> BookValidator.validateTitle(title));
    }

    @Test
    public void correctTitleTest() {
        String title = "Correct title";

        BookValidator.validateTitle(title);
    }

    @Test
    public void onlyNameAuthorTest() {
        String author = "Janusz";

        Assertions.assertThrows(BookValidationException.class,
                () -> BookValidator.validateAuthor(author));
    }

    @Test
    public void nameAndSurnameAuthorTest() {
        String author = "Janusz Kowalski";

        BookValidator.validateAuthor(author);
    }

    @Test
    public void nameAndLowerSurnameTest() {
        String author = "Janusz kowalski";

        Assertions.assertThrows(BookValidationException.class,
                () -> BookValidator.validateAuthor(author));
    }

    @Test
    public void twoNamesAndSurnameTest() {
        String author = "Janusz Jan Kowalski";

        BookValidator.validateAuthor(author);
    }

    @Test
    public void twoNamesAndTwoSurnamesTest() {
        String author = "Janusz Jan Kowalski-Malinowski";

        BookValidator.validateAuthor(author);
    }

    @Test
    public void twoNamesAndSecondLowerSurnameTest() {
        String author = "Janusz Jan Kowalski-malinowski";

        Assertions.assertThrows(BookValidationException.class,
                () -> BookValidator.validateAuthor(author));
    }

    @Test
    public void twoAuthorsWithTwoNamesAndTwoSurnamesTest() {
        String author = "Janusz Jan Kowalski-Malinowski, Zbyszek Wiesiek Malinowski-Kowalski";

        BookValidator.validateAuthor(author);
    }

    @Test
    public void correctIsbnStartsWith978Test() {
        String isbn = "978-00-123-123-5";

        BookValidator.validateIsbn(isbn);
    }

    @Test
    public void correctIsbnStartsWith979Test() {
        String isbn = "979-00-123-123-5";

        BookValidator.validateIsbn(isbn);
    }

    @Test
    public void isbnWithIncorrectStartTest() {
        String isbn = "000-00-123-123-5";

        Assertions.assertThrows(BookValidationException.class,
                () -> BookValidator.validateIsbn(isbn));
    }

    @Test
    public void incorrectIsbnTest() {
        String isbn = "979-00-123-123123-5";

        Assertions.assertThrows(BookValidationException.class,
                () -> BookValidator.validateIsbn(isbn));
    }

    @Test
    public void correctBookValidationTest() {
        Book book = new Book(10, "Tytul", "Jan Kowalski",
                50.00, 10, "979-00-123-123-5");

        BookValidator.validateBook(book);
    }

    @Test
    public void incorrectTitleBookValidationTest() {
        Book book = new Book(10, "tytul", "Jan Kowalski",
                50.00, 10, "979-00-123-123-5");

        Assertions.assertThrows(BookValidationException.class,
                () -> BookValidator.validateBook(book));
    }

}
