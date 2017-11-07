package lz.renatkaitmazov.algorithms.week1.homework;


import edu.princeton.cs.algs4.WeightedQuickUnionUF;

/**
 * @author Renat Kaitmazov
 */

public final class Percolation {

    private static final int CODE_BLOCKED = -1;


    private final WeightedQuickUnionUF quickUnion;

    private final int[][] grid;

    private final int size;

    private int openedSites;

    public Percolation(int n) {
        if (n < 1) {
            final String msg = String.format("Size must be positive. Your is: %d", n);
            throw new IllegalArgumentException(msg);
        }

        size = n;

        grid = new int[n][n];
        // Set all entries to -1.
        // It means they are blocked.
        initGrid(grid, n);

        // Two additional sites: one for the top site, the other for the bottom one.
        final int quickUnionSize = n * n + 2;
        quickUnion = new WeightedQuickUnionUF(quickUnionSize);
        // Connect to the top virtual site.
        final int topSiteId = 0;
        connectRowToVirtualSite(1, topSiteId);
        // Connect to the bottom virtual site.
        final int bottomSiteId = quickUnionSize - 1;
        connectRowToVirtualSite(size, bottomSiteId);
    }

    /*--------------------------------------------------------*/
    /* API
    /*--------------------------------------------------------*/

    public void open(int row, int col) {
        validate(row, col);
        if (!isOpen(row, col)) {
            final int siteIndex = index(row, col);
            set(row, col, siteIndex);
            ++openedSites;

            if (isRightNeighborOpened(row, col)) {
                quickUnion.union(siteIndex, index(row, col + 1));
            }

            if (isLeftNeighborOpened(row, col)) {
                quickUnion.union(siteIndex, index(row, col - 1));
            }

            if (isTopNeighborOpened(row, col)) {
                quickUnion.union(siteIndex, index(row - 1, col));
            }

            if (isBottomNeighborOpened(row, col)) {
                quickUnion.union(siteIndex, index(row + 1, col));
            }
        }
    }

    private boolean isRightNeighborOpened(int row, int col) {
        if (col + 1 > size) return false;
        return isOpen(row, col + 1);
    }

    private boolean isLeftNeighborOpened(int row, int col) {
        if (col - 1 < 1) return false;
        return isOpen(row, col - 1);
    }

    private boolean isTopNeighborOpened(int row, int col) {
        if (row - 1 < 1) return false;
        return isOpen(row - 1, col);
    }

    private boolean isBottomNeighborOpened(int row, int col) {
        if (row + 1 > size) return false;
        return isOpen(row + 1, col);
    }


    public boolean isOpen(int row, int col) {
        validate(row, col);
        return grid[row - 1][col - 1] > CODE_BLOCKED;
    }

    public boolean isFull(int row, int col) {
        validate(row, col);
        final int topSiteId = 0;
        final int index = index(row, col);
        return quickUnion.connected(topSiteId, index);
    }

    public int numberOfOpenSites() {
        return openedSites;
    }

    public boolean percolates() {
        final int topId = 0;
        final int bottomId = size * size + 1;
        return quickUnion.connected(topId, bottomId);
    }

    /*--------------------------------------------------------*/
    /* Helper methods
    /*--------------------------------------------------------*/

    private void initGrid(final int[][] grid, int size) {
        for (int row = 0; row < size; ++row) {
            for (int col = 0; col < size; ++col) {
                grid[row][col] = CODE_BLOCKED;
            }
        }
    }

    private void connectRowToVirtualSite(int row, int virtualSiteId) {
        for (int col = 1; col <= size; ++col) {
            final int index = index(row, col);
            quickUnion.union(virtualSiteId, index);
        }
    }

    private void validate(int row, int column) {
        if ((row < 1 || row > size) || (column < 1 || column > size)) {
            throw new IllegalArgumentException();
        }
    }

    private void set(int row, int col, int value) {
        grid[row - 1][col - 1] = value;
    }

    private int index(int row, int col) {
        final int actualRow = row - 1;
        final int actualCol = col - 1;
        return (actualRow * size) + actualCol;
    }

    public static void main(String[] args) {
        final Percolation percolation = new Percolation(5);
        percolation.open(1, 1);
        percolation.open(2, 1);
        percolation.open(3, 1);
        percolation.open(4, 1);
        percolation.open(5, 1);
        System.out.println(percolation.isOpen(1, 1));
        System.out.println(percolation.percolates());
    }
}
