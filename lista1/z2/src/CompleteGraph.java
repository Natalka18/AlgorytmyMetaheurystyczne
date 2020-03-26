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

    // zwraca koszt trasy będącej wynikiem zamiany miejscami miasta
    // node1 i node2 w trasie path
    int getNewCostOfPath(int[] path, int node1, int node2) {
        int[] newPath = new int[numberOfVertices];
        System.arraycopy(path, 0, newPath, 0, numberOfVertices);
        swapNodes(newPath, node1, node2);
        return getCostOfPath(newPath);
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
