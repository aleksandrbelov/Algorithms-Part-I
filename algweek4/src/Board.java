import java.util.Arrays;

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.Stack;

public class Board {

    private final int n;
    private final int hamming;
    private final int manhattan;
    private int[][] tiles;

    // construct a board from an n-by-n array of blocks
    // (where blocks[i][j] = block in row i, column j)
    public Board(int[][] blocks) {
        if (blocks == null) {
            throw new IllegalArgumentException();
        }
        this.n = blocks.length;
        this.tiles = new int[n][n];
        copy(this.tiles, blocks);
        this.hamming = hammingCalc();
        this.manhattan = manhattanCalc();
    }

    // unit tests (not graded)
    public static void main(String[] args) {
        // create initial board from file
        In in = new In(args[0]);
        int n = in.readInt();
        int[][] blocks = new int[n][n];
        for (int i = 0; i < n; i++)
            for (int j = 0; j < n; j++)
                blocks[i][j] = in.readInt();
        Board initial = new Board(blocks);
        System.out.println(initial.hamming());
        System.out.println(initial.manhattan());
    }

    private void copy(int[][] newArr, int[][] oldArr) {
        for (int i = 0; i < n; i++) {
            newArr[i] = Arrays.copyOf(oldArr[i], n);
        }
    }

    private Iterable<Board> findNeighbors() {
        Stack<Board> result = new Stack<>();
        for (int row = 0; row < n; row++) {
            for (int col = 0; col < n; col++) {
                if (tiles[row][col] == 0) {
                    if (col - 1 >= 0) {
                        result.push(new Board(exch(row, col, row, col - 1)));
                    }
                    if (col + 1 < n) {
                        result.push(new Board(exch(row, col, row, col + 1)));
                    }
                    if (row - 1 >= 0) {
                        result.push(new Board(exch(row, col, row - 1, col)));
                    }
                    if (row + 1 < n) {
                        result.push(new Board(exch(row, col, row + 1, col)));
                    }
                    break;
                }
            }
        }
        return result;
    }

    private int[][] exch(int row, int col, int newRow, int newCol) {
        int[][] newTiles = new int[n][n];
        copy(newTiles, this.tiles);
        int temp = newTiles[row][col];
        newTiles[row][col] = newTiles[newRow][newCol];
        newTiles[newRow][newCol] = temp;
        return newTiles;
    }

    // board dimension n
    public int dimension() {
        return n;
    }

    // number of blocks out of place
    public int hamming() {
        return this.hamming;
    }

    private int hammingCalc() {
        int result = 0;
        for (int row = 0; row < n; row++) {
            for (int col = 0; col < n; col++) {
                if (isNotSpace(row, col) && tiles[row][col] != goal(row, col)) {
                    result++;
                }
            }
        }
        return result;
    }

    private boolean isNotSpace(int row, int col) {
        return tiles[row][col] != 0;
    }

    private int goal(int row, int col) {
        return row * n + col + 1;
    }

    // sum of Manhattan distances between blocks and goal
    public int manhattan() {
        return this.manhattan;
    }

    private int manhattanCalc() {
        int result = 0;
        for (int row = 0; row < n; row++) {
            for (int col = 0; col < n; col++) {
                int block = tiles[row][col];
                if (isNotSpace(row, col) && block != goal(row, col)) {
                    int goalCol = (block - 1) % n ;
                    int goalRow = (block - 1) / n;
                    result += Math.abs(row - goalRow) + Math.abs(col - goalCol);
                }
            }
        }
        return result;
    }

    // is this board the goal board?
    public boolean isGoal() {
        return hamming == 0;
    }

    // a board that is obtained by exchanging any pair of blocks
    public Board twin() {
        for (int row = 0; row < tiles.length; row++)
            for (int col = 0; col < tiles.length - 1; col++)
                if (isNotSpace(row, col) && isNotSpace(row, col + 1))
                    return new Board(exch(row, col, row, col + 1));
        throw new RuntimeException();
    }

    // does this board equal y?
    public boolean equals(Object y) {
        if (y == null) return false;
        if (this.getClass() != y.getClass()) return false;
        if (this == y) return true;
        Board that = (Board) y;
        if (this.tiles.length != that.tiles.length) return false;
        for (int row = 0; row < that.tiles.length; row++) {
            for (int col = 0; col < that.tiles[row].length; col++) {
                if (this.tiles[row][col] != that.tiles[row][col]) {
                    return false;
                }
            }
        }
        return true;
    }

    // all neighboring boards
    public Iterable<Board> neighbors() {
        return findNeighbors();
    }

    // string representation of this board (in the output format specified below)
    public String toString() {
        StringBuilder s = new StringBuilder();
        s.append(n).append("\n");
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                s.append(String.format("%2d ", tiles[i][j]));
            }
            s.append("\n");
        }
        return s.toString();
    }
}