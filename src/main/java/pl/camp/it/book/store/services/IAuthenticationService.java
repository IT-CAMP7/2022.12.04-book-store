package pl.camp.it.book.store.services;

import pl.camp.it.book.store.model.User;

public interface IAuthenticationService {
    boolean authenticate(String login, String password);
    void logout();
    void registerUser(User user);
}
