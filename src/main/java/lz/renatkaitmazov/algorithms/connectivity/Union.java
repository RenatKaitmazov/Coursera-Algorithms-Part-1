package lz.renatkaitmazov.algorithms.connectivity;

/**
 * Should be subclassed by algorithms that interprets connected
 * components as a tree of elements where each array entry holds
 * a reference to its immediate root.
 *
 * @author Renat Kaitmazov
 */

public abstract class Union extends ConnectedComponent {

    /*--------------------------------------------------------*/
    /* Constructors
    /*--------------------------------------------------------*/

    public Union(int numberOfComponents) {
        super(numberOfComponents);
    }

    /*--------------------------------------------------------*/
    /* ConnectedComponent implementation
    /*--------------------------------------------------------*/

    @Override
    int findId(int element) {
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
