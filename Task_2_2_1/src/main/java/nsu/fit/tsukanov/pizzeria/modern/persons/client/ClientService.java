package nsu.fit.tsukanov.pizzeria.modern.persons.client;

import lombok.extern.slf4j.Slf4j;
import nsu.fit.tsukanov.pizzeria.modern.common.buffer.OrderBoard;
import nsu.fit.tsukanov.pizzeria.modern.common.configuration.Configuration;
import nsu.fit.tsukanov.pizzeria.modern.common.configuration.SharedClassFactory;
import nsu.fit.tsukanov.pizzeria.modern.common.interfaces.PizzaService;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Random;

@Slf4j
public class ClientService implements PizzaService {
    private final Map<Client, Thread> clientThreadMap = new LinkedHashMap<>();
    private final OrderBoard orderBoard;

    private SharedClassFactory sharedClassFactory;

    public ClientService(SharedClassFactory sharedClassFactory) {
        orderBoard = sharedClassFactory.getOrderBoard();
    }

    @Override
    public void startWorking() {
        initialize();
        log.info("Client service start working, amount of bakers: {}", clientThreadMap.size());
        clientThreadMap.forEach(((client, thread) -> {
            client.setWorking(true);
            thread.start();
            thread.setName(client.toString());
        }));

    }

    @Override
    public void stopWorking() {
        log.info("Client service stop working, amount of clients: {}", clientThreadMap.size());
        clientThreadMap.forEach((client, thread) -> {
            client.setWorking(false);
            thread.interrupt();
        });
    }

    private void initialize() {
        int clientsAmount = Configuration.CLIENTS_AMOUNT;
        Random random = new Random();
        for (int i = 0; i < clientsAmount; i++) {
            Client client = new Client(orderBoard, 5 + random.nextInt(15),
                    random.nextInt(5) + 1);
            clientThreadMap.put(client, new Thread(client, client.toString()));
        }
    }

}
