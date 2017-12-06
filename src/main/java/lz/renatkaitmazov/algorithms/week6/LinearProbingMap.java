package lz.renatkaitmazov.algorithms.week6;

import java.util.LinkedList;
import java.util.List;

import static lz.renatkaitmazov.algorithms.week6.HashUtils.hash;

/**
 * A concrete implementation of the map data structure.
 * Uses the linear probing technique to solve collision problems.
 * Uses the lazy deletion approach to remove keys from the map.
 *
 * @author Renat Kaitmazov
 */

public final class LinearProbingMap<K, V> implements Map<K, V> {

    /*--------------------------------------------------------*/
    /* Constants
    /*--------------------------------------------------------*/

    private static final int DEFAULT_CAPACITY = 16;
    private static final double LOAD_FACTOR = 0.5;

    /*--------------------------------------------------------*/
    /* Fields
    /*--------------------------------------------------------*/

    private Entry<K, V>[] entries;
    private int size;

    /*--------------------------------------------------------*/
    /* Constructors
    /*--------------------------------------------------------*/

    @SuppressWarnings("unchecked")
    public LinearProbingMap(int capacity) {
        if (capacity < 1) {
            throw new IllegalArgumentException("Capacity must be positive");
        }
        entries = (Entry<K, V>[]) new Entry[capacity];
    }

    public LinearProbingMap() {
        this(DEFAULT_CAPACITY);
    }

    /*--------------------------------------------------------*/
    /* Object class methods
    /*--------------------------------------------------------*/

    @Override
    public String toString() {
        if (isEmpty()) return "[]";
        final StringBuilder builder = new StringBuilder("[");
        for (final Entry<K, V> entry : entries) {
            if (entry != null && !entry.isRemoved()) {
                builder.append(entry).append(", ");
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
        final int capacity = entries.length;
        int keyIndex = hash(key, capacity);
        Entry<K, V> entry = entries[keyIndex];
        while (entry != null && !entry.isRemoved()) {
            if (key.equals(entry.getKey())) return entry.getValue();
            keyIndex = (keyIndex + 1) % capacity;
            entry = entries[keyIndex];
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

        final int oldCapacity = entries.length;
        if (size >= oldCapacity * LOAD_FACTOR) resize(oldCapacity << 1);
        final int newCapacity = entries.length;
        int keyIndex = hash(key, newCapacity);
        Entry<K, V> entry = entries[keyIndex];
        while (entry != null && !entry.isRemoved()) {
            if (key.equals(entry.getKey())) {
                entry.setValue(value);
                return;
            }
            keyIndex = (keyIndex + 1) % newCapacity;
            entry = entries[keyIndex];
        }
        entries[keyIndex] = new Entry<>(key, value);
        ++size;
    }

    @Override
    public V remove(K key) {
        validateNotNull(key);
        final int capacity = entries.length;
        int keyIndex = hash(key, capacity);
        Entry<K, V> entry = entries[keyIndex];
        while (entry != null && !entry.isRemoved()) {
            if (key.equals(entry.getKey())) {
                entry.setRemoved(true);
                --size;
                if (capacity >> 3 >= size) resize(capacity >> 1);
                return entry.getValue();
            }
            keyIndex = (keyIndex + 1) % capacity;
            entry = entries[keyIndex];
        }
        return null;
    }

    @Override
    public Iterable<K> keys() {
        if (isEmpty()) return null;
        final List<K> keys = new LinkedList<>();
        for (final Entry<K, V> entry : entries) {
            if (entry != null && !entry.isRemoved()) {
                keys.add(entry.getKey());
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
        final Entry<K, V>[] newEntries = (Entry<K, V>[]) new Entry[newSize];
        for (final Entry<K, V> entry : entries) {
            if (entry != null && !entry.isRemoved()) {
                int keyIndex = hash(entry.getKey(), newSize);
                while (newEntries[keyIndex] != null) {
                    keyIndex = (keyIndex + 1) % newSize;
                }
                newEntries[keyIndex] = entry;
            }
        }
        entries = newEntries;
    }

    /*--------------------------------------------------------*/
    /* Testing
    /*--------------------------------------------------------*/

    int getCapacity() {
        return entries.length;
    }
}
