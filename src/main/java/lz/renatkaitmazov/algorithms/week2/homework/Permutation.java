package lz.renatkaitmazov.algorithms.week2.homework;

import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;

/**
 * @author Renat Kaitmazov
 */

public final class Permutation {
    public static void main(String[] args) {
        if (args.length == 0) {
            return;
        }

        final RandomizedQueue<String> queue = new RandomizedQueue<>();
        while (!StdIn.isEmpty()) {
            queue.enqueue(StdIn.readString());
        }
        final int k = Integer.parseInt(args[0]);
        for (int i = 0; i < k; ++i) {
            StdOut.println(queue.dequeue());
        }
    }
}
