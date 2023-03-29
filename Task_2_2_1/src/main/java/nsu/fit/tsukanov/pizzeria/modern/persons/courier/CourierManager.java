package nsu.fit.tsukanov.pizzeria.modern.persons.courier;

import lombok.extern.slf4j.Slf4j;
import nsu.fit.tsukanov.pizzeria.modern.common.buffer.Storage;
import nsu.fit.tsukanov.pizzeria.modern.common.dto.DeliveryOrder;

import java.util.ArrayList;
import java.util.Collection;

@Slf4j
public class CourierManager implements Runnable {

    private final Courier courier;

    private final Storage<DeliveryOrder> storage;


    private volatile boolean isWorking = true;

    public CourierManager(Courier courier, Storage<DeliveryOrder> storage) {
        this.courier = courier;
        this.storage = storage;
    }

    /**
     * Courier got order from storage.
     * @throws InterruptedException aboba
     */
    public void consume() throws InterruptedException {
        Collection<DeliveryOrder> orders = new ArrayList<>();
        storage.drainTo(orders, courier.canTake());
        courier.addOrder(orders);
        log.info("{} Took pizza for delivering {}", courier, orders);
    }

    /**
     * Courier deliver order.
     * @throws InterruptedException aboba
     */
    public void produce() throws InterruptedException {
        courier.deliver();
        log.info("{} Delivered orders", courier);
    }

    /**
     * Starts getting orders from storage. After that, deliver delivery order.
     */

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

    /**
     * Set working type for stop running
     *
     * @param working true - working, false - stop working
     */
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
