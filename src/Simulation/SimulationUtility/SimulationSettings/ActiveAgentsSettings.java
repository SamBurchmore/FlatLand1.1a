package Simulation.SimulationUtility.SimulationSettings;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * This class represents a collection of agent settings.
 *
 * @author Sam Burchmore
 * @version 1.0a
 * @since 1.0a
 */
public class ActiveAgentsSettings implements Serializable {
    private final ArrayList<AgentSettings> savedAgentSettings;

    public ActiveAgentsSettings(ArrayList<AgentSettings> savedAgentSettings) {
        this.savedAgentSettings = savedAgentSettings;
    }

    public AgentSettings getSavedSettings(int index) {
        return savedAgentSettings.get(index);
    }
}
