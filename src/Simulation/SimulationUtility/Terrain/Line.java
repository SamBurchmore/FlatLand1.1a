package Simulation.SimulationUtility.Terrain;

public interface Line extends Terrain {

    void setSize(int size);
    void setDirection(Direction direction);
    int getSize();
    Direction getDirection();
}
