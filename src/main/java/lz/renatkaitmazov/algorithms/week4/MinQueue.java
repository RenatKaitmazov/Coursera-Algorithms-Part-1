package lz.renatkaitmazov.algorithms.week4;

import java.util.Comparator;

/**
 * A concrete implementation of the priority queue abstraction.
 * As its name implies it is a minimum queue, that is, the first
 * item in the queue is the smallest item.
 *
 * @author Renat Kaitmazov
 */

public final class MinQueue<T extends Comparable<T>> extends AbstractPriorityQueue<T> {

    /*--------------------------------------------------------*/
    /* Constructors
    /*--------------------------------------------------------*/

    public MinQueue() {
    }

    public MinQueue(int capacity) {
        super(capacity, Comparator.naturalOrder());
    }
}
