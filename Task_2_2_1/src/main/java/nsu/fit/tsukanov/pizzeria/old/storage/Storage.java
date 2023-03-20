package nsu.fit.tsukanov.pizzeria.old.storage;

import nsu.fit.tsukanov.pizzeria.old.order.Order;

import java.util.List;


public interface Storage {
    boolean isFull();

    boolean isEmpty();

    long amountOfPizza();

    Order addPizzaOrder(Order pizzaOrder);

    Order takePizzaOrder();

    List<Order> takePizzaOrders(int amount);
}
