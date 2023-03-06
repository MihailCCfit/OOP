package nsu.fit.tsukanov.baker;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import nsu.fit.tsukanov.pizza.PizzaOrder;

@AllArgsConstructor
@Getter
@Setter
public class Baker {
    private Long id;
    private String name;
    private Long productionTime;
    private Long errorTime;

    public PizzaOrder cook(PizzaOrder pizzaOrder) {
        try {
            wait(productionTime * 1000 + errorTime * 100);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        return pizzaOrder;
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof Baker)) {
            return false;
        }
        return ((Baker) obj).id.equals(this.id);
    }

    @Override
    public String toString() {
        return "Baker{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
