import java.util.List;

class Labyrinth {
    private int[][] labyrinth;
    int numberOfRows;
    int numberOfColumns;
    private int agentRow;
    private int agentColumn;
    static int WALL = 1;
    static int AGENT = 5;
    static int AIM = 8;
    static int FREE = 0;
    static char UP = 'U';
    static char DOWN = 'D';
    static char RIGHT = 'R';
    static char LEFT = 'L';

    Labyrinth(int rows, int columns, int[][] labyrinth) {
        this.labyrinth = labyrinth;
        numberOfColumns = columns;
        numberOfRows = rows;
        setAgentCoordinates();
    }

    // zwraca indeks kroku, w którym agent dochodzi już do celu.
    // Jeśli w tej sekwencji kroków agent nie dojdzie do celu, zwracana jest długość ciągu.
    // Niepotrzebny fragment listy sequence jest usuwany.
    int checkSequence(List<Character> sequence) {
        int currentPositionRow = agentRow;
        int currentPositionColumn = agentColumn;
        for(int i = 0; i < sequence.size(); i++) {
            Character step = sequence.get(i);
            if(step.equals(UP)) {
                currentPositionRow--;
            } else if(step.equals(DOWN)) {
                currentPositionRow++;
            } else if(step.equals(RIGHT)) {
                currentPositionColumn++;
            } else if(step.equals(LEFT)) {
                currentPositionColumn--;
            }
            if(currentPositionRow >= numberOfRows ||
                    currentPositionColumn >= numberOfColumns) {
                return sequence.size();
            } else if(labyrinth[currentPositionRow][currentPositionColumn] == WALL) {
                return sequence.size();
            } else if(labyrinth[currentPositionRow][currentPositionColumn] == AIM) {
                // usunięcie niepotrzebnego fragmentu sekwencji
                while(sequence.size() > i + 1) {
                    sequence.remove(sequence.size() - 1);
                }
                return i;
            }
        }
        return sequence.size();
    }

    // zwraca true, jeśli udało się zrobić krok, false jeśli nie.
    // Krok można zrobić, jeśli pole jest różne od WALL i różne od AIM.
    boolean makeStep(char direction) {
        if(direction == UP && getField(agentRow - 1, agentColumn) != WALL &&
                getField(agentRow - 1, agentColumn) != AIM) {
            agentRow--;
            return true;
        } else if(direction == DOWN && getField(agentRow + 1, agentColumn) != WALL &&
                getField(agentRow + 1, agentColumn) != AIM) {
            agentRow++;
            return true;
        } else if(direction == RIGHT && getField(agentRow, agentColumn + 1) != WALL &&
                getField(agentRow, agentColumn + 1) != AIM) {
            agentColumn++;
            return true;
        } else if(direction == LEFT && getField(agentRow, agentColumn - 1) != WALL
                && getField(agentRow, agentColumn - 1) != AIM) {
            agentColumn--;
            return true;
        }
        return false;
    }

    int getAgentRow() {
        return agentRow;
    }

    int getAgentColumn() {
        return agentColumn;
    }

    int getField(int row, int column) {
        return labyrinth[row][column];
    }

    void setAgentCoordinates() {
        for(int i = 0; i < numberOfRows; i++) {
            for (int j = 0; j < numberOfColumns; j++) {
                if(labyrinth[i][j] == AGENT) {
                    agentRow = i;
                    agentColumn = j;
                }
            }
        }
    }
}
