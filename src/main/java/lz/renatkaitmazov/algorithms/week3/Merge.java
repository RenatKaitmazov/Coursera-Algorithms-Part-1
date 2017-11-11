package lz.renatkaitmazov.algorithms.week3;

import lz.renatkaitmazov.algorithms.week2.sort.Insertion;

import static lz.renatkaitmazov.algorithms.week2.sort.SortUtil.isLessThan;

/**
 * An implementation of the merge sort algorithm.
 * A very efficient sorting algorithms that takes O(N*logN) steps to sort an array
 * in the worst case scenario.
 * Stable.
 * Uses an auxiliary array of size N to sort the array.
 *
 * @author Renat Kaitmazov
 */

public final class Merge {

    /**
     * The amount of elements in a subarray that should be sorted using
     * the insertion sort.
     */

    private static final int THRESHOLD = 7;

    private Merge() {
    }

    public static <T extends Comparable<T>> void sort(final T[] array) {
        if (array == null) {
            return;
        }
        final int size = array.length;
        @SuppressWarnings("unchecked")
        final T[] auxiliary = (T[]) new Comparable[size];
        sort(array, auxiliary, 0, size - 1);
    }

    private static <T extends Comparable<T>> void sort(final T[] array,
                                                       final T[] auxiliary,
                                                       int startIndex,
                                                       int endIndex) {
        if ((endIndex - startIndex + 1) <= THRESHOLD) {
            // It is more efficient to sort small subarrays using the insertion sort.
            // Reduces the amount of recursive calls.
            Insertion.sort(array, startIndex, endIndex);
            return;
        }

        // A better way to calculate the midpoint to avoid integer overflowing.
        final int middleIndex = startIndex + ((endIndex - startIndex) >> 1);

        // Sort the left part.
        sort(array, auxiliary, startIndex, middleIndex);

        // Sort the right part.
        sort(array, auxiliary, middleIndex + 1, endIndex);

        // If the first item from the right part is not smaller
        // than the last item from the first part, then the two subarrays are sorted.
        if (!isLessThan(array[middleIndex + 1], array[middleIndex])) {
            return;
        }

        // Merge the results.
        merge(array, auxiliary, startIndex, middleIndex, endIndex);
    }

    static <T extends Comparable<T>> void merge(final T[] array,
                                                        final T[] auxiliary,
                                                        int startIndex,
                                                        int middleIndex,
                                                        int endIndex) {
        final int size = (endIndex - startIndex) + 1;
        // Copy everything into the auxiliary array in [startIndex, endIndex].
        System.arraycopy(array, startIndex, auxiliary, startIndex, size);
        for (int k = startIndex, i = startIndex, j = middleIndex + 1; k <= endIndex; ++k) {
            // Left part is exhausted
            if      (i > middleIndex) array[k] = auxiliary[j++];
            // Right part is exhausted
            else if (j > endIndex) array[k] = auxiliary[i++];
            // The item on the right > the item on the left. It gives us stability.
            else if (isLessThan(auxiliary[j], auxiliary[i])) array[k] = auxiliary[j++];
            // The item on the left <= the item on the right.
            else array[k] = auxiliary[i++];
        }
    }
}
