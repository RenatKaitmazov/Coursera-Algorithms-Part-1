package lz.renatkaitmazov.algorithms.week3;

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
public final class MergeTest {

    @Test
    public void sortOddSizedArrayTest() {
        final Integer[] numbers = getUnsortedIntegerArray(11);
        assertFalse(isSorted(numbers));
        Merge.sort(numbers);
        assertTrue(isSorted(numbers));
    }

    @Test
    public void sort10000Items() {
        final Integer[] numbers = getUnsortedIntegerArray(10_000);
        assertFalse(isSorted(numbers));
        Merge.sort(numbers);
        assertTrue(isSorted(numbers));
    }

    @Test
    public void sort100000Items() {
        final Integer[] numbers = getUnsortedIntegerArray(51);
        assertFalse(isSorted(numbers));
        Merge.sort(numbers);
        assertTrue(isSorted(numbers));
    }
}