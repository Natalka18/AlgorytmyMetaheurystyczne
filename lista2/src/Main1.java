import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main1 {
    public static void main(String[] args) {
        // ustalenie parametrów
        double minTemperature = 1;
        double maxTemperature = 30;
        int testedNeighbours = 10;  // ilość testowanych sąsiadów bez zmiany temperatury

        // wczytanie  danych
        Scanner scanner = new Scanner(System.in);
        long maxTime = scanner.nextInt() * 1000;  // w milisekundach
        double x1 = scanner.nextInt();
        double x2 = scanner.nextInt();
        double x3 = scanner.nextInt();
        double x4 = scanner.nextInt();
        long startTime = System.currentTimeMillis();
        List<Double> point = new ArrayList<>();
        point.add(x1); point.add(x2); point.add(x3); point.add(x4);

        // rozwiązanie
        ProblemAbstract problem = new SalomonProblem(minTemperature, maxTemperature, testedNeighbours, point);
        problem.solve(startTime, maxTime);
        Solution solution = problem.getCurrentBestSolution();
        System.out.println(solution.toString());
    }
}
