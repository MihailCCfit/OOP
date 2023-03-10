package nsu.fit.tsukanov.baker;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import nsu.fit.tsukanov.pizza.Pizza;

import java.util.Objects;
import java.util.Random;

@AllArgsConstructor
@Getter
@Setter
@Slf4j
public class Baker {
    private Long id;
    private String name;
    private int productionTime;
    private int errorTime;

    private final Random random = new Random();

    public Pizza cook(Pizza pizza) throws InterruptedException {
        synchronized (this) {
            Thread.sleep(productionTime * 1000L + random.nextInt(errorTime+1) * 1000L + 1);
        }
        return pizza;
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof Baker)) {
            return false;
        }
        return ((Baker) obj).id.equals(this.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "{" + id +
                ": " + name +
                '}';
    }
}
