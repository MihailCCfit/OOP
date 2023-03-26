package nsu.fit.tsukanov.pizzeria.modern.persons.baker;

import lombok.extern.slf4j.Slf4j;
import nsu.fit.tsukanov.pizzeria.modern.common.objects.Pizza;

import java.util.Random;

@Slf4j
public record BakerEntity(
        Long id,
        String name,
        int productionTime,
        int errorTime) {
    public void cook(Pizza pizza) throws InterruptedException {
        final Random random = new Random();
        synchronized (this) {
            Thread.sleep(productionTime * 1000L + random.nextInt(errorTime + 1) * 1000L + 1);
        }
        pizza.setCooked(true);
    }

    @Override
    public String toString() {
        return "Baker{" +
                id +
                "-'" + name + '\'' +
                '}';
    }
}
