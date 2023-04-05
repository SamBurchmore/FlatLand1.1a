package Simulation.SimulationUtility.Terrain;

import Simulation.Environment.Environment;
import Simulation.Environment.Location;

public class ClusterCircle extends Circle {

    private final Randomizer randomizer = new Randomizer();
    private int clusterDensity;

    public ClusterCircle(Terrain terrainComponent, int size, int clusterDensity) {
        super(terrainComponent, size);
        this.clusterDensity = clusterDensity;
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
                if (randomizer.getDecision(clusterDensity)) {
                    if (
                            terrainFrame.getEnvironment().isLocationOnGrid(new Location(x1, y1)) && ((x1 - seedX) * (x1 - seedX) + (y1 - seedY) * (y1 - seedY)) <= size * size
                    ) {
                        location.setX(x1);
                        location.setY(y1);
                        terrainFrame = super.getTerrainComponent().paint(terrainFrame, location, paintFlag);
                    }
                }
            }
        }
        terrainFrame.setPointer(location);
        return terrainFrame;
    }

    public int getClusterDensity() {
        return clusterDensity;
    }

    public void setClusterDensity(int clusterDensity) {
        this.clusterDensity = clusterDensity;
    }

    @Override
    public Terrain copy() {
        return new ClusterCircle(super.getTerrainComponent(), super.getSize(), getClusterDensity());
    }
}
