package Simulation.SimulationUtility.Terrain;

import Simulation.Environment.Environment;
import Simulation.Environment.Location;

public class BasicLine extends BaseLine {


    public BasicLine(Terrain terrainComponent, int size, Direction direction) {
        super(terrainComponent, size, direction);
    }

    @Override
    public TerrainFrame paint(TerrainFrame terrainFrame, Location location, boolean paintFlag) {
        for (int i = 0; i < super.getSize(); i++) {
            if (terrainFrame.getEnvironment().isLocationOnGrid(location)) {
                terrainFrame = super.getTerrainComponent().paint(terrainFrame, location, paintFlag);
                location.setX(location.getX() + super.getDirection().getDx());
                location.setY(location.getY() + super.getDirection().getDy());
            }
            else {
                break;
            }
        }
        terrainFrame.setPointer(location);
        return terrainFrame;
    }

    @Override
    public Terrain copy() {
        return new BasicLine(super.getTerrainComponent(), super.getSize(), super.getDirection());
    }
}
