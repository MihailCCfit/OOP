package nsu.fit.tsukanov.pizzeria.modern.persons.baker;

import lombok.extern.slf4j.Slf4j;
import nsu.fit.tsukanov.pizzeria.modern.common.buffer.OrderBoard;
import nsu.fit.tsukanov.pizzeria.modern.common.buffer.Storage;
import nsu.fit.tsukanov.pizzeria.modern.common.objects.DeliveryOrder;
import nsu.fit.tsukanov.pizzeria.modern.common.objects.Order;
import nsu.fit.tsukanov.pizzeria.modern.common.objects.Pizza;

@Slf4j
public class Baker implements Runnable {

    private final BakerEntity baker;

    private final Storage storage;

    private final OrderBoard orderBoard;

    private volatile boolean isWorking = true;

    private Order order = null;


    public Baker(BakerEntity baker, Storage storage, OrderBoard orderBoard) {
        this.baker = baker;
        this.storage = storage;
        this.orderBoard = orderBoard;
    }

    public void consume() throws InterruptedException {
        order = orderBoard.take();
    }

    public void produce() throws InterruptedException {
        Pizza pizza = new Pizza(order.pizzaName());
        baker.cook(pizza);
        log.info("{} Cooked pizza {} to {}", baker, order.pizzaName(), order.clientName());
        DeliveryOrder deliveryOrder = new DeliveryOrder(pizza, order.callOrderOwner(), order.clientName());
        order = null;
        storage.put(deliveryOrder);
        log.info("{} Put order {} to storage", baker, deliveryOrder);
    }


    @Override
    public void run() {
        log.info("{} starts working", baker);
        while (isWorking) {
            try {
                consume();
                log.info("{} took order {}", baker, order);
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

    public Order getOrder() {
        return order;
    }

    @Override
    public String toString() {
        return "Baker{" +
                "baker=" + baker +
                ", isWorking=" + isWorking +
                '}';
    }
}
