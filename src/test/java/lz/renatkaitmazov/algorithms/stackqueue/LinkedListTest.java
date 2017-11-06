package lz.renatkaitmazov.algorithms.stackqueue;

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
public class LinkedListTest {

    private List<Integer> linkedList;

    @Before
    public final void setUp() {
        linkedList = new LinkedList<>();
    }

    @After
    public final void tearDown() {
        linkedList = null;
    }

    @Test
    public final void toStringTest() {
        assertEquals("[]", linkedList.toString());
        addItems(1, 2, 3, 4);
        assertEquals("[1, 2, 3, 4]", linkedList.toString());
    }

    @Test
    public final void isEmptyTest() {
        assertTrue(linkedList.isEmpty());
        addItems(1, 2);
        assertFalse(linkedList.isEmpty());
    }

    @Test
    public final void containsTest() {
        assertFalse(linkedList.contains(100));
        addItems(10, 45, 12, 34);
        assertFalse(linkedList.contains(50));
        assertTrue(linkedList.contains(12));
        assertTrue(linkedList.contains(34));
    }

    @Test
    public final void insertTest() {
        linkedList.insert(3);
        linkedList.insert(21);
        linkedList.insert(100);
        linkedList.insert(null);
        assertEquals("[null, 100, 21, 3]", linkedList.toString());
    }

    @Test
    public final void appendTest() {
        addItems(1, 2, 3, null, 123, 3);
        assertEquals("[1, 2, 3, null, 123, 3]", linkedList.toString());
    }

    @Test
    public final void insertAndAppendTest() {
        linkedList.insert(12);
        linkedList.insert(10);
        linkedList.append(30);
        linkedList.append(100);
        linkedList.insert(1);
        linkedList.append(-1);
        assertEquals(6, linkedList.size());
        assertEquals("[1, 10, 12, 30, 100, -1]", linkedList.toString());
    }

    @Test
    public final void removeTest() {
        addItems(10, 20, 30, 50);
        assertNull(linkedList.remove(100));
        assertEquals((Integer) 10, linkedList.remove(10));
        assertEquals(3, linkedList.size());
        assertEquals((Integer) 20, linkedList.get(0));
        assertEquals("[20, 30, 50]", linkedList.toString());
        assertEquals((Integer) 50, linkedList.remove(50));
        assertEquals("[20, 30]", linkedList.toString());
        assertEquals(2, linkedList.size());
    }

    @Test
    public final void removeLastTest() {
        addItems(34, 21, 34, 3, 12, 45, 73);
        assertEquals((Integer) 73, linkedList.removeLast());
        assertEquals(6, linkedList.size());
        assertEquals((Integer) 45, linkedList.get(linkedList.size() - 1));
    }

    @Test
    public final void removeFirstTest() {
        addItems(54, 2, 78, 324, 90);
        assertEquals((Integer) 54, linkedList.removeFirst());
        assertEquals(4, linkedList.size());
        assertEquals((Integer) 2, linkedList.get(0));
    }

    @Test
    public final void removeAtTest() {
        addItems(87, 90, 23456, 3, 23, 286, 436);
        assertEquals((Integer) 23456, linkedList.removeAt(2));
        assertEquals(6, linkedList.size());
        assertEquals((Integer) 3, linkedList.get(2));
        assertEquals("[87, 90, 3, 23, 286, 436]", linkedList.toString());
    }

    @Test
    public final void removeAllFromTheEndTest() {
        addItems(1, 2, 3, 4, 5, 56);
        while (!linkedList.isEmpty()) {
            linkedList.removeLast();
        }
        assertEquals(0, linkedList.size());
        assertEquals("[]", linkedList.toString());
    }

    @Test
    public final void removeAllFromTheBeginningTest() {
        addItems(1, 2, 3, 4, 5);
        while (!linkedList.isEmpty()) {
            linkedList.removeFirst();
        }
        assertEquals(0, linkedList.size());
        assertEquals("[]", linkedList.toString());
    }

    @Test
    public final void sizeTest() {
        assertEquals(0, linkedList.size());
        addItems(1, 2, 3, 4, 5, 6, 7, 8);
        assertEquals(8, linkedList.size());
    }

    @Test
    public final void indexOfTest() {
        addItems(23, null, 12, 67, 100);
        assertEquals(-1, linkedList.indexOf(1_000));
        assertEquals(1, linkedList.indexOf(null));
        assertEquals(4, linkedList.indexOf(100));
        assertEquals(0, linkedList.indexOf(23));
        assertEquals(2, linkedList.indexOf(12));
        assertEquals(3, linkedList.indexOf(67));
    }

    @Test
    public final void getTest() {
        addItems(40, 10, 100, null, 55);
        assertEquals(null, linkedList.get(3));
        assertEquals((Integer) 40, linkedList.get(0));
        assertEquals((Integer) 10, linkedList.get(1));
        assertEquals((Integer) 100, linkedList.get(2));
        assertEquals((Integer) 55, linkedList.get(4));
    }

    @Test
    public final void iteratorTest() {
        addItems(4, 5, 6, 7);
        String string = "";
        for (final Integer item : linkedList) {
            string += item;
        }
        assertEquals("4567", string);
    }

    private void addItems(Integer... items) {
        for (final Integer item : items) {
            linkedList.append(item);
        }
    }
}