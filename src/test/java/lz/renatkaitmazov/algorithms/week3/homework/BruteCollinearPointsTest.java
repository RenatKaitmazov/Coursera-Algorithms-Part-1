package lz.renatkaitmazov.algorithms.week3.homework;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import static org.junit.Assert.*;

/**
 * @author Renat Kaitmazov
 */

@RunWith(JUnit4.class)
public final class BruteCollinearPointsTest {

    @Test
    public void findPointsTest() {
        final Point[] points = {
                new Point(15, 15),
                new Point(1, 1),
                new Point(6, 3),
                new Point(9, 9),
                new Point(4, 4),
                new Point(50, 50),
                new Point(8, 2),
                new Point(4, 30)
        };

        final BruteCollinearPoints collinearPoints = new BruteCollinearPoints(points);
        assertEquals(5, collinearPoints.numberOfSegments());
    }
}