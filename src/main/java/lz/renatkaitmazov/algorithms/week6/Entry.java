package lz.renatkaitmazov.algorithms.week6;

/**
 * @author Renat Kaitmazov
 */

final class Entry<K, V> {

    /*--------------------------------------------------------*/
    /* Fields
    /*--------------------------------------------------------*/

    private final K key;
    private V value;
    private boolean isRemoved;

    /*--------------------------------------------------------*/
    /* Constructors
    /*--------------------------------------------------------*/

    Entry(K key, V value) {
        this.key = key;
        this.value = value;
    }

    /*--------------------------------------------------------*/
    /* Object class methods
    /*--------------------------------------------------------*/

    @Override
    public String toString() {
        return String.format("{%s:%s}", key, value);
    }

    /*--------------------------------------------------------*/
    /* Getters and setters
    /*--------------------------------------------------------*/

    K getKey() {
        return key;
    }

    V getValue() {
        return value;
    }

    void setValue(V value) {
        this.value = value;
    }

    boolean isRemoved() {
        return isRemoved;
    }

    void setRemoved(boolean removed) {
        isRemoved = removed;
    }
}
