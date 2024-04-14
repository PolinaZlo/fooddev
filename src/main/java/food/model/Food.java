package food.model;

import jakarta.persistence.*;
import jakarta.persistence.Id;
import lombok.*;

import java.util.Objects;

@Data
@Entity
@Table(name = "food")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Food {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    private double price;

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Food food = (Food) o;
        return Double.compare(price, food.price) == 0
                && Objects.equals(id, food.id)
                && Objects.equals(title, food.title);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, title, price);
    }

}
