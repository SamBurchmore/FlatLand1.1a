package Simulation.SimulationUtility.Terrain;

import Simulation.Environment.Environment;
import Simulation.Environment.Location;

public class CompositeLine implements Line {
    private Line line;
    private boolean paintOrClear;
    private int size;
    private Direction direction;
    public CompositeLine(boolean paintOrClear, Line line, int size, Direction direction) {
        this.line = line;
        this.paintOrClear = paintOrClear;
        this.size = size;
        this.direction = direction;
    }

    public Line getLine() {
        return line;
    }

    public void setLine(Line line) {
        this.line = line;
    }

    @Override
    public Environment paint(Environment environment, Location location) {
        environment.getTile(location).setTerrain(paintOrClear);
        return environment;
    }


    @Override
    public void setPaintOrClear(boolean paintOrClear) {
        this.paintOrClear = paintOrClear;
    }

    @Override
    public boolean isTerrain() {
        return paintOrClear;
    }

    @Override
    public void setSize(int size) {
        this.size = size;
    }

    @Override
    public void setDirection(Direction direction) {
        this.direction = direction;
    }

    @Override
    public int getSize() {
        return size;
    }

    @Override
    public Direction getDirection() {
        return direction;
    }

    @Override
    public boolean getPaintOrClear() {
        return paintOrClear;
    }

    @Override
    public Terrain copy() {
        return new Point(paintOrClear);
    }
}