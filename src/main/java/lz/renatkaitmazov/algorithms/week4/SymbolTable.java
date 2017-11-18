package lz.renatkaitmazov.algorithms.week4;

/**
 * An abstraction of the data type known as a symbol table which stores values
 * associated with keys. Sometimes it is called a dictionary.
 * Implementations of this interface do not use hashing so the operations of
 * inserting, deleting, and searching are slow.
 * Hashing will be covered on week 6.
 *
 * @author Renat Kaitmazov
 */

public interface SymbolTable<K, V> {


    /**
     * Returns the amount of items in the queue.
     *
     * @return number of items.
     */
    int size();

    /**
     * Checks to see if the queue is empty or not.
     *
     * @return <code>true</code> if there are no items in the queue,
     * <code>false</code> otherwise.
     */
    default boolean isEmpty() {
        return size() == 0;
    }

    /**
     * Puts the given value paired with the key into the table.
     * If the value is <code>null</code>, removes the key from the table.
     * Throws a runtime exception if the key is <code>null</code>.
     * If the key is already in the table, it updates paired with the key.
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
     * <code>null</code> if the key was absent.
     */
    V remove(K key);

    /**
     * Returns a value associated with the given key.
     *
     * @param key whose value should be returned.
     * @return value, paired with the key,
     * <code>null</code> if the key was absent.
     */
    V get(K key);

    /**
     * Checks to see if the given key is present in the table.
     *
     * @param key to check.
     * @return <code>true</code> if the key is in the table,
     * <code>false</code> otherwise.
     */
    default boolean contains(K key) {
        return get(key) != null;
    }

    /**
     * Returns an iterable collection of keys.
     *
     * @return a collection of keys,
     * <code>null</code> if the table is empty.
     */
    Iterable<K> keys();
}
