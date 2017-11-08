package lz.renatkaitmazov.algorithms.week2.sort;

import static lz.renatkaitmazov.algorithms.week2.sort.SortUtil.*;

/**
 * An implementation of the selection sort.
 * Requires O(N^2) steps to sort an array.
 * Not stable.
 *
 * @author Renat Kaitmazov
 */

public final class Selection {

    private Selection() {
    }

    public static <T extends Comparable<T>> void sort(final T[] array) {
        if (array == null) {
            return;
        }

        final int size = array.length;
        for (int i = 0; i < size - 1; ++i) {
            // Assume that the first item is smallest one in the array.
            int indexOfSmallestItem = i;
            T smallestItem = array[indexOfSmallestItem];
            for (int j = i + 1; j < size; ++j) {
                // Find index of the actual smallest item in the array.
                final T currentItem = array[j];
                if (isLessThan(currentItem, smallestItem)) {
                    smallestItem = currentItem;
                    indexOfSmallestItem = j;
                }
            }

            if (i != indexOfSmallestItem) {
                // The assumption was not correct.
                // Swap only if the index has changed.
                swap(array, i, indexOfSmallestItem);
            }
        }
    }
}
