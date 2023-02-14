package pl.camp.it.book.store.services;

import pl.camp.it.book.store.model.Order;

import java.util.List;

public interface IOrderService {
    void confirmOrder();
    List<Order> getOrderForCurrentUser();

    void persistOrder(Order order);
    List<Order> getOrderByUserId(int userId);
}
