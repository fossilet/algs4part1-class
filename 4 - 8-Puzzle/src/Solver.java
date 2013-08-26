/****************************************************************************
 * Author: Alberto Gutiérrez Jácome <agjacome@gmail.com>
 * Date: 07/09/2012
 * 
 * Compilation: javac Solver.java
 * Execution: java Solver file.txt
 * Dependencies: Board.java MaxPQ.java Stack.java StdOut.java
 * 
 * Description: This class implements a solver for a 8-puzzle using an A*
 * algorithm. We define a search node of the game to be a board, the number of
 * moves made to reach the board, and the previous search node. First, insert
 * the initial search node (the initial board, 0 moves, and a null previous
 * search node) into a priority queue. Then, delete from the priority queue
 * the search node with the minimum priority, and insert onto the priority
 * queue all neighboring search nodes (those that can be reached in one move
 * from the dequeued search node). Repeat this procedure until the search node
 * dequeued corresponds to a goal board. The success of this approach hinges
 * on the choice of priority function for a search node. We consider for this
 * implementation the Manhattan priority function: the sum of the Manhattan
 * distances from the blocks to their goal positions, plus the number of moves
 * made so far to the search node.
 * 
 ***************************************************************************/

public class Solver
{

    // helper class to implement each node of boards in a priority queue
    private class BoardNode implements Comparable<BoardNode>
    {
        private final Board     current;  // the current board
        private final int       mhScore;  // Manhattan score of board
        private final int       numMovs;  // number of moves need to get here
        private final BoardNode prevBNS;  // pointer to previous node

        // construct a BoardNode given its data
        public BoardNode(final Board board, final BoardNode previous,
                final int moves)
        {
            current = board;
            prevBNS = previous;
            numMovs = moves;
            mhScore = current.manhattan();
        }

        // compare with another node, using Manhattan priority function
        public int compareTo(final BoardNode that)
        {
            return mhScore + numMovs - (that.mhScore + that.numMovs);
        }
    }

    private final MinPQ<BoardNode> alternat = new MinPQ<BoardNode>();
    private final MinPQ<BoardNode> solution = new MinPQ<BoardNode>();
    private boolean                solvable = true;

    // find a solution to the initial board (using the A* algorithm)
    public Solver(final Board initial)
    {
        final Board twin = initial.twin();
        solution.insert(new BoardNode(initial, null, 0));
        alternat.insert(new BoardNode(twin, null, 0));

        solve();
    }

    // is the initial board solvable?
    public boolean isSolvable()
    {
        return solvable;
    }

    // checks if a solution is achieved (either in solution or alternative)
    private boolean isSolved()
    {
        if (solution.min().current.isGoal()) return true;
        if (alternat.min().current.isGoal()) return true;
        return false;
    }

    // min number of moves to solve initial board; -1 if no solution
    public int moves()
    {
        if (!solvable) return -1;
        return solution.min().numMovs;
    }

    // sequence of boards in a shortest solution; null if no solution
    public Iterable<Board> solution()
    {
        if (!solvable) return null;

        final Stack<Board> stack = new Stack<Board>();
        for (BoardNode goal = solution.min(); goal != null; goal = goal.prevBNS)
            stack.push(goal.current);

        return stack;
    }

    // helper method: finds the solution (the actual A* algorithm)
    private void solve()
    {
        BoardNode sol = null;
        BoardNode alt = null;

        while (!isSolved()) {
            sol = solution.delMin();
            alt = alternat.delMin();

            for (final Board b : sol.current.neighbors())
                if (sol.prevBNS == null || !b.equals(sol.prevBNS.current))
                    solution.insert(new BoardNode(b, sol, sol.numMovs + 1));

            for (final Board b : alt.current.neighbors())
                if (alt.prevBNS == null || !b.equals(alt.prevBNS.current))
                    alternat.insert(new BoardNode(b, alt, alt.numMovs + 1));
        }

        if (alternat.min().current.isGoal()) solvable = false;
    }

    // solve a slider puzzle
    public static void main(final String [ ] args)
    {
        // create initial board from file
        final In in = new In(args[0]);
        final int N = in.readInt();
        final int [ ][ ] blocks = new int[N][N];
        for (int i = 0; i < N; i++)
            for (int j = 0; j < N; j++)
                blocks[i][j] = in.readInt();
        final Board initial = new Board(blocks);

        // solve the puzzle
        final Solver solver = new Solver(initial);

        // print solution to standard output
        if (!solver.isSolvable())
            StdOut.println("No solution possible");
        else {
            StdOut.println("Minimum number of moves = " + solver.moves());
            for (final Board board : solver.solution())
                StdOut.println(board);
        }
    }
}
