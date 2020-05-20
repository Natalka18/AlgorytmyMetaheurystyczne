import java.util.ArrayList;
import java.util.List;

class Dictionary {
    private List<String> words;

    Dictionary() {
        this.words = new ArrayList<>();
    }

    void add(String word) {
        words.add(word);
    }

    boolean contains(String word) {
        return this.words.contains(word);
    }
}
