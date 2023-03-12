package pl.camp.it.book.store.validators;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import pl.camp.it.book.store.exceptions.UserValidationException;
import pl.camp.it.book.store.model.User;

public class UserValidatorTest {

    @Test
    public void correctLoginValidationTest() {
        String login = "janusz";

        UserValidator.validateLogin(login);
    }

    @Test
    public void incorrectLoginValidationTest() {
        String login = "jan";

        Assertions.assertThrows(UserValidationException.class,
                () -> UserValidator.validateLogin(login));
    }

    @Test
    public void correctPasswordTest() {
        String password = "dobrehaslo";

        UserValidator.validatePassword(password);
    }

    @Test
    public void incorrectPasswordTest() {
        String password = "zle";

        Assertions.assertThrows(UserValidationException.class,
                () -> UserValidator.validatePassword(password));
    }

    @Test
    public void correctNameTest() {
        String name = "Janusz";

        UserValidator.validateName(name);
    }

    @Test
    public void incorrectNameTest() {
        String name = "janusz";

        Assertions.assertThrows(UserValidationException.class,
                () -> UserValidator.validateName(name));
    }

    @Test
    public void correctSurnameTest() {
        String surname = "Kowalski";

        UserValidator.validateSurname(surname);
    }

    @Test
    public void incorrectSurnameTest() {
        String surname = "kowalski";

        Assertions.assertThrows(UserValidationException.class,
                () -> UserValidator.validateSurname(surname));
    }

    @Test
    public void equalPasswordsValidationTest() {
        String password1 = "jakieshaslo";
        String password2 = "jakieshaslo";

        UserValidator.validatePasswordsEquality(password1, password2);
    }

    @Test
    public void notEqualPasswordsValidationTest() {
        String password1 = "jakieshaslo";
        String password2 = "innehaslo";

        Assertions.assertThrows(UserValidationException.class,
                () -> UserValidator.validatePasswordsEquality(password1, password2));
    }

    @Test
    public void correctUserValidationTest() {
        User user = new User(10, "Janusz", "Kowalski",
                "admin", "admin123", User.Role.ADMIN);
        String password2 = "admin123";

        UserValidator.validateRegisterUser(user, password2);
    }

    @Test
    public void incorrectUserValidationTest() {
        User user = new User(10, "janusz", "Kowalski",
                "admin", "admin123", User.Role.ADMIN);
        String password2 = "admin123";

        Assertions.assertThrows(UserValidationException.class,
                () -> UserValidator.validateRegisterUser(user, password2));
    }
}
