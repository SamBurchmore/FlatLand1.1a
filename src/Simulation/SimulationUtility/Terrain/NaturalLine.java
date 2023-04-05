package Simulation.SimulationUtility.Terrain;

import Simulation.Environment.Environment;
import Simulation.Environment.Location;

public class NaturalLine extends VariableBasicLine {

    private final Randomizer randomizer = new Randomizer();
    private int bendWeight;

    public NaturalLine(TerrainShape terrainComponent, int size, Direction direction, Variance variance, int bendWeight) {
        super(terrainComponent, size, direction, variance);
        this.bendWeight = bendWeight;
    }

    @Override
    public TerrainFrame paint(TerrainFrame terrainFrame, Location location, boolean paintFlag) {
        int size = super.getVariance().getLower();
        int dxRandom = 0;
        int dyRandom = 0;
        for (int i = 0; i < super.getSize() / (super.getVariance().getChangeRate() * 2); i++) {
            int randomChangeRate = randomizer.nextInt(super.getVariance().getChangeRate());
            for (int c = -randomChangeRate; c < randomChangeRate; c++) {
                if (terrainFrame.getEnvironment().isLocationOnGrid(location)) {
                    if (c < 0) {
                        size += super.getVariance().getUpper() / randomChangeRate;
                    } else {
                        size -= super.getVariance().getUpper() / randomChangeRate;
                    }
                    getTerrainComponent().setSize(size);
                    if (randomizer.getDecision(bendWeight)) {
                        dxRandom = randomizer.getPosNegRandom(super.getVariance().getLower() + 1);
                        dyRandom = randomizer.getPosNegRandom(super.getVariance().getLower() + 1);
                    }
                    location.setX(location.getX() + super.getDirection().getDx() + dxRandom);
                    location.setY(location.getY() + super.getDirection().getDy() + dyRandom);
                    terrainFrame = getTerrainComponent().paint(terrainFrame, location, paintFlag);
                } else {
                    break;
                }
            }
        }
        terrainFrame.setPointer(location);
        return terrainFrame;
    }

    @Override
    public Terrain copy() {
        return new NaturalLine(getTerrainComponent(), super.getSize(), super.getDirection(), getVariance(), getBendWeight());
    }

    public int getBendWeight() {
        return bendWeight;
    }

    public void setBendWeight(int bendWeight) {
        this.bendWeight = bendWeight;
    }
}
