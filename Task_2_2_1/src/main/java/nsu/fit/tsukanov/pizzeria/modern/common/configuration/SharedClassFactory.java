package nsu.fit.tsukanov.pizzeria.modern.common.configuration;

import nsu.fit.tsukanov.pizzeria.modern.common.buffer.OrderBoard;
import nsu.fit.tsukanov.pizzeria.modern.common.buffer.Storage;
import nsu.fit.tsukanov.pizzeria.modern.common.dto.DeliveryOrder;
import nsu.fit.tsukanov.pizzeria.modern.persons.baker.BakerRepository;
import nsu.fit.tsukanov.pizzeria.modern.persons.baker.BakerRepositoryJSON;
import nsu.fit.tsukanov.pizzeria.modern.persons.courier.CourierRepository;
import nsu.fit.tsukanov.pizzeria.modern.persons.courier.CourierRepositoryJSON;

public class SharedClassFactory {
    private final OrderBoard orderBoard = new OrderBoard(Configuration.MAX_ORDER_BOARD_SIZE);
    private final Storage<DeliveryOrder> storage = new Storage<>(Configuration.MAX_STORAGE_SIZE);

    private final BakerRepository bakerRepository = new BakerRepositoryJSON();

    private final CourierRepository courierRepository = new CourierRepositoryJSON();

    /**
     * Idempotent function, that return the same object - bakerRepository.
     *
     * @return the same bakerRepository.
     */
    public BakerRepository getBakerRepository() {
        return bakerRepository;
    }

    /**
     * Idempotent function, that return the same object - courierRepository.
     *
     * @return the same courierRepository.
     */
    public CourierRepository getCourierRepository() {
        return courierRepository;
    }

    /**
     * Idempotent function, that return the same object - orderBoard.
     *
     * @return the same orderBoard.
     */
    public OrderBoard getOrderBoard() {
        return orderBoard;
    }

    /**
     * Idempotent function, that return the same object - storage.
     *
     * @return the same storage.
     */
    public Storage<DeliveryOrder> getStorage() {
        return storage;
    }
}
