public class FunctionG implements MinimizationProblem {
    @Override
    public double functionValue(double[] x) {
        double squareSum = x[0] * x[0] + x[1] * x[1] + x[2] * x[2] + x[3] * x[3];
        return 1 + (1.0/40000.0) * squareSum - Math.cos(x[0]) *
                Math.cos(x[1]/Math.sqrt(2)) * Math.cos(x[2]/Math.sqrt(3)) * Math.cos(x[3]/2);
    }

    @Override
    public double[] derivativeValue(double[] x) {
        double derivative0 = (1.0/20000.0) * x[0] + Math.sin(x[0]) *
                Math.cos(x[1]/Math.sqrt(2)) * Math.cos(x[2]/Math.sqrt(3)) * Math.cos(x[3]/2);
        double derivative1 = (1.0/20000.0) * x[1] + (1/Math.sqrt(2)) *
                Math.sin(x[1]/Math.sqrt(2)) * Math.cos(x[0]) * Math.cos(x[2]/Math.sqrt(3)) * Math.cos(x[3]/2);
        double derivative2 = (1.0/20000.0) * x[2] + (1/Math.sqrt(3)) *
                Math.sin(x[2]/Math.sqrt(3)) * Math.cos(x[0]) * Math.cos(x[1]/Math.sqrt(2)) * Math.cos(x[3]/2);
        double derivative3 = (1.0/20000.0) * x[3] + 0.5 * Math.sin(x[3]/2) * Math.cos(x[0]) *
                Math.cos(x[1]/Math.sqrt(2)) * Math.cos(x[2]/Math.sqrt(3));

        return new double[]{derivative0, derivative1, derivative2, derivative3};
    }

    @Override
    public boolean inDomain(double[] x) {
        return true;
    }

    @Override
    public boolean inDerivativeDomain(double[] x) {
        return true;
    }
}
