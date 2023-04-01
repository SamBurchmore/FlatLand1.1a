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
 * Motivates agents to find and eat smaller agents with a different ID.
 *
 * @author Sam Burchmore
 * @version 1.0a
 * @since 1.0a
 */
public class PredatorMotivation extends BaseMotivation {

    public PredatorMotivation(int bias, int weight) {
        super(bias, weight);
    }

    /**
     * Returns an AgentDecision with the PREDATE action if valid prey exists on the input tile.
     * Otherwise, if the tile can still be moved to, then the MOVE action is returned.
     * If not then the NONE action is returned.
     */
    @Override
    public AgentDecision run(AgentVision tile, Attributes attributes, Scores scores) {
        // Predator motivation motivates agent to move to occupied tiles
        if (tile.isOccupied()) {
            // Tile is occupied, its occupant is smaller than the agent, and it's a different 'species' (code is different),
            if (tile.getOccupantAttributes().getSize() < attributes.getSize() && !Objects.equals(tile.getOccupantAttributes().getID(), attributes.getID())) {
                return new AgentDecision(tile.getLocation(), AgentAction.PREDATE, super.getBias() + tile.getOccupantScores().getEnergy() * super.getWeight());
            }
            // Tile is occupied but its occupant is either larger or the same species, set decision to NONE and its score to -1
            return new AgentDecision(null, AgentAction.NONE, -1);
        }
        // Tile is not occupied, set decision to MOVE and score to 1
        return new AgentDecision(tile.getLocation(), AgentAction.MOVE, 1);
    }

    @Override
    public int getCode() {
        return 2;
    }

    @Override
    public Motivation copy() {
        return new PredatorMotivation(super.getBias(), super.getWeight());
    }

}
