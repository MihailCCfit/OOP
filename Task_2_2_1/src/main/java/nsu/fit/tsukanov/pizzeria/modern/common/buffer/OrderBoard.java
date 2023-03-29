package nsu.fit.tsukanov.pizzeria.modern.common.buffer;

import nsu.fit.tsukanov.pizzeria.modern.common.configuration.Configuration;
import nsu.fit.tsukanov.pizzeria.modern.common.dto.Order;

public class OrderBoard extends Buffer<Order> {
    public OrderBoard(int capacity) {
        super(capacity);
    }

    public OrderBoard() {
        super(Configuration.MAX_ORDER_BOARD_SIZE);
    }
}
