package pl.camp.it.book.store.model;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class UserTest {

    @Test
    public void cloneUserTest() {
        User user = generateFakeUser();

        User result = new User.UserBuilder().clone(user).build();

        Assertions.assertEquals(user.getPassword(), result.getPassword());
        Assertions.assertEquals(user.getId(), result.getId());
        Assertions.assertEquals(user.getRole(), result.getRole());
        Assertions.assertEquals(user.getLogin(), result.getLogin());
        Assertions.assertEquals(user.getSurname(), result.getSurname());
        Assertions.assertEquals(user.getName(), result.getName());
        Assertions.assertNotSame(user, result);
    }

    private User generateFakeUser() {
        User user = new User();
        user.setId(15);
        user.setRole(User.Role.USER);
        user.setPassword("21232f297a57a5a743894a0e4a801fc3");
        user.setLogin("admin");
        user.setName("Janusz");
        user.setSurname("Kowalski");

        return user;
    }
}
