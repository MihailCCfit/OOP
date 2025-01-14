package nsu.fit.tsukanov.pizzeria.old;

import nsu.fit.tsukanov.pizzeria.modern.common.buffer.OrderBoard;
import nsu.fit.tsukanov.pizzeria.modern.common.buffer.Storage;
import nsu.fit.tsukanov.pizzeria.modern.common.configuration.Configuration;
import nsu.fit.tsukanov.pizzeria.modern.common.configuration.SharedClassFactory;
import nsu.fit.tsukanov.pizzeria.modern.common.interfaces.StorageInterface;
import nsu.fit.tsukanov.pizzeria.modern.persons.baker.*;
import nsu.fit.tsukanov.pizzeria.modern.persons.client.Client;
import nsu.fit.tsukanov.pizzeria.modern.persons.courier.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;


public class TestClass {


    @Test
    void testProductionChain() {
        OrderBoard orderBoard = new OrderBoard(2);
        Storage storage = new Storage(2);


        Assertions.assertTrue(orderBoard.isEmpty());
        Assertions.assertFalse(orderBoard.isFull());
        Assertions.assertEquals(0, orderBoard.size());
        Assertions.assertEquals(Configuration.MAX_ORDER_BOARD_SIZE, orderBoard.capacity());
        Client client = new Client(orderBoard, 1, 1);
        Thread thread = new Thread(() -> {
            try {
                client.consume();
            } catch (InterruptedException ignore) {

            }
        });
        try {
            client.produce();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        Assertions.assertFalse(orderBoard.isEmpty());
        Assertions.assertFalse(orderBoard.isFull());
        Assertions.assertEquals(1, orderBoard.size());
        Assertions.assertFalse(client.havePizza());
        Baker baker = new Baker(0L, "Paul", 1, 1);
        BakerManager bakerManager = new BakerManager(baker, storage, orderBoard);
        Assertions.assertNull(bakerManager.getOrder());
        try {
            bakerManager.consume();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        Assertions.assertNotNull(bakerManager.getOrder());
        Assertions.assertFalse(storage.isFull());
        Assertions.assertTrue(storage.isEmpty());
        Assertions.assertEquals(0, storage.size());
        try {
            bakerManager.produce();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        Assertions.assertEquals(1, storage.size());
        Assertions.assertFalse(storage.isEmpty());
        Assertions.assertFalse(storage.isFull());
        Courier courier = new Courier(0L, "Ben", 1, 1, 2);
        Assertions.assertTrue(courier.getOrderList().isEmpty());
        CourierManager courierManager = new CourierManager(courier, storage);
        try {
            courierManager.consume();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        Assertions.assertFalse(storage.isFull());
        Assertions.assertTrue(storage.isEmpty());
        Assertions.assertEquals(0, storage.size());
        Assertions.assertFalse(courier.getOrderList().isEmpty());
        thread.start();
        try {
            courierManager.produce();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        while (!client.havePizza()) {
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
        Assertions.assertTrue(client.havePizza());
        Assertions.assertTrue(courier.getOrderList().isEmpty());
    }

    @Test
    void stupidTest() {
        SharedClassFactory factory = new SharedClassFactory();
        BakerService bakerService = new BakerService(factory);
        CourierService courierService = new CourierService(factory);
        Client client = new Client(factory.getOrderBoard(), 1, 1);
        Assertions.assertFalse(client.havePizza());
        bakerService.startWorking();
        courierService.startWorking();

        Thread clientThread = new Thread(() -> {
            try {
                client.produce();
                client.consume();
            } catch (InterruptedException e) {

            }
        }
        );
        clientThread.start();
        try {
            clientThread.join(40 * 1000);
        } catch (InterruptedException e) {
        }
        Assertions.assertTrue(client.havePizza());


    }

    @Test
    void testRepositories() {
        BakerRepository bakerRepository = new JsonBakerRepository();
        bakerRepository.delete(15L);
        Baker baker = new Baker(15L, "Og", 1, 1);
        Assertions.assertFalse(bakerRepository.findAll().contains(baker));
        bakerRepository.save(baker);
        Assertions.assertTrue(bakerRepository.findAll().contains(baker));
        BakerRepository bakerRepository1 = new JsonBakerRepository();
        Assertions.assertTrue(bakerRepository1.findAll().contains(baker));
        bakerRepository.delete(baker.id());
        Assertions.assertFalse(bakerRepository.findAll().contains(baker));

        CourierRepository courierRepository = new JsonCourierRepository();
        courierRepository.delete(15L);
        Courier courier = new Courier(15L, "Og", 1, 1, 2);
        Assertions.assertFalse(courierRepository.findAll().contains(courier));
        courierRepository.save(courier);
        Assertions.assertTrue(courierRepository.findAll().contains(courier));
        CourierRepository courierRepository1 = new JsonCourierRepository();
        Assertions.assertTrue(courierRepository1.findAll().contains(courier));
        courierRepository.delete(courier.getId());
        Assertions.assertFalse(courierRepository.findAll().contains(courier));
    }

    @Test
    void testBuffer() {
        StorageInterface<Integer> integerBuffer = new Storage<>(5);
        try {
            integerBuffer.put(1);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        try {
            integerBuffer.put(2);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        List<Integer> integers = new ArrayList<>();
        try {
            integerBuffer.drainTo(integers);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        Assertions.assertTrue(integers.contains(1) && integers.contains(2));
        Thread threadWaiting = new Thread(() -> {
            try {
                integerBuffer.drainTo(integers);
            } catch (InterruptedException ignored) {

            }
        });

        threadWaiting.start();
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        try {
            integerBuffer.put(3);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        integerBuffer.notifyCanTake();
        while (!integerBuffer.isEmpty()) {
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
        Assertions.assertTrue(integers.contains(3));

    }


}
