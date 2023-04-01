package Simulation.Agent.AgentBaseComponents;

import Simulation.Agent.AgentInterfaces.Motivation;

import java.io.Serializable;

/**
 * An abstract class which defines motivations shared behaviour.
 *
 * @author Sam Burchmore
 * @version 1.0a
 * @since 1.0a
 */
public abstract class BaseMotivation implements Motivation, Serializable {

    // The bias added in the motivation score calculation.
    int bias;
    // The bias added in the motivation score calculation.
    int weight;

    public BaseMotivation(int bias, int weight) {
        this.bias = bias;
        this.weight = weight;
    }

    /**
     * @return true if the input motivation equals its own.
     */
    @Override
    public boolean equals(Motivation motivation) {
        return motivation.getCode() == this.getCode();
    }

    @Override
    public int getBias() {
        return bias;
    }

    @Override
    public int getWeight() {
        return weight;
    }

    @Override
    public void setBias(int bias) {
        this.bias = bias;
    }

    @Override
    public void setWeight(int weight) {
        this.weight = weight;
    }

}
