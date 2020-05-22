import java.util.Hashtable;

class Dictionary {
    private Node root;

    Dictionary() {
        root = new Node();
    }

    class Node {
        private Hashtable<Character, Node> children;
        private boolean isEndOfWord;

        Node() {
            isEndOfWord = false;
            children = new Hashtable<>();
        }

        boolean isEndOfWord() {
            return isEndOfWord;
        }

        boolean containsLetter(char letter) {
            return children.get(letter) != null;
        }

        void addLetter(char letter) {
            if(! containsLetter(letter)) {
                children.put(letter, new Node());
            }
        }

        // zwraca Node, na który wskazuje litera letter.
        // Jeśli nie ma tekiej litery, zwraca null.
        Node nextNode(char letter) {
            return children.get(letter);
        }

        void markAsLeaf() {
            isEndOfWord = true;
        }
    }

    void add(String word) {
        int wordLength = word.length();
        Node currentNode = this.root;
        for (int i = 0; i < wordLength; i++) {
            char letter = word.charAt(i);
            if(! currentNode.containsLetter(letter)) {
                currentNode.addLetter(letter);
            }
            currentNode = currentNode.nextNode(letter);
        }
        currentNode.markAsLeaf();
    }

    boolean contains(String word) {
        int wordLength = word.length();
        Node currentNode = this.root;
        for (int i = 0; i < wordLength; i++) {
            char letter = word.charAt(i);
            if(! currentNode.containsLetter(letter)) {
                return false;
            }
            currentNode = currentNode.nextNode(letter);
        }
        return (currentNode != null && currentNode.isEndOfWord());
    }
}
