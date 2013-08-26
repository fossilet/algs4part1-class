/****************************************************************************
 * Author: Alberto Gutiérrez Jácome <agjacome@gmail.com>
 * Date: 16/09/2012
 * 
 * Compilation: javac PointSET.java
 * Execution: not applicable
 * Dependencies: Point2D.java RectHV.java Queue.java SET.java StdDraw.java
 * 
 * Description: A mutable data type that represents a set of points in the
 * unit square. It uses a Red-Black tree to implement the set. It supports
 * insert() and contains() in time proportional to the logarithm of the number
 * of points in the set in the worst case (O(logN)) and supports nearest() and
 * range() in time proportional to the number of points in the set (O(N)).
 * 
 ***************************************************************************/

public class PointSET
{

    private final SET<Point2D> set; // the actual set of points

    // construct an empty set of points
    public PointSET()
    {
        set = new SET<Point2D>();
    }

    // does the set contain the point p?
    public boolean contains(final Point2D p)
    {
        return set.contains(p);
    }

    // draw all of the points to standard draw
    public void draw()
    {
        StdDraw.setPenColor(StdDraw.BLACK);
        StdDraw.setPenRadius(.01);

        for (final Point2D p : set)
            p.draw();
    }

    // add the point p to the set (if it is not already in the set)
    public void insert(final Point2D p)
    {
        if (!set.contains(p)) set.add(p);
    }

    // is the set empty?
    public boolean isEmpty()
    {
        return set.isEmpty();
    }

    // a nearest neighbor in the set to p; null if set is empty
    public Point2D nearest(final Point2D p)
    {
        if (isEmpty()) return null;

        Point2D nearest = set.min();
        for (final Point2D q : set)
            if (p.distanceSquaredTo(q) < p.distanceSquaredTo(nearest))
                nearest = q;

        return nearest;
    }

    // all points in the set that are inside the rectangle
    public Iterable<Point2D> range(final RectHV rect)
    {
        final Queue<Point2D> queue = new Queue<Point2D>();
        if (rect == null || isEmpty()) return queue;

        for (final Point2D p : set)
            if (rect.contains(p)) queue.enqueue(p);

        return queue;
    }

    // number of points in the set
    public int size()
    {
        return set.size();
    }
}
