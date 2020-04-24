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
        // wersja druga - z chodzeniem wzdłuż ścian i losowaniem kroku,
        // gdy obok nie ma ścian
        List<Character> sequence = new ArrayList<>();
        // znak oznaczający, że nie znaleziono kierunku, w którym należy pójść
        char directionNotFound = 'n';
        while(checkDirection(Labyrinth.AIM, directionNotFound) == directionNotFound) {
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
                    break;
                }
            } else if(checkDirection(Labyrinth.AIM, directionNotFound) == directionNotFound) {
                makeRandomStep(sequence);
            }
        }

        char directionToExit = checkDirection(Labyrinth.AIM, directionNotFound);
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
        double r = 0.8;
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

    // zwraca, w którym kierunku od położenia agenta znajduje się pole field
    // i losuje jeden z tych kierunków.
    // dzięki temu, gdy ściana jest w więcej niż jednym polu obok agenta,
    // wybierzemy losową ścianę, wzdłuż której pójdziemy.
    // Jeśli nie ma field obok agenta, zwracany jest znak c.
    private char checkDirection(int field, char c) {
        int row = labyrinth.getAgentRow();
        int column = labyrinth.getAgentColumn();
        List<Character> directions = new ArrayList<>();
        if(labyrinth.getField(row - 1, column) == field) {
            directions.add(Labyrinth.UP);
        }
        if(labyrinth.getField(row + 1, column) == field) {
            directions.add(Labyrinth.DOWN);
        }
        if(labyrinth.getField(row, column + 1) == field) {
            directions.add(Labyrinth.RIGHT);
        }
        if(labyrinth.getField(row, column - 1) == field) {
            directions.add(Labyrinth.LEFT);
        }

        if(directions.isEmpty()) {
            return c;
        }

        Random random = new Random();
        int index = random.nextInt(directions.size());
        return directions.get(index);
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
        if(previousStep == Labyrinth.UP) {
            availableSteps.remove(new Character(Labyrinth.DOWN));
        } else if(previousStep == Labyrinth.DOWN) {
            availableSteps.remove(new Character(Labyrinth.UP));
        } else if(previousStep == Labyrinth.RIGHT) {
            availableSteps.remove(new Character(Labyrinth.LEFT));
        } else if(previousStep == Labyrinth.LEFT) {
            availableSteps.remove(new Character(Labyrinth.RIGHT));
        }
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
            while(checkDirection(Labyrinth.WALL, 'n') == direction) {
                // jeśli się da, agent robi krok, jeśli nie - przerywamy pętlę
                if(!labyrinth.makeStep(step)) {
                    break;
                } else {
                    sequence.add(step);
                }
            }
        } else if(direction == Labyrinth.RIGHT || direction == Labyrinth.LEFT) {
            // losowanie, czy idziemy wzdłuż ściany w górę, czy w dół
            char step = (random.nextBoolean() ? Labyrinth.UP : Labyrinth.DOWN);
            // pętlę wykonujemy, dopóki na prawo (na lewo) widzimy ścianę
            while(checkDirection(Labyrinth.WALL, 'n') == direction) {
                // jeśli się da, agent robi krok, jeśli nie - przerywamy pętlę
                if(!labyrinth.makeStep(step)) {
                    break;
                } else {
                    sequence.add(step);
                }
            }
        }

        return checkDirection(Labyrinth.AIM, 'n') != 'n';
    }
}
