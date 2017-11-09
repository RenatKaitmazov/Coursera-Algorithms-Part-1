package lz.renatkaitmazov.algorithms.week2.sort;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import static org.junit.Assert.*;
import static lz.renatkaitmazov.algorithms.week2.sort.SortUtil.isSorted;
import static lz.renatkaitmazov.algorithms.week2.sort.SortUtil.getSortedIntegerArray;

/**
 * @author Renat Kaitmazov
 */

@RunWith(JUnit4.class)
public final class ShuffleTest {

    @Test
    public void shuffle10Test() {
        final Integer[] numbers = getSortedIntegerArray(10);
        assertTrue(isSorted(numbers));
        Shuffle.shuffle(numbers);
        assertFalse(isSorted(numbers));
    }

    @Test
    public void shuffle1000Test() {
        final Integer[] sortedNumbers = getSortedIntegerArray(1_000);
        assertTrue(isSorted(sortedNumbers));
        Shuffle.shuffle(sortedNumbers);
        assertFalse(isSorted(sortedNumbers));
    }

}