package lz.renatkaitmazov.algorithms.week2.homework;

import edu.princeton.cs.algs4.StdRandom;

import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * Randomized queue.
 * A randomized queue is similar to a stack or queue, except that the item removed is chosen
 * uniformly at random from items in the data structure. Create a generic data type
 * RandomizedQueue that implements the following API:
 * <p>
 * public RandomizedQueue()                 // construct an empty randomized queue
 * public boolean isEmpty()                 // is the randomized queue empty?
 * public int size()                        // return the number of items on the randomized queue
 * public void enqueue(Item item)           // add the item
 * public Item dequeue()                    // remove and return a random item
 * public Item sample()                     // return a random item (but do not remove it)
 * public Iterator<Item> iterator()         // return an independent iterator over items in random order
 * <p>
 * Your randomized queue implementation must support each randomized queue operation
 * (besides creating an iterator) in constant amortized time.
 * That is, any sequence of m randomized queue operations (starting from an empty queue) must
 * take at most cm steps in the worst case, for some constant c. A randomized queue containing n
 * items must use at most 48n + 192 bytes of memory. Additionally, your iterator
 * implementation must support operations next() and hasNext() in constant worst-case time;
 * and construction in linear time; you may (and will need to) use a linear amount of extra
 * memory per iterator.
 *
 * @author Renat Kaitmazov
 */

public final class RandomizedQueue<Item> implements Iterable<Item> {

    /*--------------------------------------------------------*/
    /* Static
    /*--------------------------------------------------------*/

    private static final int DEFAULT_CAPACITY = 1;

    /*--------------------------------------------------------*/
    /* Fields
    /*--------------------------------------------------------*/

    private Item[] items;
    private int size;

    /*--------------------------------------------------------*/
    /* Nested classes
    /*--------------------------------------------------------*/

    private static final class RandomizedQueueIterator<T> implements Iterator<T> {

        private final T[] items;
        private int current;

        RandomizedQueueIterator(T[] items) {
            this.items = items;
        }

        @Override
        public boolean hasNext() {
            return current < items.length;
        }

        @Override
        public T next() {
            if (!hasNext()) {
                throw new NoSuchElementException();
            }
            return items[current++];
        }
    }

    /*--------------------------------------------------------*/
    /* Constructors
    /*--------------------------------------------------------*/

    public RandomizedQueue() {
        items = (Item[]) new Object[DEFAULT_CAPACITY];
    }

    /*--------------------------------------------------------*/
    /* API
    /*--------------------------------------------------------*/

    public boolean isEmpty() {
        return size == 0;
    }

    public int size() {
        return size;
    }

    public void enqueue(Item item) {
        ensureNotNull(item);
        if (isFull()) {
            final int newSize = size + (size >> 1) + 1;
            resize(newSize);
        }
        items[size++] = item;
        final int randomIndex = StdRandom.uniform(size);
        swap(items, randomIndex, size - 1);
    }

    public Item dequeue() {
        ensureNotEmpty();
        final int randomIndex = StdRandom.uniform(size);
        swap(items, randomIndex, --size);
        final Item itemToDequeue = items[size];
        items[size] = null; // Avoid loitering
        final int capacity = items.length;
        if ((capacity >> 2) >= size) {
            resize(capacity >> 1);
        }
        return itemToDequeue;
    }

    public Item sample() {
        ensureNotEmpty();
        final int randomIndex = StdRandom.uniform(size);
        return items[randomIndex];
    }

    public Iterator<Item> iterator() {
        final Item[] copy = (Item[]) new Object[size];
        System.arraycopy(items, 0, copy, 0, size);
        StdRandom.shuffle(copy);
        return new RandomizedQueueIterator<>(copy);
    }

    private void ensureNotNull(Item item) {
        if (item == null) {
            throw new IllegalArgumentException("Null is not allowed.");
        }
    }

    private void ensureNotEmpty() {
        if (isEmpty()) {
            throw new NoSuchElementException("Queue is empty.");
        }
    }

    private boolean isFull() {
        return size == items.length;
    }

    private void resize(int newSize) {
        final Item[] newItems = (Item[]) new Object[newSize];
        System.arraycopy(items, 0, newItems, 0, size);
        items = newItems;
    }

    private void swap(Item[] array, int i, int j) {
        if (i == j) {
            return;
        }
        final Item temp = array[i];
        array[i] = array[j];
        array[j] = temp;
    }
}
