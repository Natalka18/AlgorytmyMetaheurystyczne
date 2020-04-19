import java.util.Stack;

public abstract class SolutionAbstract<T> implements Solution<T> {
    protected T value;
    protected Stack<Solution<T>> neighbours;

    SolutionAbstract(T value) {
        this.value = value;
        initializeNeighbours();
    }

    abstract void initializeNeighbours();

    public Solution<T> nextNeighbour() {
        return neighbours.pop();
    }
}
