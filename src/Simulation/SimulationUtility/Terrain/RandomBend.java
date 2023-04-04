package Simulation.SimulationUtility.Terrain;

import java.util.Random;

public class RandomBend {
    Random random = new Random();
    private int variance;

    public RandomBend(Random random, int variance) {
        this.random = random;
        this.variance = variance;
    }

    public Direction getDirection() {
        Direction direction = new Direction(random.nextInt(variance), random.nextInt(variance));
        return direction;
    }

}
