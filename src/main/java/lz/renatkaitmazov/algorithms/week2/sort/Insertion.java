package lz.renatkaitmazov.algorithms.week2.sort;

import static lz.renatkaitmazov.algorithms.week2.sort.SortUtil.*;

/**
 * An implementation of the insertion sort algorithm.
 * Average running time is O(N^2), but O(N) for arrays which are already sorted
 * or almost sorted.
 * Stable.
 *
 * @author Renat Kaitmazov
 */

public final class Insertion {

    private Insertion() {
    }

    public static <T extends Comparable<T>> void sort(final T[] array) {
        if (array == null) {
            return;
        }

        final int size = array.length;
        for (int i = 1; i < size; ++i) {
            final T currentItem = array[i];
            int j = i - 1;
            while (j > -1 && isLessThan(currentItem, array[j])) {
                // While items before the current item are greater than the current item,
                // shift them all by one cell to the right to make a vacant cell in the array
                // for the current item.
                array[j + 1] = array[j];
                --j;
            }
            if (j != i - 1) {
                // Place the current item into the right cell if it was not already there.
                array[j + 1] = currentItem;
            }
        }
    }
}
