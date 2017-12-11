package lz.renatkaitmazov.algorithms.week5;

import lz.renatkaitmazov.algorithms.week2.Queue;
import lz.renatkaitmazov.algorithms.week4.SearchTree;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.util.Arrays;
import java.util.Comparator;

import static org.junit.Assert.*;

/**
 * @author Renat Kaitmazov
 */

@RunWith(JUnit4.class)
public final class RedBlackTreeTest {

    private SearchTree<String> emptyTree;
    private RedBlackTree<Integer> emptyIntTree;
    private SearchTree<Integer> filledTree;
    private SearchTree<Integer> reversedTree;
    private Integer[] numbers;


    @Before
    public void setUp() {
        emptyTree = new RedBlackTree<>();
        emptyIntTree = new RedBlackTree<>();
        filledTree = new RedBlackTree<>();
        reversedTree = new RedBlackTree<>(Comparator.<Integer>reverseOrder());

        numbers = new Integer[]{579, 490, 864, 300, 563, 750, 914, 50, 410, 520, 570, 631, 820, 890, 1_024};
        for (final Integer number : numbers) {
            filledTree.insert(number);
            reversedTree.insert(number);
        }
    }

    @After
    public void tearDown() {
        emptyTree = null;
        filledTree = null;
        reversedTree = null;
        numbers = null;
    }

    @Test
    public void sizeTest() {
        assertEquals(0, emptyTree.size());
        assertEquals(15, filledTree.size());
        assertEquals(15, reversedTree.size());
    }

    @Test
    public void containsTest() {
        assertFalse(emptyTree.contains("Hi"));

        assertTrue(filledTree.contains(563));
        assertTrue(filledTree.contains(890));
        assertFalse(filledTree.contains(23));
        assertFalse(filledTree.contains(360));

        assertTrue(reversedTree.contains(820));
        assertTrue(reversedTree.contains(914));
        assertFalse(reversedTree.contains(730));
        assertFalse(reversedTree.contains(834));
    }

    @Test
    public void isEmptyTest() {
        assertTrue(emptyTree.isEmpty());
        assertFalse(filledTree.isEmpty());
        assertFalse(reversedTree.isEmpty());
    }

    @Test
    public void getTest() {
        assertNull(emptyTree.get("Hello"));

        assertEquals((Integer) 864, filledTree.get(864));
        assertEquals((Integer) 570, filledTree.get(570));
        assertNull(filledTree.get(49));
        assertNull(filledTree.get(999));

        assertEquals((Integer) 579, reversedTree.get(579));
        assertEquals((Integer) 300, reversedTree.get(300));
        assertNull(reversedTree.get(751));
        assertNull(reversedTree.get(569));
    }

    @Test
    public void minTest() {
        assertNull(emptyTree.min());
        assertEquals((Integer) 50, filledTree.min());
        assertEquals((Integer) 1_024, reversedTree.min());
    }

    @Test
    public void maxTest() {
        assertNull(emptyTree.max());
        assertEquals((Integer) 1_024, filledTree.max());
        assertEquals((Integer) 50, reversedTree.max());
    }

    @Test
    public void floorTest() {
        assertNull(filledTree.floor(30));
        assertEquals((Integer) 1_024, filledTree.floor(2_000));
        assertEquals((Integer) 864, filledTree.floor(864));
        assertEquals((Integer) 570, filledTree.floor(570));
        assertEquals((Integer) 563, filledTree.floor(565));

        assertNull(emptyTree.floor("Welcome!"));
    }

    @Test
    public void ceilTest() {
        assertNull(filledTree.ceil(2_000));
        assertEquals((Integer) 820, filledTree.ceil(820));
        assertEquals((Integer) 914, filledTree.ceil(900));
        assertEquals((Integer) 50, filledTree.ceil(10));
        assertEquals((Integer) 520, filledTree.ceil(500));

        assertNull(emptyTree.ceil("Princeton"));
    }

    @Test
    public void rankTest() {
        assertEquals(7, filledTree.rank(579));
        assertEquals(8, filledTree.rank(585));
        assertEquals(10, filledTree.rank(810));
        assertEquals(10, filledTree.rank(820));
        assertEquals(15, filledTree.rank(1_900));
        assertEquals(0, filledTree.rank(-10));
    }

    @Test
    public void selectTest() {
        assertEquals((Integer) 490, filledTree.select(3));
        assertEquals((Integer) 864, filledTree.select(11));
        assertEquals((Integer) 410, filledTree.select(2));
        assertEquals((Integer) 631, filledTree.select(8));
    }

    @Test
    public void sizeInRangeTest() {
        assertEquals(4, filledTree.size(520, 579));
        assertEquals(4, filledTree.size(500, 600));
        assertEquals(15, filledTree.size(50, 1_024));
        assertEquals(15, filledTree.size(-10, 2_000));
        assertEquals(5, filledTree.size(570, 820));
        assertEquals(5, filledTree.size(565, 850));
    }

    @Test
    public void insertTest() {
        Arrays.sort(numbers);
        assertEquals(Arrays.toString(numbers), filledTree.toString());
    }

    @Test
    public void treeRebalanceItselfTest() {
        emptyIntTree.insert(9);
        emptyIntTree.insert(6);
        emptyIntTree.insert(6);
        emptyIntTree.insert(20);
        emptyIntTree.insert(21);
        emptyIntTree.insert(13);
        emptyIntTree.insert(15);
        emptyIntTree.insert(8);
        emptyIntTree.insert(17);
        emptyIntTree.insert(24);
        emptyIntTree.insert(2);
        emptyIntTree.insert(26);
        emptyIntTree.insert(18);
        emptyIntTree.insert(23);

        final String expected = emptyIntTree.postOrderToString();
        assertEquals(
                expected,
                "[2, 6, 8, 9, 6, 15, 18, 17, 13, 21, 23, 26, 24, 20]"
        );
    }

    @Test
    public void deleteMinTest() {
        Arrays.sort(numbers);
        for (final Integer number : numbers) {
            assertEquals(number, filledTree.deleteMin());
        }
        assertTrue(filledTree.isEmpty());
        assertEquals(0, filledTree.size());
    }

    @Test
    public void deleteMaxTest() {
        Arrays.sort(numbers, Comparator.reverseOrder());
        for (final Integer number : numbers) {
            assertEquals(number, filledTree.deleteMax());
        }
        assertTrue(filledTree.isEmpty());
        assertEquals(0, filledTree.size());
    }

    @Test
    public void deleteTest() {
        assertNull(filledTree.delete(2_000));
        assertNull(filledTree.delete(0));
        assertEquals((Integer) 750, filledTree.delete(750));
        assertEquals((Integer) 570, filledTree.delete(570));
        assertEquals((Integer) 490, filledTree.delete(490));
        assertEquals(12, filledTree.size());
        assertEquals(
                "[50, 300, 410, 520, 563, 579, 631, 820, 864, 890, 914, 1024]",
                filledTree.toString()
        );
    }

    @Test
    public void itemsTest() {
        final Queue<Integer> queue = (Queue<Integer>) filledTree.items();
        assertEquals(15, queue.size());
        Arrays.sort(numbers);
        int i = 0;
        for (final Integer number : queue) {
            assertEquals(number, numbers[i++]);
        }
    }

    @Test
    public void itemsInRangeTest() {
        final Queue<Integer> queue = (Queue<Integer>) filledTree.items(480, 700);
        assertEquals(6, queue.size());
        Arrays.sort(numbers);
        int i = 3;
        for (final Integer number : queue) {
            assertEquals(number, numbers[i++]);
        }
    }

    @Test
    public void itemsInRangeWhereEndOfRangeIsSmallerThanMinItemTest() {
        final Queue<Integer> queue = (Queue<Integer>) filledTree.items(-20, -10);
        assertTrue(queue.isEmpty());
    }

    @Test
    public void itemsInRangeWhereStartOfRangeIsGreaterThanMaxItemTest() {
        final Queue<Integer> queue = (Queue<Integer>) filledTree.items(2_000, 5_000);
        assertTrue(queue.isEmpty());
    }

    @Test
    public void itemsInRangeWithOneMinItemTest() {
        final Queue<Integer> queue = (Queue<Integer>) filledTree.items(50, 50);
        assertTrue(queue.size() == 1);
        assertEquals((Integer) 50, queue.dequeue());
    }

    @Test
    public void itemsInRangeWithOneMaxItemTest() {
        final Queue<Integer> queue = (Queue<Integer>) filledTree.items(1_024, 1_024);
        assertTrue(queue.size() == 1);
        assertEquals((Integer) 1_024, queue.dequeue());
    }
}