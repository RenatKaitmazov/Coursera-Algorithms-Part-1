package lz.renatkaitmazov.algorithms.week2;

/**
 * An abstraction for a list data type.
 * Stores elements in a sequential order as the client inserted them.
 *
 * @author Renat Kaitmazov
 */

public interface List<T> extends Iterable<T> {
    /**
     * Inserts an item into the beginning of the list.
     *
     * @param item to be added.
     */

    void insert(T item);

    /**
     * Appends an item to the end of the list.
     *
     * @param item to be added.
     */

    void append(T item);

    /**
     * Removes the given item if it is in the list.
     *
     * @param item to be removed.
     * @return <code>null</code> if the item is not in the list or if the list is empty,
     * that given <code>item</code> otherwise.
     */

    T remove(T item);

    /**
     * Removes the last element from the list.
     *
     * @return <code>null</code> if the list is empty,
     * the last item otherwise.
     */

    T removeLast();

    /**
     * Removes the first item from the list.
     *
     * @return <code>null</code> if the list is empty,
     * the first element otherwise.
     */

    T removeFirst();

    /**
     * Removes an item at the specified index.
     *
     * @param index of the item to be removed.
     * @return item at the specified index, <code>null</code> if the list is empty.
     */

    T removeAt(int index);

    /**
     * Returns the amount of elements in the list.
     *
     * @return number of elements.
     */

    int size();

    /**
     * Checks if the list is empty or not.
     *
     * @return <code>true</code> if the list has no elements,
     * <code>false</code> otherwise
     */

    default boolean isEmpty() {
        return size() == 0;
    }

    /**
     * Checks if an item in the list.
     *
     * @param item to be checked.
     * @return <code>true</code> if the given item is in the list,
     * <code>false</code> otherwise.
     */

    default boolean contains(T item) {
        return indexOf(item) > -1;
    }

    /**
     * Returns an index of the element we are looking for.
     *
     * @param item whose index we want to find.
     * @return <code>-1</code> if the item is not in the list,
     * an integer value in the range of [0, N - 1] otherwise.
     */

    int indexOf(T item);

    /**
     * Returns an element at the given index.
     *
     * @param index of the element.
     * @return An element at the given index.
     */

    T get(int index);
}
