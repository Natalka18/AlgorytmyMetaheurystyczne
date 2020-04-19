public interface Problem<T> {
    Solution<T> randomSolution();
    double getMaxTemperature();
    double getCurrentTemperature();
    double getMinTemperature();
    double getCurrentBestEval();
    Solution<T> getCurrentBestSolution();
    // decyduje, czy aktualne rozwiązanie oldSolution zostanie zmienione
    // (return newSolution), czy nie (return oldSolution)
    Solution<T> compareSolutions(Solution<T> oldSolution, Solution<T> newSolution);
    // zwraca liczbę sąsiadów rozwiązania, jaką należy przetestować
    // przy tej samej temperaturze
    int getNumberOfTestedNeighbours();
    // oblicza nową wartość temperatury na podstawie numeru iteracji
    void updateTemperature(int iteration, long maxTime);
    // szuka rozwiązania problemu dopóki spełniony jest warunek
    // now - startTime < maxTime. MaxTime w milisekundach
    void solve(long startTime, long maxTime);
    // zwraca ulepszone rozwiązanie currentSolution
    Solution<T> processTheSolution(Solution<T> currentSolution);
    Solution<T> getBetterSolution(Solution<T> solution1, Solution<T> solution2);
}
