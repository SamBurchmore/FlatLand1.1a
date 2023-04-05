package Simulation.SimulationUtility.Terrain;

import Simulation.Environment.Environment;
import Simulation.Environment.Location;

public abstract class BaseTerrainShape implements TerrainShape{

    private Terrain terrainComponent;
    private int size;

    public BaseTerrainShape(Terrain terrainComponent, int size) {
        this.terrainComponent = terrainComponent;
        this.size = size;
    }

    @Override
    public Terrain getTerrainComponent() {
        return terrainComponent;
    }

    @Override
    public void setTerrainComponent(Terrain terrain) {
        this.terrainComponent = terrain;
    }

    @Override
    public int getSize() {
        return size;
    }

    @Override
    public void setSize(int size) {
        this.size = size;
    }
}
