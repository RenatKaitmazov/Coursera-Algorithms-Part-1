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
public final class QuickFindTest {

    private ConnectedComponent quickFind;
    private final int numberOfComponents = 10;

    @Before
    public final void setUp() {
        quickFind = new QuickFind(numberOfComponents);
    }

    @Test
    public final void unionTest() {
        assertTrue(quickFind.union(4, 3));
        assertTrue(quickFind.union(3, 8));
        assertTrue(quickFind.union(6, 5));
        assertTrue(quickFind.union(9, 4));
        assertTrue(quickFind.union(2, 1));

        assertEquals(5, quickFind.components());

        assertTrue(quickFind.union(5, 0));
        assertTrue(quickFind.union(7, 2));
        assertTrue(quickFind.union(6, 1));

        assertEquals(2, quickFind.components());

        // Already connected.
        assertFalse(quickFind.union(2, 6));

        assertTrue(quickFind.union(0, 9));
        assertEquals(1, quickFind.components());
    }

    @Test
    public final void isConnectedTest() {
        quickFind.union(4, 3);
        quickFind.union(3, 8);
        quickFind.union(6, 5);
        quickFind.union(9, 4);
        quickFind.union(2, 1);

        assertFalse(quickFind.isConnected(0, 7));
        assertTrue(quickFind.isConnected(8, 9));
    }

    @Test
    public final void unionWithItself() {
        // Already connected (Reflexive equivalence relation)
        assertFalse(quickFind.union(0, 0));
    }

    @After
    public final void tearDown() {
        quickFind = null;
    }
}