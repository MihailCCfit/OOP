package nsu.fit.tsukanov.pizzeria.modern.persons.baker;

import lombok.extern.slf4j.Slf4j;
import nsu.fit.tsukanov.pizzeria.modern.common.buffer.OrderBoard;
import nsu.fit.tsukanov.pizzeria.modern.common.buffer.Storage;
import nsu.fit.tsukanov.pizzeria.modern.common.configuration.SharedClassFactory;
import nsu.fit.tsukanov.pizzeria.modern.common.interfaces.PizzaService;

import java.lang.annotation.Documented;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Slf4j
public class BakerService implements PizzaService {
    private final Map<BakerManager, Thread> bakerThreadMap = new LinkedHashMap<>();
    private final Storage storage;
    private final OrderBoard orderBoard;
    private final BakerRepository bakerRepository;

    private SharedClassFactory sharedClassFactory;

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
        if (bakerEntities.isEmpty()) {
            bakerEntities = initializationList();
            bakerRepository.saveAll(bakerEntities);
        }
        for (Baker baker : bakerEntities) {
            BakerManager bakerManager = new BakerManager(baker, storage, orderBoard);
            bakerThreadMap.put(bakerManager, new Thread(bakerManager, bakerManager.toString()));
        }
    }

    private List<Baker> initializationList() {
        List<Baker> bakers = new ArrayList<>();
        bakers.add(new Baker(0L, "paul", 4, 4));
        bakers.add(new Baker(1L, "albert", 8, 1));
        //bakers.add(new BakerEntity(2L, "ban", 5, 2));
        //bakers.add(new BakerEntity(3L, "bert", 15, 0));
        //bakers.add(new BakerEntity(4L, "random", 2, 15));
        return bakers;
    }
}
