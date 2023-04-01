package UnitTests;

import Simulation.Agent.AgentConcreteComponents.BasicAgent;
import Simulation.Agent.AgentConcreteComponents.BasicAttributes;
import Simulation.Agent.AgentInterfaces.Agent;
import Simulation.Agent.AgentStructs.AgentVision;
import Simulation.Agent.AgentStructs.ColorModel;
import Simulation.Environment.Environment;
import Simulation.Environment.Location;
import org.junit.jupiter.api.Test;

import java.awt.*;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class EnvironmentTest {

    @Test
    public void testGetSetTile() {
        Location location0 = new Location(0, 0);
        Location location1 = new Location(0, 1);
        Location location2 = new Location(1, 0);
        Location location3 = new Location(1, 1);
        Agent agent0 = new BasicAgent(location0, new BasicAttributes(0, "0", 0, Color.blue, ColorModel.STATIC, 0, 0, 1, 3, 4), null);
        Agent agent1 = new BasicAgent(location1, new BasicAttributes(0, "1", 1, Color.blue, ColorModel.STATIC, 0, 0, 1, 3, 4), null);
        Agent agent2 = new BasicAgent(location2, new BasicAttributes(0, "2", 2, Color.blue, ColorModel.STATIC, 0, 0, 1, 3, 4), null);
        Agent agent3 = new BasicAgent(location3, new BasicAttributes(0, "3", 3, Color.blue, ColorModel.STATIC, 0, 0, 1, 3, 4), null);
        Environment environment = new Environment(2, 5, 5, 0, 1, 1);
        environment.setOccupant(agent0);
        environment.setOccupant(agent1);
        environment.setOccupant(agent2);
        environment.setOccupant(agent3);
        assertEquals(0, (int) environment.getTile(location0).getOccupant().getAttributes().getID());
        assertEquals(1, (int) environment.getTile(location1).getOccupant().getAttributes().getID());
        assertEquals(2, (int) environment.getTile(location2).getOccupant().getAttributes().getID());
        assertEquals(3, (int) environment.getTile(location3).getOccupant().getAttributes().getID());
    }

    @Test
    public void testFreeSpace1() {
        Location location0 = new Location(0, 0);
        Location location1 = new Location(0, 1);
        Location location2 = new Location(0, 2);
        Location location3 = new Location(1, 0);
        Location location4_center = new Location(1, 1);
        Location location5 = new Location(1, 2);
        Agent agent0 = new BasicAgent(location0, new BasicAttributes(0, "0", 0, Color.blue, ColorModel.STATIC, 0, 0, 1, 3, 4), null);
        Agent agent1 = new BasicAgent(location1, new BasicAttributes(0, "1", 1, Color.blue, ColorModel.STATIC, 0, 0, 1, 3, 4), null);
        Agent agent2 = new BasicAgent(location2, new BasicAttributes(0, "2", 2, Color.blue, ColorModel.STATIC, 0, 0, 1, 3, 4), null);
        Agent agent3 = new BasicAgent(location3, new BasicAttributes(0, "3", 3, Color.blue, ColorModel.STATIC, 0, 0, 1, 3, 4), null);
        Agent agent4 = new BasicAgent(location4_center, new BasicAttributes(0, "4", 4, Color.blue, ColorModel.STATIC, 0, 0, 1, 3, 4), null);
        Agent agent5 = new BasicAgent(location5, new BasicAttributes(0, "5", 5, Color.blue, ColorModel.STATIC, 0, 0, 1, 3, 4), null);
        Environment environment = new Environment(3, 5, 5, 0, 1, 1);
        environment.setOccupant(agent0);
        environment.setOccupant(agent1);
        environment.setOccupant(agent2);
        environment.setOccupant(agent3);
        environment.setOccupant(agent4);
        environment.setOccupant(agent5);
        ArrayList<Location> freeSpace = environment.freeSpace(location4_center, 1);
        assertEquals(freeSpace.size(), 3);
    }

    @Test
    public void testFreeSpace2() {
        Location location0 = new Location(0, 0);
        Location location1 = new Location(0, 1);
        Location location2 = new Location(0, 2);
        Location location3 = new Location(1, 0);
        Location location4_center = new Location(1, 1);
        Location location5 = new Location(1, 2);
        Agent agent0 = new BasicAgent(location0, new BasicAttributes(0, "0", 0, Color.blue, ColorModel.STATIC, 0, 0, 1, 3, 4), null);
        Agent agent1 = new BasicAgent(location1, new BasicAttributes(0, "1", 1, Color.blue, ColorModel.STATIC, 0, 0, 1, 3, 4), null);
        Agent agent2 = new BasicAgent(location2, new BasicAttributes(0, "2", 2, Color.blue, ColorModel.STATIC, 0, 0, 1, 3, 4), null);
        Agent agent3 = new BasicAgent(location3, new BasicAttributes(0, "3", 3, Color.blue, ColorModel.STATIC, 0, 0, 1, 3, 4), null);
        Agent agent4 = new BasicAgent(location4_center, new BasicAttributes(0, "4", 4, Color.blue, ColorModel.STATIC, 0, 0, 1, 3, 4), null);
        Agent agent5 = new BasicAgent(location5, new BasicAttributes(0, "5", 5, Color.blue, ColorModel.STATIC, 0, 0, 1, 3, 4), null);
        Environment environment = new Environment(3, 5, 5, 0, 1, 1);
        environment.setOccupant(agent0);
        environment.setOccupant(agent1);
        environment.setOccupant(agent2);
        environment.setOccupant(agent3);
        environment.setOccupant(agent4);
        environment.setOccupant(agent5);
        ArrayList<Location> freeSpace = environment.freeSpace(location4_center, 4);
        assertEquals(freeSpace.size(), 8);
    }

    @Test
    public void testModifyTileEnergyMin0() {
        Location location = new Location(0, 0);
        Environment environment = new Environment(2, 0, 5, 0, 1, 5);
        assertEquals(1, environment.modifyTileEnergyLevel(location, 1));
        assertEquals(4, environment.modifyTileEnergyLevel(location, 4));
        assertEquals(0, environment.modifyTileEnergyLevel(location, 1));
        assertEquals(-1, environment.modifyTileEnergyLevel(location, -1));
        assertEquals(-4, environment.modifyTileEnergyLevel(location, -4));
        assertEquals(0, environment.modifyTileEnergyLevel(location, -1));
    }

    @Test
    public void testModifyTileEnergyMin2() {
        Location location = new Location(0, 0);
        Environment environment = new Environment(2, 2, 6, 2, 1, 5);
        assertEquals(1, environment.modifyTileEnergyLevel(location, 1));
        assertEquals(3, environment.modifyTileEnergyLevel(location, 3));
        assertEquals(0, environment.modifyTileEnergyLevel(location, 1));
        assertEquals(-4, environment.modifyTileEnergyLevel(location, -4));
        assertEquals(0, environment.modifyTileEnergyLevel(location, -1));
    }

    @Test
    public void testGetTileView() {
        Location locationOccupied = new Location(0, 0);
        Location locationUnoccupied = new Location(0, 1);
        Environment environment = new Environment(2, 2, 6, 2, 1, 5);
        Agent agent0 = new BasicAgent(locationOccupied, new BasicAttributes(0, "0", 0, Color.blue, ColorModel.STATIC, 0, 0, 1, 3, 4), null);
        environment.setOccupant(agent0);
        AgentVision agentVisionOccupied = environment.getTileView(locationOccupied);
        AgentVision agentVisionUnoccupied = environment.getTileView(locationUnoccupied);
        assertTrue(agentVisionOccupied.isOccupied());
        assertFalse(agentVisionUnoccupied.isOccupied());
    }

    @Test
    public void testGetTileColorOccupied() {
        Location location0 = new Location(0, 0);
        Location location1 = new Location(0, 1);
        Location location2 = new Location(1, 0);
        Location location3 = new Location(1, 1);
        Location location4 = new Location(0, 2);
        Environment environment = new Environment(3, 0, 12, 0, 1, 5);
        Agent agent0 = new BasicAgent(location0, new BasicAttributes(0, "0", 0, Color.blue, ColorModel.STATIC, 0, 0, 1, 3, 4), null);
        Agent agent1 = new BasicAgent(location1, new BasicAttributes(0, "1", 1, Color.red, ColorModel.STATIC, 0, 0, 1, 3, 4), null);
        Agent agent2 = new BasicAgent(location2, new BasicAttributes(0, "2", 2, Color.green, ColorModel.STATIC, 0, 0, 1, 3, 4), null);
        Agent agent3 = new BasicAgent(location3, new BasicAttributes(0, "3", 3, Color.yellow, ColorModel.STATIC, 0, 0, 1, 3, 4), null);
        environment.setOccupant(agent0);
        environment.setOccupant(agent1);
        environment.setOccupant(agent2);
        environment.setOccupant(agent3);
        assertEquals(Color.blue, environment.getTileColor(location0));
        assertEquals(Color.red, environment.getTileColor(location1));
        assertEquals(Color.green, environment.getTileColor(location2));
        assertEquals(Color.yellow, environment.getTileColor(location3));
    }

    @Test
    public void testGetTileColorUnoccupied() {
        Location location = new Location(0, 2);
        Environment environment = new Environment(3, 0, 12, 0, 1, 5);

        assertEquals(environment.getColors()[0], environment.getTileColor(location));

        environment.modifyTileEnergyLevel(location, 2);
        assertEquals(environment.getColors()[1], environment.getTileColor(location));

        environment.modifyTileEnergyLevel(location, 2);
        assertEquals(environment.getColors()[2], environment.getTileColor(location));

        environment.modifyTileEnergyLevel(location, 2);
        assertEquals(environment.getColors()[3], environment.getTileColor(location));

        environment.modifyTileEnergyLevel(location, 2);
        assertEquals(environment.getColors()[3], environment.getTileColor(location));

        environment.modifyTileEnergyLevel(location, 2);
        assertEquals(environment.getColors()[4], environment.getTileColor(location));

        environment.modifyTileEnergyLevel(location, 2);
        assertEquals(environment.getColors()[5], environment.getTileColor(location));
    }

    @Test
    public void testGetTileColorTerrain() {
        Location location = new Location(0, 2);
        Environment environment = new Environment(3, 0, 12, 0, 1, 5);
        environment.setTileTerrain(location, true);
        assertEquals(environment.getColors()[6], environment.getTileColor(location));
    }

}