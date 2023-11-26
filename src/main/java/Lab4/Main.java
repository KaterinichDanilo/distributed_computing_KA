package Lab4;

import java.util.Random;

public class Main {
    public static void main(String[] args) {
        int threadAmount = 2;
        double[][] m = generateRandomMatrix(100);

        long startTime = System.currentTimeMillis();
        double[] solutions = MethodGausa.calculateMethodGausa(m, threadAmount);
        long endTime = System.currentTimeMillis();
        System.out.println("Кількість потоків: " + threadAmount + ", Час: " + (endTime - startTime) + " мілісекунд");
        System.out.println("Відповідь програми: ");
        for (double s:solutions) {
            System.out.print(Math.round(s) + " ");
        }
    }

    public static double[][] generateRandomMatrix(int n) {
        Random random = new Random();
        double[] ans = new double[n];
        double[][] matrix = new double[n][n + 1];
        for (int i = 0; i < n; i++) {
            ans[i] = random.nextInt(101);
        }

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                matrix[i][j] = random.nextInt(101);
            }
            for (int j = 0; j < n; j++) {
                matrix[i][n] += ans[j] * matrix[i][j];
            }
        }

        System.out.print("Розв'язок системи: ");
        for (int i = 0; i < ans.length; i++) {
            System.out.print(ans[i] + " ");
        }
        System.out.println();

        return matrix;
    }
}
