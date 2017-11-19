package lz.renatkaitmazov.algorithms.week4;

import lz.renatkaitmazov.algorithms.week2.LinkedList;
import lz.renatkaitmazov.algorithms.week2.List;

import java.util.Iterator;

/**
 * @author Renat Kaitmazov
 */

public final class UnorderedSymbolTable<K, V> implements
        SymbolTable<K, V>,
        Iterable<Entry<K, V>> {

    /*--------------------------------------------------------*/
    /* Fields
    /*--------------------------------------------------------*/

    private Entry<K, V> head;
    private int size;

    /*--------------------------------------------------------*/
    /* Constructors
    /*--------------------------------------------------------*/

    public UnorderedSymbolTable() {
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
        for (Entry<K, V> current = head; current != null; current = current.getNext()) {
            builder.append(current).append(", ");
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
    public boolean isEmpty() {
        return head == null;
    }

    @Override
    public V get(K key) {
        validateNotNull(key);
        final Entry<K, V> entry = getEntry(key);
        if (entry != null) {
            return entry.getValue();
        }
        return null;
    }

    private Entry<K, V> getEntry(K key) {
        for (Entry<K, V> current = head; current != null; current = current.getNext()) {
            if (current.getKey().equals(key)) {
                return current;
            }
        }
        return null;
    }

    @Override
    public void put(K key, V value) {
        validateNotNull(key);
        if (value == null) {
            // The convention is, that if the value is null and the key
            // is present in the table, that entry will be removed completely.
            remove(key);
            return;
        }

        final Entry<K, V> oldEntry = getEntry(key);
        if (oldEntry != null) {
            // If the key is already in the table, update its value.
            oldEntry.setValue(value);
            return;
        }

        // An absolutely new key. Add it.
        final Entry<K, V> newEntry = new Entry<>(key, value);
        newEntry.setNext(head);
        head = newEntry;
        ++size;
    }

    @SuppressWarnings("unchecked")
    @Override
    public V remove(K key) {
        validateNotNull(key);
        for (Entry<K, V> current = head, previous = head; current != null; current = current.getNext()) {
            if (current.getKey().equals(key)) {
                final V tempValue = current.getValue();
                if (current == head) {
                    // A corner case: the key to be removed is the head.
                    head = current.getNext();
                } else {
                    // Any other entry.
                    previous.setNext(current.getNext());
                }
                --size;
                return tempValue;
            }
            previous = current;
        }
        return null;
    }

    @Override
    public Iterable<K> keys() {
        if (isEmpty()) {
            return null;
        }
        // Make a defencive copy.
        final List<K> keys = new LinkedList<>();
        for (Entry<K, V> current = head; current != null; current = current.getNext()) {
            keys.append(current.getKey());
        }
        return keys;
    }

    private void validateNotNull(K key) {
        if (key == null) {
            throw new IllegalArgumentException("key is null");
        }
    }

    /*--------------------------------------------------------*/
    /* Iterable implementation
    /*--------------------------------------------------------*/

    @Override
    public Iterator<Entry<K, V>> iterator() {
        return new UnorderedSymbolTableIterator<>(this);
    }

    /*--------------------------------------------------------*/
    /* Nested classes
    /*--------------------------------------------------------*/

    private static final class UnorderedSymbolTableIterator<K, V> implements Iterator<Entry<K, V>> {

        private Entry<K, V> current;

        UnorderedSymbolTableIterator(UnorderedSymbolTable<K, V> unorderedSymbolTable) {
            current = unorderedSymbolTable.head;
        }

        @Override
        public Entry<K, V> next() {
            final Entry<K, V> temp = current;
            current = current.getNext();
            return temp;
        }

        @Override
        public boolean hasNext() {
            return current != null;
        }
    }
}
