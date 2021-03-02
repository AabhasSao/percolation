/* *****************************************************************************
 *  Name:    Aabhas Sao
 *  NetID:   AABHAS
 *  Precept: P00
 *
 *  Description:  percolation stats
 *
 **************************************************************************** */

import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;
import edu.princeton.cs.algs4.Stopwatch;

public class PercolationStats {
    private double[] fractions;

    public PercolationStats(int n, int trials) {
        if (n <= 0 || trials <= 0) {
            throw new IllegalArgumentException();
        }
        fractions = new double[trials];

        for (int i = 0; i < trials; i++) {
            Percolation pc = new Percolation(n);
            while (!pc.percolates()) {
                int row = StdRandom.uniform(n);
                int col = StdRandom.uniform(n);
                pc.open(row, col);
            }
            // System.out.println("f " + pc.numberOfOpenSites() + " ");
            fractions[i] = (double) pc.numberOfOpenSites() / (n * n);
            // System.out.println("f " + fractions[i] + " ");
        }
    }

    // sample mean of percolation threshold
    public double mean() {
        return StdStats.mean(fractions);
    }

    // sample standard deviation of percolation threshold
    public double stddev() {
        return StdStats.stddev(fractions);
    }

    // low endpoint of 95% confidence interval
    public double confidenceLo() {
        return mean() - (1.96 * stddev() / Math.sqrt(fractions.length));
    }

    // high endpoint of 95% confidence interval
    public double confidenceHi() {
        return mean() + (1.96 * stddev() / Math.sqrt(fractions.length));
    }

    public static void main(String[] args) {
        Stopwatch s = new Stopwatch();
        if (args.length == 2) {
            int n = Integer.parseInt(args[0]);
            int T = Integer.parseInt(args[1]);
            PercolationStats pc = new PercolationStats(n, T);
            StdOut.println("mean                    = " + pc.mean());
            StdOut.println("stddev                  = " + pc.stddev());
            StdOut.println(
                    "95% confidence interval = " + "[" + pc.confidenceLo() + ", " + pc
                            .confidenceHi()
                            + "]");
        }
        System.out.println("time elapsed : " + s.elapsedTime());
    }
}
