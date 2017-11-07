package lz.renatkaitmazov.algorithms.week2;

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
public final class ArrayStackTest {

    private Stack<Integer> stack;
    private final int capacity = 1;

    @Before
    public final void setUp() {
        stack = new ArrayStack<>(capacity);
    }

    @Test(expected = IllegalArgumentException.class)
    public final void negativeCapacityFailsTest() {
        new ArrayStack<String>(-1);
    }

    @Test
    public final void expandArrayCapacityTest() {
        stack.push(1);
        // Should resize
        stack.push(2);
        // Should resize
        stack.push(3);
        assertEquals(4, getArrayLength((ArrayStack<?>) stack));
    }

    @Test
    public final void notEmptyTest() {
        stack.push(1);
        assertFalse(stack.isEmpty());
    }

    @Test
    public final void emptyTest() {
        assertTrue(stack.isEmpty());
    }

    @Test
    public final void shrinkArrayCapacityTest() {
        for (int i = 0; i < 5; ++i) {
            stack.push(i + 1);
        }
        assertEquals(8, getArrayLength((ArrayStack<?>) stack));
        while (stack.size() != 2) {
            stack.pop();
        }
        assertEquals(4, getArrayLength((ArrayStack<?>) stack));

        stack.pop();
        assertEquals(2, getArrayLength((ArrayStack<?>) stack));

    }

    @Test
    public final void expandAndShrinkCapacityTest() {
        for (int i = 0; i < 5; ++i) {
            stack.push(i + 1);
        }

        while (!stack.isEmpty()) {
            stack.pop();
        }

        assertTrue(stack.isEmpty());
        assertEquals(1, getArrayLength((ArrayStack<?>) stack));
    }

    @Test
    public final void correctSizeTest() {
        stack.push(1);
        stack.push(2);
        stack.push(3);
        assertEquals(3, stack.size());
    }

    @Test
    public final void forEachTest() {
        stack.push(1);
        stack.push(2);
        stack.push(3);
        String string = "";
        for (final Integer item : stack) {
            string += item;
        }
        assertEquals("321", string);
    }

    @Test
    public final void toStringTest() {
        stack.push(10);
        stack.push(20);
        stack.push(45);
        assertEquals("[45, 20, 10]", stack.toString());
    }

    @After
    public final void tearDown() {
        stack = null;
    }

    private int getArrayLength(ArrayStack<?> stack) {
        @SuppressWarnings("unchecked")
        final Class<? extends ArrayStack<?>> clazz = (Class<? extends ArrayStack<?>>) stack.getClass();
        try {
            final Field field = clazz.getDeclaredField("items");
            field.setAccessible(true);
            return ((Object[]) field.get(stack)).length;
        } catch (NoSuchFieldException | IllegalAccessException e) {
            return 0;
        }
    }
}