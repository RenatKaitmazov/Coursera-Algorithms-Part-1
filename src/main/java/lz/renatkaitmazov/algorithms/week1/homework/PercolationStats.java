package lz.renatkaitmazov.algorithms.week1.homework;

import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;

/**
 * @author Renat Kaitmazov
 */

public final class PercolationStats {

    private final double mean;
    private final double stdDeviation;
    private final int trials;

    public PercolationStats(int n, int trials) {
        if (n < 1 || trials < 1) {
            throw new IllegalArgumentException("wrong arguments");
        }
        this.trials = trials;
        final double[] results = new double[trials];
        for (int i = 0; i < trials; ++i) {
            final Percolation percolation = new Percolation(n);
            while (!percolation.percolates()) {
                final int row = StdRandom.uniform(1, n);
                final int col = StdRandom.uniform(1, n);
                percolation.open(row, col);
            }
            results[i] = (double) percolation.numberOfOpenSites() / n * n;
        }
        mean = StdStats.mean(results);
        stdDeviation = StdStats.stddev(results);
    }

    public double mean() {
        return 0.0; // mean;
    }

    public double stddev() {
        return 0.0; // stdDeviation;
    }


    public double confidenceLo() {
        return 0.0; // mean - (1.96 / Math.sqrt(trials));
    }


    public double confidenceHi() {
        // high endpoint of 95% confidence interval
        return 0.0; //mean + (1.96 / Math.sqrt(trials));
    }

    public static void main(String[] args) {
    }
}
