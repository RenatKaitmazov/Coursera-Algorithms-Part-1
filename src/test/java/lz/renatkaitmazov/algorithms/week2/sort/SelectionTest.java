package lz.renatkaitmazov.algorithms.week2.sort;

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
public class SelectionTest {

    @Test
    public void sort100Items() {
        final Integer[] array = getUnsortedIntegerArray(100);
        assertFalse(isSorted(array));
        Selection.sort(array);
        assertTrue(isSorted(array));
    }

    @Test
    public void sort1000Items() {
        final Integer[] array = getUnsortedIntegerArray(1_000);
        assertFalse(isSorted(array));
        Selection.sort(array);
        assertTrue(isSorted(array));
    }

    @Test
    public void sort5000Items() {
        final Integer[] array = getUnsortedIntegerArray(5_000);
        assertFalse(isSorted(array));
        Selection.sort(array);
        assertTrue(isSorted(array));
    }
}