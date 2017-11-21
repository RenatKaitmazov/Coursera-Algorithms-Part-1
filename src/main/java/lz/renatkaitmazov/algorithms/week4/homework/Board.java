package lz.renatkaitmazov.algorithms.week4.homework;

import java.util.Arrays;
import java.util.LinkedList;

/**
 * @author Renat Kaitmazov
 */

public final class Board {

    /*--------------------------------------------------------*/
    /* Fields
    /*--------------------------------------------------------*/

    private final byte dimension; // 2 ≤ n < 128
    private final int[][] blocks;
    private final short hamming; // number of blocks out of place.
    private final short manhattan; // // sum of Manhattan distances between blocks and goal.

    /*--------------------------------------------------------*/
    /* Constructors
    /*--------------------------------------------------------*/

    public Board(int[][] grid) {
        dimension = (byte) grid.length;
        blocks = copyBlocks(grid, dimension);
        hamming = calculateHamming(blocks);
        manhattan = calculateManhattan(blocks);
    }

    private int[][] copyBlocks(int[][] original, int dimension) {
        final int[][] copy = new int[dimension][dimension];
        for (int i = 0; i < dimension; ++i) {
            System.arraycopy(original[i], 0, copy[i], 0, dimension);
        }
        return copy;
    }

    private short calculateHamming(final int[][] grid) {
        short count = 0;
        for (int i = 0; i < dimension; ++i) {
            for (int j = 0; j < dimension; ++j) {
                final int actualValue = grid[i][j];
                final int expectedValue = toValue(i, j);
                if (actualValue != expectedValue) ++count;
            }
        }
        // 0 is always going to be in wrong place because it represent en empty slot, so subtract 1 from
        // the count.
        return (short) (count - 1);
    }

    private short calculateManhattan(final int[][] grid) {
        short count = 0;
        for (int i = 0; i < dimension; ++i) {
            for (int j = 0; j < dimension; ++j) {
                final int actualValue = grid[i][j];
                final int expectedValue = toValue(i, j);
                if (actualValue == expectedValue || actualValue == 0) continue;
                final int expectedRow = expectedRow(actualValue); // proper row where the actual value should be.
                final int expectedCol = expectedCol(actualValue); // proper column where the actual value should be.
                final int dr = Math.abs(expectedRow - i); // difference in row between expected and actual.
                final int dc = Math.abs(expectedCol - j); // difference in column between expected and actual.
                count += (dr + dc);
            }
        }
        return count;
    }

    private int expectedRow(int value) {
        return (value - 1) / dimension;
    }

    private int expectedCol(int value) {
        return (value - 1) % dimension;
    }

    private int toIndex(int row, int col) {
        return this.dimension * row + col;
    }

    private int toValue(int row, int col) {
        return toIndex(row, col) + 1;
    }

    /*--------------------------------------------------------*/
    /* Object class methods
    /*--------------------------------------------------------*/

    public boolean equals(Object y) {
        if (y == this) return true;
        if (y == null) return false;
        if (y.getClass() != this.getClass()) return false;
        final Board that = (Board) y;
        return Arrays.deepEquals(this.blocks, that.blocks);
    }

    public String toString() {
        final String newLine = "\n";
        final StringBuilder builder = new StringBuilder().append(dimension).append(newLine);
        for (final int[] row : blocks) {
            for (final int column : row) {
                builder.append(column).append(" ");
            }
            builder.deleteCharAt(builder.length() - 1).append(newLine);
        }
        return builder.toString().trim();
    }

    /*--------------------------------------------------------*/
    /* API
    /*--------------------------------------------------------*/

    public int dimension() {
        return dimension;
    }

    public int hamming() {
        return hamming;
    }

    public int manhattan() {
        return manhattan;
    }

    public boolean isGoal() {
        return hamming == 0;
    }

    public Board twin() {
        return this;
    }

    public Iterable<Board> neighbors() {
        return new LinkedList<>();
    }

    /*--------------------------------------------------------*/
    /* Testing
    /*--------------------------------------------------------*/

    public static void main(String[] args) {

    }
}
