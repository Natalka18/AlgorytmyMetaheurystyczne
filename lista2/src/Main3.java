import java.util.Scanner;

public class Main3 {
    public static void main(String[] args) {
        // ustalenie parametrów
        double minTemperature = 1;
        double maxTemperature = 200;
        int testedNeighbours = 10;  // ilość testowanych sąsiadów bez zmiany temperatury

        // wczytanie danych
        Scanner scanner = new Scanner(System.in);
        int maxTime = scanner.nextInt() * 1000;  // w milisekundach
        int numberOfRows = scanner.nextInt();
        int numberOfColumns = scanner.nextInt();
        int[][] array = new int[numberOfRows][numberOfColumns];
        scanner.nextLine();
        for(int i = 0; i < numberOfRows; i++) {
            String line = scanner.nextLine();
            int[] row = line.chars().map(x->x-'0').toArray();
            array[i] = row;
        }
        long startTime = System.currentTimeMillis();

        Problem problem = new LabyrinthProblem(minTemperature, maxTemperature,
                testedNeighbours, null, array, numberOfRows, numberOfColumns);

        problem.solve(startTime, maxTime);
        Solution solution = problem.getCurrentBestSolution();
        String solutionString = solution.toString();
        System.err.println(solutionString);
        System.out.println(solutionString.length());
    }
}
