import java.util.List;
import java.util.Random;

public class SalomonProblem extends ProblemAbstract<List<Double>> {

    SalomonProblem(double minTemperature, double maxTemperature,
                   int numberOfTestedNeighbours, List<Double> startingPoint) {
        super(minTemperature, maxTemperature, numberOfTestedNeighbours,
                new SalomonSolution(startingPoint));
    }

    @Override
    public Solution<List<Double>> randomSolution() {
        return null;
    }

    @Override
    public Solution<List<Double>> compareSolutions(Solution<List<Double>> oldSolution,
                                                   Solution<List<Double>> newSolution) {
        double e = Math.E;
        double newEval = newSolution.evaluate();
        double oldEval = oldSolution.evaluate();
        if(newEval < oldEval) {
            return newSolution;
        }
        // prawdopodobieństwo przejścia do newSolution
        double probability = Math.pow(e, (oldEval - newEval) / getCurrentTemperature());
        Random random = new Random();
        if(random.nextInt(1) < probability) {
            return newSolution;
        }
        return oldSolution;
    }

    @Override
    public void updateTemperature(int iteration) {
        double r = 10;
        super.currentTemperature = getMaxTemperature() * Math.pow(Math.E, -iteration * r);
    }

    @Override
    public Solution<List<Double>> processTheSolution(Solution<List<Double>> currentSolution) {
        return currentSolution;
    }

    @Override
    public Solution<List<Double>> getBetterSolution(Solution<List<Double>> solution1,
                                                    Solution<List<Double>> solution2) {
        if(solution1.evaluate() < solution2.evaluate()) {
            return solution1;
        }
        return solution2;
    }
}
