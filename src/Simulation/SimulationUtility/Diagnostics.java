package Simulation.SimulationUtility;

import java.util.ArrayDeque;

/**
 * This class collects and processes information on the simulation as it runs.
 * @author Sam Burchmore
 * @version 1.0a
 * @since 1.0a
 */
public class Diagnostics {

    final private int activeAgentsNumber = 8;

    // The current step the simulation is on.
    long step;

    // The names of the agents
    private final String[] agentNames;
    // Each agent's current population
    private Integer[] agentPopulations;
    // How many of each agent's where born last step
    private Integer[] agentsBornLastStep;
    // Each agent's total energy
    private Integer[] populationEnergy;
    // Each agent's total lifespan
    private Integer[] populationLifespan;
    // Each agent populations total size
    private Integer[] populationSize;
    // Each agent populations total creation size
    private Integer[] populationCreationSize;
    // Each agent populations total range
    private Integer[] populationRange;
    // The total amount of energy the current environment can hold
    private Integer maxEnvironmentEnergy;
    // The minimum amount of energy the current environment can hold
    private Integer minEnvironmentEnergy;
    // The amount of energy the current environment holds
    private Integer currentEnvironmentEnergy;
    // A register of extinct agents
    private Integer[] extinctFlags;
    // A queue of log messages that will be printed and cleared at the end of every step.
    private final ArrayDeque<String> logQueue;

    public Diagnostics(int maxEnvironmentEnergy, int minEnvironmentEnergy) {
        agentNames = new String[]{"Agent 1", "Agent 2", "Agent 3", "Agent 4", "Agent 5", "Agent 6", "Agent 7", "Agent 8"};
        agentPopulations = new Integer[]{0, 0, 0, 0, 0, 0, 0, 0};
        agentsBornLastStep = new Integer[]{0, 0, 0, 0, 0, 0, 0, 0};
        populationEnergy = new Integer[]{0, 0, 0, 0, 0, 0, 0, 0};
        populationLifespan = new Integer[]{0, 0, 0, 0, 0, 0, 0, 0};
        populationSize = new Integer[]{0, 0, 0, 0, 0, 0, 0, 0};
        populationCreationSize = new Integer[]{0, 0, 0, 0, 0, 0, 0, 0};
        populationRange = new Integer[]{0, 0, 0, 0, 0, 0, 0, 0};
        logQueue = new ArrayDeque<>();
        step = 0;
        extinctFlags = new Integer[]{-1, -1, -1, -1, -1, -1, -1, -1};
        this.maxEnvironmentEnergy = maxEnvironmentEnergy;
        this.minEnvironmentEnergy = minEnvironmentEnergy;
        currentEnvironmentEnergy = maxEnvironmentEnergy;
    }

    /**
     * Resets the steps field along with the agent statistics to 0. This method is the result of the user
     * clearing the board of agents, so environment statistics are not reset here, they are reset when the user
     * replenishes the environment energy.
     */
    public void clearDiagnostics() {
        clearSteps();
        setExtinctFlags(-1);
        clearAgentStats();
    }

    /**
     * Resets every field related to agents to 0.
     */
    public void clearAgentStats() {
        agentPopulations = new Integer[]{0, 0, 0, 0, 0, 0, 0, 0};
        agentsBornLastStep = new Integer[]{0, 0, 0, 0, 0, 0, 0, 0};
        populationEnergy = new Integer[]{0, 0, 0, 0, 0, 0, 0, 0};
        populationLifespan = new Integer[]{0, 0, 0, 0, 0, 0, 0, 0};
        populationSize = new Integer[]{0, 0, 0, 0, 0, 0, 0, 0};
        populationCreationSize = new Integer[]{0, 0, 0, 0, 0, 0, 0, 0};
        populationRange = new Integer[]{0, 0, 0, 0, 0, 0, 0, 0};
    }

    /**
     * Modifies the currentEnvironmentEnergy field by the input parameter. Checks if the result is smaller than 0 or
     */
    public void modifyCurrentEnvironmentEnergy(int modifyValue) {
        this.currentEnvironmentEnergy = Math.min(Math.max(currentEnvironmentEnergy + modifyValue, minEnvironmentEnergy), maxEnvironmentEnergy);
    }

    /**
     * Adds the input string to the logQueue.
     * <p>
     *
     * @param logMsg the string to be queued
     */
    public void addToLogQueue(String logMsg) {
        logQueue.add(logMsg);
    }

    /**
     * Returns a string composed of every log message in the queue, seperated by a newline character.
     */
    public String printLogQueue() {
        StringBuilder logString = new StringBuilder();
        for (String logMsg : logQueue) {
            logString.append(logMsg).append("\n");
        }
        logQueue.clear();
        logString.deleteCharAt(logString.length() - 1);
        return logString.toString();
    }

    /**
     * Returns true if the log queue is not empty.
     */
    public boolean logMessagesInQueue() {
        return !logQueue.isEmpty();
    }

    /**
     * Iterates the step field by 1, checks if an agents have gone extinct and produces log messages for those who have.
     */
    public void iterateStep() {
        if (step == 0) {
            for (int i = 0; i < 8; i++) {
                if (agentPopulations[i] == 0) {
                    extinctFlags[i] = 2;
                }

            }
        }
        step = step + 1;
        boolean extinctionOccurred = false;
        for (int i = 0; i < 8; i++) {
            if (agentPopulations[i] <= 0 && extinctFlags[i] == 0) {
                extinctFlags[i] = 1;
                extinctionOccurred = true;
            }
        }
        if (extinctionOccurred) {
            createExtinctAgentMessages();
        }
    }

    /**
     * Sets every extinctFlag to the input integer.
     * <p>
     *
     * @param flag the integer to set each flag to
     */
    public void setExtinctFlags(int flag) {
        this.extinctFlags = new Integer[]{flag, flag, flag, flag, flag, flag, flag, flag};
    }

    /**
     * Packages the environment statistic fields into an array and returns it.
     * <p>
     *
     * @return an Object array of length 3, with maxEnvironmentEnergy as its first param, currentEnvironmentEnergy as its second,
     * and the percent of currentEnvironmentEnergy compared to maxEnvironmentEnergy as its third param.
     */
    public Object[] getEnvironmentStats() {
        Object[] stats = new Object[3];
        stats[0] = maxEnvironmentEnergy;
        stats[1] = currentEnvironmentEnergy;
        stats[2] = Math.round((currentEnvironmentEnergy / (double) maxEnvironmentEnergy) * 10000) / 100.0;
        return stats;
    }

    /**
     * Adds its parameters to the agent statistic fields.
     * <p>
     *
     * @param index identifies which agents statistics the rest of the params are to be added to
     */
    public void addToAgentStats(int index, int population, int energy, int age, int size, int creationSize, int range) {
        addToAgentPopulation(index, population);
        addToAveragePopulationEnergy(index, energy);
        addToAveragePopulationLifespan(index, age);
        addToAverageSize(index, size);
        addToAverageCreationSize(index, creationSize);
        addToAverageRange(index, range);
    }

    /**
     * Packages the agent statistic fields into an array and returns it.
     * <p>
     *
     * @return a 2-dimensional Object array representing a table of the agents statistic fields,
     * where a row represents all of an agent's statistics. calculateAverages() is called on populationEnergy,
     * populationLifespan, populationSize, populationCreationSize, and populationRange.
     */
    public Object[][] getAgentStats() {
        return new Object[][]{
                agentNames,
                agentPopulations,
                calculateAverages(populationEnergy),
                calculateAverages(populationLifespan),
                agentsBornLastStep,
                calculateAverages(populationSize),
                calculateAverages(populationCreationSize),
                calculateAverages(populationRange)};
    }

    /**
     * Sets the current environment energy to the maximum energy.
     */
    public void resetCurrentEnvironmentEnergy() {
        currentEnvironmentEnergy = maxEnvironmentEnergy;
    }

    /**
     * Sets the agent names to the input string array.
     * <p>
     *
     * @param agentNames the new names for the agents.
     */
    public void setAgentNames(String[] agentNames) {
        for (int i = 0; i < activeAgentsNumber; i++) {
            setAgentName(i, agentNames[i]);
        }
    }

    /**
     * Produces and logs an info log message for each agent with an extinct flag of 1. The flag is then set
     * to 2 which identifies that this agent is extinct, to prevent additional extinction messages from being
     * generated.
     * <p>
     */
    private void createExtinctAgentMessages() {
        for (int i = 0; i < activeAgentsNumber; i++) {
            if (extinctFlags[i] == 1) {
                extinctFlags[i] = 2;
                addToLogQueue("[AGENT]: " + agentNames[i] + " has gone extinct at step " + step);
            }
        }
    }

    /**
     * Calculates the average of each agent statistic in the input array and returns the results
     * as an array of doubles.
     * <p>
     *
     * @param statistics the array of statistics the averages are to be calculated from
     * @return averages the array of calculated averages
     */
    private Double[] calculateAverages(Integer[] statistics) {
        Double[] averages = new Double[statistics.length];
        for (int i = 0; i < 8; i++) {
            averages[i] = Math.round((statistics[i] / (double) agentPopulations[i]) * 10) / 10.0;
        }
        return averages;
    }

    public void setMaxEnvironmentEnergy(int maxEnvironmentEnergy) {
        this.maxEnvironmentEnergy = maxEnvironmentEnergy;
    }

    public void setMinEnvironmentEnergy(int minEnvironmentEnergy) {
        this.minEnvironmentEnergy = minEnvironmentEnergy;
    }

    public long getStep() {
        return step;
    }

    public Integer getCurrentEnvironmentEnergy() {
        return currentEnvironmentEnergy;
    }

    private void clearSteps() {
        step = 0;
    }

    public void setAgentName(int index, String name) {
        agentNames[index] = name;
    }

    public void addToAgentPopulation(int index, int newAgents) {
        agentPopulations[index] += newAgents;
    }

    public void addToAgentsBornLastStep(int index, int newAgents) {
        agentsBornLastStep[index] += newAgents;
    }

    public void addToAveragePopulationEnergy(int index, int energy) {
        populationEnergy[index] += energy;
    }

    public void addToAveragePopulationLifespan(int index, int age) {
        populationLifespan[index] += age;
    }

    public void addToAverageSize(int index, int age) {
        populationSize[index] += age;
    }

    public void addToAverageCreationSize(int index, int age) {
        populationCreationSize[index] += age;
    }

    public void addToAverageRange(int index, int age) {
        populationRange[index] += age;
    }

}
