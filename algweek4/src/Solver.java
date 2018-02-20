import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.MinPQ;
import edu.princeton.cs.algs4.Stack;
import edu.princeton.cs.algs4.StdOut;

public class Solver {

    private SearchNode lastNode;

    // find a solution to the initial board (using the A* algorithm)
    public Solver(Board initial) {
        if (initial == null) {
            throw new IllegalArgumentException();
        }

        MinPQ<SearchNode> nodes = new MinPQ<>();
        nodes.insert(new SearchNode(initial));

        MinPQ<SearchNode> twinNodes = new MinPQ<>();
        twinNodes.insert(new SearchNode(initial.twin()));

        solve(nodes, twinNodes);
    }

    // solve a slider puzzle (given below)
    public static void main(String[] args) {
        // create initial board from file
        In in = new In();
        int n = in.readInt();
        int[][] blocks = new int[n][n];
        for (int i = 0; i < n; i++)
            for (int j = 0; j < n; j++)
                blocks[i][j] = in.readInt();
        Board initial = new Board(blocks);

        // solve the puzzle
        Solver solver = new Solver(initial);

        // print solution to standard output
        if (!solver.isSolvable())
            StdOut.println("No solution possible");
        else {
            StdOut.println("Minimum number of moves = " + solver.moves());
            for (Board board : solver.solution())
                StdOut.println(board);
        }
    }

    private void solve(MinPQ<SearchNode> nodes, MinPQ<SearchNode> twinNodes) {
        while (true) {
            lastNode = makeMove(nodes);
            if (lastNode != null || makeMove(twinNodes) != null) {
                break;
            }
        }
    }

    private SearchNode makeMove(MinPQ<SearchNode> nodes) {
        if (nodes.isEmpty()) {
            return null;
        }
        SearchNode current = nodes.delMin();
        if (current.board.isGoal()) {
            return current;
        }
        for (Board neighbor : current.board.neighbors()) {
            if (current.predecessor == null || !neighbor.equals(current.predecessor.board)) {
                nodes.insert(new SearchNode(neighbor, current));
            }
        }
        return null;
    }

    // is the initial board solvable?
    public boolean isSolvable() {
        return lastNode != null;
    }

    // min number of moves to solve initial board; -1 if unsolvable
    public int moves() {
        return isSolvable() ? lastNode.moves : -1;
    }

    // sequence of boards in a shortest solution; null if unsolvable
    public Iterable<Board> solution() {
        if (!isSolvable()) return null;
        Stack<Board> solution = new Stack<>();
        SearchNode l = this.lastNode;
        while (l != null) {
            solution.push(l.board);
            l = l.predecessor;
        }
        return solution;
    }

    private class SearchNode implements Comparable<SearchNode> {

        private Board board;
        private SearchNode predecessor;
        private final int moves;
        private final int manhattan;


        SearchNode(Board initial) {
            this.board = initial;
            this.manhattan = initial.manhattan();
            this.moves = 0;
        }

        SearchNode(Board board, SearchNode predecessor) {
            this.board = board;
            this.predecessor = predecessor;
            this.moves = predecessor.moves + 1;
            this.manhattan = board.manhattan();
        }

        @Override
        public int compareTo(SearchNode o) {
            return (this.manhattan - o.manhattan) + (this.moves - o.moves);
        }
    }
}