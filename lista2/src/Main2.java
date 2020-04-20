import java.util.Scanner;

public class Main2 {
    public static void main(String[] args) {
        // ustalenie parametrów
        double minTemperature = 1;
        double maxTemperature = 30;
        int testedNeighbours = 10;  // ilość testowanych sąsiadów bez zmiany temperatury

        // wczytanie  danych
        Scanner scanner = new Scanner(System.in);
        long maxTime = scanner.nextInt() * 1000;  // w milisekundach
        int n = scanner.nextInt();
        int m = scanner.nextInt();
        int k = scanner.nextInt();
        Integer[][] matrix = new Integer[n][m];
        for(int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                matrix[i][j] = scanner.nextInt();
            }
        }

        Problem problem = new MatrixProblem(minTemperature, maxTemperature,
                testedNeighbours, null, n, m, k, matrix);

        // test drukowania macierzy
        System.out.println("test drukowania macierzy");
        Solution s = new MatrixSolution(matrix, n, m, matrix);
        System.out.println(s.toString());
        //test generowania losowego rozwiązania
        System.out.println("test generowania losowego rozwiązania");
        s = problem.randomSolution();
        System.out.println(s.toString());
    }
}
