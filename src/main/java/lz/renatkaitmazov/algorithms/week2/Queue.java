package lz.renatkaitmazov.algorithms.week2;

/**
 * An abstraction for a queue data type (First In First Out).
 * Includes the very basic operations.
 *
 * @author Renat Kaitmazov
 */

public interface Queue<T> extends Iterable<T> {
    void enqueue(T item);

    T dequeue();

    int size();

    default boolean isEmpty() {
        return size() == 0;
    }
}
