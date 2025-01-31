import java.util.ArrayList;
import java.util.List;
import java.util.Random;

class Solution {
    private String word;
    private int evaluation;
    private List<Character> alphabet;

    Solution(String word, List<Character> alphabet) {
        this.word = word;
        this.evaluation = 0;
        this.alphabet = alphabet;
    }

    String mutateWithProbability(double probability) {
        Random random = new Random();
        if(random.nextDouble() < probability) {
            mutate();
        }
        // zwraca słowo po mutacji
        return this.word;
    }

    private void mutate() {
        int wordLength = this.word.length();
        Random random = new Random();
        // lista liter, które nie zostały użyte w słowie
        List<Character> availableLetters = new ArrayList<>(this.alphabet);
        // usunięcie użytych liter
        for (int i = 0; i < this.word.length(); i++) {
            Character letter = this.word.charAt(i);
            availableLetters.remove(letter);
        }
        int numberOfAvailableLetters = availableLetters.size();

        if(numberOfAvailableLetters > 0) {
            // dodajemy literę w losowym miejscu lub zamieniamy losową literę na inną
            int mutation = random.nextInt(10);
            if(mutation < 7) {  // dodanie litery (70%)
                int offset = random.nextInt(wordLength + 1);
                StringBuilder builder = new StringBuilder(this.word);
                // dostawiamy jedną literę z prawdopodobieństwem 0.7,
                // dwie z prawdopodobieństwem 0.3
                double r = random.nextDouble();
                if(r < 0.3 && numberOfAvailableLetters > 1) {
                    // dwie litery
                    int letterIndex1 = random.nextInt(numberOfAvailableLetters);
                    int letterIndex2 = random.nextInt(numberOfAvailableLetters);
                    char[] s = {availableLetters.get(letterIndex1),
                            availableLetters.get(letterIndex2)};
                    builder.insert(offset, s);
                } else {
                    // jedna litera
                    int letterIndex = random.nextInt(numberOfAvailableLetters);
                    builder.insert(offset, availableLetters.get(letterIndex));
                }

                this.word = builder.toString();

            } else {  // wymiana litery (30%)
                int letterIndex = random.nextInt(numberOfAvailableLetters);
                int index = random.nextInt(wordLength);
                StringBuilder builder = new StringBuilder(this.word);
                builder.replace(index, index + 1,
                        String.valueOf(availableLetters.get(letterIndex)));
                this.word = builder.toString();
            }
        } else {
            // robimy transpozycję
                int index1 = random.nextInt(wordLength);
                int index2 = random.nextInt(wordLength);
                StringBuilder newWord = new StringBuilder(this.word);
                char letter1 = this.word.charAt(index1);
                char letter2 = this.word.charAt(index2);
                newWord.setCharAt(index1, letter2);
                newWord.setCharAt(index2, letter1);
                this.word = newWord.toString();
        }
    }

    void setEvaluation(int evaluation) {
        this.evaluation = evaluation;
    }

    int getEvaluation() {
        return evaluation;
    }

    String getWord() {
        return word;
    }

    void setWord(String word) {
        this.word = word;
    }

    // zwraca część słowa this.word od 0 do index - 1
    String getPrefix(int index) {
        return this.word.substring(0, index);
    }

    String getInfix(int index1, int index2) {
        return this.word.substring(index1, index2);
    }
    // zwraca część słowa this.word od index do końca
    String getSuffix(int index) {
        return this.word.substring(index);
    }

    public String toString() {
        return word + " " + evaluation;
    }

    Solution copy() {
        return new Solution(this.word, this.alphabet);
    }
}
