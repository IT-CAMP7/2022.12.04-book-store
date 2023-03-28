package pl.camp.it.book.store.services.impl;

import jdk.jshell.spi.ExecutionControl;
import org.apache.commons.codec.digest.DigestUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import pl.camp.it.book.store.model.User;
import pl.camp.it.book.store.model.dto.UserDTO;
import pl.camp.it.book.store.services.IUserService;

import java.util.Optional;

public class UserServiceImplTest extends ServiceGenericTest {
    @Autowired
    IUserService userService;

    @Test
    public void getUserByLoginTest() {
        String login = "testUser";
        Optional<User> user = generateFakeUser();
        Mockito.when(this.userDAO.getUserByLogin(login)).thenReturn(user);

        Optional<User> result = this.userService.getUserByLogin(login);

        Mockito.verify(this.userDAO, Mockito.times(1))
                .getUserByLogin(login);
        Assertions.assertSame(user, result);
    }

    @Test
    public void persistUserTest() {
        UserDTO userDTO = new UserDTO();
        userDTO.setLogin("testLogin");
        userDTO.setName("testName");
        userDTO.setPassword("testPassword");
        userDTO.setRole(User.Role.USER);
        userDTO.setSurname("testSurname");

        User result = this.userService.persistUser(userDTO);

        Mockito.verify(this.userDAO, Mockito.times(1))
                .persistUser(result);
        Assertions.assertEquals(userDTO.getLogin(), result.getLogin());
        Assertions.assertEquals(userDTO.getName(), result.getName());
        Assertions.assertEquals(DigestUtils.md5Hex(userDTO.getPassword()),
                result.getPassword());
        Assertions.assertEquals(userDTO.getRole(), result.getRole());
        Assertions.assertEquals(userDTO.getSurname(), result.getSurname());
    }

    @Test
    public void persistNullUserTest() {
        User result = this.userService.persistUser(null);

        Mockito.verify(this.userDAO, Mockito.never()).persistUser(ArgumentMatchers.any());
        Assertions.assertNull(result);
    }

    @Test
    public void getUserByIdTest() throws ExecutionControl.NotImplementedException {
        int id = 10;
        Optional<User> user = generateFakeUser();
        Mockito.when(this.userDAO.getUserById(id)).thenReturn(user);

        Optional<User> result = this.userService.getUserById(id);

        Mockito.verify(this.userDAO, Mockito.times(1))
                .getUserById(id);
        Assertions.assertSame(user, result);
    }

    @Test
    public void updateUserTest() throws ExecutionControl.NotImplementedException {
        UserDTO userDTO = new UserDTO();
        int id = 10;
        User user = generateFakeUser().get();
        Mockito.when(this.userDAO.updateUser(userDTO, id)).thenReturn(user);

        User result = this.userService.updateUser(userDTO, id);

        Mockito.verify(this.userDAO, Mockito.times(1))
                .updateUser(userDTO, id);
        Assertions.assertSame(user, result);
    }

    private Optional<User> generateFakeUser() {
        User user = new User();
        user.setId(10);
        user.setLogin("testUser");
        user.setPassword("fed3b61b26081849378080b34e693d2e");
        user.setName("Imie");
        user.setSurname("Nazwisko");
        user.setRole(User.Role.ADMIN);

        return Optional.of(user);
    }
}
