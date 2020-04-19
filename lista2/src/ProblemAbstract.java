public abstract class ProblemAbstract<T> implements Problem<T> {
    private Solution<T> currentBestSolution;
    private double currentBestEval;  // wartość funkcji oceny obecnie najlepszego rozwiązania
    private double minTemperature;
    private double maxTemperature;
    double currentTemperature;
    private int numberOfTestedNeighbours;
    private Solution<T> startingSolution;

    ProblemAbstract(double minTemperature, double maxTemperature,
                    int numberOfTestedNeighbours, Solution<T> startingSolution) {
        this.minTemperature = minTemperature;
        this.maxTemperature = maxTemperature;
        this.numberOfTestedNeighbours = numberOfTestedNeighbours;
        this.startingSolution = startingSolution;
    }

    public void solve(long startTime, long maxTime) {
        currentTemperature = maxTemperature;
        int iteration = 0;
        Solution<T> currentSolution;
        if(startingSolution.isNull()) {
            currentSolution = randomSolution();
        } else {
            currentSolution = startingSolution;
        }
        currentBestSolution = currentSolution.copy();
        currentBestEval = currentBestSolution.evaluate();
        while(System.currentTimeMillis() - startTime < maxTime) {
            for(int i = 0; i < numberOfTestedNeighbours; i++) {
                Solution<T> newSolution = currentSolution.nextNeighbour();
                if(newSolution == null) {  // skończyli się sąsiedzi
                    break;
                }
                currentSolution = compareSolutions(currentSolution, newSolution);
            }
            currentBestSolution = getBetterSolution(currentSolution, currentBestSolution);
            currentBestEval = currentBestSolution.evaluate();
            iteration++;
            updateTemperature(iteration, maxTime);
            if(currentTemperature < minTemperature) {
                currentTemperature = maxTemperature;
                currentSolution = randomSolution();
            }
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
