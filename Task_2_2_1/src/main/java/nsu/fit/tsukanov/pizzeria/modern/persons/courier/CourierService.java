package nsu.fit.tsukanov.pizzeria.modern.persons.courier;

import lombok.extern.slf4j.Slf4j;
import nsu.fit.tsukanov.pizzeria.modern.common.buffer.Storage;
import nsu.fit.tsukanov.pizzeria.modern.common.configuration.SharedClassFactory;
import nsu.fit.tsukanov.pizzeria.modern.common.dto.DeliveryOrder;
import nsu.fit.tsukanov.pizzeria.modern.common.interfaces.PizzaService;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Slf4j
public class CourierService implements PizzaService {
    private final Map<CourierManager, Thread> courierThreadMap = new LinkedHashMap<>();
    private final Storage<DeliveryOrder> storage;
    private final CourierRepository courierRepository;

    public CourierService(SharedClassFactory sharedClassFactory) {
        storage = sharedClassFactory.getStorage();
        courierRepository = sharedClassFactory.getCourierRepository();
    }

    /**
     * Start working. maybe download objects.
     */
    @Override
    public void startWorking() {
        initialize();
        log.info("Courier service start working, amount of bakers: {}", courierThreadMap.size());
        courierThreadMap.forEach(((courierManager, thread) -> {
            courierManager.setWorking(true);
            thread.start();
            thread.setName(courierManager.toString());
        }));

    }

    /**
     * Stop working. It may be not instantly.
     */
    @Override
    public void stopWorking() {
        log.info("Courier service stop working, amount of couriers: {}", courierThreadMap.size());
        courierThreadMap.forEach((courierManager, thread) -> {
            courierManager.setWorking(false);
            thread.interrupt();
        });
    }


    private void initialize() {
        List<Courier> couriers = courierRepository.findAll();
        for (Courier courier : couriers) {
            CourierManager courierManager = new CourierManager(courier, storage);
            courierThreadMap.put(courierManager, new Thread(courierManager));
        }

    }

}
