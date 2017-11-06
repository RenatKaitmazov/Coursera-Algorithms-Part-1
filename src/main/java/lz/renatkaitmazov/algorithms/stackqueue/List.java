package lz.renatkaitmazov.algorithms.stackqueue;

/**
 * @author Renat Kaitmazov
 */

public interface List<T> extends Iterable<T> {
    void insert(T item);

    void append(T item);

    int size();

    default boolean isEmpty() {
        return size() == 0;
    }

    default boolean contains(T item) {
        return indexOf(item) > -1;
    }

    int indexOf(T item);

    T get(int index);
}
