package Simulation.SimulationUtility.Terrain;

import Simulation.Environment.Environment;
import Simulation.Environment.Location;

public class Circle extends CompositeTerrain {

    private int size;

    public Circle(boolean paintOrClear, Terrain terrain, int size) {
        super(paintOrClear, terrain);
        this.size = size;
    }

    @Override
    public Environment paint(Environment environment, Location location) {
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
                ) {
                    environment = super.getTerrain().paint(environment, new Location(x1, y1));
                }
            }
        }
        return environment;
    }

    @Override
    public void setSize(int size) {
        this.size = size;
    }

    public int getSize() {
        return size;
    }
}
