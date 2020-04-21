import java.util.Random;
import java.util.Stack;

public class MatrixSolution extends SolutionAbstract<Integer[][]> {
    private int numberOfRows;
    private int numberOfColumns;
    private Integer[][] targetMatrix;
    private int rowsInBlock;  // liczba wierszy w bloku w tym rozwiązaniu
    private int columnsInBlock;  // liczba kolumn w bloku w tym rozwiązaniu
    private Stack<Solution<Integer[][]>> neighbours;
    static int[] possibleValuesInSolution =
            {0, 32, 64, 128, 160, 192, 223, 255};

    MatrixSolution(Integer[][] value, int n, int m, Integer[][] matrix,
                   int rowsInBlock, int columnsInBlock) {
        super(value);
        this.numberOfRows = n;
        this.numberOfColumns = m;
        this.targetMatrix = matrix;
        this.rowsInBlock = rowsInBlock;
        this.columnsInBlock = columnsInBlock;
    }

    @Override
    public double evaluate() {
        Integer[][] M = super.value;
        int sum = 0;
        for(int i = 1; i < numberOfRows; i++) {
            for(int j = 0; j < numberOfColumns; j++) {
                int num = M[i][j] - targetMatrix[i][j];
                sum += (num * num);
            }
        }
        return ((double) sum) / (numberOfColumns * numberOfRows);
    }

    @Override
    public Solution<Integer[][]> nextNeighbour() {
        if(neighbours == null) {
            initializeNeighboursList();
        }
        if(neighbours.empty()) {
            return null;
        }
        return neighbours.pop();
    }

    @Override
    public Solution<Integer[][]> copy() {
        return new MatrixSolution(super.value, numberOfRows,
                numberOfColumns, targetMatrix, rowsInBlock, columnsInBlock);
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

    // sąsiad ma taki sam podział na bloki, jeden blok ma inną wartość
    private void initializeNeighboursList() {
        Random random = new Random();
        Integer[][] neighbourValue;
        neighbours = new Stack<>();
        // liczba bloków w wierszu
        int blocksInRow = numberOfColumns / columnsInBlock;
        // liczba bloków w kolumnie
        int blocksInColumn = numberOfRows / rowsInBlock;
        // wiersze i kolumny w nowym podziale numerujemy od 1
        // przechodzimy przez wszystkie bloki
        for(int rowNumber = 1; rowNumber <= blocksInColumn; rowNumber++) {
            for(int columnNumber = 1; columnNumber <= blocksInRow; columnNumber++) {
                // losowanie wartości, która zostanie umieszczona w tym bloku
                // musi być inna niż aktualna wartość
                int currentValue = super.value[rowsInBlock * (rowNumber - 1)]
                        [columnsInBlock * (columnNumber - 1)];
                int randomValue = 0;
                do {
                    int randomIndex =
                            random.nextInt(MatrixSolution.possibleValuesInSolution.length);
                    randomValue = MatrixSolution.possibleValuesInSolution[randomIndex];
                } while(randomValue == currentValue);
                // utworzenie nowej macierzy sąsiada i wypełnienie jednego bloku innymi wartościami
                neighbourValue = copyMatrix(super.value, numberOfRows, numberOfColumns);
                for(int i = rowsInBlock * (rowNumber - 1); i < rowsInBlock * rowNumber; i++) {
                    for(int j = columnsInBlock * (columnNumber - 1); j < columnsInBlock * columnNumber; j++) {
                        neighbourValue[i][j] = randomValue;
                    }
                }
                // utworzenie sąsiada i umieszczenie go na stosie
                Solution<Integer[][]> neighbour = new MatrixSolution(neighbourValue, numberOfRows,
                        numberOfColumns, targetMatrix, rowsInBlock, columnsInBlock);
                neighbours.push(neighbour);
            }
        }
    }

    private Integer[][] copyMatrix(Integer[][] M, int n, int m) {
        Integer[][] result = new Integer[n][m];
        for(int i = 0; i < n; i++) {
            if (m >= 0) System.arraycopy(M[i], 0, result[i], 0, m);
        }
        return result;
    }
}
