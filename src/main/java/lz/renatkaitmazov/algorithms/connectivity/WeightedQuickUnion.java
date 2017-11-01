package lz.renatkaitmazov.algorithms.connectivity;

/**
 * A concrete implementation to solve dynamic connectivity problem.
 * Interprets connected components as a tree of elements where
 * each array entry holds a reference to its immediate root.
 * This kind of algorithms allows for fast union operation and
 * checking if two elements are connected. The running time of the
 * algorithm in the worst case scenario is logarithmic.
 * The gain in performance is achieved due to using an auxiliary
 * array that keeps track of weights (the number of elements)
 * of each tree. No bigger tree can be a subtree of a smaller tree.
 * It also uses a path compression technique to make a tree as flat
 * as possible. In real world this small improvement makes its union
 * and check operations perform in almost constant time.
 * The idea here is that when we find the root of the entire tree when
 * performing {@link #findId(int)} we make each element on the path to the root
 * point to the root found. The more {@link #findId(int)}
 * is called, the flat the tree becomes.
 *
 * @author Renat Kaitmazov
 */

public final class WeightedQuickUnion extends Union {

    /*--------------------------------------------------------*/
    /* Fields
    /*--------------------------------------------------------*/

    private final int[] weights;

    /*--------------------------------------------------------*/
    /* Constructors
    /*--------------------------------------------------------*/

    public WeightedQuickUnion(int numberOfComponents) {
        super(numberOfComponents);
        weights = new int[numberOfComponents];
        initWeights(weights, numberOfComponents);
    }

    private void initWeights(final int[] weights, int size) {
        // Initially the size of each tree is 1: the entry itself.
        for (int i = 0; i < size; ++i) {
            weights[i] = 1;
        }
    }

    /*--------------------------------------------------------*/
    /* ConnectedComponent implementation
    /*--------------------------------------------------------*/

    @Override
    final boolean connect(int id1, int id2) {
        // Find weights of the trees.
        final int weight1 = weights[id1];
        final int weight2 = weights[id2];
        if (weight2 > weight1) {
            // The first tree becomes a subtree of the second tree
            // if and only if the weight of the second tree is greater
            // than that of the first tree.
            elements[id1] = id2;
            // Increase its weight.
            weights[id2] += weight1;
        } else {
            // The second tree becomes a subtree of the first tree
            // because it weights less.
            elements[id2] = id1;
            // Increase its weight.
            weights[id1] += weight2;
        }
        return true;
    }

    @Override
    final int findId(int element) {
        final int ultimateRoot = super.findId(element);
        int immediateRoot = elements[element];
        // Perform path compression
        while (immediateRoot != elements[element]) {
            final int grandparent = elements[immediateRoot];
            // Make the immediate root point to the ultimate root of the tree
            // to flatten it out.
            elements[immediateRoot] = ultimateRoot;
            immediateRoot = grandparent;
        }
        return ultimateRoot;
    }
}
