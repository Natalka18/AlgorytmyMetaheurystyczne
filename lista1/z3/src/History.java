import java.util.ArrayList;
import java.util.List;

class History {
    //  pamiętane są zamiany danych indeksów w ciągu kroków
    private int sequenceLength;
    private int[][] frequencies;
    private int[][] waitingPeriods;
    private static int WAITING_PERIOD;  // ile iteracji dana zamiana pozostaje tabu
    private static int MEMORY;  // jak długo pamiętane są dane o częstotliwości zamian
    private int iterationNumber;

    History(int sequenceLength, int waitingPeriod, int memory) {
        this.sequenceLength = sequenceLength;
        WAITING_PERIOD = waitingPeriod;
        MEMORY = memory;
        iterationNumber = 0;
        initializeArrays();
    }

    List<Integer[]> getNonTabooPairs() {
        List<Integer[]> result = new ArrayList<>();
        for(int i = 0; i < sequenceLength; i++) {
            for(int j = i + 1; j < sequenceLength; j++) {
                if(waitingPeriods[i][j] == 0) {
                    Integer[] pair = {i, j};
                    result.add(pair);
                }
            }
        }
        return result;
    }

    // zapisuje w historii zamianę na pozycjach index1, index2
    void saveChange(int index1, int index2) {
        if(index1 < sequenceLength && index2 < sequenceLength) {
            incrementFrequency(index1, index2);
            markAsTaboo(index1, index2);
        }
    }

    void endOfIteration() {
        iterationNumber++;
        if(iterationNumber > MEMORY) {
            iterationNumber = 0;
            zeroFrequencies();
        }
        decrementWaitingPeriods();
    }

    int getFrequency(int index1, int index2) {
        return frequencies[index1][index2];
    }

    private void incrementFrequency(int index1, int index2) {
        frequencies[index1][index2]++;
        frequencies[index2][index1]++;
    }

    private void markAsTaboo(int index1, int index2) {
        waitingPeriods[index1][index2] = WAITING_PERIOD;
        waitingPeriods[index2][index1] = WAITING_PERIOD;
    }

    private void zeroFrequencies() {
        for(int i = 0; i < sequenceLength; i++) {
            for(int j = 0; j < sequenceLength; j++) {
                frequencies[i][j] = 0;
            }
        }
    }

    private void decrementWaitingPeriods() {
        for(int i = 0; i < sequenceLength; i++) {
            for(int j = 0; j < sequenceLength; j++) {
                if(waitingPeriods[i][j] > 0) {
                    waitingPeriods[i][j]--;
                }
            }
        }
    }

    private void initializeArrays() {
        frequencies = new int[sequenceLength][sequenceLength];
        waitingPeriods = new int[sequenceLength][sequenceLength];
        for(int i = 0; i < sequenceLength; i++) {
            for(int j = 0; j < sequenceLength; j++) {
                frequencies[i][j] = 0;
                waitingPeriods[i][j] = 0;
            }
        }
    }
}
