import java.util.Scanner;

public class Main3 {
    public static void main(String[] args) {
        // ustalenie parametrów
        double minTemperature = 1;
        double maxTemperature = 200;
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

        Problem problem = new LabyrinthProblem(minTemperature, maxTemperature,
                testedNeighbours, null, array, numberOfRows, numberOfColumns);

        problem.solve(startTime, maxTime);
        Solution solution = problem.getCurrentBestSolution();
        System.out.println(solution.toString());

        // testy metody checkSequence
//        Labyrinth labyrinth = new Labyrinth(numberOfRows, numberOfColumns, array);
//        List<Character> list = new ArrayList<>();
//        list.add('L');
//        list.add('U');
//        list.add('L');
//        list.add('L');
//        list.add('U');
//        list.add('L');
//        System.out.println(labyrinth.checkSequence(list));
//        System.out.println(list.toString());

        // testy metody initializeNeighboursList
//        List<Character> list2 = new ArrayList<>();
//        list2.add('L');
//        list2.add('U');
//        list2.add('R');
//        list2.add('D');
//        Solution s = new LabyrinthSolution(list2, array, numberOfRows, numberOfColumns);
//        System.out.println(s.toString());
//        Solution n = s.nextNeighbour();
//        System.out.println(n.toString());
//        n = s.nextNeighbour();
//        System.out.println(n.toString());
//        n = s.nextNeighbour();
//        System.out.println(n.toString());
//        n = s.nextNeighbour();
//        System.out.println(n.toString());
//        n = s.nextNeighbour();
//        System.out.println(n.toString());
//        n = s.nextNeighbour();
//        System.out.println(n.toString());

        // test losowania ciagu
//        Solution result = problem.randomSolution();
//        System.out.println(result.toString());
    }
}
