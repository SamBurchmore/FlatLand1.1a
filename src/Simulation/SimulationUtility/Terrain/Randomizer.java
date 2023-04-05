package Simulation.SimulationUtility.Terrain;

import java.util.Random;

public class Randomizer extends Random{

    public Randomizer() {
    }

    public boolean getDecision(int chance) {
        return super.nextInt(10000) < chance;
    }

    public int getPosNegRandom(int bound) {
        if (super.nextInt(2) == 0) {
            return -super.nextInt(bound);
        }
        return super.nextInt(bound);
    }

    public Direction getDirection(int dxUpperBound, int dyUpperBound) {
        return new Direction(getPosNegRandom(dxUpperBound), getPosNegRandom(dyUpperBound));
    }

}
