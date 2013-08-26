/****************************************************************************
 * Author: Alberto Gutiérrez Jácome <agjacome@gmail.com>
 * Date: 07/09/2012
 * 
 * Compilation: javac Board.java
 * Execution: not applicable
 * Dependencies: StdRandom.java Stack.java
 * 
 * Description: This class implements an immutable data type for a 8-Puzzle
 * board. The 8-puzzle problem is a puzzle invented and popularized by Noyes
 * Palmer Chapman in the 1870s. It is played on a 3-by-3 grid with 8 square
 * blocks labeled 1 through 8 and a blank square. Your goal is to rearrange
 * the blocks so that they are in order, using as few moves as possible. You
 * are permitted to slide blocks horizontally or vertically into the blank
 * square. This type is used to represent any state of the board (a sequence
 * of numbers in some order) at any time.
 * 
 ***************************************************************************/

import java.util.Arrays;

public class Board
{
    private final char [ ] grid;  // the actual board
    private final int      size;  // length of the board (square: N-by-N)
    private final char [ ] solv;  // the solution of this board

    // construct a board from a N-by-N array of blocks
    public Board(final int [ ][ ] blocks)
    {
        size = blocks.length;
        grid = new char[size * size];
        solv = new char[size * size];

        int zero = 0;
        for (int i = 0; i < size * size; i++) {
            grid[i] = (char) blocks[i / size][i % size];
            solv[i] = (char) blocks[i / size][i % size];
            if (grid[i] == 0) zero = i;
        }

        final char swap = solv[zero];
        solv[zero] = solv[size * size - 1];
        solv[size * size - 1] = swap;
        Arrays.sort(solv, 0, size * size - 1);
    }

    // board dimension N
    public int dimension()
    {
        return size;
    }

    // does this block equal y?
    public boolean equals(final Object y)
    {
        if (y == this) return true;
        if (y == null) return false;
        if (y.getClass() != this.getClass()) return false;

        final Board that = (Board) y;
        return that.size == size && Arrays.equals(that.grid, grid);
    }

    // number of blocks out of place
    public int hamming()
    {
        int count = 0;
        for (int i = 0; i < size * size; i++)
            if (grid[i] != 0 && grid[i] != solv[i]) count++;
        return count;
    }

    // is this board the goal board?
    public boolean isGoal()
    {
        return Arrays.equals(grid, solv);
    }

    // sum of Manhattan distances between blocks and goal
    public int manhattan()
    {
        int sum = 0;
        for (int i = 0; i < size * size; i++)
            if (grid[i] != 0 && grid[i] != solv[i]) {
                final int pos = Arrays.binarySearch(solv, grid[i]);
                final int rowdiff = pos / size - i / size;
                final int coldiff = pos % size - i % size;
                sum = sum + Math.abs(rowdiff) + Math.abs(coldiff);
            }

        return sum;
    }

    // all neighboring boards: left, right, up, down
    public Iterable<Board> neighbors()
    {
        final Stack<Board> stack = new Stack<Board>();

        int pos = 0;
        final int [ ][ ] copy = new int[size][size];
        for (int i = 0; i < size * size; i++) {
            copy[i / size][i % size] = grid[i];
            if (grid[i] == 0) pos = i;
        }

        // left
        if (pos % size - 1 >= 0) {
            swap(copy, pos / size, pos % size - 1, pos / size, pos % size);
            stack.push(new Board(copy));
            swap(copy, pos / size, pos % size - 1, pos / size, pos % size);
        }

        // right
        if (pos % size + 1 < size) {
            swap(copy, pos / size, pos % size + 1, pos / size, pos % size);
            stack.push(new Board(copy));
            swap(copy, pos / size, pos % size + 1, pos / size, pos % size);
        }

        // up
        if (pos / size - 1 >= 0) {
            swap(copy, pos / size - 1, pos % size, pos / size, pos % size);
            stack.push(new Board(copy));
            swap(copy, pos / size - 1, pos % size, pos / size, pos % size);
        }

        // down
        if (pos / size + 1 < size) {
            swap(copy, pos / size + 1, pos % size, pos / size, pos % size);
            stack.push(new Board(copy));
            swap(copy, pos / size + 1, pos % size, pos / size, pos % size);
        }

        return stack;
    }

    // swap two coordinates i(x,y) and j(x,y) in a given 2D-array
    private void swap(final int [ ][ ] a, final int ix, final int iy,
            final int jx, final int jy)
    {
        final int swap = a[ix][iy];
        a[ix][iy] = a[jx][jy];
        a[jx][jy] = swap;
    }

    // string representation of the board
    public String toString()
    {
        final StringBuilder s = new StringBuilder();
        s.append(size + "\n");
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++)
                s.append(String.format("%2d ", (int) grid[i * size + j]));
            s.append("\n");
        }
        return s.toString();
    }

    // a board obtained by exchanging two adjacent blocks in the same row
    public Board twin()
    {
        final int [ ][ ] copy = new int[size][size];
        for (int i = 0; i < size * size; i++)
            copy[i / size][i % size] = grid[i];

        int row = StdRandom.uniform(0, size);
        while (copy[row][0] == 0 || copy[row][1] == 0)
            row = StdRandom.uniform(0, size);

        swap(copy, row, 0, row, 1);
        return new Board(copy);
    }
}
