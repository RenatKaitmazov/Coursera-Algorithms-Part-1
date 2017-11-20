package lz.renatkaitmazov.algorithms.week4;

import lz.renatkaitmazov.algorithms.week2.LinkedList;
import lz.renatkaitmazov.algorithms.week2.LinkedQueue;
import lz.renatkaitmazov.algorithms.week2.Queue;

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
        if (isEmpty()) return "[]";
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
            // By convention, if the value is null, remove the key associated with the value.
            remove(key);
            return;
        }
        // Number of items less than the key.
        final int rankOfKey = helperRank(key);
        if (rankOfKey < size) {
            // It is not the case that all keys are less than the new key.
            final Entry<K, V> oldEntry = entries[rankOfKey];
            if (key.equals(oldEntry.getKey())) {
                // There already exists an entry with the same key. Duplicates are not allowed.
                // Just update its value.
                oldEntry.setValue(value);
                return;
            }
        }
        // If the table is full, make it twice as big.
        final int capacity = entries.length;
        if (size == capacity) resize(capacity << 1);
        // Shift every key which is larger than the new key to the right by one cell to make a free cell for the
        // new key.
        System.arraycopy(entries, rankOfKey, entries, rankOfKey + 1, size - rankOfKey);
        entries[rankOfKey] = new Entry<>(key, value);
        ++size;
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
        if (isEmpty()) return null;
        // Number of keys less than the given key.
        final int rankOfKey = helperRank(key);
        if (rankOfKey < size) {
            // The given key was not larger than any of other keys in the table.
            final Entry<K, V> entryToRemove = entries[rankOfKey];
            if (key.equals(entryToRemove.getKey())) {
                // Found a match. Remove the key.
                removeAt(rankOfKey);
                return entryToRemove.getValue();
            }
        }
        return null;
    }

    @Override
    public V get(K key) {
        validateNotNull(key);
        if (isEmpty()) return null;
        // Number of keys less than the given key.
        final int rankOfKey = helperRank(key);
        if (rankOfKey < size) {
            // The given key was not larger than any of other keys in the table.
            final Entry<K, V> entry = entries[rankOfKey];
            if (key.equals(entry.getKey())) {
                // Successfully found a match.
                return entry.getValue();
            }
        }
        return null;
    }

    @Override
    public Iterable<K> keys() {
        if (isEmpty()) return null;
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

    /**
     * Returns the number of keys less than the given key.
     *
     * @param key whose rank should be calculated.
     * @return number of keys less than <code>key</code>. The number is in [0...size].
     */
    public int rank(K key) {
        validateNotNull(key);
        return helperRank(key);
    }

    /**
     * Returns the number of keys less than the given key.
     * It is assumed that the key is not null.
     * Call this method if and only if you are sure that the key
     * is not null. This helper method does not perform any checks.
     * It is actually a classic binary search algorithm.
     * The only difference is, it does not return <code>-1</code> if the is
     * not present in the table.
     *
     * @param key whose rank should be calculated.
     * @return number of keys less than <code>key</code>. The number is in [0...size].
     */
    private int helperRank(K key) {
        int start = 0;
        int end = size - 1;
        while (end >= start) {
            final int middle = start + ((end - start) >> 1);
            final Entry<K, V> guessedEntry = entries[middle];
            final int compareResult = key.compareTo(guessedEntry.getKey());
            if      (compareResult < 0) end = middle - 1;
            else if (compareResult > 0) start = middle + 1;
            else return middle;
        }
        return start;
    }

    /**
     * Returns the largest key less than or equal to the given key.
     *
     * @param key a key.
     * @return the largest key <code><= key</code>,
     * <code>null</code> if the table is empty.
     */
    public K floor(K key) {
        validateNotNull(key);
        if (isEmpty()) return null;
        final int rankOfKey = helperRank(key);
        // If the given key is not larger than any of other keys in the table and
        // there is a match, then return the given key, since key <= entries[rankOfEntry].
        if (rankOfKey < size && key.equals(entries[rankOfKey].getKey())) return key;
        // If the given key is less than the smallest key in the table, then return null,
        // since there is no a key such that <= the given key.
        if (rankOfKey == 0 && key.compareTo(entries[0].getKey()) < 0) return null;
        // Return a key such that < the given key.
        return entries[rankOfKey - 1].getKey();
    }

    /**
     * Returns the smallest greater than or equal to the given key.
     *
     * @param key a key.
     * @return the largest key <code>>= key</code>,
     * <code>null</code> if the table is empty.
     */
    public K ceil(K key) {
        validateNotNull(key);
        if (isEmpty()) return null;
        // Number of keys smaller than the given key.
        final int rankOfKey = helperRank(key);
        // The key is not larger than the table's largest key.
        if (rankOfKey < size) return entries[rankOfKey].getKey();
        return null;
    }

    /**
     * Returns a key at the given index.
     * If the index is out of the table's bounds, a runtime exception is thrown.
     *
     * @param index of the key.
     * @return key at the <code>index</code>
     * or a runtime exception is thrown, if the index is wrong.
     */
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

    /**
     * Returns the smallest key in the table.
     *
     * @return the smallest key,
     * <code>null</code> if the table is empty.
     */
    public K min() {
        if (isEmpty()) return null;
        final Entry<K, V> minEntry = entries[0];
        return minEntry.getKey();
    }

    /**
     * Returns the largest key in the table.
     *
     * @return the largest key,
     * <code>null</code> if the table is empty.
     */
    public K max() {
        if (isEmpty()) return null;
        final Entry<K, V> maxEntry = entries[size - 1];
        return maxEntry.getKey();
    }

    /**
     * Removes the smallest key in the table.
     *
     * @return smallest key,
     * <code>null</code> if the table is empty.
     */
    public K removeMin() {
        if (isEmpty()) return null;
        final Entry<K, V> minEntry = removeAt(0);
        return minEntry.getKey();
    }

    /**
     * Removes the largest key in the table.
     *
     * @return the largest key in the table,
     * <code>null</code> if the table is empty.
     */
    public K removeMax() {
        if (isEmpty()) return null;
        final Entry<K, V> maxEntry = removeAt(size - 1);
        return maxEntry.getKey();
    }

    public int size(K start, K end) {
        if (!isEmpty()) {
            // Make sure that the start index is not larger than the end index.
            validateRange(start, end);
            // Number of items less than the the starting key.
            final int startRank = helperRank(start);
            // Numbers of items less than the ending key plus 1 if the ending key is in the table.
            // Rank method does not take the key itself into account, so we need to check the end key.
            final int endRank = helperRank(end) + (contains(end) ? 1 : 0);
            // The keys are in the range. Calculate the difference between end and start.
            if (startRank < endRank) return endRank - startRank;
        }
        return 0;
    }

    /**
     * It is assumed that the keys are not null.
     *
     * @param start of the range.
     * @param end of the range.
     */
    private void validateRange(K start, K end) {
        if (start.compareTo(end) > 0) {
            throw new IllegalArgumentException("end is less than start");
        }
    }

    /**
     * Returns the index of the given key.
     * Call this method if and only if you are confident that the key is in the table.
     * It is assumed that the key is not null and it is present in the table.
     * Used internally for convenience.
     *
     * @param key whose index should be returned.
     * @return index of the given key.
     */
    private int indexOf(K key) {
        for (int start = 0, end = size - 1; start <= end; ) {
            final int middle = start + ((end - start) >> 1);
            final int compareResult = key.compareTo(entries[middle].getKey());
            if      (compareResult > 0) start = middle + 1;
            else if (compareResult < 0) end = middle - 1;
            else return middle;
        }
        // This should never happen. Read the information above the method declaration.
        return -1;
    }

    /**
     * Returns keys in the given range [start, end].
     * If start is larger than end, a runtime exception is thrown.
     *
     * @param start the start of the range.
     * @param end the end of the range.
     * @return an iterable collection of keys in sorted order in the range [start, end],
     * empty list if no items were found in the range,
     * <code>null</code> if the table is empty.
     */
    public Iterable<K> keys(K start, K end) {
        if (isEmpty()) {
            return null;
        }
        validateRange(start, end);
        final Queue<K> keys = new LinkedQueue<>();
        final int startIndex = helperRank(start);
        final int endIndex = helperRank(end);
        for (int i = startIndex; i < endIndex; ++i) {
            final Entry<K, V> entry = entries[i];
            keys.enqueue(entry.getKey());
        }
        // It is possible not to include the largest key of the range, since the loop
        // does not take into account the end index of the range (because it potentially can be equal to size
        // in which an exception will be thrown).
        // We need to make sure that we don't miss anything.
        if (contains(end)) keys.enqueue(end);
        return keys;
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
        System.arraycopy(entries, index + 1, entries, index + 1 - 1, size - (index + 1));
        // Avoid loitering.
        entries[--size] = null;
        final int capacity = entries.length;
        // Halve the capacity of the table if the number of keys are at least four times less than
        // the size of the table.
        if ((capacity >> 2) >= size) resize(capacity >> 1);
        return entryToRemove;
    }

    /*--------------------------------------------------------*/
    /* For testing purposes only!
    /*--------------------------------------------------------*/

    int capacity() {
        return entries.length;
    }
}
