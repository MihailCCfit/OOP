package nsu.fit.tsukanov.courier;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import nsu.fit.tsukanov.order.Order;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Random;


@Slf4j
@Getter
@Setter
public class Courier {
    private Long id;
    private String name;
    private int deliveryTime;
    private int errorTime;
    private int capacity;

    public Courier(Long id, String name, int deliveryTime, int errorTime, int capacity) {
        this.id = id;
        this.name = name;
        this.deliveryTime = deliveryTime;
        this.errorTime = errorTime;
        this.capacity = capacity;
    }

    @Getter(AccessLevel.NONE)
    @Setter(AccessLevel.NONE)
    private List<Order> orderList = new ArrayList<>();

    private void deliver(Order order) {
        Random random = new Random();
        log.info("{} Start delivering", this);
        synchronized (this) {
            try {
                Thread.sleep(deliveryTime * 1000L + random.nextInt(errorTime + 1) * 1000L + 1);
            } catch (InterruptedException ignore) {
            }
        }
        order.getCall().accept(order.getClient());
        log.info("Courier {} delivered order", this);
    }

    public void deliver() {
        for (Order order : orderList) {
            deliver(order);
        }
    }

    public void addOrder(Order order) {
        orderList.add(order);
    }

    public void addOrders(Collection<Order> orders) {
        orderList.addAll(orders);
    }


}

