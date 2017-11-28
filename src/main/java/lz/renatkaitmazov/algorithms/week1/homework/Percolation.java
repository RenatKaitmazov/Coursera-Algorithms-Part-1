package lz.renatkaitmazov.algorithms.week1.homework;

import edu.princeton.cs.algs4.WeightedQuickUnionUF;

/**
 * @author Renat Kaitmazov
 */

public final class Percolation {

    /*--------------------------------------------------------*/
    /* Fields
    /*--------------------------------------------------------*/

    private final int virtualTopId;
    private final int virtualBottomId;
    private final WeightedQuickUnionUF mainUnion;
    private final WeightedQuickUnionUF backwashAwareUnion;
    private final boolean[][] grid;
    private final int size;
    private int openSites;

    /*--------------------------------------------------------*/
    /* Constructors
    /*--------------------------------------------------------*/

    public Percolation(int n) {
        if (n < 1) throw new IllegalArgumentException("n is not positive");
        virtualTopId = 0;
        virtualBottomId = n * n + 1;
        size = n;
        grid = new boolean[n][n];
        final int componentAmount = n * n + 2;
        mainUnion = new WeightedQuickUnionUF(componentAmount);
        backwashAwareUnion = new WeightedQuickUnionUF(componentAmount);
    }

    /*--------------------------------------------------------*/
    /* API
    /*--------------------------------------------------------*/

    public void open(int row, int col) {
        if (isOpen(row, col)) return;
        grid[row - 1][col - 1] = true;
        ++openSites;

        // Transform the row and the column into an appropriate index
        // because the quick union class uses a one-dimensional array.
        final int currentSiteIndex = toIndex(row, col);

        // The topmost row must be connected to the virtual top.
        if (row == 1) {
            mainUnion.union(virtualTopId, currentSiteIndex);
            backwashAwareUnion.union(virtualTopId, currentSiteIndex);
        }
        // The bottommost row must be connected to virtual the bottom.
        if (row == size) mainUnion.union(virtualBottomId, currentSiteIndex);

        // Connect all neighbors of the current site if and only if the neighbors are open.
        if (row > 1 && isOpen(row - 1, col)) {
            final int topNeighborIndex = toIndex(row - 1, col);
            mainUnion.union(topNeighborIndex, currentSiteIndex);
            backwashAwareUnion.union(topNeighborIndex, currentSiteIndex);
        }

        if (row < size && isOpen(row + 1, col)) {
            final int bottomNeighborIndex = toIndex(row + 1, col);
            mainUnion.union(bottomNeighborIndex, currentSiteIndex);
            backwashAwareUnion.union(bottomNeighborIndex, currentSiteIndex);
        }

        if (col > 1 && isOpen(row, col - 1)) {
            final int leftNeighborIndex = toIndex(row, col - 1);
            mainUnion.union(leftNeighborIndex, currentSiteIndex);
            backwashAwareUnion.union(leftNeighborIndex, currentSiteIndex);
        }

        if (col < size && isOpen(row, col + 1)) {
            final int rightNeighborIndex = toIndex(row, col + 1);
            mainUnion.union(rightNeighborIndex, currentSiteIndex);
            backwashAwareUnion.union(rightNeighborIndex, currentSiteIndex);
        }
    }

    public boolean isOpen(int row, int col) {
        validateRowAndColumn(row, col);
        return grid[row - 1][col - 1];
    }

    public boolean isFull(int row, int col) {
        validateRowAndColumn(row, col);
        // A site is considered to be full if and only if it is connected to the top
        // but not to the bottom to avoid backwash bug.
        return backwashAwareUnion.connected(virtualTopId, toIndex(row, col));
    }

    public int numberOfOpenSites() {
        return openSites;
    }

    public boolean percolates() {
        // If the top virtual site is connected to the bottom virtual site,
        // the system percolates.
        return mainUnion.connected(virtualTopId, virtualBottomId);
    }

    /*--------------------------------------------------------*/
    /* Helper methods
    /*--------------------------------------------------------*/

    private void validateRowAndColumn(int row, int col) {
        // row or column must be in range [1, N]
        if ((row < 1 || row > size) || (col < 1 || col > size)) {
            throw new IllegalArgumentException("wrong row or column");
        }
    }

    private int toIndex(int row, int col) {
        final int r = row - 1;
        final int c = col - 1;
        return (size * r) + c + 1;
    }

    public static void main(String[] args) {
        final String path = "src/main/java/lz/renatkaitmazov/algorithms/week1/homework/";
        final String fileName = "percolation.txt";
        PercolationVisualizer.main(new String[]{path + fileName});
    }
}
