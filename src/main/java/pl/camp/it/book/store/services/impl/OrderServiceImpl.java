package pl.camp.it.book.store.services.impl;

import jakarta.annotation.Resource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.camp.it.book.store.database.IOrderDAO;
import pl.camp.it.book.store.exceptions.NotEnoughBookException;
import pl.camp.it.book.store.model.Book;
import pl.camp.it.book.store.model.Order;
import pl.camp.it.book.store.model.OrderPosition;
import pl.camp.it.book.store.services.ICartService;
import pl.camp.it.book.store.services.IOrderService;
import pl.camp.it.book.store.session.SessionObject;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Service
public class OrderServiceImpl implements IOrderService {

    @Resource
    SessionObject sessionObject;

    @Autowired
    ICartService cartService;

    @Autowired
    IOrderDAO orderDAO;

    @Override
    public void confirmOrder() {
        Collection<OrderPosition> orderPositions = this.sessionObject.getCart().values();

        for(OrderPosition orderPosition : orderPositions) {
            Book book = orderPosition.getBook();
            int newBookQuantity = book.getQuantity() - orderPosition.getQuantity();
            if(newBookQuantity < 0) {
                throw new NotEnoughBookException();
            }
            book.setQuantity(newBookQuantity);
        }

        Order order = new Order(this.sessionObject.getUser().getId(),
                new ArrayList<>(orderPositions),
                LocalDateTime.now(),
                Order.State.NEW,
                this.cartService.calculateCartSum());

        this.orderDAO.persistOrder(order);

        this.cartService.clearCart();
    }

    @Override
    public List<Order> getOrderForCurrentUser() {
        return this.orderDAO.getOrdersByUserId(this.sessionObject.getUser().getId());
    }
}
