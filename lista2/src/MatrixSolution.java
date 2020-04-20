import java.util.Arrays;

public class MatrixSolution extends SolutionAbstract<Integer[][]> {
    private int numberOfRows;
    private int numberOfColumns;
    private Integer[][] targetMatrix;
    static int[] possibleValuesInSolution =
            {0, 32, 64, 128, 160, 192, 223, 255};

    MatrixSolution(Integer[][] value, int n, int m, Integer[][] matrix) {
        super(value);
        this.numberOfRows = n;
        this.numberOfColumns = m;
        this.targetMatrix = matrix;
    }

    @Override
    public double evaluate() {
        return 0;
    }

    @Override
    public Solution<Integer[][]> nextNeighbour() {
        return null;
    }

    @Override
    public Solution<Integer[][]> copy() {
        return null;
    }

    @Override
    public String toString() {
        // zwraca macierz w postaci Stringa
        StringBuilder s = new StringBuilder();
        for(int i = 0; i < numberOfRows; i++) {
            for(int j = 0; j < numberOfColumns; j++) {
                s.append(super.value[i][j]);
                s.append(' ');
            }
            s.append('\n');
        }
        return s.toString();
    }
}
