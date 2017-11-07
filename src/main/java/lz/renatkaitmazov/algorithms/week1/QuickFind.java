package lz.renatkaitmazov.algorithms.week1;

/**
 * A concrete implementation to solve dynamic week1 problem.
 * Allows the client to check if two elements are connected in
 * a constant time. Not efficient when performing the union command,
 * since it uses the so-called eager approach.
 *
 * @author Renat Kaitmazov
 */

public final class QuickFind extends ConnectedComponent {

    /*--------------------------------------------------------*/
    /* Constructors
    /*--------------------------------------------------------*/

    public QuickFind(int numberOfComponents) {
        super(numberOfComponents);
    }

    /*--------------------------------------------------------*/
    /* ConnectedComponent implementation
    /*--------------------------------------------------------*/

    @Override
    final boolean connect(int id1, int id2) {
        // We assign the id of the first element to
        // the id of the second element.
        // It takes time proportional to the amount of elements.
        // Not an efficient algorithm.
        final int size = elements.length;
        for (int i = 0; i < size; ++i) {
            if (elements[i] == id2) {
                // Current element's id is the same as the id of the second element.
                // Change it to the id of the first element.
                elements[i] = id1;
            }
        }
        return true;
    }

    @Override
    final int findId(int element) {
        return elements[element];
    }
}
