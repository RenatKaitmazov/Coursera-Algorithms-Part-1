package lz.renatkaitmazov.algorithms.week1.homework;

import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;

/**
 * @author Renat Kaitmazov
 */

public final class PercolationStats {

    /*--------------------------------------------------------*/
    /* Constants
    /*--------------------------------------------------------*/

    private static final double CONFIDENCE = 1.96;

    /*--------------------------------------------------------*/
    /* Fields
    /*--------------------------------------------------------*/

    private final double mean;
    private final double stdDeviation;
    private final int trials;

    /*--------------------------------------------------------*/
    /* Constructors
    /*--------------------------------------------------------*/

    public PercolationStats(int n, int trials) {
        if (n < 1 || trials < 1) throw new IllegalArgumentException("wrong arguments");
        this.trials = trials;
        final double[] results = new double[trials];
        final int size = n * n;
        for (int i = 0; i < trials; ++i) {
            final Percolation percolation = new Percolation(n);
            while (!percolation.percolates()) {
                final int row = StdRandom.uniform(n) + 1;
                final int col = StdRandom.uniform(n) + 1;
                percolation.open(row, col);
            }
            results[i] = (double) percolation.numberOfOpenSites() / size;
        }
        mean = StdStats.mean(results);
        stdDeviation = StdStats.stddev(results);
    }

    /*--------------------------------------------------------*/
    /* API
    /*--------------------------------------------------------*/

    public double mean() {
        return mean;
    }

    public double stddev() {
        return stdDeviation;
    }


    public double confidenceLo() {
        return mean - (CONFIDENCE * stdDeviation / Math.sqrt(trials));
    }


    public double confidenceHi() {
        return mean + (CONFIDENCE * stdDeviation / Math.sqrt(trials));
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
