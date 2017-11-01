package lz.renatkaitmazov.algorithms.connectivity;

/**
 * A concrete implementation to solve dynamic connectivity problem.
 * In the worst case scenario both operations can take linear time.
 * But on average the union operation is faster than quick find's
 * union operation.
 * Interprets connected components as a tree of elements where
 * each array entry holds a reference to its immediate root.
 * If the array entry and the element it holds are the same, it means
 * that the element is the ultimate root of the current tree.
 *
 * @author Renat Kaitmazov
 */

public final class QuickUnion extends Union {

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
    final boolean connect(int id1, int id2) {
        // Make the second tree a subtree of the first tree.
        elements[id2] = id1;
        return true;
    }
}
