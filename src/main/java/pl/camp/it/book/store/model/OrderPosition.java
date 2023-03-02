package pl.camp.it.book.store.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity(name = "torderposition")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class OrderPosition implements Saveable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @ManyToOne(fetch = FetchType.EAGER)
    private Book book;
    private int quantity;

    public OrderPosition(Book book, int quantity) {
        this.book = book;
        this.quantity = quantity;
    }

    public void incrementQuantity() {
        this.quantity++;
    }
}
