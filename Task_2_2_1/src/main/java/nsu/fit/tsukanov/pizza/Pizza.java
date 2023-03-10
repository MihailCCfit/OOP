package nsu.fit.tsukanov.pizza;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class Pizza {
    private String name;

    @Override
    public String toString() {
        return name;
    }
}
