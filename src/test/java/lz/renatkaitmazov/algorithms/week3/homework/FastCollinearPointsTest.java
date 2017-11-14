package lz.renatkaitmazov.algorithms.week3.homework;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.io.*;
import java.util.Scanner;

import static org.junit.Assert.*;

/**
 * @author Renat Kaitmazov
 */

@RunWith(JUnit4.class)
public final class FastCollinearPointsTest {

    private final String path = "src/test/java/lz/renatkaitmazov/algorithms/week3/homework/";

    @Test
    public void findPointsTest() {
        final Point[] points = {
                new Point(15, 15),
                new Point(1, 1),
                new Point(6, 3),
                new Point(9, 9),
                new Point(4, 4),
                new Point(8, 2),
                new Point(50, 50),
                new Point(4, 30)
        };

        final FastCollinearPoints collinearPoints = new FastCollinearPoints(points);
        assertEquals(1, collinearPoints.numberOfSegments());
        assertEquals("(1, 1) -> (50, 50)", collinearPoints.segments()[0].toString());
    }

    @Test
    public void input8Test() {
        final String fileName = "input8.txt";
        final Point[] points = readFromFile(path + fileName);
        final FastCollinearPoints collinearPoints = new FastCollinearPoints(points);
        final LineSegment[] segments = collinearPoints.segments();
        assertEquals(2, collinearPoints.numberOfSegments());
    }

    @Test
    public void inputEquidistantTest() {
        final String fileName = "equidistant.txt";
        final Point[] points = readFromFile(path + fileName);
        final FastCollinearPoints collinearPoints = new FastCollinearPoints(points);
        final LineSegment[] segments = collinearPoints.segments();
        assertEquals(4, collinearPoints.numberOfSegments());
    }

    private Point[] readFromFile(String filePath) {
        final File file = new File(filePath);
        try {
            final FileInputStream fileStream = new FileInputStream(file);
            final InputStreamReader inputStream = new InputStreamReader(fileStream);
            final BufferedReader reader = new BufferedReader(inputStream);
            final Scanner scanner = new Scanner(reader);
            try (scanner) {
                final int size = scanner.nextInt();
                final Point[] points = new Point[size];
                for (int i = 0; i < size; ++i) {
                    final int x = scanner.nextInt();
                    final int y = scanner.nextInt();
                    final Point point = new Point(x, y);
                    points[i] = point;
                }
                return points;
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }
}