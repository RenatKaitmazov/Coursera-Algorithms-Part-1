package lz.renatkaitmazov.algorithms.week4;

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
public final class OrderedSymbolTableTest {

    private OrderedSymbolTable<Integer, String> symbolTable;

    @Before
    public void setUp() {
        symbolTable = new OrderedSymbolTable<>();
    }

    @After
    public void tearDown() {
        symbolTable = null;
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

}