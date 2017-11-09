package lz.renatkaitmazov.algorithms.week2.sort;

import java.util.Random;

import static lz.renatkaitmazov.algorithms.week2.sort.SortUtil.swap;

/**
 * An implementation of the shuffling algorithm.
 * Takes linear time to get the job done.
 *
 * @author Renat Kaitmazov
 */

public final class Shuffle {

    private static final Random RANDOM = new Random(System.nanoTime());

    private Shuffle() {
    }

    public static <T> void shuffle(final T[] array) {
        final int size = array.length;
        for (int i = 0; i < size; ++i) {
            final int randomIndex = RANDOM.nextInt(i + 1);
            swap(array, i, randomIndex);
        }
    }
}
