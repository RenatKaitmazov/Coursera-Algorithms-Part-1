package lz.renatkaitmazov.algorithms.week4.homework;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.util.List;

import static org.junit.Assert.*;

/**
 * @author Renat Kaitmazov
 */

@RunWith(JUnit4.class)
public final class BoardTest {

    private Board board;
    private int[][] block;

    @Before
    public void setUp() {
        block = new int[][]{
                {1, 2, 0},
                {3, 4, 5},
                {6, 7, 8}
        };
        board = new Board(block);
    }

    @After
    public void tearDown() {
        board = null;
        block = null;
    }

    @Test
    public void constructorCopiesTheOriginalArrayTest() {
        block[0][0] = 100;
        final String expected = "3\n1 2 0\n3 4 5\n6 7 8";
        assertEquals(expected, board.toString());
    }

    @Test
    public void dimensionTest() {
        assertEquals(3, board.dimension());
    }

    @Test
    public void hammingTest() {
        assertEquals(6, board.hamming());

        int[][] newBlocks = {
                {8, 1, 3},
                {4, 0, 2},
                {7, 6, 5}
        };
        Board newBoard = new Board(newBlocks);
        assertEquals(5, newBoard.hamming());

        newBlocks = new int[][]{
                {0, 1, 2},
                {3, 4, 5},
                {6, 7, 8}
        };
        newBoard = new Board(newBlocks);
        assertEquals(8, newBoard.hamming());
    }

    @Test
    public void toStringTest() {
        final String expected = "3\n1 2 0\n3 4 5\n6 7 8";
        assertEquals(expected, board.toString());
    }

    @Test
    public void equalsTest() {
        final Board equalCopy = new Board(block);
        assertTrue(board.equals(equalCopy));

        block[0][0] = 100;
        final Board notEqualCopy = new Board(block);
        assertFalse(board.equals(notEqualCopy));
    }

    @Test
    public void manhattanTest() {
        assertEquals(10, board.manhattan());
    }

    @Test
    public void falseGoalTest() {
        assertFalse(board.isGoal());
    }

    @Test
    public void trueGoalTest() {
        final int[][] newBlocks = {
                {1, 2, 3},
                {4, 5, 6},
                {7, 8, 0}
        };

        final Board completedBoard = new Board(newBlocks);
        assertTrue(completedBoard.isGoal());
    }

    @Test
    public void neighborsTest() {
        final List<Board> neighbors = (List<Board>) board.neighbors();
        assertEquals(2, neighbors.size());

        final int[][] blocks1 = {
                {1, 0, 2},
                {3, 4, 5},
                {6, 7, 8}
        };
        final Board board1 = new Board(blocks1);
        assertTrue(neighbors.contains(board1));

        final int[][] blocks2 = {
                {1, 2, 5},
                {3, 4, 0},
                {6, 7, 8}
        };
        final Board board2 = new Board(blocks2);
        assertTrue(neighbors.contains(board2));
    }

    @Test
    public void twinTest() {
        final int[][] blocks1 = {
                {1, 0},
                {3, 2}
        };
        final Board board1 = new Board(blocks1);
        final String expected = "2\n1 0\n2 3";
        assertEquals(expected, board1.twin().toString());
    }
}