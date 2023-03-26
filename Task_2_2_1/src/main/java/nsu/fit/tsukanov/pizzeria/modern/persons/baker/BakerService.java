package nsu.fit.tsukanov.pizzeria.modern.persons.baker;

import lombok.extern.slf4j.Slf4j;
import nsu.fit.tsukanov.pizzeria.modern.common.buffer.OrderBoard;
import nsu.fit.tsukanov.pizzeria.modern.common.buffer.Storage;
import nsu.fit.tsukanov.pizzeria.modern.common.configuration.SharedClassFactory;
import nsu.fit.tsukanov.pizzeria.modern.common.interfaces.PizzaService;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Slf4j
public class BakerService implements PizzaService {
    private final Map<Baker, Thread> bakerThreadMap = new LinkedHashMap<>();
    private final Storage storage;
    private final OrderBoard orderBoard;
    private final BakerRepository bakerRepository;

    private SharedClassFactory sharedClassFactory;

    public BakerService(SharedClassFactory sharedClassFactory) {
        storage = sharedClassFactory.getStorage();
        orderBoard = sharedClassFactory.getOrderBoard();
        bakerRepository = sharedClassFactory.getBakerRepository();
    }

    @Override
    public void startWorking() {
        initialize();
        log.info("Baker service start working, amount of bakers: {}", bakerThreadMap.size());
        bakerThreadMap.forEach(((baker, thread) -> {
            baker.setWorking(true);
            thread.start();
            thread.setName(baker.toString());
        }));

    }

    @Override
    public void stopWorking() {
        log.info("Baker service stop working, amount of bakers: {}", bakerThreadMap.size());
        bakerThreadMap.forEach((baker, thread) -> {
            baker.setWorking(false);
            thread.interrupt();
        });
    }

    private void initialize() {
        List<BakerEntity> bakerEntities = bakerRepository.findAll();
        if (bakerEntities.isEmpty()) {
            bakerEntities = initializationList();
            bakerRepository.saveAll(bakerEntities);
        }
        for (BakerEntity bakerEntity : bakerEntities) {
            Baker baker = new Baker(bakerEntity, storage, orderBoard);
            bakerThreadMap.put(baker, new Thread(baker, baker.toString()));
        }
    }

    private List<BakerEntity> initializationList() {
        List<BakerEntity> bakers = new ArrayList<>();
        bakers.add(new BakerEntity(0L, "paul", 4, 4));
        bakers.add(new BakerEntity(1L, "albert", 8, 1));
        //bakers.add(new BakerEntity(2L, "ban", 5, 2));
        //bakers.add(new BakerEntity(3L, "bert", 15, 0));
        //bakers.add(new BakerEntity(4L, "random", 2, 15));
        return bakers;
    }
}
