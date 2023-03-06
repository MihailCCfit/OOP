package nsu.fit.tsukanov.order;

public interface OrderBoard {
    Order takeOrder();

    Order addOrder(Order order);

    boolean hasOrder();
}
