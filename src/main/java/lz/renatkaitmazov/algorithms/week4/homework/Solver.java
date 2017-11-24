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

    private LinkedList<Board> path = new LinkedList<>();

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
        final LinkedList<Node> visitedNodes = new LinkedList<>();

        // Stating nodes.
        Node solvableNode = new Node(solvable, null, 0);
        Node unsolvableNode = new Node(unsolvable, null, 0);

        // The start of the path to the goal is always the board we are given.
        visitedNodes.add(solvableNode);
//        path.add(solvable);

        for (;;) {
            if (unsolvableNode.isGoal()) {
                // The assumption was wrong.
                // The initial board given is actually unsolvable.
                path = null;
                return;
            }

            if (solvableNode.isGoal()) {
                // The assumption is correct. The puzzle is solved.
                break;
            }

            addUnvisitedNodes(solvableNode, solvableNodes);
            addUnvisitedNodes(unsolvableNode, unsolvableNodes);

            solvableNode = solvableNodes.delMin();
            unsolvableNode = unsolvableNodes.delMin();

            // Make a stack of visited nodes.
            // In order to be able to resolve the final path, we need to start
            // from the last added node up to the initial node following the chain of predecessors.
            visitedNodes.addFirst(solvableNode);
        }
        resolvePath(visitedNodes);
    }

    private void resolvePath(LinkedList<Node> visitedStackOfNodes) {
        // Start finding the path from the last added node.
        Node current = visitedStackOfNodes.removeFirst();
        path.addFirst(current.current);
        for (final Node node : visitedStackOfNodes) {
            if (current.predecessor != node.current) {
                continue;
            }
            path.addFirst(node.current);
            current = node;
        }
    }

    private void addUnvisitedNodes(Node startNode, MinPQ<Node> unvisitedNodesQueue) {
        for (final Board neighbor : startNode.neighbors()) {
            if (startNode.canAddNeighbor(neighbor)) {
                final Node next = new Node(neighbor, startNode.current, startNode.moves + 1);
                unvisitedNodesQueue.insert(next);
            }
        }
    }


    /*--------------------------------------------------------*/
    /* API
    /*--------------------------------------------------------*/

    public boolean isSolvable() {
        return path != null;
    }

    public int moves() {
        return path != null ? path.size() - 1 : -1;
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
        private final int manhattan;

        public Node(Board current, Board predecessor, int moves) {
            this.current = current;
            this.predecessor = predecessor;
            this.moves = moves;
            this.manhattan = current.manhattan();
        }

        @Override
        public int compareTo(Node that) {
            if (manhattan + this.moves < that.manhattan + that.moves) return -1;
            if (manhattan + this.moves > that.manhattan + that.moves) return +1;
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
