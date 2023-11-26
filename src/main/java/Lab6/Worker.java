package Lab6;

import java.util.concurrent.ExecutorService;

public class Worker implements Runnable {
    private static final int N = 8;
    private static int solutions = 0;
    private static final Object lock = new Object();
    private static ExecutorService executor;
    private int row, col;
    private int[] board;

    public static void setExecutor(ExecutorService executor) {
        Worker.executor = executor;
    }

    Worker(int row, int col, int[] board) {
        this.row = row;
        this.col = col;
        this.board = board.clone();
        this.board[row] = col;
    }

    @Override
    public void run() {
        if (row + 1 == N) {
            synchronized (lock) {
                printBoard(board);
                solutions++;
            }
            return;
        }

        for (int i = 0; i < N; i++) {
            if (isSafe(row + 1, i, board)) {
                int[] newBoard = board.clone();
                newBoard[row + 1] = i;
                executor.execute(new Worker(row + 1, i, newBoard));
            }
        }
    }
    private boolean isSafe(int row, int col, int[] board) {
        for (int i = 0; i < row; i++) {
            if (board[i] == col || Math.abs(row - i) == Math.abs(col - board[i])) {
                return false;
            }
        }
        return true;
    }

    private void printBoard(int[] board) {
        System.out.println("Розв'язок " + (solutions + 1) + ":");
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                System.out.print(board[i] == j ? "1 " : "0 ");
            }
            System.out.println();
        }
        System.out.println();
    }
}
