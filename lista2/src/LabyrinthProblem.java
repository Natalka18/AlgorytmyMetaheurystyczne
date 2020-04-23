import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class LabyrinthProblem extends ProblemAbstract<List<Character>> {
    private Labyrinth labyrinth;

    LabyrinthProblem(double minTemperature, double maxTemperature,
                     int numberOfTestedNeighbours,
                     List<Character> startingSequence,
                     int[][] labyrinthMatrix, int rows, int columns) {
        super(minTemperature, maxTemperature, numberOfTestedNeighbours,
                new LabyrinthSolution(startingSequence, labyrinthMatrix, rows, columns));
        this.labyrinth = new Labyrinth(rows, columns, labyrinthMatrix);
    }

    @Override
    public Solution<List<Character>> randomSolution() {
        // TODO ulepszyć randomSolution
        // najprostsza wersja - losujemy ciąg kroków o długości n*m
        /*
        int sequenceLength = labyrinth.numberOfColumns * labyrinth.numberOfRows;
        char[] characters = {Labyrinth.UP, Labyrinth.DOWN, Labyrinth.LEFT, Labyrinth.RIGHT};
        List<Character> sequence;
        do {
            sequence = randomSequence(sequenceLength, characters);
        } while(labyrinth.checkSequence(sequence) == sequenceLength);
        // losowanie ciągu dopóki nie uzyskamy ciągu, który wyprowadza z labiryntu
        return new LabyrinthSolution(sequence, labyrinth);
        */

        // wersja druga - z chodzeniem wzdłuż ścian i losowaniem kroku,
        // gdy obok nie ma ścian
        List<Character> sequence = new ArrayList<>();
        // znak oznaczający, że nie znaleziono kierunku, w którym należy pójść
        char directionNotFound = 'n';
        char directionToExit = checkDirection(Labyrinth.AIM, directionNotFound);
        while(directionToExit == directionNotFound) {
            // jeśli zbyt długo chodzimy, to szukamy ciągu kroków od początku
            if(sequence.size() >= labyrinth.numberOfRows * labyrinth.numberOfColumns) {
                labyrinth.setAgentCoordinates();
                sequence.clear();
            }
            // sprawdzenie, czy obok agenta jest ściana. Jeśli tak, idziemy wzdłuż niej
            char directionToWall = checkDirection(Labyrinth.WALL, directionNotFound);
            if(directionToWall != directionNotFound) {
                // idziemy przy ścianie, sprawdzając, czy nie ma w niej wyjścia
                if(goAlongTheWall(directionToWall, sequence)) {
                    // znaleziono wyjście na tej ścianie
                    directionToExit = directionToWall;
                }
            } else {
                makeRandomStep(sequence);
                directionToExit = checkDirection(Labyrinth.AIM, directionNotFound);
            }
        }
        sequence.add(directionToExit);
        labyrinth.setAgentCoordinates();
        return new LabyrinthSolution(sequence, labyrinth);
    }

    @Override
    public Solution<List<Character>> compareSolutions(Solution<List<Character>> oldSolution,
                                                      Solution<List<Character>> newSolution) {
        double e = Math.E;
        double newEval = newSolution.evaluate();
        double oldEval = oldSolution.evaluate();
        if(newEval < oldEval) {
            return newSolution.copy();
        }
        // prawdopodobieństwo przejścia do newSolution
        double probability = Math.pow(e, (oldEval - newEval) / getCurrentTemperature());
        Random random = new Random();
        if(random.nextInt(1) < probability) {
            return newSolution.copy();
        }
        return oldSolution.copy();
    }

    @Override
    public void updateTemperature(int iteration, long maxTime) {
        // TODO ulepszyć updateTemperature
        double r = 0.7;
        super.currentTemperature *= r;
    }

    @Override
    public Solution<List<Character>> processTheSolution(Solution<List<Character>> currentSolution) {
        return currentSolution;
    }

    @Override
    public Solution<List<Character>> getBetterSolution(Solution<List<Character>> solution1,
                                                       Solution<List<Character>> solution2) {
        if(solution1.evaluate() < solution2.evaluate()) {
            return solution1;
        }
        return solution2;
    }

    // zwraca, w którym kierunku od położenia agenta znajduje się pole field.
    // Jeśli nie ma field obok agenta, zwracany jest znak c.
    private char checkDirection(int field, char c) {
        int row = labyrinth.getAgentRow();
        int column = labyrinth.getAgentColumn();

        if(labyrinth.getField(row - 1, column) == field) {
            return Labyrinth.UP;
        }
        if(labyrinth.getField(row + 1, column) == field) {
            return Labyrinth.DOWN;
        }
        if(labyrinth.getField(row, column + 1) == field) {
            return Labyrinth.RIGHT;
        }
        if(labyrinth.getField(row, column - 1) == field) {
            return Labyrinth.LEFT;
        }
        return c;
    }

    // metoda zakłada, że wokół agenta nie ma ścian, więc może iść w każdym kierunku.
    // previousStep - poprzedni krok, jaki agent wykonał,
    // sequence - lista wykonanych kroków.
    // Krok losujemy w taki sposób, żeby nie wrócić do miejsca, gdzie agent był krok wcześniej
    private void makeRandomStep(List<Character> sequence) {
        char previousStep = (sequence.isEmpty() ? 'n' : sequence.get(sequence.size() - 1));
        List<Character> availableSteps = new ArrayList<>();
        availableSteps.add(Labyrinth.DOWN);
        availableSteps.add(Labyrinth.UP);
        availableSteps.add(Labyrinth.RIGHT);
        availableSteps.add(Labyrinth.LEFT);
        //availableSteps.remove(new Character(previousStep));
        Random random = new Random();
        int index = random.nextInt(availableSteps.size());
        char step = availableSteps.get(index);
        sequence.add(step);
        labyrinth.makeStep(step);
    }

    // direction - gdzie względem agenta jest ściana,
    // sequence - ciąg kroków wykonanych do tej pory.
    // zwraca true, jeśli znaleziono wyjście
    private boolean goAlongTheWall(char direction, List<Character> sequence) {
        Random random = new Random();
        if(direction == Labyrinth.UP || direction == Labyrinth.DOWN) {
            // losowanie, czy idziemy wzdłuż ściany w lewo, czy w prawo
            char step = (random.nextBoolean() ? Labyrinth.LEFT : Labyrinth.RIGHT);
            // pętlę wykonujemy, dopóki na górze (na dole) widzimy ścianę
            do {
                // jeśli się da, agent robi krok, jeśli nie - przerywamy pętlę
                if(!labyrinth.makeStep(step)) {
                    break;
                } else {
                    sequence.add(step);
                }
            } while(checkDirection(Labyrinth.WALL, 'n') == direction);
        } else if(direction == Labyrinth.RIGHT || direction == Labyrinth.LEFT) {
            // losowanie, czy idziemy wzdłuż ściany w górę, czy w dół
            char step = (random.nextBoolean() ? Labyrinth.UP : Labyrinth.DOWN);
            // pętlę wykonujemy, dopóki na prawo (na lewo) widzimy ścianę
            do {
                // jeśli się da, agent robi krok, jeśli nie - przerywamy pętlę
                if(!labyrinth.makeStep(step)) {
                    break;
                } else {
                    sequence.add(step);
                }
            } while(checkDirection(Labyrinth.WALL, 'n') == direction);
        }

        return checkDirection(Labyrinth.AIM, 'n') != 'n';
    }

    // zwraca losowy ciąg o długości length składający się ze znaków z tablicy characters
    private List<Character> randomSequence(int length, char[] characters) {
        List<Character> result = new ArrayList<>();
        Random random = new Random();
        for(int i = 0; i < length; i++) {
            // losujemy znak z tablicy characters
            int index = random.nextInt(characters.length);
            result.add(characters[index]);
        }
        return result;
    }
}
