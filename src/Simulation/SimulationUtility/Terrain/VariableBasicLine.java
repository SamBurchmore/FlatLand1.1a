package Simulation.SimulationUtility.Terrain;

import Simulation.Environment.Environment;
import Simulation.Environment.Location;

public class VariableBasicLine extends BasicLine {
    private Variance variance;
    public VariableBasicLine(boolean paintOrClear, Terrain terrain, int size, Direction direction, Variance variance) {
        super(paintOrClear, terrain, size, direction);
        this.variance = variance;
    }

    @Override
    public Environment paint(Environment environment, Location location) {
        Location pointer = location;
        int size = variance.getLower();
        for (int i = 0; i < super.getSize() / (variance.getChangeRate() * 2); i++) {
                for (int c = -variance.getChangeRate(); c < variance.getChangeRate(); c++) {
                    if (environment.isLocationOnGrid(pointer)) {
                        if (c < 0) {
                            size += variance.getUpper() / variance.getChangeRate();
                        } else {
                            size -= variance.getUpper() / variance.getChangeRate();
                        }
                        super.getTerrain().setSize(size);
                        environment = super.getTerrain().paint(environment, pointer);
                        pointer.setX(pointer.getX() + super.getDirection().dx());
                        pointer.setY(pointer.getY() + super.getDirection().dy());
                    } else {
                        break;
                    }
                }
        }
        return environment;
    }

    public Variance getVariance() {
        return variance;
    }

    public void setVariance(Variance variance) {
        this.variance = variance;
    }

    @Override
    public Terrain copy() {
        return null;
    }
}
