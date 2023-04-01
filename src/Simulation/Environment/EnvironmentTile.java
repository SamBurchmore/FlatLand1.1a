package Simulation.Environment;

import Simulation.Agent.AgentInterfaces.Agent;

import java.io.Serializable;

/**
 * Represents an individual environment tile. It contains an energy level,
 * a terrain flag and can store one occupant.
 *
 * @author Sam Burchmore
 * @version 1.0a
 * @since 1.0a
 */
public class EnvironmentTile implements Serializable {

    // Is this tile terrain or not, i.e. can it be traversed by an agent
    private boolean isTerrain;
    // The current energy level of this tile.
    private int energyLevel;
    // The tiles location
    private final Location location;
    // The agent (or lack of) currently occupying this tile
    private Agent currentAgent;

    public EnvironmentTile(int energyLevel, int x, int y) {
        this.energyLevel = energyLevel;
        this.location = new Location(x, y);
        this.currentAgent = null;
        isTerrain = false;
    }

    public boolean isTerrain() {
        return isTerrain;
    }

    public void setTerrain(boolean isTerrain) {
        this.isTerrain = isTerrain;
    }

    public Integer getEnergyLevel() {
        return this.energyLevel;
    }

    public void setEnergyLevel(Integer energyLevel) {
        this.energyLevel = energyLevel;
    }

    public Location getLocation() {
        return this.location;
    }

    public Agent getOccupant() {
        return this.currentAgent;
    }

    public void setOccupant(Agent newAgent) {
        this.currentAgent = newAgent;
    }

    public boolean isOccupied() {
        return this.currentAgent != null;
    }

}
