package nsu.fit.tsukanov.pizzeria.old;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.List;



public class TestClass {

    private OrderBoard orderBoard = new OrderBoardDeque();


    @Test
    void orderBoard() {
        Assertions.assertFalse(orderBoard.hasOrder());
        orderBoard.addOrder(new Order(null, null, null, null));
        Assertions.assertTrue(orderBoard.hasOrder());
        orderBoard.takeOrder();
        Assertions.assertFalse(orderBoard.hasOrder());
    }

    @Test
    void client() {


        BakerRepository bakerRepository = new BakerRepositoryJSON();
        CourierRepository courierRepository = new CourierRepositoryJSON();
        Storage storage = new StorageImplementation();
        OrderBoard orderBoard = new OrderBoardDeque();
        BakerService bakerService = new BakerService(bakerRepository, storage, orderBoard);
        Client client = new Client(orderBoard, 5, 1);
        CourierService courierService = new CourierService(courierRepository, storage);
        List<PizzaService> services = List.of(bakerService, courierService);
        initializing(services);
        Assertions.assertFalse(orderBoard.hasOrder());
        client.order();
        Assertions.assertTrue(orderBoard.hasOrder());
        Assertions.assertFalse(client.gotPizza());
        Assertions.assertTrue(storage.isEmpty());
        bakerService.startWorking();
        synchronized (orderBoard) {
            while (orderBoard.hasOrder()) {
                try {
                    orderBoard.wait(3000);
                } catch (InterruptedException ignored) {

                }
            }
        }
        Assertions.assertFalse(orderBoard.hasOrder());
        courierService.startWorking();
        client.waitOrder();
        Assertions.assertTrue(client.gotPizza());
        bakerService.stopWorking();
        courierService.stopWorking();
        bakerService.enableWorking();
        courierService.enableWorking();
        bakerService.alarmWorking();
        courierService.alarmWorking();
        //courierService.startWorking();
    }

}
