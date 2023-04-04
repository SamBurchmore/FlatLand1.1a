package Simulation.SimulationUtility.Terrain;

import Simulation.Environment.Environment;
import Simulation.Environment.Location;

public class BasicLine implements Line {

    private int size;
    private Direction direction;
    private boolean paintOrClear;
    private Terrain terrain;

    public BasicLine(boolean paintOrClear, Terrain terrain, int size, Direction direction) {
        this.size = size;
        this.direction = direction;
        this.terrain = terrain;
        this.paintOrClear = paintOrClear;
    }

    @Override
    public Environment paint(Environment environment, Location location) {
        Location pointer = location;
        for (int i = 0; i < size; i++) {
            if (environment.isLocationOnGrid(pointer)) {
                environment = terrain.paint(environment, pointer);
                pointer.setX(pointer.getX() + direction.dx());
                pointer.setY(pointer.getY() + direction.dy());
            }
            else {
                break;
            }
        }
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

    public void setSize(int size) {
        this.size = size;
    }

    @Override
    public boolean getPaintOrClear() {
        return paintOrClear;
    }

    @Override
    public Terrain copy() {
        return null;
    }

    public void setDirection(Direction direction) {
        this.direction = direction;
    }

    public int getSize() {
        return size;
    }

    public Direction getDirection() {
        return direction;
    }
}
