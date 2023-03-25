package nsu.fit.tsukanov.pizzeria.modern.common.interfaces;

import java.util.Collection;

/**
 * With synchronization
 *
 * @param <T> the objects that contains in buffer
 */
public interface Buffer<T> {
    /**
     * Notify all objects that are waiting to put something int buffer.
     */
    void notifyAllCanPut();

    /**
     * Notify object that's waiting to put something into buffer.
     */
    void notifyCanPut();

    /**
     * Notify all objects that are waiting to take something from buffer.
     */
    void notifyAllCanTake();

    /**
     * Notify object that's waiting to take something from the buffer.
     */
    void notifyCanTake();

    /**
     * Put something to the buffer. This method uses synchronized, so it's parallel save. Waits if buffer is full.
     *
     * @param object the object that will be stored into the buffer.
     */
    void put(T object) throws InterruptedException;

    /**
     * Take some object from the buffer.
     * This method uses synchronized, so it's parallel save. Waits if buffer is empty.
     *
     * @return took object from buffer.
     */

    T take() throws InterruptedException;

    /**
     * Removes all available elements from this queue and adds them
     * to the given collection.
     *
     * @param collection the collection to transfer elements into
     * @return the number of elements transferred
     */

    int drainTo(Collection<? super T> collection) throws InterruptedException;

    /**
     * Removes at most the given number of available elements from this queue and adds them to the given collection.
     *
     * @param collectionForSaving – the collection to transfer elements into
     * @param maxElements         – the maximum number of elements to transfer
     * @return the number of elements transferred
     */
    int drainTo(Collection<? super T> collectionForSaving, int maxElements) throws InterruptedException;

    /**
     * Check buffer for full.
     *
     * @return true if buffer is full
     */
    boolean isFull();


    /**
     * Check buffer for empty.
     *
     * @return true if buffer is empty
     */
    boolean isEmpty();

    /**
     * Return the current size.
     *
     * @return the currents size (amount of elements) in the buffer
     */
    int size();

    /**
     * Return max size of the buffer.
     *
     * @return max size of the buffer.
     */
    int capacity();


}
