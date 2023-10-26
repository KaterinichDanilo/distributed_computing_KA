package Lab3;

import java.util.Locale;
import java.util.Scanner;

public class Main {
    private static final double A = -1, B = 0;
    public static void main(String[] args) {
        final Scanner in = new Scanner(System.in);
        in.useLocale(Locale.ENGLISH);
        IntegralCalculator integralCalculator = new IntegralCalculator();
        while (true){
            System.out.println("Введіть кількість розбиттів: ");
            int n = in.nextInt();
            System.out.println("Введіть кількість потоків: ");
            int threads = in.nextInt();
            System.out.println(integralCalculator.countIntegral(A, B, n, threads));
            countIntegral(A, B, n);
        }
    }

    private static void countIntegral(double a, double b, int n) {
        double h = (b - a) / n;
        double integral = 0;

        for (int i = 0; i < n; i++) {
            double xi = a + i * h;
            double fxi = Function.countFunc(xi + h/2);
            integral += fxi;
        }
        integral *= h;

        System.out.println("Значення інтеграла в однопоточній реалізації: " + integral);
    }
}
