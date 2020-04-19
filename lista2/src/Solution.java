import java.util.List;

public interface Solution<T> {
    double evaluate();  // zwraca wartość funkcji oceny tego rozwiązania
    // zwraca kolejnego sąsiada z listy sąsiadów. Jeśli nie ma już sąsiadów
    // tego rozwiązania, zwraca null.
    Solution<T> nextNeighbour();
    String toString();
    Solution<T> clone();
}
