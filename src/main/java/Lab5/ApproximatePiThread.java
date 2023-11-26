package Lab5;

import java.util.Random;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class ApproximatePiThread implements Runnable{
    private static ReentrantReadWriteLock readWriteLock = new ReentrantReadWriteLock();
    private static int counter = 0;
    private int sTart, sFinish;

    public ApproximatePiThread(int sTart, int sFinish) {
        this.sTart = sTart;
        this.sFinish = sFinish;
    }

    public static int getCounter() {
        return counter;
    }

    @Override
    public void run() {
        Random random = new Random();

        for (int s = sTart; s < sFinish; s++) {
            double x = random.nextDouble();
            double y = random.nextDouble();

            if (x * x + y * y < 1) {
                readWriteLock.writeLock().lock();
                counter++;
                readWriteLock.writeLock().unlock();
            }
        }
    }
}
