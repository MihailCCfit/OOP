package nsu.fit.tsukanov.order;

import lombok.extern.slf4j.Slf4j;

import java.util.Deque;
import java.util.LinkedList;

@Slf4j
public class OrderBoardDeque implements OrderBoard {
    private final Deque<Order> orderQueue = new LinkedList<>();


    @Override
    public Order takeOrder() {
        synchronized (this) {
            return orderQueue.removeFirst();
        }
    }

    @Override
    public Order addOrder(Order order) {

        synchronized (this) {
            orderQueue.addLast(order);
            notifyAll();
        }

        return order;
    }

    @Override
    public boolean hasOrder() {
        synchronized (this) {
            return !orderQueue.isEmpty();
        }
    }
}
