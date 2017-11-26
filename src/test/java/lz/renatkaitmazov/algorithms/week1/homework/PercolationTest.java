package lz.renatkaitmazov.algorithms.week1.homework;

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
public final class PercolationTest {

    private Percolation percolation;
    private final int size = 4;

    @Before
    public void setUp() {
        percolation = new Percolation(size);
    }

    @After
    public void tearDown() {
        percolation = null;
    }

    @Test
    public void initiallyAllSitesClosedCheck() {
        for (int row = 1; row <= size; ++row) {
            for (int col = 1; col <= size; ++col) {
                assertFalse(percolation.isOpen(row, col));
            }
        }
    }

    @Test(expected = IllegalArgumentException.class)
    public void wrongRowIndexFailsTest() {
        percolation.open(size + 1, size);
    }

    @Test(expected = IllegalArgumentException.class)
    public void wrongColumnIndexFailsTest() {
        percolation.open(size, size + 1);
    }

    @Test
    public void openSiteTest() {
        percolation.open(1, 1);
        assertTrue(percolation.isOpen(1, 1));
        assertEquals(1, percolation.numberOfOpenSites());
    }

    @Test
    public void isSiteFullTest() {
        assertFalse(percolation.isFull(1, 2));
        percolation.open(1, 2);
        assertTrue(percolation.isFull(1, 2));

        assertFalse(percolation.isFull(2, 2));
        percolation.open(2, 2);
        assertTrue(percolation.isFull(2, 2));

        assertFalse(percolation.isFull(size, 1));
        percolation.open(size, 1);
        assertFalse(percolation.isFull(size, 1));

        assertFalse(percolation.isFull(size - 1, 1));
        percolation.open(size - 1, 1);
        assertFalse(percolation.isFull(size - 1, 1));
    }

    @Test
    public void doesNotBackwashTest() {
        percolation.open(4, 1);
        percolation.open(3, 1);
        percolation.open(2, 1);
        assertEquals(3, percolation.numberOfOpenSites());
        assertFalse(percolation.isFull(4, 1));
        assertFalse(percolation.isFull(3, 1));
        assertFalse(percolation.isFull(2, 1));
    }

    @Test
    public void percolatesTest1() {
        percolation.open(4, 4);
        percolation.open(4, 4);
        percolation.open(3, 4);
        percolation.open(2, 4);
        percolation.open(1, 4);
        assertEquals(4, percolation.numberOfOpenSites());
        assertTrue(percolation.percolates());
        assertTrue(percolation.isFull(4, 4));
        assertTrue(percolation.isFull(3, 4));
        assertTrue(percolation.isFull(2, 4));
        assertTrue(percolation.isFull(1, 4));
    }

    @Test
    public void percolatesTest2() {
        percolation.open(1, 1);
        percolation.open(1, 3);
        percolation.open(2, 2);
        percolation.open(2, 4);
        percolation.open(3, 1);
        percolation.open(3, 3);
        percolation.open(4, 2);
        percolation.open(4, 4);
        percolation.open(3, 2);
        assertFalse(percolation.isFull(3, 2));
        percolation.open(2, 3);
        assertTrue(percolation.isFull(3, 2));
        assertTrue(percolation.percolates());
    }
}