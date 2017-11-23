package lz.renatkaitmazov.algorithms.week4.homework;

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.MinPQ;
import edu.princeton.cs.algs4.StdOut;

import java.util.LinkedList;

/**
 * @author Renat Kaitmazov
 */

public final class Solver {

    /*--------------------------------------------------------*/
    /* Fields
    /*--------------------------------------------------------*/

    private final LinkedList<Board> path = new LinkedList<>();

    /*--------------------------------------------------------*/
    /* Constructors
    /*--------------------------------------------------------*/

    public Solver(Board initial) {
        if (initial == null) {
            throw new IllegalArgumentException("board is null");
        }

        // Assume that it possible to solve the puzzle with the given board.
        final Board solvable = initial;
        // If it is not the case, then the board with any two digits swapped must be unsolvable.
        final Board unsolvable = solvable.twin();

        // Store unvisited nodes prioritized by their heuristic:
        // Manhattan distance + number of moves to the node from the initial node.
        final MinPQ<Node> solvableNodes = new MinPQ<>();
        final MinPQ<Node> unsolvableNodes = new MinPQ<>();

        // Stating nodes.
        Node solvableNode = new Node(solvable, null, 0);
        Node unsolvableNode = new Node(unsolvable, null, 0);

        // The start of the path to the goal is always the board we are given.
        path.add(solvable);

        for (;;) {
            if (unsolvableNode.isGoal()) {
                // The assumption was wrong.
                // The initial board given is actually unsolvable.
                path.clear();
                return;
            }

            if (solvableNode.isGoal()) {
                System.out.println(path.getLast());
                // The assumption is correct. The puzzle is solved.
                return;
            }

            addNeighbors(solvableNode, solvableNodes);
            addNeighbors(unsolvableNode, unsolvableNodes);

            solvableNode = solvableNodes.delMin();
            unsolvableNode = unsolvableNodes.delMin();

            if (path.getLast().manhattan() >= solvableNode.current.manhattan()) {
                // TODO Wrong logic of defining the final path.
                path.add(solvableNode.current);
            }
        }
    }

    private void addNeighbors(Node node, MinPQ<Node> nodes) {
        for (final Board neighbor : node.neighbors()) {
            if (node.canAddNeighbor(neighbor)) {
                final Node next = new Node(neighbor, node.current, node.moves + 1);
                nodes.insert(next);
            }
        }
    }


    /*--------------------------------------------------------*/
    /* API
    /*--------------------------------------------------------*/

    public boolean isSolvable() {
        return !path.isEmpty();
    }

    public int moves() {
        return path.size() - 1;
    }

    public Iterable<Board> solution() {
        return path;
    }

    /*--------------------------------------------------------*/
    /* Nested classes
    /*--------------------------------------------------------*/

    private static final class Node implements Comparable<Node> {
        private final Board current;
        private final Board predecessor;
        private final int moves;

        public Node(Board current, Board predecessor, int moves) {
            this.current = current;
            this.predecessor = predecessor;
            this.moves = moves;
        }

        @Override
        public int compareTo(Node that) {
            final int thisPriority = this.current.manhattan() + this.moves;
            final int thatPriority = that.current.manhattan() + that.moves;
            if (thisPriority > thatPriority) return -1;
            if (thisPriority < thatPriority) return +1;
            return 0;
        }

        public boolean canAddNeighbor(Board neighbor) {
            return !neighbor.equals(predecessor);
        }

        public boolean isGoal() {
            return current.isGoal();
        }

        public Iterable<Board> neighbors() {
            return current.neighbors();
        }

        @Override
        public String toString() {
            return String.format("moves: %d\npriority: %d\nCurrent: %s", moves, current.manhattan(), current);
        }
    }

    /*--------------------------------------------------------*/
    /* Testing
    /*--------------------------------------------------------*/

    /**
     * For debugging and testing.
     * The assignment requires that I include the main method.
     * Normally, I test everything using JUnit.
     *
     * @param args command line arguments.
     */
    public static void main(String[] args) {
        final String path = "src/main/java/lz/renatkaitmazov/algorithms/week4/homework/";
        final String filename = "puzzle.txt";
        final In in = new In(path + filename);
        final int n = in.readInt();
        final int[][] tiles = new int[n][n];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                tiles[i][j] = in.readInt();
            }
        }

        // solve the slider puzzle
        final Board initial = new Board(tiles);
        final Solver solver = new Solver(initial);
        StdOut.println(filename + ": " + solver.moves());
    }
}
