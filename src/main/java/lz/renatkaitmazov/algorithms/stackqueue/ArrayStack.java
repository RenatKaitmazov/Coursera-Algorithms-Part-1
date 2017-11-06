package lz.renatkaitmazov.algorithms.stackqueue;

import java.util.Iterator;

/**
 * A concrete implementation of the stack data type.
 * Uses a resizeable array to hold items.
 *
 * @author Renat Kaitmazov
 */

public class ArrayStack<T> implements Stack<T> {

    /*--------------------------------------------------------*/
    /* Static
    /*--------------------------------------------------------*/

    /**
     * A default size of the array.
     * Used in case the client does not know in advance how big the
     * stack is going to be.
     */

    private static final int DEFAULT_CAPACITY = 16;

    /*--------------------------------------------------------*/
    /* Fields
    /*--------------------------------------------------------*/

    /**
     * Keeps track of the number of elements in the stack.
     * It is also used a pointer to the last element in the array.
     */

    private int size;

    /**
     * A resizeable array of type <code>T</code>
     * that holds elements.
     */

    private T[] items;

    /*--------------------------------------------------------*/
    /* Constructors
    /*--------------------------------------------------------*/

    public ArrayStack() {
        this(DEFAULT_CAPACITY);
    }

    @SuppressWarnings("unchecked")
    public ArrayStack(int capacity) {
        if (capacity < 0) {
            throw new IllegalArgumentException("Capacity cannot be negative");
        }
        items = (T[]) new Object[capacity];
    }

    /*--------------------------------------------------------*/
    /* Object class methods
    /*--------------------------------------------------------*/

    @Override
    public final String toString() {
        if (isEmpty()) {
            return "[]";
        }
        final StringBuilder builder = new StringBuilder("[");
        for (int i = size - 1; i > -1; --i) {
            final T item = items[i];
            builder.append(item).append(", ");
        }
        final int end = builder.length();
        final int start = end - 2;
        return builder.replace(start, end, "]")
                .toString();
    }

    /*--------------------------------------------------------*/
    /* Stack implementation
    /*--------------------------------------------------------*/

    @Override
    public final int size() {
        return size;
    }

    @Override
    public final void push(T item) {
        final int capacity = items.length;
        if (size == capacity) {
            resize(capacity << 1);
        }
        items[size++] = item;
    }

    @Override
    public final T pop() {
        if (isEmpty()) {
            return null;
        }
        final T itemToPop = items[--size];
        items[size] = null; // Avoid loitering.
        final boolean shouldShrink = size <= (items.length >> 2);
        if (shouldShrink) {
            resize(items.length >> 1);
        }
        return itemToPop;
    }

    /*--------------------------------------------------------*/
    /* Iterable implementation
    /*--------------------------------------------------------*/

    private static final class ReversedIterator<T> implements Iterator<T> {

        private T[] items;
        private int iteratorSize;

        ReversedIterator(T[] items, int iteratorSize) {
            this.items = items;
            this.iteratorSize = iteratorSize;
        }

        @Override
        public final boolean hasNext() {
            return iteratorSize > 0;
        }

        @Override
        public final T next() {
            return this.items[--iteratorSize];
        }
    }

    @Override
    public final Iterator<T> iterator() {
        return new ReversedIterator<>(items, size);
    }

    /*--------------------------------------------------------*/
    /* Helper methods
    /*--------------------------------------------------------*/

    private void resize(int newCapacity) {
        @SuppressWarnings("unchecked")
        final T[] newItems = (T[]) new Object[newCapacity];
        System.arraycopy(items, 0, newItems, 0, size);
        items = newItems;
    }
}
