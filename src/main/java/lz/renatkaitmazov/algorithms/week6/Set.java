package lz.renatkaitmazov.algorithms.week6;

/**
 * An abstraction of the set data structure.
 * It stores unique values only and does not guarantee that the order
 * in which items are added is preserved.
 * A class that is going to be stored in a set must override
 * the {@link #hashCode()} and the {@link #equals(Object)} methods
 * because the data structure relies on them. Otherwise
 * there won't be guarantees that the set stores unique values.
 * Null values are not allowed to be added.
 *
 * @author Renat Kaitmazov
 */

public interface Set<T> extends Iterable<T> {

    /**
     * Returns the amount of elements in the set.
     *
     * @return number of elements.
     */
    int size();

    /**
     * Checks if the set is empty or not.
     *
     * @return <code>true</code> if the set has no elements,
     * <code>false</code> otherwise
     */
    default boolean isEmpty() {
        return size() == 0;
    }

    /**
     * Checks if an item in the set.
     *
     * @param item to be checked.
     * @return <code>true</code> if the given item is in the set,
     * <code>false</code> otherwise.
     */
    default boolean contains(T item) {
        return get(item) != null;
    }

    /**
     * Returns the given item from the set if the item is in the set.
     * Does not delete the item.
     *
     * @param item
     * @return <code>item</code> if it is the set,
     * <code>null</code> if the set is empty or if the item is absent.
     */
    T get(T item);

    /**
     * Adds an item into the set.
     *
     * @param item to be added.
     */
    void add(T item);

    /**
     * Removes the given item if it is in the set.
     *
     * @param item to be removed.
     * @return <code>null</code> if the item is not in the set or if the set is empty,
     * that given <code>item</code> otherwise.
     */
    T remove(T item);
}
