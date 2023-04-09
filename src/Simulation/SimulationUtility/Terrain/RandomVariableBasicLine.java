package Simulation.SimulationUtility.Terrain;

import Simulation.Environment.Environment;
import Simulation.Environment.Location;

public class RandomVariableBasicLine extends VariableBasicLine {

    private final Randomizer randomizer = new Randomizer();

    public RandomVariableBasicLine(TerrainShape terrainComponent, int size, Direction direction, Variance variance) {
        super(terrainComponent, size, direction, variance);
    }

    @Override
    public TerrainFrame paint(TerrainFrame terrainFrame, Location location, boolean paintFlag) {
//        if (super.getDirection().getDx() == 0 && super.getDirection().getDy() == 0) {
//            return terrainFrame;
//        }
        int size = super.getVariance().getLower();
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
                    location.setX(location.getX() + super.getDirection().getDx());
                    location.setY(location.getY() + super.getDirection().getDy());
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
        return new RandomVariableBasicLine(getTerrainComponent(), super.getSize(), super.getDirection(), getVariance());
    }

}
