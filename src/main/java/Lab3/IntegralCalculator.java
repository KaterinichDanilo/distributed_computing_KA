package Lab3;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class IntegralCalculator {
    private double totalSum;
    private class CountIntegralThread implements Runnable{
        private double a, b, h, sum = 0;
        private static ReentrantReadWriteLock readWriteLock = new ReentrantReadWriteLock();

        public CountIntegralThread(double a, double b, double h) {
            this.a = a;
            this.b = b;
            this.h = h;
        }

        @Override
        public void run() {
            for (double i = a; i < b; i+=h) {
                sum += Function.countFunc(i + h/2)*h;
            }
            readWriteLock.writeLock().lock();
            totalSum += sum;
            readWriteLock.writeLock().unlock();
        }
    }

    public double countIntegral(double a, double b, int n, int threadsAmount) {
        totalSum = 0;
        HashMap<Long, Double> indexes = new HashMap<>();
        double h = (b - a)/n;
        indexes.put(0L, a);
        for (long i = 1; i < n + 1; i++) {
            indexes.put(i, indexes.get(i-1) + h);
        }

        List<Thread> threadList = new LinkedList<>();
        long startTime = System.currentTimeMillis();
        for (long i = 0; i < threadsAmount; i++) {
            Thread thread = new Thread(new CountIntegralThread(indexes.get(i * n/threadsAmount),
                    indexes.get((i + 1) * n/threadsAmount), h));
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
        long endTime = System.currentTimeMillis();
        System.out.println("Multithreading took " + (endTime - startTime) + " milliseconds");
        return totalSum;
    }

}
