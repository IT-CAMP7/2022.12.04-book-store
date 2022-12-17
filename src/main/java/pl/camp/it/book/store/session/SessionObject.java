package pl.camp.it.book.store.session;

import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.SessionScope;
import pl.camp.it.book.store.model.User;

import java.util.Optional;

@Component
@SessionScope
public class SessionObject {
    private User user = null;
    private Optional<String> pattern = Optional.empty();

    public boolean isLogged() {
        return this.user != null;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Optional<String> getPattern() {
        Optional<String> temp = pattern;
        pattern = Optional.empty();
        return temp;
    }

    public void setPattern(String pattern) {
        this.pattern = Optional.of(pattern);
    }
}
