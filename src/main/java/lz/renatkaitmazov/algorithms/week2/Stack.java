package lz.renatkaitmazov.algorithms.week2;

/**
 * An abstraction for a stack data type (Last In First Out).
 * Includes the very basic operations.
 *
 * @author Renat Kaitmazov
 */

public interface Stack<T> extends Iterable<T> {

    /**
     * Returns the amount of element in the stack.
     *
     * @return number of items.
     */

    int size();

    /**
     * Checks if the stack is empty or not.
     *
     * @return <code>true</code> if the stack has no elements
     * <code>false</code> otherwise.
     */

    default boolean isEmpty() {
        return size() == 0;
    }

    /**
     * Inserts an item at the top of the stack. (The first one to be removed)
     *
     * @param item to be inserted in the stack.
     */

    void push(T item);

    /**
     * Removes the topmost element in the stack if is not empty.
     *
     * @return an item of type <code>T</code>, <code>null</code> if the stack is empty.
     */

    T pop();
}
