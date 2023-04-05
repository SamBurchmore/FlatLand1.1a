package Simulation.SimulationUtility.Terrain;

public interface TerrainShape extends Terrain {

    Terrain getTerrainComponent();

    void setTerrainComponent(Terrain terrain);

    int getSize();

    void setSize(int size);

}
