package nsu.fit.tsukanov.client;


import lombok.extern.slf4j.Slf4j;
import nsu.fit.tsukanov.order.Order;
import nsu.fit.tsukanov.order.OrderBoard;
import nsu.fit.tsukanov.pizza.Pizza;

import java.util.Random;

@Slf4j
public class Client implements Runnable {
    private final OrderBoard orderBoard;
    private final String name = "Client " + new Random().nextInt(100);

    public Client(OrderBoard orderBoard) {
        this.orderBoard = orderBoard;
    }

    /**
     * When an object implementing interface {@code Runnable} is used
     * to create a thread, starting the thread causes the object's
     * {@code run} method to be called in that separately executing
     * thread.
     * <p>
     * The general contract of the method {@code run} is that it may
     * take any action whatsoever.
     *
     * @see Thread#run()
     */
    @Override
    public void run() {
        Random random = new Random();
        while (true) {
            var order = getRandomOrder(random.nextInt());
            try {
                orderBoard.addOrder(order);
                log.info("{} put order {}", this, order);
                synchronized (this) {
                    this.wait();
                }
            } catch (InterruptedException e) {
                log.warn("{} ends working", this);
                return;
            }
            try {
                Thread.sleep(1000 * 10);
            } catch (InterruptedException e) {
                log.warn("{} ends working", this);
                return;
            }
        }
    }

    //TODO: Client class
    public Order getRandomOrder(int x) {
        int mod = 3;
        x = x % mod;
        if (x < 0) {
            x += mod;
        }
        x++;

        String name = switch (x) {
            case 1 -> "Pepperoni";
            case 2 -> "Margaritta";
            default -> "Barbecue";
        };
        Order pizzaOrder = new Order("EAT", new Pizza(name), this, (client) -> {
            client.notify();
            log.info("Call client {}", client);
        });
        return pizzaOrder;
    }

    @Override
    public String toString() {
        return "Client{" +
                "name='" + name + '\'' +
                '}';
    }
}
