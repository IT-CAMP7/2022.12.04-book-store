package pl.camp.it.book.store.model.dto;

import lombok.Getter;
import lombok.Setter;
import pl.camp.it.book.store.model.User;

@Getter
@Setter
public class UserResponseDTO {
    private int id;
    private String name;
    private String surname;
    private String login;
    private User.Role role;
    private String orders;

    public UserResponseDTO(User user) {
        this.id = user.getId();
        this.name = user.getName();
        this.surname = user.getSurname();
        this.login = user.getLogin();
        this.role = user.getRole();
        this.orders = "http://localhost:8085/api/v1/order?userId=" + user.getId();
    }
}
