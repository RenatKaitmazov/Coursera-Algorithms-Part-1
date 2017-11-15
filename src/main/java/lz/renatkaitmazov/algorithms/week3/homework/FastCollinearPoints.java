package lz.renatkaitmazov.algorithms.week3.homework;

import java.util.List;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Map;
import java.util.HashMap;
import java.util.Collections;
import java.util.Arrays;

/**
 * @author Renat Kaitmazov
 */

public final class FastCollinearPoints {

    private final List<LineSegment> listOfSegments = new ArrayList<>();
    private final Map<Double, List<Point>> pointsPerSlopeMap = new HashMap<>();
    private final int numberOfSegments;

    public FastCollinearPoints(Point[] points) {
        validateNotNull(points);
        final int size = points.length;
        final Point[] copy = new Point[size];
        System.arraycopy(points, 0, copy, 0, size);
        validateInput(copy);
        findLineSegments(copy, size);
        numberOfSegments = listOfSegments.size();
    }

    private void findLineSegments(Point[] points, int size) {
        for (int i = 0; i < size; ++i) {
            // Pick a point as an origin
            final Point origin = points[i];

            // Copy every point besides the origin into an auxiliary array.
            final int newSize = size - i - 1;
            final Point[] copy = new Point[newSize];
            System.arraycopy(points, i + 1, copy, 0, newSize);

            // Sort them by their slope toward the origin.
            Arrays.sort(copy, 0, newSize, origin.slopeOrder());
            // Keep track of the collinear points to avoid duplicates.
            final List<Point> collinearPoints = new LinkedList<>();
            for (int j = 1; j < newSize; ++j) {
                final Point point1 = copy[j - 1];
                final Point point2 = copy[j];
                final double slope1 = origin.slopeTo(point1);
                final double slope2 = origin.slopeTo(point2);

                // Two points are on the same line with the origin
                if (slope1 == slope2) {
                    if (collinearPoints.isEmpty()) {
                        collinearPoints.add(point1);
                        collinearPoints.add(point2);
                    } else {
                        // To avoid duplication.
                        collinearPoints.add(point2);
                    }

                    // It is possible that all points are on the same line with the origin.
                    // This check deals with that situation.
                    if (j < newSize - 1) {
                        continue;
                    }
                }

                final int collinearPointsCount = collinearPoints.size();
                // There are at least three points.
                if (collinearPointsCount > 2) {
                    // Sort collinear points to check the last point.
                    Collections.sort(collinearPoints);
                    final Point tail = collinearPoints.get(collinearPointsCount - 1);
                    List<Point> tops = pointsPerSlopeMap.get(slope1);
                    if (tops == null) {
                        tops = new LinkedList<>();
                    }
                    // Add a line segment if it hasn't existed so far.
                    if (!tops.contains(tail)) {
                        tops.add(tail);
                        pointsPerSlopeMap.put(slope1, tops);
                        final LineSegment segment = new LineSegment(origin, tail);
                        listOfSegments.add(segment);
                    }
                }
                collinearPoints.clear();
            }
        }
    }

    public int numberOfSegments() {
        return numberOfSegments;
    }

    public LineSegment[] segments() {
        final LineSegment[] copy = new LineSegment[numberOfSegments];
        for (int i = 0; i < numberOfSegments; ++i) {
            copy[i] = listOfSegments.get(i);
        }
        return copy;
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
