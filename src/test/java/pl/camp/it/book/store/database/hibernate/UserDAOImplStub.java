package pl.camp.it.book.store.database.hibernate;

import jdk.jshell.spi.ExecutionControl;
import pl.camp.it.book.store.database.IUserDAO;
import pl.camp.it.book.store.model.User;
import pl.camp.it.book.store.model.dto.UserDTO;

import java.util.Optional;

public class UserDAOImplStub implements IUserDAO {

    @Override
    public Optional<User> getUserByLogin(String login) {
        if("testUser".equals(login)) {
            User user = new User();
            user.setId(10);
            user.setLogin("testUser");
            user.setPassword("fed3b61b26081849378080b34e693d2e");
            user.setName("Imie");
            user.setSurname("Nazwisko");
            user.setRole(User.Role.ADMIN);

            return Optional.of(user);
        } else if("admin".equals(login)) {
            User user = new User();
            user.setId(10);
            user.setLogin("admin");
            user.setPassword("fed3b61b260818480b34e693d2e");
            user.setName("Imie");
            user.setSurname("Nazwisko");
            user.setRole(User.Role.ADMIN);

            return Optional.of(user);
        } else {
            return Optional.empty();
        }
    }

    @Override
    public void persistUser(User user) {

    }

    @Override
    public Optional<User> getUserById(int id) throws ExecutionControl.NotImplementedException {
        return Optional.empty();
    }

    @Override
    public void updateUser(User user) throws ExecutionControl.NotImplementedException {

    }

    @Override
    public User updateUser(UserDTO userDTO, int id) throws ExecutionControl.NotImplementedException {
        return null;
    }
}
