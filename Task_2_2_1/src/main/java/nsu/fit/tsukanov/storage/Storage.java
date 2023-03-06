package nsu.fit.tsukanov.storage;

import nsu.fit.tsukanov.pizza.PizzaOrder;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface Storage {
    boolean isFull();

    boolean isEmpty();

    long amountOfPizza();

    PizzaOrder addPizzaOrder(PizzaOrder pizzaOrder);

    PizzaOrder takePizzaOrder();

    List<PizzaOrder> takePizzaOrders(long amount);
}
