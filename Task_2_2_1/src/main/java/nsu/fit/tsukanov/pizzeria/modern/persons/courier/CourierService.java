package nsu.fit.tsukanov.pizzeria.modern.persons.courier;

import lombok.extern.slf4j.Slf4j;
import nsu.fit.tsukanov.pizzeria.modern.common.buffer.Storage;
import nsu.fit.tsukanov.pizzeria.modern.common.configuration.SharedClassFactory;
import nsu.fit.tsukanov.pizzeria.modern.common.interfaces.PizzaService;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Slf4j
public class CourierService implements PizzaService {
    private final Map<Courier, Thread> courierThreadMap = new LinkedHashMap<>();
    private final Storage storage;
    private final CourierRepository courierRepository;

    public CourierService(SharedClassFactory sharedClassFactory) {
        storage = sharedClassFactory.getStorage();
        courierRepository = sharedClassFactory.getCourierRepository();
    }

    @Override
    public void startWorking() {
        initialize();
        log.info("Courier service start working, amount of bakers: {}", courierThreadMap.size());
        courierThreadMap.forEach(((courier, thread) -> {
            courier.setWorking(true);
            thread.start();
            thread.setName(courier.toString());
        }));

    }

    @Override
    public void stopWorking() {
        log.info("Courier service stop working, amount of couriers: {}", courierThreadMap.size());
        courierThreadMap.forEach((courier, thread) -> {
            courier.setWorking(false);
            thread.interrupt();
        });
    }

    private void initialize() {
        List<CourierEntity> couriers = courierRepository.findAll();
        if (couriers.isEmpty()) {
            couriers = initializationList();
            courierRepository.saveAll(couriers);
        }


        for (CourierEntity courierEntity : couriers) {
            Courier courier = new Courier(courierEntity, storage);
            courierThreadMap.put(courier, new Thread(courier));
        }

    }

    private List<CourierEntity> initializationList() {
        List<CourierEntity> courierEntities = new ArrayList<>();
        courierEntities.add(new CourierEntity(0L, "paul", 4, 4, 2));
        courierEntities.add(new CourierEntity(1L, "albert", 8, 1, 1));
        courierEntities.add(new CourierEntity(2L, "ban", 5, 2, 3));
        return courierEntities;
    }
}
