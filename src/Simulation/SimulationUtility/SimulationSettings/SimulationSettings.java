package Simulation.SimulationUtility.SimulationSettings;

import java.io.Serializable;

/**
 * This class groups all the settings objects into one setting for the entire simulation.
 *
 * @author Sam Burchmore
 * @version 1.0a
 * @since 1.0a
 */
public record SimulationSettings(ActiveAgentsSettings agentSettings, EnvironmentSettings environmentSettings,
                                 TerrainSettings terrainSettings, Boolean[] terrainMask) implements Serializable {

}
