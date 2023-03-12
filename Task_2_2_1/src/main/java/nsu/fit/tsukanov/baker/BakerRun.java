package nsu.fit.tsukanov.baker;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import nsu.fit.tsukanov.order.Order;
import nsu.fit.tsukanov.order.OrderBoard;
import nsu.fit.tsukanov.storage.Storage;
import nsu.fit.tsukanov.workingType.WorkingType;

import static nsu.fit.tsukanov.workingType.WorkingType.*;


@AllArgsConstructor
@Slf4j
public class BakerRun implements Runnable {
    private final Baker self;
    private final Storage storage;
    private final OrderBoard orderBoard;
    private WorkingType workingType;

    public WorkingType getWorkingType() {
        return workingType;
    }

    public void setWorkingType(WorkingType workingType) {
        this.workingType = workingType;
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
        log.info("Baker {} started working", self);
        while (loopCondition()) {
            Order order;
            synchronized (orderBoard) {
                while (!orderBoard.hasOrder()) {
                    try {
                        orderBoard.wait();
                    } catch (InterruptedException e) {
                        if (workingType == ALARM) {
                            log.warn("Baker {} evacuated", self);
                            return;
                        } else if (workingType == LAST) {
                            log.info("Baker {} end working", self);
                            return;
                        }
                    }
                }
                order = orderBoard.takeOrder();
                log.info("Baker {} get order {}", self, order);
                orderBoard.notifyAll();
            }
            log.info("Baker {} starts cooking", self);
            try {
                order.setPizza(self.cook(order.getPizza()));
            } catch (InterruptedException e) {
                if (workingType == ALARM) {
                    log.warn("Baker {} evacuated", self);
                    return;
                } else if (workingType == LAST) {
                    orderBoard.addOrder(order);
                    log.info("Baker {} end working, returned order", self);
                    return;
                }

            }
            log.info("Baker {} ends cooking pizza {}", self, order.getPizza());
            synchronized (storage) {
                while (storage.isFull()) {
                    try {
                        log.info("{}", storage.amountOfPizza());
                        storage.notifyAll();
                        storage.wait();
                    } catch (InterruptedException e) {
                        if (workingType == ALARM) {
                            log.warn("Baker {} evacuated", self);
                            return;
                        } else if (workingType == LAST) {
                            orderBoard.addOrder(order);
                            log.info("Baker {} end working, returned order", self);
                            return;
                        }
                    }
                }
                storage.addPizzaOrder(order);
                log.info("Baker {} put order into storage", self);
                storage.notifyAll();
            }
        }
    }

    boolean loopCondition() {
        return workingType == WORKING;
    }
}
