package lz.renatkaitmazov.algorithms.week3.homework;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

/**
 * @author Renat Kaitmazov
 */

public final class FastCollinearPoints {

    private final LineSegment[] segments;
    private final int numberOfSegments;

    public FastCollinearPoints(Point[] points) {
        validateNotNull(points);
        final int size = points.length;
        final Point[] copy = new Point[size];
        System.arraycopy(points, 0, copy, 0, size);
        validateInput(copy);
        segments = findLineSegments(copy);
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

    private static LineSegment[] findLineSegments(Point[] pointsByY) {
        final List<LineSegment> segmentList = new LinkedList<>();
        final int size = pointsByY.length;
        final Point[] pointsBySlope = new Point[size];
        Point lastPoint = null;
        for (int a = 0; a < size; ++a) {
            final int newSize = size - a - 1;
            if (newSize < 3) break;
            System.arraycopy(pointsByY, a + 1, pointsBySlope, 0, newSize);
            final Point pivot = pointsByY[a];
            Arrays.sort(pointsBySlope, 0, newSize, pivot.slopeOrder());

            final Predicate equal = ((lhs, rhs) -> pivot.slopeTo(lhs) == pivot.slopeTo(rhs));
            final int start = calculateIndex(pointsBySlope, 0, newSize, equal);
            if (start < 0) continue;

            final Predicate notEqual = ((lhs, rhs) -> pivot.slopeTo(lhs) != pivot.slopeTo(rhs));
            int end = calculateIndex(pointsBySlope, start + 1, newSize, notEqual);
            end = (end < 0) ? newSize - 1 : end;

            final int pointsOnLine = end - start + 1;
            if (pointsOnLine > 2) {
                final Point endPoint = pointsBySlope[end];
                if (lastPoint == endPoint) continue;
                lastPoint = endPoint;
                final LineSegment lineSegment = new LineSegment(pivot, endPoint);
                segmentList.add(lineSegment);
            }
        }
        return lineSegmentListToArray(segmentList);
    }

    private interface Predicate {
        boolean check(Point lhs, Point rhs);
    }

    private static int calculateIndex(Point[] points, int start, int size, Predicate predicate) {
        for (int i = start; i < size - 1; ++i) {
            final Point lhs = points[i];
            final Point rhs = points[i + 1];
            if (predicate.check(lhs, rhs)) {
                return i;
            }
        }
        return -1;
    }

    private static LineSegment[] lineSegmentListToArray(List<LineSegment> list) {
        final LineSegment[] array = new LineSegment[list.size()];
        int i = 0;
        for (final LineSegment lineSegment : list) {
            array[i++] = lineSegment;
        }
        return array;
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
