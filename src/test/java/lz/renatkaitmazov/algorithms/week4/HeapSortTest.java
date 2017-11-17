package lz.renatkaitmazov.algorithms.week4;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import static org.junit.Assert.*;
import static lz.renatkaitmazov.algorithms.week2.sort.SortUtil.isSorted;
import static lz.renatkaitmazov.algorithms.week2.sort.SortUtil.getUnsortedIntegerArray;

/**
 * @author Renat Kaitmazov
 */

@RunWith(JUnit4.class)
public final class HeapSortTest {

    @Test
    public void sort1000ItemsTest() {
        final Integer[] numbers = getUnsortedIntegerArray(1_000);
        assertFalse(isSorted(numbers));
        HeapSort.sort(numbers);
        assertTrue(isSorted(numbers));

    }

    @Test
    public void sort10000ItemsTest() {
        final Integer[] numbers = getUnsortedIntegerArray(10_000);
        assertFalse(isSorted(numbers));
        HeapSort.sort(numbers);
        assertTrue(isSorted(numbers));
    }

    @Test
    public void sort100000ItemsTest() {
        final Integer[] numbers = getUnsortedIntegerArray(100_000);
        assertFalse(isSorted(numbers));
        HeapSort.sort(numbers);
        assertTrue(isSorted(numbers));
    }
}