public abstract class SolutionAbstract<T> implements Solution<T> {
    T value;

    SolutionAbstract(T value) {
        this.value = value;
    }

    @Override
    public boolean isNull() {
        return value == null;
    }
}
