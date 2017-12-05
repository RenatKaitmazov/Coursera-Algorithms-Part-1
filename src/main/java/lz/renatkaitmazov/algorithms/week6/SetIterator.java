package lz.renatkaitmazov.algorithms.week6;

import java.util.Iterator;

/**
 * @author Renat Kaitmazov
 */

abstract class SetIterator<T> implements Iterator<T> {

    private final T[] items;
    private final int size;
    private int currentIndex;

    @SuppressWarnings("unchecked")
    SetIterator(Set<T> set) {
        size = set.size();
        items = (T[]) new Object[size];
        fillItems(items, set, size);
    }

    abstract void fillItems(T[] items, Set<T> s, int size);

    @Override
    public final boolean hasNext() {
        return currentIndex < size;
    }

    @Override
    public final T next() {
        return items[currentIndex++];
    }
}
