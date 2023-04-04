package Simulation.SimulationUtility.Terrain;

import Simulation.Environment.Environment;
import Simulation.Environment.Location;

public interface Terrain {

    Environment paint(Environment environment, Location location);

    void setPaintOrClear(boolean paintOrClear);

    boolean isTerrain();

    void setSize(int size);

    boolean getPaintOrClear();

    Terrain copy();

}
