package lz.renatkaitmazov.algorithms.connectivity;

/**
 * A concrete implementation to solve dynamic connectivity problem.
 * In the worst case scenario both operations can take linear time.
 * But on average the union operation is faster than quick find's
 * union operation.
 * Here we interpret connected components as a tree of elements where
 * each array entry holds a reference to its immediate root.
 * If the array entry and the element it holds are the same, it means
 * that the element is the ultimate root of the current tree.
 *
 * @author Renat Kaitmazov
 */

public final class QuickUnion extends ConnectedComponent {

    /*--------------------------------------------------------*/
    /* Constructors
    /*--------------------------------------------------------*/

    public QuickUnion(int numberOfComponents) {
        super(numberOfComponents);
    }

    /*--------------------------------------------------------*/
    /* ConnectedComponent implementation
    /*--------------------------------------------------------*/

    @Override
    final boolean connect(int element1, int element2) {
        // Find the ultimate roots of each of these elements.
        final int id1 = findId(element1);
        final int id2 = findId(element2);
        // Make the second tree a subtree of the first tree.
        elements[id2] = id1;
        return true;
    }

    @Override
    final int findId(int element) {
        // We need to find the ultimate root of the given element.
        // We start from that element.
        int currentRoot = element;
        // We go up the tree until the current root is not equal to
        // the element hold at the entry whose index is the same as the current root.
        // If the array entry and its element are the same, we are done.
        while (currentRoot != elements[currentRoot]) {
            currentRoot = elements[currentRoot];
        }
        return currentRoot;
    }
}
