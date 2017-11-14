package lz.renatkaitmazov.algorithms.week3.homework;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

/**
 * @author Renat Kaitmazov
 */

public final class BruteCollinearPoints {

    private final LineSegment[] segments;
    private final int numberOfSegments;

    public BruteCollinearPoints(Point[] points) {
        validateNotNull(points);
        final int size = points.length;
        final Point[] copy = new Point[size];
        System.arraycopy(points, 0, copy, 0, size);
        validateInput(copy);
        segments = findSegments(copy);
        numberOfSegments = segments.length;
    }

    public int numberOfSegments() {
        return numberOfSegments;
    }

    public LineSegment[] segments() {
        final LineSegment[] copy = new LineSegment[numberOfSegments];
        System.arraycopy(segments, 0, copy, 0, numberOfSegments);
        return copy;
    }

    private static LineSegment[] findSegments(Point[] points) {
        final List<LineSegment> buffer = new LinkedList<>();
        final int size = points.length;
        for (int a = 0; a < size; ++a) {
            final Point p1 = points[a];
            for (int b = a + 1; b < size; ++b) {
                final Point p2 = points[b];
                for (int c = b + 1; c < size; ++c) {
                    final Point p3 = points[c];
                    final double slope1 = p1.slopeTo(p2);
                    final double slope2 = p2.slopeTo(p3);
                    if (slope1 != slope2) continue;
                    for (int d = c + 1; d < size; ++d) {
                        final Point p4 = points[d];
                        final double slope3 = p3.slopeTo(p4);
                        if (slope2 == slope3) {
                            final LineSegment lineSegment = new LineSegment(p1, p4);
                            buffer.add(lineSegment);
                        }
                    }
                }
            }
        }
        final LineSegment[] lineSegments = new LineSegment[buffer.size()];
        int i = 0;
        for (final LineSegment lineSegment : buffer) {
            lineSegments[i++] = lineSegment;
        }
        return lineSegments;
    }

    private static void validateInput(Point[] points) {
        for (final Point point : points) {
            validateNotNull(point);
        }
        Arrays.sort(points);
        final int size = points.length;
        for (int i = 1; i < size; ++i) {
            final Point p1 = points[i - 1];
            final Point p2 = points[i];
            if (p1.compareTo(p2) == 0) {
                throw new IllegalArgumentException("equal points");
            }
        }
    }

    private static <T> void validateNotNull(T item) {
        if (item == null) {
            throw new IllegalArgumentException("null is not allowed");
        }
    }
}
