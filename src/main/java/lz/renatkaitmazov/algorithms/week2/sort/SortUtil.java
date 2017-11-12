package lz.renatkaitmazov.algorithms.week2.sort;

import java.util.Random;

/**
 * @author Renat Kaitmazov
 */

public final class SortUtil {

    private static final Random RANDOM = new Random(System.nanoTime());

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

    public static Integer[] getSortedIntegerArray(int size) {
        final Integer[] numbers = new Integer[size];
        for (int i = 0; i < size; ++i) {
            numbers[i] = i;
        }
        return numbers;
    }

    public static Integer[] getUnsortedIntegerArray(int size) {
        final Integer[] numbers = new Integer[size];
        final int bound = size << 1;
        for (int i = 0; i < size; ++i) {
            numbers[i] = RANDOM.nextInt(bound);
        }
        return numbers;
    }

    public static <T> void shuffle(final T[] array) {
        final int size = array.length;
        for (int i = 0; i < size; ++i) {
            final int randomIndex = RANDOM.nextInt(i + 1);
            swap(array, i, randomIndex);
        }
    }
}
