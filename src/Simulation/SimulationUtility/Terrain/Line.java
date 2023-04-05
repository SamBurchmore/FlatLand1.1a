package Simulation.SimulationUtility.Terrain;

public interface Line extends TerrainShape {

    Direction getDirection();

    void setDirection(Direction direction);

    void setTerrainComponent(TerrainShape terrainShape);

    void setTerrainComponent(Line line);

}
