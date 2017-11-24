package lz.renatkaitmazov.algorithms.week4.homework;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import static org.junit.Assert.*;

/**
 * @author Renat Kaitmazov
 */

@RunWith(JUnit4.class)
public final class SolverTest {

    @Test
    public void grid3By3Move4Test() {
        final int[][] blocks = {
                {0, 1, 3},
                {4, 2, 5},
                {7, 8, 6}
        };
        final Solver solver = createSolver(blocks);
        assertEquals(4, solver.moves());
    }

    @Test
    public void grid2By2Move0Test() {
        final int[][] blocks = {
                {1, 2},
                {3, 0}
        };
        final Solver solver = createSolver(blocks);
        assertEquals(0, solver.moves());
    }

    @Test
    public void grid2By2Move1Test() {
        final int[][] blocks = {
                {1, 2},
                {0, 3}
        };
        final Solver solver = createSolver(blocks);
        assertEquals(1, solver.moves());
    }

    @Test
    public void grid2By2Move6Test() {
        final int[][] blocks = {
                {0, 3},
                {2, 1}
        };
        final Solver solver = createSolver(blocks);
        assertEquals(6, solver.moves());
    }

    @Test
    public void unsolvable2By2Test1() {
        final int[][] blocks = {
                {1, 0},
                {2, 3}
        };
        final Solver solver = createSolver(blocks);
        assertFalse(solver.isSolvable());
        assertEquals(-1, solver.moves());
    }

    @Test
    public void unsolvable2By2Test2() {
        final int[][] blocks = {
                {0, 1},
                {2, 3}
        };
        final Solver solver = createSolver(blocks);
        assertFalse(solver.isSolvable());
        assertEquals(-1, solver.moves());
    }

    @Test
    public void grid3By3Move15Test() {
        final int[][] blocks = {
                {2, 0, 8},
                {1, 3, 5},
                {4, 6, 7}
        };
        final Solver solver = createSolver(blocks);
        assertEquals(15, solver.moves());
    }

    @Test
    public void grid3By3Move31Test() {
        final int[][] blocks = {
                {8, 6, 7},
                {2, 5, 4},
                {3, 0, 1}
        };
        final Solver solver = createSolver(blocks);
        assertEquals(31, solver.moves());
    }

//    @Test
    public void grid4By4Move80() {
        final int[][] blocks = {
                {0, 12, 9, 13},
                {15, 11, 10, 14},
                {3, 7, 5, 6},
                {4, 8, 2, 1}
        };
        final Solver solver = createSolver(blocks);
        assertEquals(80, solver.moves());
    }

    private static Solver createSolver(int[][] blocks) {
        final Board board = new Board(blocks);
        return new Solver(board);
    }
}