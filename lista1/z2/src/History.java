import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

class History {
    private int WAITING_PERIOD;  // ile iteracji dana para wierzchołków pozostaje tabu
    private int TERM_FOR_FREQUENCY;  // ile ostatnich iteracji jest branych pod uwagę przy określaniu częstotliwości
    // Map<Node2, waitingPeriod>, indeks w liście jest równy Node1
    private List<Map<Integer, Integer>> waitingPeriods;
    private List<Map<Integer, Integer>> frequency;
    private int iterationNumber;

    History(int termForWaitingPeriod, int termForFrequency,
            int numberOfVertices) {
        this.WAITING_PERIOD = termForWaitingPeriod;
        this.TERM_FOR_FREQUENCY = termForFrequency;
        iterationNumber = 0;
        initializeLists(numberOfVertices);
    }

    List<Integer[]> getNonTabooPairs() {
        List<Integer[]> result = new ArrayList<>();
        for (int node1 = 0; node1 < waitingPeriods.size(); node1++) {
            Map<Integer, Integer> map = waitingPeriods.get(node1);
            for(Map.Entry<Integer, Integer> entry : map.entrySet()) {
                int node2 = entry.getKey();
                int waitingPeriod = entry.getValue();
                if(waitingPeriod == 0) {
                    Integer[] pair = {node1, node2};
                    result.add(pair);
                }
            }
        }
        return result;
    }

    void incrementFrequencyForPair(int node1, int node2) {
        Map<Integer, Integer> map = frequency.get(node1);
        if(map.containsKey(node2)) {
            int f = map.get(node2);
            f++;
            map.put(node2, f);
        } else {
            map = frequency.get(node2);
            if(map.containsKey(node1)) {
                int f = map.get(node1);
                f++;
                map.put(node1, f);
            }
        }
    }

    void markAsTaboo(int node1, int node2) {
        Map<Integer, Integer> map = waitingPeriods.get(node1);
        if(map.containsKey(node2)) {
            map.put(node2, WAITING_PERIOD);
        } else {
            map = waitingPeriods.get(node2);
            if(map.containsKey(node1)) {
                map.put(node1, WAITING_PERIOD);
            }
        }
    }

    void endOfIteration() {
        iterationNumber++;
        if(iterationNumber > TERM_FOR_FREQUENCY) {
            iterationNumber = 0;
            resetFrequency();
        }
        decrementWaitingElements();
    }

    private void resetFrequency() {
        for(Map<Integer, Integer> map : frequency) {
            for (Map.Entry<Integer, Integer> entry : map.entrySet()) {
                int key = entry.getKey();
                map.put(key, 0);
            }
        }
    }

    private void decrementWaitingElements() {
        for (Map<Integer, Integer> map : waitingPeriods) {
            for (Map.Entry<Integer, Integer> entry : map.entrySet()) {
                int node2 = entry.getKey();
                int waitingPeriod = entry.getValue();
                if (waitingPeriod != 0) {
                    waitingPeriod--;
                    map.put(node2, waitingPeriod);
                }
            }
        }
    }

    private void initializeLists(int n) {
        waitingPeriods = new ArrayList<>(n);
        frequency = new ArrayList<>(n);
        for(int i = 0; i < n; i++) {
            Map<Integer, Integer> mapWaitingPeriod = new HashMap<>();
            Map<Integer, Integer> mapFrequency = new HashMap<>();
            waitingPeriods.add(mapWaitingPeriod);
            frequency.add(mapFrequency);
            for(int j = i + 1; j < n; j++) {
                mapWaitingPeriod.put(j, 0);
                mapFrequency.put(j, 0);
            }
        }
    }


    // do testów
    void printFrequency() {
        for(int node1 = 0; node1 < frequency.size(); node1++) {
            Map<Integer, Integer> map = frequency.get(node1);
            for (Map.Entry<Integer, Integer> entry : map.entrySet()) {
                int node2 = entry.getKey();
                int f = entry.getValue();
                System.out.println(node1 + " " + node2 + " " + f);
            }
        }
    }

    // do testów
    void printWaitingPeriod() {
        for(int node1 = 0; node1 < waitingPeriods.size(); node1++) {
            Map<Integer, Integer> map = waitingPeriods.get(node1);
            for (Map.Entry<Integer, Integer> entry : map.entrySet()) {
                int node2 = entry.getKey();
                int f = entry.getValue();
                System.out.println(node1 + " " + node2 + " " + f);
            }
        }
    }
}
