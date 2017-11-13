package lz.renatkaitmazov.algorithms.week3;

import lz.renatkaitmazov.algorithms.week2.sort.Insertion;

import static lz.renatkaitmazov.algorithms.week2.sort.SortUtil.swap;
import static lz.renatkaitmazov.algorithms.week3.Quick.median;

/**
 * An implementation of the quick sort algorithm that is specifically designed
 * to sort arrays with a lot of duplicate key.
 * It divides an array into three parts where items less than a pivot are in
 * the first part, items equal to the pivot are in the second part, and items greater
 * than the pivot are in the third part.
 * This approach allows this sorting algorithm to do its work way more efficiently when
 * there are lots of duplicates.
 *
 * @author Renat Kaitmazov
 */

public final class Quick3Way {

    /**
     * The amount of elements in a subarray that should be sorted using
     * the insertion sort.
     */

    private static final int THRESHOLD = 7;


    private Quick3Way() {
    }

    public static <T extends Comparable<T>> void sort(final T[] array) {
        sort(array, 0, array.length - 1);
    }

    private static <T extends Comparable<T>> void sort(final T[] array, int start, int end) {
        if ((end - start + 1) <= THRESHOLD) {
            Insertion.sort(array, start, end);
            return;
        }

        final int middle = start + ((end - start) >> 1);
        median(array, start, middle, end);

        int less = start; // Items smaller than the pivot are after behind index.
        int equal = start + 1; // Items equal to the pivot are behind this index but after the less index
        int greater = end; // Items greater than the pivot are after this index.
        /*  less            equal           greater */
        /* [< pivot      | = pivot |       > pivot] */

        final T pivot = array[start];
        while (equal <= greater) {
            final T current = array[equal];
            final int compareResult = current.compareTo(pivot);
            if      (compareResult < 0) swap(array, equal++, less++);
            else if (compareResult > 0) swap(array, equal, greater--);
            else ++equal;
        }

        // Sort items less than the pivot.
        sort(array, start, less - 1);
        // Sort items greater than the pivot.
        sort(array, greater + 1, end);
    }
}
