package lz.renatkaitmazov.algorithms.week4.homework;

import edu.princeton.cs.algs4.MinPQ;

import java.util.LinkedList;

/**
 * @author Renat Kaitmazov
 */

public final class Solver {

    /*--------------------------------------------------------*/
    /* Fields
    /*--------------------------------------------------------*/

    private final LinkedList<Board> pathToGoal = new LinkedList<>();
    private boolean isSolvable = true;

    /*--------------------------------------------------------*/
    /* Constructors
    /*--------------------------------------------------------*/

    public Solver(Board initial) {
        if (initial == null) {
            throw new IllegalArgumentException("board is null");
        }

        pathToGoal.add(initial);

        SearchNode currentNode = new SearchNode(initial, null, 0);
        final MinPQ<SearchNode> queueOfNodes = new MinPQ<>();
        queueOfNodes.insert(currentNode);
        while (!currentNode.isGoal()) {
            currentNode = queueOfNodes.delMin();
            final Iterable<Board> currentBoardNeighbors = currentNode.neighbors();
            for (final Board neighbor : currentBoardNeighbors) {
                if (!currentNode.predecessorEqualsNeighbor(neighbor)) {
                    final Board predecessor = currentNode.current;
                    final SearchNode nextSearchNode = new SearchNode(neighbor, predecessor, currentNode.moves + 1);
                    queueOfNodes.insert(nextSearchNode);
                }
            }
        }
    }

    /*--------------------------------------------------------*/
    /* API
    /*--------------------------------------------------------*/

    public boolean isSolvable() {
        return isSolvable;
    }

    public int moves() {
        return pathToGoal.size() - 1;
    }

    public Iterable<Board> solution() {
        return pathToGoal;
    }

    /*--------------------------------------------------------*/
    /* Nested classes
    /*--------------------------------------------------------*/

    private static final class SearchNode implements Comparable<SearchNode> {

        private final Board current;
        private final Board predecessor;
        private final int moves;

        public SearchNode(Board current, Board predecessor, int moves) {
            this.current = current;
            this.predecessor = predecessor;
            this.moves = moves;
        }

        @Override
        public int compareTo(SearchNode that) {
            final int thisManhattan = this.current.manhattan();
            final int thatManhattan = that.current.manhattan();
            final int thisPriority = thisManhattan + this.moves;
            final int thatPriority = thatManhattan + that.moves;
            if (thisPriority < thatPriority) return -1;
            if (thisPriority > thatPriority) return +1;
            final int thisHamming = this.current.hamming();
            final int thatHamming = that.current.hamming();
            return Integer.compare(thisHamming + this.moves, thatHamming + that.moves);
        }

        public boolean predecessorEqualsNeighbor(Board neighbor) {
            return predecessor != null && predecessor.equals(neighbor);
        }

        public boolean isGoal() {
            return current.isGoal();
        }

        public Iterable<Board> neighbors() {
            return current.neighbors();
        }

        @Override
        public String toString() {
            return String.format("moves: %d\nCurrent: %s", moves, current);
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
    }
}
