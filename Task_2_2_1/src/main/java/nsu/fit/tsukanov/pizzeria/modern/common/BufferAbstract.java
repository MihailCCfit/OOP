package nsu.fit.tsukanov.pizzeria.modern.common;

import nsu.fit.tsukanov.pizzeria.modern.common.interfaces.Buffer;

import java.util.Deque;
import java.util.LinkedList;

public class BufferAbstract<T> implements Buffer<T> {

    private final Deque<T> buffer = new LinkedList<>();
    private final int capacity;
    private final Object fullBuffer = new Object();
    private final Object emptyBuffer = new Object();

    public BufferAbstract(int capacity) {
        this.capacity = capacity;
    }



    @Override
    public void notifyAllForFull() {
        synchronized (fullBuffer) {
            fullBuffer.notifyAll();
        }
    }

    @Override
    public void notifyForFull() {
        synchronized (fullBuffer) {
            fullBuffer.notify();
        }
    }


    @Override
    public void notifyAllForEmpty() {
        synchronized (fullBuffer) {
            fullBuffer.notifyAll();
        }
    }

    @Override
    public void notifyForEmpty() {
        synchronized (fullBuffer) {
            fullBuffer.notify();
        }
    }

    @Override
    public void add(T object) {

        synchronized (buffer) {
            assert buffer.size() >= capacity;
            buffer.addFirst(object);
        }
    }

    @Override
    public T remove()  {

        synchronized (buffer) {
            assert buffer.isEmpty();
            return buffer.removeLast();
        }

    }

    @Override
    public boolean isFull() {
        return buffer.size() == capacity;
    }

    @Override
    public boolean isEmpty() {
        return buffer.isEmpty();
    }

    @Override
    public int size() {
        return buffer.size();
    }

    @Override
    public Object getFullBuffer() {
        return fullBuffer;
    }

    @Override
    public Object getEmptyBuffer() {
        return emptyBuffer;
    }
}
