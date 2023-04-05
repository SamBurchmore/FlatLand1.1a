package Simulation.SimulationUtility.Terrain;

import Simulation.Environment.Environment;
import Simulation.Environment.Location;

public class Point implements Terrain {

    public Point() {
    }

    @Override
    public TerrainFrame paint(TerrainFrame terrainFrame, Location location, boolean paintFlag) {
        terrainFrame.getEnvironment().getTile(location).setTerrain(paintFlag);
        terrainFrame.setPointer(location);
        return terrainFrame;
    }



    @Override
    public Terrain copy() {
        return new Point();
    }
}
