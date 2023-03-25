package nsu.fit.tsukanov.pizzeria.modern.persons.baker;

import nsu.fit.tsukanov.pizzeria.modern.common.ServiceFactory;
import nsu.fit.tsukanov.pizzeria.modern.common.buffer.OrderBoard;
import nsu.fit.tsukanov.pizzeria.modern.common.buffer.Storage;
import nsu.fit.tsukanov.pizzeria.modern.common.objects.DeliveryOrder;
import nsu.fit.tsukanov.pizzeria.modern.common.objects.Order;
import nsu.fit.tsukanov.pizzeria.modern.common.objects.Pizza;

public class Baker implements Runnable {

    private final BakerEntity baker;

    private final Storage storage = ServiceFactory.getStorage();

    private final OrderBoard orderBoard = ServiceFactory.getOrderBoard();

    private volatile boolean isWorking = true;

    private Order order;


    public Baker(BakerEntity baker) {
        this.baker = baker;
    }

    public void consume() throws InterruptedException {
        order = orderBoard.take();
    }

    public void produce() throws InterruptedException {
        Pizza pizza = new Pizza(order.pizzaName());
        baker.cook(pizza);
        DeliveryOrder deliveryOrder = new DeliveryOrder(pizza, order.callOrderOwner());
        storage.put(deliveryOrder);
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
