package nsu.fit.tsukanov.order;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Deque;
import java.util.LinkedList;

@Slf4j
@Service
public class OrderBoardDeque implements OrderBoard {
    private final Deque<Order> orderQueue = new LinkedList<>();


    @Override
    public Order takeOrder() {
        synchronized (orderQueue) {
            return orderQueue.removeFirst();
        }
    }

    @Override
    public Order addOrder(Order order) {

        synchronized (orderQueue) {
            orderQueue.addLast(order);
        }
        this.notifyAll();
        return order;
    }

    @Override
    public boolean hasOrder() {
        synchronized (orderQueue) {
            return !orderQueue.isEmpty();
        }
    }
}
