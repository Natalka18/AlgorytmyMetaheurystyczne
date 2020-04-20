import java.util.ArrayList;
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
        Random random = new Random();
        List<Double> s = new ArrayList<>();
        for(int i = 0; i < 4; i++) {
            s.add((double) random.nextInt());
        }
        return new SalomonSolution(s);
    }

    @Override
    public Solution<List<Double>> compareSolutions(Solution<List<Double>> oldSolution,
                                                   Solution<List<Double>> newSolution) {
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
        // str. 153: r w pracy Spearsa było odwrotnością liczby prób (pomnożonej przez liczbę zmiennych).
        // W tym programie w ciągu sekundy wykonuje się 80000 prób.
        double r = 1.0 / (80000 * maxTime);
        super.currentTemperature = getMaxTemperature() * Math.exp(-iteration * r);
    }

    @Override
    public Solution<List<Double>> processTheSolution(Solution<List<Double>> currentSolution) {
        return currentSolution;
    }

    @Override
    public Solution<List<Double>> getBetterSolution(Solution<List<Double>> solution1,
                                                    Solution<List<Double>> solution2) {
        if(solution1.evaluate() < solution2.evaluate()) {
            return solution1.copy();
        }
        return solution2.copy();
    }
}
