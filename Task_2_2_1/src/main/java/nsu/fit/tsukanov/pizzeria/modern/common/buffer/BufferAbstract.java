package nsu.fit.tsukanov.pizzeria.modern.common.buffer;

import nsu.fit.tsukanov.pizzeria.modern.common.interfaces.Buffer;

import java.util.Collection;
import java.util.Deque;
import java.util.Iterator;
import java.util.LinkedList;

public class BufferAbstract<T> implements Buffer<T> {

    private final Deque<T> buffer = new LinkedList<>();
    private final int capacity;
    private final Object canPut = new Object();
    private final Object canTake = new Object();

    public BufferAbstract(int capacity) {
        this.capacity = capacity;
    }

    /**
     * Notify all objects that are waiting to put something int buffer.
     */
    @Override
    public void notifyAllCanPut() {
        synchronized (canPut) {
            canPut.notifyAll();
        }
    }

    /**
     * Notify object that's waiting to put something into buffer.
     */
    @Override
    public void notifyCanPut() {
        synchronized (canPut) {
            canPut.notify();
        }
    }

    /**
     * Notify all objects that are waiting to take something from buffer.
     */
    @Override
    public void notifyAllCanTake() {
        synchronized (canTake) {
            canTake.notifyAll();
        }
    }

    /**
     * Notify object that's waiting to take something from the buffer.
     */
    @Override
    public void notifyCanTake() {
        synchronized (canTake) {
            canTake.notify();
        }
    }

    /**
     * Put something to the buffer. This method uses synchronized, so it's parallel save. Waits if buffer is full.
     *
     * @param object the object that will be stored into the buffer.
     */
    @Override
    public void put(T object) throws InterruptedException {
        synchronized (canPut) {
            while (isFull()) {
                canPut.wait();
            }
            synchronized (buffer) {
                buffer.addFirst(object);
            }
        }
        notifyAllCanTake();
        //notifyCanTake();
    }

    /**
     * Take some object from the buffer.
     * This method uses synchronized, so it's parallel save. Waits if buffer is empty.
     *
     * @return took object from buffer.
     */
    @Override
    public T take() throws InterruptedException {
        T tookObject;
        synchronized (canTake) {
            while (isEmpty()) {
                canTake.wait();
            }
            synchronized (buffer) {
                tookObject = buffer.removeLast();
            }
        }
        notifyAllCanPut();
        return tookObject;
    }

    /**
     * Removes all available elements from this queue and adds them
     * to the given collection.
     *
     * @param collection the collection to transfer elements into
     * @return the number of elements transferred
     */
    @Override
    public int drainTo(Collection<? super T> collection) throws InterruptedException {
        int amount = 0;
        synchronized (canTake) {
            while (isEmpty()) {
                canTake.wait();
            }
            synchronized (buffer) {
                collection.addAll(buffer);
                amount = buffer.size();
                buffer.clear();
            }
        }
        return amount;
    }

    /**
     * Removes at most the given number of available elements from this queue and adds them to the given collection.
     *
     * @param collectionForSaving – the collection to transfer elements into
     * @param maxElements         – the maximum number of elements to transfer
     * @return the number of elements transferred
     */
    @Override
    public int drainTo(Collection<? super T> collectionForSaving, int maxElements) throws InterruptedException {
        int amount = 0;
        synchronized (canTake) {
            while (isEmpty()) {
                canTake.wait();
            }
            synchronized (buffer) {
                Iterator<T> iterator = buffer.iterator();
                int i = maxElements;
                while (iterator.hasNext() && i > 0) {
                    collectionForSaving.add(iterator.next());
                    iterator.remove();
                    i--;
                }
            }
        }
        return amount;
    }

    /**
     * Check buffer for full.
     *
     * @return true if buffer is full
     */
    @Override
    public boolean isFull() {
        synchronized (buffer) {
            return buffer.size() == capacity;
        }
    }

    /**
     * Check buffer for empty.
     *
     * @return true if buffer is empty
     */
    @Override
    public boolean isEmpty() {
        synchronized (buffer) {
            return buffer.isEmpty();
        }
    }

    /**
     * Return the current size.
     *
     * @return the currents size (amount of elements) in the buffer
     */
    @Override
    public int size() {
        synchronized (buffer) {
            return buffer.size();
        }
    }

    /**
     * Return max size of the buffer.
     *
     * @return max size of the buffer.
     */
    @Override
    public int capacity() {
        return capacity;
    }
}
