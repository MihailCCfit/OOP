package nsu.fit.tsukanov.pizzeria.modern.modules.client;


import lombok.extern.slf4j.Slf4j;
import nsu.fit.tsukanov.pizzeria.modern.common.pizza.Pizza;
import nsu.fit.tsukanov.pizzeria.modern.modules.order.Order;
import nsu.fit.tsukanov.pizzeria.modern.modules.order.OrderBoard;

import java.util.Random;

@Slf4j
public class Client implements Runnable {
    private final OrderBoard orderBoard;
    private final String name = "Client " + new Random().nextInt(255);
    private final Random random = new Random();
    private final int waitingTime;
    private final int biasTime;
    private boolean orderReturned = false;
    private volatile boolean runFlag;

    public Client(OrderBoard orderBoard, int waitingTime, int biasTime) {
        this.orderBoard = orderBoard;
        this.waitingTime = waitingTime;
        this.biasTime = biasTime;
        if (biasTime < 0 || waitingTime < 0) {
            throw new IllegalArgumentException("negative time");
        }
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
        log.info("Client {} start working", this);
        while (!Thread.interrupted()) {

            if (!orderAndWait()) break;
        }
        log.info("Client {} end working", this);
    }

    /**
     * @return true when working was interrupted
     */
    public boolean orderAndWait() {
        try {
            order();
        } catch (InterruptedException e) {
            return false;
        }
        return waitOrder();
    }

    public void order() throws InterruptedException {
        var order = getRandomOrder(random.nextInt());
        orderReturned = false;
        synchronized (this) {
            try {
                Thread.sleep(random.nextInt(biasTime) * 1000L + 1);
            } catch (InterruptedException ignored) {
            }
        }
        Object fullBuf;
        synchronized (fullBuf = orderBoard.getFullBuffer()) {
            while (orderBoard.isFull()) {
                fullBuf.wait();
            }
            orderBoard.add(order);
            log.info("{} put order {}", this, order);
            fullBuf.notify();
            orderBoard.notifyAllForEmpty();
        }
    }

    public boolean waitOrder() {
        while (!gotPizza()) {
            try {
                synchronized (this) {
                    this.wait(30 * 1000);
                }
            } catch (InterruptedException e) {
                log.warn("{} got pizza", this);
            }
        }
        try {
            Thread.sleep(random.nextInt(biasTime) * 1000L + waitingTime * 1000L + 1);
        } catch (InterruptedException e) {
            log.warn("{} ends working", this);
            return false;
        }
        return true;
    }

    public boolean gotPizza() {
        return orderReturned;
    }

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
        return new Order("EAT", new Pizza(name), this, (client) -> {
            synchronized (orderBoard.getFullBuffer()) {
                orderReturned = true;
                orderBoard.getFullBuffer().notifyAll();
                synchronized (this) {
                    this.notify();
                }

            }
            log.info("Call client {}", client);
        });
    }

    public void stop() {

    }

    @Override
    public String toString() {
        return name;
    }
}
