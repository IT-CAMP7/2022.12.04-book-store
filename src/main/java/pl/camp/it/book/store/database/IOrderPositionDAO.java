package pl.camp.it.book.store.database;

import pl.camp.it.book.store.model.OrderPosition;

import java.util.List;

public interface IOrderPositionDAO {
    void persistOrderPosition(OrderPosition orderPosition, int orderId);
    List<OrderPosition> getOrderPositionByOrderId(int id);
}
