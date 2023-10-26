package Lab2.Task3;

import java.util.concurrent.locks.ReentrantReadWriteLock;

public class SumAllElementsInMatrixThead implements Runnable{
    private static ReentrantReadWriteLock readWriteLock = new ReentrantReadWriteLock();
    int[][] matrix;
    int rowStart, rowStop;
    private static int totalSum;

    public SumAllElementsInMatrixThead(int[][] matrix, int rowStart, int rowStop) {
        this.matrix = matrix;
        this.rowStart = rowStart;
        this.rowStop = rowStop;
    }

    public static int getTotalSum() {
        return totalSum;
    }

    @Override
    public void run() {
        for (int i = rowStart; i < rowStop; i++) {
            int sumInRow = 0;
            for (int j = 0; j < matrix[i].length; j++) {
                sumInRow += matrix[i][j];
            }
            readWriteLock.writeLock().lock();
            try {
                totalSum += sumInRow;
            } finally {
                readWriteLock.writeLock().unlock();
            }
        }
    }
}
