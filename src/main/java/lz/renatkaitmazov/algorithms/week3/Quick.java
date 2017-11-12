package lz.renatkaitmazov.algorithms.week3;

import lz.renatkaitmazov.algorithms.week2.sort.Insertion;

import static lz.renatkaitmazov.algorithms.week2.sort.SortUtil.swap;
import static lz.renatkaitmazov.algorithms.week2.sort.SortUtil.isLessThan;

/**
 * An implementation of the quick sort algorithm.
 * The fastest sorting algorithm amongst those that compare values to rearrange them
 * given that items are randomly distributed. Takes O(N*logN) steps on average to sort
 * an array. In the worst case scenario (an array is already sorted) the performance
 * degrades to O(N^2).
 * Not stable.
 *
 * @author Renat Kaitmazov
 */

public final class Quick {

    /**
     * The amount of elements in a subarray that should be sorted using
     * the insertion sort.
     */

    private static final int THRESHOLD = 7;

    private Quick() {
    }

    public static <T extends Comparable<T>> void sort(final T[] array) {
        if (array == null) {
            return;
        }
        sort(array, 0, array.length - 1);
    }

    private static <T extends Comparable<T>> void sort(final T[] array, int startIndex, int endIndex) {
        if ((endIndex - startIndex + 1) <= THRESHOLD) {
            Insertion.sort(array, startIndex, endIndex);
            return;
        }

        // The index of pivot which is in the right place in the array.
        final int partition = partition(array, startIndex, endIndex);
        // Sort the left part.
        sort(array, startIndex, partition - 1);
        // Sort the right part.
        sort(array, partition + 1, endIndex);
    }

    static <T extends Comparable<T>> int partition(final T[] array, int start, int end) {
        final int middle = start + ((end - start) >> 1);
        median(array, start, middle, end);
        swap(array, start, middle);
        final T pivot = array[start];
        int i = start + 1;
        int j = end;

        while (true) {
            // Find an item that is greater than the pivot.
            while (i <= end && !isLessThan(pivot, array[i])) ++i;
            // Find an item that is smaller than the pivot.
            while (j > start && !isLessThan(array[j], pivot)) --j;
            // Has checked an items, no more work to do.
            if (i > j) break;
            // Put items into the right places
            swap(array, i++, j--);
        }
        // Put the pivot into the right place.
        // No items to the right of the pivot is smaller.
        // No items to the left of the pivot is greater.
        swap(array, start, j);
        return j;
    }


    private static <T extends Comparable<T>> void median(final T[] array, int start, int middle, int end) {
        if (!isLessThan(array[start], array[middle])) swap(array, start, middle);
        if (!isLessThan(array[start], array[end])) swap(array, start, end);
        if (!isLessThan(array[middle], array[end])) swap(array, middle, end);
    }

}
