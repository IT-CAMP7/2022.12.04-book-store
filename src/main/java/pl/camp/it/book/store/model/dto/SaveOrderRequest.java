package pl.camp.it.book.store.model.dto;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import pl.camp.it.book.store.model.Order;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class SaveOrderRequest {
    private int userId;
    private List<OrderPosition> orderPositions = new ArrayList<>();
    private LocalDateTime date;
    private Order.State state;

    @NoArgsConstructor
    @AllArgsConstructor
    @Getter
    @Setter
    public static class OrderPosition {
        private int bookId;
        private int quantity;
    }
}
