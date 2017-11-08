package lz.renatkaitmazov.algorithms.week2.homework;

import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * Dequeue.
 * A double-ended queue or deque (pronounced “deck”) is a generalization of a stack and a queue
 * that supports adding and removing items from either the front or the back of the data
 * structure. Create a generic data type Deque that implements the following API:
 * <p>
 * public Deque()                           // construct an empty deque
 * public boolean isEmpty()                 // is the deque empty?
 * public int size()                        // return the number of items on the deque
 * public void addFirst(Item item)          // add the item to the front
 * public void addLast(Item item)           // add the item to the end
 * public Item removeFirst()                // remove and return the item from the front
 * public Item removeLast()                 // remove and return the item from the end
 * public Iterator<Item> iterator()         // return an iterator over items in order from front to end
 * <p>
 * Performance requirements.
 * Your deque implementation must support each deque operation (including construction) in constant worst-case time.
 * A deque containing n items must use at most 48n + 192 bytes of memory and use space proportional to the number
 * of items currently in the deque. Additionally, your iterator implementation must support each operation
 * (including construction) in constant worst-case time.
 *
 * @author Renat Kaitmazov
 */

public final class Deque<Item> implements Iterable<Item> {

    /*--------------------------------------------------------*/
    /* Fields
    /*--------------------------------------------------------*/

    private final DoublyLinkedList<Item> deque = new DoublyLinkedList<>();

    /*--------------------------------------------------------*/
    /* Nested classes
    /*--------------------------------------------------------*/

    private static final class Link<T> {
        private Link<T> previous;
        private Link<T> next;
        private final T item;

        Link(T item) {
            this.item = item;
        }

        @Override
        public String toString() {
            return item != null ? item.toString() : "null";
        }
    }

    private static final class DoublyLinkedList<T> implements Iterable<T> {
        private Link<T> head;
        private Link<T> tail;
        private int size;

        boolean isEmpty() {
            return size == 0;
        }

        int size() {
            return size;
        }

        void addLast(T item) {
            final Link<T> newLink = new Link<>(item);
            if (isEmpty()) {
                head = newLink;
            } else {
                tail.next = newLink;
            }
            newLink.previous = tail;
            tail = newLink;
            ++size;
        }

        void addFirst(T item) {
            final Link<T> newLink = new Link<>(item);
            if (isEmpty()) {
                tail = newLink;
            } else {
                head.previous = newLink;
            }
            newLink.next = head;
            head = newLink;
            ++size;
        }

        T removeFirst() {
            checkNotEmpty();
            return remove(head);
        }

        T removeLast() {
            checkNotEmpty();
            return remove(tail);
        }

        private void checkNotEmpty() {
            if (isEmpty()) {
                throw new NoSuchElementException("List is empty.");
            }
        }

        private T remove(Link<T> link) {
            final T item = link.item;
            final Link<T> next = link.next;
            final Link<T> previous = link.previous;
            if (head == tail) {
                // There is only one element in the list.
                head = null;
                tail = null;
            } else if (previous == null) {
                // Remove the head.
                next.previous = null;
                head = next;
            } else if (next == null) {
                // Remove the tail.
                previous.next = null;
                tail = previous;
            } else {
                // Remove some arbitrary link between the head and the tail.
                previous.next = next;
                next.previous = previous;
            }
            --size;
            return item;
        }

        @Override
        public Iterator<T> iterator() {
            return new DoublyLinkedListIterator<>(this);
        }

        @Override
        public String toString() {
            if (isEmpty()) {
                return "[]";
            }
            final StringBuilder builder = new StringBuilder("[");
            Link<T> current = head;
            while (current != null) {
                builder.append(current).append(", ");
                current = current.next;
            }
            final int end = builder.length();
            final int start = end - 2;
            return builder.replace(start, end, "]")
                    .toString();
        }
    }

    private static final class DoublyLinkedListIterator<T> implements Iterator<T> {
        private Link<T> current;

        DoublyLinkedListIterator(DoublyLinkedList<T> list) {
            current = list.head;
        }

        @Override
        public boolean hasNext() {
            return current != null;
        }

        @Override
        public T next() {
            if (!hasNext()) {
                throw new NoSuchElementException();
            }
            final T temp = current.item;
            current = current.next;
            return temp;
        }
    }

    /*--------------------------------------------------------*/
    /* Constructors
    /*--------------------------------------------------------*/

    public Deque() {
    }

    /*--------------------------------------------------------*/
    /* API
    /*--------------------------------------------------------*/

    public boolean isEmpty() {
        return deque.isEmpty();
    }

    public int size() {
        return deque.size();
    }

    public void addFirst(Item item) {
        checkNotNull(item);
        deque.addFirst(item);
    }

    public void addLast(Item item) {
        checkNotNull(item);
        deque.addLast(item);
    }

    public Item removeFirst() {
        return deque.removeFirst();
    }

    public Item removeLast() {
        return deque.removeLast();
    }

    /*--------------------------------------------------------*/
    /* Iterable implementation
    /*--------------------------------------------------------*/

    @Override
    public Iterator<Item> iterator() {
        return deque.iterator();
    }

//    @Override
//    public final String toString() {
//        return deque.toString();
//    }

    /*--------------------------------------------------------*/
    /* Helper
    /*--------------------------------------------------------*/

    private void checkNotNull(Item item) {
        if (item == null) {
            throw new IllegalArgumentException("Null is not allowed.");
        }
    }
}
