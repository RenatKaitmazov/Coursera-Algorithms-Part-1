package lz.renatkaitmazov.algorithms.week4;

import static lz.renatkaitmazov.algorithms.week2.sort.SortUtil.swap;

/**
 * An implementation of the heap sort algorithm.
 * Takes O(N*logN) steps to sort an array of size N.
 * Builds a max heap and then exchanges the last item with the maximum item.
 * The process repeats until the array is sorted.
 * Not stable.
 *
 * @author Renat Kaitmazov
 */

public final class HeapSort {

    /*--------------------------------------------------------*/
    /* Constructors
    /*--------------------------------------------------------*/

    private HeapSort() {
    }

    /*--------------------------------------------------------*/
    /* API
    /*--------------------------------------------------------*/

    public static <T extends Comparable<T>> void sort(final T[] array) {
        if (array == null) {
            return;
        }
        final int size = array.length;
        for (int parentIndex = (size >> 1) - 1; parentIndex > -1; --parentIndex) {
            // First of all, we need to build a maximum priority queue.
            // We start from the last parent and go up to the first parent.
            goDown(array, parentIndex, size);
        }

        // At this time the first item in the array is the largest one.
        // This invariant should hold until the array is sorted.
        for (int i = size - 1; i > 0; --i) {
            // Exchange the largest item in the array with the last item.
            swap(array, 0, i);
            // It is most likely that the order of the queue we built is violated
            // so we need to make sure that we preserve the invariant that the first item is the largest one
            // in [0..i).
            goDown(array, 0, i);
        }
    }

    private static <T extends Comparable<T>> void goDown(T[] array, int parentIndex, int size) {
        for (int childIndex = (parentIndex << 1) + 1; childIndex < size; childIndex = (parentIndex << 1) + 1) {
            // If the right child is greater than the left one, move the pointer by one to reflect it.
            if (childIndex + 1 < size && isGreater(array[childIndex + 1], array[childIndex])) ++childIndex;
            // If the child is not greater than the parent, stop.
            if (!isGreater(array[childIndex], array[parentIndex])) return;
            // Otherwise exchange them.
            swap(array, parentIndex, childIndex);
            // Go to examine the next parent.
            parentIndex = childIndex;
        }
    }

    private static <T extends Comparable<T>> boolean isGreater(T lhs, T rhs) {
        return lhs.compareTo(rhs) > 0;
    }
}
