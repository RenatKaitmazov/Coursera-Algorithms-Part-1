package lz.renatkaitmazov.algorithms.week6;

import java.util.Iterator;

import static lz.renatkaitmazov.algorithms.week6.HashUtils.hash;

/**
 * A concrete implementation of the set data structure.
 * Puts items into adjacent cells to resolve the collision problem.
 * Does not remove items immediately, uses the lazy approach.
 *
 * @author Renat Kaitmazov
 */

public final class LinearProbingSet<T> implements Set<T> {

    /*--------------------------------------------------------*/
    /* Constants
    /*--------------------------------------------------------*/

    private static final int DEFAULT_CAPACITY = 16;
    private static final double LOAD_FACTOR = 0.5;

    /*--------------------------------------------------------*/
    /* Fields
    /*--------------------------------------------------------*/

    private Node<T>[] set;
    private int size;

    /*--------------------------------------------------------*/
    /* Constants
    /*--------------------------------------------------------*/

    @SuppressWarnings("unchecked")
    public LinearProbingSet(int capacity) {
        if (capacity < 1) {
            throw new IllegalArgumentException("Capacity must be positive");
        }
        set = (Node<T>[]) new Node[capacity];
    }

    public LinearProbingSet() {
        this(DEFAULT_CAPACITY);
    }

    /*--------------------------------------------------------*/
    /* Object class methods
    /*--------------------------------------------------------*/

    @Override
    public String toString() {
        if (isEmpty()) return "[]";
        final StringBuilder builder = new StringBuilder("[");
        for (final Node<T> node : set) {
            if (node != null && !node.isRemoved) {
                builder.append(node.item).append(", ");
            }
        }
        final int end = builder.length();
        final int start = end - 2;
        return builder.replace(start, end, "]").toString();
    }

    /*--------------------------------------------------------*/
    /* Set implementation
    /*--------------------------------------------------------*/

    @Override
    public int size() {
        return size;
    }

    @Override
    public T get(T item) {
        if (item != null && !isEmpty()) {
            final int capacity = set.length;
            int itemIndex = hash(item, capacity);
            Node<T> node = set[itemIndex];
            if (node != null && !node.isRemoved) {
                while (node != null) {
                    if (item.equals(node.item)) return item;
                    itemIndex = (itemIndex + 1) % capacity;
                    node = set[itemIndex];
                }
            }
        }
        return null;
    }

    @Override
    public void add(T item) {
        if (item != null) {
            final int capacity = set.length;
            if (size >= capacity * LOAD_FACTOR) resize(capacity << 1);
            final int newCapacity = set.length;
            int itemIndex = hash(item, newCapacity);
            Node<T> node = set[itemIndex];
            while (node != null && !node.isRemoved) {
                if (item.equals(node.item)) return;
                itemIndex = (itemIndex + 1) % newCapacity;
                node = set[itemIndex];
            }
            set[itemIndex] = new Node<>(item);
            ++size;
        }
    }

    @Override
    public T remove(T item) {
        if (item != null && !isEmpty()) {
            final int capacity = set.length;
            int itemIndex = hash(item, capacity);
            Node<T> node = set[itemIndex];
            while (node != null) {
                if (!node.isRemoved && item.equals(node.item)) {
                    node.isRemoved = true;
                    --size;
                    if ((capacity >> 3) > size) resize(capacity >> 1);
                    return item;
                }
                itemIndex = (itemIndex + 1) % capacity;
                node = set[itemIndex];
            }
        }
        return null;
    }

    /*--------------------------------------------------------*/
    /* Iterable implementation
    /*--------------------------------------------------------*/

    @Override
    public Iterator<T> iterator() {
        return new LinearProbingSetIterator<>(this);
    }

    /*--------------------------------------------------------*/
    /* Helper methods
    /*--------------------------------------------------------*/

    private void resize(int newSize) {
        @SuppressWarnings("unchecked")
        final Node<T>[] newSet = (Node<T>[]) new Node[newSize];
        for (final Node<T> node : set) {
            if (node != null && !node.isRemoved) {
                int newItemIndex = hash(node.item, newSize);
                while (newSet[newItemIndex] != null) {
                    newItemIndex = (newItemIndex + 1) % newSize;
                }
                newSet[newItemIndex] = node;
            }
        }
        set = newSet;
    }

    /*--------------------------------------------------------*/
    /* Testing
    /*--------------------------------------------------------*/

    int getCapacity() {
        return set.length;
    }

    /*--------------------------------------------------------*/
    /* Nested classes
    /*--------------------------------------------------------*/

    /**
     * A wrapper class that holds the actual data and has a boolean flag
     * to support lazy deletion.
     */
    private static final class Node<T> {
        private final T item;
        private boolean isRemoved;

        Node(T item) {
            this.item = item;
        }

        @Override
        public String toString() {
            return item.toString();
        }
    }

    private static final class LinearProbingSetIterator<T> extends SetIterator<T> {

        LinearProbingSetIterator(Set<T> set) {
            super(set);
        }

        @Override
        void fillItems(T[] items, Set<T> s, int size) {
            final LinearProbingSet<T> set = (LinearProbingSet<T>) s;
            // There are null or removed nodes at some cells.
            // We mustn't include them.
            int i = 0;
            for (final Node<T> node : set.set) {
                if (node != null && !node.isRemoved) {
                    items[i++] = node.item;
                }
            }
        }
    }
}
