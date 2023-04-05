package Simulation.SimulationUtility.Terrain;

import Simulation.Environment.Location;

public class ChainShape extends BaseTerrainShape {

    public ChainShape(Terrain terrainComponent, int size) {
        super(terrainComponent, size);
    }

    @Override
    public TerrainFrame paint(TerrainFrame terrainFrame, Location location, boolean paintFlag) {
        // Paint initial component
        terrainFrame = getTerrainComponent().paint(terrainFrame, location, paintFlag);
        for (int i = 0; i <= super.getSize(); i++) {
            terrainFrame = getTerrainComponent().paint(terrainFrame, terrainFrame.getPointer(), paintFlag);
        }
        return terrainFrame;
    }

    @Override
    public Terrain copy() {
        return null;
    }
}
