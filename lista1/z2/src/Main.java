import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        // wczytanie danych i utworzenie grafu
        Scanner scanner = new Scanner(System.in);
        String line = scanner.nextLine();
        String[] numbers = line.split(" ");
        int maxTime = Integer.parseInt(numbers[0]);
        int numberOfNodes = Integer.parseInt(numbers[1]);

        CompleteGraph graph = new CompleteGraph(numberOfNodes);

        for(int i = 0; i < numberOfNodes; i++) {
            line = scanner.nextLine();
            numbers = line.split(" ");
            for(int j = 0; j < numberOfNodes; j++) {
                int cost = Integer.parseInt(numbers[j]);
                graph.setCost(i, j, cost);
            }
        }

        Solver s = new Solver(graph, numberOfNodes / 3, numberOfNodes, 0.4, 1000 * maxTime);
        s.solve();
        System.out.println(s.currentMinimum);
        int[] path = s.currentShortestPath;
        for(int i = 0; i < numberOfNodes; i++) {
            path[i]++;
            System.out.print(path[i] + " ");
        }
        System.out.print(path[0]);
    }
}
