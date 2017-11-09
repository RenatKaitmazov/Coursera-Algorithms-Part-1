package lz.renatkaitmazov.algorithms.week2.sort;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.util.Random;

import static org.junit.Assert.*;

/**
 * @author Renat Kaitmazov
 */

@RunWith(JUnit4.class)
public class SelectionTest {

    private Random random;

    @Before
    public void setUp() {
        random = new Random(System.nanoTime());
    }

    @After
    public void tearDown() {
        random = null;
    }

    @Test
    public void sort100Items() {
        final Integer[] array = getUnsortedArray(100);
        assertFalse(SortUtil.isSorted(array));
        Selection.sort(array);
        assertTrue(SortUtil.isSorted(array));
    }

    @Test
    public void sort1000Items() {
        final Integer[] array = getUnsortedArray(1_000);
        assertFalse(SortUtil.isSorted(array));
        Selection.sort(array);
        assertTrue(SortUtil.isSorted(array));
    }

    @Test
    public void sort5000Items() {
        final Integer[] array = getUnsortedArray(5_000);
        assertFalse(SortUtil.isSorted(array));
        Selection.sort(array);
        assertTrue(SortUtil.isSorted(array));
    }

    private Integer[] getUnsortedArray(int size) {
        final Integer[] numbers = new Integer[size];
        for (int i = 0; i < size; ++i) {
            numbers[i] = random.nextInt(size << 1);
        }
        return numbers;
    }
}