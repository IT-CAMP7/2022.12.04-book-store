package pl.camp.it.book.store.session;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import pl.camp.it.book.store.model.User;

import java.util.Optional;

public class SessionObjectTest {

    @Test
    public void isLoggedUserTest() {
        SessionObject sessionObject = new SessionObject();
        sessionObject.setUser(new User());

        Assertions.assertTrue(sessionObject.isLogged());
    }

    @Test
    public void notLoggedUserTest() {
        SessionObject sessionObject = new SessionObject();

        Assertions.assertFalse(sessionObject.isLogged());
    }

    @Test
    public void patternTest() {
        SessionObject sessionObject = new SessionObject();
        String pattern = "Pattern";

        sessionObject.setPattern(pattern);
        Optional<String> result = sessionObject.getPattern();

        Assertions.assertTrue(result.isPresent());
        Assertions.assertEquals(pattern, result.get());
        Assertions.assertFalse(sessionObject.getPattern().isPresent());
    }

    @Test
    public void infoTest() {
        SessionObject sessionObject = new SessionObject();
        String info = "Info";

        sessionObject.setInfo(info);
        Optional<String> result = sessionObject.getInfo();

        Assertions.assertTrue(result.isPresent());
        Assertions.assertEquals(info, result.get());
        Assertions.assertFalse(sessionObject.getInfo().isPresent());
    }

    @Test
    public void isAdminWhenUserNullTest() {
        SessionObject sessionObject = new SessionObject();

        Assertions.assertFalse(sessionObject.isAdmin());
    }

    @Test
    public void isAdminWhenUserNotAdminTest() {
        SessionObject sessionObject = new SessionObject();
        sessionObject.setUser(generateFakeUser(User.Role.USER));

        Assertions.assertFalse(sessionObject.isAdmin());
    }

    @Test
    public void isAdminWhenUserAdminTest() {
        SessionObject sessionObject = new SessionObject();
        sessionObject.setUser(generateFakeUser(User.Role.ADMIN));

        Assertions.assertTrue(sessionObject.isAdmin());
    }

    private User generateFakeUser(User.Role role) {
        User user = new User();
        user.setId(2345);
        user.setRole(role);
        user.setPassword("sdfgsdfg");
        user.setLogin("sdfgsddfg");
        user.setName("sdfgsdfg");
        user.setSurname("sdfgsdfgj");

        return user;
    }
}
