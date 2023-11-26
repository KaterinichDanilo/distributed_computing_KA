package Lab6;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class EightQueens {
    public static void main(String[] args) {
        ExecutorService executor = Executors.newFixedThreadPool(4);
        Worker.setExecutor(executor);

        for (int i = 0; i < 8; i++) {
            executor.execute(new Worker(0, i, new int[8]));
        }
    }


}
