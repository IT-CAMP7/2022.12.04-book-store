package pl.camp.it.book.store.controllers.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import pl.camp.it.book.store.model.Order;
import pl.camp.it.book.store.model.dto.OrdersDTO;
import pl.camp.it.book.store.services.IOrderService;

@RestController
@RequestMapping(path = "/api/v1/order")
public class RestApiOrderController {

    @Autowired
    IOrderService orderService;

    @RequestMapping(path = "", method = RequestMethod.GET)
    public OrdersDTO getOrdersByUserId(@RequestParam int userId) {
        OrdersDTO ordersDTO = new OrdersDTO();
        ordersDTO.getOrders().addAll(orderService.getOrderByUserId(userId));
        return ordersDTO;
    }
}
