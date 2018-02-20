import java.util.ArrayList;
import java.util.Arrays;

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;

public class BruteCollinearPoints {

    private LineSegment[] lineSegments;

    // finds all line segments containing 4 points
    public BruteCollinearPoints(Point[] points) {
        if (points == null) {
            throw new IllegalArgumentException();
        }
        Point[] newPoints = Arrays.copyOf(points, points.length);
        validation(newPoints);
        findLineSegments(newPoints);
    }

    public static void main(String[] args) {
        // read the n points from a file
//        In in = new In(args[0]);
//        int n = in.readInt();
//        Point[] points = new Point[n];
//        for (int i = 0; i < n; i++) {
//            int x = in.readInt();
//            int y = in.readInt();
//            points[i] = new Point(x, y);
//        }
        Point[] points = new Point[8];
        points[0] = new Point(10000, 0);
        points[1] = new Point(0, 10000);
        points[2] = new Point(3000,   7000);
        points[3] = new Point(7000,   3000);
        points[4] = null;
        points[5] = new Point(3000,   4000);
        points[6] = new Point(14000,  15000);
        points[7] = new Point(6000,   7000);

        BruteCollinearPoints collinear = new BruteCollinearPoints(points);
        // draw the points
        StdDraw.enableDoubleBuffering();
        StdDraw.setXscale(0, 32768);
        StdDraw.setYscale(0, 32768);
        for (Point p : points) {
            if (p == null) throw new IllegalArgumentException();
            p.draw();
        }
        StdDraw.show();

        // print and draw the line segments
        for (LineSegment segment : collinear.segments()) {
            StdOut.println(segment);
            segment.draw();
        }
        StdDraw.show();
    }

    private void findLineSegments(Point[] points) {
        ArrayList<LineSegment> listSegments = new ArrayList<>();
        Arrays.sort(points);
        for (int i = 0; i < points.length - 3; i++) {
            for (int j = i + 1; j < points.length - 2; j++) {
                for (int k = j + 1; k < points.length - 1; k++) {
                    if (points[i].slopeTo(points[j]) != points[i].slopeTo(points[k])) continue;
                    for (int l = k + 1; l < points.length; l++) {
                        if (isLine(points[i].slopeTo(points[j]), points[i].slopeTo(points[k]), points[i].slopeTo(points[l]))) {
                            listSegments.add(new LineSegment(points[i], points[l]));
                        }
                    }
                }
            }
        }
        this.lineSegments = new LineSegment[listSegments.size()];
        this.lineSegments = listSegments.toArray(this.lineSegments);
    }

    private boolean isLine(double v, double v1, double v2) {
        return v == v1 && v1 == v2;
    }

    private void validation(Point[] points) {
        for (Point p: points) {
            if (p == null) throw new IllegalArgumentException();
        }
        Arrays.sort(points);
        for (int i = 0; i < points.length - 1; i++) {
            if (points[i].compareTo(points[i + 1]) == 0) {
                throw new IllegalArgumentException();
            }
        }
    }

    // the number of line segments
    public int numberOfSegments() {
        return lineSegments.length;
    }

    public LineSegment[] segments() {
        return Arrays.copyOf(lineSegments, lineSegments.length);
    }
}
