package Simulation.SimulationUtility.Terrain;

import Simulation.Environment.Environment;
import Simulation.Environment.Location;

public class Square extends BaseTerrainShape {

    public Square(Terrain terrainComponent, int size) {
        super(terrainComponent, size);
    }

    @Override
    public TerrainFrame paint(TerrainFrame terrainFrame, Location location, boolean paintFlag) {
        int seedX = location.getX();
        int seedY = location.getY();
        int size = super.getSize();
        int x1;
        int y1;
        for (int x = -size; x <= size; x++) {
            for (int y = -size; y <= size; y++) {
                x1 = seedX + x;
                y1 = seedY + y;
                if (terrainFrame.getEnvironment().isLocationOnGrid(new Location(x1, y1))) {
                    location.setX(x1);
                    location.setY(y1);
                    terrainFrame = super.getTerrainComponent().paint(terrainFrame, location, paintFlag);
                }
            }
        }
        terrainFrame.setPointer(location);
        return terrainFrame;
    }

    @Override
    public Terrain copy() {
        return new Square(super.getTerrainComponent(), super.getSize());
    }
}
