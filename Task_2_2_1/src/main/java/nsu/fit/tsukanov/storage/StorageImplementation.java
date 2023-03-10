package nsu.fit.tsukanov.storage;

import lombok.extern.slf4j.Slf4j;
import nsu.fit.tsukanov.configuration.Configuration;
import nsu.fit.tsukanov.order.Order;

import java.util.ArrayList;
import java.util.Deque;
import java.util.LinkedList;
import java.util.List;

@Slf4j
public class StorageImplementation implements Storage {
    private final Deque<Order> pizzaOrders = new LinkedList<>();

    private final int maxSize = Configuration.MAX_STORAGE_SIZE;

    private int amount = 0;


    @Override
    public boolean isFull() {
        assert (amount > 0 && amount <= maxSize);
        return amount == maxSize;
    }

    @Override
    public boolean isEmpty() {
        return amount == 0;
    }

    @Override
    public long amountOfPizza() {
        return amount;
    }

    @Override
    public Order addPizzaOrder(Order pizzaOrder) {
        synchronized (this) {
            pizzaOrders.add(pizzaOrder);
            amount++;
        }
        log.info("Storage size: {}. Pizza added", amount);
        this.notifyAll();
        return pizzaOrder;
    }

    @Override
    public Order takePizzaOrder() {

        if (amount == 0) {
            throw new IllegalStateException();
        }
        Order pizzaOrder;
        synchronized (this) {
            amount--;
            pizzaOrder = pizzaOrders.removeLast();
        }
        log.info("Storage size: {}. Pizza taked", amount);
        this.notifyAll();
        return pizzaOrder;
    }

    @Override
    public List<Order> takePizzaOrders(long amount) {
        List<Order> orders = new ArrayList<>();
        synchronized (pizzaOrders) {
            for (long i = 0; i < amount; i++) {
                if (pizzaOrders.isEmpty()) {
                    break;
                }
                orders.add(pizzaOrders.removeLast());
            }
        }
        this.notifyAll();
        return orders;
    }


}
