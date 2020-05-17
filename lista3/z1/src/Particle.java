import java.util.Random;

class Particle {
    private int dimension;  // długość wektora rozwiązania
    private double[] position;
    private double[] velocity;
    private double inertia;  // współczynnik bezwładności
    // współczynnik istotności najlepszego lokalnego rozwiązania
    private double localFactor;
    // współczynnik istotności najlepszego globalnego rozwiązania
    private double globalFactor;
    private double[] bestLocallySolution;
    private Swarm swarm;

    Particle(double[] position, double localFactor,
                    double globalFactor, double inertia, Swarm swarm) {
        this.position = position;
        this.localFactor = localFactor;
        this.globalFactor = globalFactor;
        this.bestLocallySolution = position;
        this.inertia = inertia;
        this.dimension = this.position.length;
        this.swarm = swarm;
        randomVelocity();
    }

    void move() {
        updatePosition();
        // jeśli kolejna pozycja cząstki jest poza dziedziną,
        // to cząstka nie zmienia położenia i losowana jest
        // nowa prędkość cząstki
        if(!swarm.inDomain(this.position)) {
            setPreviousPosition();
            randomVelocity();
        }

        this.bestLocallySolution = swarm.betterSolution(this.bestLocallySolution, this.position);
        this.swarm.updateBestGloballySolution(this.position);

        updateVelocity();
    }

    private void updatePosition() {
        for (int i = 0; i < this.dimension; i++) {
            this.position[i] += this.velocity[i];
        }
    }

    private void setPreviousPosition() {
        for (int i = 0; i < this.dimension; i++) {
            this.position[i] -= this.velocity[i];
        }
    }

    private void updateVelocity() {
        double localF = randomFactor(this.localFactor);
        double globalF = randomFactor(this.globalFactor);

        double[] firstComponent = multiply(this.inertia, this.velocity);
        double[] secondComponent = multiply(localF,
                subtract(this.bestLocallySolution, this.position));
        double[] thirdComponent = multiply(globalF,
                subtract(this.swarm.getBestGloballySolution(), this.position));

        this.velocity = add(add(firstComponent, secondComponent), thirdComponent);
    }

    private double randomFactor(double maxFactor) {
        Random random = new Random();
        return random.nextDouble() * maxFactor;
    }

    private double[] multiply(double factor, double[] vector) {
        double[] result = new double[vector.length];
        for (int i = 0; i < vector.length; i++) {
            result[i] = vector[i] * factor;
        }
        return result;
    }

    private double[] subtract(double[] vector1, double[] vector2) {
        double[] result = new double[vector1.length];
        for (int i = 0; i < vector1.length; i++) {
            result[i] = vector1[i] - vector2[i];
        }
        return result;
    }

    private double[] add(double[] vector1, double[] vector2) {
        double[] result = new double[vector1.length];
        for (int i = 0; i < vector1.length; i++) {
            result[i] = vector1[i] + vector2[i];
        }
        return result;
    }

    private void randomVelocity() {
        this.velocity = new double[this.dimension];
        Random random = new Random();
        for (int i = 0; i < this.dimension; i++) {
            velocity[i] = random.nextGaussian();
        }
    }
}
