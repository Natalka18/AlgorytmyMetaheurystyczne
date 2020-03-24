public interface MinimizationProblem {
    double functionValue(double[] x);
    double[] derivativeValue(double[] x);
    boolean inDomain(double[] x);  // sprawdza, czy dany punkt należy do dziedziny funkcji
    boolean inDerivativeDomain(double[] x);
}
