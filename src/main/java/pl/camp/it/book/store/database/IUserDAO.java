package pl.camp.it.book.store.database;

import pl.camp.it.book.store.model.User;

import java.util.Optional;

public interface IUserDAO {
    Optional<User> getUserByLogin(String login);
    void persistUser(User user);
}
