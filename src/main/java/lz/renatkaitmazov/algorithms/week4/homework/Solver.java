package lz.renatkaitmazov.algorithms.week4.homework;

import edu.princeton.cs.algs4.MinPQ;

import java.util.HashSet;

/**
 * @author Renat Kaitmazov
 */

public final class Solver {

    /*--------------------------------------------------------*/
    /* Fields
    /*--------------------------------------------------------*/

    private final MinPQ<Board> queue = new MinPQ<>((lhs, rhs) -> {
        final int lhsHamming = lhs.hamming();
        final int rhsHamming = rhs.hamming();
        return Integer.compare(lhsHamming, rhsHamming);
    });

    /*--------------------------------------------------------*/
    /* Constructors
    /*--------------------------------------------------------*/

    public Solver(Board initial) {
        if (initial == null) {
            throw new IllegalArgumentException("board is null");
        }
        queue.insert(initial);
    }

    /*--------------------------------------------------------*/
    /* API
    /*--------------------------------------------------------*/

    public boolean isSolvable() {
        return true;
    }

    public int moves() {
        return 0;
    }

    public Iterable<Board> solution() {
        return new HashSet<>();
    }

    /*--------------------------------------------------------*/
    /* Testing
    /*--------------------------------------------------------*/

    public static void main(String[] args) {

    }
}
