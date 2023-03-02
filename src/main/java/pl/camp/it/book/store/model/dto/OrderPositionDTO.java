package pl.camp.it.book.store.model.dto;

import lombok.Getter;
import lombok.Setter;
import pl.camp.it.book.store.model.OrderPosition;

@Getter
@Setter
public class OrderPositionDTO {
    private int id;
    private String book;
    private int quantity;

    public OrderPositionDTO(OrderPosition orderPosition) {
        this.id = orderPosition.getId();
        this.book = "http://localhost:8085/api/v1/book/" + orderPosition.getBook().getId();
        this.quantity = orderPosition.getQuantity();
    }
}
