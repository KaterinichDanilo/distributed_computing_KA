package Lab1;

import java.util.Locale;
import java.util.Scanner;

public class Lab1 {

    public static class MyThread extends Thread{

        private static double pow = 1, a = 5, aLeft = Math.pow(10, -100), aRight = Math.pow(10, 100);
        private static final Object object = new Object();
        private static boolean done = false;

        private double powerFactor;

        public MyThread(double powerFactor) {
            this.powerFactor = powerFactor;
        }

        public static void setPow(int pow) {
            MyThread.pow = pow;
            MyThread.done = false;
        }

        @Override
        public void run() {
            double currentAValue;
            synchronized (object) {
                currentAValue = Math.pow(a, pow);
            }

            try {
                sleep(10);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }

            while (currentAValue > aLeft && currentAValue < aRight && !done) {
                synchronized (object) {
                    pow += powerFactor;
                    currentAValue = Math.pow(a, pow);
                    System.out.println("Thread name: " + Thread.currentThread().getName() + " priority = " + Thread.currentThread().getPriority() + "powerFactor = " + powerFactor + " : " + currentAValue);
                }
            }
            done = true;
        }
    }

    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        in.useLocale(Locale.ENGLISH);

        while (true) {
            System.out.println("Введіть powerFactor для першого потоку: ");
            double pf1 = in.nextDouble();
            System.out.println("Введіть пріоритет для першого потоку: ");
            int pr1 = in.nextInt();
            Thread thread = new Thread(new MyThread(pf1));
            thread.setPriority(pr1);

            System.out.println("Введіть powerFactor для другого потоку: ");
            double pf2 = in.nextDouble();
            System.out.println("Введіть пріоритет для другого потоку: ");
            int pr2 = in.nextInt();
            Thread thread2 = new Thread(new MyThread(pf2));
            thread2.setPriority(pr2);

            System.out.println("Початок роботи: ");
            thread.start();
            thread2.start();
            try {
                thread.join();
                thread2.join();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }

            System.out.println("Роботу завершено!");
            MyThread.setPow(1);

        }
    }
}
