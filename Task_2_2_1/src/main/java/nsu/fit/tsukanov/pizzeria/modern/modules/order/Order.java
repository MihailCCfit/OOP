package nsu.fit.tsukanov.pizzeria.modern.modules.order;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import nsu.fit.tsukanov.pizzeria.modern.modules.client.Client;
import nsu.fit.tsukanov.pizzeria.modern.common.pizza.Pizza;

import java.util.function.Consumer;

@AllArgsConstructor
@Slf4j
@Getter
public class Order implements Consumer<Client> {

    private String nameOrder;
    private Pizza pizza;

    private Client client;
    private Consumer<Client> call;

    public Pizza getPizza() {

        return pizza;
    }

    public void setPizza(Pizza pizza) {
        this.pizza = pizza;
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
        return "{" + nameOrder +
                ", " + pizza +
                ", " + client +
                '}';
    }
}
