import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int maxTime = scanner.nextInt();
        int numberOfRows = scanner.nextInt();
        int numberOfColumns = scanner.nextInt();
        int[][] array = new int[numberOfRows][numberOfColumns];
        scanner.nextLine();
        for(int i = 0; i < numberOfRows; i++) {
            String line = scanner.nextLine();
            int[] row = line.chars().map(x->x-'0').toArray();
            array[i] = row;
        }
        //____________________________________________________________________________________
        // test
        for(int i = 0; i < numberOfRows; i++) {
            for(int j = 0; j < numberOfColumns; j++) {
                System.out.print(array[i][j]);
            }
            System.out.println();
        }

        Labyrinth labyrinth = new Labyrinth(numberOfRows, numberOfColumns, array);

        List<Character> sequence = Arrays.asList('L', 'U', 'R', 'D', 'D', 'L', 'D', 'U');

        System.out.println(labyrinth.checkSequence(sequence));
    }
}
