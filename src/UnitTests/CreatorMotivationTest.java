package UnitTests;

import Simulation.Agent.AgentConcreteComponents.BasicAgent;
import Simulation.Agent.AgentConcreteComponents.BasicAttributes;
import Simulation.Agent.AgentConcreteComponents.CreatorMotivation;
import Simulation.Agent.AgentInterfaces.Attributes;
import Simulation.Agent.AgentInterfaces.Motivation;
import Simulation.Agent.AgentStructs.AgentAction;
import Simulation.Agent.AgentStructs.AgentDecision;
import Simulation.Agent.AgentStructs.AgentVision;
import Simulation.Agent.AgentStructs.ColorModel;
import Simulation.Environment.Location;
import org.junit.jupiter.api.Test;

import java.awt.*;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class CreatorMotivationTest {

    @Test
    void runOccupantIsValidMate() {
        CreatorMotivation creatorMotivation = new CreatorMotivation(10, 2);
        ArrayList<Motivation> motivations = new ArrayList<>();
        motivations.add(creatorMotivation);

        // First set up a compatible pair, we will use this configuration for the rest of the tests, modifying only the aspect we are testing.
        // This should ensure we are testing only that aspect.
        BasicAgent testAgent = new BasicAgent(
                new Location(0, 0),
                new BasicAttributes(0, "BlueSize=3", 0, Color.blue, ColorModel.STATIC, 0, 0, 1, 3, 4),
                (ArrayList<Motivation>) motivations.clone());
        testAgent.getAttributes().calculateAttributes();
        testAgent.getScores().setAge(6);

        BasicAgent potentialMate = new BasicAgent(
                new Location(0, 0),
                new BasicAttributes(0, "BlueSize=3", 0, Color.blue, ColorModel.STATIC, 0, 0, 1, 3, 4),
                (ArrayList<Motivation>) motivations.clone());
        potentialMate.getAttributes().calculateAttributes();
        potentialMate.getScores().setAge(6);

        AgentVision occupiedAgentVision = new AgentVision(8, true, new Location(1, 1), potentialMate.getAttributes(), potentialMate.getScores());
        int desiredScore = 20;
        AgentDecision agentDecision = testAgent.getMotivations().get(0).run(occupiedAgentVision, testAgent.getAttributes(), testAgent.getScores());
        assertEquals(agentDecision.decisionScore(), desiredScore);
        assertEquals(agentDecision.agentAction(), AgentAction.CREATE);
    }

    @Test
    void runOccupiedDifferentID() {
        CreatorMotivation creatorMotivation = new CreatorMotivation(10, 2);
        ArrayList<Motivation> motivations = new ArrayList<>();
        motivations.add(creatorMotivation);

        BasicAgent testAgent = new BasicAgent(
                new Location(0, 0),
                new BasicAttributes(0, "BlueSize=3", 0, Color.blue, ColorModel.STATIC, 0, 0, 1, 3, 4),
                (ArrayList<Motivation>) motivations.clone());
        testAgent.getAttributes().calculateAttributes();
        testAgent.getScores().setAge(6);

        BasicAgent potentialMate = new BasicAgent(
                new Location(0, 0),
                new BasicAttributes(0, "BlueSize=3", 0, Color.blue, ColorModel.STATIC, 0, 0, 1, 3, 4),
                (ArrayList<Motivation>) motivations.clone());
        potentialMate.getAttributes().calculateAttributes();
        potentialMate.getScores().setAge(6);
        potentialMate.getAttributes().setID(1); // Agents should no longer be compatible

        AgentVision occupiedAgentVision = new AgentVision(8, true, new Location(1, 1), potentialMate.getAttributes(), potentialMate.getScores());
        int desiredScore = -1;
        AgentDecision agentDecision = testAgent.getMotivations().get(0).run(occupiedAgentVision, testAgent.getAttributes(), testAgent.getScores());
        assertEquals(agentDecision.decisionScore(), desiredScore);
        assertEquals(agentDecision.agentAction(), AgentAction.NONE);
    }

    @Test
    void runOccupiedTestSubjectCCAbove0() {
        CreatorMotivation creatorMotivation = new CreatorMotivation(10, 2);
        ArrayList<Motivation> motivations = new ArrayList<>();
        motivations.add(creatorMotivation);

        BasicAgent testAgent = new BasicAgent(
                new Location(0, 0),
                new BasicAttributes(0, "BlueSize=3", 0, Color.blue, ColorModel.STATIC, 0, 0, 1, 3, 4),
                (ArrayList<Motivation>) motivations.clone());
        testAgent.getAttributes().calculateAttributes();
        testAgent.getScores().setAge(6);
        testAgent.getScores().setCreationCounter(1); // Agents should no longer be compatible

        BasicAgent potentialMate = new BasicAgent(
                new Location(0, 0),
                new BasicAttributes(0, "BlueSize=3", 0, Color.blue, ColorModel.STATIC, 0, 0, 1, 3, 4),
                (ArrayList<Motivation>) motivations.clone());
        potentialMate.getAttributes().calculateAttributes();
        potentialMate.getScores().setAge(6);

        AgentVision occupiedAgentVision = new AgentVision(8, true, new Location(1, 1), potentialMate.getAttributes(), potentialMate.getScores());
        int desiredScore = -1;
        AgentDecision agentDecision = testAgent.getMotivations().get(0).run(occupiedAgentVision, testAgent.getAttributes(), testAgent.getScores());
        assertEquals(agentDecision.decisionScore(), desiredScore);
        assertEquals(agentDecision.agentAction(), AgentAction.NONE);
    }

    @Test
    void runOccupiedTestSubjectAgeBelowCA() {
        CreatorMotivation creatorMotivation = new CreatorMotivation(10, 2);
        ArrayList<Motivation> motivations = new ArrayList<>();
        motivations.add(creatorMotivation);

        BasicAgent testAgent = new BasicAgent(
                new Location(0, 0),
                new BasicAttributes(0, "BlueSize=3", 0, Color.blue, ColorModel.STATIC, 0, 0, 1, 3, 4),
                (ArrayList<Motivation>) motivations.clone());
        testAgent.getAttributes().calculateAttributes();
        testAgent.getScores().setAge(5); // Agents should no longer be compatible

        BasicAgent potentialMate = new BasicAgent(
                new Location(0, 0),
                new BasicAttributes(0, "BlueSize=3", 0, Color.blue, ColorModel.STATIC, 0, 0, 1, 3, 4),
                (ArrayList<Motivation>) motivations.clone());
        potentialMate.getAttributes().calculateAttributes();
        potentialMate.getScores().setAge(6);

        AgentVision occupiedAgentVision = new AgentVision(8, true, new Location(1, 1), potentialMate.getAttributes(), potentialMate.getScores());
        int desiredScore = -1;
        AgentDecision agentDecision = testAgent.getMotivations().get(0).run(occupiedAgentVision, testAgent.getAttributes(), testAgent.getScores());
        assertEquals(agentDecision.decisionScore(), desiredScore);
        assertEquals(agentDecision.agentAction(), AgentAction.NONE);
    }

    @Test
    void runOccupiedTestSubjectEneBelowQuarter() {
        CreatorMotivation creatorMotivation = new CreatorMotivation(10, 2);
        ArrayList<Motivation> motivations = new ArrayList<>();
        motivations.add(creatorMotivation);

        BasicAgent testAgent = new BasicAgent(
                new Location(0, 0),
                new BasicAttributes(0, "BlueSize=3", 0, Color.blue, ColorModel.STATIC, 0, 0, 1, 3, 4),
                (ArrayList<Motivation>) motivations.clone());
        testAgent.getAttributes().calculateAttributes();
        testAgent.getScores().setAge(6);
        testAgent.getScores().setEnergy((testAgent.getAttributes().getEnergyCapacity() / 4) - 1); // Agents should no longer be compatible

        BasicAgent potentialMate = new BasicAgent(
                new Location(0, 0),
                new BasicAttributes(0, "BlueSize=3", 0, Color.blue, ColorModel.STATIC, 0, 0, 1, 3, 4),
                (ArrayList<Motivation>) motivations.clone());
        potentialMate.getAttributes().calculateAttributes();
        potentialMate.getScores().setAge(6);

        AgentVision occupiedAgentVision = new AgentVision(8, true, new Location(1, 1), potentialMate.getAttributes(), potentialMate.getScores());
        int desiredScore = -1;
        AgentDecision agentDecision = testAgent.getMotivations().get(0).run(occupiedAgentVision, testAgent.getAttributes(), testAgent.getScores());
        assertEquals(agentDecision.decisionScore(), desiredScore);
        assertEquals(agentDecision.agentAction(), AgentAction.NONE);
    }

    @Test
    void runOccupiedCCAbove0() {
        CreatorMotivation creatorMotivation = new CreatorMotivation(10, 2);
        ArrayList<Motivation> motivations = new ArrayList<>();
        motivations.add(creatorMotivation);

        BasicAgent testAgent = new BasicAgent(
                new Location(0, 0),
                new BasicAttributes(0, "BlueSize=3", 0, Color.blue, ColorModel.STATIC, 0, 0, 1, 3, 4),
                (ArrayList<Motivation>) motivations.clone());
        testAgent.getAttributes().calculateAttributes();
        testAgent.getScores().setAge(6);

        BasicAgent potentialMate = new BasicAgent(
                new Location(0, 0),
                new BasicAttributes(0, "BlueSize=3", 0, Color.blue, ColorModel.STATIC, 0, 0, 1, 3, 4),
                (ArrayList<Motivation>) motivations.clone());
        potentialMate.getAttributes().calculateAttributes();
        potentialMate.getScores().setAge(6);
        potentialMate.getScores().setCreationCounter(1); // Agents should no longer be compatible

        AgentVision occupiedAgentVision = new AgentVision(8, true, new Location(1, 1), potentialMate.getAttributes(), potentialMate.getScores());
        int desiredScore = -1;
        AgentDecision agentDecision = testAgent.getMotivations().get(0).run(occupiedAgentVision, testAgent.getAttributes(), testAgent.getScores());
        assertEquals(agentDecision.decisionScore(), desiredScore);
        assertEquals(agentDecision.agentAction(), AgentAction.NONE);
    }

    @Test
    void runOccupiedAgeBelowCA() {
        CreatorMotivation creatorMotivation = new CreatorMotivation(10, 2);
        ArrayList<Motivation> motivations = new ArrayList<>();
        motivations.add(creatorMotivation);

        BasicAgent testAgent = new BasicAgent(
                new Location(0, 0),
                new BasicAttributes(0, "BlueSize=3", 0, Color.blue, ColorModel.STATIC, 0, 0, 1, 3, 4),
                (ArrayList<Motivation>) motivations.clone());
        testAgent.getAttributes().calculateAttributes();
        testAgent.getScores().setAge(6);

        BasicAgent potentialMate = new BasicAgent(
                new Location(0, 0),
                new BasicAttributes(0, "BlueSize=3", 0, Color.blue, ColorModel.STATIC, 0, 0, 1, 3, 4),
                (ArrayList<Motivation>) motivations.clone());
        potentialMate.getAttributes().calculateAttributes();
        potentialMate.getScores().setAge(5); // Agents should no longer be compatible

        AgentVision occupiedAgentVision = new AgentVision(8, true, new Location(1, 1), potentialMate.getAttributes(), potentialMate.getScores());
        int desiredScore = -1;
        AgentDecision agentDecision = testAgent.getMotivations().get(0).run(occupiedAgentVision, testAgent.getAttributes(), testAgent.getScores());
        assertEquals(agentDecision.decisionScore(), desiredScore);
        assertEquals(agentDecision.agentAction(), AgentAction.NONE);
    }

    @Test
    void runOccupiedCompareAttributesFalse() {
        CreatorMotivation creatorMotivation = new CreatorMotivation(10, 2);
        ArrayList<Motivation> motivations = new ArrayList<>();
        motivations.add(creatorMotivation);

        BasicAgent testAgent = new BasicAgent(
                new Location(0, 0),
                new BasicAttributes(0, "BlueSize=3", 0, Color.blue, ColorModel.STATIC, 0, 0, 1, 3, 4),
                (ArrayList<Motivation>) motivations.clone());
        testAgent.getAttributes().calculateAttributes();
        testAgent.getScores().setAge(6);
        testAgent.getAttributes().setSize(14); // Agents should no longer be compatible

        BasicAgent potentialMate = new BasicAgent(
                new Location(0, 0),
                new BasicAttributes(0, "BlueSize=3", 0, Color.blue, ColorModel.STATIC, 0, 0, 1, 3, 4),
                (ArrayList<Motivation>) motivations.clone());
        potentialMate.getAttributes().calculateAttributes();
        potentialMate.getScores().setAge(6);

        AgentVision occupiedAgentVision = new AgentVision(8, true, new Location(1, 1), potentialMate.getAttributes(), potentialMate.getScores());
        int desiredScore = -1;
        AgentDecision agentDecision = testAgent.getMotivations().get(0).run(occupiedAgentVision, testAgent.getAttributes(), testAgent.getScores());
        assertEquals(agentDecision.decisionScore(), desiredScore);
        assertEquals(agentDecision.agentAction(), AgentAction.NONE);
    }

    @Test
    void runUnoccupied() {
        CreatorMotivation creatorMotivation = new CreatorMotivation(10, 2);
        ArrayList<Motivation> motivations = new ArrayList<>();
        motivations.add(creatorMotivation);

        BasicAgent testAgent = new BasicAgent(
                new Location(0, 0),
                new BasicAttributes(0, "BlueSize=3", 0, Color.blue, ColorModel.STATIC, 0, 0, 1, 3, 4),
                (ArrayList<Motivation>) motivations.clone());
        testAgent.getAttributes().calculateAttributes();
        testAgent.getScores().setAge(6);


        AgentVision occupiedAgentVision = new AgentVision(8, false, new Location(1, 1));
        int desiredScore = 1;
        AgentDecision agentDecision = testAgent.getMotivations().get(0).run(occupiedAgentVision, testAgent.getAttributes(), testAgent.getScores());
        assertEquals(agentDecision.decisionScore(), desiredScore);
        assertEquals(agentDecision.agentAction(), AgentAction.MOVE);
    }

    @Test
    void compareAttributesSizeDifferenceAbove10() {
        Attributes attributesA = new BasicAttributes(0, "Blue", 0, Color.blue, ColorModel.STATIC, 0, 0, 1, 3, 4);
        Attributes attributesB = new BasicAttributes(0, "Blue", 0, Color.blue, ColorModel.STATIC, 0, 0, 1, 14, 4);
        assertFalse(CreatorMotivation.compareAttributes(attributesA, attributesB));
    }

    @Test
    void compareAttributesCreateSizeDifferenceAbove2() {
        Attributes attributesA = new BasicAttributes(0, "Blue", 0, Color.blue, ColorModel.STATIC, 0, 0, 1, 3, 4);
        Attributes attributesB = new BasicAttributes(0, "Blue", 0, Color.blue, ColorModel.STATIC, 0, 0, 1, 3, 7);
        assertFalse(CreatorMotivation.compareAttributes(attributesA, attributesB));
    }

    @Test
    void compareAttributesRangeDifferenceAbove2() {
        Attributes attributesA = new BasicAttributes(0, "Blue", 0, Color.blue, ColorModel.STATIC, 0, 0, 1, 3, 4);
        Attributes attributesB = new BasicAttributes(0, "Blue", 0, Color.blue, ColorModel.STATIC, 0, 0, 3, 3, 4);
        assertFalse(CreatorMotivation.compareAttributes(attributesA, attributesB));
    }

    @Test
    void compareAttributesSame() {
        Attributes attributesA = new BasicAttributes(0, "Blue", 0, Color.blue, ColorModel.STATIC, 0, 0, 1, 3, 4);
        Attributes attributesB = new BasicAttributes(0, "Blue", 0, Color.blue, ColorModel.STATIC, 0, 0, 1, 3, 4);
        assertTrue(CreatorMotivation.compareAttributes(attributesA, attributesB));
    }

    @Test
    void compareAttributesSimilar() {
        Attributes attributesA = new BasicAttributes(0, "Blue", 0, Color.blue, ColorModel.STATIC, 0, 0, 1, 3, 4);
        Attributes attributesB = new BasicAttributes(0, "Blue", 0, Color.blue, ColorModel.STATIC, 0, 0, 2, 10, 6);
        assertTrue(CreatorMotivation.compareAttributes(attributesA, attributesB));
    }

}