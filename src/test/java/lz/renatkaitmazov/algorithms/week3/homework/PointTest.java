package lz.renatkaitmazov.algorithms.week3.homework;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.util.Comparator;

import static org.junit.Assert.*;

/**
 * @author Renat Kaitmazov
 */

@RunWith(JUnit4.class)
public final class PointTest {

    @Test
    public void slopeWithSamePointTest() {
        final Point original = new Point(12, 20);
        final double delta = 0.0;
        assertEquals(Double.NEGATIVE_INFINITY, original.slopeTo(original), delta);
    }

    @Test
    public void verticalLineSlopeTest() {
        final int x = 10;
        final Point point1 = new Point(x, 20);
        final Point point2 = new Point(x, 39);
        final double delta = 0.0;
        assertEquals(Double.POSITIVE_INFINITY, point1.slopeTo(point2), delta);
        assertEquals(Double.POSITIVE_INFINITY, point2.slopeTo(point1), delta);
    }

    @Test
    public void positiveSlopeTest() {
        final double delta = 0.00001;
        final Point point1 = new Point(134, 3617);
        final Point point2 = new Point(52, 1341);
        assertEquals(27.75609, point1.slopeTo(point2), delta);
        assertEquals(27.75609, point2.slopeTo(point1), delta);
    }

    @Test
    public void negativeSlopeTest() {
        final Point point1 = new Point(131, 67);
        final Point point2 = new Point(156, 45);
        final double delta = 0.0001;
        assertEquals(-0.88, point1.slopeTo(point2), delta);
        assertEquals(-0.88, point2.slopeTo(point1), delta);
    }

    @Test
    public void compareToItselfTest() {
        final Point point1 = new Point(23, 12);
        final Point point2 = new Point(23, 12);
        assertEquals(0, point1.compareTo(point2));
        assertEquals(0, point2.compareTo(point1));
    }

    @Test
    public void compareToPointWithSameYCoordinateTest() {
        final int y = 90;
        final Point point1 = new Point(12, y);
        final Point point2 = new Point(45, y);
        assertTrue(point1.compareTo(point2) < 0);
        assertTrue(point2.compareTo(point1) > 0);
    }

    @Test
    public void compareArbitraryPointsTest() {
        final Point point1 = new Point(12, 90);
        final Point point2 = new Point(30, 67);
        assertTrue(point1.compareTo(point2) > 0);
        assertTrue(point2.compareTo(point1) < 0);
    }

    @Test
    public void slopeComparatorTest() {
        final Point origin = new Point(0, 0);
        final Comparator<Point> comparator = origin.slopeOrder();

        final Point point1 = new Point(100, 90);
        final Point point2 = new Point(135, 40);
        assertTrue(comparator.compare(point1, point2) > 0);
        assertTrue(comparator.compare(point2, point1) < 0);
    }
}