package lz.renatkaitmazov.algorithms.week4;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.Random;
import java.util.Scanner;

import static org.junit.Assert.*;
import static lz.renatkaitmazov.algorithms.week2.sort.SortUtil.getSortedIntegerArray;
import static lz.renatkaitmazov.algorithms.week2.sort.SortUtil.swap;

/**
 * @author Renat Kaitmazov
 */

@RunWith(JUnit4.class)
public final class OrderedSymbolTableTest {

    private OrderedSymbolTable<Integer, String> symbolTable;
    private Random random;

    @Before
    public void setUp() {
        symbolTable = new OrderedSymbolTable<>();
        random = new Random(System.nanoTime());
    }

    @After
    public void tearDown() {
        symbolTable = null;
        random = null;
    }

    @Test
    public void initialSizeTest() {
        assertEquals(0, symbolTable.size());
        assertEquals("[]", symbolTable.toString());
    }

    @Test
    public void isInitiallyEmptyTest() {
        assertTrue(symbolTable.isEmpty());
    }

    @Test
    public void putTest() {
        symbolTable.put(100, "one hundred");
        symbolTable.put(23, "twenty three");
        symbolTable.put(50, "fifty");
        symbolTable.put(2, "two");
        symbolTable.put(67, "sixty seven");

        assertEquals(5, symbolTable.size());
        assertEquals(
                "[{2:two}, {23:twenty three}, {50:fifty}, {67:sixty seven}, {100:one hundred}]",
                symbolTable.toString()
        );
    }

    @Test
    public void putOldKeyUpdatesValueTest() {
        symbolTable.put(100, "one hundred");
        symbolTable.put(23, "twenty three");
        symbolTable.put(50, "fifty");
        symbolTable.put(2, "two");
        symbolTable.put(67, "sixty seven");
        symbolTable.put(50, "fifty__");

        assertEquals(5, symbolTable.size());
        assertEquals(
                "[{2:two}, {23:twenty three}, {50:fifty__}, {67:sixty seven}, {100:one hundred}]",
                symbolTable.toString()
        );
    }

    @Test
    public void removeTest() {
        symbolTable.put(501, "five hundred and one");
        symbolTable.put(6, "six");
        symbolTable.put(-12, "negative twelve");
        symbolTable.put(912, "nine hundred and twelve");
        symbolTable.put(81, "eighty one");
        symbolTable.put(343, "three hundred and forty three");
        symbolTable.put(231, "two hundred and thirty one");

        assertEquals(7, symbolTable.size());
        assertEquals("eighty one", symbolTable.remove(81));
        assertEquals("negative twelve", symbolTable.remove(-12));
        assertEquals("three hundred and forty three", symbolTable.remove(343));
        assertEquals(4, symbolTable.size());
        assertEquals(
                "[{6:six}, {231:two hundred and thirty one}, {501:five hundred and one}, {912:nine hundred and twelve}]",
                symbolTable.toString()
        );

        assertNull(symbolTable.remove(1_000));
        assertNull(symbolTable.remove(-100));
        assertEquals("two hundred and thirty one", symbolTable.remove(231));
        assertEquals("nine hundred and twelve", symbolTable.remove(912));
        assertEquals("six", symbolTable.remove(6));
        assertEquals("five hundred and one", symbolTable.remove(501));
        assertEquals(1, symbolTable.capacity());
        assertTrue(symbolTable.isEmpty());
    }

    @Test
    public void getTest() {
        symbolTable.put(5, "five");
        symbolTable.put(7, "seven");
        symbolTable.put(3, "three");
        symbolTable.put(13, "thirteen");
        symbolTable.put(8, "eight");
        symbolTable.put(10, "ten");
        symbolTable.put(1, "one");

        assertEquals(7, symbolTable.size());
        assertEquals("eight", symbolTable.get(8));
        assertEquals("five", symbolTable.get(5));
        assertEquals("seven", symbolTable.get(7));
        assertEquals("thirteen", symbolTable.get(13));
        assertEquals("eight", symbolTable.get(8));
        assertEquals("ten", symbolTable.get(10));
        assertEquals("one", symbolTable.get(1));
        assertNull(symbolTable.get(100));
    }

    @Test
    public void keysTest() {
        symbolTable.put(-5, "negative five");
        symbolTable.put(15, "fifteen");
        symbolTable.put(100, "one hundred");
        symbolTable.put(103, "one hundred and three");
        symbolTable.put(80, "eighty");
        symbolTable.put(71, "seventy one");
        symbolTable.put(149, "one hundred forty nine");

        final Iterable<Integer> keys = symbolTable.keys();
        assertNotNull(keys);
        String string = "";
        for (final Integer key : keys) {
            string += key;
        }
        assertEquals("-5157180100103149", string);
    }

    @Test
    public void rankTest() {
        randomlyFillSymbolTableWithData();
        assertEquals(46, symbolTable.rank(46));
        assertEquals(0, symbolTable.rank(0));
        assertEquals(14, symbolTable.rank(14));

        Integer key = 90;
        assertEquals(key, symbolTable.getKeyAt(symbolTable.rank(key)));
    }

    @Test
    public void getKeyAtTest() {
        randomlyFillSymbolTableWithData();
        assertEquals((Integer) 46, symbolTable.getKeyAt(46));

        final int index = 76;
        assertEquals(index, symbolTable.rank(symbolTable.getKeyAt(76)));
    }

    @Test
    public void minTest() {
        randomlyFillSymbolTableWithData();
        assertEquals((Integer) 0, symbolTable.min());
        symbolTable.remove(0);
        assertEquals((Integer) 1, symbolTable.min());
    }

    @Test
    public void maxTest() {
        randomlyFillSymbolTableWithData();
        assertEquals((Integer) 99, symbolTable.max());
        symbolTable.remove(symbolTable.size() - 1);
        assertEquals((Integer) 98, symbolTable.max());
    }

    @Test
    public void removeMinTest() {
        randomlyFillSymbolTableWithData();
        assertEquals((Integer) 0, symbolTable.removeMin());
        assertEquals((Integer) 1, symbolTable.removeMin());
        assertEquals((Integer) 2, symbolTable.removeMin());
        assertEquals((Integer) 3, symbolTable.removeMin());
        assertEquals((Integer) 4, symbolTable.removeMin());

        while (!symbolTable.isEmpty()) {
            symbolTable.removeMin();
        }
        assertTrue(symbolTable.isEmpty());
        assertEquals(1, symbolTable.capacity());
    }

    @Test
    public void removeMaxTest() {
        randomlyFillSymbolTableWithData();
        assertEquals((Integer) 99, symbolTable.removeMax());
        assertEquals((Integer) 98, symbolTable.removeMax());
        assertEquals((Integer) 97, symbolTable.removeMax());
        assertEquals((Integer) 96, symbolTable.removeMax());
        assertEquals((Integer) 95, symbolTable.removeMax());

        while (!symbolTable.isEmpty()) {
            symbolTable.removeMax();
        }
        assertTrue(symbolTable.isEmpty());
        assertEquals(1, symbolTable.capacity());
    }

    @Test
    public void floor1Test() {
        randomlyFillSymbolTableWithData();
        assertNull(symbolTable.floor(-10));
        assertEquals((Integer) 0, symbolTable.floor(0));
        assertEquals((Integer) 99, symbolTable.floor(1_000));
    }

    @Test
    public void floor2Test() {
        symbolTable.put(12, ".");
        symbolTable.put(34, ".");
        symbolTable.put(67, ".");
        symbolTable.put(109, ".");
        symbolTable.put(456, ".");

        assertEquals((Integer) 12, symbolTable.floor(20));
        assertEquals((Integer) 34, symbolTable.floor(34));
        assertEquals((Integer) 34, symbolTable.floor(50));
        assertEquals((Integer) 67, symbolTable.floor(67));
        assertEquals((Integer) 67, symbolTable.floor(98));
        assertEquals((Integer) 456, symbolTable.floor(1_000_000));
    }

    @Test
    public void ceil1Test() {
        randomlyFillSymbolTableWithData();
        assertNull(symbolTable.ceil(1_000));
        assertEquals((Integer) 0, symbolTable.ceil(-10));
        assertEquals((Integer) 56, symbolTable.ceil(56));
    }

    @Test
    public void ceil2Test() {
        symbolTable.put(89, "=");
        symbolTable.put(167, "=");
        symbolTable.put(526, "=");
        symbolTable.put(810, "=");
        symbolTable.put(1_921, "=");

        assertNull(symbolTable.ceil(2_000));
        assertEquals((Integer) 1_921, symbolTable.ceil(1_921));
        assertEquals((Integer) 1_921, symbolTable.ceil(1_000));
        assertEquals((Integer) 810, symbolTable.ceil(701));
        assertEquals((Integer) 526, symbolTable.ceil(526));
        assertEquals((Integer) 89, symbolTable.ceil(-100));
    }

    @Test
    public void ceil3Test() {
        final OrderedSymbolTable<Integer, String> st = new OrderedSymbolTable<>(2);
        st.put(34, "-");
        st.put(67, "-");
        assertNull(st.ceil(100));
    }

    @Test
    public void frequencyCounterTest() throws Exception {
        final String path = "src/test/java/lz/renatkaitmazov/algorithms/week4/";
        final String name = "tinyTale.txt";

        final File testFile = new File(path + name);
        final FileInputStream fileStream = new FileInputStream(testFile);
        final InputStreamReader inputStreamReader = new InputStreamReader(fileStream);
        final BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
        final Scanner input = new Scanner(bufferedReader);

        try (input) {
            final OrderedSymbolTable<String, Integer> counter = new OrderedSymbolTable<>();
            while (input.hasNext()) {
                final String word = input.next();
                final Integer frequency = counter.get(word);
                if (frequency == null) {
                    counter.put(word, 1);
                } else {
                    counter.put(word, frequency + 1);
                }
            }

            assertEquals(20, counter.size());
            assertEquals((Integer) 10, counter.get("was"));
            assertEquals((Integer) 10, counter.get("it"));
            assertEquals((Integer) 10, counter.get("the"));
            assertEquals((Integer) 10, counter.get("of"));
            assertEquals((Integer) 1, counter.get("spring"));
        }

    }

    @Test
    public void sizeOfRange1Test() {
        randomlyFillSymbolTableWithData();
        assertEquals(100, symbolTable.size(-12, 102));
        assertEquals(50, symbolTable.size(1, 50));
        assertEquals(41, symbolTable.size(50, 90));
        assertEquals(1, symbolTable.size(90, 90));
    }

    @Test
    public void sizeOfRange2Test() {
        symbolTable.put(-15, "-");
        symbolTable.put(1, "-");
        symbolTable.put(19, "-");
        symbolTable.put(34, "-");
        symbolTable.put(78, "-");

        assertEquals(5, symbolTable.size(-100, 90));
        assertEquals(1, symbolTable.size(50, 78));
        assertEquals(1, symbolTable.size(-15, -15));
        assertEquals(1, symbolTable.size(-19, -14));
        assertEquals(0, symbolTable.size(79, 100));
        assertEquals(0, symbolTable.size(-30, -20));
    }

    @Test(expected = IllegalArgumentException.class)
    public void sizeOfRange3Test() {
        assertEquals(0, symbolTable.size(90, 50));
        randomlyFillSymbolTableWithData();
        symbolTable.size(90, 50);
    }

    @Test
    public void keysInRange1Test() {
        assertNull(symbolTable.keys(0, 99));
        randomlyFillSymbolTableWithData();

        assertArrayEquals(
                new Integer[]{99},
                iterableToArray(symbolTable.keys(99, 120), 1)
        );

        assertArrayEquals(
                new Integer[]{0},
                iterableToArray(symbolTable.keys(-10, 0), 1)
        );

        assertArrayEquals(
                new Integer[]{},
                iterableToArray(symbolTable.keys(-10, -6), 0)
        );

        assertArrayEquals(
                new Integer[]{},
                iterableToArray(symbolTable.keys(102, 167), 0)
        );

        assertArrayEquals(
                new Integer[]{0, 1, 2, 3, 4},
                iterableToArray(symbolTable.keys(0, 4), 5)
        );
    }

    @Test
    public void keysInRange2Test() {
        symbolTable.put(10, "+");
        symbolTable.put(15, "+");
        symbolTable.put(29, "+");
        symbolTable.put(47, "+");
        symbolTable.put(89, "+");

        assertArrayEquals(
                new Integer[]{29, 47},
                iterableToArray(symbolTable.keys(16, 88), 2)
        );

        assertArrayEquals(
                new Integer[]{10, 15, 29, 47, 89},
                iterableToArray(symbolTable.keys(-10, 100), 5)
        );
    }

    @Test(expected = IllegalArgumentException.class)
    public void keysInRange3Test() {
        randomlyFillSymbolTableWithData();
        symbolTable.keys(90, 12);
    }

    private void randomlyFillSymbolTableWithData() {
        final int size = 100;
        int upperBound = size;
        final Integer[] numbers = getSortedIntegerArray(size);
        for (int i = size - 1; i > -1; --i) {
            final int randomIndex = random.nextInt(upperBound--);
            final Integer key = numbers[randomIndex];
            symbolTable.put(key, "-");
            swap(numbers, i, randomIndex);
        }
    }

    private <T> T[] iterableToArray(Iterable<T> iterable, int size) {
        @SuppressWarnings("unchecked")
        final T[] array = (T[]) new Object[size];
        int i = 0;
        for (final T item : iterable) {
            array[i++] = item;
        }
        return array;
    }
}