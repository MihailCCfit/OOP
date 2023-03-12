package nsu.fit.tsukanov.baker;

import lombok.extern.slf4j.Slf4j;
import nsu.fit.tsukanov.pizza.Pizza;

import java.util.Random;

@Slf4j
public record Baker(
        Long id,
        String name,
        int productionTime,
        int errorTime) {
    public Pizza cook(Pizza pizza) throws InterruptedException {
        final Random random = new Random();
        synchronized (this) {
            Thread.sleep(productionTime * 1000L + random.nextInt(errorTime + 1) * 1000L + 1);
        }
        return pizza;
    }

}
