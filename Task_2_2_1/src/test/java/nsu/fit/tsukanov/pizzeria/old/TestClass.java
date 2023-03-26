package nsu.fit.tsukanov.pizzeria.old;

import nsu.fit.tsukanov.pizzeria.modern.common.buffer.BufferAbstract;
import nsu.fit.tsukanov.pizzeria.modern.common.buffer.OrderBoard;
import nsu.fit.tsukanov.pizzeria.modern.common.buffer.Storage;
import nsu.fit.tsukanov.pizzeria.modern.common.configuration.Configuration;
import nsu.fit.tsukanov.pizzeria.modern.common.interfaces.Buffer;
import nsu.fit.tsukanov.pizzeria.modern.controller.Pizzeria;
import nsu.fit.tsukanov.pizzeria.modern.persons.baker.Baker;
import nsu.fit.tsukanov.pizzeria.modern.persons.baker.BakerEntity;
import nsu.fit.tsukanov.pizzeria.modern.persons.baker.BakerRepository;
import nsu.fit.tsukanov.pizzeria.modern.persons.baker.BakerRepositoryJSON;
import nsu.fit.tsukanov.pizzeria.modern.persons.client.Client;
import nsu.fit.tsukanov.pizzeria.modern.persons.courier.Courier;
import nsu.fit.tsukanov.pizzeria.modern.persons.courier.CourierEntity;
import nsu.fit.tsukanov.pizzeria.modern.persons.courier.CourierRepository;
import nsu.fit.tsukanov.pizzeria.modern.persons.courier.CourierRepositoryJSON;
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
        BakerEntity bakerEntity = new BakerEntity(0L, "Paul", 1, 1);
        Baker baker = new Baker(bakerEntity, storage, orderBoard);
        Assertions.assertNull(baker.getOrder());
        try {
            baker.consume();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        Assertions.assertNotNull(baker.getOrder());
        Assertions.assertFalse(storage.isFull());
        Assertions.assertTrue(storage.isEmpty());
        Assertions.assertEquals(0, storage.size());
        try {
            baker.produce();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        Assertions.assertEquals(1, storage.size());
        Assertions.assertFalse(storage.isEmpty());
        Assertions.assertFalse(storage.isFull());
        CourierEntity courierEntity = new CourierEntity(0L, "Ben", 1, 1, 2);
        Assertions.assertTrue(courierEntity.getOrderList().isEmpty());
        Courier courier = new Courier(courierEntity, storage);
        try {
            courier.consume();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        Assertions.assertFalse(storage.isFull());
        Assertions.assertTrue(storage.isEmpty());
        Assertions.assertEquals(0, storage.size());
        Assertions.assertFalse(courierEntity.getOrderList().isEmpty());
        thread.start();
        try {
            courier.produce();
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
        Assertions.assertTrue(courierEntity.getOrderList().isEmpty());
    }

    @Test
    void stupidTest() {
        Pizzeria pizzeria = new Pizzeria();
        Thread pizThread = new Thread(() -> {
            pizzeria.start();
            try {
                Thread.sleep(1000 * 15);
            } catch (InterruptedException ignore) {
            }
            pizzeria.stop();
        });
        pizThread.start();
        try {
            pizThread.join();
        } catch (InterruptedException ignore) {

        }
    }

    @Test
    void testRepositories() {
        BakerRepository bakerRepository = new BakerRepositoryJSON();
        bakerRepository.delete(15L);
        BakerEntity baker = new BakerEntity(15L, "Og", 1, 1);
        Assertions.assertFalse(bakerRepository.findAll().contains(baker));
        bakerRepository.save(baker);
        Assertions.assertTrue(bakerRepository.findAll().contains(baker));
        BakerRepository bakerRepository1 = new BakerRepositoryJSON();
        Assertions.assertTrue(bakerRepository1.findAll().contains(baker));
        bakerRepository.delete(baker.id());
        Assertions.assertFalse(bakerRepository.findAll().contains(baker));

        CourierRepository courierRepository = new CourierRepositoryJSON();
        courierRepository.delete(15L);
        CourierEntity courier = new CourierEntity(15L, "Og", 1, 1, 2);
        Assertions.assertFalse(courierRepository.findAll().contains(courier));
        courierRepository.save(courier);
        Assertions.assertTrue(courierRepository.findAll().contains(courier));
        CourierRepository courierRepository1 = new CourierRepositoryJSON();
        Assertions.assertTrue(courierRepository1.findAll().contains(courier));
        courierRepository.delete(courier.getId());
        Assertions.assertFalse(courierRepository.findAll().contains(courier));
    }

    @Test
    void testBuffer() {
        Buffer<Integer> integerBuffer = new BufferAbstract<>(5);
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
