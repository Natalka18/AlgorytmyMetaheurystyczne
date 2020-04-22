import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main3 {
    public static void main(String[] args) {
        // ustalenie parametrów
        double minTemperature = 1;
        double maxTemperature = 300;
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

        // testy metody checkSequence
        Labyrinth labyrinth = new Labyrinth(numberOfRows, numberOfColumns, array);
        List<Character> list = new ArrayList<>();
        list.add('L');
        list.add('U');
        list.add('L');
        list.add('L');
        list.add('U');
        list.add('L');
        System.out.println(labyrinth.checkSequence(list));
        System.out.println(list.toString());
    }
}
