package Lab2.Task3;

public class SumElementsInRowsMatrix implements Runnable{
    int[][] matrix;
    int rowStart, rowStop;
    int[] resMatrix;

    public SumElementsInRowsMatrix(int[][] matrix, int rowStart, int rowStop, int[] resMatrix) {
        this.matrix = matrix;
        this.rowStart = rowStart;
        this.rowStop = rowStop;
        this.resMatrix = resMatrix;
    }

    @Override
    public void run() {
        for (int i = rowStart; i < rowStop; i++) {
            int sum = 0;
            for (int j = 0; j < matrix[i].length; j++) {
                sum += matrix[i][j];
            }
            resMatrix[i] = sum;
        }
    }
}
