package Lab2;

import Lab2.Task2.MultiplyMatricesThread;
import Lab2.Task3.SumAllElementsInMatrixThead;
import Lab2.Task3.SumElementsInRowsMatrix;

import java.util.LinkedList;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        List<Thread> threadList;
        long startTime, endTime;
        int threads = 5;
//        Task1
        System.out.println("Task1");
        int amountOfThreads = Runtime.getRuntime().availableProcessors();
        System.out.println(amountOfThreads);

        for (int i = 0; i < amountOfThreads; i++) {
            Thread thread = new Thread(() -> System.out.println(Thread.currentThread().getName() + ": Hello World!"));
            thread.start();
            try {
                thread.join();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }


//        Task2
        System.out.println("Task2");
        int [][] matrixA = new int[1000][1000];
        int [][] matrixB = new int[1000][1000];
        fillArrayWithRandom(matrixA);
        fillArrayWithRandom(matrixB);
        int [][] resultMatrix = new int[matrixA.length][matrixB[0].length];

        startTime = System.currentTimeMillis();
        threadList = new LinkedList<>();
        for (int i = 0; i < threads; i++) {
            Thread thread = new Thread(new MultiplyMatricesThread(matrixA, matrixB, resultMatrix,
                    i * resultMatrix.length/threads, (i+1) * resultMatrix.length/threads));
            thread.start();
            threadList.add(thread);
        }

        for (Thread t:threadList) {
            try {
                t.join();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
        endTime = System.currentTimeMillis();
        System.out.println("Multithreading took " + (endTime - startTime) + " milliseconds");
        System.out.println("Виведемо перший рядок результуючої матриці:");
        for (int i = 0; i < resultMatrix[0].length; i++) {
            System.out.print(resultMatrix[0][i] + " ");
        }
        System.out.println();

        resultMatrix = new int[matrixA.length][matrixB[0].length];
        startTime = System.currentTimeMillis();
        multiplyMatrices(matrixA, matrixB, resultMatrix);
        endTime = System.currentTimeMillis();
        System.out.println("One thread " + (endTime - startTime) + " milliseconds");
        System.out.println("Виведемо перший рядок результуючої матриці:");
        for (int i = 0; i < resultMatrix[0].length; i++) {
            System.out.print(resultMatrix[0][i] + " ");
        }
        System.out.println();

//        Task3
        System.out.println("Task3 ");
        int [][] matrixC = new int[100][100];
        int [] cRes = new int[matrixC.length];
        fillArrayWithRandom(matrixC);
        threadList = new LinkedList<>();
        startTime = System.currentTimeMillis();
        for (int i = 0; i < threads; i++) {
            Thread thread = new Thread(new SumElementsInRowsMatrix(matrixC, i*matrixC.length/threads, (i+1)*matrixC.length/threads, cRes));
            thread.start();
            threadList.add(thread);
        }
        for (Thread t:threadList) {
            try {
                t.join();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }

        endTime = System.currentTimeMillis();
        System.out.println("Multithreading took " + (endTime - startTime) + " milliseconds");
        for (int i = 0; i < cRes.length; i++) {
            System.out.print(cRes[i] + " ");
        }
        System.out.println();

        cRes = new int[matrixC.length];
        startTime = System.currentTimeMillis();
        getSumElInRows(matrixC, cRes);
        endTime = System.currentTimeMillis();
        System.out.println("One thread took " + (endTime - startTime) + " milliseconds");
        for (int i = 0; i < cRes.length; i++) {
            System.out.print(cRes[i] + " ");
        }


//        Task3 2
        System.out.println("Task3 Part 2");
        int[][] matrixD = new int[1000][1000];
        fillArrayWithRandom(matrixD);
        threadList = new LinkedList<>();
        startTime = System.currentTimeMillis();
        for (int i = 0; i < threads; i++) {
            Thread thread = new Thread(new SumAllElementsInMatrixThead(matrixD, i*matrixD.length/threads, (i+1)*matrixD.length/threads));
            thread.start();
            threadList.add(thread);
        }
        for (Thread t:threadList) {
            try {
                t.join();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
        endTime = System.currentTimeMillis();
        System.out.println("Multithreading took " + (endTime - startTime) + " milliseconds");
        System.out.println(SumAllElementsInMatrixThead.getTotalSum());

        startTime = System.currentTimeMillis();
        int sum = getSumAllElInMatrix(matrixD);
        endTime = System.currentTimeMillis();
        System.out.println("One thread took " + (endTime - startTime) + " milliseconds");
        System.out.println(sum);
    }

    private static void fillArrayWithRandom(int[][] array){
        for (int i = 0; i < array.length; i++) {
            for (int j = 0; j < array[i].length; j++) {
                array[i][j] = (int) (Math.random() * 100);
            }
        }
    }

    private static void multiplyMatrices(int[][] matrixA, int[][] matrixB, int[][] matrixRes) {
        for (int i = 0; i < matrixB.length; i++) {
            for (int j = 0; j < matrixB[0].length; j++) {
                for (int k = 0; k < matrixB.length; k++)
                    matrixRes[i][j] += matrixA[i][k] * matrixB[k][j];
            }
        }
    }

    private static void getSumElInRows(int[][] matrix, int[] sums) {
        for (int i = 0; i < matrix.length; i++) {
            int sum = 0;
            for (int j = 0; j < matrix[i].length; j++) {
                sum += matrix[i][j];
            }
            sums[i] = sum;
        }
    }

    private static int getSumAllElInMatrix(int[][] matrix) {
        int sum = 0;
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[i].length; j++) {
                sum += matrix[i][j];
            }
        }
        return sum;
    }
}
