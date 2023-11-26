package Lab4;

import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.CyclicBarrier;

public class MethodGausa {
    public static double[] calculateMethodGausa(double[][] matrix, int threadsAmount) {
        List<Thread> threadList = new LinkedList<>();
        int n = matrix.length;
        int m = matrix[0].length;
        CyclicBarrier cyclicBarrier = new CyclicBarrier(threadsAmount);

        for (int i = 0; i < threadsAmount; i++) {
            Thread thread = new Thread(new DirectChiGausaThread(i * m / threadsAmount, (i + 1) * m / threadsAmount, matrix, cyclicBarrier));
            thread.start();
            threadList.add(thread);
        }

        for (Thread t : threadList) {
            try {
                t.join();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }


        for (int i = 0; i < n; i++) {
            double tmp = matrix[i][i];
            for (int j = i; j <= n; j++) {
                matrix[i][j] /= tmp;
            }
        }


        double[] solution = new double[n];
        for (int i = n - 1; i >= 0; i--) {
            solution[i] = matrix[i][n];
            for (int j = i + 1; j < n; j++) {
                solution[i] -= matrix[i][j] * solution[j];
            }
        }
        return solution;
    }
}
