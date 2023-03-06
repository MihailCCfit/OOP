package nsu.fit.tsukanov.order;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import nsu.fit.tsukanov.client.Client;
import nsu.fit.tsukanov.pizza.PizzaOrder;

import java.util.function.Consumer;

@AllArgsConstructor
@Slf4j
public class Order implements Consumer<Client> {

    private String nameOrder;
    private PizzaOrder pizza;

    private Client client;
    private Consumer<Client> call;

    public PizzaOrder getPizzaOrder() {
        return pizza;
    }

    /**
     * Performs this operation on the given argument.
     *
     * @param client the input argument
     */
    @Override
    public void accept(Client client) {
        call.accept(client);
    }

    @Override
    public String toString() {
        return "Order{" +
                "nameOrder='" + nameOrder + '\'' +
                ", pizza=" + pizza +
                ", client=" + client +
                '}';
    }
}
