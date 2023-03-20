package nsu.fit.tsukanov.pizzeria.modern.modules.courier;

import lombok.extern.slf4j.Slf4j;
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

    @Override
    public void run() {
        runFlag = true;
        log.info("Courier {} started working", self);
        while (runFlag) {
            log.info("Courier {} started taking orders", self);
            consume();
            log.info("Courier {} took orders {}", self, self.orderList());
            if (!runFlag) {
                break;
            }
            log.info("Courier {} start delivering {}", self, self.orderList());
            produce();
            log.info("Courier {} ends delivering {}", self, self.orderList());
        }
        log.info("Courier {} ends working", self);
    }


    public void consume() {
        Object storageEmpty;
        synchronized (storageEmpty = storage.getEmptyBuffer()) {
            while (storage.isEmpty()) {
                try {
                    storageEmpty.wait();
                } catch (InterruptedException e) {
                    return;
                }
            }
            if (!runFlag) {
                return;
            }
            int i = 0;
            while (!storage.isEmpty() && i < self.capacity()) {
                i++;
                self.addOrder(storage.remove());
            }
            storage.getFullBuffer().notifyAll();
        }

    }

    public void produce() {
        self.deliver();
    }

    public void stop() {
        runFlag = false;
    }
}
