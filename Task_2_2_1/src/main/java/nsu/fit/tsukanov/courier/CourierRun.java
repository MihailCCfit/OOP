package nsu.fit.tsukanov.courier;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import nsu.fit.tsukanov.storage.Storage;
import nsu.fit.tsukanov.workingType.WorkingType;

import static nsu.fit.tsukanov.workingType.WorkingType.*;

@RequiredArgsConstructor
@Slf4j
public class CourierRun implements Runnable {
    private final Courier self;
    private final Storage storage;
    private WorkingType workingType;

    public WorkingType getWorkingType() {
        return workingType;
    }

    public void setWorkingType(WorkingType workingType) {
        this.workingType = workingType;
    }

    public CourierRun(Courier self, Storage storage, WorkingType workingType) {
        this.self = self;
        this.storage = storage;
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
        log.info("Courier {} started working", self);
        while (loopCondition()) {
            synchronized (storage) {
                while (storage.isEmpty()) {
                    try {
                        storage.wait();
                    } catch (InterruptedException e) {
                        if (workingType == ALARM) {
                            log.warn("Courier {} evacuated", self);
                            return;
                        } else if (workingType == LAST) {
                            log.info("Courier {} end working", self);
                            return;
                        }
                    }
                }
                log.info("Courier {} waits for pizza", self);
                var orders = storage.takePizzaOrders(self.getCapacity());
                log.info("Courier {} get orders {}", self, orders);
                self.addOrders(orders);
                storage.notifyAll();
            }
            log.info("Courier {} starts delivering", self);
            self.deliver();
        }
    }

    boolean loopCondition() {
        return workingType == WORKING;
    }
}
