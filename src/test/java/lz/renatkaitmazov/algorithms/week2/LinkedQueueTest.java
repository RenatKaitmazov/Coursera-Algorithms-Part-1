package lz.renatkaitmazov.algorithms.week2;

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
public class LinkedQueueTest {

    private Queue<Integer> queue;

    @Before
    public final void setUp() {
        queue = new LinkedQueue<>();
    }

    @After
    public final void tearDown() {
        queue = null;
    }

    @Test
    public final void isEmptyTest() {
        assertTrue(queue.isEmpty());
        addValues(1);
        assertFalse(queue.isEmpty());
    }

    @Test
    public final void sizeTest() {
        assertEquals(0, queue.size());
        addValues(1, 2, 3, 4, 5);
        assertEquals(5, queue.size());
    }

    @Test
    public final void toStringTest() {
        assertEquals("[]", queue.toString());
        addValues(5, 3, 6, 2);
        assertEquals("[5, 3, 6, 2]", queue.toString());
    }

    @Test
    public final void enqueueTest() {
        addValues(10, 54, 23, 192);
        assertEquals("[10, 54, 23, 192]", queue.toString());
        assertEquals(4, queue.size());
    }

    @Test
    public final void dequeue() {
        addValues(90, 64, 7, 12, 429);
        assertEquals((Integer) 90, queue.dequeue());
        assertEquals(4, queue.size());
        assertEquals((Integer) 64, queue.dequeue());
        assertEquals(3, queue.size());
        assertEquals((Integer) 7, queue.dequeue());
        assertEquals(2, queue.size());
    }

    @Test
    public final void enqueueAndDequeueAllTest() {
        addValues(12, 54, 23, 76, 34, 12);
        while (!queue.isEmpty()) {
            queue.dequeue();
        }
        assertEquals(0, queue.size());
        assertTrue(queue.isEmpty());
        assertEquals("[]", queue.toString());
    }

    @Test
    public final void iteratorTest() {
        addValues(4, 5, 10, 34);
        String string = "";
        for (final Integer number : queue) {
            string += number;
        }
        assertEquals("451034", string);
    }

    private void addValues(Integer... values) {
        for (final Integer value : values) {
            queue.enqueue(value);
        }
    }
}