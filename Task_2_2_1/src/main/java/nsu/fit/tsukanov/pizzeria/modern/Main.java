package nsu.fit.tsukanov.pizzeria.modern;

import nsu.fit.tsukanov.pizzeria.modern.common.interfaces.PizzaService;
import nsu.fit.tsukanov.pizzeria.modern.modules.baker.BakerService;
import nsu.fit.tsukanov.pizzeria.modern.modules.baker.repository.BakerRepository;
import nsu.fit.tsukanov.pizzeria.modern.modules.baker.repository.BakerRepositoryJSON;
import nsu.fit.tsukanov.pizzeria.modern.modules.client.ClientService;
import nsu.fit.tsukanov.pizzeria.modern.modules.courier.CourierRepository;
import nsu.fit.tsukanov.pizzeria.modern.modules.courier.CourierRepositoryJSON;
import nsu.fit.tsukanov.pizzeria.modern.modules.courier.CourierService;
import nsu.fit.tsukanov.pizzeria.modern.modules.order.OrderBoard;
import nsu.fit.tsukanov.pizzeria.modern.modules.order.OrderBoardDeque;
import nsu.fit.tsukanov.pizzeria.modern.modules.storage.Storage;
import nsu.fit.tsukanov.pizzeria.modern.modules.storage.StorageImplementation;

import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        BakerRepository bakerRepository = new BakerRepositoryJSON();
        CourierRepository courierRepository = new CourierRepositoryJSON();
        Storage storage = new StorageImplementation();
        OrderBoard orderBoard = new OrderBoardDeque();
        BakerService bakerService = new BakerService(bakerRepository, storage, orderBoard);
        ClientService clientService = new ClientService(orderBoard);

        CourierService courierService = new CourierService(courierRepository, storage);
        List<PizzaService> services = List.of(bakerService, clientService, courierService);

        initializing(services);
        starting(services);
        Scanner scanner = new Scanner(System.in);
        while (true) {
            String line = scanner.nextLine();
            switch (line) {
                case "start" -> starting(services);
                case "stop" -> stop(services);
                case "end" -> {
                    System.out.println("Ends");
                    return;
                }
            }
        }
    }

    public static void initializing(List<PizzaService> services) {
        services.forEach(PizzaService::initialize);
    }

    public static void starting(List<PizzaService> services) {
        services.forEach(PizzaService::startWorking);
    }

    public static void stop(List<PizzaService> services) {
        services.forEach(PizzaService::stopWorking);
    }

}
