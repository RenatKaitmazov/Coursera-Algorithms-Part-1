package lz.renatkaitmazov.algorithms.stackqueue;

import java.util.Iterator;

/**
 * @author Renat Kaitmazov
 */

public class LinkedList<T> implements List<T> {

    /*--------------------------------------------------------*/
    /* Fields
    /*--------------------------------------------------------*/

    private int size;

    /*--------------------------------------------------------*/
    /* Constructors
    /*--------------------------------------------------------*/

    public LinkedList() {
    }

    /*--------------------------------------------------------*/
    /* List implementation
    /*--------------------------------------------------------*/

    @Override
    public final void insert(T item) {

    }

    @Override
    public final void append(T item) {

    }

    @Override
    public final int size() {
        return size;
    }

    @Override
    public final int indexOf(T item) {
        return 0;
    }

    @Override
    public final T get(int index) {
        return null;
    }

    /*--------------------------------------------------------*/
    /* Iterable implementation
    /*--------------------------------------------------------*/

    @Override
    public final Iterator<T> iterator() {
        return null;
    }
}
