import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

class Solution {
    private List<Character> sequence;
    private int evaluation;

    Solution(List<Character> sequence) {
        this.sequence = sequence;
        this.evaluation = -1;
    }

    void mutateWithProbability(double probability) {
        Random random = new Random();
        if(random.nextDouble() < probability) {
            mutate();
        }
    }

    private void mutate() {
        // losujemy sposób mutacji
        // Jeśli sekwencja jest dłuższa niż lub równa dwa, to losujemy jedną z opcji:
        // - transpozycja,
        // - odwrócenie podlisty,
        // - usunięcie kroku,
        // - dodanie kroku,
        // - zastąpienie kroku innym.
        // Jeśli sekwencja jest krótsza, to dodajemy krok.
        Random random = new Random();
        int mutation = random.nextInt(100);
        if(getLength() >= 2) {
            if(mutation < 10) {  // 10%
                transpose();
            } else if(mutation < 20) {  // 10%
                reverse();
            } else if(mutation < 60) {  // 40%
                removeStep();
            } else if(mutation < 80) {  // 20%
                addStep();
            } else {  // 20%
                replace();
            }
        } else {
            if(mutation < 50) {
                addStep();
            } else {
                replace();
            }
        }
    }

    // transpozycja dwóch losowych kroków
    private void transpose() {
        Random random = new Random();
        int index1 = random.nextInt(getLength());
        int index2 = random.nextInt(getLength());
        char c1 = this.sequence.get(index1);
        char c2 = this.sequence.get(index2);

        List<Character> s = new ArrayList<>(this.sequence);

        s.set(index1, c2);
        s.set(index2, c1);

        this.sequence = s;
    }

    // dodanie losowego kroku w losowym miejscu
    private void addStep() {
        Random random = new Random();
        int index = random.nextInt(getLength() + 1);
        List<Character> steps = new ArrayList<>();
        steps.add(Labyrinth.RIGHT);
        steps.add(Labyrinth.DOWN);
        steps.add(Labyrinth.UP);
        steps.add(Labyrinth.LEFT);
        Collections.shuffle(steps);
        char step = steps.get(0);

        List<Character> s = new ArrayList<>(this.sequence);

        s.add(index, step);

        this.sequence = s;
    }

    private void replace() {
        Random random = new Random();
        int index = random.nextInt(getLength());
        List<Character> steps = new ArrayList<>();
        steps.add(Labyrinth.RIGHT);
        steps.add(Labyrinth.DOWN);
        steps.add(Labyrinth.UP);
        steps.add(Labyrinth.LEFT);
        Collections.shuffle(steps);
        char step = steps.get(0);
        List<Character> s = new ArrayList<>(this.sequence);
        s.set(index, step);
        this.sequence = s;
    }

    // odwrócenie kolejności wybranej losowo podlisty
    private void reverse() {
        Random random = new Random();
        int index1 = random.nextInt(getLength());
        int index2 = random.nextInt(getLength());
        if(index1 > index2) {
            int temp = index1;
            index1 = index2;
            index2 = temp;
        }
        List<Character> infix = getInfix(index1, index2);
        Collections.reverse(infix);
        List<Character> prefix = getPrefix(index1);
        List<Character> suffix = getSuffix(index2 + 1);
        List<Character> newSequence = new ArrayList<>(prefix);
        newSequence.addAll(infix);
        newSequence.addAll(suffix);
        this.sequence = newSequence;
    }

    // usunięcie losowego kroku
    private void removeStep() {
        Random random = new Random();
        int index = random.nextInt(getLength());

        List<Character> s = new ArrayList<>(this.sequence);

        s.remove(index);

        this.sequence = s;
    }

    boolean isAllowed() {
        return this.evaluation != -1;
    }

    Solution copy() {
        return new Solution(new ArrayList<>(sequence));
    }

    // zwraca część słowa od index1 do index2 (włącznie)
    // index w przedziale [0, length - 1]
    private List<Character> getInfix(int index1, int index2) {
        return this.sequence.subList(index1, index2 + 1);
    }

    // zwraca część ciągu od zera do index - 1
    // zwraca pierwsze index znaków
    // index jest w przedziale [0, length]
    List<Character> getPrefix(int index) {
        return this.sequence.subList(0, index);
    }

    // zwraca część ciągu od index do końca
    List<Character> getSuffix(int index) {
        int length = this.sequence.size();
        return this.sequence.subList(index, length);
    }

    List<Character> getSequence() {
        return sequence;
    }

    void setSequence(List<Character> sequence) {
        this.sequence = sequence;
    }

    int getLength() {
        return this.sequence.size();
    }

    void setEvaluation(int evaluation) {
        this.evaluation = evaluation;
    }

    int getEvaluation() {
        return evaluation;
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        for (Character character : this.sequence) {
            stringBuilder.append(character);
        }
        return stringBuilder.toString();
    }
}
