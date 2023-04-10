package Simulation.Agent.AgentConcreteComponents;

import Simulation.Agent.AgentInterfaces.Agent;
import Simulation.Agent.AgentInterfaces.Attributes;
import Simulation.Agent.AgentInterfaces.Motivation;
import Simulation.Agent.AgentInterfaces.Scores;
import Simulation.Environment.Environment;
import Simulation.Environment.EnvironmentTile;
import Simulation.Environment.Location;

import java.util.ArrayList;
import java.util.Collections;

/**
 * This class represents an agent. It groups all the data required by an agent and contains some logical methods used by the Simulation.AgentLogic class.
 *
 * @author Sam Burchmore
 * @version 1.0a
 * @since 1.0a
 */
public class BasicAgent implements Agent {

    // What tile the agent currently occupies
    private Location location;
    // The agent's attributes
    private Attributes attributes;
    // The agent's scores
    private Scores scores;
    // The agents motivations
    private ArrayList<Motivation> motivations;
    // Lets Simulation.AgentLogic know this agent's space has been taken
    private boolean spaceTaken;

    /**
     * Constructs a BasicAgent object.
     *
     * @param location    the initial location
     * @param attributes  this agents attributes
     * @param scores      this agents scores
     * @param motivations this agents collection of motivations
     */
    public BasicAgent(Location location, Attributes attributes, Scores scores, ArrayList<Motivation> motivations) {
        this.location = location;
        this.attributes = attributes;
        this.scores = scores;
        this.motivations = motivations;
        spaceTaken = false;
    }

    /**
     * Constructs a BasicAgent object, creating and initialising its scores automatically.
     *
     * @param location    the initial location
     * @param attributes  this agents attributes
     * @param motivations this agents collection of motivations
     */
    public BasicAgent(Location location, Attributes attributes, ArrayList<Motivation> motivations) {
        this.location = location;
        this.attributes = attributes;
        this.scores = new BasicScores(attributes.getEnergyCapacity(), attributes.getEnergyCapacity(), attributes.getLifespan());
        this.motivations = motivations;
        spaceTaken = false;
    }

    /**
     * Constructs a new agent using 2 existing parent agents.
     * <p>
     * The new agents attributes are produced by combining the 2 parent attributes. The BasicAttributes constructor used will randomly
     * choose attributes from either parent. As motivations do not mutate, and agents can only breed with agents of the same type,
     * its assumed here that both parents will have the same motivations, so parentAs are copied. As the agents calculated attributes
     * are handled in Simulation.AgentLogic, the scores are simply copied from parentA. They will be initialised with the calculated
     * attributes in Simulation.AgentLogic.
     *
     * @param location the starting location of the new agent
     * @param parentA  the agent producing the children
     * @param parentB  the compatible agent found
     */
    public BasicAgent(Location location, Agent parentA, Agent parentB) {
        this.location = location;
        this.attributes = parentA.getAttributes().combine(parentB.getAttributes());
        this.motivations = parentA.copyMotivations();
        this.scores = parentA.getScores().copy();
        spaceTaken = false;
        initScores();
    }

    /**
     * Iterates the agents age by one, and de-iterates its creation counter
     */
    @Override
    public void liveDay() {
        this.getScores().setAge(this.getScores().getAge() + 1);
        this.getScores().setCreationCounter((this.getScores().getCreationCounter() - 1));
    }

    /**
     * Is the agent dead?
     *
     * @return true if the agents energy is <= 0 or if its age is >= its lifespan.
     */
    @Override
    public boolean isDead() {
        return this.getScores().getEnergy() <= 0 || this.getScores().getAge() >= this.getAttributes().getLifespan();
    }

    /**
     * Moves the agent to a new location, and reduces its energy accordingly.
     * <p>
     * Calculates the distance between the agents current and new location, then removes the distance * energyLostPerTile from its current energy.
     * It then sets the agents location to the new one.
     *
     * @param newLocation the location being moved to.
     */
    @Override
    public void move(Location newLocation) {
        Location oldLocation = location;
        int distance = (int) Math.floor(
                Math.sqrt(
                        ((newLocation.getX() - oldLocation.getX()) * (newLocation.getX() - oldLocation.getX()))
                                + ((newLocation.getY() - oldLocation.getY()) * (newLocation.getY() - oldLocation.getY()))
                ));
        this.getScores().setEnergy(this.getScores().getEnergy() - this.getAttributes().getEnergyLostPerTile() * distance);
        this.setLocation(newLocation);
    }

    /**
     * Increases the agents energy and returns how much was successfully taken from the tile.
     * <p>
     *
     * @param environmentTile the tile the agent currently occupies
     * @return how much energy was successfully taken from the tile
     */
    @Override
    public int graze(EnvironmentTile environmentTile) {
        if (environmentTile.getEnergyLevel() <= 0) {
            // If there's no energy, environment loses nothing and agent gains no energy.
            return 0;
        }
        if (environmentTile.getEnergyLevel() >= getAttributes().getEatAmount()) {
            // If there's more food than the agents eat amount, environment loses agents eat amount and agent gets it
            getScores().setEnergy(getScores().getEnergy() + getAttributes().getEatAmount());
            return getAttributes().getEatAmount();
        }
        // If there's less food than the agents eat amount, environment loses all and agent gains it.
        getScores().setEnergy(getScores().getEnergy() + environmentTile.getEnergyLevel());
        return environmentTile.getEnergyLevel();
    }

    /**
     * Increases the agent energy by the prey's energy.
     *
     * @param preyScores the scores belonging to the prey agent.
     */
    @Override
    public void predate(Scores preyScores) {
        getScores().setEnergy(getScores().getEnergy() + (preyScores.getEnergy() * 5));
    }

    /**
     * Returns a collection of child agents that where successfully created.
     * <p>
     * Checks for free space around the agent, free space is defined as a tile that is not terrain, and has no occupant or one
     * with a size smaller than the agent checking for space. If there is free space around the agent, it will shuffle the spaces,
     * and then possibly reduce them so the collection equals the agents creationSize. It will then place an agent at each space, settings its
     * current energy to the parents creationCost before removing the same amount from the parents' energy. Once the parent is out of energy or
     * all the spaces have had children placed, the parents creationCounter is set to its creationDelay and the children are returned.
     *
     * @param parentBLocation The location of the agents mate
     * @param environment     The environment
     * @return a possibly empty collection of agents
     */
    @Override
    public ArrayList<Agent> create(Location parentBLocation, Environment environment) {
        ArrayList<Location> childLocations = environment.freeSpace(this.getLocation(), getAttributes().getSize());
        ArrayList<Agent> childAgents = new ArrayList<>();
        if (!childLocations.isEmpty()) {
            Collections.shuffle(childLocations);
            for (Location childLocation : childLocations.subList(0, Math.min(childLocations.size(), this.getAttributes().getCreationSize()))) {
                if (getAttributes().getCreationCost() <= getScores().getEnergy()) {
                    Agent child = combine(environment.getTile(parentBLocation).getOccupant(), childLocation);
                    childAgents.add(child);
                    getScores().setEnergy(getScores().getEnergy() - getAttributes().getCreationCost());
                } else {
                    getScores().setCreationCounter(getAttributes().getCreationDelay());
                    return childAgents;
                }
            }
        }
        getScores().setCreationCounter(getAttributes().getCreationDelay());
        return childAgents;
    }

    /**
     * @return returns a copy of the agents motivations
     */
    @Override
    public ArrayList<Motivation> copyMotivations() {
        ArrayList<Motivation> motivations = new ArrayList<>();
        for (Motivation motivation : getMotivations()) {
            motivations.add(motivation.copy());
        }
        return motivations;
    }

    /**
     * Returns a new BasicAgent produced using this agent and another.
     * <p>
     * As the agents current energy is initialised with the parents creationCost, this can be done here.
     *
     * @param parentB       the second agent being used in the creation
     * @param childLocation the child agents location
     * @return a new agent at the location specified and with its current energy initialised
     */
    public Agent combine(Agent parentB, Location childLocation) {
        Agent newAgent = new BasicAgent(childLocation, this, parentB);
        newAgent.getScores().setEnergy(getAttributes().getCreationCost());
        return newAgent;
    }

    /**
     * Initialises the agents scores with its attributes.
     * <p>
     * As mutations are handled in this class, calculated attributes must be handled here too. As scores
     * require some of the agents calculated attributes, their initialisation needs to be handled here to.
     */
    @Override
    public void initScores() {
        getScores().setMaxEnergy(getAttributes().getEnergyCapacity());
        getScores().setMaxAge(getAttributes().getLifespan());
        getScores().setAge(0);
        getScores().setCreationCounter(getAttributes().getCreationAge());
    }

    @Override
    public boolean spaceTaken() {
        return spaceTaken;
    }

    @Override
    public void setSpaceTaken() {
        spaceTaken = true;
    }

    @Override
    public Location getLocation() {
        return this.location;
    }

    @Override
    public void setLocation(Location location) {
        this.location = location;
    }

    @Override
    public Attributes getAttributes() {
        return this.attributes;
    }

    @Override
    public void setAttributes(Attributes attributes) {
        this.attributes = attributes;
    }

    @Override
    public Scores getScores() {
        return this.scores;
    }

    @Override
    public void setScores(Scores scores) {
        this.scores = scores;
    }

    @Override
    public ArrayList<Motivation> getMotivations() {
        return motivations;
    }

    @Override
    public void setMotivations(ArrayList<Motivation> motivations) {
        this.motivations = motivations;
    }

    @Override
    public Object copy() {
        return new BasicAgent(this.getLocation(), this.getAttributes().copy(), this.getScores().copy(), this.copyMotivations());
    }
}
