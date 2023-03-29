package nsu.fit.tsukanov.pizzeria.modern.common.dto;


public class Pizza {
    private String name;
    private boolean cooked = false;

    public boolean isCooked() {
        return cooked;
    }

    public void setCooked(boolean cooked) {
        this.cooked = cooked;
    }

    public Pizza(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return name;
    }
}
