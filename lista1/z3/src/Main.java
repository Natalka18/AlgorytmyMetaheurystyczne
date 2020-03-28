import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
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

        Labyrinth labyrinth = new Labyrinth(numberOfRows, numberOfColumns, array);
        Solver solver = new Solver(labyrinth, maxTime, startTime, 0.4,
                numberOfColumns*numberOfRows/5, numberOfColumns*numberOfRows/2);
        solver.solve();
        System.out.println(solver.currentShortestSequence);
    }
}
