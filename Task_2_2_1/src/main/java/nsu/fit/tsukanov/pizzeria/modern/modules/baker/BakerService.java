package nsu.fit.tsukanov.pizzeria.modern.modules.baker;

import lombok.extern.slf4j.Slf4j;
import nsu.fit.tsukanov.pizzeria.modern.common.interfaces.PizzaService;
import nsu.fit.tsukanov.pizzeria.modern.modules.baker.repository.BakerRepository;
import nsu.fit.tsukanov.pizzeria.modern.modules.order.OrderBoard;
import nsu.fit.tsukanov.pizzeria.modern.modules.storage.Storage;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
public class BakerService implements PizzaService {
    private final BakerRepository bakerRepository;
    private final List<Thread> threads = new ArrayList<>();
    private final Map<Baker, BakerRun> bakerRunMap = new HashMap<>();
    private final Storage storage;

    private final OrderBoard orderBoard;

    public BakerService(BakerRepository bakerRepository, Storage storage, OrderBoard orderBoard) {
        this.bakerRepository = bakerRepository;
        this.storage = storage;
        this.orderBoard = orderBoard;
    }

    private List<Baker> initializationList() {
        List<Baker> bakers = new ArrayList<>();
        bakers.add(new Baker(0L, "paul", 4, 4));
        bakers.add(new Baker(1L, "albert", 8, 1));
        bakers.add(new Baker(2L, "ban", 5, 2));
        bakers.add(new Baker(3L, "bert", 15, 0));
        bakers.add(new Baker(4L, "random", 2, 15));
        return bakers;
    }

    @Override
    public void initialize() {
        List<Baker> bakers = bakerRepository.findAll();
        if (bakers.isEmpty()) {
            bakerRepository.addAll(initializationList());
            log.info("First initialization");
        } else {
            log.info("Downloaded bakers, amount: {}", bakers.size());
        }
        log.info("Bakers was initialized: {}", bakers.size());
    }

    public void startWorking() {
        for (Baker baker : bakerRepository.findAll()) {
            var bakerRun = new BakerRun(baker, storage, orderBoard);
            bakerRunMap.put(baker, bakerRun);
            Thread thread = new Thread(bakerRun);
            thread.start();
            threads.add(thread);
        }
        log.info("Baker Service started working, amount: {}", threads.size());
    }

    @Override
    public void stopWorking() {
        for (BakerRun bakerRun : bakerRunMap.values()) {
            bakerRun.stop();
        }
        threads.forEach(Thread::interrupt);
        bakerRunMap.clear();
        threads.clear();
    }


}
