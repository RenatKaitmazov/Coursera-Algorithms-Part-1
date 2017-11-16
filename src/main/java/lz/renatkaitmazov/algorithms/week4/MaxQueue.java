package lz.renatkaitmazov.algorithms.week4;

import java.util.Comparator;

/**
 * A concrete implementation of the priority queue abstraction.
 * As its name implies it is a maximum queue, that is, the first
 * item in the queue is the maximum item.
 *
 * @author Renat Kaitmazov
 */

public final class MaxQueue<T extends Comparable<T>> extends AbstractPriorityQueue<T> {

    /*--------------------------------------------------------*/
    /* Constructors
    /*--------------------------------------------------------*/

    public MaxQueue() {
    }

    public MaxQueue(int capacity) {
        super(capacity, Comparator.reverseOrder());
    }
}
