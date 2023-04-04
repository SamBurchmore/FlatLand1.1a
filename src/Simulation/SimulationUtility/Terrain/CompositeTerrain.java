package Simulation.SimulationUtility.Terrain;

import Simulation.Environment.Environment;
import Simulation.Environment.Location;

public abstract class CompositeTerrain extends Point{
    private Terrain terrain;
    public CompositeTerrain(boolean paintOrClear, Terrain terrain) {
        super(paintOrClear);
        this.terrain = terrain;
    }

    public Terrain getTerrain() {
        return terrain;
    }

    public void setTerrain(Terrain terrain) {
        this.terrain = terrain;
    }

}
