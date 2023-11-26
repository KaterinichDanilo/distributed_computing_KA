package Lab4;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class DirectChiGausaThread implements Runnable{

    private static final ReentrantReadWriteLock lock = new ReentrantReadWriteLock();
    private int colStart, colEnd;
    private double[][] matrix;
    private static CyclicBarrier cyclicBarrier;

    public DirectChiGausaThread(int colStart, int colEnd, double[][] matrix, CyclicBarrier cyclicBarrier) {
        this.colStart = colStart;
        this.colEnd = colEnd;
        this.matrix = matrix;
        this.cyclicBarrier = cyclicBarrier;
    }

    @Override
    public void run() {
        int n = matrix.length;
        int m = matrix[0].length;
        double[][] matrixT = new double[n][m];

        lock.writeLock().lock();
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                matrixT[i][j] = matrix[i][j];
            }
        }
        lock.writeLock().unlock();

        for (int i = 0; i < n; i++) {
            for (int j = i + 1; j < n; j++) {
                double tmp = matrixT[j][i] / matrixT[i][i];

                for (int k = colStart; k < colEnd; k++) {
                    double w = matrixT[j][k];
                    matrixT[j][k] -= tmp * matrixT[i][k];
                }
            }

            try {
                cyclicBarrier.await();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            } catch (BrokenBarrierException e) {
                throw new RuntimeException(e);
            }


            lock.writeLock().lock();
            for (int j = 1; j < n; j++) {
                for (int d = colStart; d < colEnd; d++) {
                    matrix[j][d] = matrixT[j][d];
                }
            }
            lock.writeLock().unlock();

            try {
                cyclicBarrier.await();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            } catch (BrokenBarrierException e) {
                throw new RuntimeException(e);
            }

            lock.writeLock().lock();
            for (int j = 1; j < n; j++) {
                for (int d = 0; d < m; d++) {
                    if (d >= colStart && d < colEnd) continue;
                    matrixT[j][d] = matrix[j][d];
                }
            }
            lock.writeLock().unlock();

//            try {
//                cyclicBarrier.await();
//            } catch (InterruptedException e) {
//                throw new RuntimeException(e);
//            } catch (BrokenBarrierException e) {
//                throw new RuntimeException(e);
//            }
        }
    }

}
