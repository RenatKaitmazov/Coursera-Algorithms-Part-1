package lz.renatkaitmazov.algorithms.week1;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.lang.reflect.Field;

import static org.junit.Assert.*;

/**
 * @author Renat Kaitmazov
 */

@RunWith(JUnit4.class)
public final class WeightedQuickUnionTest {

    private ConnectedComponent quickUnion;
    private final int numberOfComponents = 10;

    @Before
    public final void setUp() {
        quickUnion = new WeightedQuickUnion(numberOfComponents);
    }

    @Test
    public final void initWeightsTest() throws Exception {
        final WeightedQuickUnion union = (WeightedQuickUnion) quickUnion;
        @SuppressWarnings("unckecked")
        final Class<WeightedQuickUnion> clazz = (Class<WeightedQuickUnion>) union.getClass();
        final Field weightsField = clazz.getDeclaredField("weights");
        weightsField.setAccessible(true);
        final int[] weights = (int[]) weightsField.get(union);
        for (final int weight : weights) {
            assertEquals(1, weight);
        }
    }

    @Test
    public final void unionTest() throws Exception {
        assertTrue(quickUnion.union(4, 3));
        assertTrue(quickUnion.union(3, 8));
        assertTrue(quickUnion.union(6, 5));
        assertTrue(quickUnion.union(9, 4));
        assertTrue(quickUnion.union(2, 1));
        assertArrayEquals(
                new int[]{0, 2, 2, 4, 4, 6, 6, 7, 4, 4},
                quickUnion.elements
        );

        assertEquals(5, quickUnion.components());

        assertTrue(quickUnion.union(5, 0));
        assertTrue(quickUnion.union(7, 2));
        assertTrue(quickUnion.union(6, 1));

        assertEquals(2, quickUnion.components());

        // Already connected.
        assertFalse(quickUnion.union(2, 6));

        assertTrue(quickUnion.union(0, 9));
        assertEquals(1, quickUnion.components());

        final WeightedQuickUnion union = (WeightedQuickUnion) quickUnion;
        @SuppressWarnings("unchecked")
        final Class<WeightedQuickUnion> clazz = (Class<WeightedQuickUnion>) union.getClass();
        final Field weightsField = clazz.getDeclaredField("weights");
        weightsField.setAccessible(true);
        final int[] weights = (int[]) weightsField.get(union);
        // The ultimate root is 6. Its weight must be 10 (contains all the elements)
        assertEquals(10, weights[6]);

        assertArrayEquals(
                new int[]{6, 2, 6, 4, 6, 6, 6, 2, 4, 4},
                quickUnion.elements
        );
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