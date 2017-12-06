package lz.renatkaitmazov.algorithms.week6;

import lz.renatkaitmazov.algorithms.week2.LinkedList;
import lz.renatkaitmazov.algorithms.week2.List;

import java.util.Iterator;

import static lz.renatkaitmazov.algorithms.week6.HashUtils.hash;

/**
 * A concrete implementation of the set data structure.
 * Uses a linked list to resolve collision problems.
 * The lists are created in a lazy manner to be memory efficient.
 *
 * @author Renat Kaitmazov
 */

public final class SeparateChainingSet<T> implements Set<T> {

    /*--------------------------------------------------------*/
    /* Constants
    /*--------------------------------------------------------*/

    private static final int DEFAULT_CAPACITY = 16;
    private static final double LOAD_FACTOR = 0.75;

    /*--------------------------------------------------------*/
    /* Fields
    /*--------------------------------------------------------*/

    private List<T>[] set;
    private int size;

    /*--------------------------------------------------------*/
    /* Constructors
    /*--------------------------------------------------------*/

    @SuppressWarnings("unchecked")
    public SeparateChainingSet(int capacity) {
        if (capacity < 1) {
            throw new IllegalArgumentException("Capacity must be positive");
        }
        set = (LinkedList<T>[]) new LinkedList[capacity];
    }

    public SeparateChainingSet() {
        this(DEFAULT_CAPACITY);
    }

    /*--------------------------------------------------------*/
    /* Object class methods
    /*--------------------------------------------------------*/

    @Override
    public String toString() {
        if (isEmpty()) return "[]";
        final StringBuilder builder = new StringBuilder("[");
        for (final List<T> chain : set) {
            if (chain != null) {
                for (final T item : chain) {
                    builder.append(item).append(", ");
                }
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
            final int itemIndex = hash(item, set.length);
            final List<T> chain = set[itemIndex];
            if (chain != null) {
                for (final T currentItem : chain) {
                    if (item.equals(currentItem)) return item;
                }
            }
        }
        return null;
    }

    @Override
    public void add(T item) {
        // A null item cannot be added into the set.
        if (item != null) {
            final int capacity = set.length;
            // If the set if 75% full, then make it twice as big to reduce number of collisions.
            if (size >= capacity * LOAD_FACTOR) resize(capacity << 1);
            // Generate a hash value (effectively it is an array index at which the item will be stored).
            final int itemIndex = hash(item, set.length);
            if (set[itemIndex] == null) set[itemIndex] = new LinkedList<>();
            final List<T> chain = set[itemIndex];
            // A set does not allow for duplicates.
            if (!chain.contains(item)) {
                chain.append(item);
                ++size;
            }
        }
    }

    @Override
    public T remove(T item) {
        if (item != null && !isEmpty()) {
            final int capacity = set.length;
            final int itemIndex = hash(item, capacity);
            List<T> chain = set[itemIndex];
            if (chain != null) {
                final T itemToRemove = chain.remove(item);
                if (itemToRemove != null) {
                    --size;
                    if (capacity >> 2 >= size) resize(capacity >> 1);
                    return itemToRemove;
                }
            }
        }
        return null;
    }

    /*--------------------------------------------------------*/
    /* Iterable implementation
    /*--------------------------------------------------------*/

    @Override
    public Iterator<T> iterator() {
        return new SeparateChainingSetIterator<>(this);
    }

    /*--------------------------------------------------------*/
    /* Helper methods
    /*--------------------------------------------------------*/

    private void resize(int newSize) {
        @SuppressWarnings("unchecked")
        final List<T>[] newSet = (LinkedList<T>[]) new LinkedList[newSize];
        // A simple copy won't do. The hash value for each item in the set was calculated relative to the old size
        // of the set. Now when the capacity of the set has changed, we need to recalculate a hash value for
        // each item and put it into a proper position in the set.
        for (final List<T> chain : set) {
            if (chain != null) {
                for (final T item : chain) {
                    // Recalculate a hash value relative to the new size.
                    final int newItemIndex = hash(item, newSize);
                    if (newSet[newItemIndex] == null) newSet[newItemIndex] = new LinkedList<>();
                    newSet[newItemIndex].append(item);
                }
            }
        }
        set = newSet;
    }

    /*--------------------------------------------------------*/
    /* For testing only!
    /*--------------------------------------------------------*/

    int getCapacity() {
        return set.length;
    }

    /*--------------------------------------------------------*/
    /* Nested classes
    /*--------------------------------------------------------*/

    private static final class SeparateChainingSetIterator<T> extends SetIterator<T> {

        SeparateChainingSetIterator(Set<T> set) {
            super(set);
        }

        @Override
        void fillItems(T[] items, Set<T> s, int size) {
            final SeparateChainingSet<T> set = (SeparateChainingSet<T>) s;
            // There are null chains at some cells.
            // We mustn't include them.
            int i = 0;
            for (final List<T> chain : set.set) {
                if (chain != null) {
                    for (final T item : chain) {
                        items[i++] = item;
                    }
                }
            }
        }
    }
}
