package Simulation.SimulationUtility.Terrain;

import Simulation.Environment.Location;

public class BendingCompoundLine extends BaseLine {

    private final Randomizer randomizer = new Randomizer();
    private int bendWeight;

    public BendingCompoundLine(Line lineComponent, int size, Direction direction, int bendWeight) {
        super(lineComponent, size, direction);
        this.bendWeight = bendWeight;
    }

    @Override
    public TerrainFrame paint(TerrainFrame terrainFrame, Location location, boolean paintFlag) {
        // Paint initial component
        terrainFrame = getTerrainComponent().paint(terrainFrame, location, paintFlag);
        for (int i = 0; i <= super.getSize(); i++) {
            if (randomizer.getDecision(bendWeight)) {
                getTerrainComponent().setDirection(randomizer.getDirection(super.getDirection().getDx(), super.getDirection().getDy()));
            }
            terrainFrame = getTerrainComponent().paint(terrainFrame, terrainFrame.getPointer(), paintFlag);
        }
        return terrainFrame;
    }

    public int getBendWeight() {
        return bendWeight;
    }

    public void setBendWeight(int bendWeight) {
        this.bendWeight = bendWeight;
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
