package lz.renatkaitmazov.algorithms.week3;

import static lz.renatkaitmazov.algorithms.week3.Quick.partition;

/**
 * Select kth element from an array.
 *
 * @author Renat Kaitmazov
 */

public final class Select {

    private Select() {
    }

    public static <T extends Comparable<T>> T select(final T[] array, int i) {
        if (array != null) {
            int start = 0;
            int end = array.length - 1;
            while (start <= end) {
                final int partition = partition(array, start, end);
                if      (i < partition) end = partition - 1;
                else if (i > partition) start = partition + 1;
                else return array[partition];
            }
        }
        return null;
    }
}
