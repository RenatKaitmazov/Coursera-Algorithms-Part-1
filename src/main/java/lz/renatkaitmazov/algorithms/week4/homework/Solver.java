package lz.renatkaitmazov.algorithms.week4.homework;

import edu.princeton.cs.algs4.MinPQ;

import java.util.*;

/**
 * @author Renat Kaitmazov
 */

public final class Solver {

    /*--------------------------------------------------------*/
    /* Constants
    /*--------------------------------------------------------*/

    private static final Comparator<Board> COMPARATOR = Comparator.comparingInt(Board::hamming);

    /*--------------------------------------------------------*/
    /* Fields
    /*--------------------------------------------------------*/

    private final LinkedList<Board> stepsToGoal = new LinkedList<>();
    private boolean isSolvable = true;
    private int steps;

    /*--------------------------------------------------------*/
    /* Constructors
    /*--------------------------------------------------------*/

    public Solver(Board initial) {
        if (initial == null) {
            throw new IllegalArgumentException("board is null");
        }

        try {
            final MinPQ<Board> boardQueue = new MinPQ<>(COMPARATOR);
            final List<Board> duplicates = new LinkedList<>();
            Board current = initial;
            stepsToGoal.addLast(current);
            duplicates.add(current);
            while (!current.isGoal()) {
                for (final Board neighbor : current.neighbors()) {
                    if (!duplicates.contains(neighbor)) {
                        duplicates.add(neighbor);
                        boardQueue.insert(neighbor);
                    }
                }
                current = boardQueue.delMin();
                stepsToGoal.addLast(current);
                ++steps;
            }
            if (!duplicates.contains(current)) {
                stepsToGoal.addLast(current);
            }
        } catch (NoSuchElementException e) {
            steps = 0;
            isSolvable = false;
        }
    }

    /*--------------------------------------------------------*/
    /* API
    /*--------------------------------------------------------*/

    public boolean isSolvable() {
        return isSolvable;
    }

    public int moves() {
        return steps;
    }

    public Iterable<Board> solution() {
        return stepsToGoal;
    }

    /*--------------------------------------------------------*/
    /* Testing
    /*--------------------------------------------------------*/

    public static void main(String[] args) {
        final int[][] blocks3x3_4 = {
                {0, 1, 3},
                {4, 2, 5},
                {7, 8, 6}
        };
        final Solver solver1 = createSolver(blocks3x3_4);
        assert solver1.moves() == 4;

        final int[][] block2x2_0 = {
                {1, 2},
                {3, 0}
        };
        final Solver solver2 = createSolver(block2x2_0);
        assert solver2.moves() == 0;

        final int[][] block2x2_1 = {
                {1, 2},
                {0, 3}
        };
        final Solver solver3 = createSolver(block2x2_1);
        assert solver3.moves() == 1;

        final int[][] block2x2_6 = {
                {0, 3},
                {2, 1}
        };

        final Solver solver4 = createSolver(block2x2_6);
        assert solver4.moves() == 6;

        final int[][] block3x3_31 = {
                {8, 6, 7},
                {2, 5, 4},
                {3, 0, 1}
        };
        final Solver solver5 = createSolver(block3x3_31);
        assert solver5.moves() == 31;

        final int[][] block4x4_80 = {
                {0, 12, 9, 13},
                {15, 11, 10, 14},
                {3, 7, 5, 6},
                {4, 8, 2, 1}
        };
        final Solver solver6 = createSolver(block4x4_80);
        assert solver6.moves() == 80;
    }

    private static Solver createSolver(int[][] blocks) {
        final Board board = new Board(blocks);
        return new Solver(board);
    }
}
