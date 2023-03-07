package nsu.fit.tsukanov.baker;

import lombok.extern.slf4j.Slf4j;
import nsu.fit.tsukanov.baker.repository.BakerRepository;
import nsu.fit.tsukanov.order.OrderBoard;
import nsu.fit.tsukanov.storage.Storage;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
public class BakerService {
    private final BakerRepository bakerRepository;
    private final List<Thread> threads = new ArrayList<>();
    private final List<BakerRun> bakerRunList = new ArrayList<>();
    private final Storage storage;

    private final OrderBoard orderBoard;

    public BakerService(BakerRepository bakerRepository, Storage storage, OrderBoard orderBoard) {
        this.bakerRepository = bakerRepository;
        this.storage = storage;
        this.orderBoard = orderBoard;
        bakerReposInit();
    }

    private void bakerReposInit() {
        List<Baker> bakers = bakerRepository.findAll();
        if (bakers.isEmpty()) {
            bakerRepository.addAll(initializationList());
            log.info("First initialization");
        } else {
            log.info("Downloaded bakers, amount: {}", bakers.size());
        }
        log.info("Bakers was initialized: {}", bakers.size());
    }

    private List<Baker> initializationList() {
        List<Baker> bakers = new ArrayList<>();
        bakers.add(new Baker(0L, "f", 1L, 1L));
        return bakers;
    }

    public void startWorking() {
        for (Baker baker : bakerRepository.findAll()) {
            Thread thread = new Thread(new BakerRun(baker, storage, orderBoard, true));
            thread.start();
        }
        log.info("Baker Service started working, amount: {}", threads.size());
    }

    public void stopWorking() {
        for (Thread thread : threads) {
            thread.interrupt();
        }
        threads.clear();
    }


}
