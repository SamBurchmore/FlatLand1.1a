package Simulation.SimulationUtility.Terrain;

import Simulation.Environment.Location;

import java.util.concurrent.TimeUnit;

public class RandomRadiantLine extends CompoundLine{

    private int depth;
    private int branches;

    public RandomRadiantLine(Line lineComponent, int size, Direction direction, int depth, int branches) {
        super(lineComponent, size, direction);
        this.depth = depth;
        this.branches = branches;
    }

    @Override
    public TerrainFrame paint(TerrainFrame terrainFrame, Location location, boolean paintFlag) {
        for (int i = -branches; i <= branches; i++) {
            for (int j = -branches; j <= branches; j++) {
                if (i != 0 && j != 0) {
                    super.getTerrainComponent().getDirection().setDx(j);
                    super.getTerrainComponent().getDirection().setDy(i);
                    terrainFrame = super.getTerrainComponent().paint(terrainFrame, location, paintFlag);
                }
            }
        }
        return terrainFrame;
    }

    public int getDepth() {
        return depth;
    }

    public void setDepth(int depth) {
        this.depth = depth;
    }

    public int getBranches() {
        return branches;
    }

    public void setBranches(int branches) {
        this.branches = branches;
    }
}
