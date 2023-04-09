package Simulation;

import Simulation.Agent.AgentConcreteComponents.BasicAgent;
import Simulation.Agent.AgentInterfaces.Agent;
import Simulation.Agent.AgentInterfaces.Motivation;
import Simulation.Agent.AgentStructs.AgentAction;
import Simulation.Agent.AgentStructs.AgentDecision;
import Simulation.Agent.AgentStructs.AgentVision;
import Simulation.Environment.Environment;
import Simulation.Environment.EnvironmentTile;
import Simulation.Environment.Location;
import Simulation.SimulationUtility.AgentEditor;
import Simulation.SimulationUtility.Diagnostics;
import Simulation.SimulationUtility.SimulationSettings.EnvironmentSettings;
import Simulation.SimulationUtility.SimulationSettings.SimulationSettings;
import Simulation.SimulationUtility.SimulationSettings.TerrainSettings;
import Simulation.SimulationUtility.Terrain.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;
import java.util.stream.IntStream;

/**
 * This class represents the simulation. It acts as a controller to the Environment and the Agents. It contains 2 inner classes: AgentLogic and TerrainGenerator.
 * AgentLogic contains all the methods required for agents to interact with and live in the simulation. TerrainGenerator contains methods which generate terrain shapes
 * on the Environment.
 *
 * @author Sam Burchmore
 * @version 1.0a
 * @since 1.0a
 */
public class Simulation {
    // The instance of the environment class
    private Environment environment;
    // Here we store all the agents currently in the simulation
    private ArrayList<Agent> agentList;
    // All newly born and surviving agents from each cycle are placed in here. At the end agentList is set to this
    private ArrayList<Agent> aliveAgentList;
    // The instance of the agent editor class
    private final AgentEditor agentEditor;
    // The instance of the diagnostics class
    private final Diagnostics diagnostics;
    // The instance of the agent logic inner class
    private final AgentLogic agentLogic;
    // The instance of the terrain generator class
    private final TerrainGenerator terrainGenerator;
    // The random instance used for decisions within the class
    private final Random random = new Random();
    // How much info is logged by the diagnostics class = (0=low, 1=high)
    private int diagnosticsVerbosity = 1;

    public Simulation(int size, int minEnergyLevel, int maxEnergyLevel, double energyRegenChance, int energyRegenAmount) {
        this.environment = new Environment(size, maxEnergyLevel, maxEnergyLevel, minEnergyLevel, energyRegenChance, energyRegenAmount);
        this.agentList = new ArrayList<>();
        this.aliveAgentList = new ArrayList<>();
        this.diagnostics = new Diagnostics(maxEnergyLevel * (size * size), minEnergyLevel * (size * size));
        this.agentEditor = new AgentEditor();
        this.agentLogic = new AgentLogic();
        this.terrainGenerator = new TerrainGenerator();

    }

    /**
     * Populates the environment with agents.
     * <p>
     * Iterates over every environment tile and tries to place an agent on one. The chance of an agent being placed corresponds
     * to the density parameter, i.e. a 100.0 an agent will always be placed, and at 1.0 and agent will have a 1/100 chance of
     * being placed. If this passes, then it randomly selects one agent from the AgentEditor instance. The chance of this agent
     * being placed corresponds with its spawning weight. At 1, it will always spawn and never at 0.
     *
     * @param density how densely should the environment be populated with agents.
     */
    public void populate(double density) {
        ArrayList<Agent> activeAgents = agentEditor.getActiveAgents();
        // Iterate over each tile
        IntStream.range(0, environment.getSize() * environment.getSize()).sequential().forEach(i -> {
            if (this.random.nextInt(10000) / 100.0 < density && !environment.getGrid()[i].isOccupied()) {
                // Select a random agent
                int agentIndex = random.nextInt(activeAgents.size());
                BasicAgent agent;
                for (int j = 0; j < activeAgents.size(); j++) {
                    // Try to place the randomly selected agent
                    if (j == agentIndex && agentEditor.getAgent(j).getAttributes().getSpawningWeight() > random.nextInt(100)) {
                        agent = agentLogic.getAgentFromEditor(j);
                        EnvironmentTile wt = environment.getGrid()[i];
                        agent.setLocation(wt.getLocation());
                        wt.setOccupant(agent);
                        agentList.add(agent);
                        diagnostics.addToAgentStats(
                                j,
                                1, agent.getScores().getEnergy(),
                                agent.getScores().getAge(),
                                agent.getAttributes().getSize(),
                                agent.getAttributes().getCreationSize(),
                                agent.getAttributes().getRange());
                    }
                }
            }
        });
    }

    /**
     * Cycles the environment for one step.
     * <p>
     * Iterates over the agentList. If an agent hasn't been eaten earlier in the cycle, it runs the agent. After it's
     * iterated over every agent, it then overwrites agentList with aliveAgentList. Then it iterates over each environment
     * tile and possible regenerates its energy, depending on the environments settings.
     */
    public void cycle() {
        // Reset agent stats for new round
        diagnostics.clearAgentStats();
        // Iterate over all agents in the simulation
        for (Agent currentAgent : agentList) {
            if (!currentAgent.spaceTaken()) { // Has this agents space been taken this cycle?
                agentLogic.runAgent(currentAgent);
                if (!currentAgent.isDead()) {
                    // If the agent is alive after running a step, log its stats
                    diagnostics.addToAgentStats(
                            currentAgent.getAttributes().getID(),
                            1, currentAgent.getScores().getEnergy(),
                            currentAgent.getScores().getAge(),
                            currentAgent.getAttributes().getSize(),
                            currentAgent.getAttributes().getCreationSize(),
                            currentAgent.getAttributes().getRange()
                    );
                }
            }
        }
        agentList = aliveAgentList;
        Collections.shuffle(agentList);
        aliveAgentList = new ArrayList<>();
        // Iterate over each tile
        IntStream.range(0, environment.getSize() * environment.getSize()).sequential().forEach(i -> {
            // Try to replenish its energy
            if (random.nextInt(10000) / 100.0 < environment.getEnergyRegenChance() && !environment.getGrid()[i].isTerrain()) {
                int modifyAmount = environment.modifyTileEnergyLevel(environment.getGrid()[i].getLocation(), environment.getEnergyRegenAmount());
                diagnostics.modifyCurrentEnvironmentEnergy(modifyAmount);
            }
        });
    }

    /**
     * Removes all agents from the environment.
     * <p>
     * Iterates over the environment and sets every tile occupant to null.
     */
    public void clearAgents() {
        IntStream.range(0, environment.getSize() * environment.getSize()).sequential().forEach(i -> {
            EnvironmentTile current_wt = environment.getGrid()[i];
            current_wt.setOccupant(null);
        });
        agentList = new ArrayList<>();
    }

    /**
     * Replenishes the environment's energy.
     * <p>
     * Iterates over the environment and sets every tile's energy level to its max.
     */
    public void replenishEnvironmentEnergy() {
        IntStream.range(0, environment.getSize() * environment.getSize()).sequential().forEach(i -> {
            EnvironmentTile current_wt = environment.getGrid()[i];
            current_wt.setEnergyLevel(environment.getMaxEnergyLevel());
        });
        diagnostics.resetCurrentEnvironmentEnergy();
    }

    /**
     * Updates the environments settings.
     * <p>
     * Sets the environments settings to the input settings and handles removing the agents and updating the diagnostics accordingly.
     *
     * @param environmentSettings the new environment settings
     */
    public void setEnvironmentSettings(EnvironmentSettings environmentSettings) {
        if (environmentSettings.getSize() != environment.getSize()) {
            clearAgents();
            environment.setEnvironmentSettings(environmentSettings);
            diagnostics.setMaxEnvironmentEnergy(environmentSettings.getMaxEnergyLevel() * environment.getSize() * environment.getSize());
            diagnostics.setMinEnvironmentEnergy(environmentSettings.getMinEnergyLevel() * environment.getSize() * environment.getSize());
            diagnostics.resetCurrentEnvironmentEnergy();
        } else if (environmentSettings.getMaxEnergyLevel() < environment.getMaxEnergyLevel()) {
            environment.setEnvironmentSettings(environmentSettings);
            diagnostics.setMaxEnvironmentEnergy(environmentSettings.getMaxEnergyLevel() * environment.getSize() * environment.getSize());
            diagnostics.setMinEnvironmentEnergy(environmentSettings.getMinEnergyLevel() * environment.getSize() * environment.getSize());

            diagnostics.resetCurrentEnvironmentEnergy();
        }
        environment.setEnvironmentSettings(environmentSettings);
        diagnostics.setMaxEnvironmentEnergy(environmentSettings.getMaxEnergyLevel() * environment.getSize() * environment.getSize());
        diagnostics.setMinEnvironmentEnergy(environmentSettings.getMinEnergyLevel() * environment.getSize() * environment.getSize());

    }

    /**
     * Groups the logical methods used for running agents.
     * <p>
     * This class is responsible for the agent's logic. These methods are placed here so the environment and agents can
     * communicate directly, rather than passing objects around. Only 2 methods in this class are public: runAgent() and
     * getAgentFromEditor(). All other methods exist to support the runAgent() method.
     *
     * @author Sam Burchmore
     * @version 1.0a
     * @since 1.0a
     */
    private class AgentLogic {

        /**
         * Runs the input agent for one day.
         * <p>
         * This method modifies the following Simulation parameters: environment, aliveAgentList, and diagnostics.
         * First the method checks if the agent has been eaten, this means its place on the environment has been taken,
         * so at this point the method ends. If an agent survives the day, it will be added to the aliveAgentList so by simply
         * ending the method we've now removed this agent from the simulation. If the method continues, it then calls the agents
         * liveDay() method, this increments its age and decrements its creationCounter. If the agent is still alive after this,
         * a collection of AgentVision objects is produced by the lookaround() method. This is then transformed into a single AgentDecision
         * method. The method then handles the agents decision.
         *
         * @param agent the agent to be run
         */
        public void runAgent(Agent agent) {
            if (agent.spaceTaken()) {
                return; // Agent has been eaten by another agent, therefor its already been removed from the environment, all we need to do is not add it to the aliveAgentList
            }
            agent.liveDay(); // Increments its age and decrements its creationCounter
            if (agent.isDead()) {
                environment.setOccupant(agent.getLocation(), null); // If the agent is now dead, remove it from the board and don't add it to aliveAgentList
                return;
            }
            ArrayList<AgentVision> agentView = lookAround(agent);
            AgentDecision agentDecision = reactToView(agent, agentView);
            if (agentDecision.agentAction().equals(AgentAction.NONE)) { // Do nothing
                aliveAgentList.add(agent); // Agent is still alive
                return;
            } else if (agentDecision.agentAction().equals(AgentAction.MOVE)) { //Just Move
                environment.setOccupant(agent.getLocation(), null); // Remove agent from old location
                agent.move(agentDecision.location()); // Move to the new location
                environment.setOccupant(agent); // Set the agent to the new location
                aliveAgentList.add(agent); // Agent is still alive
            } else if (agentDecision.agentAction().equals(AgentAction.CREATE)) { // Create children
                ArrayList<Agent> childAgents;
                childAgents = placeAgents(agent.create(agentDecision.location(), environment));
                aliveAgentList.addAll(childAgents); // Add new agents to the alive agents list
                aliveAgentList.add(agent); // Agent is still alive
            } else if (agentDecision.agentAction().equals(AgentAction.GRAZE)) { // Take energy from the environment
                environment.setOccupant(agent.getLocation(), null); // Remove agent from old location
                agent.move(agentDecision.location()); // Move to chosen location
                clearSpace(agent);
                environment.setOccupant(agent); // Set the agent to the new location
                int grazeAmount = -agent.graze(environment.getTile(agent.getLocation())); // Take energy, grazeAmount equals how much was successfully taken
                environment.modifyTileEnergyLevel(agent.getLocation(), grazeAmount); // Update environment with grazeAmount
                aliveAgentList.add(agent); // Agent is still alive
                diagnostics.modifyCurrentEnvironmentEnergy(grazeAmount); // Track the energy change in the diagnostics class
            } else if (agentDecision.agentAction().equals(AgentAction.PREDATE)) { // Take energy from another agent and take its place
                environment.getTile(agentDecision.location()).getOccupant().setSpaceTaken(); // We set the preys hasBeenEaten flag to true
                agent.predate(environment.getTile(agentDecision.location()).getOccupant().getScores()); // Predator gains energy from the prey
                environment.setOccupant(agent.getLocation(), null); // Move to chosen location
                agent.move(agentDecision.location()); // Predator now occupies preys location
                environment.setOccupant(agent); // Overwrite the occupant to the predator
                aliveAgentList.add(agent); // Agent is still alive
            }
            if (agent.isDead()) { // Agent may have exhausted its energy so check again here
                environment.setOccupant(agent.getLocation(), null); // If the agent is now dead, remove it from the board and don't add it to aliveAgentList
            }
        }

        /**
         * Checks if the agents location is occupied, if so it sets the occupants spaceTaken to true.
         *
         * @param agent The agent moving to a new space.
         */
        private void clearSpace(Agent agent) {
            if (environment.getTile(agent.getLocation()).isOccupied()) {
                environment.getTile(agent.getLocation()).getOccupant().setSpaceTaken();
            }
        }

        /**
         * Places agents on the environment.
         * <p>
         * Additionally, checks if the child agents new location is
         * occupied, if so it sets that agents spaceTaken flag to true.
         *
         * @param childAgents the collection of new agents
         */
        private ArrayList<Agent> placeAgents(ArrayList<Agent> childAgents) {
            for (Agent child : childAgents) {
                diagnostics.addToAgentsBornLastStep(child.getAttributes().getID(), 1); // log that a new agents been born
                if (diagnosticsVerbosity >= 1) {
                    diagnostics.addToLogQueue("[AGENT]: " + child.getAttributes().getName() + " born.");
                }
                agentLogic.clearSpace(child);
                environment.setOccupant(child);
            }
            return childAgents;
        }

        /**
         * Returns an AgentVision object of each tile within the agents range.
         * <p>
         * Iterates in a square pattern centered on the agent. A range of 1 means only the 8 adjacent tiles
         * will be looked at. A range of 2 means the surrounding 24 tiles are looked at and so on.
         *
         * @param agent the agent to look around
         */
        private ArrayList<AgentVision> lookAround(Agent agent) {
            Location agentLocation = agent.getLocation();
            int visionRange = agent.getAttributes().getRange();
            ArrayList<AgentVision> agentViews = new ArrayList<>();
            for (int i = -visionRange; i <= visionRange; i++) {
                for (int j = -visionRange; j <= visionRange; j++) {
                    int X = agentLocation.getX() + i;
                    int Y = agentLocation.getY() + j;
                    // Checks the agent isn't looking outside the grid, at its current tile or at terrain.
                    if (((X < environment.getSize())
                            && (Y < environment.getSize()))
                            && ((X >= 0) && (Y >= 0))
                            && !(i == 0 && j == 0)
                            && !environment.getTile(X, Y).isTerrain()) {
                        AgentVision av = environment.getTileView(new Location(X, Y));
                        agentViews.add(av);
                    }
                }
            }
            Collections.shuffle(agentViews);
            return agentViews;
        }

        /**
         * Produces an AgentDecision from a collection of AgentVision objects and the agents motivations.
         * <p>
         * Iterates over each AgentVision object and produces a possible decision for each one. It then calls
         * the getBestDecision() method to get the chosen AgentDecision.
         *
         * @param agent     the agent to look around
         * @param agentView the collection of AgentVision objects
         */
        private static AgentDecision reactToView(Agent agent, ArrayList<AgentVision> agentView) {
            ArrayList<AgentDecision> possibleDecisions = new ArrayList<>();
            for (AgentVision currentAV : agentView) {
                possibleDecisions.add(reactToTile(agent, currentAV));
            }
            return getBestDecision(possibleDecisions);
        }

        /**
         * Produces an AgentDecision from a single AgentVision object and the agents motivations.
         * <p>
         * Iterates over each of the agents motivations and produces a possible decision for each one. It then calls
         * getBestDecision() to return the chosen decision.
         *
         * @param agent       the agent to look around
         * @param agentVision the AgentVision object to produce a decision for
         */
        private static AgentDecision reactToTile(Agent agent, AgentVision agentVision) {
            ArrayList<AgentDecision> possibleDecisions = new ArrayList<>();
            for (Motivation motivation : agent.getMotivations()) {
                possibleDecisions.add(motivation.run(agentVision, agent.getAttributes(), agent.getScores()));
            }
            return getBestDecision(possibleDecisions);
        }

        /**
         * Returns the AgentDecision with the highest score.
         * <p>
         *
         * @param agentDecisions the agent to look around
         */
        private static AgentDecision getBestDecision(ArrayList<AgentDecision> agentDecisions) {
            AgentDecision finalDecision = new AgentDecision(null, AgentAction.NONE, 0);
            Collections.shuffle(agentDecisions);
            for (AgentDecision agentDecision : agentDecisions) {
                if (agentDecision.decisionScore() > finalDecision.decisionScore()) {
                    finalDecision = agentDecision;
                }
            }
            return finalDecision;
        }

        /**
         * Returns a copy of the agent from the agentEditor at the specified index
         * <p>
         *
         * @param index the index of the agent to copy
         */
        public BasicAgent getAgentFromEditor(int index) {
            return (BasicAgent) agentEditor.getAgent(index).copy();
        }
    }

    /**
     * Groups the methods which paint terrain on the environment.
     * <p>
     * Whether a tile is terrain or not is defined by a boolean value. This class contains methods that sets environment tiles
     * terrain flags to true in varying patterns. At the lowest level a circle is generated with a specified size, then using the
     * same algorithm and a Random object, a cluster of circles is generated. A cluster size and density is specified. Then a line
     * of clusters is generated with a line size and  density specified. Finally, a number of lines generated at random positions
     * with random directions are generated. Here only a density is specified. By changing the values, fairly different forms of terrain
     * can be generated.
     *
     * @author Sam Burchmore
     * @version 1.0a
     * @since 1.0a
     */
    public class TerrainGenerator {

        ClusterCircle clusterCircle = new ClusterCircle(new Circle(new Point(), 3), 4, 2500);
        BasicLine basicLine = new BasicLine(new Circle(new Point(), 4), 10, new Direction(1, 1));
        RandomVariableBasicLine randomVariableBasicLine = new RandomVariableBasicLine(clusterCircle, 10, new Direction(1, 1), new Variance(6, 4, 4));
        BendingCompoundLine bendingCompoundLine = new BendingCompoundLine(randomVariableBasicLine, 20, new Direction(6, 6), 10000);

        // The TerrainSettings object that stores the initial and current terrain settings
        private TerrainSettings terrainSettings;
        private final Random random;

        public TerrainGenerator() {
            terrainSettings = new TerrainSettings(2, 5000, 500, 400, 10000, 15, 2, 2, 4, 1, true);
            random = new Random();
        }

        public void paintTerrainMask(Boolean[] terrainMask) {
            for (int i = 0; i < terrainMask.length; i++) {
                environment.getGrid()[i].setTerrain(terrainMask[i]);
            }
        }

        /**
         * Sets all tiles in the environment to terrain.
         */
        public void fillTerrain() {
            for (EnvironmentTile environmentTile :
                    environment.getGrid()) {
                environmentTile.setTerrain(true);
            }
        }

        /**
         * Sets all tiles in the environment to not terrain.
         */
        public void clearTerrain() {
            for (EnvironmentTile environmentTile :
                    environment.getGrid()) {
                environmentTile.setTerrain(false);
            }
        }

        public void paintTerrain(Terrain terrain, boolean paintFlag, Location location) {
            environment = terrain.paint(new TerrainFrame(environment, location), location, paintFlag).getEnvironment();
        }

        public TerrainSettings getTerrainSettings() {
            return terrainSettings;
        }

        public void setTerrainSettings(TerrainSettings terrainSettings) {
            this.terrainSettings = terrainSettings;
        }

        private int getPosNegRandom(int bound) {
            if (random.nextInt(2) == 0) {
                return -random.nextInt(bound);
            }
            return random.nextInt(bound);
        }
    }

    public void setDiagnosticsVerbosity(int diagnosticsVerbosity) {
        this.diagnosticsVerbosity = diagnosticsVerbosity;
    }

    public void updateAgentNames() {
        diagnostics.setAgentNames(agentEditor.getAgentNames());
    }

    public SimulationSettings getSimulationSettings() {
        return new SimulationSettings(
                agentEditor.getActiveAgentsSettings(),
                environment.getEnvironmentSettings(),
                terrainGenerator.getTerrainSettings(),
                environment.getTerrainMask());
    }

    public void setSimulationSettings(SimulationSettings simulationSettings) {
        environment.setEnvironmentSettings(simulationSettings.environmentSettings());
        agentEditor.setActiveAgentsSettings(simulationSettings.agentSettings());
        terrainGenerator.setTerrainSettings(simulationSettings.terrainSettings());
        terrainGenerator.paintTerrainMask(simulationSettings.terrainMask());
    }

    public Environment getEnvironment() {
        return environment;
    }

    public Diagnostics getDiagnostics() {
        return this.diagnostics;
    }

    public AgentEditor getAgentEditor() {
        return agentEditor;
    }

    public TerrainGenerator getTerrainGenerator() {
        return terrainGenerator;
    }

}

