package pl.camp.it.book.store.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

@Entity(name = "torder")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Order implements Saveable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @ManyToOne(fetch = FetchType.EAGER)
    private User user;
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<OrderPosition> positions = new LinkedList<>();
    private LocalDateTime date;
    @Enumerated(EnumType.STRING)
    private State state;
    private double total;

    public enum State {
        NEW,
        PAID,
        CONFIRMED,
        SENT,
        DONE
    }
}
