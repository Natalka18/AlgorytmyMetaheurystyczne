import java.util.Arrays;

class MinimizationProblemSolver {
    private long maxTime;  // w milisekundach
    private double step;
    private double[] startingPoint;
    private MinimizationProblem function;

    MinimizationProblemSolver(long max_time, MinimizationProblem function,
                              double step, double[] start) {
        this.maxTime = max_time;
        this.function = function;
        this.startingPoint = start;
        this.step = step;
    }

    // zwraca punkt, w którym znajduje się znalezione minimum
    // w przypadku wystąpienia błędu zwraca null
    double[] solve() {
        double[] currentPoint = new double[startingPoint.length];
        System.arraycopy(startingPoint, 0, currentPoint, 0, startingPoint.length);
        double[] currentMinPoint = new double[startingPoint.length];
        System.arraycopy(currentPoint, 0, currentMinPoint, 0, startingPoint.length);
        long startTime = System.currentTimeMillis();

            double currentMinValue = function.functionValue(startingPoint);
            while(System.currentTimeMillis() - startTime < maxTime) {
                double[] gradient = function.derivativeValue(currentPoint);
                boolean zeroGradient = true;

                // x_t+1 = x_t - step * gradient(f)(x_t)
                for(int i = 0; i < startingPoint.length; i++) {
                    if (gradient[i] != 0) {
                        zeroGradient = false;
                        currentPoint[i] = currentPoint[i] - step * gradient[i];
                    }
                }

                if(zeroGradient) {
                    return currentMinPoint;
                }

                if(!function.inDomain(currentPoint) || !function.inDerivativeDomain(currentPoint)) {
                    return currentMinPoint;
                }

                double currentFunctionValue = function.functionValue(currentPoint);

                if(currentFunctionValue < currentMinValue) {
                    currentMinValue = currentFunctionValue;
                    System.arraycopy(currentPoint, 0, currentMinPoint, 0, startingPoint.length);
                }
            }
            return currentMinPoint;
    }
}
