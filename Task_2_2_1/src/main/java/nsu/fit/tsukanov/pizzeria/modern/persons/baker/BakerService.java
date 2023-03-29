package nsu.fit.tsukanov.pizzeria.modern.persons.baker;

import lombok.extern.slf4j.Slf4j;
import nsu.fit.tsukanov.pizzeria.modern.common.buffer.OrderBoard;
import nsu.fit.tsukanov.pizzeria.modern.common.buffer.Storage;
import nsu.fit.tsukanov.pizzeria.modern.common.configuration.SharedClassFactory;
import nsu.fit.tsukanov.pizzeria.modern.common.dto.DeliveryOrder;
import nsu.fit.tsukanov.pizzeria.modern.common.interfaces.PizzaService;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Slf4j
public class BakerService implements PizzaService {
    private final Map<BakerManager, Thread> bakerThreadMap = new LinkedHashMap<>();
    private final Storage<DeliveryOrder> storage;
    private final OrderBoard orderBoard;
    private final BakerRepository bakerRepository;

    public BakerService(SharedClassFactory sharedClassFactory) {
        storage = sharedClassFactory.getStorage();
        orderBoard = sharedClassFactory.getOrderBoard();
        bakerRepository = sharedClassFactory.getBakerRepository();
    }

    /**
     * Start working. maybe download objects.
     */
    @Override
    public void startWorking() {
        initialize();
        log.info("Baker service start working, amount of bakers: {}", bakerThreadMap.size());
        bakerThreadMap.forEach(((bakerManager, thread) -> {
            bakerManager.setWorking(true);
            thread.start();
            thread.setName(bakerManager.toString());
        }));

    }

    /**
     * Stop working. It may be not instantly.
     */
    @Override
    public void stopWorking() {
        log.info("Baker service stop working, amount of bakers: {}", bakerThreadMap.size());
        bakerThreadMap.forEach((bakerManager, thread) -> {
            bakerManager.setWorking(false);
            thread.interrupt();
        });
    }

    private void initialize() {
        List<Baker> bakerEntities = bakerRepository.findAll();
        for (Baker baker : bakerEntities) {
            BakerManager bakerManager = new BakerManager(baker, storage, orderBoard);
            bakerThreadMap.put(bakerManager, new Thread(bakerManager, bakerManager.toString()));
        }
    }


}
