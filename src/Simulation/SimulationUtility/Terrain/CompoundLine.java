package Simulation.SimulationUtility.Terrain;

import Simulation.Environment.Environment;
import Simulation.Environment.Location;

public class CompoundLine extends BaseLine {

    public CompoundLine(Line lineComponent, int size, Direction direction) {
        super(lineComponent, size, direction);
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

    @Override
    public Line getTerrainComponent() {
        return (Line) super.getTerrainComponent();
    }

    @Override
    public void setTerrainComponent(Line line) {
        super.setTerrainComponent(line);
    }
}
