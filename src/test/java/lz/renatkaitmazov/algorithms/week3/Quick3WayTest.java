package lz.renatkaitmazov.algorithms.week3;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import static org.junit.Assert.*;
import static lz.renatkaitmazov.algorithms.week2.sort.SortUtil.getUnsortedIntegerArray;
import static lz.renatkaitmazov.algorithms.week2.sort.SortUtil.isSorted;

/**
 * @author Renat Kaitmazov
 */

@RunWith(JUnit4.class)
public final class Quick3WayTest {


    @Test
    public void sort1000Items() {
        final Integer[] numbers = getUnsortedIntegerArray(20, 5);
        assertFalse(isSorted(numbers));
        Quick3Way.sort(numbers);
        assertTrue(isSorted(numbers));
    }

    @Test
    public void sort10000Items() {
        final Integer[] numbers = getUnsortedIntegerArray(10_000, 100);
        assertFalse(isSorted(numbers));
        Quick3Way.sort(numbers);
        assertTrue(isSorted(numbers));
    }

    @Test
    public void sort100000Items() {
        final Integer[] numbers = getUnsortedIntegerArray(100_000, 1_000);
        assertFalse(isSorted(numbers));
        Quick3Way.sort(numbers);
        assertTrue(isSorted(numbers));
    }
}