package lz.renatkaitmazov.algorithms.week3;

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
public final class QuickTest {


    @Test
    public void sort1000Items() {
        final Integer[] numbers = getUnsortedIntegerArray(1_000);
        assertFalse(isSorted(numbers));
        Quick.sort(numbers);
        assertTrue(isSorted(numbers));
    }

    @Test
    public void sort10000Items() {
        final Integer[] numbers = getUnsortedIntegerArray(10_000);
        assertFalse(isSorted(numbers));
        Quick.sort(numbers);
        assertTrue(isSorted(numbers));
    }

    @Test
    public void sort100000Items() {
        final Integer[] numbers = getUnsortedIntegerArray(100_000);
        assertFalse(isSorted(numbers));
        Quick.sort(numbers);
        assertTrue(isSorted(numbers));
    }

    @Test
    public void sortAlreadySorted100000Items() {
        final Integer[] numbers = getSortedIntegerArray(1_000_000);
        assertTrue(isSorted(numbers));
        Quick.sort(numbers);
        assertTrue(isSorted(numbers));
    }

}