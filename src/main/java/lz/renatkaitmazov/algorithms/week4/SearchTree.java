package lz.renatkaitmazov.algorithms.week4;

/**
 * An abstraction of the tree data structure.
 * <p>
 * Ideally, it should be packed into its own package along with its concrete implementation.
 * The problem is week 4 covers binary search tree, but self-balancing trees are covered in
 * week 5. This is the main reason of me putting the interface into this package.
 * </p>
 *
 * @author Renat Kaitmazov
 */

public interface SearchTree<T extends Comparable<T>> {

    /**
     * Returns number of items in the tree.
     *
     * @return number of items.
     */
    int size();

    /**
     * Checks to see if the tree is empty.
     *
     * @return <code>true</code> if there are no items in tree,
     * <code>false</code> otherwise.
     */
    boolean isEmpty();

    /**
     * Returns the given item if it is in the tree.
     *
     * @param item to search for.
     * @return <code>item</code> if it is in the tree,
     * <code>null</code> if the tree is empty or the item is absent.
     */
    T get(T item);

    /**
     * Returns the smallest item in the tree.
     *
     * @return the smallest item,
     * <code>null</code> if the tree is empty.
     */
    T min();

    /**
     * Returns the largest item in the tree.
     *
     * @return the largest item,
     * <code>null</code> if the tree is empty.
     */
    T max();

    /**
     * Returns the largest item less than or equal to the given item.
     *
     * @param item an item.
     * @return the largest item <code><= item</code>,
     * <code>null</code> if the tree is empty.
     */
    T floor(T item);

    /**
     * Returns the smallest item greater than or equal to the given item.
     *
     * @param item an item.
     * @return the largest item <code>>= item</code>,
     * <code>null</code> if the tree is empty.
     */
    T ceil(T item);

    /**
     * Returns number of items less than or equal to the given item.
     *
     * @param item whose rank should be calculated.
     * @return number of items less than or equal <code>item</code>. The number is in [0...size].
     */
    int rank(T item);

    /**
     * Returns item at the given index.
     *
     * @param index of an item.
     * @return item at the index,
     * <code>null</code> if the tree is empty.
     */
    T select(int index);

    /**
     * Returns the size of the a subtree in the given range of items.
     *
     * @param left  the start of the range inclusive.
     * @param right the end of the range inclusive.
     * @return how many item are in the given range.
     */
    int size(T left, T right);

    /**
     * Checks to see if the given item is in the tree.
     *
     * @param item to search for.
     * @return <code>true</code> if the given item is in the tree,
     * <code>false</code> if the tree is empty or the item is absent.
     */
    default boolean contains(T item) {
        return get(item) != null;
    }

    /**
     * Inserts the given item into the tree.
     *
     * @param item to insert.
     */
    void insert(T item);

    /**
     * Deletes the smallest item from the tree.
     *
     * @return the smallest item,
     * <code>null</code> if the tree is empty.
     */
    T deleteMin();

    /**
     * Deletes the largest item from the tree.
     *
     * @return the largest item,
     * <code>null</code> if the tree is empty.
     */
    T deleteMax();

    /**
     * @param item to delete.
     * @return the deleted item,
     * <code>null</code> if the tree is empty or the given item is absent.
     */
    T delete(T item);

    /**
     * Returns an iterable collection of items in the sorted order.
     *
     * @return collection of items.
     */
    Iterable<T> items();

    /**
     * Returns an iterable collection of items within the range
     * in the sorted order.
     *
     * @param left  the start of the range.
     * @param right the end of the range.
     * @return collections of items.
     */
    Iterable<T> items(T left, T right);
}
