package nsu.fit.tsukanov.pizzeria.modern.common.interfaces;

public interface PizzaService {
    void initialize();

    void startWorking();

    void enableWorking();

    void stopWorking();

    void finalWorking();

    void alarmWorking();
}
