package lz.renatkaitmazov.algorithms.week6;

/**
 * An abstraction of the map data structure (associative array or symbol table).
 * Stores values paired with keys.
 * Keys are unique and are added into the map in no particular order.
 * Does not allow for duplicates.
 * A key that is going to be stored in a set must override
 * the {@link #hashCode()} and the {@link #equals(Object)} methods
 * because the data structure relies on them. Otherwise
 * there won't be guarantees that the map stores unique values.
 * Null keys are not allowed to be added.
 * If the client tries to add a null value, the key associated with the value will be removed from the map.
 *
 * @author Renat Kaitmazov
 */

public interface Map<K, V> {
    /**
     * Returns the amount of keys in the map.
     *
     * @return number of keys.
     */
    int size();

    /**
     * Checks if the map is empty or not.
     *
     * @return <code>true</code> if the map has no elements,
     * <code>false</code> otherwise
     */
    default boolean isEmpty() {
        return size() == 0;
    }

    /**
     * Returns a value associated with the given key.
     *
     * @param key whose value will be searched for.
     * @return <code>value</code> paired with the key if it is the map,
     * <code>null</code> if the map is empty or if the key is absent.
     */
    V get(K key);

    /**
     * Checks to see if the key is in the map.
     *
     * @param key to be checked.
     * @return <code>true</code> if the given key is in the map,
     * <code>false</code> otherwise.
     */
    default boolean contains(K key) {
        return get(key) != null;
    }

    /**
     * Puts the given value paired with the key into the map.
     * If the value is <code>null</code>, removes the key from the table.
     * Throws a runtime exception if the key is <code>null</code>.
     * If the key is already in the table, it updates the value paired with the key.
     *
     * @param key   a key
     * @param value associated with the key
     */
    void put(K key, V value);

    /**
     * Removes a pair of the given key and its associated value.
     *
     * @param key to remove.
     * @return value, paired with the key,
     * <code>null</code> if the key was absent or the map is empty.
     */
    V remove(K key);

    /**
     * Returns an iterable collection of keys.
     *
     * @return a collection of keys,
     * <code>null</code> if the table is empty.
     */
    Iterable<K> keys();
}
