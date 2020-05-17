import java.util.Arrays;
import java.util.Locale;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        scanner.useLocale(Locale.US);
        long maxTime = scanner.nextInt() * 1000;  // w milisekundach
        double inertia = 0.01;
        double localFactor = 3;
        double globalFactor = 10;
        int swarmSize = 30;
        int dimension = 5;
        double[] initialSolution = new double[dimension];
        for (int i = 0; i < dimension; i++) {
            initialSolution[i] = scanner.nextInt();
        }
        double[] epsilons = new double[dimension];
        for (int i = 0; i < dimension; i++) {
            epsilons[i] = scanner.nextDouble();
        }

        long startTime = System.currentTimeMillis();
        Swarm swarm = new Swarm(swarmSize, dimension, initialSolution,
                epsilons, inertia, localFactor, globalFactor);
        swarm.start(startTime, maxTime);

        double[] solution = swarm.getBestGloballySolution();
        double eval = swarm.evaluate(solution);
        System.out.println(solution[0] + " " + solution[1] + " " + solution[2]
                + " " + solution[3] + " " + solution[4] + " " + eval);
    }
}
