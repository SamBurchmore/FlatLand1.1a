package UnitTests;

import Simulation.Agent.AgentConcreteComponents.BasicAgent;
import Simulation.Agent.AgentConcreteComponents.BasicAttributes;
import Simulation.Agent.AgentStructs.ColorModel;
import Simulation.Environment.EnvironmentTile;
import Simulation.Environment.Location;
import org.junit.jupiter.api.Test;

import java.awt.*;

import static org.junit.jupiter.api.Assertions.assertTrue;

class EnvironmentTileTest {

    @Test
    public void testSetTile_IsOccupied() {
        EnvironmentTile environmentTile = new EnvironmentTile(5, 0, 0);
        environmentTile.setOccupant(new BasicAgent(
                new Location(-1, -1),
                new BasicAttributes(0, "agent", 0, Color.blue, ColorModel.STATIC, 0, 0, 1, 3, 4),
                null));
        assertTrue(environmentTile.isOccupied());
    }


}