package lz.renatkaitmazov.algorithms.week4;

/**
 * A wrapper class that store a key and a value associated with the key.
 *
 * @param <K> key.
 * @param <V> value.
 */
public final class Entry<K, V> {

    private final K key;
    private V value;
    private Entry<K, V> next;

    Entry(K key, V value) {
        this.key = key;
        this.value = value;
    }

    public K getKey() {
        return key;
    }

    public V getValue() {
        return value;
    }

    public void setValue(V value) {
        this.value = value;
    }

    public Entry<K, V> getNext() {
        return next;
    }

    public void setNext(Entry<K, V> next) {
        this.next = next;
    }

    @Override
    public String toString() {
        final String val = (value != null) ? value.toString() : "null";
        return String.format("{%s:%s}", key, val);
    }

    @Override
    public boolean equals(Object o) {
        if (o == this) return true;
        if (o == null) return false;
        if (o.getClass() != getClass()) return false;
        @SuppressWarnings("unchecked") final Entry<K, V> that = (Entry<K, V>) o;
        return this.key.equals(that.key);
    }

    @Override
    public int hashCode() {
        int result = 17;
        result = 31 * result + key.hashCode();
        result = 31 * result + (value != null ? value.hashCode() : 0);
        return result;
    }
}