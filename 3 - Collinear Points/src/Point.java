/*************************************************************************
 * Name: Alberto Gutiérrez Jácome
 * Email: agjacome@gmail.com
 * 
 * Compilation: javac Point.java
 * Execution: not applicable
 * Dependencies: StdDraw.java
 * 
 * Description: An immutable data type for points in the plane.
 * 
 *************************************************************************/

import java.util.Comparator;


public class Point implements Comparable<Point>
{

    // class that implements the slope order comparator
    private class SlopeOrder implements Comparator<Point>
    {
        public int compare(final Point q1, final Point q2)
        {
            if (q1 == null) throw new java.lang.RuntimeException();
            if (q2 == null) throw new java.lang.RuntimeException();

            final double slope1 = slopeTo(q1);
            final double slope2 = slopeTo(q2);

            if (slope1 < slope2) return -1;
            if (slope1 > slope2) return +1;
            return 0;
        }
    }

    // compare points by slope
    public final Comparator<Point> SLOPE_ORDER = new SlopeOrder();
    private final int              x;
    private final int              y;

    // create the point (x, y)
    public Point(final int x, final int y)
    {
        this.x = x;
        this.y = y;
    }

    // is this point lexicographically smaller than that one?
    public int compareTo(final Point that)
    {
        if (that == null) throw new java.lang.RuntimeException();

        if (y < that.y || y == that.y && x < that.x) return -1;
        if (y > that.y || x > that.x) return +1;
        return 0;
    }

    // plot this point to standard drawing
    public void draw()
    {
        StdDraw.point(x, y);
    }

    // draw line between this point and that point to standard drawing
    public void drawTo(final Point that)
    {
        StdDraw.line(x, y, that.x, that.y);
    }

    // slope between this point and that point
    public double slopeTo(final Point that)
    {
        if (that == null) throw new java.lang.RuntimeException();

        final double deltaY = that.y - y;
        final double deltaX = that.x - x;

        if (deltaX == 0 && deltaY == 0) return Double.NEGATIVE_INFINITY;
        if (deltaX == 0) return Double.POSITIVE_INFINITY;
        if (deltaY == 0) return +0.0;

        return deltaY / deltaX;
    }

    // return string representation of this point
    public String toString()
    {
        return "(" + x + ", " + y + ")";
    }

}
