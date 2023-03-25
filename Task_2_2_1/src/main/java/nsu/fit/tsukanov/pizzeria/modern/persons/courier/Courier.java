package nsu.fit.tsukanov.pizzeria.modern.persons.courier;

import nsu.fit.tsukanov.pizzeria.modern.common.ServiceFactory;
import nsu.fit.tsukanov.pizzeria.modern.common.buffer.OrderBoard;
import nsu.fit.tsukanov.pizzeria.modern.common.buffer.Storage;
import nsu.fit.tsukanov.pizzeria.modern.common.objects.DeliveryOrder;
import nsu.fit.tsukanov.pizzeria.modern.common.objects.Order;

import java.util.ArrayList;
import java.util.Collection;

public class Courier implements Runnable {

    private final CourierEntity courier;

    private final Storage storage = ServiceFactory.getStorage();

    private final OrderBoard orderBoard = ServiceFactory.getOrderBoard();

    private volatile boolean isWorking = true;

    private Order order;


    public Courier(CourierEntity courier) {
        this.courier = courier;
    }

    public void consume() throws InterruptedException {
        Collection<DeliveryOrder> orders = new ArrayList<>();
        int amount = courier.canTake();
        storage.drainTo(orders, amount);
        courier.addOrder(orders);
    }

    public void produce() throws InterruptedException {
        courier.delivery();
    }


    @Override
    public void run() {
        while (isWorking) {
            try {
                consume();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            if (!isWorking) {
                break;
            }
            try {
                produce();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            if (!isWorking) {
                break;
            }
        }
    }

    public boolean isWorking() {
        return isWorking;
    }

    public void setWorking(boolean working) {
        isWorking = working;
    }


}
