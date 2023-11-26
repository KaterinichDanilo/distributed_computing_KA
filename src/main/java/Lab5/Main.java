package Lab5;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Main {
    public static void main(String[] args) {
        final int N = 1000, threadsAmount = 2;
        final double W = 1.0/N;
        long startTime, endTime;
        List<Thread> threadList;
        double pi;

//        Task1
        System.out.println("Частина 1");

        startTime = System.currentTimeMillis();
        threadList = new ArrayList<>();
        for (int i = 0; i < threadsAmount; i++) {
            Thread thread = new Thread(new PiCalculateThread(i * N/threadsAmount, (i + 1)*N/threadsAmount, W));
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

        pi = W * PiCalculateThread.getSum();
        System.out.println(pi);
        endTime = System.currentTimeMillis();
        System.out.println("Кількість потоків: " + threadsAmount + ", Час = " + (endTime - startTime) + " мілісекунд");

//        Task2
        System.out.println("Частина 2");

        int numSamples = 1000000;
        startTime = System.currentTimeMillis();
        threadList = new ArrayList<>();
        for (int i = 0; i < threadsAmount; i++) {
            Thread thread = new Thread(new ApproximatePiThread(i * numSamples/threadsAmount, (i + 1)*numSamples/threadsAmount));
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
        pi = 4.0 * ApproximatePiThread.getCounter() / numSamples;
        System.out.println("Значення Pi отримане за допомогою методу Монте-Карло: " + pi);
        endTime = System.currentTimeMillis();
        System.out.println("Кількість потоків: " + threadsAmount + ", Час = " + (endTime - startTime) + " мілісекунд");
    }
}
