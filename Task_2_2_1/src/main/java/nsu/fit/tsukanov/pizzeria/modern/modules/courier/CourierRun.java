package nsu.fit.tsukanov.pizzeria.modern.modules.courier;

import lombok.extern.slf4j.Slf4j;
import nsu.fit.tsukanov.pizzeria.modern.common.pizza.Pizza;
import nsu.fit.tsukanov.pizzeria.modern.modules.storage.Storage;

@Slf4j
public class CourierRun implements Runnable {
    private final Courier self;
    private final Storage storage;

    private volatile boolean runFlag;

    public CourierRun(Courier self, Storage storage) {
        this.self = self;
        this.storage = storage;
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
        if (orderBoard.isEmpty()) {
            try {
                orderBoard.wait();
            } catch (InterruptedException e) {
                return;
            }
        }
        if (!runFlag) {
            return;
        }
        currentOrder = orderBoard.remove();
        orderBoard.notifyAllForFull();

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
        while (storage.isFull()) {
            try {
                storage.wait();
            } catch (InterruptedException e) {
                break;
            }
        }
        if (!runFlag) {
            return;
        }
        storage.add(currentOrder);
        storage.notifyAllForEmpty();
    }

    public void stop() {
        runFlag = false;
        orderBoard.notifyAllForFull();
    }
}
