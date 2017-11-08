package lz.renatkaitmazov.algorithms.week2.sort;

/**
 * @author Renat Kaitmazov
 */

public final class SortUtil {

    private SortUtil() {
    }

    public static <T> void swap(final T[] array, int i, int j) {
        final T temp = array[i];
        array[i] = array[j];
        array[j] = temp;
    }

    public static <T extends Comparable<T>> boolean isLessThan(T lhs, T rhs) {
        return lhs.compareTo(rhs) < 0;
    }

    public static <T extends Comparable<T>> boolean isSorted(final T[] array) {
        final int upperBound = array.length - 1;
        for (int i = 0; i < upperBound; ++i) {
            final T current = array[i];
            final T next = array[i + 1];
            if (isLessThan(next, current)) {
                return false;
            }
        }
        return true;
    }
}
