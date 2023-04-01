package UnitTests;

import Simulation.Agent.AgentConcreteComponents.BasicAgent;
import Simulation.Agent.AgentConcreteComponents.BasicAttributes;
import Simulation.Agent.AgentConcreteComponents.GrazerMotivation;
import Simulation.Agent.AgentInterfaces.Motivation;
import Simulation.Agent.AgentStructs.AgentAction;
import Simulation.Agent.AgentStructs.AgentDecision;
import Simulation.Agent.AgentStructs.AgentVision;
import Simulation.Agent.AgentStructs.ColorModel;
import Simulation.Environment.Location;
import org.junit.jupiter.api.Test;

import java.awt.*;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;

class GrazerMotivationTest {

    @Test
    void runUnoccupied() {

        GrazerMotivation grazerMotivation = new GrazerMotivation(10, 2);
        ArrayList<Motivation> motivations = new ArrayList<>();
        motivations.add(grazerMotivation);

        BasicAgent testAgent = new BasicAgent(
                new Location(0, 0),
                new BasicAttributes(0, "BlueSize=3", 0, Color.blue, ColorModel.STATIC, 0, 0, 1, 3, 4),
                (ArrayList<Motivation>) motivations.clone());
        testAgent.getAttributes().calculateAttributes();

        // Here we check if GrazerMotivation returns the correct score for an unoccupied tile with more energy that its eat amount.
        // It should be (18 (10 + min(8 4)*2) = 18) and the action should be GRAZE
        AgentVision unoccipiedAgentVision = new AgentVision(8, false, new Location(1, 1));
        int desiredScore = 18;
        AgentDecision agentDecision = testAgent.getMotivations().get(0).run(unoccipiedAgentVision, testAgent.getAttributes(), testAgent.getScores());
        assertEquals(agentDecision.decisionScore(), desiredScore);
        assertEquals(agentDecision.agentAction(), AgentAction.GRAZE);

        // Here we check if GrazerMotivation returns the correct score for an unoccupied tile with less energy that its eat amount.
        // It should be 14 ((10 + min(2, 4)*2) = 14) and the action should be GRAZE
        unoccipiedAgentVision = new AgentVision(2, false, new Location(1, 1));
        desiredScore = 14;
        agentDecision = testAgent.getMotivations().get(0).run(unoccipiedAgentVision, testAgent.getAttributes(), testAgent.getScores());
        assertEquals(agentDecision.decisionScore(), desiredScore);
        assertEquals(agentDecision.agentAction(), AgentAction.GRAZE);

        // Here we check if GrazerMotivation returns the correct score for an unoccupied tile with no energy.
        // It should be 1 and the action should be MOVE
        unoccipiedAgentVision = new AgentVision(0, false, new Location(1, 1));
        desiredScore = 1;
        agentDecision = testAgent.getMotivations().get(0).run(unoccipiedAgentVision, testAgent.getAttributes(), testAgent.getScores());
        assertEquals(agentDecision.decisionScore(), desiredScore);
        assertEquals(agentDecision.agentAction(), AgentAction.MOVE);
    }

    @Test
    void runOccupiedSmaller() {

        GrazerMotivation grazerMotivation = new GrazerMotivation(10, 2);
        ArrayList<Motivation> motivations = new ArrayList<>();
        motivations.add(grazerMotivation);

        BasicAgent testAgent = new BasicAgent(
                new Location(0, 0),
                new BasicAttributes(0, "BlueSize=3", 0, Color.blue, ColorModel.STATIC, 0, 0, 1, 3, 4),
                (ArrayList<Motivation>) motivations.clone());
        testAgent.getAttributes().calculateAttributes();

        BasicAgent smallerAgent = new BasicAgent(
                new Location(1, 1),
                new BasicAttributes(0, "BlueSize=2", 0, Color.blue, ColorModel.STATIC, 0, 0, 1, 2, 4),
                (ArrayList<Motivation>) motivations.clone());
        smallerAgent.getAttributes().calculateAttributes();

        // Here we check if GrazerMotivation returns the correct score for a tile occupied with an agent of a smaller size.
        // It should be 18 ((10 + min(8 4)*2) = 18) and the action should be GRAZE
        AgentVision occupiedWithSmallerSizeVision = new AgentVision(8, true, new Location(1, 1), smallerAgent.getAttributes(), smallerAgent.getScores());
        int desiredScore = 18;
        AgentDecision agentDecision = testAgent.getMotivations().get(0).run(occupiedWithSmallerSizeVision, testAgent.getAttributes(), testAgent.getScores());
        assertEquals(agentDecision.decisionScore(), desiredScore);
        assertEquals(agentDecision.agentAction(), AgentAction.GRAZE);
    }

    @Test
    void runOccupiedEqual() {
        GrazerMotivation grazerMotivation = new GrazerMotivation(10, 2);
        ArrayList<Motivation> motivations = new ArrayList<>();
        motivations.add(grazerMotivation);

        BasicAgent testAgent = new BasicAgent(
                new Location(0, 0),
                new BasicAttributes(0, "BlueSize=3", 0, Color.blue, ColorModel.STATIC, 0, 0, 1, 3, 4),
                (ArrayList<Motivation>) motivations.clone());
        testAgent.getAttributes().calculateAttributes();

        BasicAgent sameSizeAgent = new BasicAgent(
                new Location(1, 1),
                new BasicAttributes(0, "BlueSize=3", 0, Color.blue, ColorModel.STATIC, 0, 0, 1, 3, 4),
                (ArrayList<Motivation>) motivations.clone());
        sameSizeAgent.getAttributes().calculateAttributes();

        // Here we check if GrazerMotivation returns the correct score for a tile occupied with an agent of the same size.
        // It should be -1 and the action should be NONE
        AgentVision occupiedWithSameSizeVision = new AgentVision(8, true, new Location(1, 1), sameSizeAgent.getAttributes(), sameSizeAgent.getScores());
        int desiredScore = -1;
        AgentDecision agentDecision = testAgent.getMotivations().get(0).run(occupiedWithSameSizeVision, testAgent.getAttributes(), testAgent.getScores());
        assertEquals(agentDecision.decisionScore(), desiredScore);
        assertEquals(agentDecision.agentAction(), AgentAction.NONE);
    }

    @Test
    void runOccupiedLarger() {

        GrazerMotivation grazerMotivation = new GrazerMotivation(10, 2);
        ArrayList<Motivation> motivations = new ArrayList<>();
        motivations.add(grazerMotivation);

        BasicAgent testAgent = new BasicAgent(
                new Location(0, 0),
                new BasicAttributes(0, "BlueSize=3", 0, Color.blue, ColorModel.STATIC, 0, 0, 1, 3, 4),
                (ArrayList<Motivation>) motivations.clone());
        testAgent.getAttributes().calculateAttributes();

        BasicAgent largerAgent = new BasicAgent(
                new Location(1, 1),
                new BasicAttributes(0, "BlueSize=4", 0, Color.blue, ColorModel.STATIC, 0, 0, 1, 4, 4),
                (ArrayList<Motivation>) motivations.clone());
        largerAgent.getAttributes().calculateAttributes();

        // Here we check if GrazerMotivation returns the correct score for a tile occupied with an agent of a larger size.
        // It should be -1 and the action should be NONE
        AgentVision occupiedWithLargerSizeVision = new AgentVision(8, true, new Location(1, 1), largerAgent.getAttributes(), largerAgent.getScores());
        int desiredScore = -1;
        AgentDecision agentDecision = testAgent.getMotivations().get(0).run(occupiedWithLargerSizeVision, testAgent.getAttributes(), testAgent.getScores());
        assertEquals(agentDecision.decisionScore(), desiredScore);
        assertEquals(agentDecision.agentAction(), AgentAction.NONE);
    }
}