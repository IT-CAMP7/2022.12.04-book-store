package pl.camp.it.book.store.database.hibernate;

import pl.camp.it.book.store.database.IOrderPositionDAO;
import pl.camp.it.book.store.model.OrderPosition;

import java.util.List;
import java.util.Optional;

public class OrderPositionDAOImplStub implements IOrderPositionDAO {
    @Override
    public void persistOrderPosition(OrderPosition orderPosition, int orderId) {

    }

    @Override
    public List<OrderPosition> getOrderPositionByOrderId(int id) {
        return null;
    }

    @Override
    public Optional<OrderPosition> getOrderPositionById(int id) {
        return Optional.empty();
    }
}
