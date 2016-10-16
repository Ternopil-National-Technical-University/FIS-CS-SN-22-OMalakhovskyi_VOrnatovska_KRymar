import java.util.Arrays;

public class Board {
    
    private int N;
    private int[] blocks;

    public Board(int[][] blocks) {
        N = blocks[0].length;
        this.blocks = new int[N*N];
        for (int i = 0; i < N; i++)
            for (int j = 0; j < N; j++)
                this.blocks[i*N+j] = blocks[i][j];
    }
    
    private Board(int[] blocks) {
        N = (int) Math.sqrt(blocks.length);
        this.blocks = new int[blocks.length];
        for (int i = 0; i < blocks.length; i++)
            this.blocks[i] = blocks[i];
    }

    public int dimension() {
        return N;
    }

    public int hamming() {
        int cnt = 0;
        for (int i = 0; i < blocks.length; i++)
            if (blocks[i] != 0 && blocks[i] != i+1)
                cnt++;
        return cnt;
    }

    public int manhattan() {
        int sum;
        int row, col;

        sum = 0;
        for (int i = 0; i < blocks.length; i++) {
            if (blocks[i] != 0 && blocks[i] != i+1) {
                col = Math.abs(i % N - (blocks[i]-1) % N);
                row = Math.abs(i / N - (blocks[i]-1) / N);
                sum += (row + col);
            }
        }
        return sum;
    }

    public boolean isGoal() {
        for (int i = 0; i < blocks.length; i++)
            if (blocks[i] != 0 && blocks[i] != i+1)
                return false;
        return true;
    }

    public Board twin() {
        Board tBoard;
        if (N <= 1)    return null;

        tBoard = new Board(blocks);
        if (blocks[0] != 0 && blocks[1] != 0)
            exch(tBoard.blocks, 0, 1);
        else
            exch(tBoard.blocks, N, N+1);
        return tBoard;
    }

    public boolean equals(Object y) {
        if (y == null) return false;
        if (y == this) return true;
        if (y.getClass() != this.getClass())
            return false;

        Board yBoard = (Board) y;
        return Arrays.equals(blocks, yBoard.blocks);
    }
    
    private void exch(int[] a, int i, int j) {
        int temp = a[j];
        a[j] = a[i];
        a[i] = temp;
    }

    public Iterable<Board> neighbors() {
        int i;
        Board neiBoard;
        Queue<Board> bq = new Queue<Board>();

        for (i = 0; i < blocks.length; i++)
            if (blocks[i] == 0) break;

        if (i >= blocks.length) return null;
        
        if (i >= N)    {
            neiBoard = new Board(blocks);
            exch(neiBoard.blocks, i, i-N);
            bq.enqueue(neiBoard);
        }
        if (i < blocks.length - N) {
            neiBoard = new Board(blocks);
            exch(neiBoard.blocks, i, i+N);
            bq.enqueue(neiBoard);
        }
        if (i % N != 0) {
            neiBoard = new Board(blocks);
            exch(neiBoard.blocks, i, i-1);
            bq.enqueue(neiBoard);
        }
        if ((i+1) % N != 0) {
            neiBoard = new Board(blocks);
            exch(neiBoard.blocks, i, i+1);
            bq.enqueue(neiBoard);
        }
        return bq;
    }
    
    public String toString() {
        StringBuilder s = new StringBuilder();
        int digit = 0;
        String format;
        
        s.append(N);
        s.append("\n");

        for (int n = blocks.length; n != 0; n /= 10)
            digit++;
        
        format = "%" + digit + "d ";
        for (int i = 0; i < blocks.length; i++) {
            s.append(String.format(format, blocks[i]));
            if ((i+1) % N == 0)
                s.append("\n");
        }
        return s.toString();
    }
    
    public static void main(String[] args) {
        In in = new In(args[0]);
        int N = in.readInt();
        int[][] blocks = new int[N][N];
        for (int i = 0; i < N; i++)
            for (int j = 0; j < N; j++)
                blocks[i][j] = in.readInt();
        Board initial = new Board(blocks);

        StdOut.print(initial.toString());
        StdOut.print(initial.twin().toString());
        StdOut.println(initial.hamming());
        StdOut.println(initial.manhattan());
        StdOut.println(initial.dimension());
        StdOut.println(initial.isGoal());
        
        for (Board b : initial.neighbors()) {
            StdOut.println(b.toString());
            for (Board d : b.neighbors()) {
                StdOut.println("===========");
                StdOut.println(d.toString());
                StdOut.println("===========");
            }
        }
    }
}