package nsu.fit.tsukanov.pizzeria.modern.modules.order;

import lombok.extern.slf4j.Slf4j;
import nsu.fit.tsukanov.pizzeria.modern.common.BufferAbstract;
import nsu.fit.tsukanov.pizzeria.modern.common.configuration.Configuration;

@Slf4j
public class OrderBoardDeque extends BufferAbstract<Order> implements OrderBoard {

    public OrderBoardDeque() {
        super(Configuration.MAX_ORDER_BOARD_SIZE);
    }
}
