package Simulation.SimulationUtility.Terrain;

public class TerrainFactory {

    private final Point point = new Point();
    private final Square square = new Square(point, 5);
    private final Circle circle = new Circle(point, 5);
    private final RandomVariableBasicLine randomVariableBasicLine = new RandomVariableBasicLine(circle, 5, new Direction(1, 1), new Variance(6, 3, 2));
    private final CompoundLine compoundLine = new CompoundLine(randomVariableBasicLine, 5, new Direction(5, 5));

    public Terrain getTerrain(int index) {
        switch (index){
            case 0 -> {
                return point;
            }
            case 1 -> {
                return square;
            }
            case 2 -> {
                return circle;
            }
            case 3 -> {
                return randomVariableBasicLine;
            }
        }
        return point;
    }


}
