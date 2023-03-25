package nsu.fit.tsukanov.pizzeria.modern.common.objects;

import nsu.fit.tsukanov.pizzeria.modern.common.objects.Pizza;

import java.util.function.Consumer;


public record Order(String pizzaName, Consumer<Pizza> callOrderOwner) {
}
