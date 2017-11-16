package lz.renatkaitmazov.algorithms.week4;

/**
 * An abstraction of the data type known as a priority queue
 * where items in the queue have special order such that no children is
 * greater or smaller (depending on the implementation) than their parent.
 * Each parent has exactly two child elements.
 *
 * @author Renat Kaitmazov
 */

public interface PriorityQueue<T extends Comparable<T>> {

    /**
     * Returns the amount of items in the queue.
     *
     * @return number of items.
     */
    int size();

    /**
     * Checks to see if the queue is empty or not.
     *
     * @return <code>true</code> if there are no items in the queue,
     * <code>false</code> otherwise.
     */
    default boolean isEmpty() {
        return size() == 0;
    }

    /**
     * Inserts the given item in the queue.
     *
     * @param item to be inserted.
     */
    void insert(T item);

    /**
     * Removes and returns the first item from the queue.
     *
     * @return an item of type <code>T</code>,
     * <code>null</code> if the queue is empty.
     */
    T remove();

    /**
     * Returns the first item from the queue but not removes it.
     *
     * @return the first item of type <code>T</code>,
     * <code>null</code> if the queue is empty.
     */
    T peek();
}
