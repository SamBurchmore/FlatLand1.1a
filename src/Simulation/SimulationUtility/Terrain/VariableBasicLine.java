package Simulation.SimulationUtility.Terrain;

import Simulation.Environment.Environment;
import Simulation.Environment.Location;

public class VariableBasicLine extends BaseLine {

    private Variance variance;

    public VariableBasicLine(TerrainShape terrainComponent, int size, Direction direction, Variance variance) {
        super(terrainComponent, size, direction);
        this.variance = variance;
    }


    @Override
    public TerrainFrame paint(TerrainFrame terrainFrame, Location location, boolean paintFlag) {
        int size = variance.getLower();
        for (int i = 0; i < super.getSize() / (variance.getChangeRate() * 2); i++) {
                for (int c = -variance.getChangeRate(); c < variance.getChangeRate(); c++) {
                    if (terrainFrame.getEnvironment().isLocationOnGrid(location)) {
                        if (c < 0) {
                            size += variance.getUpper() / variance.getChangeRate();
                        } else {
                            size -= variance.getUpper() / variance.getChangeRate();
                        }
                        getTerrainComponent().setSize(size);
                        terrainFrame = getTerrainComponent().paint(terrainFrame, location, paintFlag);
                        location.setX(location.getX() + super.getDirection().getDx());
                        location.setY(location.getY() + super.getDirection().getDy());
                    } else {
                        break;
                    }
                }
        }
        terrainFrame.setPointer(location);
        return terrainFrame;
    }

    public Variance getVariance() {
        return variance;
    }

    public void setVariance(Variance variance) {
        this.variance = variance;
    }

    @Override
    public Terrain copy() {
        return new VariableBasicLine(getTerrainComponent(), super.getSize(), super.getDirection(), getVariance());
    }

    @Override
    public TerrainShape getTerrainComponent() {
        return (TerrainShape) super.getTerrainComponent();
    }

    @Override
    public void setTerrainComponent(Terrain terrainShape) {
        super.setTerrainComponent(terrainShape);
    }

    @Override
    public void setTerrainComponent(Line line) {

    }
}
