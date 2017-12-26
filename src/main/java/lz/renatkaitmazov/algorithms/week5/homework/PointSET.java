package lz.renatkaitmazov.algorithms.week5.homework;

import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;
import edu.princeton.cs.algs4.StdDraw;

import java.util.LinkedList;
import java.util.List;
import java.util.TreeSet;

/**
 * @author Renat Kaitmazov
 */

public final class PointSET {

    /*--------------------------------------------------------*/
    /* Fields
    /*--------------------------------------------------------*/

    private final TreeSet<Point2D> pointsSet = new TreeSet<>();

    /*--------------------------------------------------------*/
    /* API
    /*--------------------------------------------------------*/

    public boolean isEmpty() {
        return pointsSet.isEmpty();
    }

    public int size() {
        return pointsSet.size();
    }

    public boolean contains(Point2D point) {
        return pointsSet.contains(point);
    }

    public void insert(Point2D point) {
        validateNotNull(point);
        pointsSet.add(point);
    }

    public void draw() {
        pointsSet.forEach(this::draw);
    }

    public Iterable<Point2D> range(RectHV rect) {
        validateNotNull(rect);
        final List<Point2D> pointsInRange = new LinkedList<>();
        for (final Point2D point : pointsSet) {
            if (rect.contains(point)) {
                pointsInRange.add(point);
            }
        }
        return pointsInRange;
    }

    public Point2D nearest(Point2D point) {
        validateNotNull(point);
        if (isEmpty()) return null;
        final Object[] points = pointsSet.toArray();
        final int length = points.length;
        Point2D nearestPoint = (Point2D) points[0];
        double nearestDistance = nearestPoint.distanceSquaredTo(point);
        for (int i = 1; i < length; ++i) {
            final Point2D currentPoint = (Point2D) points[i];
            final double currentDistance = currentPoint.distanceSquaredTo(point);
            if (currentDistance < nearestDistance) {
                nearestDistance = currentDistance;
                nearestPoint = currentPoint;
            }
        }
        return nearestPoint;
    }

    /*--------------------------------------------------------*/
    /* Helper methods
    /*--------------------------------------------------------*/

    private <T> void validateNotNull(T item) {
        if (item == null) {
            throw new IllegalArgumentException("null is not allowed");
        }
    }

    private void draw(Point2D point) {
        StdDraw.point(point.x(), point.y());
    }
}
