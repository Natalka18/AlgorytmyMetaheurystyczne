import java.util.*;

public class LabyrinthSolution extends SolutionAbstract<List<Character>> {
    private Labyrinth labyrinth;
    private Stack<Solution<List<Character>>> neighbours;

    LabyrinthSolution(List<Character> value, int[][] labyrinthMatrix,
                      int rows, int columns) {
        super(value);
        this.labyrinth = new Labyrinth(rows, columns, labyrinthMatrix);
    }

    LabyrinthSolution(List<Character> value, Labyrinth labyrinth) {
        super(value);
        this.labyrinth = labyrinth;
    }

    @Override
    public double evaluate() {
        // problem polega na zminimalizowaniu tej wartości
        int numberOfSteps = labyrinth.checkSequence(super.value);
        // pierwszy wariant
        // a ma wartość 0, jeśli ten ciąg prowadzi do wyjścia,
        // wartość 1 w przeciwnym przypadku
//        int a = (numberOfSteps < super.value.size()) ? 0 : 1;
//        return a * numberOfSteps + numberOfSteps;

        // inny wariant
        if(numberOfSteps == super.value.size()) {
            // jeśli ścieżka nie prowadzi nas do wyjścia, to zwracamy dużą wartość
            return Double.MAX_VALUE;
        } else {
            return numberOfSteps;
        }
    }

    @Override
    public Solution<List<Character>> nextNeighbour() {
        // pierwsza wersja - sąsiadzi różnią się jedną transpozycją od tego rozwiązania
//        if(neighbours == null) {
//            initializeNeighboursList();
//        }
//        if(neighbours.empty()) {
//            return null;
//        }
//        return neighbours.pop();

        // druga wersja - sąsiedzi różnią się na jednym miejscu od tego ciągu,
        // mogą też różnić się od tego ciągu brakiem jednej pary postaci:
        // UD, DU, LR lub RL.

        List<Character> neighbourSequence = new ArrayList<>(super.value);

        // usuwamy niepotrzebne pary kroków
        for(int i = 1; i < neighbourSequence.size(); i++) {
            if(neighbourSequence.get(i) == 'L' && neighbourSequence.get(i - 1) == 'R') {
                // usuwamy tę parę i kończymy szukanie niepotrzebnych par
                neighbourSequence.remove(i);
                neighbourSequence.remove(i - 1);
                break;
            }
            if(neighbourSequence.get(i) == 'R' && neighbourSequence.get(i - 1) == 'L') {
                // usuwamy tę parę i kończymy szukanie niepotrzebnych par
                neighbourSequence.remove(i);
                neighbourSequence.remove(i - 1);
                break;
            }
            if(neighbourSequence.get(i) == 'D' && neighbourSequence.get(i - 1) == 'U') {
                // usuwamy tę parę i kończymy szukanie niepotrzebnych par
                neighbourSequence.remove(i);
                neighbourSequence.remove(i - 1);
                break;
            }
            if(neighbourSequence.get(i) == 'U' && neighbourSequence.get(i - 1) == 'D') {
                // usuwamy tę parę i kończymy szukanie niepotrzebnych par
                neighbourSequence.remove(i);
                neighbourSequence.remove(i - 1);
                break;
            }
        }

        // losujemy indeks, na którym zmienimy wartość
        Random random = new Random();
        //List<Character> neighbourSequence = new ArrayList<>(super.value);
        int index = random.nextInt(neighbourSequence.size());
        List<Character> availableSteps = new ArrayList<>();
        availableSteps.add(Labyrinth.LEFT);
        availableSteps.add(Labyrinth.RIGHT);
        availableSteps.add(Labyrinth.DOWN);
        availableSteps.add(Labyrinth.UP);
        //availableSteps.remove(neighbourSequence.get(index));
        // nie chcemy też zmieniać na krok, który spowoduje powrót do położenia z poprzedniego kroku
        if(index > 0) {
            char previousStep = neighbourSequence.get(index - 1);
            char availableStepToRemove;
            if(previousStep == 'L') {
                availableStepToRemove = 'R';
            } else if(previousStep == 'R') {
                availableStepToRemove = 'L';
            } else if(previousStep == 'D') {
                availableStepToRemove = 'U';
            } else {
                availableStepToRemove = 'D';
            }
            availableSteps.remove(new Character(availableStepToRemove));
        }
        if(index < neighbourSequence.size() - 1) {
            char nextStep = neighbourSequence.get(index + 1);
            char availableStepToRemove;
            if(nextStep == 'L') {
                availableStepToRemove = 'R';
            } else if(nextStep == 'R') {
                availableStepToRemove = 'L';
            } else if(nextStep == 'D') {
                availableStepToRemove = 'U';
            } else {
                availableStepToRemove = 'D';
            }
            availableSteps.remove(new Character(availableStepToRemove));
        }
        // umieszczamy losową wartość pod indeksem index
        int newStepIndex = random.nextInt(availableSteps.size());
        char newStep = availableSteps.get(newStepIndex);
        neighbourSequence.set(index, newStep);



        return new LabyrinthSolution(neighbourSequence, labyrinth);
    }

    @Override
    public Solution<List<Character>> copy() {
        return new LabyrinthSolution(super.value, labyrinth);
    }

    @Override
    public String toString() {
        StringBuilder s = new StringBuilder();
        for(Character step : super.value) {
            s.append(step);
        }
        return s.toString();
    }

    // sąsiedzi to ciągi z jedną transpozycją
    private void initializeNeighboursList() {
        neighbours = new Stack<>();
        int sequenceLength = super.value.size();
        // wyznaczamy pary indeksów do zamiany miejscami
        for(int index1 = 0; index1 < sequenceLength - 1; index1++) {
            for(int index2 = index1 + 1; index2 < sequenceLength; index2++) {
                // kopiujemy aktualny ciąg kroków
                List<Character> newSequence = new ArrayList<>(super.value);
                // i przestawiamy znaki o indeksach index1 i index2
                char temp = super.value.get(index1);
                newSequence.set(index1, super.value.get(index2));
                newSequence.set(index2, temp);
                neighbours.push(new LabyrinthSolution(newSequence, labyrinth));
            }
        }
    }
}
