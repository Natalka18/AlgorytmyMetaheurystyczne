import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class SalomonSolution extends SolutionAbstract<List<Double>> {
    SalomonSolution(List<Double> value) {
        super(value);
    }

    @Override
    public double evaluate() {
        // wartość oceny jest równa wartości funkcji salomona
        double module = module();
        return 1 - Math.cos(2 * Math.PI * module) + 0.1 * module;
    }

    @Override
    public Solution<List<Double>> nextNeighbour() {
        List<Double> neighbourValue = new ArrayList<>();
        Random random = new Random();
        for(Double x : super.value) {
            double min = 0;
            double max = x;
            double d = (max - min) / 6;
            double N = random.nextGaussian() * d;
            neighbourValue.add(x + N);
        }
        return new SalomonSolution(neighbourValue);
    }

    private double module() {
        double sum = 0;
        for(int i = 0; i < 4; i++) {
            double x = super.value.get(i);
            sum += x * x;
        }
        return Math.sqrt(sum);
    }

    @Override
    public Solution<List<Double>> copy() {
        return new SalomonSolution(super.value);
    }

    @Override
    public String toString() {
        StringBuilder s = new StringBuilder();
        for(Double x : super.value) {
            s.append(x);
            s.append(" ");
        }
        double eval = evaluate();
        s.append(eval);
        return s.toString();
    }
}
