import java.util.List;
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

        graph.print();
        System.out.println(graph.getCostOfEdge(3,4));
        System.out.println(graph.getCostOfEdge(2,2));
        int[] path = {0, 4, 3, 2, 1};
        System.out.println(graph.getCostOfPath(path));

        History h = new History(4, 4, 5);
        List<Integer[]> list = h.getNonTabooPairs();
        System.out.println("test historii");
        for(Integer[] pair : list) {
            System.out.println(pair[0] + " " + pair[1]);
        }
        System.out.println("_______________________________________________________");
        h.markAsTaboo(4,0);
        list = h.getNonTabooPairs();
        for(Integer[] pair : list) {
            System.out.println(pair[0] + " " + pair[1]);
        }

        // przed wypisaniem odpowiedzi trzeba dodać jedynkę do numerów wierzchołków
    }
}
