import java.util.Scanner;
import java.util.Stack;

public class Main2 {
    public static void main(String[] args) {
        // ustalenie parametrów
        double minTemperature = 1;
        double maxTemperature = 100;

        // wczytanie  danych
        Scanner scanner = new Scanner(System.in);
        long maxTime = scanner.nextInt() * 1000;  // w milisekundach
        int n = scanner.nextInt();
        int m = scanner.nextInt();
        int k = scanner.nextInt();
        int testedNeighbours = (n * m) / (k * k);  // ilość testowanych sąsiadów bez zmiany temperatury
        Integer[][] matrix = new Integer[n][m];
        for(int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                matrix[i][j] = scanner.nextInt();
            }
        }

        long startTime = System.currentTimeMillis();

        Problem problem = new MatrixProblem(minTemperature, maxTemperature,
                testedNeighbours, null, n, m, k, matrix, 0, 0);

        problem.solve(startTime, maxTime);
        Solution s = problem.getCurrentBestSolution();
        System.err.println(s.toString());
        System.out.println(s.evaluate());
    }
}
