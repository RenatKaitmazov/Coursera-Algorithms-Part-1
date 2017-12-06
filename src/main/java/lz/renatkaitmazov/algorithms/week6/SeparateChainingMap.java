package lz.renatkaitmazov.algorithms.week6;


import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import static lz.renatkaitmazov.algorithms.week6.HashUtils.hash;

/**
 * A concrete implementation of the map data structure.
 * Uses the separate chaining method to solve collision problems.
 * Lists for storing collided map are created lazily to be
 * more memory efficient.
 *
 * @author Renat Kaitmazov
 */

public final class SeparateChainingMap<K, V> implements Map<K, V> {

    /*--------------------------------------------------------*/
    /* Constants
    /*--------------------------------------------------------*/

    private static final int DEFAULT_CAPACITY = 16;
    private static final double LOAD_FACTOR = 0.75;

    /*--------------------------------------------------------*/
    /* Fields
    /*--------------------------------------------------------*/

    private List<Entry<K, V>>[] map;
    private int size;

    /*--------------------------------------------------------*/
    /* Constructors
    /*--------------------------------------------------------*/

    @SuppressWarnings("unchecked")
    public SeparateChainingMap(int capacity) {
        if (capacity < 1) {
            throw new IllegalArgumentException("Capacity must be positive");
        }
        map = (List<Entry<K, V>>[]) new LinkedList[capacity];
    }

    public SeparateChainingMap() {
        this(DEFAULT_CAPACITY);
    }

    /*--------------------------------------------------------*/
    /* Object class methods
    /*--------------------------------------------------------*/

    @Override
    public String toString() {
        if (isEmpty()) return "[]";
        final StringBuilder builder = new StringBuilder("[");
        for (final List<Entry<K, V>> chain : map) {
            if (chain != null) {
                for (final Entry<K, V> entry : chain) {
                    builder.append(entry).append(", ");
                }
            }
        }
        final int end = builder.length();
        final int start = end - 2;
        return builder.replace(start, end, "]").toString();
    }

    /*--------------------------------------------------------*/
    /* Map implementation
    /*--------------------------------------------------------*/

    @Override
    public int size() {
        return size;
    }

    @Override
    public V get(K key) {
        validateNotNull(key);
        final int keyIndex = hash(key, map.length);
        final List<Entry<K, V>> chain = map[keyIndex];
        if (chain != null) {
            for (final Entry<K, V> entry : chain) {
                if (key.equals(entry.getKey())) {
                    return entry.getValue();
                }
            }
        }
        return null;
    }

    @Override
    public void put(K key, V value) {
        validateNotNull(key);
        if (value == null) {
            remove(key);
            return;
        }

        final int capacity = map.length;
        // Resize if the map is 75% full to reduce number of collisions.
        if (size >= capacity * LOAD_FACTOR) resize(capacity << 1);
        final int keyIndex = hash(key, map.length);
        if (map[keyIndex] == null) map[keyIndex] = new LinkedList<>();
        final List<Entry<K, V>> chain = map[keyIndex];
        for (final Entry<K, V> entry : chain) {
            if (key.equals(entry.getKey())){
                // The key is already in the map, update its value.
                entry.setValue(value);
                return;
            }
        }
        chain.add(new Entry<>(key, value));
        ++size;
    }

    @Override
    public V remove(K key) {
        validateNotNull(key);
        if (isEmpty()) return null;
        final int capacity = map.length;
        final int keyIndex = hash(key, capacity);
        final List<Entry<K, V>> chain = map[keyIndex];
        if (chain != null) {
            for (Iterator<Entry<K, V>> iterator = chain.listIterator(); iterator.hasNext();) {
                final Entry<K, V> entry = iterator.next();
                if (key.equals(entry.getKey())) {
                    iterator.remove();
                    --size;
                    if (capacity >> 2 >= size) resize(capacity >> 1);
                    return entry.getValue();
                }
            }
        }
        return null;
    }

    @Override
    public Iterable<K> keys() {
        if (isEmpty()) return null;
        final List<K> keys = new LinkedList<>();
        for (final List<Entry<K, V>> chain : this.map) {
            if (chain != null) {
                for (final Entry<K, V> entry : chain) {
                    keys.add(entry.getKey());
                }
            }
        }
        return keys;
    }

    /*--------------------------------------------------------*/
    /* Helper methods
    /*--------------------------------------------------------*/

    private void validateNotNull(K key) {
        if (key == null) {
            throw new IllegalArgumentException("Key cannot be null");
        }
    }

    private void resize(int newSize) {
        @SuppressWarnings("unchecked")
        final List<Entry<K, V>>[] newMap = (List<Entry<K,V>>[]) new LinkedList[newSize];
        // A simple copy won't do. The hash value for each item in the set was calculated relative to the old size
        // of the set. Now when the capacity of the set has changed, we need to recalculate a hash value for
        // each item and put it into a proper position in the set.
        for (final List<Entry<K, V>> chain : map) {
            if (chain != null) {
                for (final Entry<K, V> entry : chain) {
                    // Recalculate a hash value for each entry in the map.
                    final int keyIndex = hash(entry.getKey(), newSize);
                    if (newMap[keyIndex] == null) newMap[keyIndex] = new LinkedList<>();
                    newMap[keyIndex].add(entry);
                }
            }
        }
        map = newMap;
    }

    /*--------------------------------------------------------*/
    /* Testing
    /*--------------------------------------------------------*/

    int getCapacity() {
        return map.length;
    }
}
