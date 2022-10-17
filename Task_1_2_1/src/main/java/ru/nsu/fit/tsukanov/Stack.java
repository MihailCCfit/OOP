package ru.nsu.fit.tsukanov;

import java.util.Arrays;
import java.util.Iterator;

/**
 * A stack is a linear data structure that follows the principle of Last In First Out (LIFO).
 * This means the last element inserted inside the stack is removed first.
 * You can think of the stack data structure as the pile of plates on top of another.
 * This stack is based on a dynamic array that resizes match the current number of objects.
 * After push operation, size may be increased. After pop operation, size may be decreased.
 *
 * @param <T> is any type.
 * @author Tsukanov Mikhail
 * @version 0.1
 * @see Stack#push(Object)
 * @see Stack#pop()
 */
public class Stack<T> implements Iterable<T> {
    private T[] datas;
    private static final int DEFAULT_SIZE = 5;
    private int pointer;

    /**
     * Without initial object array, it is initializing array for data.
     * Set pointer to zero.
     */
    @SuppressWarnings({"unchecked"})
    public Stack() {
        datas = (T[]) new Object[DEFAULT_SIZE];
        this.pointer = 0;
    }

    /**
     * Initializes stack, and copy initial arr into stack.
     *
     * @param arr the arr that will be copied into stack arr.
     */
    public Stack(T[] arr) {
        this();
        if (arr != null) {
            datas = arr.clone();
            this.pointer = arr.length;
        }
    }

    /**
     * Add object to stack. Stack array can be increased by this method.
     *
     * @param obj the object, that will be added to stack.
     */
    public void push(T obj) {
        if (pointer == datas.length) {
            setSize(datas.length * 2 + 1);
        }
        datas[pointer++] = obj;
    }

    /**
     * Return an object from the stack, and extract this from array.
     * This object is at the top of the array.
     *
     * @return an object from the stack. This object is at the top of the array.
     * @throws IllegalStateException if there isn't element in stack.
     */
    public T pop() {
        if (pointer == 0) {
            throw new IllegalStateException("stack is empty");
        }
        T res = datas[--pointer];
        datas[pointer] = null;
        if ((pointer + 1) * 2 < datas.length) {
            setSize(datas.length / 2 + 1);
        }
        return res;
    }

    /**
     * Return size of stack (number of elements).
     *
     * @return size of stack.
     */
    int size() {
        return pointer;
    }


    /**
     * Push elements from initial stack into current stack.
     *
     * @param stack the initial stack, elements from which will be added into current stack.
     */
    public void pushStack(Stack<T> stack) {
        if (stack == null) {
            return;
        }
        setSize(stack.pointer + this.pointer);
        System.arraycopy(stack.datas, 0, this.datas, this.pointer, stack.pointer);
        this.pointer += stack.pointer;
    }

    /**
     * Pop elements from this stack and pop them into new stack, that will be returned.
     *
     * @param len the number of objects, that will be pop from stack.
     * @return stack from popped objects.
     * @throws IllegalStateException from pop() method
     * @see Stack#pop()
     * @see Stack#push(Object)
     */
    public Stack<T> popStack(int len) {
        Stack<T> newStack;
        if (len > pointer) {
            throw new IllegalStateException();
        }
        newStack = new Stack<>(Arrays.copyOfRange(datas, pointer - len, pointer));
        Arrays.fill(datas, pointer - len, pointer, null);
        pointer -= len;
        return newStack;
    }

    private void setSize(int size) {
        datas = Arrays.copyOf(datas, size);
    }

    /**
     * Return the number of objects in the stack.
     *
     * @return the number of objects in the stack.
     */
    public int count() {
        return pointer;
    }

    /**
     * Return Copy of stack array.
     *
     * @return Copy of stack array.
     */
    public T[] getElements() {
        return datas;
    }

    /**
     * Check stack for empty.
     *
     * @return True if there isn't element in stack.
     */
    public boolean isEmpty() {
        return pointer == 0;
    }

    @Override
    public String toString() {
        return "Stack{"
                + "pointer=" + pointer
                + "datas=" + Arrays.toString(datas)
                + '}';
    }

    /**
     * Linear iterator.
     */
    @Override
    public Iterator<T> iterator() {
        return new IterStack();
    }

    private class IterStack implements Iterator<T> {
        private int current;

        private IterStack() {
            current = -1;
        }

        /**
         * Check for existing of next element.
         *
         * @return True if there is element at least.
         */
        @Override
        public boolean hasNext() {
            return current + 1 < pointer;
        }

        /**
         * return next object in stack. Firstly return element at 0 index.
         *
         * @return next object in stack.
         */
        @Override
        public T next() {
            return datas[++current];
        }

    }
}
