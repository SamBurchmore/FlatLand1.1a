package Simulation.Agent.AgentConcreteComponents;

import Simulation.Agent.AgentBaseComponents.BaseMotivation;
import Simulation.Agent.AgentInterfaces.Attributes;
import Simulation.Agent.AgentInterfaces.Motivation;
import Simulation.Agent.AgentInterfaces.Scores;
import Simulation.Agent.AgentStructs.AgentAction;
import Simulation.Agent.AgentStructs.AgentDecision;
import Simulation.Agent.AgentStructs.AgentVision;

/**
 * Motivates agents to find free tiles with energy.
 *
 * @author Sam Burchmore
 * @version 1.0a
 * @since 1.0a
 */
public class GrazerMotivation extends BaseMotivation {

    public GrazerMotivation(int bias, int weight) {
        super(bias, weight);
    }

    /**
     * Returns an AgentDecision with the GRAZE action if the tile has energy and can be moved to.
     * Otherwise, if the tile can still be moved to, then the MOVE action is returned.
     * If not then the NONE action is returned.
     */
    @Override
    public AgentDecision run(AgentVision tile, Attributes attributes, Scores scores) {
        // Check if the tile is unoccupied, or its occupant is smaller
        if (!tile.isOccupied() || tile.getOccupantAttributes().getSize() < attributes.getSize()) {
            // Check if the tiles energy is above 0
            if (tile.getEnergyLevel() > 0) {
                return new AgentDecision(tile.getLocation(), AgentAction.GRAZE, (super.getBias() + Math.min(tile.getEnergyLevel(), attributes.getEatAmount()) * super.getWeight()));
            }
            return new AgentDecision(tile.getLocation(), AgentAction.MOVE, 1);
        }
        return new AgentDecision(null, AgentAction.NONE, -1);
    }

    @Override
    public int getCode() {
        return 1;
    }

    @Override
    public Motivation copy() {
        return new GrazerMotivation(super.getBias(), super.getWeight());
    }

}