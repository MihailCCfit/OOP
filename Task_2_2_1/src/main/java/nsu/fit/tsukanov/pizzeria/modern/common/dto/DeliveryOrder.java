package nsu.fit.tsukanov.pizzeria.modern.common.dto;

import java.util.function.Consumer;

/**
 * Class that simulate delivered box with products
 */
public class DeliveryOrder {
    private final Pizza pizza;
    private final Consumer<Pizza> orderCallback;
    private final String clientName;

    public DeliveryOrder(Pizza pizza, Consumer<Pizza> orderCallback, String clientName) {
        this.pizza = pizza;
        this.orderCallback = orderCallback;
        this.clientName = clientName;
    }

    /**
     * Call client
     */
    public void callOrderOwner() {
        orderCallback.accept(pizza);
    }

    @Override
    public String toString() {
        return "DeliveryOrder{" +
                "pizza=" + pizza +
                ", clientName='" + clientName + '\'' +
                '}';
    }
}
