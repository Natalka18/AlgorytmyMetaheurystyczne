import java.util.Random;

class Solver {
    private CompleteGraph graph;
    private History history;
    // współczynnik z przedziału (0,1) określający, jak istotna jest częstotliwość zamiany
    private double epsilon;
    private int[] currentShortestPath;
    private int currentMinimum;

    Solver(CompleteGraph graph, int termForWaitingPeriod,
           int termForFrequency, double epsilon) {
        this.graph = graph;
        this.epsilon = epsilon;
        history = new History(termForWaitingPeriod,
                termForFrequency, graph.getNumberOfVertices());
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

    private int[] swapNodes(int[] path, int node1, int node2) {
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
        return path;
    }
}
