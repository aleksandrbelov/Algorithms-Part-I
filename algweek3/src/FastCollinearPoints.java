import java.util.ArrayList;
import java.util.Arrays;

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;

public class FastCollinearPoints {

    private LineSegment[] segments;

    // finds all line segments containing 4 or more points
    public FastCollinearPoints(Point[] points){
        if (points == null) {
            throw new IllegalArgumentException();
        }
        Point[] newPoints = Arrays.copyOf(points, points.length);
        validation(newPoints);
        findSegment(newPoints);
    }

    public static void main(String[] args) {
        // read the n points from a file
        In in = new In(args[0]);
        int n = in.readInt();
        Point[] points = new Point[n];
        for (int i = 0; i < n; i++) {
            int x = in.readInt();
            int y = in.readInt();
            points[i] = new Point(x, y);
        }
//        Point[] points = new Point[8];
//        points[0] = new Point(10000, 0);
//        points[1] = new Point(0, 10000);
//        points[2] = new Point(3000,   7000);
//        points[3] = new Point(7000,   3000);
//        points[4] = new Point(20000,  21000);
//        points[5] = new Point(3000,   4000);
//        points[6] = new Point(14000,  15000);
//        points[7] = new Point(6000,   7000);

        FastCollinearPoints collinear = new FastCollinearPoints(points);
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

    private void findSegment(Point[] points) {
        ArrayList<LineSegment> lineSegments = new ArrayList<>();
        for (int i = 0; i < points.length - 3; i++) {
            Arrays.sort(points);
            Arrays.sort(points, i, points.length, points[i].slopeOrder());
            for (int j = i + 1; j < points.length - 2; j++) {
                if (isLine(points[i].slopeTo(points[j]), points[i].slopeTo(points[j + 1]), points[i].slopeTo(points[j + 2]))){
                    lineSegments.add(new LineSegment(points[i], points[j + 2]));
                }
            }
        }
        segments = new LineSegment[lineSegments.size()];
        segments = lineSegments.toArray(segments);
    }

    private boolean isLine(double v, double v1, double v2) {
        return v == v1 && v1 == v2;
    }

    // the number of line segments
    public int numberOfSegments(){
        return segments.length;
    }
    // the line segments
    public LineSegment[] segments(){
        return Arrays.copyOf(segments, segments.length);
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
}
