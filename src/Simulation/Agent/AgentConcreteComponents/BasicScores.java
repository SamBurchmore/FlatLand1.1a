package Simulation.Agent.AgentConcreteComponents;

import Simulation.Agent.AgentInterfaces.Scores;

import java.io.Serializable;

/**
 * Makes up the agents scores. Scores are the agents current state, how much energy it has, how old it is, and if
 * it's still in its creationDelay period.
 *
 * @author Sam Burchmore
 * @version 1.0a
 * @since 1.0a
 */
public class BasicScores implements Scores, Serializable {
    // The agents current energy
    private int energy;
    // How many steps the agents has existed for
    private int age;
    // How much energy the agent can store, used here so energy isn't increased past it
    private int maxEnergy;
    // How long the agent can exist for, used here so energy isn't increased past it
    private int maxAge;
    // Keeps track of if the agent can currently breed
    private int creationCounter;

    /**
     * Constructs a BasicScores object using the input parameters.
     * <p>
     * The constructor simply takes the input parameters and assigns them
     * to the new BasicAttributes instance. Then it calls the calculateAttributes()
     * method to calculate the rest of the attributes. If the attributes mutationMagnitude
     * is greater than zero, it will generate its mutating color from its seed color.
     */
    public BasicScores(int energy, int maxEnergy, int maxAge) {
        this.energy = energy;
        this.maxEnergy = maxEnergy;
        this.maxAge = maxAge;
        this.creationCounter = 0;
        this.age = 0;
    }

    @Override
    public int getEnergy() {
        return this.energy;
    }

    @Override
    public void setEnergy(int energy) {
        this.energy = Math.min(Math.max(energy, 0), maxEnergy);
    }

    @Override
    public int getMaxEnergy() {
        return this.maxEnergy;
    }

    @Override
    public void setMaxEnergy(int maxHunger) {
        this.maxEnergy = maxHunger;
    }

    @Override
    public int getAge() {
        return this.age;
    }

    @Override
    public void setAge(int age) {
        this.age = age;
    }

    @Override
    public int getMaxAge() {
        return this.maxAge;
    }

    @Override
    public void setMaxAge(int maxAge) {
        this.maxAge = maxAge;
    }

    @Override
    public int getCreationCounter() {
        return this.creationCounter;
    }

    @Override
    public void setCreationCounter(int creationCounter) {
        this.creationCounter = Math.max(creationCounter, 0);

    }

    @Override
    public Scores copy() {
        return new BasicScores(getEnergy(), getMaxEnergy(), getMaxAge());
    }
}
