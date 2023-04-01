package Simulation.SimulationUtility.SimulationSettings;

import java.io.Serializable;

/**
 * This class groups terrain settings.
 *
 * @author Sam Burchmore
 * @version 1.0a
 * @since 1.0a
 */
public record TerrainSettings(int rockSize, int caveDensity, int bendDensity, int caveLength, int caveWeight,
                              int cavernSize, int upperCaveSize, int lowerCaveSize, int caveWave, int caveAmount,
                              boolean isTerrain) implements Serializable {

    @Override
    public String toString() {
        return "rockSize=" + rockSize +
                ",\ncaveDensity=" + caveDensity +
                ",\nbendDensity=" + bendDensity +
                ",\ncaveLength=" + caveLength +
                ",\ncaveWeight=" + caveWeight +
                ",\ncavernSize=" + cavernSize +
                ",\nupperCaveSize=" + upperCaveSize +
                ",\nlowerCaveSize=" + lowerCaveSize +
                ",\ncaveWave=" + caveWave +
                ",\ncaveAmount=" + caveAmount +
                ",\nisTerrain=" + isTerrain;
    }
}
