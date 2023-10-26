package Lab2.Task2;

public class MultiplyMatricesThread implements Runnable{
    int [][] matrixA, matrixB, resultMatrix;
    int rowStart, rowEnd;

    public MultiplyMatricesThread(int[][] matrixA, int[][] matrixB, int[][] resultMatrix, int rowStart, int rowEnd) {
        this.matrixA = matrixA;
        this.matrixB = matrixB;
        this.resultMatrix = resultMatrix;
        this.rowStart = rowStart;
        this.rowEnd = rowEnd;
    }

    private void findMatrixMultiplyElement(int rowRes) {
        for (int i = 0; i < matrixA.length; i++) {
            int res = 0;
            for (int j = 0; j < matrixB.length; j++) {
                res += matrixA[rowRes][j] * matrixB[j][i];
            }
            resultMatrix[rowRes][i] = res;
        }
    }

    @Override
    public void run() {
        for (int i = rowStart; i < rowEnd; i++) {
            findMatrixMultiplyElement(i);
        }
    }
}
