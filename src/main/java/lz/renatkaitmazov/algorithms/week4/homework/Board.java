package lz.renatkaitmazov.algorithms.week4.homework;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

/**
 * @author Renat Kaitmazov
 */

public final class Board {

    /*--------------------------------------------------------*/
    /* Fields
    /*--------------------------------------------------------*/

    private final int dimension;
    private final short[][] blocks;
    private final int hamming; // number of blocks out of place.
    private final int manhattan; // // sum of Manhattan distances between blocks and goal.
    private final int emptySpotRow;
    private final int emptySpotCol;

    /*--------------------------------------------------------*/
    /* Constructors
    /*--------------------------------------------------------*/

    public Board(int[][] grid) {
        dimension = grid.length;
        blocks = copy2DArray(grid);
        hamming = calculateHamming(blocks);
        manhattan = calculateManhattan(blocks);
        final int[] rowAndColumn = getEmptySpotRowAndColumn(blocks);
        emptySpotRow = rowAndColumn[0];
        emptySpotCol = rowAndColumn[1];
    }

    private short[][] copy2DArray(final int[][] original) {
        final short[][] copy = new short[dimension][dimension];
        for (int i = 0; i < dimension; ++i) {
            for (int j = 0; j < dimension; ++j) {
                copy[i][j] = (short) original[i][j];
            }
        }
        return copy;
    }

    private int[][] copy2DArray(final short[][] original) {
        final int[][] copy = new int[dimension][dimension];
        for (int i = 0; i < dimension; ++i) {
            for (int j = 0; j < dimension; ++j) {
                copy[i][j] = original[i][j];
            }
        }
        return copy;
    }

    private int calculateHamming(final short[][] grid) {
        int count = 0;
        for (int i = 0; i < dimension; ++i) {
            for (int j = 0; j < dimension; ++j) {
                final int actualValue = grid[i][j];
                final int expectedValue = toValue(i, j);
                if (actualValue != expectedValue) ++count;
            }
        }
        // 0 is always going to be in wrong place because it represent en empty slot, so subtract 1 from
        // the count.
        return count - 1;
    }

    private int calculateManhattan(final short[][] grid) {
        int count = 0;
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

    private int[] getEmptySpotRowAndColumn(final short[][] grid) {
        for (int i = 0; i < dimension; ++i) {
            for (int j = 0; j < dimension; ++j) {
                if (grid[i][j] == 0) return new int[]{i, j};
            }
        }
        // It should never get to this statement, because 0 must always be present in the array.
        return new int[]{dimension - 1, dimension - 1};
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
        for (final short[] row : blocks) {
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
        final int[][] copy = copy2DArray(blocks);
        final int row = (emptySpotRow + 1) % dimension;
        swap(copy, row, 0, row, 1);
        return new Board(copy);
    }

    private void swap(int[][] array, int row1, int col1, int row2, int col2) {
        final int temp = array[row1][col1];
        array[row1][col1] = array[row2][col2];
        array[row2][col2] = temp;
    }

    public Iterable<Board> neighbors() {
        final List<Board> boardList = new LinkedList<>();

        if (emptySpotCol < dimension - 1) {
            final int[][] copy = copy2DArray(blocks);
            swap(copy, emptySpotRow, emptySpotCol, emptySpotRow, emptySpotCol + 1);
            boardList.add(new Board(copy));
        }

        if (emptySpotCol > 0) {
            final int[][] copy = copy2DArray(blocks);
            swap(copy, emptySpotRow, emptySpotCol - 1, emptySpotRow, emptySpotCol);
            boardList.add(new Board(copy));
        }

        if (emptySpotRow < dimension - 1) {
            final int[][] copy = copy2DArray(blocks);
            swap(copy, emptySpotRow, emptySpotCol, emptySpotRow + 1, emptySpotCol);
            boardList.add(new Board(copy));
        }

        if (emptySpotRow > 0) {
            final int[][] copy = copy2DArray(blocks);
            swap(copy, emptySpotRow - 1, emptySpotCol, emptySpotRow, emptySpotCol);
            boardList.add(new Board(copy));
        }

        return boardList;
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
