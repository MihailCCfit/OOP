package nsu.fit.tsukanov.pizza;

import lombok.Data;
import nsu.fit.tsukanov.order.Order;

@Data
public class PizzaOrder { //TODO: Fix PizzaOrder
    private Pizza pizza;
    private Order order;
}
