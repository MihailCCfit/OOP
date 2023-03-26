package nsu.fit.tsukanov.pizzeria.modern.common.objects;

import java.util.function.Consumer;


public record Order(String pizzaName, Consumer<Pizza> callOrderOwner, String clientName) {

    @Override
    public String toString() {
        return "Order{" +
                "pizzaName='" + pizzaName + '\'' +
                ", clientName='" + clientName + '\'' +
                '}';
    }
}
