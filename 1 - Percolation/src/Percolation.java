/****************************************************************************
 * Author: Alberto Gutiérrez Jácome <agjacome@gmail.com>
 * Date: 23/08/2012
 * 
 * Compilation: javac Percolation.java
 * Execution: not applicable
 * Dependencies: WeightedQuickUnionUF.java
 * 
 * This class implements a percolation system modeled using a N-by-N grid of
 * sites. Each site is either open or blocked. A full site is an open site
 * that can be connected to an open site in the top row via a chain of
 * neighboring (left, right, up, down) open sites. The system percolates if
 * there is a full site in the bottom row. In other words, a system percolates
 * if we fill all open sites connected to the top row and that process fills
 * some open site on the bottom row.
 * 
 ***************************************************************************/

public class Percolation
{

    private int                  size;  // size of the grid (size-by-size)
    private boolean [ ][ ]       grid;  // grid of open/close sites
    private WeightedQuickUnionUF path;  // union-find to check percolation
    private WeightedQuickUnionUF wash;  // union-find to handle back-wash

    private final int            VTOP;  // virtual top for union-find
    private final int            VBTM;  // virtual bottom for union-find

    // create a N-by-N grid, with all sites blocked
    public Percolation(int N)
    {
        if (N <= 0) throw new IllegalArgumentException();
        size = N;
        grid = new boolean[N][N];
        path = new WeightedQuickUnionUF(N * N + 2);
        wash = new WeightedQuickUnionUF(N * N + 1);

        VTOP = 0;
        VBTM = N * N + 1;
    }

    // check if a given pair (row, column) is inside the grid range
    private boolean isInRange(int i, int j)
    {
        return (i >= 1 && j >= 1 && i <= size && j <= size);
    }

    // returns the one-dimensional index of (row i, column j) pair
    private int getIndex(int i, int j)
    {
        return (i - 1) * size + j;
    }

    // creates union (in path and wash) between p and q indexes
    private void makeUnion(int p, int q)
    {
        path.union(p, q);
        wash.union(p, q);
    }

    // open site (row i, column j) if it is not already
    public void open(int i, int j)
    {
        if (isOpen(i, j)) return;

        grid[i - 1][j - 1] = true;
        final int index = getIndex(i, j);

        if (isInRange(i + 1, j) && isOpen(i + 1, j))
            makeUnion(index, index + size);
        if (isInRange(i - 1, j) && isOpen(i - 1, j))
            makeUnion(index, index - size);
        if (isInRange(i, j + 1) && isOpen(i, j + 1))
            makeUnion(index, index + 1);
        if (isInRange(i, j - 1) && isOpen(i, j - 1))
            makeUnion(index, index - 1);

        if (i == 1) makeUnion(index, VTOP);
        if (i == size) path.union(index, VBTM);
    }

    // is site (row i, column j) open?
    public boolean isOpen(int i, int j)
    {
        if (!isInRange(i, j)) throw new IndexOutOfBoundsException();
        return grid[i - 1][j - 1];
    }

    // is site (row i, column j) full?
    public boolean isFull(int i, int j)
    {
        return isOpen(i, j) && wash.connected(getIndex(i, j), VTOP);
    }

    // does the system percolate?
    public boolean percolates()
    {
        return path.connected(VTOP, VBTM);
    }

}
