package nsu.fit.tsukanov.pizzeria.modern.modules.client;

import lombok.extern.slf4j.Slf4j;
import nsu.fit.tsukanov.pizzeria.modern.common.interfaces.PizzaService;
import nsu.fit.tsukanov.pizzeria.modern.modules.order.OrderBoard;
import nsu.fit.tsukanov.pizzeria.modern.workingType.WorkingType;
import org.springframework.beans.factory.annotation.Value;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Slf4j
public class ClientService implements PizzaService {
    @Value(value = "${custom.clients.number}")
    private Integer clientsAmount;

    private final List<Thread> threads = new ArrayList<>();
    private final List<Client> clients;
    private final OrderBoard orderBoard;

    public ClientService(OrderBoard orderBoard) {
        this.orderBoard = orderBoard;
        clients = new ArrayList<>();

    }

    @Override
    public void initialize() {
        if (clientsAmount == null) {
            clientsAmount = 5;
        }
        Random random = new Random();
        for (int i = 0; i < clientsAmount; i++) {
            clients.add(new Client(orderBoard, 5 + random.nextInt(15),
                    random.nextInt(5) + 1));
        }
    }

    public void startWorking() {
        threads.clear();
        clients.forEach((cl) -> {
            Thread thread = new Thread(cl);
            threads.add(thread);
            thread.setDaemon(true);
            thread.start();
        });
        log.info("Client service starts working, clients number {}", threads.size());
    }

    @Override
    public void enableWorking() {

    }

    @Override
    public void stopWorking() {
        endWorking();

    }

    @Override
    public void finalWorking() {
        endWorking();
    }

    @Override
    public void alarmWorking() {
        endWorking();
    }

    public void endWorking() {

        log.info("Client service end working");
        threads.clear();
    }

    public void setWorking(WorkingType workingType) {
        log.info("Set working [{}] for all clients", workingType);
    }
}
