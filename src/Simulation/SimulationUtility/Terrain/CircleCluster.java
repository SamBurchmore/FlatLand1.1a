package Simulation.SimulationUtility.Terrain;

import Simulation.Environment.Environment;
import Simulation.Environment.Location;

import java.util.Random;

public class CircleCluster extends Circle {
    Random random = new Random();
    private int density;
    public CircleCluster(boolean paintOrClear, Terrain terrain, int size, int density) {
        super(paintOrClear, terrain, size);
        this.density = density;
    }

    @Override
    public Environment paint(Environment environment, Location location) {
        int size = super.getSize();
        int seedX = location.getX();
        int seedY = location.getY();
        int x1;
        int y1;
        for (int x = -size; x <= size; x++) {
            for (int y = -size; y <= size; y++) {
                x1 = seedX + x;
                y1 = seedY + y;
                if (
                        environment.isLocationOnGrid(new Location(x1, y1))
                        && ((x1 - seedX) * (x1 - seedX) + (y1 - seedY) * (y1 - seedY)) <= size * size
                )
                {
                    if (random.nextInt(10000) < density) {
                        environment = super.getTerrain().paint(environment, new Location(x1, y1));
                    }
                }
            }
        }
        return environment;
    }

}
