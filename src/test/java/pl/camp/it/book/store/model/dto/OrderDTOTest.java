package pl.camp.it.book.store.model.dto;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import pl.camp.it.book.store.model.Order;
import pl.camp.it.book.store.model.OrderPosition;
import pl.camp.it.book.store.model.User;

import java.time.LocalDateTime;

public class OrderDTOTest {

    @Test
    public void constructorTest() {
        Order order = generateFakeOrder();
        String expectedUser = "http://localhost:8085/api/v1/user/15";
        int expectedPositionsCount = 3;
        String expectedFirstPosition = "http://localhost:8085/api/v1/orderposition/1";
        String expectedSecondPosition = "http://localhost:8085/api/v1/orderposition/2";
        String expectedThirdPosition = "http://localhost:8085/api/v1/orderposition/3";

        OrderDTO result = new OrderDTO(order);

        Assertions.assertEquals(order.getId(), result.getId());
        Assertions.assertEquals(expectedUser, result.getUser());
        Assertions.assertEquals(order.getState(), result.getState());
        Assertions.assertEquals(order.getTotal(), result.getTotal());
        Assertions.assertEquals(order.getDate(), result.getDate());
        Assertions.assertEquals(expectedPositionsCount, result.getPositions().size());
        Assertions.assertTrue(result.getPositions().contains(expectedFirstPosition));
        Assertions.assertTrue(result.getPositions().contains(expectedSecondPosition));
        Assertions.assertTrue(result.getPositions().contains(expectedThirdPosition));
    }

    @Test
    public void nullUserConstructorTest() {
        Order order = generateFakeOrder();
        order.setUser(null);
        int expectedPositionsCount = 3;
        String expectedFirstPosition = "http://localhost:8085/api/v1/orderposition/1";
        String expectedSecondPosition = "http://localhost:8085/api/v1/orderposition/2";
        String expectedThirdPosition = "http://localhost:8085/api/v1/orderposition/3";

        OrderDTO result = new OrderDTO(order);

        Assertions.assertEquals(order.getId(), result.getId());
        Assertions.assertNull(result.getUser());
        Assertions.assertEquals(order.getState(), result.getState());
        Assertions.assertEquals(order.getTotal(), result.getTotal());
        Assertions.assertEquals(order.getDate(), result.getDate());
        Assertions.assertEquals(expectedPositionsCount, result.getPositions().size());
        Assertions.assertTrue(result.getPositions().contains(expectedFirstPosition));
        Assertions.assertTrue(result.getPositions().contains(expectedSecondPosition));
        Assertions.assertTrue(result.getPositions().contains(expectedThirdPosition));
    }

    private Order generateFakeOrder() {
        Order order = new Order();
        order.setId(10);
        order.setState(Order.State.NEW);
        order.setDate(LocalDateTime.of(2023, 3, 12, 10, 0));
        order.setTotal(105.99);

        order.setUser(generateFakeUser());
        order.getPositions().add(generateFakeOrderPosition(1,4));
        order.getPositions().add(generateFakeOrderPosition(2,5));
        order.getPositions().add(generateFakeOrderPosition(3,2));

        return order;
    }

    private User generateFakeUser() {
        User user = new User();
        user.setId(15);
        user.setRole(User.Role.USER);
        user.setPassword("oaisudboiu");
        user.setLogin("akshjdf");
        user.setName("aksjhdgf");
        user.setSurname("askjhdgasd");

        return user;
    }

    private OrderPosition generateFakeOrderPosition(int id, int quantity) {
        OrderPosition orderPosition = new OrderPosition();
        orderPosition.setId(id);
        orderPosition.setQuantity(quantity);

        return orderPosition;
    }
}
