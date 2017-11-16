package lz.renatkaitmazov.algorithms.week4;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import static org.junit.Assert.*;

/**
 * @author Renat Kaitmazov
 */

@RunWith(JUnit4.class)
public final class MaxQueueTest {

    private AbstractPriorityQueue<Integer> maxQueue;

    @Before
    public void setUp() {
        maxQueue = new MaxQueue<>(3);
    }

    @After
    public void tearDown() {
        maxQueue = null;
    }

    @Test
    public void emptyQueueTest() {
        assertTrue(maxQueue.isEmpty());
    }

    @Test
    public void initialSizeTest() {
        assertEquals(0, maxQueue.size());
    }

    @Test
    public void initialToStringTest() {
        assertEquals("[]", maxQueue.toString());
    }

    @Test
    public void insertTest() {
        maxQueue.insert(43);
        maxQueue.insert(76);
        maxQueue.insert(55);
        maxQueue.insert(32);
        maxQueue.insert(15);

        assertEquals(6, maxQueue.capacity());
        assertEquals(5, maxQueue.size());
        assertEquals("[76, 43, 55, 32, 15]", maxQueue.toString());
    }

    @Test
    public void peekTest() {
        maxQueue.insert(100);
        maxQueue.insert(10);
        maxQueue.insert(12);
        maxQueue.insert(9);
        maxQueue.insert(55);
        maxQueue.insert(500);

        assertEquals((Integer) 500, maxQueue.peek());
        assertEquals("[500, 55, 100, 9, 10, 12]", maxQueue.toString());
    }

    @Test
    public void removeTest() {
        maxQueue.insert(192);
        maxQueue.insert(635);
        maxQueue.insert(361);
        maxQueue.insert(810);
        maxQueue.insert(651);
        maxQueue.insert(991);
        maxQueue.insert(231);
        maxQueue.insert(442);

        assertEquals(12, maxQueue.capacity());
        assertEquals(8, maxQueue.size());
        assertEquals("[991, 651, 810, 442, 635, 361, 231, 192]", maxQueue.toString());
        assertEquals((Integer) 991, maxQueue.remove());
        assertEquals((Integer) 810, maxQueue.remove());
        assertEquals((Integer) 651, maxQueue.remove());
        assertEquals((Integer) 635, maxQueue.remove());
        assertEquals((Integer) 442, maxQueue.remove());
        assertEquals(6, maxQueue.capacity());
        assertEquals((Integer) 361, maxQueue.remove());
        assertEquals((Integer) 231, maxQueue.remove());
        assertEquals((Integer) 192, maxQueue.remove());
        assertNull(maxQueue.remove());
        assertEquals(1, maxQueue.capacity());
        assertEquals("[]", maxQueue.toString());
        assertTrue(maxQueue.isEmpty());
    }
}