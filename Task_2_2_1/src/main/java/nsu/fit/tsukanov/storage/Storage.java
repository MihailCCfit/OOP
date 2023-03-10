package nsu.fit.tsukanov.storage;

import nsu.fit.tsukanov.order.Order;

import java.util.List;


public interface Storage {
    boolean isFull();

    boolean isEmpty();

    long amountOfPizza();

    Order addPizzaOrder(Order pizzaOrder);

    Order takePizzaOrder();

    List<Order> takePizzaOrders(int amount);
}
