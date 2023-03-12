package pl.camp.it.book.store.database.hibernate;

import jdk.jshell.spi.ExecutionControl;
import pl.camp.it.book.store.database.IOrderDAO;
import pl.camp.it.book.store.model.Order;

import java.util.List;
import java.util.Optional;

public class OrderDAOImplStub implements IOrderDAO {
    @Override
    public void persistOrder(Order order) {

    }

    @Override
    public void updateOrder(Order order) throws ExecutionControl.NotImplementedException {

    }

    @Override
    public List<Order> getOrdersByUserId(int userId) {
        return null;
    }

    @Override
    public Optional<Order> getOrderById(int id) {
        return Optional.empty();
    }
}
