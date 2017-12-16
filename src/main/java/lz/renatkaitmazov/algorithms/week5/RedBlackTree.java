package lz.renatkaitmazov.algorithms.week5;

import lz.renatkaitmazov.algorithms.week2.LinkedQueue;
import lz.renatkaitmazov.algorithms.week2.Queue;
import lz.renatkaitmazov.algorithms.week4.SearchTree;

import java.util.Comparator;

import static lz.renatkaitmazov.algorithms.week5.RedBlackTree.Node.BLACK;
import static lz.renatkaitmazov.algorithms.week5.RedBlackTree.Node.RED;

/**
 * A concrete implementation of the search tree.
 * Guarantees a logarithmic worst case performance by
 * automatically balancing itself.
 *
 * @author Renat Kaitmazov
 */

public final class RedBlackTree<T extends Comparable<T>> implements SearchTree<T> {

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

    public RedBlackTree() {
        this(Comparator.naturalOrder());
    }

    public RedBlackTree(Comparator<T> comparator) {
        this.comparator = comparator;
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
    public void insert(T item) {
        validateNotNull(item);
        root = insert(root, item);
        root.color = BLACK;
    }

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
    public T deleteMin() {
        return null;
    }

    @Override
    public T deleteMax() {
        return null;
    }

    @Override
    public T delete(T item) {
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

    private static boolean isRed(Node x) {
        return x != null && x.color == RED;
    }

    private static <T extends Comparable<T>> Node<T> rotateLeft(Node<T> node) {
        final Node<T> rightChild = node.right;
        node.right = rightChild.left;
        rightChild.left = node;
        rightChild.color = node.color;
        node.color = RED;
        rightChild.size = node.size;
        node.size = 1 + size(node.left) + size(node.right);
        return rightChild;
    }

    private static <T extends Comparable<T>> Node<T> rotateRight(Node<T> node) {
        final Node<T> leftChild = node.left;
        node.left = leftChild.right;
        leftChild.right = node;
        leftChild.color = node.color;
        node.color = RED;
        leftChild.size = node.size;
        node.size = 1 + size(node.left) + size(node.right);
        return leftChild;
    }

    private static <T extends Comparable<T>> void flipColors(Node<T> node) {
        node.color = RED;
        node.left.color = BLACK;
        node.right.color = BLACK;
    }

    private static <T extends Comparable<T>> int size(Node<T> node) {
        return node != null ? node.size : 0;
    }

    private void validateNotNull(T item) {
        if (item == null) {
            throw new IllegalArgumentException("Item cannot be null");
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
        if (node == null) return new Node<>(item, 1, RED);
        final int compareResult = comparator.compare(item, node.item);
        if (compareResult < 0) node.left = insert(node.left, item);
        else node.right = insert(node.right, item);
        // Red nodes must lean to the left, rotate the node to the left to keep the invariant.
        if (isRed(node.right) && !isRed(node.left)) node = rotateLeft(node);
        // Two consecutive red nodes are not allowed. Rotate the node to the right to keep the invariant.
        if (isRed(node.left) && isRed(node.left.left)) node = rotateRight(node);
        // If both children are red, flip their color with the node.
        if (isRed(node.left) && isRed(node.right)) flipColors(node);
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

    /*--------------------------------------------------------*/
    /* Testing
    /*--------------------------------------------------------*/

    String postOrderToString() {
        if (isEmpty()) return "[]";
        final StringBuilder builder = new StringBuilder("[");
        postOrderTraversal(root, builder);
        final int end = builder.length();
        final int start = end - 2;
        return builder.replace(start, end, "]").toString();
    }

    private void postOrderTraversal(Node<T> node, StringBuilder builder) {
        if (node == null) return;
        postOrderTraversal(node.left, builder);
        postOrderTraversal(node.right, builder);
        builder.append(node).append(", ");
    }

    /*--------------------------------------------------------*/
    /* Nested classes
    /*--------------------------------------------------------*/

    static final class Node<T extends Comparable<T>> {

        static final boolean RED = true;
        static final boolean BLACK = false;

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

        /**
         * The color of the node. Either red or black.
         */
        private boolean color;

        Node(T item, int size, boolean color) {
            this.item = item;
            this.size = size;
            this.color = color;
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
            @SuppressWarnings("unchecked")
            final Node<T> that = (Node<T>) o;
            return this.item.equals(that.item);
        }

        @Override
        public int hashCode() {
            return item.hashCode();
        }
    }
}
