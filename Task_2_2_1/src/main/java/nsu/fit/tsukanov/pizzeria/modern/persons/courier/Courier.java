package nsu.fit.tsukanov.pizzeria.modern.persons.courier;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import nsu.fit.tsukanov.pizzeria.modern.common.dto.DeliveryOrder;

import java.util.*;


@Getter
@Setter
@NoArgsConstructor
public class Courier {
    private Long id;
    private String name;
    private int deliveryTime;
    private int errorTime;
    private int capacity;
    @JsonIgnore
    private final List<DeliveryOrder> orderList = new ArrayList<>();

    public Courier(Long id, String name, int deliveryTime, int errorTime, int capacity) {
        this.id = id;
        this.name = name;
        this.deliveryTime = deliveryTime;
        this.errorTime = errorTime;
        this.capacity = capacity;
    }

    public void addOrder(DeliveryOrder order) {
        if (orderList.size() >= capacity) {
            return;
        }
        orderList.add(order);
    }

    public void addOrder(Collection<DeliveryOrder> orders) {
        int size = orderList.size();
        for (DeliveryOrder order : orders) {
            if (size >= capacity) {
                return;
            }
            orderList.add(order);
            size++;
        }
    }

    public void deliver() throws InterruptedException {
        Random random = new Random();
        Iterator<DeliveryOrder> iterator = orderList.iterator();
        while (iterator.hasNext()) {
            var order = iterator.next();
            iterator.remove();
            order.callOrderOwner();
        }
        orderList.clear();
        synchronized (this) {
            Thread.sleep((long) 1000 * deliveryTime + random.nextInt(1000 * errorTime));
        }
    }

    public int canTake() {
        return capacity - orderList.size();
    }

    @Override
    public String toString() {
        return "Courier{" +
                id +
                "-'" + name + '\'' +
                "orders:" + orderList +
                "}";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Courier that = (Courier) o;
        return id.equals(that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}

