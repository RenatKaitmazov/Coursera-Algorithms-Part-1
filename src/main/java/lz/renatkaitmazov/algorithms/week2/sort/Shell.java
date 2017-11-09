package lz.renatkaitmazov.algorithms.week2.sort;

import static lz.renatkaitmazov.algorithms.week2.sort.SortUtil.isLessThan;

/**
 * An implementation of the shell sort algorithm.
 * On average requires O(N*log^2(N)) steps to sort an array.
 * Not stable.
 *
 * @author Renat Kaitmazov
 */

public final class Shell {

    private Shell() {
    }

    public static <T extends Comparable<T>> void sort(final T[] array) {
        if (array == null) {
            return;
        }

        final int size = array.length;
        final int bound = size / 3;
        int gap = 1;
        while (gap < bound) {
            // Calculate initial gap between two comparable items.
            // It is calculated by the following formula: 3n + 1
            gap = 3 * gap + 1;
        }

        while (gap > 0) {

            // The algorithm is similar to the insertion sort with one crucial difference.
            // The insertion sort moves items only by one cell while the shell sort moves
            // items by some gap which will essentially become equal to one but at that time
            // the array is going to be almost sorted and as we know the insertion sort
            // takes linear time for arrays that are partially sorted.

            for (int i = gap; i < size; ++i) {
                final T currentItem = array[i];
                int j = i - gap;
                while (j > -1 && isLessThan(currentItem, array[j])) {
                    array[j + gap] = array[j];
                    j -= gap;
                }

                if (j != i - gap) {
                    array[j + gap] = currentItem;
                }
            }

            gap /= 3;
        }
    }
}
