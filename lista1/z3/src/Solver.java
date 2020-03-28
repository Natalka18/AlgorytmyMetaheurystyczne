import java.util.ArrayList;
import java.util.List;

class Solver {
    private Labyrinth labyrinth;
    private History history;
    private long maxTime;  // w milisekundach
    private long startTime;  // kiedy rozpoczęło się wykonywanie programu
    // współczynnik z przedziału (0,1) określający, jak istotna jest częstotliwość zamiany
    private double epsilon;
    List<Character> currentShortestSequence;

    Solver(Labyrinth labyrinth, long maxTime, long startTime,
           double epsilon, int waitingPeriod, int memory) {
        this.labyrinth = labyrinth;
        this.maxTime = maxTime;
        this.startTime = startTime;
        this.epsilon = epsilon;
        currentShortestSequence = findCorrectSequence();
        assert currentShortestSequence != null;
        history = new History(currentShortestSequence.size(), waitingPeriod, memory);
    }

    void solve() {
        List<Character> currentSequence = new ArrayList<>(currentShortestSequence);
        while(System.currentTimeMillis() - startTime < maxTime) {
            // wybór najleszego niezakazanego sąsiada
            List<Integer[]> nonTabooPairs = history.getNonTabooPairs();
            Integer[] bestPair = new Integer[2];
            double bestEval = 0;
            if(!nonTabooPairs.isEmpty()) {
                bestPair = nonTabooPairs.get(0);
                bestEval = eval(currentSequence, bestPair[0], bestPair[1]);
                for (Integer[] pair : nonTabooPairs) {
                    double currentEval = eval(currentSequence, pair[0], pair[1]);
                    if (currentEval < bestEval) {
                        bestPair = pair;
                        bestEval = currentEval;
                    }
                }
                // najlepszy sąsiad staje się obecnym ciągiem
                currentSequence = getNeighbour(currentSequence, bestPair[0], bestPair[1]);
                history.saveChange(bestPair[0], bestPair[1]);
            }
            // zapamiętujemy obecny ciąg, jeśli jest krótszy od obecnie najlepszego
            int currentSequenceLength = labyrinth.checkSequence(currentSequence);
            int currentShortestSequenceLength = labyrinth.checkSequence(currentShortestSequence);
            if(currentSequenceLength < currentShortestSequenceLength) {
                currentShortestSequence = currentSequence.subList(0, currentSequenceLength + 1);
            }
            history.endOfIteration();
        }
    }

    // zwraca wartość funkcji oceny sąsiada utworzonego przez zamianę kroków
    // o indeksach index1 i index2 w ciągu sequence
    private double eval(List<Character> sequence, int index1, int index2) {
        List<Character> neighbour = getNeighbour(sequence, index1, index2);
        int numberOfSteps = labyrinth.checkSequence(neighbour);
        int a = (numberOfSteps < neighbour.size()) ? 0 : 1;
        int frequency = history.getFrequency(index1, index2);
        return a * numberOfSteps + numberOfSteps + epsilon * frequency;
    }

    private List<Character> getNeighbour(List<Character> sequence, int index1, int index2) {
        List<Character> newSequence = new ArrayList<>(sequence);
        swapSteps(newSequence, index1, index2);
        return newSequence;
    }

    private void swapSteps(List<Character> sequence, int index1, int index2) {
        if(index1 < sequence.size() && index2 < sequence.size()) {
            char temp = sequence.get(index1);
            sequence.set(index1, sequence.get(index2));
            sequence.set(index2, temp);
        }
    }

    private List<Character> findCorrectSequence() {
        List<Character> result = new ArrayList<>();
        int currentRow = labyrinth.getAgentRow();
        int currentColumn = labyrinth.getAgentColumn();

        while(labyrinth.getField(currentRow,currentColumn - 1) != Labyrinth.AIM &&
                labyrinth.getField(currentRow,currentColumn + 1) != Labyrinth.AIM &&
                labyrinth.getField(currentRow + 1,currentColumn) != Labyrinth.AIM &&
                labyrinth.getField(currentRow - 1,currentColumn) != Labyrinth.AIM) {
            while (labyrinth.getField(currentRow, currentColumn - 1) != Labyrinth.WALL) {
                if(labyrinth.getField(currentRow + 1, currentColumn) == Labyrinth.AIM) {
                    result.add(Labyrinth.DOWN);
                    return result;
                }
                result.add(Labyrinth.LEFT);
                currentColumn--;
            }
            if(labyrinth.getField(currentRow + 1, currentColumn) == Labyrinth.AIM) {
                result.add(Labyrinth.DOWN);
                return result;
            }
            while (labyrinth.getField(currentRow - 1, currentColumn) != Labyrinth.WALL) {
                if (labyrinth.getField(currentRow, currentColumn - 1) == Labyrinth.AIM) {
                    result.add(Labyrinth.LEFT);
                    return result;
                }
                result.add(Labyrinth.UP);
                currentRow--;
            }
            if (labyrinth.getField(currentRow, currentColumn - 1) == Labyrinth.AIM) {
                result.add(Labyrinth.LEFT);
                return result;
            }
            while (labyrinth.getField(currentRow, currentColumn + 1) != Labyrinth.WALL) {
                if (labyrinth.getField(currentRow - 1, currentColumn) == Labyrinth.AIM) {
                    result.add(Labyrinth.UP);
                    return result;
                }
                result.add(Labyrinth.RIGHT);
                currentColumn++;
            }
            if (labyrinth.getField(currentRow - 1, currentColumn) == Labyrinth.AIM) {
                result.add(Labyrinth.UP);
                return result;
            }
            while (labyrinth.getField(currentRow + 1, currentColumn) != Labyrinth.WALL) {
                if (labyrinth.getField(currentRow, currentColumn + 1) == Labyrinth.AIM) {
                    result.add(Labyrinth.RIGHT);
                    return result;
                }
                result.add(Labyrinth.DOWN);
                currentRow++;
            }
            if (labyrinth.getField(currentRow, currentColumn + 1) == Labyrinth.AIM) {
                result.add(Labyrinth.RIGHT);
                return result;
            }
        }
        if(labyrinth.getField(currentRow + 1, currentColumn) == Labyrinth.AIM) {
            result.add(Labyrinth.DOWN);
            return result;
        }
        if (labyrinth.getField(currentRow, currentColumn - 1) == Labyrinth.AIM) {
            result.add(Labyrinth.LEFT);
            return result;
        }
        if (labyrinth.getField(currentRow - 1, currentColumn) == Labyrinth.AIM) {
            result.add(Labyrinth.UP);
            return result;
        }
        if (labyrinth.getField(currentRow, currentColumn + 1) == Labyrinth.AIM) {
            result.add(Labyrinth.RIGHT);
            return result;
        }
        return null;
    }
}
