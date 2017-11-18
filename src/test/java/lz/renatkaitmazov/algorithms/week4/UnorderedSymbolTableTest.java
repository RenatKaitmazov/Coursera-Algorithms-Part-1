package lz.renatkaitmazov.algorithms.week4;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

/**
 * @author Renat Kaitmazov
 */

@RunWith(JUnit4.class)
public final class UnorderedSymbolTableTest {

    private UnorderedSymbolTable<String, String> phoneBook;

    @Before
    public void setUp() {
        phoneBook = new UnorderedSymbolTable<>();
    }

    @After
    public void tearDown() {
        phoneBook = null;
    }

    @Test
    public void initialSizeTest() {
        assertEquals(0, phoneBook.size());
    }

    @Test
    public void initiallyEmptyTest() {
        assertTrue(phoneBook.isEmpty());
    }

    @Test
    public void containsTest() {
        phoneBook.put("Renat", "8-999-111-11-11");
        phoneBook.put("Alex", "8-888-111-00-00");
        assertTrue(phoneBook.contains("Renat"));
        assertTrue(phoneBook.contains("Alex"));
    }

    @Test
    public void putTest() {
        phoneBook.put("John", "+7 (945) 457-89-12");
        phoneBook.put("Sue", "123456789");
        phoneBook.put("Vanessa", "65473651");
        phoneBook.put("Axel", "09173719");
        assertFalse(phoneBook.isEmpty());
        assertEquals(4, phoneBook.size());
        final String actual = "[{Axel:09173719}, {Vanessa:65473651}, {Sue:123456789}, {John:+7 (945) 457-89-12}]";
        assertEquals(actual, phoneBook.toString());
    }

    @Test
    public void getTest() {
        phoneBook.put("John", "+7 (945) 457-89-12");
        phoneBook.put("Sue", "123456789");
        phoneBook.put("Vanessa", "65473651");
        phoneBook.put("Axel", "09173719");
        assertEquals("65473651", phoneBook.get("Vanessa"));
        assertEquals("09173719", phoneBook.get("Axel"));
        assertEquals("+7 (945) 457-89-12", phoneBook.get("John"));
        assertEquals("123456789", phoneBook.get("Sue"));
    }

    @Test
    public void removeTest() {
        phoneBook.put("John", "+7 (945) 457-89-12");
        phoneBook.put("Sue", "123456789");
        phoneBook.put("Vanessa", "65473651");
        phoneBook.put("Axel", "09173719");

        assertEquals("123456789", phoneBook.remove("Sue"));
        assertEquals(3, phoneBook.size());
        final String actual = "[{Axel:09173719}, {Vanessa:65473651}, {John:+7 (945) 457-89-12}]";
        assertEquals(actual, phoneBook.toString());
        assertEquals("09173719", phoneBook.remove("Axel"));
        assertEquals("65473651", phoneBook.remove("Vanessa"));
        assertEquals("+7 (945) 457-89-12", phoneBook.remove("John"));
        assertTrue(phoneBook.isEmpty());
        assertEquals("[]", phoneBook.toString());
    }

    @Test
    public void removeTheOnlyValue() {
        phoneBook.put("Jojo", "2424");
        assertEquals("2424", phoneBook.remove("Jojo"));
        assertEquals(0, phoneBook.size());
        assertTrue(phoneBook.isEmpty());
    }

    @Test
    public void iterableKeysTest() {
        phoneBook.put("John", "+7 (945) 457-89-12");
        phoneBook.put("Sue", "123456789");
        phoneBook.put("Vanessa", "65473651");
        phoneBook.put("Axel", "09173719");

        final List<String> expected = new ArrayList<>();
        expected.add("Axel");
        expected.add("Vanessa");
        expected.add("Sue");
        expected.add("John");

        final Iterable<String> keys = phoneBook.keys();
        int i = 0;
        for (final String key : keys) {
            assertEquals(expected.get(i++), key);
        }
    }

    @Test
    public void iterableTest() {
        phoneBook.put("1", "One");
        phoneBook.put("2", "Two");
        phoneBook.put("3", "Three");

        String value = "";
        for (final Entry<String, String> entry : phoneBook) {
            value += entry;
        }
        assertEquals("{3:Three}{2:Two}{1:One}", value);
    }

    @Test
    public void updateOldValueTest() {
        phoneBook.put("Memphis", "123-12");
        phoneBook.put("Memphis", "665-23");
        assertEquals(1, phoneBook.size());
        assertEquals("665-23", phoneBook.get("Memphis"));
    }
}