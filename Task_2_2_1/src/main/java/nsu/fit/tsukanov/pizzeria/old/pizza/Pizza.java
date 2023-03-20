package nsu.fit.tsukanov.pizzeria.old.pizza;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class Pizza {
    private String name;

    @Override
    public String toString() {
        return name;
    }
}
