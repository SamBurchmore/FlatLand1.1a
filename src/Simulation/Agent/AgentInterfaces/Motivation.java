package Simulation.Agent.AgentInterfaces;

import Simulation.Agent.AgentStructs.AgentDecision;
import Simulation.Agent.AgentStructs.AgentVision;

/**
 * Provides an interface for agent motivations.
 * <p>
 * This interface can be considered a strategy with Agent being the context.
 *
 * @author Sam Burchmore
 * @version 1.0a
 * @since 1.0a
 */
public interface Motivation {

    /**
     * This method should return an AgentDecision instance.
     * <p>
     * It should use the tile, attributes, and scores parameters to construct the decision and assign it a decision score.
     *
     * @param tile       the tile to generate a decision from
     * @param attributes the agents attributes
     * @param scores     the agents current state
     * @return the decision generated
     */
    AgentDecision run(AgentVision tile, Attributes attributes, Scores scores);

    Motivation copy();

    boolean equals(Motivation motivation);

    int getCode();

    int getBias();

    int getWeight();

    void setBias(int bias);

    void setWeight(int weight);
}
