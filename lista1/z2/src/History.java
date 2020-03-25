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

    History(int termForWaitingPeriod, int termForFrequency,
            int numberOfVertices) {
        this.WAITING_PERIOD = termForWaitingPeriod;
        this.TERM_FOR_FREQUENCY = termForFrequency;
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

    void resetFrequency() {
        for(Map<Integer, Integer> map : frequency) {
            for (Map.Entry<Integer, Integer> entry : map.entrySet()) {
                int key = entry.getKey();
                map.put(key, 0);
            }
        }
    }

    void decrementWaitingElements() {
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

    void incrementFrequencyForPair(int node1, int node2) {
        Map<Integer, Integer> map = frequency.get(node1);
        if(!map.containsKey(node2)) {
            map = frequency.get(node2);
            Integer frequency = map.get(node1);
            frequency++;
            map.put(node1, frequency);
        } else {
            Integer frequency = map.get(node2);
            frequency++;
            map.put(node2, frequency);
        }
    }

    void markAsTaboo(int node1, int node2) {
        Map<Integer, Integer> map = waitingPeriods.get(node1);
        if(!map.containsKey(node2)) {
            map = waitingPeriods.get(node2);
            map.put(node1, WAITING_PERIOD);
        } else {
            Integer period = map.get(node2);
            map.put(node1, WAITING_PERIOD);
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
}
