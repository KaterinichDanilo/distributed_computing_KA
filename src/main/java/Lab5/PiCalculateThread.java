package Lab5;

import java.util.concurrent.locks.ReentrantReadWriteLock;

public class PiCalculateThread implements Runnable{
    private static double sum = 0;
    private static ReentrantReadWriteLock readWriteLock = new ReentrantReadWriteLock();
    private final int iStart, iEnd;
    private double w;

    public PiCalculateThread(int iStart, int iEnd, double w) {
        this.iStart = iStart;
        this.iEnd = iEnd;
        this.w = w;
    }

    public static double getSum() {
        return sum;
    }

    private static double f(double y) {return(4.0/(1.0 + Math.pow(y, 2)));}

    @Override
    public void run() {
        for (int i = iStart; i < iEnd; i++) {
            double x = w * (i-0.5);
            readWriteLock.writeLock().lock();
            sum += f(x);
            readWriteLock.writeLock().unlock();
        }
    }
}
