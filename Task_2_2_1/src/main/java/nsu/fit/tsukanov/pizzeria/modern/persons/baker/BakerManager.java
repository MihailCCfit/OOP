package nsu.fit.tsukanov.pizzeria.modern.persons.baker;

import lombok.extern.slf4j.Slf4j;
import nsu.fit.tsukanov.pizzeria.modern.common.buffer.OrderBoard;
import nsu.fit.tsukanov.pizzeria.modern.common.buffer.Storage;
import nsu.fit.tsukanov.pizzeria.modern.common.dto.DeliveryOrder;
import nsu.fit.tsukanov.pizzeria.modern.common.dto.Order;
import nsu.fit.tsukanov.pizzeria.modern.common.dto.Pizza;

@Slf4j
public class BakerManager implements Runnable {

    private final Baker baker;

    private final Storage storage;

    private final OrderBoard orderBoard;

    private volatile boolean isWorking = true;

    private Order order = null;


    public BakerManager(Baker baker, Storage storage, OrderBoard orderBoard) {
        this.baker = baker;
        this.storage = storage;
        this.orderBoard = orderBoard;
    }

    /**
     * Get order from orderBoard
     */
    public void consume() throws InterruptedException {
        order = orderBoard.take();
    }

    /**
     * Cooks pizza (waiting) and puts into storage
     *
     * @throws InterruptedException sometimes
     */
    public void produce() throws InterruptedException {
        Pizza pizza = new Pizza(order.pizzaName());
        baker.cook(pizza);
        log.info("{} Cooked pizza {} to {}", baker, order.pizzaName(), order.clientName());
        DeliveryOrder deliveryOrder = new DeliveryOrder(pizza, order.orderCallback(), order.clientName());
        order = null;
        storage.put(deliveryOrder);
        log.info("{} Put order {} to storage", baker, deliveryOrder);
    }

    /**
     * Starts getting orders and cooking pizzas. After that, put storage and start cycle again.
     */

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

    /**
     * Set working type for stop running
     * @param working true - working, false - stop working
     */
    public void setWorking(boolean working) {
        isWorking = working;
    }

    /**
     * Just for test
     * @return order
     */
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
