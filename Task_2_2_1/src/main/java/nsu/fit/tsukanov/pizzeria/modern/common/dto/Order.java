package nsu.fit.tsukanov.pizzeria.modern.common.dto;

import java.util.function.Consumer;


public record Order(String pizzaName, Consumer<Pizza> orderCallback, String clientName) {

    @Override
    public String toString() {
        return "Order{" +
                "pizzaName='" + pizzaName + '\'' +
                ", clientName='" + clientName + '\'' +
                '}';
    }
}
