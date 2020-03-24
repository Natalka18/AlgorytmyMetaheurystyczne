import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int maxTime = scanner.nextInt() * 1000;
        int b = scanner.nextInt();
        double[] start;
        double step = 0.000000001;
        MinimizationProblem function = MinimizationProblemFactory.getMinimizationProblem(b);
        if(b == 1) {  // funkcja G
            start = new double[]{0.1, 0.1, 0.1, 0.1};
        } else {  // funkcja H
            start = new double[]{1,1,1,1.1};
        }
        MinimizationProblemSolver solver = new MinimizationProblemSolver(maxTime, function, step, start);
        double[] solution = solver.solve();
        double minValue = function.functionValue(solution);
        for (double v : solution) {
            System.out.print(Double.toString(v) + ' ');
        }
        System.out.print(minValue);
    }
}
