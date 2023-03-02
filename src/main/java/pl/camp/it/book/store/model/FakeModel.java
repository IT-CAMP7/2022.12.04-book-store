package pl.camp.it.book.store.model;

import lombok.*;
import lombok.experimental.Accessors;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@EqualsAndHashCode
@Builder
@Accessors(fluent = true)
public class FakeModel {
    private int id;
    private String cos;
    private String cos2;

    public void setId(int id) {
        System.out.println("seter do id robi cos customowego !!");
        this.id = id;
    }
}
