package lz.renatkaitmazov.algorithms.week2.sort;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import static org.junit.Assert.*;
import static lz.renatkaitmazov.algorithms.week2.sort.SortUtil.isSorted;
import static lz.renatkaitmazov.algorithms.week2.sort.SortUtil.getSortedIntegerArray;
import static lz.renatkaitmazov.algorithms.week2.sort.SortUtil.getUnsortedIntegerArray;

/**
 * @author Renat Kaitmazov
 */

@RunWith(JUnit4.class)
public final class ShellTest {

    @Test
    public void sort100Test() {
        final Integer[] numbers = getUnsortedIntegerArray(100);
        assertFalse(isSorted(numbers));
        Shell.sort(numbers);
        assertTrue(isSorted(numbers));
    }

    @Test
    public void sort1000Test() {
        final Integer[] numbers = getUnsortedIntegerArray(1_000);
        assertFalse(isSorted(numbers));
        Shell.sort(numbers);
        assertTrue(isSorted(numbers));
    }

    @Test
    public void sort50000Test() {
        final Integer[] numbers = getUnsortedIntegerArray(50_000);
        assertFalse(isSorted(numbers));
        Shell.sort(numbers);
        assertTrue(isSorted(numbers));
    }

    @Test
    public void sort100000AlreadySortedArrayTest() {
        final Integer[] numbers = getSortedIntegerArray(100_000);
        assertTrue(isSorted(numbers));
        Shell.sort(numbers);
        assertTrue(isSorted(numbers));
    }
}