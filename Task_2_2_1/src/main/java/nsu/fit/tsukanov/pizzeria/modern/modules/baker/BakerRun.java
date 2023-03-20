package nsu.fit.tsukanov.pizzeria.modern.modules.baker;

import lombok.extern.slf4j.Slf4j;
import nsu.fit.tsukanov.pizzeria.modern.common.pizza.Pizza;
import nsu.fit.tsukanov.pizzeria.modern.modules.order.Order;
import nsu.fit.tsukanov.pizzeria.modern.modules.order.OrderBoard;
import nsu.fit.tsukanov.pizzeria.modern.modules.storage.Storage;

@Slf4j
public class BakerRun implements Runnable {
    private final Baker self;
    private final Storage storage;
    private final OrderBoard orderBoard;
    private Order currentOrder = null;

    private volatile boolean runFlag;

    public BakerRun(Baker self, Storage storage, OrderBoard orderBoard) {
        this.self = self;
        this.storage = storage;
        this.orderBoard = orderBoard;
        runFlag = true;
    }

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
        runFlag = true;
        log.info("Baker {} started working", self);
        while (runFlag) {
            log.info("Baker {} started taking order", self);
            consume();
            log.info("Baker {} took order {}", self, currentOrder);
            if (!runFlag) {
                break;
            }
            log.info("Baker {} start producing {}", self, currentOrder);
            produce();
            log.info("Baker {} ends producing {}", self, currentOrder);
        }
        log.info("Baker {} ends working", self);
    }


    public void consume() {
        Object boardEmpty;
        synchronized (boardEmpty = orderBoard.getEmptyBuffer()) {
            while (orderBoard.isEmpty()) {
                try {
                    boardEmpty.wait();
                } catch (InterruptedException e) {
                    return;
                }
            }
            if (!runFlag) {
                return;
            }
            currentOrder = orderBoard.remove();
            storage.getFullBuffer().notifyAll();
        }

    }

    public void produce() {
        try {
            Pizza producedPizza = self.cook(currentOrder.getPizza());
            currentOrder.setPizza(producedPizza);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        if (!runFlag) {
            return;
        }
        Object storageFull;
        synchronized (storageFull = storage.getFullBuffer()) {
            while (storage.isFull()) {
                try {
                    storageFull.wait();
                } catch (InterruptedException e) {
                    break;
                }
            }
            if (!runFlag) {
                return;
            }
            storage.add(currentOrder);
            storage.getEmptyBuffer().notifyAll();
        }
    }

    public void stop() {
        runFlag = false;
    }
}
