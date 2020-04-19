import java.util.List;

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
        return null;
    }

    @Override
    public void updateTemperature(int iteration) {

    }

    @Override
    public Solution<List<Double>> processTheSolution(Solution<List<Double>> currentSolution) {
        return currentSolution;
    }

    @Override
    public Solution<List<Double>> getBetterSolution(Solution<List<Double>> solution1,
                                                    Solution<List<Double>> solution2) {
        return null;
    }
}
