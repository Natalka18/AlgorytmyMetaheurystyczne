import static java.lang.Math.pow;

public class FunctionH implements MinimizationProblem {
    @Override
    public double functionValue(double[] x) {
        if(inDomain(x)) {
            double squareSum = x[0] * x[0] + x[1] * x[1] + x[2] * x[2] + x[3] * x[3];
            return pow(squareSum - 4, 0.25) + 0.125 * squareSum
                    + 0.25 * (x[0] + x[1] + x[2] + x[3]) + 0.5;
        } else {
            return 0.0;
        }
    }

    @Override
    public double[] derivativeValue(double[] x) {
        if(inDerivativeDomain(x)) {
            return new double[]{partialDerivative(x, 0), partialDerivative(x, 1),
                    partialDerivative(x, 2), partialDerivative(x, 3)};
        } else {
            return null;
        }
    }

    @Override
    public boolean inDomain(double[] x) {
        double squareSum = x[0]*x[0] + x[1]*x[1] + x[2]*x[2] + x[3]*x[3];
        return squareSum >= 4;
    }

    @Override
    public boolean inDerivativeDomain(double[] x) {
        double squareSum = x[0]*x[0] + x[1]*x[1] + x[2]*x[2] + x[3]*x[3];
        return squareSum > 4;
    }

    // wzór na pochodną po x[i] (po każdej współrzędnej wygląda tak samo)
    private double partialDerivative(double[] x, int i) {
        double squareSum = x[0]*x[0] + x[1]*x[1] + x[2]*x[2] + x[3]*x[3];
        return x[i] / (2 * pow(squareSum - 4, 0.75)) + 0.25 * x[i] + 0.25;
    }
}
