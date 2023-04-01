package Simulation.Agent.AgentStructs;

import Simulation.Environment.Location;

/**
 * This Record contains a location, an AgentAction and an integer representing the decisions score.
 *
 * @param location      the tile involved in this decision, either its being moved to or the agent is breeding with its occupant.
 * @param agentAction   the action involved with this decision, used by the Simulation.AgentLogic class to handle the agents decision.
 * @param decisionScore how much the agent wants to make this decision, an agent will always choose the decision with the highest score.
 * @author Sam Burchmore
 * @version 1.0a
 * @since 1.0a
 */
public record AgentDecision(Location location, AgentAction agentAction, int decisionScore) {
}
