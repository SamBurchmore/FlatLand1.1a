package Simulation.Agent.AgentStructs;

/**
 * This enum contains the 5 possible actions an agent can take.
 * MOVE = move to a location
 * CREATE = breed with another agent
 * GRAZE = move to a location and take energy from it
 * PREDATE = move to a location and take the energy and space from its occupant
 * NONE = do nothing
 *
 * @author Sam Burchmore
 * @version 1.0a
 * @since 1.0a
 */
public enum AgentAction {
    MOVE,
    CREATE,
    GRAZE,
    PREDATE,
    NONE
}
