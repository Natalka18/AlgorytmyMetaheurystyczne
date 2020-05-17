import java.util.ArrayList;
import java.util.List;

class Swarm {
    private int size;  // liczba cząstek w roju
    private List<Particle> particles;
    private double[] bestGloballySolution;
    private int dimension;  // długość wektora rozwiązania
    private double[] epsilons;  // do funkcji oceny

    Swarm(int size, int dimension, double[] initialSolution,
          double[] epsilons, double inertia, double localFactor,
          double globalFactor) {
        this.size = size;
        this.dimension = dimension;
        this.bestGloballySolution = initialSolution;
        this.epsilons = epsilons;
        createParticles(inertia, localFactor, globalFactor, initialSolution);
    }

    void start(long startTime, long maxTime) {
        while(System.currentTimeMillis() - startTime < maxTime) {
            for (Particle particle :
                    this.particles) {
                particle.move();
            }
        }
    }

    double evaluate(double[] solution) {
        double result = 0;
        for (int i = 0; i < this.dimension; i++) {
            result += this.epsilons[i] * Math.pow(Math.abs(solution[i]), i + 1);
        }
        return result;
    }

    double[] betterSolution(double[] solution1, double[] solution2) {
        if(evaluate(solution1) < evaluate(solution2)) {
            return solution1;
        }
        return solution2;
    }

    void updateBestGloballySolution(double[] solution) {
        this.bestGloballySolution = betterSolution(this.bestGloballySolution, solution);
    }

    boolean inDomain(double[] solution) {
        for (int i = 0; i < this.dimension; i++) {
            if(solution[i] < -5) {
                return false;
            }
            if(solution[i] > 5) {
                return false;
            }
        }
        return true;
    }

    double[] getBestGloballySolution() {
        return this.bestGloballySolution;
    }

    private void createParticles(double inertia, double localFactor,
                                 double globalFactor, double[] initialSolution) {
        this.particles = new ArrayList<>();
        for (int i = 0; i < this.size; i++) {
            double[] particlePosition = new double[this.dimension];
            System.arraycopy(initialSolution, 0, particlePosition, 0, this.dimension);
            Particle particle = new Particle(particlePosition, localFactor, globalFactor, inertia, this);
            particles.add(particle);
        }
    }
}
