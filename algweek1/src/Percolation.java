import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {

    private static final int VIRT_TOP = 0;
    private final WeightedQuickUnionUF weightedQuickUnionUF;
    private final int virtBottom;
    private final int dimension;
    private boolean[] open;
    private int numOfOpen = 0;
    private boolean isPercolated;

    // create n-by-n grid, with all sites blocked
    public Percolation(int n) {
        if (n <= 0) {
            throw new IllegalArgumentException("dimension is le 0");
        }
        this.dimension = n;
        int size = n * n;
        virtBottom = size + 1;
        weightedQuickUnionUF = new WeightedQuickUnionUF(size + 2);
        open = new boolean[size];
    }

    public static void main(String[] args) {
        Percolation percolation = new Percolation(5);
        for (int i = 1; i <= 4; i++) {
            percolation.open(i, i);
            percolation.open(i + 1, i);
        }

        System.out.println(percolation.weightedQuickUnionUF.connected(percolation.xyTo1D(1, 1), percolation.xyTo1D(1, 2)));
        System.out.println(percolation.weightedQuickUnionUF.connected(percolation.xyTo1D(1, 1), percolation.xyTo1D(2, 1)));
        System.out.println(percolation.numberOfOpenSites());
        System.out.println(percolation.isFull(4, 1));
        System.out.println(percolation.percolates());
    }

    // open site (row, col) if it is not open already
    public void open(int row, int col) {
        validate(row, col);
        if (isOpen(row, col)) return;

        int i = xyTo1D(row, col);

        open[i - 1] = true;
        numOfOpen++;

        if (isInOfBound(row, col - 1) && isOpen(row, col - 1)) {
            weightedQuickUnionUF.union(i, xyTo1D(row, col - 1));
        }
        if (isInOfBound(row, col + 1) && isOpen(row, col + 1)) {
            weightedQuickUnionUF.union(i, xyTo1D(row, col + 1));
        }
        if (!isInOfBound(row - 1, col)) {
            weightedQuickUnionUF.union(i, VIRT_TOP);
        } else if (isInOfBound(row - 1, col) && isOpen(row - 1, col)) {
            weightedQuickUnionUF.union(i, xyTo1D(row - 1, col));
        }
        if (!isInOfBound(row + 1, col)) {
            weightedQuickUnionUF.union(i, virtBottom);
        } else if (isInOfBound(row + 1, col) && isOpen(row + 1, col)) {
            weightedQuickUnionUF.union(i, xyTo1D(row + 1, col));
        }
    }

    private boolean isInOfBound(int row, int col) {
        return row > 0 && row <= dimension && col > 0 && col <= dimension;
    }

    // is site (row, col) open?
    public boolean isOpen(int row, int col) {
        validate(row, col);
        return open[xyTo1D(row, col) - 1];
    }

    // is site (row, col) full?
    public boolean isFull(int row, int col) {
        validate(row, col);
        return isOpen(row, col) && weightedQuickUnionUF.connected(VIRT_TOP, xyTo1D(row, col));
    }

    // number of open sites
    public int numberOfOpenSites() {
        return numOfOpen;
    }

    public boolean percolates() {
        if (!isPercolated) {
            isPercolated = weightedQuickUnionUF.connected(VIRT_TOP, virtBottom);
        }
        return isPercolated;
    }

    private void validate(int x, int y) {
        if (!isInOfBound(x, y)) {
            throw new IllegalArgumentException("x,y beyond 1 and dimension");
        }
    }

    private int xyTo1D(int x, int y) {
        return (x - 1) * dimension + y;
    }
}
