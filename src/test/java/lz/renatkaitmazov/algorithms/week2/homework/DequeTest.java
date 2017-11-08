package lz.renatkaitmazov.algorithms.week2.homework;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.NoSuchElementException;

import static org.junit.Assert.*;

/**
 * @author Renat Kaitmazov
 */

public class DequeTest {

    private Deque<Integer> deque;

    @Before
    public final void setUp() {
        deque = new Deque<>();
    }

    @After
    public final void tearDown() {
        deque = null;
    }

    @Test
    public final void isEmpty() {
        assertTrue(deque.isEmpty());
        deque.addFirst(23);
        assertFalse(deque.isEmpty());
    }

    @Test
    public final void size() {
        assertEquals(0, deque.size());
        addToEnd(1, 2, 3, 4, 100, 5, 6, 7, 87, 8);
        assertEquals(10, deque.size());
    }

    @Test
    public final void addFirst() {
        addToFront(5, 6, 7, 8);
        assertEquals(4, deque.size());
        assertEquals("[8, 7, 6, 5]", deque.toString());
    }

    @Test
    public final void addLast() {
        addToEnd(1, 2, 3, 4, 10);
        assertEquals(5, deque.size());
        assertEquals("[1, 2, 3, 4, 10]", deque.toString());
    }

    @Test
    public final void removeFirst() {
        addToEnd(1, 2, 3, 4, 5, 6);
        assertEquals((Integer) 1, deque.removeFirst());
        assertEquals(5, deque.size());
        assertEquals((Integer) 2, deque.removeFirst());
        assertEquals(4, deque.size());
        assertEquals((Integer) 3, deque.removeFirst());
        assertEquals(3, deque.size());
        assertEquals((Integer) 4, deque.removeFirst());
        assertEquals(2, deque.size());
        assertEquals("[5, 6]", deque.toString());
    }

    @Test
    public final void removeLast() {
        addToEnd(1, 2, 3, 4, 5, 6);
        assertEquals((Integer) 6, deque.removeLast());
        assertEquals(5, deque.size());
        assertEquals((Integer) 5, deque.removeLast());
        assertEquals(4, deque.size());
        assertEquals((Integer) 4, deque.removeLast());
        assertEquals(3, deque.size());
        assertEquals((Integer) 3, deque.removeLast());
        assertEquals(2, deque.size());
        assertEquals("[1, 2]", deque.toString());
    }

    @Test
    public final void addFirstAndLastMixTest() {
        deque.addLast(4);
        deque.addLast(456);
        deque.addFirst(3);
        deque.addFirst(12);
        deque.addLast(12);
        assertEquals("[12, 3, 4, 456, 12]", deque.toString());
    }

    @Test
    public final void removeAllTest() {
        addToEnd(45, 2, 4);
        addToFront(10, 13, 1);
        while (!deque.isEmpty()) {
            deque.removeLast();
        }
        assertEquals("[]", deque.toString());
        assertTrue(deque.isEmpty());
    }

    @Test(expected = IllegalArgumentException.class)
    public final void addNullFirstTest() {
        deque.addFirst(null);
    }

    @Test(expected = IllegalArgumentException.class)
    public final void addNullLastTest() {
        deque.addLast(null);
    }

    @Test(expected = NoSuchElementException.class)
    public final void removeFirstFromEmptyDequeueTest() {
        deque.removeFirst();
    }

    @Test(expected = NoSuchElementException.class)
    public final void removeLastFromEmptyDequeueTest() {
        deque.removeLast();
    }

    @Test
    public final void iterator() {
        addToEnd(10, 20, 30, 40);
        String string = "";
        for (final Integer value : deque) {
            string += value;
        }
        assertEquals("10203040", string);
    }


    private void addToFront(Integer... values) {
        for (final Integer value : values) {
            deque.addFirst(value);
        }
    }

    private void addToEnd(Integer... values) {
        for (final Integer value : values) {
            deque.addLast(value);
        }
    }
}