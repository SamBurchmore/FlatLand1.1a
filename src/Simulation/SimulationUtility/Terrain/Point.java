package Simulation.SimulationUtility.Terrain;

import Simulation.Environment.Environment;
import Simulation.Environment.Location;

public class Point implements Terrain{

    private boolean paintOrClear;

    public Point(boolean paintOrClear) {
        this.paintOrClear = paintOrClear;
    }

    @Override
    public Environment paint(Environment environment, Location location) {
        environment.getTile(location).setTerrain(paintOrClear);
        return environment;
    }


    @Override
    public void setPaintOrClear(boolean paintOrClear) {
        this.paintOrClear = paintOrClear;
    }

    @Override
    public boolean isTerrain() {
        return paintOrClear;
    }

    @Override
    public void setSize(int size) {

    }

    @Override
    public boolean getPaintOrClear() {
        return paintOrClear;
    }

    @Override
    public Terrain copy() {
        return new Point(paintOrClear);
    }
}
