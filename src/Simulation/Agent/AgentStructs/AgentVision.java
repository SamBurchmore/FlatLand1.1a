package Simulation.Agent.AgentStructs;

import Simulation.Agent.AgentInterfaces.Attributes;
import Simulation.Agent.AgentInterfaces.Scores;
import Simulation.Environment.Location;

/**
 * This class represents the visible aspect of an environment tile. When Simulation.AgentLogic.lookAround(Agent) is called,
 * an AgentVision object will be generated for each tile within range. These are then used in the Simulation.AgentLogic class
 * to generate an AgentDecision.
 *
 * @author Sam Burchmore
 * @version 1.0a
 * @since 1.0a
 */
public class AgentVision {

    private final int energyLevel;
    private final Attributes occupantAttributes;
    private final Scores occupantScores;
    private final Location location;
    private final boolean isOccupied;

    /**
     * Constructs a vision object with an occupant.
     */
    public AgentVision(int energyLevel, boolean isOccupied, Location location, Attributes occupantAttributes, Scores occupantScores) {
        this.energyLevel = energyLevel;
        this.occupantAttributes = occupantAttributes;
        this.occupantScores = occupantScores;
        this.location = location;
        this.isOccupied = isOccupied;
    }

    /**
     * Constructs a vision object without an occupant.
     */
    public AgentVision(int energyLevel, boolean isOccupied, Location location) {
        this.energyLevel = energyLevel;
        this.occupantAttributes = null;
        this.occupantScores = null;
        this.location = location;
        this.isOccupied = isOccupied;
    }

    public int getEnergyLevel() {
        return this.energyLevel;
    }

    public boolean isOccupied() {
        return this.isOccupied;
    }

    public Attributes getOccupantAttributes() {
        return this.occupantAttributes;
    }

    public Scores getOccupantScores() {
        return this.occupantScores;
    }

    public Location getLocation() {
        return this.location;
    }
}
