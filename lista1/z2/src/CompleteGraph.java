class CompleteGraph {
    private int numberOfVertices;
    private int[][] costs;

    CompleteGraph(int numberOfVertices) {
        this.numberOfVertices = numberOfVertices;
        costs = new int[numberOfVertices][numberOfVertices];
    }

    void setCost(int start, int end, int value) {
        costs[start][end] = value;
    }

    int getNumberOfVertices() {
        return numberOfVertices;
    }

    int getCostOfEdge(int start, int end) {
        if(start != end) {
            return costs[start][end];
        } else {
            return 0;
        }
    }

    // przykładowa ścieżka: [0, 4, 3, 2, 1]
    int getCostOfPath(int[] path) {
        int cost = 0;
        for(int i = 0; i < numberOfVertices - 1; i++) {
            int j = i + 1;
            cost = cost + costs[path[i]][path[j]];
        }
        // trzeba dodać powrót
        cost = cost + costs[path[numberOfVertices - 1]][path[0]];
        return cost;
    }

    void print() {
        for(int i = 0; i < numberOfVertices; i++) {
            for(int j = 0; j < numberOfVertices; j++) {
                System.out.print(costs[i][j]);
                System.out.print(" ");
            }
            System.out.println();
        }
    }
}
