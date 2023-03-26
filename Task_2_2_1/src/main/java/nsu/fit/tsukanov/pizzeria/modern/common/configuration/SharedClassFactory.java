package nsu.fit.tsukanov.pizzeria.modern.common.configuration;

import nsu.fit.tsukanov.pizzeria.modern.common.buffer.OrderBoard;
import nsu.fit.tsukanov.pizzeria.modern.common.buffer.Storage;
import nsu.fit.tsukanov.pizzeria.modern.persons.baker.BakerRepository;
import nsu.fit.tsukanov.pizzeria.modern.persons.baker.BakerRepositoryJSON;
import nsu.fit.tsukanov.pizzeria.modern.persons.courier.CourierRepository;
import nsu.fit.tsukanov.pizzeria.modern.persons.courier.CourierRepositoryJSON;

public class SharedClassFactory {
    private final OrderBoard orderBoard = new OrderBoard(Configuration.MAX_ORDER_BOARD_SIZE);
    private final Storage storage = new Storage(Configuration.MAX_STORAGE_SIZE);

    private final BakerRepository bakerRepository = new BakerRepositoryJSON();

    private final CourierRepository courierRepository = new CourierRepositoryJSON();

    public BakerRepository getBakerRepository() {
        return bakerRepository;
    }

    public CourierRepository getCourierRepository() {
        return courierRepository;
    }

    public OrderBoard getOrderBoard() {
        return orderBoard;
    }

    public Storage getStorage() {
        return storage;
    }
}
