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
public final class ConnectedComponentTest {

    private ConnectedComponent component;
    private final int numberOfComponents = 5;

    @Before
    public final void setUp() {
        component = new ConnectedComponent(numberOfComponents) {
            @Override
            final boolean connect(int id1, int id2) {
                return false;
            }

            @Override
            final int findId(int element) {
                return 0;
            }
        };
    }

    @Test
    public final void initializationTest() {
        final int[] expectedElements = {0, 1, 2, 3, 4};
        assertEquals(numberOfComponents, component.components());
        assertArrayEquals(expectedElements, component.elements());
    }

    @Test
    public final void returnCopyOfElementsTest() {
        final int[] original = component.elements;
        final int[] copy = component.elements();
        assertFalse(original == copy);
        assertArrayEquals(original, copy);
    }

    @Test
    public final void wrongIndexShouldFailTest() {
        // For union
        checkFailed(() -> component.union(-1, 3));
        checkFailed(() -> component.union(0, 7));
        checkFailed(() -> component.union(-1, 10));

        // For connection check
        checkFailed(() -> component.isConnected(-1, 3));
        checkFailed(() -> component.isConnected(0, 7));
        checkFailed(() -> component.isConnected(-1, 10));
    }

    private void checkFailed(Runnable operation) {
        boolean failed = false;
        try {
            operation.run();
        } catch (Throwable error) {
            failed = true;
        }
        assertTrue(failed);
    }

    @After
    public final void tearDown() {
        component = null;
    }
}