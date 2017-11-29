package lz.renatkaitmazov.algorithms.week4;

import lz.renatkaitmazov.algorithms.week2.LinkedQueue;
import lz.renatkaitmazov.algorithms.week2.Queue;

import java.util.Comparator;

/**
 * A concrete implementation of the search tree data type,
 * where each node has two children. The left one is smaller than the parent,
 * and the right one is greater than or equal to the parent.
 * Duplicates are allowed.
 * Null values are not allowed.
 * The tree is not balanced, so in the worst case scenario where items are added
 * in order be it ascending or descending the performance can degrade from logarithmic to linear.
 * <p>
 * Some methods in this class use recursion because it is simpler and more elegant than loops,
 * while other methods use loops when they are as easy to implement as using recursion.
 * Loops make them a little bit more efficient because of not using call stack.
 * </p>
 *
 * @author Renat Kaitmazov
 */

public final class BinarySearchTree<T extends Comparable<T>> implements SearchTree<T> {

    /*--------------------------------------------------------*/
    /* Fields
    /*--------------------------------------------------------*/

    /**
     * A comparator that defines order into which items are put.
     * The ascending order is the default one if the client does not
     * provide his own comparator.
     */
    private final Comparator<T> comparator;

    /**
     * The root of the tree.
     */
    private Node<T> root;

    /*--------------------------------------------------------*/
    /* Constructors
    /*--------------------------------------------------------*/

    public BinarySearchTree(Comparator<T> comparator) {
        this.comparator = comparator;
    }

    public BinarySearchTree() {
        this(Comparator.naturalOrder());
    }

    /*--------------------------------------------------------*/
    /* Object class methods
    /*--------------------------------------------------------*/

    @Override
    public String toString() {
        if (isEmpty()) return "[]";
        final StringBuilder builder = new StringBuilder("[");
        inOrderToString(root, builder);
        final int end = builder.length();
        final int start = end - 2;
        return builder.replace(start, end, "]").toString();
    }

    private void inOrderToString(Node<T> node, StringBuilder builder) {
        if (node != null) {
            inOrderToString(node.left, builder);
            builder.append(node).append(", ");
            inOrderToString(node.right, builder);
        }
    }

    /*--------------------------------------------------------*/
    /* SearchTree implementation
    /*--------------------------------------------------------*/

    @Override
    public int size() {
        return size(root);
    }

    @Override
    public boolean isEmpty() {
        return root == null;
    }

    @Override
    public T get(T item) {
        validateNotNull(item);
        Node<T> node = getNode(item);
        if (node != null) return node.item;
        return null;
    }

    @Override
    public T min() {
        if (isEmpty()) return null;
        return min(root).item;
    }

    @Override
    public T max() {
        if (isEmpty()) return null;
        return max(root).item;
    }

    @Override
    public T floor(T item) {
        validateNotNull(item);
        final Node<T> floor = floor(root, item);
        if (floor != null) {
            return floor.item;
        }
        return null;
    }

    @Override
    public T ceil(T item) {
        validateNotNull(item);
        final Node<T> floor = ceil(root, item);
        if (floor != null) {
            return floor.item;
        }
        return null;
    }

    @Override
    public int rank(T item) {
        validateNotNull(item);
        return rank(root, item);
    }

    @Override
    public T select(int index) {
        validateIndex(index);
        return select(root, index).item;
    }

    @Override
    public int size(T left, T right) {
        validateNotNull(left);
        validateNotNull(right);
        validateRange(left, right);
        final int diff = rank(root, right) - rank(root, left);
        if (contains(right)) {
            return diff + 1;
        }
        return diff;
    }

    @Override
    public void insert(T item) {
        validateNotNull(item);
        root = insert(root, item);
    }

    @Override
    public T deleteMin() {
        if (isEmpty()) return null;
        final Pair<T, Node<T>> pair = deleteMin(root);
        root = pair.node;
        return pair.item;
    }

    @Override
    public T deleteMax() {
        if (isEmpty()) return null;
        final Pair<T, Node<T>> pair = deleteMax(root);
        root = pair.node;
        return pair.item;
    }

    @Override
    public T delete(T item) {
        validateNotNull(item);
        final int size = size();
        root = delete(root, item);
        if (size != size()) {
            return item;
        }
        return null;
    }

    @Override
    public Iterable<T> items() {
        final Queue<T> queue = new LinkedQueue<>();
        inOrderTraversal(root, queue);
        return queue;
    }

    @Override
    public Iterable<T> items(T left, T right) {
        validateNotNull(left);
        validateNotNull(right);
        validateRange(left, right);
        final Queue<T> queue = new LinkedQueue<>();
        inOrderTraversalInRange(root, left, right, queue);
        return queue;
    }

    /*--------------------------------------------------------*/
    /* Helper methods
    /*--------------------------------------------------------*/

    private void validateNotNull(T item) {
        if (item == null) {
            throw new IllegalArgumentException("Null values are not allowed");
        }
    }

    private void validateRange(T left, T right) {
        if (comparator.compare(left, right) > 0) {
            throw new IllegalArgumentException("Start of range is greater than end");
        }
    }

    private void validateIndex(int index) {
        if (index < 0 || index >= size()) {
            throw new IllegalArgumentException("Index is out of bounds");
        }
    }

    private Node<T> insert(Node<T> node, T item) {
        if (node == null) return new Node<>(item, 1); // Found a place to insert a new node.
        final int compareResult = comparator.compare(item, node.item);
        if (compareResult < 0) node.left = insert(node.left, item); // Item must be inserted into the left subtree.
        else node.right = insert(node.right, item); // Item must be inserted into the right subtree.
        node.size = 1 + size(node.left) + size(node.right); // Update the number of children of the node.
        return node;
    }

    private Pair<T, Node<T>> deleteMin(Node<T> node) {
        if (node.left == null) return new Pair<>(node.item, node.right);
        final Pair<T, Node<T>> pair = deleteMin(node.left);
        node.left = pair.node;
        pair.node = node;
        node.size = 1 + size(node.left) + size(node.right);
        return pair;
    }

    private Pair<T, Node<T>> deleteMax(Node<T> node) {
        if (node.right == null) return new Pair<>(node.item, node.left);
        final Pair<T, Node<T>> pair = deleteMax(node.right);
        node.right = pair.node;
        pair.node = node;
        node.size = 1 + size(node.left) + size(node.right);
        return pair;
    }

    /**
     * Uses Hibbard deletion technique.
     */
    private Node<T> delete(Node<T> node, T item) {
        if (node == null) return null;
        final int compareResult = comparator.compare(item, node.item);
        if      (compareResult < 0) node.left = delete(node.left, item);
        else if (compareResult > 0) node.right = delete(node.right, item);
        else {
            // Found the node to delete.
            // The node possibly has only a left child.
            if (node.right == null) return node.left;
            // The node possibly has only a right child.
            if (node.left == null) return node.right;

            // The node has two children.
            final Node<T> nodeToDelete = node;
            // Find a successor. The smallest node in the right subtree of the node to be deleted.
            node = min(nodeToDelete.right);
            // Make the parent of the successor be its right child.
            node.right = deleteMin(nodeToDelete.right).node;
            // Make the left child of the node to be deleted be the left child of the successor.
            node.left = nodeToDelete.left;
        }
        node.size = 1 + size(node.left) + size(node.right);
        return node;
    }

    private Node<T> getNode(T item) {
        Node<T> current = root;
        while (current != null) {
            final int compareResult = comparator.compare(item, current.item);
            if (compareResult < 0) current = current.left;
            else if (compareResult > 0) current = current.right;
            else return current;
        }
        return null;
    }

    private int size(Node<T> node) {
        if (node != null) return node.size;
        return 0;
    }

    /**
     * Returns the node containing the smallest item.
     * Call this method only if the tree is not empty!
     *
     * @return node with the smallest item.
     */
    private Node<T> min(Node<T> node) {
        Node<T> current = node;
        while (current.left != null) current = current.left;
        return current;
    }

    /**
     * Returns the node containing the largest item.
     * Call this method only if the tree is not empty!
     *
     * @return node with the largest item.
     */
    private Node<T> max(Node<T> node) {
        Node<T> current = node;
        while (current.right != null) current = current.right;
        return current;
    }

    private Node<T> floor(Node<T> node, T item) {
        if (node == null) return null;
        final int compareResult = comparator.compare(item, node.item);
        // Found the item in the tree. Immediately return it.
        if (compareResult == 0) return node;
        // If the given item is smaller than the item of the root of the current subtree, then the floor of the item
        // must be in the left subtree.
        if (compareResult < 0) return floor(node.left, item);

        // Otherwise the floor of the item could be in the right subtree.
        // But only if there is an item in the right subtree which is smaller than or equal to the item in the
        // right subtree.
        final Node<T> possibleFloor = floor(node.right, item);
        if (possibleFloor != null) {
            return possibleFloor;
        }
        return node;
    }

    private Node<T> ceil(Node<T> node, T item) {
        if (node == null) return null;
        final int compareResult = comparator.compare(item, node.item);
        // Found the item in the tree. Immediately return it.
        if (compareResult == 0) return node;
        // If the given item is greater than the item of the root of the current subtree, then the ceil of the item
        // must be in the right subtree.
        if (compareResult > 0) return ceil(node.right, item);

        // Otherwise the ceil of the item could be in the left subtree.
        // But only if there is an item in the left subtree which is larger than or equal to the item in the
        // left subtree.
        final Node<T> possibleCeil = ceil(node.left, item);
        if (possibleCeil != null) {
            return possibleCeil;
        }
        return node;
    }

    private int rank(Node<T> node, T item) {
        if (node == null) return 0;
        final int compareResult = comparator.compare(item, node.item);
        final int rank = size(node.left);
        if (compareResult < 0) return rank(node.left, item);
        if (compareResult > 0) return 1 + rank + rank(node.right, item);
        return rank;
    }

    private Node<T> select(Node<T> node, int index) {
        final int nodeIndex = size(node.left);
        if (index < nodeIndex) return select(node.left, index);
        if (index > nodeIndex) return select(node.right, index - nodeIndex - 1);
        return node;
    }

    private void inOrderTraversal(Node<T> node, Queue<T> queue) {
        if (node != null) {
            inOrderTraversal(node.left, queue);
            queue.enqueue(node.item);
            inOrderTraversal(node.right, queue);
        }
    }

    private void inOrderTraversalInRange(Node<T> node, T left, T right, Queue<T> queue) {
        if (node == null) return;
        final int compareLeft = comparator.compare(left, node.item);
        final int compareRight = comparator.compare(right, node.item);
        if (compareLeft < 0) inOrderTraversalInRange(node.left, left, right, queue);
        if (compareLeft <= 0 && compareRight >= 0) queue.enqueue(node.item);
        if (compareRight > 0) inOrderTraversalInRange(node.right, left, right, queue);
    }

    /*--------------------------------------------------------*/
    /* Nested classes
    /*--------------------------------------------------------*/

    private static final class Node<T extends Comparable<T>> {
        /**
         * The left child of this node.
         * Has to be smaller the this node.
         */
        private Node<T> left;

        /**
         * The right child of this node.
         * Has to be greater than or equal to this node.
         */
        private Node<T> right;

        /**
         * Data stored by this node.
         */
        private T item;

        /**
         * The number of node in a subtree including itself where this node is the root.
         */
        private int size;

        Node(T item, int size) {
            this.item = item;
            this.size = size;
        }

        @Override
        public String toString() {
            return item.toString();
        }

        @Override
        public boolean equals(Object o) {
            if (o == null) return false;
            if (o == this) return true;
            if (o.getClass() != this.getClass()) return false;
            @SuppressWarnings("unchecked") final Node<T> that = (Node<T>) o;
            return this.item.equals(that.item);
        }

        @Override
        public int hashCode() {
            return item.hashCode();
        }
    }

    private static final class Pair<T1, T2> {
        private final T1 item;
        private T2 node;

        Pair(T1 item,T2 node) {
            this.item = item;
            this.node = node;
        }
    }
}
