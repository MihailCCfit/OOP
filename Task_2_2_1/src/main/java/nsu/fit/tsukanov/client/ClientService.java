package nsu.fit.tsukanov.client;

import lombok.extern.slf4j.Slf4j;
import nsu.fit.tsukanov.order.OrderBoard;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
public class ClientService {
    @Value(value = "${custom.clients.number}")
    private Integer clientsAmount;

    private final List<Thread> threads = new ArrayList<>();
    private final List<Client> clients;
    private final OrderBoard orderBoard;

    public ClientService(OrderBoard orderBoard) {
        this.orderBoard = orderBoard;
        clients = new ArrayList<>();
        if (clientsAmount == null) {
            clientsAmount = 5;
        }
        for (int i = 0; i < clientsAmount; i++) {
            clients.add(new Client(orderBoard));
        }
    }

    public void startWorking(){
        threads.clear();
        clients.forEach((cl)-> {
            Thread thread = new Thread(cl);
            threads.add(thread);
            thread.start();
        });
        log.info("Client service starts working, clients number {}", threads.size());
    }

    public void endWorking(){
        for (Thread thread : threads) {
            thread.interrupt();
        }
        log.info("Client service end working");
        threads.clear();
    }
}
