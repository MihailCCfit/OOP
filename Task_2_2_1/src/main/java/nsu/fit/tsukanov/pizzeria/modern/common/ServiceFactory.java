package nsu.fit.tsukanov.pizzeria.modern.common;

import nsu.fit.tsukanov.pizzeria.modern.common.buffer.OrderBoard;
import nsu.fit.tsukanov.pizzeria.modern.common.buffer.Storage;
import nsu.fit.tsukanov.pizzeria.modern.common.configuration.Configuration;

public class ServiceFactory {
    private static OrderBoard orderBoard = new OrderBoard(Configuration.MAX_ORDER_BOARD_SIZE);
    private static Storage storage = new Storage(Configuration.MAX_STORAGE_SIZE);

    public static OrderBoard getOrderBoard() {
        return orderBoard;
    }

    public static Storage getStorage() {
        return storage;
    }
}
