package nsu.fit.tsukanov.storage;

import nsu.fit.tsukanov.order.Order;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface Storage {
    boolean isFull();

    boolean isEmpty();

    long amountOfPizza();

    Order addPizzaOrder(Order pizzaOrder);

    Order takePizzaOrder();

    List<Order> takePizzaOrders(long amount);
}
