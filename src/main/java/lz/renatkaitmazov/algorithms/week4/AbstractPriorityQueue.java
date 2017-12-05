package lz.renatkaitmazov.algorithms.week4;

import java.util.Comparator;

import static lz.renatkaitmazov.algorithms.week2.sort.SortUtil.swap;

/**
 * @author Renat Kaitmazov
 */

public abstract class AbstractPriorityQueue<T extends Comparable<T>> implements PriorityQueue<T> {

    /*--------------------------------------------------------*/
    /* Constants
    /*--------------------------------------------------------*/

    private static final int DEFAULT_CAPACITY = 16;

    /*--------------------------------------------------------*/
    /* Fields
    /*--------------------------------------------------------*/

    protected final Comparator<T> comparator;
    protected T[] items;
    protected int size;

    /*--------------------------------------------------------*/
    /* Constructors
    /*--------------------------------------------------------*/

    public AbstractPriorityQueue() {
        this(DEFAULT_CAPACITY);
    }

    public AbstractPriorityQueue(int capacity) {
        this(capacity, Comparator.naturalOrder());
    }

    @SuppressWarnings("unchecked")
    public AbstractPriorityQueue(int capacity, Comparator<T> comparator) {
        if (capacity < 1) {
            throw new IllegalArgumentException("Capacity must be positive");
        }
        this.comparator = comparator;
        items = (T[]) new Comparable[capacity];
    }

    /*--------------------------------------------------------*/
    /* Object class methods
    /*--------------------------------------------------------*/

    @Override
    public String toString() {
        if (isEmpty()) return "[]";
        final StringBuilder builder = new StringBuilder("[");
        for (int i = 0; i < size; ++i) {
            final T item = items[i];
            builder.append(item).append(", ");
        }
        final int end = builder.length();
        final int start = end - 2;
        return builder.replace(start, end, "]")
                .toString();
    }

    /*--------------------------------------------------------*/
    /* API
    /*--------------------------------------------------------*/

    @Override
    public final int size() {
        return size;
    }

    @Override
    public final void insert(T item) {
        final int capacity = items.length;
        if (size == capacity) {
            // The array is full.
            // Make it twice as big.
            resize(capacity << 1);
        }
        items[size++] = item;
        // Rearrange the queue to keep its properties.
        goUp();
    }

    private void goUp() {
        for (int childIndex = size - 1; childIndex > 0; ) {
            // Calculate the parent's index.
            final int parentIndex = (childIndex - 1) >> 1;
            // If parent is not greater than the child, stop.
            if (comparator.compare(items[parentIndex], items[childIndex]) <= 0) return;
            // Otherwise exchange them.
            swap(items, parentIndex, childIndex);
            childIndex = parentIndex;
        }
    }

    @Override
    public final T remove() {
        if (isEmpty()) {
            return null;
        }
        // Item to remove.
        final T temp = items[0];
        // Swap it with the last item.
        swap(items, 0, --size);
        // Avoid loitering
        items[size] = null;
        final int capacity = items.length;
        if ((capacity >> 2) >= size) {
            // The number of items is at least four times smaller than the size of the array.
            // Halve the size of the array
            resize(capacity >> 1);
        }
        // Rearrange the array to keep the properties of the priority queue.
        goDown();
        return temp;
    }

    private void resize(int newSize) {
        @SuppressWarnings("unchecked")
        final T[] newItems = (T[]) new Comparable[newSize];
        System.arraycopy(items, 0, newItems, 0, size);
        items = newItems;
    }

    private void goDown() {
        for (int childIndex = 1, parentIndex = 0; childIndex < size; childIndex = (childIndex << 1) + 1) {
            // Assume that the left child is not greater than the right one.
            // If it is not the case, change the index to be the index of the right child.
            if (childIndex + 1 < size && comparator.compare(items[childIndex + 1], items[childIndex]) < 0) ++childIndex;
            // If the parent is not greater than its maximum child, stop.
            if (comparator.compare(items[parentIndex], items[childIndex]) <= 0) return;
            // Otherwise exchange the parent with its maximum child,
            swap(items, parentIndex, childIndex);
            parentIndex = childIndex;
        }
    }

    @Override
    public final T peek() {
        if (isEmpty()) {
            return null;
        }
        return items[0];
    }

    /*--------------------------------------------------------*/
    /* For testing only!
    /*--------------------------------------------------------*/

    final int capacity() {
        return items.length;
    }
}
