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
        for (String s :
                this.words) {
            if(s.equalsIgnoreCase(word)) {
                return true;
            }
        }
        return false;
    }
}
