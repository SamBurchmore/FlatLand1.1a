package Simulation.SimulationUtility.Terrain;

import Simulation.Environment.EnvironmentTile;
import Simulation.Environment.Location;

public class TerrainMask {
    private Location location;
    private boolean[] grid;
    private int size;

    public TerrainMask(Location location, int size) {
        this.location = location;
        this.size = size;
        this.grid = new boolean[size * size];
        int x = 0;
        int y = 0;
        for (int i = 0; i < size * size; i++) {
            this.grid[i] = false;
            x++;
            if (x == size) {
                x = 0;
                y++;
            }
        }
    }

    public boolean[] getGrid() {
        return grid;
    }

    public void setGrid(boolean[] grid) {
        this.grid = grid;
    }

    public void setTile(Location location, boolean isTerrain) {
        grid[location.getY() *  size + location.getX()] = isTerrain;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }
}
