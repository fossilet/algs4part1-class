/*************************************************************************
 * Name: Alberto Gutiérrez Jácome
 * Email: agjacome@gmail.com
 * 
 * Compilation: javac Brute.java
 * Execution: java Brute input.txt
 * Dependencies: StdDraw.java StdOut.java In.java
 * 
 * Description: A brute-force algorithm to find lines inside a given set of
 * points. Lines interpreted as at least 4 collinear points.
 * 
 *************************************************************************/

public class Brute
{

    // check if a given set of four points form a line
    private static boolean isLine(Point p, Point q, Point r, Point s)
    {
        double slope = p.slopeTo(q);
        return p.slopeTo(r) == slope && p.slopeTo(s) == slope;
    }

    // main method, first and only argument is file to read
    public static void main(String [ ] args)
    {
        // re-scale coordinates and turn on animation mode
        StdDraw.setXscale(0, 32768);
        StdDraw.setYscale(0, 32768);
        StdDraw.show(0);

        // read in the input in the points array
        In in = new In(args[0]);
        int N = in.readInt();
        Point [ ] points = new Point[N];
        for (int i = 0; i < N; i++) {
            int x = in.readInt();
            int y = in.readInt();
            points[i] = new Point(x, y);
            points[i].draw();
        }

        // brute-force [O(N⁴)] find the lines, and display them
        Quick.sort(points);
        for (int i = 0; i < N - 3; i++)
            for (int j = i + 1; j < N - 2; j++)
                for (int k = j + 1; k < N - 1; k++)
                    for (int l = k + 1; l < N; l++)
                        if (isLine(points[i], points[j], points[k], points[l])) {
                            StdOut.print(points[i] + " -> ");
                            StdOut.print(points[j] + " -> ");
                            StdOut.print(points[k] + " -> ");
                            StdOut.print(points[l] + "\n");

                            points[i].drawTo(points[l]);
                        }

        // display to screen all at once
        StdDraw.show(0);
    }

}
