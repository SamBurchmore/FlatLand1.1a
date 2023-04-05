package Simulation.SimulationUtility.Terrain;

import Simulation.Environment.Environment;
import Simulation.Environment.Location;

public interface Terrain {

    TerrainFrame paint(TerrainFrame terrainFrame, Location location, boolean paintFlag);

    Terrain copy();

}
