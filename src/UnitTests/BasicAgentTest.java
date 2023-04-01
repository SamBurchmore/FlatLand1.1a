package UnitTests;
import Simulation.Agent.AgentConcreteComponents.BasicAgent;
import Simulation.Agent.AgentConcreteComponents.BasicAttributes;
import Simulation.Agent.AgentInterfaces.Agent;
import Simulation.Agent.AgentStructs.ColorModel;
import Simulation.Environment.EnvironmentTile;
import Simulation.Environment.Location;
import org.junit.jupiter.api.Test;

import java.awt.*;

import static org.junit.jupiter.api.Assertions.*;

public class BasicAgentTest {

    @Test
    public void testLiveDay() {
        Agent agent0 = new BasicAgent(new Location(0, 0), new BasicAttributes(0, "0", 0, Color.blue, ColorModel.STATIC, 0, 0, 1, 3, 4), null);
        agent0.liveDay();
        assertEquals(1, agent0.getScores().getAge());
        assertEquals(0, agent0.getScores().getCreationCounter());
    }

    @Test
    public void testIsDead() {
        Agent agent0 = new BasicAgent(new Location(0, 0), new BasicAttributes(0, "0", 0, Color.blue, ColorModel.STATIC, 0, 0, 1, 3, 4), null);
        agent0.getScores().setAge(agent0.getScores().getMaxAge());
        assertTrue(agent0.isDead());
    }

    @Test
    public void testMove1Straight() {
        Agent agent0 = new BasicAgent(new Location(0, 0), new BasicAttributes(0, "0", 0, Color.blue, ColorModel.STATIC, 0, 0, 1, 3, 4), null);
        agent0.move(new Location(1, 0));
        assertEquals(13, agent0.getScores().getEnergy());
        assertEquals(new Location(1, 0), agent0.getLocation());
    }

    @Test
    public void testMove2Straight() {
        Agent agent0 = new BasicAgent(new Location(0, 0), new BasicAttributes(0, "0", 0, Color.blue, ColorModel.STATIC, 0, 0, 1, 3, 4), null);
        agent0.move(new Location(2, 0));
        assertEquals(11, agent0.getScores().getEnergy());
        assertEquals(new Location(2, 0), agent0.getLocation());
    }

    @Test
    public void testMove3Straight() {
        Agent agent0 = new BasicAgent(new Location(0, 0), new BasicAttributes(0, "0", 0, Color.blue, ColorModel.STATIC, 0, 0, 1, 3, 4), null);
        agent0.move(new Location(3, 0));
        assertEquals(9, agent0.getScores().getEnergy());
        assertEquals(new Location(3, 0), agent0.getLocation());
    }

    @Test
    public void testMove1Diagonal() {
        Agent agent0 = new BasicAgent(new Location(0, 0), new BasicAttributes(0, "0", 0, Color.blue, ColorModel.STATIC, 0, 0, 1, 3, 4), null);
        agent0.move(new Location(1, 1));
        assertEquals(13, agent0.getScores().getEnergy());
        assertEquals(new Location(1, 1), agent0.getLocation());
    }

    @Test
    public void testMove2Diagonal() {
        Agent agent0 = new BasicAgent(new Location(0, 0), new BasicAttributes(0, "0", 0, Color.blue, ColorModel.STATIC, 0, 0, 1, 3, 4), null);
        agent0.move(new Location(2, 2));
        assertEquals(11, agent0.getScores().getEnergy());
        assertEquals(new Location(2, 2), agent0.getLocation());
    }

    @Test
    public void testMove3Diagonal() {
        Agent agent0 = new BasicAgent(new Location(0, 0), new BasicAttributes(0, "0", 0, Color.blue, ColorModel.STATIC, 0, 0, 1, 3, 4), null);
        agent0.move(new Location(3, 3));
        assertEquals(7, agent0.getScores().getEnergy());
        assertEquals(new Location(3, 3), agent0.getLocation());
    }

    @Test
    public void testGraze() {
        Agent agent0 = new BasicAgent(new Location(0, 0), new BasicAttributes(0, "0", 0, Color.blue, ColorModel.STATIC, 0, 0, 1, 3, 4), null);
        EnvironmentTile environmentTile = new EnvironmentTile(5, 0, 0);
        agent0.getScores().setEnergy(11);
        assertEquals(11, agent0.getScores().getEnergy());
        int eatAmount = agent0.graze(environmentTile);
        assertEquals(4, eatAmount);
        assertEquals(15, agent0.getScores().getEnergy());
    }

    @Test
    public void testPredate() {
        Agent agent0 = new BasicAgent(new Location(0, 0), new BasicAttributes(0, "0", 0, Color.blue, ColorModel.STATIC, 0, 0, 1, 3, 4), null);
        Agent agent1 = new BasicAgent(new Location(0, 0), new BasicAttributes(0, "0", 0, Color.blue, ColorModel.STATIC, 0, 0, 1, 2, 4), null);
        agent0.getScores().setEnergy(5);
        assertEquals(5, agent0.getScores().getEnergy());
        agent0.predate(agent1.getScores());
        assertEquals(15, agent0.getScores().getEnergy());
    }
}
