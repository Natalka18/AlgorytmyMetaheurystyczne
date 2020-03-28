import java.util.Random;

class MinimizationProblemSolver {
    private long maxTime;  // w milisekundach
    private double step;
    private double[] startingPoint;
    private MinimizationProblem function;
    private long startTime;

    MinimizationProblemSolver(long max_time, MinimizationProblem function,
                              double step, double[] start, long startTime) {
        this.maxTime = max_time;
        this.function = function;
        this.startingPoint = start;
        this.step = step;
        this.startTime = startTime;
    }

    // zwraca punkt, w którym znajduje się znalezione minimum
    // w przypadku wystąpienia błędu zwraca null
    double[] solve() {
        double[] currentPoint = new double[startingPoint.length];
        System.arraycopy(startingPoint, 0, currentPoint, 0, startingPoint.length);
        double[] currentMinPoint = new double[startingPoint.length];
        System.arraycopy(currentPoint, 0, currentMinPoint, 0, startingPoint.length);

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
                    //return currentMinPoint;
                    currentPoint = generateStartingPoint();
                }

                if(!function.inDomain(currentPoint) || !function.inDerivativeDomain(currentPoint)) {
                    //return currentMinPoint;
                    currentPoint = generateStartingPoint();
                }

                double currentFunctionValue = function.functionValue(currentPoint);

                if(currentFunctionValue < currentMinValue) {
                    currentMinValue = currentFunctionValue;
                    System.arraycopy(currentPoint, 0, currentMinPoint, 0, startingPoint.length);
                }
            }
            return currentMinPoint;
    }

    // generuje losowo nowy punkt startowy
    private double[] generateStartingPoint() {
        int length = startingPoint.length;
        double[] newPoint = new double[length];
        do {
            for(int i = 0; i < length; i++) {
                newPoint[i] = randomDouble();
            }
        } while ((!function.inDomain(newPoint) ||
                !function.inDerivativeDomain(newPoint)) &&
                System.currentTimeMillis() - startTime < maxTime);
        return newPoint;
    }

    // generuje losową liczbę rzeczywistą
    private double randomDouble() {
        // losowanie znaku
        Random random = new Random();
        int sign = (random.nextBoolean()) ? -1 : 1;
        // losowanie liczby rzeczywistej z przedziały [0,1),
        // a następnie mnożenie jej przez losową liczbę całkowitą
        // z przedziału [0,10}
        return sign * (random.nextDouble() * random.nextInt(10));
    }
}
