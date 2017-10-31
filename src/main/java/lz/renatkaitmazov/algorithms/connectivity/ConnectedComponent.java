package lz.renatkaitmazov.algorithms.connectivity;

import java.util.Arrays;

/**
 * An abstraction for a data type known as a connected component.
 * A connected component is a maximal set of objects that are mutually
 * connected.
 * Allows the client to perform union commands that unions two
 * elements from different components if they are not already
 * connected. It also allows the client to check is two elements
 * already connected.
 *
 * @author Renat Kaitmazov
 */

public abstract class ConnectedComponent {

    /*--------------------------------------------------------*/
    /* Fields
    /*--------------------------------------------------------*/

    /**
     * An amount of connected components.
     * Initially each connected component is considered
     * as a set with only one element.
     * As the client perform union operation, the size
     * of such components will grow and the amount of the sets will decrease.
     * <p>
     * For instance, if we have 5 objects from 0 to 4, each object
     * fits inside a set {0}, {1}, {2}, {3}, {4}. There are 5 distinct sets
     * each having only one element in it. Say, we perform a union of
     * 2 and 4. It might be represented like this:
     * {0}, {1}, {2, 4} {3}
     * As you can see the amount of sets has decreased and
     * the number of elements in one of the sets has increased.
     */

    int numberOfComponents;

    /**
     * An array of elements used to model connected components.
     * Array is chosen because it is more efficient when working
     * with primitive types and allows very fast element access
     * knowing its index.
     */

    final int[] elements;

    /*--------------------------------------------------------*/
    /* Constructors
    /*--------------------------------------------------------*/

    public ConnectedComponent(int numberOfComponents) {
        this.numberOfComponents = numberOfComponents;
        this.elements = new int[numberOfComponents];
        initElements(elements, numberOfComponents);
    }

    /**
     * Set up the array of elements in ascending order,
     * where each element corresponds to its index.
     *
     * @param elements an array of elements to initialize
     * @param size     the amount of components in the <code>elements</code>
     */

    private void initElements(final int[] elements, int size) {
        for (int i = 0; i < size; ++i) {
            elements[i] = i;
        }
    }

    /*--------------------------------------------------------*/
    /* Getters
    /*--------------------------------------------------------*/

    /**
     * Returns the amount of connected components.
     *
     * @return number of connected components.
     */

    public final int components() {
        return numberOfComponents;
    }

    /**
     * Returns a copy of the array with its elements.
     *
     * @return connected component elements.
     */
    public final int[] elements() {
        final int size = elements.length;
        // Make a defensive copy.
        final int[] copy = new int[size];
        System.arraycopy(elements, 0, copy, 0, size);
        return copy;
    }

    /*--------------------------------------------------------*/
    /* Abstract methods
    /*--------------------------------------------------------*/

    /**
     * Performs a union operation of the given elements.
     *
     * @param element1 the first element.
     * @param element2 the second element.
     * @return <code>true</code> if managed to connect the given
     * elements, false otherwise.
     */

    public final boolean union(int element1, int element2) {
        validateIndex(element1);
        validateIndex(element2);
        if (isConnected(element1, element2)) {
            return false;
        }
        if (connect(element1, element2)) {
            --numberOfComponents;
            return true;
        }
        return false;
    }

    /**
     * The implementation should be provided by classes
     * extending this class.
     *
     * @param element1 the first element.
     * @param element2 the second element.
     * @return <code>true</code> if managed to connect the given
     * elements, <code>false</code> otherwise.
     */

    abstract boolean connect(int element1, int element2);

    /**
     * Checks to see if the given elements are connected.
     *
     * @param element1 the first element.
     * @param element2 the second element.
     * @return <code>true</code> if the elements are connected,
     * <code>false</code> otherwise.
     */

    public final boolean isConnected(int element1, int element2) {
        validateIndex(element1);
        validateIndex(element2);
        return checkConnected(element1, element2);
    }


    /**
     * The implementation should be provided by classes
     * extending this class.
     *
     * @param element1 the first element.
     * @param element2 the second element.
     * @return <code>true</code> if the elements are connected,
     * <code>false</code> otherwise.
     */

    abstract boolean checkConnected(int element1, int element2);

    /**
     * Returns the entry id of the given element.
     * The method is used internally for convenience.
     *
     * @param element whose id we are looking for.
     * @return an id of the element form {@link #elements}.
     */

    final int findId(int element) {
        validateIndex(element);
        return find(element);
    }

    /**
     * The implementation should be provided by classes
     * extending this class.
     *
     * @param element whose id we are looking for.
     * @return an id of the element form {@link #elements}.
     */

    abstract int find(int element);

    /*--------------------------------------------------------*/
    /* Object class methods
    /*--------------------------------------------------------*/

    @Override
    public final String toString() {
        return new StringBuilder("Number of components: ")
                .append(numberOfComponents)
                .append("\n")
                .append(Arrays.toString(elements))
                .toString();
    }

    /*--------------------------------------------------------*/
    /* Helper methods
    /*--------------------------------------------------------*/

    private void validateIndex(int index) {
        final int size = elements.length;
        if (index < 0 || index >= size) {
            final String msg = String.format("Index should be in the range [0, %d]. Your index is %d", size, index);
            throw new IllegalArgumentException(msg);
        }
    }
}
