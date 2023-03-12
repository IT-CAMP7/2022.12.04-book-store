package pl.camp.it.book.store.services.impl;

import jakarta.annotation.Resource;
import org.apache.commons.codec.digest.DigestUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import pl.camp.it.book.store.exceptions.UserLoginExistException;
import pl.camp.it.book.store.model.User;
import pl.camp.it.book.store.services.IAuthenticationService;
import pl.camp.it.book.store.session.SessionObject;

import java.util.Optional;

public class AuthenticationServiceImplTest extends ServiceGenericTest {

    @Autowired
    IAuthenticationService authenticationService;

    @Resource
    SessionObject sessionObject;

    @Test
    public void correctAuthenticationTest() {
        Mockito.when(this.userDAO.getUserByLogin(ArgumentMatchers.anyString()))
                .thenReturn(generateFakeUser());
        String login = "testUser";
        String password = "testPassword";

        this.authenticationService.authenticate(login, password);

        Assertions.assertTrue(this.sessionObject.isLogged());
        Assertions.assertEquals(login, this.sessionObject.getUser().getLogin());
    }

    @Test
    public void incorrectLoginAuthenticationTest() {
        Mockito.when(this.userDAO.getUserByLogin(ArgumentMatchers.anyString()))
                .thenReturn(Optional.empty());
        String login = "nieistniejacylogin";
        String password = "lksdjafhlkk";

        this.authenticationService.authenticate(login, password);

        Assertions.assertFalse(this.sessionObject.isLogged());
    }

    @Test
    public void correctLoginAndIncorrectPasswordAuthenticationTest() {
        Mockito.when(this.userDAO.getUserByLogin(ArgumentMatchers.anyString()))
                .thenReturn(generateFakeUser());
        String login = "testUser";
        String password = "zlehaslo";

        this.authenticationService.authenticate(login, password);

        Assertions.assertFalse(this.sessionObject.isLogged());
    }

    @Test
    public void logoutUserTest() {
        this.sessionObject.setUser(generateFakeUser().get());

        this.authenticationService.logout();

        Assertions.assertFalse(this.sessionObject.isLogged());
        Assertions.assertNull(this.sessionObject.getUser());
    }

    @Test
    public void logoutNotLoggedUserTest() {
        this.sessionObject.setUser(null);

        this.authenticationService.logout();

        Assertions.assertFalse(this.sessionObject.isLogged());
        Assertions.assertNull(this.sessionObject.getUser());
    }

    @Test
    public void registerUserTest() {
        User user = generateFakeUser().get();
        user.setPassword("testPassword");
        user.setRole(null);
        String expectedPassword = DigestUtils.md5Hex("testPassword");
        User.Role expectedRole = User.Role.USER;

        this.authenticationService.registerUser(user);

        Assertions.assertEquals(expectedPassword, user.getPassword());
        Assertions.assertEquals(expectedRole, user.getRole());

        Mockito.verify(this.userDAO, Mockito.only())
                .persistUser(ArgumentMatchers.any(User.class));
        Mockito.verify(this.userDAO, Mockito.times(1))
                .persistUser(user);
    }

    @Test
    public void loginAlreadyExistWhenRegister() {
        Mockito.doThrow(new UserLoginExistException())
                .when(this.userDAO).persistUser(ArgumentMatchers.any());
        User user = generateFakeUser().get();
        user.setPassword("testPassword");
        user.setRole(null);

        Assertions.assertThrows(UserLoginExistException.class,
                () -> this.authenticationService.registerUser(user));
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
