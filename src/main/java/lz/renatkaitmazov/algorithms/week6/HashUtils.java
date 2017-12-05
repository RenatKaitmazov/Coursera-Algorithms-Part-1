package lz.renatkaitmazov.algorithms.week6;

/**
 * A utility class for sets and maps.
 *
 * @author Renat Kaitmazov
 */

public final class HashUtils {

    /*--------------------------------------------------------*/
    /* Constructors
    /*--------------------------------------------------------*/

    private HashUtils() {
    }

    /*--------------------------------------------------------*/
    /* API
    /*--------------------------------------------------------*/

    /**
     * Generates a hash code for the given item.
     * The method does perform any checks. Be sure
     * that the item is not null.
     *
     * @param item whose hash code will be generated.
     * @param upperBound hash code will not exceed the upper bound.
     * @return a positive number.
     */
    static <T> int hash(T item, int upperBound) {
        // The number will never be negative due to the and operator.
        // The leading bit of any positive number is 0.
        // The leading bit of any negative number is 1.
        // 0 & 1 = 0. So the result of the and operation is always a positive number which can be used as an array index.
        return (item.hashCode() & Integer.MAX_VALUE) % upperBound;
    }
}
