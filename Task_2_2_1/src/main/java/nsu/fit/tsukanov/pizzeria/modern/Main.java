package nsu.fit.tsukanov.pizzeria.modern;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import nsu.fit.tsukanov.pizzeria.modern.common.buffer.BufferAbstract;
import nsu.fit.tsukanov.pizzeria.modern.common.interfaces.Buffer;
import nsu.fit.tsukanov.pizzeria.modern.common.interfaces.PizzaService;
import nsu.fit.tsukanov.pizzeria.modern.persons.courier.CourierEntity;

import java.util.List;

public class Main {
    public static void main(String[] args) {
        CourierEntity courier = new CourierEntity();
        courier.setId(1);
        courier.getOrderList().add(5);
        System.out.println("Before: " + courier);
        System.out.println("After:");
        String json;
        try {
            json = new ObjectMapper().writeValueAsString(courier);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        System.out.println(json);
        CourierEntity newCourier;
        try {
            newCourier = (CourierEntity) new ObjectMapper().readValue(json, CourierEntity.class);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        System.out.println(newCourier);

        Buffer<Integer> buffer = new BufferAbstract(5);
        try {
            buffer.put(1);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        try {
            System.out.println(buffer.take());
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

    }


    public static void starting(List<PizzaService> services) {
        services.forEach(PizzaService::startWorking);
    }

    public static void stop(List<PizzaService> services) {
        services.forEach(PizzaService::stopWorking);
    }

}
