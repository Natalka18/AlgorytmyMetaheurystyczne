import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class Main {
    public static void main(String[] args) throws FileNotFoundException {
        int populationSize = 100;
        double mutationProbability = 0.7;

        Scanner scanner = new Scanner(System.in);
        int maxTime = scanner.nextInt() * 1000;  // w milisekundach
        int numberOfLetters = scanner.nextInt();
        int numberOfInitialSolutions = scanner.nextInt();
        scanner.nextLine();
        Hashtable<Character, Integer> letterValues = new Hashtable<>();
        List<Character> availableLetters = new ArrayList<>();
        List<String> initialSolutions = new ArrayList<>();
        for (int i = 0; i < numberOfLetters; i++) {
            String line = scanner.nextLine();
            String[] s = line.split(" ");
            Character letter = s[0].charAt(0);
            Integer value = Integer.parseInt(s[1]);
            letterValues.put(letter, value);
            availableLetters.add(letter);
        }
        for (int i = 0; i < numberOfInitialSolutions; i++) {
            String word = scanner.nextLine();
            initialSolutions.add(word);
        }

        long startTime = System.currentTimeMillis();

        // zmienić ścieżkę na "dict.txt"
        scanner = new Scanner(new File("out/production/z2/dict.txt"));
        Dictionary dictionary = new Dictionary();
        while(scanner.hasNext()) {
            dictionary.add(scanner.next());
        }
        scanner.close();

        Population population = new Population(populationSize, letterValues,
                initialSolutions, mutationProbability, dictionary, availableLetters);
        population.start(startTime, maxTime);
        String solution = population.getBestSolution();
        System.err.println(solution);
        System.out.println(population.evaluate(solution));
    }
}
