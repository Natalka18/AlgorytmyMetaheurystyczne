public abstract class ProblemAbstract<T> implements Problem<T> {
    private Solution<T> currentBestSolution;
    private double currentBestEval;  // wartość funkcji oceny obecnie najlepszego rozwiązania
    private double minTemperature;
    private double maxTemperature;
    private double currentTemperature;
    private int numberOfTestedNeighbours;

    ProblemAbstract(double minTemperature, double maxTemperature,
                    int numberOfTestedNeighbours) {
        this.minTemperature = minTemperature;
        this.maxTemperature = maxTemperature;
        this.numberOfTestedNeighbours = numberOfTestedNeighbours;
    }

    public void solve(long startTime, long maxTime) {
        currentTemperature = maxTemperature;
        int iteration = 0;
        Solution<T> currentSolution = randomSolution();
        currentBestSolution = currentSolution.clone();
        currentBestEval = currentBestSolution.evaluate();
        while(System.currentTimeMillis() - startTime < maxTime) {
            for(int i = 0; i < numberOfTestedNeighbours; i++) {
                Solution<T> newSolution = currentSolution.nextNeighbour();
                currentSolution = compareSolutions(currentSolution, newSolution);
            }
            currentBestSolution = getBetterSolution(currentSolution, currentBestSolution);
            currentBestEval = currentBestSolution.evaluate();
            iteration++;
            updateTemperature(iteration);
        }
    }

    @Override
    public double getCurrentTemperature() {
        return currentTemperature;
    }

    @Override
    public double getMaxTemperature() {
        return maxTemperature;
    }

    @Override
    public double getMinTemperature() {
        return minTemperature;
    }

    @Override
    public Solution<T> getCurrentBestSolution() {
        return currentBestSolution;
    }

    @Override
    public int getNumberOfTestedNeighbours() {
        return numberOfTestedNeighbours;
    }

    @Override
    public double getCurrentBestEval() {
        return currentBestEval;
    }
}
