package Simulation.Agent.AgentInterfaces;


/**
 * Provides an interface for agent scores.
 *
 * @author Sam Burchmore
 * @version 1.0a
 * @since 1.0a
 */
public interface Scores {
    void setAge(int age);

    void setCreationCounter(int creationCounter);

    void setEnergy(int energy);

    int getEnergy();

    int getAge();

    int getMaxEnergy();

    int getMaxAge();

    void setMaxEnergy(int maxEnergy);

    void setMaxAge(int maxAge);

    int getCreationCounter();

    Scores copy();
}
