package lz.renatkaitmazov.algorithms.week6;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.util.*;

import static org.junit.Assert.*;

/**
 * @author Renat Kaitmazov
 */

@RunWith(JUnit4.class)
public final class LinearProbingSetTest {

    private LinearProbingSet<Integer> set;
    private int capacity = 4;

    @Before
    public void setUp() {
        set = new LinearProbingSet<>(capacity);
    }

    @After
    public void tearDown() {
        set = null;
    }

    @Test
    public void sizeTest() {
        set.add(3);
        set.add(6);
        set.add(10);
        assertFalse(set.isEmpty());
        assertEquals(3, set.size());
    }

    @Test
    public void noDuplicatesTest() {
        final Random random = new Random(System.nanoTime());
        for (int i = 0; i < 100_000; ++i) {
            set.add(random.nextInt(10));
        }
        assertTrue(set.size() <= 10);
    }

    @Test
    public void addTest1() {
        set.add(10);
        set.remove(10);
        set.add(10);
        assertEquals(1, set.size());
        assertTrue(set.contains(10));
    }

    @Test
    public void addTest2() {
        // 0 and 8 generates two equal hash codes.
        set.add(8);
        set.add(0);
        set.add(0);
        assertEquals(2, set.size());
    }

    @Test
    public void getTest() {
        final Integer[] numbers = {4, 3, 6, 4, 7, 8, 810};
        for (final Integer number : numbers) {
            set.add(number);
        }
        assertNull(set.get(-1));
        assertNull(set.get(1_000));
        assertEquals((Integer) 4, set.get(4));
        assertEquals((Integer) 6, set.get(6));
        assertEquals((Integer) 8, set.get(8));
        assertEquals((Integer) 810, set.get(810));
    }

    @Test
    public void removeTest() {
        final Integer[] numbers = {4, 3, 6, 4, 7, 8, 810};
        for (final Integer number : numbers) {
            set.add(number);
        }
        assertEquals((Integer) 810, set.remove(810));
        assertEquals((Integer) 4, set.remove(4));
        assertEquals((Integer) 3, set.remove(3));
        assertEquals((Integer) 6, set.remove(6));
        assertEquals((Integer) 7, set.remove(7));
        assertEquals((Integer) 8, set.remove(8));
        assertTrue(set.isEmpty());
        assertEquals(4, set.getCapacity());
    }

    @Test
    public void iteratorTest() {
        final Integer[] numbers = {4, 3, 6, 4, 7, 8, 810};
        for (final Integer number : numbers) {
            set.add(number);
        }
        final List<Integer> actual = new ArrayList<>();
        final Iterator<Integer> iterator = set.iterator();
        while (iterator.hasNext()) {
            actual.add(iterator.next());
        }
        final List<Integer> expected = Arrays.asList(3, 4, 6, 7, 8, 810);
        actual.sort(Comparator.naturalOrder());
        assertEquals(actual, expected);
    }
}