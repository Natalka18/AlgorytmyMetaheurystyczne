import java.util.List;
import java.util.Random;

class Solver {
    private CompleteGraph graph;
    private History history;
    // współczynnik z przedziału (0,1) określający, jak istotna jest częstotliwość zamiany
    private double epsilon;
    int[] currentShortestPath;
    int currentMinimum;
    private long maxTime;  // w milisekundach
    private long startTime;

    Solver(CompleteGraph graph, int termForWaitingPeriod,
           int termForFrequency, double epsilon, long maxTime, long startTime) {
        this.graph = graph;
        this.epsilon = epsilon;
        this.maxTime = maxTime;
        history = new History(termForWaitingPeriod,
                termForFrequency, graph.getNumberOfVertices());
        this.startTime = startTime;
    }

    void solve() {
        int numberOfVertices = graph.getNumberOfVertices();
        currentShortestPath = randomPath();
        currentMinimum = graph.getCostOfPath(currentShortestPath);
        boolean aspiration = false;
        int[] currentPath = new int[numberOfVertices];
        System.arraycopy(currentShortestPath, 0, currentPath, 0, numberOfVertices);

        while(System.currentTimeMillis() - startTime < maxTime) {
            List<Integer[]> tabooPairs = history.getTabooPairs();
            int currentCost;
            if(!tabooPairs.isEmpty()) {
                aspiration = false; // czy zastosowano kryterium aspiracji
                Integer[] bestTabooPair = tabooPairs.get(0);
                for (Integer[] pair : tabooPairs) {
                    int costAfterChange = graph.getNewCostOfPath(currentPath,
                            pair[0], pair[1]);
                    // kryterium aspiracji
                    if (costAfterChange < currentMinimum) {
                        currentMinimum = costAfterChange;
                        bestTabooPair = pair;
                        aspiration = true;
                    }
                }

                if(aspiration) {
                    swapNodes(currentPath, bestTabooPair[0], bestTabooPair[1]);
                    System.arraycopy(currentPath, 0, currentShortestPath,
                            0, numberOfVertices);
                    history.markAsTaboo(bestTabooPair[0], bestTabooPair[1]);
                    history.incrementFrequencyForPair(bestTabooPair[0], bestTabooPair[1]);
                }
            }
             if(!aspiration) {  // wybór najlepszej zamiany, która nie jest zakazana
                 List<Integer[]> nonTabooPairs = history.getNonTabooPairs();
                 if (!nonTabooPairs.isEmpty()) {
                     Integer[] bestPair = nonTabooPairs.get(0);
                     int frequency = history.getFrequencyOfPair(bestPair[0], bestPair[1]);
                     // wartość funkcji oceny z zamianą bestPair pogorszona o współczynnik
                     // związany z częstotliwością zamiany tej pary
                     double bestEval = graph.getNewCostOfPath(currentPath,
                             bestPair[0], bestPair[1]) + epsilon * frequency;
                     double eval = bestEval;
                     for (Integer[] pair : nonTabooPairs) {
                         frequency = history.getFrequencyOfPair(pair[0], pair[1]);
                         eval = graph.getNewCostOfPath(currentPath,
                                 pair[0], pair[1]) + epsilon * frequency;
                         if (eval < bestEval) {
                             bestEval = eval;
                             bestPair = pair;
                         }
                     }

                     swapNodes(currentPath, bestPair[0], bestPair[1]);
                     history.markAsTaboo(bestPair[0], bestPair[1]);
                     history.incrementFrequencyForPair(bestPair[0], bestPair[1]);
                 }
             }
             currentCost = graph.getCostOfPath(currentPath);
             if (currentCost < currentMinimum) {
                 currentMinimum = currentCost;
                 System.arraycopy(currentPath, 0, currentShortestPath, 0, numberOfVertices);
             }

            history.endOfIteration();
        }
    }

    private int[] randomPath() {
        int n = graph.getNumberOfVertices();
        int[] path = new int[n];
        Random random = new Random();
        for(int i = 0; i < n; i++) {
            path[i] = i;
        }
        for(int i = 0; i < n-1; i++) {
            int randomIndex = random.nextInt(n-i);
            int temp = path[n-1-i];
            path[n-1-i] = path[randomIndex];
            path[randomIndex] = temp;
        }
        return path;
    }

    private void swapNodes(int[] path, int node1, int node2) {
        int index1 = 0;
        int index2 = 0;
        for(int i = 0; i < path.length; i++) {
            if(node1 == path[i]) {
                index1 = i;
            }
            if(node2 == path[i]) {
                index2 = i;
            }
        }
        int temp = path[index1];
        path[index1] = path[index2];
        path[index2] = temp;
    }
}
