package lz.renatkaitmazov.algorithms.week3;

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
public final class SelectTest {

    private final int size = 11;
    private Integer[] numbers;

    @Before
    public void setUp() {
        numbers = new Integer[size];
        numbers[0] = 21;
        numbers[1] = 32;
        numbers[2] = 65;
        numbers[3] = 12;
        numbers[4] = 87;
        numbers[5] = 34;
        numbers[6] = 14;
        numbers[7] = 89;
        numbers[8] = 56;
        numbers[9] = 100;
        numbers[10] = 2;
    }

    @After
    public void tearDown() {
        numbers = null;
    }

    @Test
    public void selectBiggestTest() {
        assertEquals((Integer) 100, Select.select(numbers, numbers.length - 1));
    }

    @Test
    public void selectSmallestTest() {
        assertEquals((Integer) 2, Select.select(numbers, 0));
    }

    @Test
    public void selectKthTest() {
        assertEquals((Integer) 12, Select.select(numbers, 1));
        assertEquals((Integer) 14, Select.select(numbers, 2));
        assertEquals((Integer) 89, Select.select(numbers, numbers.length - 2));
    }
}