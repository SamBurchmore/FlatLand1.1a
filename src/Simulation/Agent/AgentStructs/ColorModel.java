package Simulation.Agent.AgentStructs;

/**
 * This enum contains the 3 possible color models and agent can use
 * STATIC = color never changes.
 * ATTRIBUTES = color changes according to the 3 mutating attributes: size, creationSize, and range
 * RANDOM = color changes randomly every time a new agent is born
 *
 * @author Sam Burchmore
 * @version 1.0a
 * @since 1.0a
 */
public enum ColorModel {
    STATIC,
    ATTRIBUTES,
    RANDOM
}
