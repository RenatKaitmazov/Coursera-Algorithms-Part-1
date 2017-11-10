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
        sort(array, 0, array.length - 1);
    }

    /**
     * Normally this method should be hidden from clients. But the curriculum of the course
     * does not allow me to do so, so I have to make it public.
     * It would be better to group all sorting algorithms into one package and use this method internally
     * (it is really helpful to use the insertion sort for tiny subarrays in the merge and quick sorts to reduce
     * the amount of recursive calls), but, again, it is due to the curriculum that covers elementary sorting algorithms
     * in week two, and more advanced sorting algorithms in week three.
     *
     * Sorts subarrays.
     *
     * @param array to be sorted.
     * @param startIndex the beginning index of the subarray.
     * @param endIndex the last index of the subarray (inclusive)
     */
    public static <T extends Comparable<T>> void sort(final T[] array, int startIndex, int endIndex) {
        for (int i = startIndex + 1; i <= endIndex; ++i) {
            final T currentItem = array[i];
            int j = i - 1;
            while (j >= startIndex && isLessThan(currentItem, array[j])) {
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
