package lz.renatkaitmazov.algorithms.week2.homework;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * @author Renat Kaitmazov
 */

public class RandomizedQueueTest {

    private RandomizedQueue<Integer> queue;

    @Before
    public final void setUp() {
        queue = new RandomizedQueue<>();
    }

    @After
    public final void tearDown() {
        queue = null;
    }

    @Test
    public final void isEmpty() {
        assertTrue(queue.isEmpty());
        queue.enqueue(23);
        assertFalse(queue.isEmpty());
    }

    @Test
    public final void size() {
        assertEquals(0, queue.size());
        addItems(45, 123, 5, 2, 2);
        assertEquals(5, queue.size());
    }

    @Test
    public final void enqueue() {
        addItems();
    }

    @Test
    public final void dequeue() {
    }

    @Test
    public final void enqueueAndDequeue() {
        addItems(4, 3, 2, 6, 7, 12, 43);
        while (!queue.isEmpty()) {
            queue.dequeue();
        }
        assertEquals(0, queue.size());
        assertTrue(queue.isEmpty());
    }

    @Test
    public final void sample() {
        addItems(45, 23, 12, 56, 123, 12, 1);
        final boolean b = (45 == queue.sample()) && (45 == queue.sample()) && (45 == queue.sample());
        assertFalse(b);
    }

    @Test
    public final void iterator() {
        addItems(54, 23, 87, 1, 4, 21);
        String string = "";
        for (final Integer number : queue) {
            string += number;
        }
        assertNotEquals("5423871421", string);
    }

    private void addItems(Integer... values) {
        for (final Integer value : values) {
            queue.enqueue(value);
        }
    }
}