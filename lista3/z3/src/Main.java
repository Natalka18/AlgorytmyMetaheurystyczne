import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

class Main {
    public static void main(String[] args) {
        double mutationProbability = 0.8;

        Scanner scanner = new Scanner(System.in);
        int maxTime = scanner.nextInt() * 1000;  // w milisekundach
        int n = scanner.nextInt();
        int m = scanner.nextInt();
        int s = scanner.nextInt();  // liczba rozwiązań początkowych
        int p = scanner.nextInt();  // maksymalna liczebność populacji
        int[][] array = new int[n][m];
        scanner.nextLine();
        for(int i = 0; i < n; i++) {
            String line = scanner.nextLine();
            int[] row = line.chars().map(x->x-'0').toArray();
            array[i] = row;
        }
        List<List<Character>> initialSolutions = new ArrayList<>();
        for (int i = 0; i < s; i++) {
            List<Character> solution = new ArrayList<>();
            String line = scanner.nextLine();
            for (int j = 0; j < line.length(); j++) {
                char c = line.charAt(j);
                solution.add(c);
            }
            initialSolutions.add(solution);
        }
        long startTime = System.currentTimeMillis();
        Labyrinth labyrinth = new Labyrinth(n, m, array);
        Population population = new Population(p, initialSolutions, labyrinth, mutationProbability);
        population.start(startTime, maxTime);
        Solution best = population.getBestSolution();
        System.out.println(labyrinth.checkSequence(best.getSequence()) + 1);
        System.err.println(best.toString());
    }
}
