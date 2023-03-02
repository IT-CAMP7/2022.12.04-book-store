package pl.camp.it.book.store.model.dto;

import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
public class OrdersDTO {
    private final List<OrderDTO> orders = new ArrayList<>();
}
