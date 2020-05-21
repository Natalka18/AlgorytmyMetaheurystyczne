import java.util.*;

class Population {
    private int size;
    private Hashtable<Character, Integer> letterValue;
    private List<Character> alphabet;  // dostępne litery
    private List<Solution> solutions;
    private String bestSolution;
    private double mutationProbability;
    private Dictionary dictionary;

    Population(int size, Hashtable<Character, Integer> letterValue,
               List<String> initialSolutions, double mutationProbability,
               Dictionary dictionary, List<Character> alphabet) {
        this.size = size;
        this.letterValue = letterValue;
        this.solutions = new ArrayList<>();
        this.mutationProbability = mutationProbability;
        this.dictionary = dictionary;
        this.bestSolution = "";
        this.alphabet = alphabet;
        createInitialPopulation(initialSolutions);
    }

    void start(long startTime, int maxTime) {
        while(System.currentTimeMillis() - startTime < maxTime) {
            System.out.println(this.toString());
            recombination();
            evaluateSolutions();
            select2();
            mutate();
        }
    }

    int evaluate(String solution) {
        // sprawdzenie, czy można ułożyć słowo solution z dostępnych liter
        List<Character> availableLetters = new ArrayList<>(this.alphabet);
        for (int i = 0; i < solution.length(); i++) {
            Character letter = solution.charAt(i);
            if(! availableLetters.contains(letter)) {
                // jeśli słowa nie można ułożyć z podanych liter,
                // to podczas selekcji zostanie usunięte z populacji
                return -1;
            }
            availableLetters.remove(letter);
        }

        // sprawdzenie, czy słowo jest w słowniku
        if(! dictionary.contains(solution)) {
            return 0;
        }

        // punkty za słowo
        int result = 0;
        for (int i = 0; i < solution.length(); i++) {
            Character letter = solution.charAt(i);
            result += getLetterValue(letter);
        }
        return result;
    }

    String getBestSolution() {
        // szukamy najlepszego osobnika w obecnej populacji,
        // porównując każdego z nich z najlepszym rozwiązaniem
        // wybranym z pierwszego pokolenia
        for (Solution solution :
                this.solutions) {
            this.bestSolution = betterSolution(this.bestSolution, solution.getWord());
        }
        return this.bestSolution;
    }

    // ruletka
    private void select1() {
        // tworzymy listę sum częściowych ocen wszystkich osobników
        List<Integer> partialSums = new ArrayList<>();
        Solution s = this.solutions.get(0);
        // + 1, żeby osobniki o wartości zero też mogły zostać wybrane
        partialSums.add((s.getEvaluation() + 1));
        for (int i = 1; i < this.solutions.size(); i++) {
            int lastSum = partialSums.get(partialSums.size() - 1);
            s = this.solutions.get(i);
            partialSums.add(lastSum + (s.getEvaluation() + 1));
        }

        int lastSum = partialSums.get(partialSums.size() - 1);
        Random random = new Random();
        List<Solution> newPopulation = new ArrayList<>();
        // losujemy osobnika tyle razy, żeby zapełnić wszystkie miejsca w populacji
            for (int i = 0; i < this.size; i++) {
                int randInt = random.nextInt(lastSum);
                int j;
                for (j = 0; j < partialSums.size(); j++) {
                    if (randInt < partialSums.get(j)) {
                        break;
                    }
                }
                newPopulation.add(this.solutions.get(j).clone());
            }

            this.solutions = newPopulation;
    }

    // turniej
    private void select2() {
        int n = 5;  // liczba osobników w jednej grupie
        List<Solution> newPopulation = new ArrayList<>();
        Random random = new Random();
        for (int i = 0; i < this.size; i++) {
            // lista rozwiązań, z której wybierzemy najlepsze
            List<Solution> solutionList = new ArrayList<>();
            for (int j = 0; j < n; j++) {
                int index = random.nextInt(this.solutions.size());
                solutionList.add(this.solutions.get(index));
            }
            // wybieramy najlepsze rozwiązanie z grupy, które przejdzie
            // do nowej populacji
            Solution best = solutionList.get(0);
            for (int j = 1; j < n; j++) {
                if(solutionList.get(j).getEvaluation() > best.getEvaluation()) {
                    best = solutionList.get(j);
                }
            }
            // dodajemy je do nowej populacji
            newPopulation.add(best.clone());
        }
        this.solutions = newPopulation;
    }

    private void recombination() {
        // paruje osobniki i je krzyżuje
        List<Solution> parents = new ArrayList<>(this.solutions);
        Collections.shuffle(parents);
        while (! parents.isEmpty()) {
            Solution mom = parents.get(0);
            parents.remove(0);
            if(parents.isEmpty()) {
                break;
            }
            Solution dad = parents.get(0);
            parents.remove(0);
            cross(mom, dad);
        }
    }

    private void cross(Solution solution1, Solution solution2) {
        Random random = new Random();
        int solution1Length = solution1.getWord().length();
        int solution2Length = solution2.getWord().length();
        int index1;
        if(solution1Length > 1) {
            index1 = random.nextInt(solution1Length - 1) + 1;  // losowanie od 1 do długości
        } else {
            index1 = 1;
        }
        int index2;
        if(solution2Length > 1) {
            index2 = random.nextInt(solution2Length - 1) + 1;
        } else {
            index2 = 1;
        }
        String prefix1 = solution1.getPrefix(index1);
        String suffix1 = solution1.getSuffix(index1);
        String prefix2 = solution2.getPrefix(index2);
        String suffix2 = solution2.getSuffix(index2);

        String word1 = prefix1 + suffix2;
        String word2 = prefix2 + suffix1;
        Solution child1 = new Solution(word1, this.alphabet);
        Solution child2 = new Solution(word2, this.alphabet);
        this.solutions.add(child1);
        this.solutions.add(child2);
    }

    private void mutate() {
        for (Solution solution :
                this.solutions) {
            String newWord = solution.mutateWithProbability(this.mutationProbability);
        }
    }

    private void evaluateSolutions() {
        for (Solution solution :
                this.solutions) {
            int eval = evaluate(solution.getWord());
            solution.setEvaluation(eval);
        }
    }

    private String betterSolution(String solution1, String solution2) {
        if(evaluate(solution1) > evaluate(solution2)) {
            return solution1;
        }
        return solution2;
    }

    private void createInitialPopulation(List<String> solutions) {
        int n = solutions.size();
        Random random = new Random();
        for (int i = 0; i < this.size; i++) {
            int index = random.nextInt(n);
            String word = solutions.get(index);
            Solution solution = new Solution(word, this.alphabet);
            this.solutions.add(solution);
            this.bestSolution = betterSolution(this.bestSolution, word);
        }
    }

    private int getLetterValue(Character letter) {
        return this.letterValue.get(letter);
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        for (Solution solution :
                this.solutions) {
            stringBuilder.append(solution.toString()).append("\n");
        }
        return stringBuilder.toString();
    }
}
