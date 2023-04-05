package Simulation.SimulationUtility.Terrain;

public abstract class BaseLine extends BaseTerrainShape implements Line {

    private Direction direction;

    public BaseLine(Terrain terrainComponent, int size, Direction direction) {
        super(terrainComponent, size);
        this.direction = direction;
    }

    @Override
    public Direction getDirection() {
        return direction;
    }

    @Override
    public void setDirection(Direction direction) {
        this.direction = direction;
    }

    public void setTerrainComponent(TerrainShape terrainShape) {
        super.setTerrainComponent(terrainShape);
    }

    public void setTerrainComponent(Line line) {
        super.setTerrainComponent(line);
    }

    public void setTerrainComponent(Terrain terrain) {
        super.setTerrainComponent(terrain);
    }

}
