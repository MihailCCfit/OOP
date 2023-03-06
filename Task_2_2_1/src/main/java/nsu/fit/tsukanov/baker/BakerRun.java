package nsu.fit.tsukanov.baker;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import nsu.fit.tsukanov.order.Order;
import nsu.fit.tsukanov.order.OrderBoard;
import nsu.fit.tsukanov.pizza.PizzaOrder;
import nsu.fit.tsukanov.storage.Storage;


@AllArgsConstructor
@Slf4j
public class BakerRun implements Runnable {
    private final Baker self;
    private final Storage storage;
    private final OrderBoard orderBoard;

    /**
     * When an object implementing interface {@code Runnable} is used
     * to create a thread, starting the thread causes the object's
     * {@code run} method to be called in that separately executing
     * thread.
     * <p>
     * The general contract of the method {@code run} is that it may
     * take any action whatsoever.
     *
     * @see Thread#run()
     */
    @Override
    public void run() {
        log.info("Baker {} started working", self);
        while (endCondition()) {
            Order order;
            synchronized (orderBoard) {
                while (!orderBoard.hasOrder()) {
                    try {
                        orderBoard.wait();
                    } catch (InterruptedException e) {
                        log.warn("{} Ends working at board", self);
                        return;
                    }
                }
                order = orderBoard.takeOrder();
                log.info("Baker {} get order {}", self, order);
            }
            log.info("Baker {} starts cooking", self);
            PizzaOrder pizzaOrder = self.cook(order.getPizzaOrder());
            log.info("Baker {} ends cooking pizza {}", self, pizzaOrder);
            synchronized (storage) {
                while (storage.isFull()) {
                    try {
                        storage.wait();
                    } catch (InterruptedException e) {
                        log.warn("{} Ends working at storage", self);
                        return;
                    }
                }
                storage.addPizzaOrder(pizzaOrder);
                log.info("{} put order into storage", self);
            }
            storage.notifyAll();
        }
    }

    boolean endCondition() {
        return true;
    }
}
