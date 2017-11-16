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
public final class MinQueueTest {

    private AbstractPriorityQueue<String> minQueue;

    @Before
    public void setUp() {
        minQueue = new MinQueue<>(2);
    }

    @After
    public void tearDown() {
        minQueue = null;
    }

    @Test
    public void initialToStringTest() {
        assertEquals("[]", minQueue.toString());
    }

    @Test
    public void emptyQueueTest() {
        assertTrue(minQueue.isEmpty());
    }

    @Test
    public void initialSizeTest() {
        assertEquals(0, minQueue.size());
    }

    @Test
    public void insert() {
        minQueue.insert("Zoo");
        minQueue.insert("Food");
        minQueue.insert("Helium");
        minQueue.insert("Day");
        minQueue.insert("Apple");

        assertEquals(8, minQueue.capacity());
        assertEquals(5, minQueue.size());
        assertEquals("[Apple, Day, Helium, Zoo, Food]", minQueue.toString());
    }

    @Test
    public void remove() {
        assertNull(minQueue.remove());

        minQueue.insert("Zoo");
        minQueue.insert("Food");
        minQueue.insert("Helium");
        minQueue.insert("Day");
        minQueue.insert("Apple");

        assertEquals("Apple", minQueue.remove());
        assertEquals("Day", minQueue.remove());
        assertEquals("Food", minQueue.remove());
        assertEquals("Helium", minQueue.remove());
        assertEquals("Zoo", minQueue.remove());
        assertEquals(1, minQueue.capacity());
        assertEquals(0, minQueue.size());
        assertTrue(minQueue.isEmpty());
        assertEquals("[]", minQueue.toString());
    }

    @Test
    public void peek() {
        assertNull(minQueue.peek());

        minQueue.insert("Zoo");
        minQueue.insert("Food");
        minQueue.insert("Helium");
        minQueue.insert("Day");
        minQueue.insert("Apple");

        assertEquals("Apple", minQueue.peek());
    }

}