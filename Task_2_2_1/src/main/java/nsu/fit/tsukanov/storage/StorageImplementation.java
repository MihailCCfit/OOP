package nsu.fit.tsukanov.storage;

import lombok.extern.slf4j.Slf4j;
import nsu.fit.tsukanov.pizza.PizzaOrder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Deque;
import java.util.LinkedList;
import java.util.List;

@Slf4j
@Repository
public class StorageImplementation implements Storage {
    private final Deque<PizzaOrder> pizzaOrders = new LinkedList<>();

    @Value(value = "${custom.storage.max-size}")
    private int maxSize;

    private int amount = 0;


    @Override
    public boolean isFull() {
        assert (amount > 0 && amount <= maxSize);
        return amount == maxSize;
    }

    @Override
    public boolean isEmpty() {
        return amount==0;
    }

    @Override
    public long amountOfPizza() {
        return amount;
    }

    @Override
    public PizzaOrder addPizzaOrder(PizzaOrder pizzaOrder) {
        synchronized (this) {
            pizzaOrders.add(pizzaOrder);
            amount++;
        }
        return pizzaOrder;
    }

    @Override
    public PizzaOrder takePizzaOrder() {
        if (amount==0){
            throw new IllegalStateException();
        }
        synchronized (this) {
            amount--;
            return pizzaOrders.removeLast();
        }
    }

    @Override
    public List<PizzaOrder> takePizzaOrders(long amount) {
        List<PizzaOrder> orders = new ArrayList<>();
        synchronized (pizzaOrders) {
            for (long i = 0; i < amount; i++) {
                if (pizzaOrders.isEmpty()) {
                    break;
                }
                orders.add(pizzaOrders.removeLast());
            }
        }
        return orders;
    }


}
