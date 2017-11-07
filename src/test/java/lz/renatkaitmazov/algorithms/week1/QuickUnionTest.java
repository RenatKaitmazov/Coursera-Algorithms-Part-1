package lz.renatkaitmazov.algorithms.week1;

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
public final class QuickUnionTest {

    private ConnectedComponent quickUnion;
    private final int numberOfComponents = 10;

    @Before
    public final void setUp() {
        quickUnion = new QuickUnion(numberOfComponents);
    }

    @Test
    public final void unionTest() {
        assertTrue(quickUnion.union(4, 3));
        assertTrue(quickUnion.union(3, 8));
        assertTrue(quickUnion.union(6, 5));
        assertTrue(quickUnion.union(9, 4));
        assertTrue(quickUnion.union(2, 1));

        assertEquals(5, quickUnion.components());

        assertTrue(quickUnion.union(5, 0));
        assertTrue(quickUnion.union(7, 2));
        assertTrue(quickUnion.union(6, 1));

        assertEquals(2, quickUnion.components());

        // Already connected.
        assertFalse(quickUnion.union(2, 6));

        assertTrue(quickUnion.union(0, 9));
        assertEquals(1, quickUnion.components());
    }

    @Test
    public final void isConnectedTest() {
        quickUnion.union(4, 3);
        quickUnion.union(3, 8);
        quickUnion.union(6, 5);
        quickUnion.union(9, 4);
        quickUnion.union(2, 1);

        assertFalse(quickUnion.isConnected(0, 7));
        assertTrue(quickUnion.isConnected(8, 9));
    }

    @Test
    public final void unionWithItself() {
        // Already connected (Reflexive equivalence relation)
        assertFalse(quickUnion.union(0, 0));
    }

    @After
    public final void tearDown() {
        quickUnion = null;
    }
}