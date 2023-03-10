package nsu.fit.tsukanov.courier;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import nsu.fit.tsukanov.order.Order;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Random;

import static com.fasterxml.jackson.annotation.JsonProperty.Access.WRITE_ONLY;

@RequiredArgsConstructor
@Slf4j
@Getter
public class Courier {
    private final Long id;
    private final String name;
    private final int deliveryTime;
    private final int errorTime;
    private final int capacity;
    @JsonProperty(access = WRITE_ONLY)
    private List<Order> orderList = new ArrayList<>();
    private final Random random = new Random();

    private void deliver(Order order) {
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

    @Override
    public String toString() {
        return "{" +
                id +
                ", " + name
                + '}';
    }
}

