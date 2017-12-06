package lz.renatkaitmazov.algorithms.week6;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

import static org.junit.Assert.*;

/**
 * @author Renat Kaitmazov
 */

@RunWith(JUnit4.class)
public final class SeparateChainingMapTest {

    private final int capacity = 5;
    private SeparateChainingMap<String, Integer> map;

    @Before
    public void setUp() {
        map = new SeparateChainingMap<>(capacity);
    }

    @After
    public void tearDown() {
        map = null;
    }

    @Test
    public void addTest() {
        map.put("One", 1);
        map.put("Two", 2);
        map.put("Three", 3);
        map.put("Four", 4);
        assertEquals(4, map.size());
    }

    @Test
    public void noDuplicatesTest() {
        map.put("One", 1);
        map.put("One", 1);
        map.put("One", 1);
        map.put("One", 1);
        assertEquals(1, map.size());
        assertEquals(5, map.getCapacity());
    }

    @Test
    public void emptyMapReturnsNullWhenCallingGetTest() {
        assertNull(map.get("One"));
    }

    @Test
    public void getTest() {
        map.put("public", 1);
        map.put("static", 2);
        map.put("void", 3);
        map.put("main", 4);
        map.put("String[]", 5);
        map.put("args", 6);
        map.put("public", -1);
        assertEquals(6, map.size());
        assertEquals(10, map.getCapacity());
        assertEquals(Integer.valueOf(-1), map.get("public"));
        assertNull(map.get("One"));
        assertEquals(Integer.valueOf(2), map.get("static"));
        assertEquals(Integer.valueOf(3), map.get("void"));
        assertEquals(Integer.valueOf(4), map.get("main"));
        assertEquals(Integer.valueOf(5), map.get("String[]"));
        assertEquals(Integer.valueOf(6), map.get("args"));
    }

    @Test
    public void keysTest() {
        map.put("Rust", 1);
        map.put("Kotlin", 2);
        map.put("Java", 3);
        map.put("Scala", 4);
        map.put("Swift", 5);

        final List<String> expected = Arrays.asList("Java", "Kotlin", "Rust", "Scala", "Swift");
        final List<String> actual = (List<String>) map.keys();
        assertNotNull(actual);
        actual.sort(Comparator.naturalOrder());
        assertEquals(expected, actual);
    }

    @Test
    public void removeTest() {
        map.put("Android", 1);
        map.put("Android", null);
        assertTrue(map.isEmpty());
        assertEquals(2, map.getCapacity());

        map.put("iOS", 2);
        map.put("macOSX", 3); // Capacity = 4
        map.put("Windows", 4); // Capacity = 8
        map.put("Linux", 5);
        map.put("Symbian", 6);
        assertEquals(8, map.getCapacity());

        assertNull(map.remove("FreeBSD"));

        assertEquals(Integer.valueOf(3), map.remove("macOSX"));
        assertEquals(Integer.valueOf(6), map.remove("Symbian"));
        assertEquals(Integer.valueOf(2), map.remove("iOS")); // Capacity = 4
        assertEquals(Integer.valueOf(4), map.remove("Windows"));
        assertEquals(Integer.valueOf(5), map.remove("Linux"));
        assertTrue(map.isEmpty());
        assertEquals(1, map.getCapacity());
    }
}