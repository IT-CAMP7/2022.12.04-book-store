package pl.camp.it.book.store.model.dto;

import pl.camp.it.book.store.model.Order;

import java.util.ArrayList;
import java.util.List;

public class OrdersDTO {
    private final List<Order> orders = new ArrayList<>();

    public List<Order> getOrders() {
        return orders;
    }
}
