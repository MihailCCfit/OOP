package nsu.fit.tsukanov.pizzeria.modern.controller;

import lombok.extern.slf4j.Slf4j;
import nsu.fit.tsukanov.pizzeria.modern.common.configuration.SharedClassFactory;
import nsu.fit.tsukanov.pizzeria.modern.persons.baker.BakerService;
import nsu.fit.tsukanov.pizzeria.modern.persons.client.ClientService;
import nsu.fit.tsukanov.pizzeria.modern.persons.courier.CourierService;

@Slf4j
public class Pizzeria {
    private final BakerService bakerService;
    private final CourierService courierService;
    private final ClientService clientService;

    private final SharedClassFactory factory = new SharedClassFactory();

    public Pizzeria() {
        log.info("Pizzeria created");
        bakerService = new BakerService(factory);
        clientService = new ClientService(factory);
        courierService = new CourierService(factory);
    }

    public void start() {
        log.info("Pizzeria start new beautiful day");
        bakerService.startWorking();
        clientService.startWorking();
        courierService.startWorking();
    }

    public void stop() {
        bakerService.stopWorking();
        clientService.stopWorking();
        courierService.stopWorking();
    }
}
