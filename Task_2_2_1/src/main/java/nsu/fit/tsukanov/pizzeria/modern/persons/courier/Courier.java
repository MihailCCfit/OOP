package nsu.fit.tsukanov.pizzeria.modern.persons.courier;

import lombok.extern.slf4j.Slf4j;
import nsu.fit.tsukanov.pizzeria.modern.common.buffer.Storage;
import nsu.fit.tsukanov.pizzeria.modern.common.objects.DeliveryOrder;

import java.util.ArrayList;
import java.util.Collection;

@Slf4j
public class Courier implements Runnable {

    private final CourierEntity courier;

    private final Storage storage;


    private volatile boolean isWorking = true;

    public Courier(CourierEntity courier, Storage storage) {
        this.courier = courier;
        this.storage = storage;
    }

    public void consume() throws InterruptedException {
        Collection<DeliveryOrder> orders = new ArrayList<>();
        storage.drainTo(orders, courier.canTake());
        courier.addOrder(orders);
        log.info("{} Took pizza for delivering {}", courier, orders);
    }

    public void produce() throws InterruptedException {
        courier.delivery();
        log.info("{} Delivered orders", courier);
    }


    @Override
    public void run() {
        while (isWorking) {
            try {
                consume();
            } catch (InterruptedException e) {
                if (!isWorking) {
                    break;
                }
            }

            try {
                produce();
            } catch (InterruptedException e) {
                if (!isWorking) {
                    break;
                }
            }

        }
    }

    public boolean isWorking() {
        return isWorking;
    }

    public void setWorking(boolean working) {
        isWorking = working;
    }


    @Override
    public String toString() {
        return "Courier{" +
                "courier=" + courier +
                ", isWorking=" + isWorking +
                '}';
    }
}
