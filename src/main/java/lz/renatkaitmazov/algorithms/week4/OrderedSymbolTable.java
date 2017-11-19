package lz.renatkaitmazov.algorithms.week4;

import lz.renatkaitmazov.algorithms.week2.LinkedList;

/**
 * @author Renat Kaitmazov
 */

public final class OrderedSymbolTable<K extends Comparable<K>, V> implements SymbolTable<K, V> {

    /*--------------------------------------------------------*/
    /* Constants
    /*--------------------------------------------------------*/

    /**
     * A default capacity of the array in case the client does not know
     * in advance how big the table is going to be.
     */
    private static final int DEFAULT_CAPACITY = 16;

    /*--------------------------------------------------------*/
    /* Fields
    /*--------------------------------------------------------*/

    /**
     * The backing data storage. Stores entries of values associated
     * with keys in ascending order.
     */
    private Entry<K, V>[] entries;

    /**
     * Keeps track of the number of elements in the table.
     * It is also used a pointer to the last element in the array.
     */
    private int size;

    /*--------------------------------------------------------*/
    /* Constructors
    /*--------------------------------------------------------*/

    public OrderedSymbolTable() {
        this(DEFAULT_CAPACITY);
    }

    @SuppressWarnings("unchecked")
    public OrderedSymbolTable(int capacity) {
        entries = (Entry<K, V>[]) new Entry[capacity];
    }

    /*--------------------------------------------------------*/
    /* Object class methods
    /*--------------------------------------------------------*/

    @Override
    public String toString() {
        if (isEmpty()) {
            return "[]";
        }
        final StringBuilder builder = new StringBuilder("[");
        for (int i = 0; i < size; ++i) {
            final Entry<K, V> entry = entries[i];
            builder.append(entry).append(", ");
        }
        final int end = builder.length();
        final int start = end - 2;
        return builder.replace(start, end, "]")
                .toString();
    }

    /*--------------------------------------------------------*/
    /* SymbolTable implementation
    /*--------------------------------------------------------*/

    @Override
    public int size() {
        return size;
    }

    @Override
    public void put(K key, V value) {
        validateNotNull(key);
        if (value == null) {
            remove(key);
            return;
        }

        if (isEmpty()) {
            entries[size] = new Entry<>(key, value);
        } else {
            final int ceilIndex = ceiling(key);
            final Entry<K, V> oldEntry = entries[ceilIndex];
            if (oldEntry != null && key.equals(oldEntry.getKey())) {
                // Just update the value of the existing entry.
                oldEntry.setValue(value);
                return;
            }
            // Insert a new value.
            final Entry<K, V> newEntry = new Entry<>(key, value);
            // Check fullness of the table.
            final int capacity = entries.length;
            if (size == capacity) {
                resize(capacity << 1);
            }
            // Make a free cell in the array for the newly created entry.
            System.arraycopy(entries, ceilIndex, entries, ceilIndex + 1, size - ceilIndex);
            // Insert the entry into the free cell.
            entries[ceilIndex] = newEntry;
        }
        ++size;
    }

    private int ceiling(K key) {
        int start = 0;
        int end = size - 1;
        while (start <= end) {
            final int middle = start + ((end - start) >> 1);
            final Entry<K, V> guess = entries[middle];
            final int compareResult = key.compareTo(guess.getKey());
            if      (compareResult > 0) start = middle + 1;
            else if (compareResult < 0) end = middle - 1;
            else return middle;
        }
        return start;
    }

    private void resize(int newSize) {
        @SuppressWarnings("unchecked")
        final Entry<K, V>[] newEntries = (Entry<K, V>[]) new Entry[newSize];
        System.arraycopy(entries, 0, newEntries, 0, size);
        entries = newEntries;
    }

    private <T> void validateNotNull(T item) {
        if (item == null) {
            throw new IllegalArgumentException("key is null");
        }
    }

    @Override
    public V remove(K key) {
        validateNotNull(key);
        if (isEmpty()) {
            return null;
        }
        final int indexOfKey = indexOfKey(key);
        if (indexOfKey > -1) {
            // The key is in the table.
            final Entry<K, V> entryToRemove = removeAt(indexOfKey);
            --size;
            final int capacity = entries.length;
            if ((capacity >> 2) >= size) {
                // Number of entries in the table is at least four times smaller than the capacity.
                // Halve the capacity of the backing array.
                resize(capacity >> 1);
            }
            return entryToRemove.getValue();
        }
        return null;
    }

    @Override
    public V get(K key) {
        validateNotNull(key);
        if (isEmpty()) {
            return null;
        }
        final int indexOfKey = indexOfKey(key);
        if (indexOfKey > -1) {
            final Entry<K, V> entry = entries[indexOfKey];
            return entry.getValue();
        }
        return null;
    }

    @Override
    public Iterable<K> keys() {
        if (isEmpty()) {
            return null;
        }
        final LinkedList<K> keys = new LinkedList<>();
        for (int i = 0; i < size; ++i) {
            final Entry<K, V> entry = entries[i];
            keys.append(entry.getKey());
        }
        return keys;
    }


    /*--------------------------------------------------------*/
    /* Additional API methods
    /*--------------------------------------------------------*/

    public int indexOf(K key) {
        validateNotNull(key);
        return indexOfKey(key);
    }

    /**
     * A helper method that returns an index of the given key.
     * Notice that it does not check if the key is null, so call
     * this method if and only if you are sure that the key is not null.
     * It is a classical binary search algorithm.
     *
     * @param key whose index is looked for.
     * @return index of the key,
     * <code>-1</code> if the key is not in the table.
     */
    private int indexOfKey(K key) {
        for (int left = 0, right = size - 1; left <= right; ) {
            final int middle = left + ((right - left) >> 1);
            final Entry<K, V> guess = entries[middle];
            final int compareResult = key.compareTo(guess.getKey());
            if      (compareResult > 0) left = middle + 1;
            else if (compareResult < 0) right = middle - 1;
            else return middle;
        }
        return -1;
    }

    public K floor(K key) {
        // todo
        return null;
    }

    public K ceil(K key) {
        // todo
        return null;
    }

    public K getKeyAt(int index) {
        validateIndex(index);
        final Entry<K, V> entry = entries[index];
        return entry.getKey();
    }

    private void validateIndex(int index) {
        if (index < 0 || index >= size) {
            throw new IllegalArgumentException("index is out of bounds");
        }
    }

    public K min() {
        if (isEmpty()) {
            return null;
        }
        final Entry<K, V> minEntry = entries[0];
        return minEntry.getKey();
    }

    public K max() {
        if (isEmpty()) {
            return null;
        }
        final Entry<K, V> maxEntry = entries[size - 1];
        return maxEntry.getKey();
    }

    public K removeMin() {
        if (isEmpty()) {
            return null;
        }
        final Entry<K, V> minEntry = removeAt(0);
        return minEntry.getKey();
    }

    public K removeMax() {
        if (isEmpty()) {
            return null;
        }
        final Entry<K, V> maxEntry = removeAt(--size);
        return maxEntry.getKey();
    }

    /**
     * A helper method to remove an entry at the given index from the table.
     * It is assumed that the table is not empty, and that the index with
     * valid bounds.
     * The method does not do eny checks. Call it if and only if you are
     * confident that there is at least one item in the table, and the index
     * is valid.
     *
     * @param index of the entry to be removed.
     * @return removed entry.
     */
    private Entry<K, V> removeAt(int index) {
        final Entry<K, V> entryToRemove = entries[index];
        // Shift each entry to the left from the end up to the given index.
        System.arraycopy(entries, index + 1, entries, index + 1 - 1, size - index + 1);
        // Avoid loitering.
        entries[size - 1] = null;
        return entryToRemove;
    }

    /*--------------------------------------------------------*/
    /* For testing purposes only!
    /*--------------------------------------------------------*/

    int capacity() {
        return entries.length;
    }
}
