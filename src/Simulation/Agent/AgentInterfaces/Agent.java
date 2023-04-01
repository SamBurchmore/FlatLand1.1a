package Simulation.Agent.AgentInterfaces;

import Simulation.Environment.Environment;
import Simulation.Environment.EnvironmentTile;
import Simulation.Environment.Location;

import java.util.ArrayList;

/**
 * Provides a contract for how agents should interact with the simulation.
 *
 * @author Sam Burchmore
 * @version 1.0a
 * @since 1.0a
 */
public interface Agent {

    /**
     * This method should be called every simulated day. It should contain all the logic that should occur
     * on a daily basis.
     * <p>
     * I.e. age increasing.
     */
    void liveDay();

    /**
     * This method should set the agents location to the input location. It should also
     * handle any other logic intended to occur every time an agent moves.
     * <p>
     * I.e. reducing energy based on distance.
     *
     * @param newLocation the location the agent is moving to.
     */
    void move(Location newLocation);

    /**
     * This method should increase the agents energy based on the amount in the environmentTile and the
     * agents eat amount and then returns how much energy was taken from the tile.
     * <p>
     *
     * @param environmentTile the tile the agent is grazing from
     * @return how much energy was successfully taken
     */
    int graze(EnvironmentTile environmentTile);

    /**
     * This method should increase the agents energy based of the input Scores object.
     *
     * @param preyScores the state of the agents found prey
     */
    void predate(Scores preyScores);

    /**
     * This method should return a collection of child agents constructed using this agents attributes
     * and the attributes of the agent at parentBLocation. The size of the collection returned should
     * correspond to the agents creationCost field and its current state.
     *
     * @param parentBLocation the location of the second parent
     * @param environment     the environment the parents currently inhabit, used to generate new child locations.
     * @return the collection of successfully created children.
     */
    ArrayList<Agent> create(Location parentBLocation, Environment environment);

    /**
     * This method should return an exact copy of the agents collection of motivations.
     */
    ArrayList<Motivation> copyMotivations();

    /**
     * This method should return true if the agent should no longer be alive.
     * <p> Allows the AgentLogic class to know if it should remove this agent from the simulation or not.
     */
    boolean isDead();

    /**
     * This method should return true if the agents space has been taken by an agent earlier in the cycle.
     * <p>
     * Lets the AgentLogic class know this agent should be removed from the simulation. This could be because
     * it has been preyed upon, or a larger grazer has taken its space.
     */
    boolean spaceTaken();

    void initScores();

    void setSpaceTaken();

    ArrayList<Motivation> getMotivations();

    void setMotivations(ArrayList<Motivation> motivations);

    Attributes getAttributes();

    Scores getScores();

    void setAttributes(Attributes attributes);

    void setLocation(Location location);

    Location getLocation();

    void setScores(Scores scores);

    Object copy();

    String toString();
}
