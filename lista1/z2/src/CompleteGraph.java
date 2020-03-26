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
    // node1 i node2 w trasie path o koszcie oldCost
    int getNewCostOfPath(int[] path, int oldCost, int node1, int node2) {
        int index1 = 0;
        int index2 = 0;
        for(int i = 0; i < numberOfVertices; i++) {
            if(path[i] == node1) {
                index1 = i;
                int index = Math.floorMod(i-1, numberOfVertices);
                oldCost = oldCost - getCostOfEdge(path[index], node1) + getCostOfEdge(path[index], node2); // tu było + 0
                index = Math.floorMod(i+1, numberOfVertices);
                oldCost = oldCost - getCostOfEdge(node1, path[index]) + getCostOfEdge(node2, path[index]);
            }
            if(path[i] == node2) {
                index2 = i;
                int index = Math.floorMod(i-1, numberOfVertices);
                oldCost = oldCost - getCostOfEdge(path[index], node2) + getCostOfEdge(path[index], node1);
                index = Math.floorMod(i+1, numberOfVertices);
                oldCost = oldCost - getCostOfEdge(node2, path[index]) + getCostOfEdge(node1, path[index]);
            }
        }
        if((index1 == 0 && index2 == numberOfVertices - 1) ||
                (index2 == 0 && index1 == numberOfVertices - 1)) {
            oldCost = oldCost + getCostOfEdge(path[index1], path[index2]) +
                    getCostOfEdge(path[index2], path[index1]);
        }
        return oldCost;
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
