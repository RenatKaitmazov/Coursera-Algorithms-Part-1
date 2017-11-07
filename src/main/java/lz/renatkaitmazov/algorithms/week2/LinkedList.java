package lz.renatkaitmazov.algorithms.week2;

import java.util.Iterator;
import java.util.Objects;

/**
 * A concrete implementation of the list data type.
 * Based on a singly linked items.
 *
 * @author Renat Kaitmazov
 */

public final class LinkedList<T> implements List<T> {

    /*--------------------------------------------------------*/
    /* Fields
    /*--------------------------------------------------------*/

    /**
     * A pointer to the first link in the list.
     */

    private Link<T> head;

    /**
     * A pointer to the last element in the list.
     */

    private Link<T> tail;

    /**
     * Keeps track of the number of elements in the list.
     */

    private int size;

    /*--------------------------------------------------------*/
    /* Constructors
    /*--------------------------------------------------------*/

    public LinkedList() {
    }

    /*--------------------------------------------------------*/
    /* Object class methods
    /*--------------------------------------------------------*/

    @Override
    public final String toString() {
        if (isEmpty()) {
            return "[]";
        }
        final StringBuilder builder = new StringBuilder("[");
        Link<T> current = head;
        while (current != null) {
            builder.append(current.toString()).append(", ");
            current = current.getNext();
        }
        final int end = builder.length();
        final int start = end - 2;
        return builder.replace(start, end, "]")
                .toString();
    }

    /*--------------------------------------------------------*/
    /* List implementation
    /*--------------------------------------------------------*/

    @Override
    public final void insert(T item) {
        final Link<T> newLink = new Link<>(item);
        if (isEmpty()) {
            tail = newLink;
        }
        newLink.setNext(head);
        head = newLink;
        ++size;
    }

    @Override
    public final void append(T item) {
        final Link<T> newLink = new Link<>(item);
        if (isEmpty()) {
            head = newLink;
        } else {
            tail.setNext(newLink);
        }
        tail = newLink;
        ++size;
    }

    @Override
    public final T remove(T item) {
        Link<T> previous = head;
        Link<T> current = head;
        while (current != null) {
            final T data = current.getItem();
            if (Objects.equals(data, item)) {
                break;
            }
            previous = current;
            current = current.getNext();
        }
        if (current == null) return null;
        if (current == head) return removeFirst();
        if (head == tail) tail = null;
        final T temp = current.getItem();
        previous.setNext(current.getNext());
        --size;
        return temp;
    }

    @Override
    public final T removeLast() {
        if (isEmpty()) {
            return null;
        }
        Link<T> previous = head;
        Link<T> current = head;
        while (current != null) {
            previous = current;
            current = current.getNext();
        }
        final T temp = tail.getItem();
        if (head == tail) {
            head = null;
        }
        tail = previous;
        --size;
        return temp;
    }

    @Override
    public final T removeFirst() {
        if (isEmpty()) {
            return null;
        }
        final T temp = head.getItem();
        if (head == tail) {
            // There is only one item in the list.
            tail = null;
        }
        head = head.getNext();
        --size;
        return temp;
    }

    @Override
    public final T removeAt(int index) {
        validateIndex(index);
        Link<T> previous = head;
        Link<T> current = head;
        for (int i = 0; i < index; ++i) {
            previous = current;
            current = current.getNext();
        }
        if (current == head) return removeFirst();
        if (head == tail) tail = null;
        final T temp = current.getItem();
        previous.setNext(current.getNext());
        --size;
        return temp;
    }

    @Override
    public final int size() {
        return size;
    }

    @Override
    public final int indexOf(T item) {
        Link<T> current = head;
        int i = 0;
        while (current != null) {
            final T data = current.getItem();
            if (Objects.equals(data, item)) {
                return i;
            }
            ++i;
            current = current.getNext();
        }
        return -1;
    }

    @Override
    public final T get(int index) {
        validateIndex(index);
        Link<T> current = head;
        for (int i = 0; i < index; ++i) {
            current = current.getNext();
        }
        return current.getItem();
    }

    private void validateIndex(int index) {
        if (index < 0 || index >= size) {
            throw new IllegalArgumentException("Wrong index.");
        }
    }

    /*--------------------------------------------------------*/
    /* Iterable implementation
    /*--------------------------------------------------------*/

    private static final class ArrayStackIterator<T> implements Iterator<T> {

        private Link<T> current;

        ArrayStackIterator(Link<T> head) {
            current = head;
        }

        @Override
        public final boolean hasNext() {
            return current != null;
        }

        @Override
        public final T next() {
            final T temp = current.getItem();
            current = current.getNext();
            return temp;
        }
    }

    @Override
    public final Iterator<T> iterator() {
        return new ArrayStackIterator<>(this.head);
    }

    /**
     * A wrapper to hold an item a pointer to the next wrapper.
     */

    private static final class Link<T> {
        private T item;
        private Link<T> next;

        Link(T item) {
            this.item = item;
        }

        public final T getItem() {
            return item;
        }

        public final void setItem(T item) {
            this.item = item;
        }

        public final Link<T> getNext() {
            return next;
        }

        public final void setNext(Link<T> next) {
            this.next = next;
        }

        @Override
        public final String toString() {
            return (item != null) ? item.toString() : "null";
        }

        @Override
        public final boolean equals(Object object) {
            if (object == null) return false;
            if (object == this) return true;
            if (object.getClass() != getClass()) return false;
            @SuppressWarnings("unchecked")
            final Link<T> that = (Link<T>) object;
            return Objects.equals(this.item, that.item);
        }

        @Override
        public final int hashCode() {
            int result = 17;
            result = result * 31 + (item != null ? item.hashCode() : 0);
            return result;
        }
    }
}
