package pl.camp.it.book.store.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity(name = "tuser")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class User implements Saveable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String name;
    private String surname;
    @Column(unique = true)
    private String login;
    private String password;
    @Enumerated(EnumType.STRING)
    private Role role;
    @OneToMany(cascade = CascadeType.MERGE, fetch = FetchType.EAGER)
    private List<Order> orders = new ArrayList<>();

    public User(int id, String name, String surname, String login, String password, Role role) {
        this.id = id;
        this.name = name;
        this.surname = surname;
        this.login = login;
        this.password = password;
        this.role = role;
    }

    public enum Role {
        ADMIN,
        USER
    }

    public static class UserBuilder {
        User user = new User();

        public UserBuilder id(int id) {
            this.user.setId(id);
            return this;
        }

        public UserBuilder name(String name) {
            this.user.setName(name);
            return this;
        }

        public UserBuilder surname(String surname) {
            this.user.setSurname(surname);
            return this;
        }

        public UserBuilder login(String login) {
            this.user.setLogin(login);
            return this;
        }

        public UserBuilder password(String password) {
            this.user.setPassword(password);
            return this;
        }

        public UserBuilder role(Role role) {
            this.user.setRole(role);
            return this;
        }

        public User build() {
            return this.user;
        }

        public UserBuilder clone(User user) {
            id(user.getId())
                    .name(user.getName())
                    .surname(user.getSurname())
                    .login(user.getLogin())
                    .password(user.getPassword())
                    .role(user.getRole());
            return this;
        }
    }
}
