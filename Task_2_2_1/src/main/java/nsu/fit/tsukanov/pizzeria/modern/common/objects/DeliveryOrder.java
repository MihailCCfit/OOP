package nsu.fit.tsukanov.pizzeria.modern.common.objects;

import java.util.function.Consumer;


public class DeliveryOrder {
    private final Pizza pizza;
    private final Consumer<Pizza> callOrderOwner;

    public DeliveryOrder(Pizza pizza, Consumer<Pizza> callOrderOwner) {
        this.pizza = pizza;
        this.callOrderOwner = callOrderOwner;
    }

    public void callOrderOwner() {
        callOrderOwner.accept(pizza);
    }
}
