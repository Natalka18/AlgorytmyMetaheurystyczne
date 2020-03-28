import java.util.List;

class Labyrinth {
    private int[][] labyrinth;
    private int numberOfRows;
    private int numberOfColumns;
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
    // Jeśli w tej sekwencji kroków agent nie dojdzie do celu, zwracana jest długość ciągu
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
                return i;
            }
        }
        return sequence.size();
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

    private void setAgentCoordinates() {
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
