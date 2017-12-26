package lz.renatkaitmazov.algorithms.week5.homework;

import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.In;

import java.util.LinkedList;
import java.util.List;

import static java.lang.Math.abs;
import static java.lang.Math.min;

/**
 * @author Renat Kaitmazov
 */

public final class KdTree {

    /*--------------------------------------------------------*/
    /* Fields
    /*--------------------------------------------------------*/

    private Node root;
    private int size;

    /*--------------------------------------------------------*/
    /* API
    /*--------------------------------------------------------*/

    public boolean isEmpty() {
        return root == null;
    }

    public int size() {
        return size;
    }

    public boolean contains(Point2D point) {
        validateNotNull(point);
        Node current = root;
        while (current != null) {
            // Found a node
            if (current.point.equals(point)) return true;
            // Find out where to go: left/bottom or right/top
            final int compareResult = current.compare(point);
            if (compareResult > 0) {
                current = current.smaller;
            } else {
                // If two point are on the same line, by convention, we go to the right/top side.
                current = current.greater;
            }
        }
        return false;
    }

    public void insert(Point2D point) {
        validateNotNull(point);
        final double left = 0.0;
        final double bottom = 0.0;
        final double right = 1.0;
        final double top = 1.0;
        root = insert(root, point, left, bottom, right, top, 0);
    }

    public void draw() {
        draw(root);
    }

    public Iterable<Point2D> range(RectHV rect) {
        validateNotNull(rect);
        final List<Point2D> points = new LinkedList<>();
        range(root, rect, points);
        return points;
    }

    public Point2D nearest(Point2D point) {
        validateNotNull(point);
        if (isEmpty()) return null;
        return nearest(root, point, root, Double.POSITIVE_INFINITY).point;
    }

    /*--------------------------------------------------------*/
    /* Helper
    /*--------------------------------------------------------*/

    private <T> void validateNotNull(T item) {
        if (item == null) {
            throw new IllegalArgumentException("null is not allowed");
        }
    }

    private Node insert(Node node, Point2D point, double left, double bottom, double right, double top, int level) {
        if (node == null) {
            // Found a place to insert a point.
            ++size;
            final RectHV rect = new RectHV(left, bottom, right, top);
            return new Node(point, rect, level);
        }

        final boolean isEvenLevel = node.isEvenLevel();
        final double x = node.point.x();
        final double y = node.point.y();

        // This is a set of points, so we can't insert two identical points.
        if (!node.point.equals(point)) {
            // Find out where to go: left/bottom or right/top
            final int compareResult = node.compare(point);
            if (compareResult > 0) {
                // The point is going to be inserted either in the left or bottom part relative to the current point.
                // Only the right or the top bound might be changed in this path depending on the level evenness.
                right = isEvenLevel ? x : right;
                top = isEvenLevel ? top : y;
                node.smaller = insert(node.smaller, point, left, bottom, right, top, level + 1);
            } else {
                // The point is going to be inserted either in the right or top part relative to the current point.
                // If two point are on the same line, by convention, we go to the right/top side.
                // Only the left or the bottom bound might be changed in this path depending on the level evenness.
                left = isEvenLevel ? x : left;
                bottom = isEvenLevel ? bottom : y;
                node.greater = insert(node.greater, point, left, bottom, right, top, level + 1);
            }
        }
        return node;
    }

    private void draw(Node node) {
        if (node == null) return;
        draw(node.smaller);
        final Point2D point = node.point;
        final RectHV rect = node.rect;
        final double left = rect.xmin();
        final double bottom = rect.ymin();
        final double right = rect.xmax();
        final double top = rect.ymax();
        StdDraw.setPenRadius(0.002);
        if (node.isEvenLevel()) {
            StdDraw.setPenColor(StdDraw.RED);
            final double x = point.x();
            StdDraw.line(x, bottom, x, top);
        } else {
            StdDraw.setPenColor(StdDraw.BLUE);
            final double y = point.y();
            StdDraw.line(left, y, right, y);
        }
        StdDraw.setPenColor(StdDraw.BLACK);
        StdDraw.setPenRadius(0.01);
        StdDraw.point(point.x(), point.y());
        draw(node.greater);
    }

    private void range(Node node, RectHV rect, List<Point2D> pointsInRect) {
        if (node == null) return;
        final Point2D point = node.point;
        if (rect.contains(point)) pointsInRect.add(point);

        // At even levels we either go to the left or right.
        // At odd levels we either go to the bottom or top.
        final boolean isEvenLevel = node.isEvenLevel();
        final double x = point.x();
        final double y = point.y();

        final double left = rect.xmin();
        final double bottom = rect.ymin();
        // The left side of the rectangle must be to the left of the splitting line.
        final boolean shouldScanLeftPart = isEvenLevel && left < x;
        // The bottom side of the rectangle must be below the splitting line.
        final boolean shouldScanBottomPart = !isEvenLevel && bottom < y;
        if (shouldScanLeftPart || shouldScanBottomPart) range(node.smaller, rect, pointsInRect);

        final double right = rect.xmax();
        final double top = rect.ymax();
        // The right side of the rectangle must be to the right of the splitting line.
        final boolean shouldScanRightPart = isEvenLevel && right >= x;
        // The top side of the rectangle must be above the splitting line.
        final boolean shouldScanTopPart = !isEvenLevel && top >= y;
        if (shouldScanRightPart || shouldScanTopPart) range(node.greater, rect, pointsInRect);
    }

    private Node nearest(Node node, Point2D queryPoint, Node nearestNode, double nearestDistance) {
        if (node == null) return nearestNode;

        // Measure the distance between the current point and the query point.
        final double currentDistance = node.point.distanceSquaredTo(queryPoint);
        if (currentDistance < nearestDistance) {
            // Update nearest point and distance.
            nearestNode = node;
            nearestDistance = currentDistance;
        }

        // Go toward the query point as if we are trying to insert it into the tree.
        final Node nextNearestNode = node.nextNearestTo(queryPoint);
        // Find the nearest point in that subtree.
        nearestNode = nearest(nextNearestNode, queryPoint, nearestNode, nearestDistance);

        /*
         * TODO
         * do not compute the distance between the query point and the point in a node
         * if the closest point discovered so far is closer than the distance between
         * the query point and the rectangle corresponding to the node
         */

        final double radius = nearestNode.point.distanceTo(queryPoint);
        if (node.isEvenLevel()) {
            final double queryPointX = queryPoint.x();
            final double leftmostX = queryPointX - radius;
            final double rightmostX = queryPointX + radius;
            if (leftmostX > node.point.x() || rightmostX < node.point.x()) return nearestNode;
        } else {
            final double queryPointY = queryPoint.y();
            final double bottommostY = queryPointY - radius;
            final double topmostY = queryPointY + radius;
            if (bottommostY > node.point.y() || topmostY < node.point.y()) return nearestNode;
        }

        final Node nextChild = node.nextChild(nextNearestNode);
        final Node alternativeNode = nearest(nextChild, queryPoint, nearestNode, nearestDistance);
        return nearestTo(nearestNode, alternativeNode, queryPoint);
    }

    private static Node nearestTo(Node lhs, Node rhs, Point2D queryPoint) {
        if (lhs == rhs) return lhs;
        final double lhsDistance = lhs.point.distanceSquaredTo(queryPoint);
        final double rhsDistance = rhs.point.distanceSquaredTo(queryPoint);
        if (lhsDistance < rhsDistance) return lhs;
        return rhs;
    }

    /*--------------------------------------------------------*/
    /* Nested classes
    /*--------------------------------------------------------*/

    private static final class Node {

        private final Point2D point;
        private final RectHV rect;
        private final int level; // the level at which the node is located inside a kd tree.
        private Node smaller; // either to the left or to the bottom of the current point.
        private Node greater; // either to the right or to the top of the current point.

        Node(Point2D point, RectHV rect, int level) {
            this.point = point;
            this.rect = rect;
            this.level = level;
        }

        int compare(Point2D that) {
            if (isEvenLevel()) {
                if (this.point.x() < that.x()) return -1;
                if (this.point.x() > that.x()) return +1;
            } else {
                if (this.point.y() < that.y()) return -1;
                if (this.point.y() > that.y()) return +1;
            }
            return 0;
        }

        boolean isEvenLevel() {
            return level % 2 == 0;
        }

        Node nextNearestTo(Point2D queryPoint) {
            final boolean isEvenLevel = isEvenLevel();
            final double queryX = queryPoint.x();
            final double queryY = queryPoint.y();
            if (isEvenLevel && queryX < point.x() || !isEvenLevel && queryY < point.y()) return smaller;
            return greater;
        }

        Node nextChild(Node child) {
            if (child == smaller) return greater;
            return smaller;
        }
    }

    public static void main(String[] args) {

        // initialize the two data structures with point from file
        String filename = args[0];
        In in = new In(filename);
        PointSET brute = new PointSET();
        KdTree kdtree = new KdTree();
        while (!in.isEmpty()) {
            double x = in.readDouble();
            double y = in.readDouble();
            Point2D p = new Point2D(x, y);
            kdtree.insert(p);
            brute.insert(p);
        }

        final Point2D queryPoint = new Point2D(0.271484375, 0.36328125);
        System.out.println("Query point: " + queryPoint);
        System.out.println(kdtree.nearest(queryPoint));
        System.out.println(brute.nearest(queryPoint));

        // process nearest neighbor queries
        StdDraw.enableDoubleBuffering();
        while (true) {

            // the location (x, y) of the mouse
            double x = StdDraw.mouseX();
            double y = StdDraw.mouseY();
            Point2D query = new Point2D(x, y);

            if (StdDraw.isMousePressed()) {
                System.out.println(x + " " + y);
            }

            // draw all of the points
            StdDraw.clear();
            StdDraw.setPenColor(StdDraw.BLACK);
            StdDraw.setPenRadius(0.01);
            brute.draw();

            // draw in red the nearest neighbor (using brute-force algorithm)
            StdDraw.setPenRadius(0.03);
            StdDraw.setPenColor(StdDraw.RED);
            brute.nearest(query).draw();
            StdDraw.setPenRadius(0.02);

            // draw in blue the nearest neighbor (using kd-tree algorithm)
            StdDraw.setPenColor(StdDraw.BLUE);
            kdtree.nearest(query).draw();
            StdDraw.show();
            StdDraw.pause(40);
        }
    }
}
