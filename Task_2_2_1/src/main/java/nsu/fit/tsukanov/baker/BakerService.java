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
    private final Storage storage;

    private final OrderBoard orderBoard;

    public BakerService(BakerRepository bakerRepository, Storage storage, OrderBoard orderBoard) {
        this.bakerRepository = bakerRepository;
        this.storage = storage;
        this.orderBoard = orderBoard;
    }

    public void startWorking() {
        for (Baker baker : bakerRepository.findAll()) {
            Thread thread = new Thread(new BakerRun(baker, storage, orderBoard));
            thread.start();
        }
    }

    public void stopWorking() {
        for (Thread thread : threads) {
            thread.interrupt();
        }
        threads.clear();
    }


}
