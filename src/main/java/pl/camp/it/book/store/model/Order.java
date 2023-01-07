package pl.camp.it.book.store.model;

import java.time.LocalDateTime;
import java.util.List;

public class Order {
    private int id;
    private int userId;
    private List<OrderPosition> positions;
    private LocalDateTime date;
    private State state;
    private double total;

    public Order(int id, int userId, List<OrderPosition> positions, LocalDateTime date, State state, double total) {
        this(userId, positions, date, state, total);
        this.id = id;
    }

    public Order(int userId, List<OrderPosition> positions, LocalDateTime date, State state, double total) {
        this.userId = userId;
        this.positions = positions;
        this.date = date;
        this.state = state;
        this.total = total;
    }

    public Order() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public List<OrderPosition> getPositions() {
        return positions;
    }

    public void setPositions(List<OrderPosition> positions) {
        this.positions = positions;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    public State getState() {
        return state;
    }

    public void setState(State state) {
        this.state = state;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    public enum State {
        NEW,
        PAID,
        CONFIRMED,
        SENT,
        DONE
    }
}
