package nsu.fit.tsukanov.courier;

import lombok.extern.slf4j.Slf4j;
import nsu.fit.tsukanov.order.Order;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Random;


@Slf4j
record Courier(
        Long id,
        String name,
        int deliveryTime,
        int errorTime,
        int capacity,
        List<Order> orderList
) {
    public Courier(Long id, String name, int deliveryTime, int errorTime, int capacity) {
        this(id, name, deliveryTime, errorTime, capacity, new ArrayList<>());
    }

    private void deliver(Order order) {
        Random random = new Random();
        synchronized (this) {
            try {
                Thread.sleep(deliveryTime * 1000L + random.nextInt(errorTime + 1) * 1000L + 1);
            } catch (InterruptedException ignore) {
            }
        }
        order.getCall().accept(order.getClient());
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

