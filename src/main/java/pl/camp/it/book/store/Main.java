package pl.camp.it.book.store;

import pl.camp.it.book.store.model.User;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class Main {
    public static void main(String[] args) {
        User user = new User(1, "Janusz", "Kowalski",
                "janusz123", "janusz123", User.Role.ADMIN);

        Optional<User> userBox = Optional.of(user);
        Optional<User> userBox2 = Optional.empty();

        if(userBox.isPresent()) {
            User user2 = userBox.get();
        }

        //userBox2.get();
        User user3 = userBox2.orElse(new User(2, "Wiesiek",
                "Malinowski", "wisiek123", "wiesiek123",
                User.Role.ADMIN));
        System.out.println(user3.getName());

        Map<Integer, String> map = new HashMap<>();
        map.put(1, "asdfg");

        map.remove(3);

        System.out.println(map);
    }
}
