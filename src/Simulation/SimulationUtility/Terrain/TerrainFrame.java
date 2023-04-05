package Simulation.SimulationUtility.Terrain;

import Simulation.Environment.Environment;
import Simulation.Environment.Location;

public class TerrainFrame {
    private Environment environment;
    private Location pointer;

    public TerrainFrame(Environment environment, Location pointer) {
        this.environment = environment;
        this.pointer = pointer;
    }

    public Environment getEnvironment() {
        return environment;
    }

    public void setEnvironment(Environment environment) {
        this.environment = environment;
    }

    public Location getPointer() {
        return pointer;
    }

    public void setPointer(Location pointer) {
        this.pointer = pointer;
    }
}
