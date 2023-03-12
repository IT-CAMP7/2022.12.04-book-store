package pl.camp.it.book.store.model.dto;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import pl.camp.it.book.store.model.User;

public class UserResponseDTOTest {

    @Test
    public void constructorTest() {
        User user = generateFakeUser();
        String expectedOrders = "http://localhost:8085/api/v1/order?userId=15";

        UserResponseDTO result = new UserResponseDTO(user);

        Assertions.assertEquals(user.getLogin(), result.getLogin());
        Assertions.assertEquals(user.getName(), result.getName());
        Assertions.assertEquals(user.getSurname(), result.getSurname());
        Assertions.assertEquals(user.getRole(), result.getRole());
        Assertions.assertEquals(user.getId(), result.getId());
        Assertions.assertEquals(expectedOrders, result.getOrders());
    }

    private User generateFakeUser() {
        User user = new User();
        user.setId(15);
        user.setRole(User.Role.USER);
        user.setPassword("isaudgfklojasdlkjh");
        user.setLogin("admin");
        user.setName("Janusz");
        user.setSurname("Kowalski");

        return user;
    }
}
