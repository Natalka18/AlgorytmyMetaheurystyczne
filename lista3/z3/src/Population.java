import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

class Population {
    private int size;
    private Labyrinth labyrinth;
    private List<Solution> solutions;
    private double mutationProbability;
    private Solution bestSolution;

    Population(int size, List<List<Character>> initialSolutions,
               Labyrinth labyrinth, double mutationProbability) {
        this.size = size;
        this.labyrinth = labyrinth;
        this.mutationProbability = mutationProbability;
        createInitialPopulation(initialSolutions);
    }

    void start(long startTime, int maxTime) {
        while(System.currentTimeMillis() - startTime < maxTime) {
            recombination();
            mutate();
            evaluateAll();
            select1();
            System.out.println(solutions);
        }
    }

    Solution getBestSolution() {
        // szukamy najlepszego osobnika w obecnej populacji,
        // porównując każdego z nich z najlepszym rozwiązaniem
        // wybranym z pierwszego pokolenia
        for (Solution solution :
                this.solutions) {
            this.bestSolution = betterSolution(this.bestSolution, solution);
        }
        return this.bestSolution;
    }
    
    private void evaluateAll() {
        for (Solution solution :
                this.solutions) {
            int eval = evaluate(solution);
            solution.setEvaluation(eval);
            if(solution.isAllowed() && solution.getEvaluation() < solution.getLength()) {
                // usunięcie niepotrzebnej części sekwencji
                solution.setSequence(solution.getPrefix(eval + 1));
            }
        }
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

    private void mutate() {
        for (Solution solution :
                this.solutions) {
            solution.mutateWithProbability(this.mutationProbability);
        }
    }

    private void cross(Solution solution1, Solution solution2) {
        int length1 = solution1.getLength();
        int length2 = solution2.getLength();
        Random random = new Random();
        int index1;
        int index2;
        if(length1 > 1) {
            index1 = random.nextInt(length1 - 1) + 1;  // [1, length - 1]
        } else {
            index1 = 1;
        }
        if(length2 > 1) {
            index2 = random.nextInt(length2 - 1) + 1;
        } else {
            index2 = 1;
        }
        List<Character> prefix1 = solution1.getPrefix(index1);
        List<Character> suffix1 = solution1.getSuffix(index1);
        List<Character> prefix2 = solution2.getPrefix(index2);
        List<Character> suffix2 = solution2.getSuffix(index2);

        List<Character> childSequence1 = new ArrayList<>(prefix1);
        childSequence1.addAll(suffix2);
        List<Character> childSequence2 = new ArrayList<>(prefix2);
        childSequence2.addAll(suffix1);
        Solution child1 = new Solution(childSequence1);
        Solution child2 = new Solution(childSequence2);
        this.solutions.add(child1);
        this.solutions.add(child2);
    }

    // selekcja losowa, usuwane są rozwiązania niedopuszczalne
    private void select1() {
        List<Solution> nextPopulation = new ArrayList<>();
        Random random = new Random();
        Solution best = getBestSolution();
        nextPopulation.add(best.copy());
        for (int i = 0; i < this.size - 1; i++) {
            int currentSize = this.solutions.size();
            int index = random.nextInt(currentSize);
            Solution s = this.solutions.get(index);
            nextPopulation.add(s.copy());
        }
        this.solutions = nextPopulation;
    }

    private int evaluate(Solution solution) {
        // -1, jeśli ta sekwencja jest niedopuszczalna (agent wchodzi w ścianę),
        // numer kroku, w którym agent dochodzi do celu, jeśli dochodzi (numerujemy od 0),
        // int_max, jeśli sekwencja jest dopuszczalna, ale nie prowadzi do celu
        return labyrinth.checkSequence(solution.getSequence());
    }

    private Solution betterSolution(Solution solution1, Solution solution2) {
        if(! solution1.isAllowed()) {
            return solution2;
        }
        if(! solution2.isAllowed()) {
            return solution1;
        }
        if(evaluate(solution1) < evaluate(solution2)) {
            return solution1;
        }
        return solution2;
    }

    private void createInitialPopulation(List<List<Character>> initialSolutions) {
        this.solutions = new ArrayList<>();
        int n = initialSolutions.size();
        Random random = new Random();
        if(! initialSolutions.isEmpty()) {
            int index = random.nextInt(n);
            List<Character> sequence = initialSolutions.get(index);
            Solution solution = new Solution(sequence);
            this.solutions.add(solution);
            this.bestSolution = solution;
        }
        for (int i = 1; i < this.size; i++) {
            int index = random.nextInt(n);
            List<Character> sequence = initialSolutions.get(index);
            Solution solution = new Solution(sequence);
            this.solutions.add(solution);
            this.bestSolution = betterSolution(this.bestSolution, solution);
        }
    }
}
