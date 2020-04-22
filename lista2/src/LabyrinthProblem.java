import java.util.List;
import java.util.Random;

public class LabyrinthProblem extends ProblemAbstract<List<Character>> {
    private Labyrinth labyrinth;

    LabyrinthProblem(double minTemperature, double maxTemperature,
                     int numberOfTestedNeighbours,
                     Solution<List<Character>> startingSolution,
                     int[][] labyrinthMatrix, int rows, int columns) {
        super(minTemperature, maxTemperature, numberOfTestedNeighbours, startingSolution);
        this.labyrinth = new Labyrinth(rows, columns, labyrinthMatrix);
    }

    @Override
    public Solution<List<Character>> randomSolution() {
        // TODO
        return null;
    }

    @Override
    public Solution<List<Character>> compareSolutions(Solution<List<Character>> oldSolution,
                                                      Solution<List<Character>> newSolution) {
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
        // TODO
    }

    @Override
    public Solution<List<Character>> processTheSolution(Solution<List<Character>> currentSolution) {
        return currentSolution;
    }

    @Override
    public Solution<List<Character>> getBetterSolution(Solution<List<Character>> solution1,
                                                       Solution<List<Character>> solution2) {
        if(solution1.evaluate() < solution2.evaluate()) {
            return solution1;
        }
        return solution2;
    }
}
