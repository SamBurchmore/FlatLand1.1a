package Simulation.Agent.AgentConcreteComponents;

import Simulation.Agent.AgentBaseComponents.BaseMotivation;
import Simulation.Agent.AgentInterfaces.Attributes;
import Simulation.Agent.AgentInterfaces.Motivation;
import Simulation.Agent.AgentInterfaces.Scores;
import Simulation.Agent.AgentStructs.AgentAction;
import Simulation.Agent.AgentStructs.AgentDecision;
import Simulation.Agent.AgentStructs.AgentVision;

import java.util.Objects;

/**
 * Motivates agents to find a mate and create new agents.
 *
 * @author Sam Burchmore
 * @version 1.0a
 * @since 1.0a
 */
public class CreatorMotivation extends BaseMotivation {

    public CreatorMotivation(int bias, int weight) {
        super(bias, weight);
    }

    /**
     * Returns an AgentDecision with the CREATE action if a valid mate exists on the input tile.
     * Otherwise, if the tile can still be moved to, then the MOVE action is returned.
     * If not then the NONE action is returned.
     */
    @Override
    public AgentDecision run(AgentVision tile, Attributes attributes, Scores scores) {
        // Creator motivation motivates agent to move to occupied tiles
        if (tile.isOccupied()) {
            // Check if tile occupant has same code, its age is above its creation age, its creation counter is less than 0, and its attributes are similar
            if (Objects.equals(tile.getOccupantAttributes().getID(), attributes.getID())
                    && tile.getOccupantScores().getAge() >= tile.getOccupantAttributes().getCreationAge()
                    && tile.getOccupantScores().getCreationCounter() <= 0
                    && compareAttributes(attributes, tile.getOccupantAttributes())) {
                // Check if this agents age is above its creation age, its creation counter is less than 0, and it has more than 1/4 its max energy
                if (scores.getAge() >= attributes.getCreationAge()
                        && scores.getCreationCounter() <= 0
                        && scores.getEnergy() > attributes.getEnergyCapacity() / 4) {
                    return new AgentDecision(tile.getLocation(), AgentAction.CREATE, super.getBias() * super.getWeight());
                }
            }
            // Tile is occupied but its occupant is a different species, set decision to NONE and score to -10
            return new AgentDecision(null, AgentAction.NONE, -1);
        }
        // Tile is not occupied, set decision to MOVE and score to 1
        return new AgentDecision(tile.getLocation(), AgentAction.MOVE, 1);
    }

    @Override
    public Motivation copy() {
        return new CreatorMotivation(super.getBias(), super.getWeight());
    }

    @Override
    public int getCode() {
        return 0;
    }

    /**
     * Compares 2 attributes and returns false if their values are too different.
     * <p>
     * If the size difference is greater than 10 or if the creation size or range difference is greater than 2, return false.
     */
    public static boolean compareAttributes(Attributes attributesA, Attributes attributesB) {
        if (Math.abs(attributesA.getSize() - attributesB.getSize()) > 10) {
            return false;
        }
        if (Math.abs(attributesA.getCreationSize() - attributesB.getCreationSize()) > 2) {
            return false;
        }
        return Math.abs(attributesA.getRange() - attributesB.getRange()) < 2;
    }

}

