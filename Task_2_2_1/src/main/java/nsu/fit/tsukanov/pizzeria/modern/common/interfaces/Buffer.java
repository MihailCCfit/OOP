package nsu.fit.tsukanov.pizzeria.modern.common.interfaces;

public interface Buffer<T> {
    void notifyAllForFull();

    void notifyForFull();

    void notifyAllForEmpty();

    void notifyForEmpty();

    void add(T object);
    T remove();

    boolean isFull();

    boolean isEmpty();

    int size();

    Object getFullBuffer();

    Object getEmptyBuffer();
}
