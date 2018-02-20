import java.util.Comparator;

import edu.princeton.cs.algs4.StdDraw;

public class Point implements Comparable<Point> {

    private static final int MAX_VALUE = 32767;
    private final int x;
    private final int y;

    // constructs the point (x, y)
    public Point(int x, int y) {
        validation(x, y);
        this.x = x;
        this.y = y;
    }

    public static void main(String[] args) {
        double compare = new Point(1, 1).slopeTo(new Point(2, 1));
        System.out.println(compare);
    }

    private void validation(int x, int y) {
        if (x < 0 || x > MAX_VALUE || y < 0 || y > MAX_VALUE) {
            throw new IllegalArgumentException();
        }
    }

    // draws this point
    public void draw() {
        StdDraw.point(x, y);
    }

    // draws the line segment from this point to that point
    public void drawTo(Point that) {
        StdDraw.line(this.x, this.y, that.x, that.y);
    }

    // string representation
    public String toString() {
        return "(" + x + ", " + y + ")";
    }

    @Override
    public int compareTo(Point that) {
        if (this.x == that.x && this.y == that.y) {
            return 0;
        }
        if (this.y < that.y || this.y == that.y && this.x < that.x)
            return -1;
        return 1;
    }

    // the slope between this point and that point
    public double slopeTo(Point that) {
        if (this.compareTo(that) == 0) {
            return Double.NEGATIVE_INFINITY;
        }
        if (that.x == this.x) {
            return Double.POSITIVE_INFINITY;
        }
        if (that.y == this.y) {
            return +0.0D;
        }
        return (that.y - this.y) / (double) (that.x - this.x);
    }

    // compare two points by slopes they make with this point
    public Comparator<Point> slopeOrder() {
        return (o1, o2) -> {
            double slopeO1 = this.slopeTo(o1);
            double slopeO2 = this.slopeTo(o2);
            return Double.compare(slopeO1, slopeO2);
        };
    }

//    private class SlopOrder implements Comparator<Point> {
//
//        private Point origin;
//
//        public SlopOrder(Point origin) {
//            this.origin = origin;
//        }
//
//        @Override
//        public int compare(Point o1, Point o2) {
//            double slopeO1 = origin.slopeTo(o1);
//            double slopeO2 = origin.slopeTo(o2);
//            return Double.compare(slopeO1, slopeO2);
//        }
//    }
}
