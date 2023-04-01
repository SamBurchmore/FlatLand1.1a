package Simulation.SimulationUtility;

import Simulation.Agent.AgentConcreteComponents.BasicAgent;
import Simulation.Agent.AgentConcreteComponents.CreatorMotivation;
import Simulation.Agent.AgentConcreteComponents.GrazerMotivation;
import Simulation.Agent.AgentConcreteComponents.MutatingAttributes;
import Simulation.Agent.AgentInterfaces.Agent;
import Simulation.Agent.AgentInterfaces.Motivation;
import Simulation.Agent.AgentStructs.ColorModel;
import Simulation.Environment.Location;
import Simulation.SimulationUtility.SimulationSettings.ActiveAgentsSettings;
import Simulation.SimulationUtility.SimulationSettings.AgentSettings;

import java.awt.*;
import java.util.ArrayList;

/**
 * This class stores a collection of 8 agents. It provides methods to update and retrieve these agents settings.
 * Additionally, it contains an integer which represents which of its agents is currently open.
 *
 * @author Sam Burchmore
 * @version 1.0a
 * @since 1.0a
 */
public class AgentEditor {

    private final int AGENT_NUMBER = 8;
    final private ArrayList<Agent> activeAgents;
    private int editingAgentIndex;

    /**
     * Constructs 8 default agents.
     */
    public AgentEditor() {
        activeAgents = new ArrayList<>();
        ArrayList<Motivation> motivations = new ArrayList<>();
        motivations.add(new CreatorMotivation(20, 1));
        motivations.add(new GrazerMotivation(10, 1));
        for (int i = 0; i < 8; i++) {
            activeAgents.add(i, new BasicAgent(
                    new Location(-1, -1),
                    new MutatingAttributes(100, "Agent " + (i + 1), i, Color.blue, ColorModel.STATIC, 5, 0, 1, 3, 4),
                    (ArrayList<Motivation>) motivations.clone()));
        }
        editingAgentIndex = 0;
    }

    /**
     * @return the settings of the currently open agent.
     */
    public AgentSettings getEditingAgentSettings() {
        return new AgentSettings(activeAgents.get(editingAgentIndex).getAttributes(), activeAgents.get(editingAgentIndex).getMotivations());
    }

    /**
     * @return the settings of the specified agent.
     */
    public AgentSettings getAgentSettings(int index) {
        return new AgentSettings(activeAgents.get(index).getAttributes(), activeAgents.get(index).getMotivations());
    }

    /**
     * Updates the currently open agents settings to the input settings.
     */
    public void setEditingAgentSettings(AgentSettings agentSettings) {
        getAgent(editingAgentIndex).setAttributes(agentSettings.getAttributes());
        getAgent(editingAgentIndex).setMotivations(agentSettings.getMotivations());
    }

    /**
     * Updates the specified agents settings to the input settings.
     */
    public void setAgentSettings(AgentSettings agentSettings, int index) {
        getAgent(index).setAttributes(agentSettings.getAttributes());
        getAgent(index).setMotivations(agentSettings.getMotivations());
    }

    /**
     * Packages and returns the active agents names in an array.
     */
    public String[] getAgentNames() {
        String[] names = new String[8];
        for (int i = 0; i < AGENT_NUMBER; i++) {
            names[i] = activeAgents.get(i).getAttributes().getName();
        }
        return names;
    }

    /**
     * Returns the sum of the agents weights.
     */
    public int getWeight() {
        int weight = 0;
        for (Agent agent : activeAgents) {
            weight += agent.getAttributes().getSpawningWeight();
        }
        return weight;
    }

    /**
     * Creates and returns an ActiveAgentsSettings object from the active agents.
     */
    public ActiveAgentsSettings getActiveAgentsSettings() {
        ArrayList<AgentSettings> activeAgentSettings = new ArrayList<>();
        for (int i = 0; i < AGENT_NUMBER; i++) {
            activeAgentSettings.add(i, getAgentSettings(i));
        }
        return new ActiveAgentsSettings(activeAgentSettings);
    }

    /**
     * Updates the active agents settings with those from the input ActiveAgentsSettings.
     */
    public void setActiveAgentsSettings(ActiveAgentsSettings activeAgentsSettings) {
        for (int i = 0; i < AGENT_NUMBER; i++) {
            setAgentSettings(activeAgentsSettings.getSavedSettings(i), i);
        }
    }

    public void setEditingAgentIndex(int index) {
        editingAgentIndex = index;
    }

    public int getEditingAgentIndex() {
        return editingAgentIndex;
    }

    public ArrayList<Agent> getActiveAgents() {
        return activeAgents;
    }

    public Agent getAgent(int index) {
        return activeAgents.get(index);
    }
}
