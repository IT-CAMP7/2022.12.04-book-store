package pl.camp.it.book.store.database;

import jdk.jshell.spi.ExecutionControl;
import pl.camp.it.book.store.model.User;
import pl.camp.it.book.store.model.dto.UserDTO;

import java.sql.SQLIntegrityConstraintViolationException;
import java.util.Optional;

public interface IUserDAO {
    Optional<User> getUserByLogin(String login);
    void persistUser(User user);

    Optional<User> getUserById(int id) throws ExecutionControl.NotImplementedException;

    void updateUser(User user) throws ExecutionControl.NotImplementedException;

    public User updateUser(UserDTO userDTO, int id) throws ExecutionControl.NotImplementedException;
}
