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
    private final WeightedQuickUnionUF quickUnion;
    private final boolean[][] grid;
    private final int size;
    private int openSites;

    /*--------------------------------------------------------*/
    /* Constructors
    /*--------------------------------------------------------*/

    public Percolation(int n) {
        if (n < 1) {
            throw new IllegalArgumentException("n is not positive");
        }
        virtualTopId = 0;
        virtualBottomId = n * n + 1;
        size = n;
        grid = new boolean[n][n];
        quickUnion = new WeightedQuickUnionUF(n * n + 2);
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
        if (row == 1)       quickUnion.union(virtualTopId, currentSiteIndex);
        // The bottommost row must be connected to virtual the bottom.
        if (row == size)    quickUnion.union(virtualBottomId, currentSiteIndex);

        // Connect all neighbors of the current site if and only if the neighbors are open.
        if (row > 1     && isOpen(row - 1, col)) quickUnion.union(toIndex(row - 1, col), currentSiteIndex); // Top neighbor.
        if (row < size  && isOpen(row + 1, col)) quickUnion.union(toIndex(row + 1, col), currentSiteIndex); // Bottom neighbor.
        if (col > 1     && isOpen(row, col - 1)) quickUnion.union(toIndex(row, col - 1), currentSiteIndex); // Left neighbor.
        if (col < size  && isOpen(row, col + 1)) quickUnion.union(toIndex(row, col + 1), currentSiteIndex); // Right neighbor.
    }

    public boolean isOpen(int row, int col) {
        validateRowAndColumn(row, col);
        return grid[row - 1][col - 1];
    }

    public boolean isFull(int row, int col) {
        validateRowAndColumn(row, col);
        // A site is considered to be full if and only if it is connected to the top
        // but not to the bottom to avoid backwash bug.
        return quickUnion.connected(virtualTopId, toIndex(row, col));
    }

    public int numberOfOpenSites() {
        return openSites;
    }

    public boolean percolates() {
        // If the top virtual site is connected to the bottom virtual site,
        // the system percolates.
        return quickUnion.connected(virtualTopId, virtualBottomId);
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
        InteractivePercolationVisualizer.main(args);
    }
}
