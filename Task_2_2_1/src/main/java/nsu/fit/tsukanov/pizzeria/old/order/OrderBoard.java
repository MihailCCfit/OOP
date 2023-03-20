package nsu.fit.tsukanov.pizzeria.old.order;

public interface OrderBoard {
    Order takeOrder();

    Order addOrder(Order order);

    boolean hasOrder();

}
