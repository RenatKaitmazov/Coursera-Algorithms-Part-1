package lz.renatkaitmazov.algorithms.week2;

import java.util.Iterator;

/**
 * A concrete implementation of the queue data type.
 * Uses a singly linked list underneath.
 *
 * @author Renat Kaitmazov
 */

public final class LinkedQueue<T> implements Queue<T> {

    /*--------------------------------------------------------*/
    /* Fields
    /*--------------------------------------------------------*/

    /**
     * The underlying data structure that holds elements.
     * Uses as much memory as there are elements. (An array based implementation does not have that property)
     * Easier to implement using a linked list.
     * Acts a delegate.
     */

    private final List<T> queue = new LinkedList<>();

    /*--------------------------------------------------------*/
    /* Constructors
    /*--------------------------------------------------------*/

    public LinkedQueue() {
    }

    /*--------------------------------------------------------*/
    /* Queue implementation
    /*--------------------------------------------------------*/

    @Override
    public final String toString() {
        return queue.toString();
    }

    @Override
    public final void enqueue(T item) {
        queue.append(item);
    }

    @Override
    public final T dequeue() {
        return queue.removeFirst();
    }

    @Override
    public final int size() {
        return queue.size();
    }

    /*--------------------------------------------------------*/
    /* Iterable implementation
    /*--------------------------------------------------------*/

    @Override
    public final Iterator<T> iterator() {
        return queue.iterator();
    }
}
