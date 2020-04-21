import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class MatrixProblem extends ProblemAbstract<Integer[][]> {
    private Integer[][] targetMatrix;
    private int numberOfRows;
    private int numberOfColumns;
    private int blockSize;

    // rowsInBlock - liczba wierszy w bloku w macierzy startowej
    MatrixProblem(double minTemperature, double maxTemperature,
                  int numberOfTestedNeighbours, Integer[][] startingMatrix,
                  int numberOfRows, int numberOfColumns, int blockSize,
                  Integer[][] targetMatrix, int rowsInBlock, int columnsInBlock) {
        super(minTemperature, maxTemperature,
                numberOfTestedNeighbours,
                new MatrixSolution(startingMatrix, numberOfRows,
                        numberOfColumns, targetMatrix, rowsInBlock, columnsInBlock));
        this.blockSize = blockSize;
        this.numberOfColumns = numberOfColumns;
        this.numberOfRows = numberOfRows;
        this.targetMatrix = targetMatrix;
    }

    @Override
    public Solution<Integer[][]> randomSolution() {
        // losowanie rozmiaru bloków a (ilość wierszy bloku) i b (kolumny w bloku).
        // a jest dzielnikiem liczby wierszy, a b dzielnikiem liczby kolumn
        Random random = new Random();
        List<Integer> a_list = divisors(numberOfRows);
        List<Integer> b_list = divisors(numberOfColumns);
        int a_index = random.nextInt(a_list.size());
        int b_index = random.nextInt(b_list.size());
        int a = a_list.get(a_index);
        int b = b_list.get(b_index);

        // wypełnianie bloków wartościami - w każdym bloku losowa wartość
        int numberOfBlocksInRow = numberOfColumns / b;
        int numberOfBlocksInColumn = numberOfRows / a;
        Integer[][] randomSolution = new Integer[numberOfRows][numberOfColumns];
        // columnNumber - numer bloku w danym wierszu
        for(int rowNumber = 1; rowNumber <= numberOfBlocksInColumn; rowNumber++) {
            for(int columnNumber = 1; columnNumber <= numberOfBlocksInRow; columnNumber++) {
                // losowanie wartości, która zostanie umieszczona w tym bloku
                int randomIndex =
                        random.nextInt(MatrixSolution.possibleValuesInSolution.length);
                int randomValue = MatrixSolution.possibleValuesInSolution[randomIndex];
                for(int i = a * (rowNumber - 1); i < a * rowNumber; i++) {
                    for(int j = b * (columnNumber - 1); j < b * columnNumber; j++) {
                        randomSolution[i][j] = randomValue;
                    }
                }
            }
        }
        return new MatrixSolution(randomSolution, numberOfRows,
                numberOfColumns, targetMatrix, a, b);
    }

    @Override
    public Solution<Integer[][]> compareSolutions(Solution<Integer[][]> oldSolution,
                                                  Solution<Integer[][]> newSolution) {
        double e = Math.E;
        double newEval = newSolution.evaluate();
        double oldEval = oldSolution.evaluate();
        if(newEval < oldEval) {
            return newSolution.copy();
        }
        // prawdopodobieństwo przejścia do newSolution
        double probability = Math.pow(e, (oldEval - newEval) / getCurrentTemperature());
        Random random = new Random();
        if(random.nextInt(1) < probability) {
            return newSolution.copy();
        }
        return oldSolution.copy();
    }

    @Override
    public void updateTemperature(int iteration, long maxTime) {
        double r = 0.95;
        super.currentTemperature *= r;
    }

    @Override
    public Solution<Integer[][]> processTheSolution(Solution<Integer[][]> currentSolution) {
        return currentSolution;
    }

    @Override
    public Solution<Integer[][]> getBetterSolution(Solution<Integer[][]> solution1,
                                                   Solution<Integer[][]> solution2) {
        if(solution1.evaluate() < solution2.evaluate()) {
            return solution1;
        }
        return solution2;
    }

    // zwraca dzielniki liczby number większe lub równe blockSize
    private List<Integer> divisors(int number) {
        List<Integer> result = new ArrayList<>();
        for(int i = blockSize; i <= number / 2; i++) {
            if(number % i == 0) {
                result.add(i);
            }
        }
        return result;
    }
}
