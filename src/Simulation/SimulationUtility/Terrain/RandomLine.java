//package Simulation.SimulationUtility.Terrain;
//
//import Simulation.Environment.Environment;
//import Simulation.Environment.Location;
//
//public class RandomLine extends BasicLine {
//
//    public RandomLine(boolean paintOrClear, Terrain terrain, int size, Direction direction) {
//        super(paintOrClear, terrain, size, direction);
//    }
//
//    @Override
//    public Environment paint(Environment environment, Location location) {
//        Location pointer = location;
//        for (int i = 0; i < super.getSize(); i++) {
//            if (environment.isLocationOnGrid(pointer)) {
//                environment = super.getTerrain().paint(environment, pointer);
//                pointer.setX(pointer.getX() + super.getDirection().dx());
//                pointer.setY(pointer.getY() + super.getDirection().dy());
//            }
//            else {
//                break;
//            }
//        }
//        environment = super.getTerrain().paint(environment, pointer);
//        return environment;
//    }
//
//    @Override
//    public Terrain copy() {
//        return new RandomLine(super.getPaintOrClear(), super.getTerrain(), super.getSize(), super.getDirection());
//    }
//}
