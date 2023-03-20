package nsu.fit.tsukanov.pizzeria.modern.common.pizza;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class Pizza {
    private String name;

    @Override
    public String toString() {
        return name;
    }
}
