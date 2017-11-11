package lz.renatkaitmazov.algorithms.week3;

import static lz.renatkaitmazov.algorithms.week3.Merge.merge;
import static lz.renatkaitmazov.algorithms.week2.sort.SortUtil.isLessThan;

/**
 * An alternative implementation of the merge sort.
 * Does not use recursion.
 * Stable.
 *
 * @author Renat Kaitmazov
 */

public final class BottomUpMerge {

    private BottomUpMerge() {
    }

    public static <T extends Comparable<T>> void sort(final T[] array) {
        if (array == null) {
            return;
        }

        final int size = array.length;
        @SuppressWarnings("unchecked")
        final T[] auxiliary = (T[]) new Comparable[size];
        for (int step = 1; step < size; step <<= 1) {
            for (int start = 0; start < size - step; start += (step << 1)) {
                final int potentialEnd = start + (step << 1) - 1;
                final int actualEnd = Math.min(potentialEnd, size - 1);
                int middle = start + step - 1;
                if (!isLessThan(array[middle + 1], array[middle])) {
                    continue;
                }
                merge(array, auxiliary, start, middle, actualEnd);
            }
        }
    }
}
