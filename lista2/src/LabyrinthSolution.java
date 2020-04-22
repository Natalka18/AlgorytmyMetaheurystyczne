import java.util.List;
import java.util.Stack;

public class LabyrinthSolution extends SolutionAbstract<List<Character>> {
    private Labyrinth labyrinth;
    Stack<Solution<List<Character>>> neighbours;

    LabyrinthSolution(List<Character> value, int[][] labyrinthMatrix,
                      int rows, int columns) {
        super(value);
        this.labyrinth = new Labyrinth(rows, columns, labyrinthMatrix);
    }

    private LabyrinthSolution(List<Character> value, Labyrinth labyrinth) {
        super(value);
        this.labyrinth = labyrinth;
    }

    @Override
    public double evaluate() {
        // problem polega na zminimalizowaniu tej wartości
        int numberOfSteps = labyrinth.checkSequence(super.value);
        // a ma wartość 0, jeśli ten ciąg prowadzi do wyjścia,
        // wartość 1 w przeciwnym przypadku
        int a = (numberOfSteps < super.value.size()) ? 0 : 1;
        return a * numberOfSteps + numberOfSteps;
    }

    @Override
    public Solution<List<Character>> nextNeighbour() {
        if(neighbours == null) {
            initializeNeighboursList();
        }
        if(neighbours.empty()) {
            return null;
        }
        return neighbours.pop();
    }

    @Override
    public Solution<List<Character>> copy() {
        return new LabyrinthSolution(super.value, labyrinth);
    }

    private void initializeNeighboursList() {
        neighbours = new Stack<>();
        // TODO
    }
}
