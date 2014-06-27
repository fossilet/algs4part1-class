/****************************************************************************
 * Author: Alberto Gutiérrez Jácome <agjacome@gmail.com>
 * Date: 23/08/2012
 * 
 * Compilation: javac PercolationStats.java
 * Execution: java PercolationStats N T
 * Dependencies: Percolation.java StdRandom.java StdStats.java StdOut.java
 * 
 * This class implements the Monte Carlo simulation to estimate the
 * percolation threshold. The program takes the grid size N and the number of
 * independent computational experiments T to perform on that grid. Then, it
 * prints out the mean, standard deviation and the 95% confidence interval for
 * the percolation threshold.
 * 
 ***************************************************************************/

public class PercolationStats
{

    private double [ ] thrs;    // array of T thresholds computed

    // perform T independent computational experiments on an N-by-N grid
    public PercolationStats(int N, int T)
    {
        if (N <= 0 || T <= 0) throw new IllegalArgumentException();

        thrs = new double[T];

        for (int test = 0; test < T; test++) {

            Percolation percolation = new Percolation(N);

            while (!percolation.percolates()) {
                final int x = StdRandom.uniform(1, N + 1);
                final int y = StdRandom.uniform(1, N + 1);

                if (!percolation.isOpen(x, y)) {
                    percolation.open(x, y);
                    thrs[test]++;
                }
            }

            thrs[test] /= N * N;
        }
    }

    // sample mean of percolation threshold
    public double mean()
    {
        return StdStats.mean(thrs);
    }

    // sample standard deviation of percolation threshold
    public double stddev()
    {
        return StdStats.stddev(thrs);
    }

    // returns lower bound of the 95% confidence interval
    public double confidenceLo()
    {
        return mean() - 1.96 * stddev() / Math.pow(thrs.length, 0.5);
    }

    // returns upper bound of the 95% confidence interval
    public double confidenceHi()
    {
        return mean() + 1.96 * stddev() / Math.pow(thrs.length, 0.5);
    }

    // test client
    public static void main(String [ ] args)
    {
        int N = Integer.parseInt(args[0]);
        int T = Integer.parseInt(args[1]);
        PercolationStats test = new PercolationStats(N, T);

        double mean = test.mean();
        double stddev = test.stddev();
        double cinv = 1.96 * stddev / Math.sqrt(T);

        StdOut.println("mean\t\t\t= " + mean);
        StdOut.println("stddev\t\t\t= " + stddev);
        StdOut.println("95% confidence interval\t= " + (mean - cinv) + ", "
                + (mean + cinv));
    }
}
