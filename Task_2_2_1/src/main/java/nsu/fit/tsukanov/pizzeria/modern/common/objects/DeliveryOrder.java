package nsu.fit.tsukanov.pizzeria.modern.common.objects;

import java.util.function.Consumer;


public class DeliveryOrder {
    private final Pizza pizza;
    private final Consumer<Pizza> callOrderOwner;
    private final String clientName;

    public DeliveryOrder(Pizza pizza, Consumer<Pizza> callOrderOwner, String clientName) {
        this.pizza = pizza;
        this.callOrderOwner = callOrderOwner;
        this.clientName = clientName;
    }

    public void callOrderOwner() {
        callOrderOwner.accept(pizza);
    }

    @Override
    public String toString() {
        return "DeliveryOrder{" +
                "pizza=" + pizza +
                ", clientName='" + clientName + '\'' +
                '}';
    }
}
