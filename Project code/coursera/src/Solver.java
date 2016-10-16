import java.util.Comparator;

public class Solver {
    
    private SearchNode goal;
    
    private class SearchNode {
        private int moves;
        private Board board;
        private SearchNode preNode;
        
        public SearchNode(Board b) {
            moves = 0;
            preNode = null;
            board = b;
        }
    }
    
    public Solver(Board initial) {
        PriorityOrder po = new PriorityOrder();
        MinPQ<SearchNode> pq = new MinPQ<SearchNode>(po);
        SearchNode sn = new SearchNode(initial);
        /* twin search node */
        PriorityOrder twinPo = new PriorityOrder();
        MinPQ<SearchNode> twinPq = new MinPQ<SearchNode>(twinPo);
        SearchNode twinSn = new SearchNode(initial.twin());
        pq.insert(sn);
        twinPq.insert(twinSn);
        
        SearchNode minNode = pq.delMin();
        SearchNode twinMinNode = twinPq.delMin();
        while (!minNode.board.isGoal() && !twinMinNode.board.isGoal()) {
            for (Board b : minNode.board.neighbors()) {
                if ((minNode.preNode == null)
                        || !b.equals(minNode.preNode.board)) {
                    SearchNode node = new SearchNode(b);
                    node.moves = minNode.moves + 1;
                    node.preNode = minNode;
                    pq.insert(node);
                }
            }
            for (Board b : twinMinNode.board.neighbors()) {
                if ((minNode.preNode == null)
                        || !b.equals(twinMinNode.preNode.board)) {
                    SearchNode node = new SearchNode(b);
                    node.moves = twinMinNode.moves + 1;
                    node.preNode = twinMinNode;
                    twinPq.insert(node);
                }
            }
            minNode = pq.delMin();
            twinMinNode = twinPq.delMin();
        }
        
        if (minNode.board.isGoal())
            goal = minNode;
        else
            goal = null;
    }
    
    private class PriorityOrder implements Comparator<SearchNode> {
        public int compare(SearchNode s1, SearchNode s2) {
            int priority1 = s1.board.manhattan() + s1.moves;
            int priority2 = s2.board.manhattan() + s2.moves;
            
            if         (priority1 > priority2) return 1;
            else if (priority1 < priority2) return -1;
            else                             return 0;
        }
    }

    public boolean isSolvable() {
        return goal != null;
    }

    public int moves() {
        if (goal == null) return -1;
        return goal.moves;
    }

    public Iterable<Board> solution() {
        if (!isSolvable())  return null;
        Stack<Board> bStack = new Stack<Board>();
        for (SearchNode s = goal; s != null; s = s.preNode)
            bStack.push(s.board);
        
        return bStack;
    }

    public static void main(String[] args) {
        In in = new In(args[0]);
        int N = in.readInt();
        int[][] blocks = new int[N][N];
        for (int i = 0; i < N; i++)
            for (int j = 0; j < N; j++)
                blocks[i][j] = in.readInt();
        Board initial = new Board(blocks);

        Solver solver = new Solver(initial);

        if (!solver.isSolvable())
            StdOut.println("No solution possible");
        else {
            StdOut.println("Minimum number of moves = " + solver.moves());
            for (Board board : solver.solution())
                StdOut.println(board);
        }
    }

}