package lz.renatkaitmazov.algorithms.week2.sort;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.util.Random;

import static org.junit.Assert.*;
import static lz.renatkaitmazov.algorithms.week2.sort.SortUtil.isSorted;

/**
 * @author Renat Kaitmazov
 */

@RunWith(JUnit4.class)
public class InsertionTest {

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
    public void sort100Test() {
        final Integer[] numbers = getUnsortedArray(10);
        assertFalse(isSorted(numbers));
        Insertion.sort(numbers);
        assertTrue(isSorted(numbers));
    }

    @Test
    public void sort1000Test() {
        final Integer[] numbers = getUnsortedArray(1_000);
        assertFalse(isSorted(numbers));
        Insertion.sort(numbers);
        assertTrue(isSorted(numbers));
    }

    @Test
    public void sort5000Test() {
        final Integer[] numbers = getUnsortedArray(5_000);
        assertFalse(isSorted(numbers));
        Insertion.sort(numbers);
        assertTrue(isSorted(numbers));
    }

    @Test
    public void sort100000AlreadySortedArrayTest() {
        final Integer[] numbers = getSortedArray(100_000);
        assertTrue(isSorted(numbers));
        Insertion.sort(numbers);
        assertTrue(isSorted(numbers));
    }

    private Integer[] getUnsortedArray(int size) {
        final Integer[] numbers = new Integer[size];
        final int bound = size << 1;
        for (int i = 0; i < size; ++i) {
            numbers[i] = random.nextInt(bound);
        }
        return numbers;
    }

    private Integer[] getSortedArray(int size) {
        final Integer[] numbers = new Integer[size];
        for (int i = 0; i < size; ++i) {
            numbers[i] = i;
        }
        return numbers;
    }
}