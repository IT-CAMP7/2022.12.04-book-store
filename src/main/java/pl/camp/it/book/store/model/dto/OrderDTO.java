package pl.camp.it.book.store.model.dto;

import lombok.Getter;
import lombok.Setter;
import pl.camp.it.book.store.model.Order;
import pl.camp.it.book.store.model.OrderPosition;

import java.time.LocalDateTime;
import java.util.LinkedList;
import java.util.List;

@Getter
@Setter
public class OrderDTO {
    private int id;
    private String user;
    private List<String> positions = new LinkedList<>();
    private LocalDateTime date;
    private Order.State state;
    private double total;

    public OrderDTO(Order order) {
        this.id = order.getId();
        this.user = "http://localhost:8085/api/v1/user/" + order.getUser().getId();
        this.state = order.getState();
        this.date = order.getDate();
        this.total = order.getTotal();
        for (OrderPosition orderPosition : order.getPositions()) {
            this.positions.add("http://localhost:8085/api/v1/orderposition/"
                    + orderPosition.getId());
        }
    }
}
