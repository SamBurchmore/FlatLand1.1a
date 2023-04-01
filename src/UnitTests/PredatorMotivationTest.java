package UnitTests;

import Simulation.Agent.AgentConcreteComponents.BasicAgent;
import Simulation.Agent.AgentConcreteComponents.BasicAttributes;
import Simulation.Agent.AgentConcreteComponents.PredatorMotivation;
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

class PredatorMotivationTest {

    @Test
    void runOccupiedSmallerDifferentID() {
        PredatorMotivation predatorMotivation = new PredatorMotivation(10, 2);
        ArrayList<Motivation> motivations = new ArrayList<>();
        motivations.add(predatorMotivation);

        BasicAgent testAgent = new BasicAgent(
                new Location(0, 0),
                new BasicAttributes(0, "BlueSize=3", 0, Color.blue, ColorModel.STATIC, 0, 0, 1, 3, 4),
                (ArrayList<Motivation>) motivations.clone());
        testAgent.getAttributes().calculateAttributes();

        BasicAgent potentialPrey = new BasicAgent(
                new Location(0, 0),
                new BasicAttributes(0, "BlueSize=3", 1, Color.blue, ColorModel.STATIC, 0, 0, 1, 2, 4),
                (ArrayList<Motivation>) motivations.clone());
        potentialPrey.getAttributes().calculateAttributes();

        AgentVision occupiedAgentVision = new AgentVision(8, true, new Location(1, 1), potentialPrey.getAttributes(), potentialPrey.getScores());
        int desiredScore = 30;
        AgentDecision agentDecision = testAgent.getMotivations().get(0).run(occupiedAgentVision, testAgent.getAttributes(), testAgent.getScores());
        assertEquals(desiredScore, agentDecision.decisionScore());
        assertEquals(agentDecision.agentAction(), AgentAction.PREDATE);
    }

    @Test
    void runOccupiedSmallerSameID() {
        PredatorMotivation predatorMotivation = new PredatorMotivation(10, 2);
        ArrayList<Motivation> motivations = new ArrayList<>();
        motivations.add(predatorMotivation);

        BasicAgent testAgent = new BasicAgent(
                new Location(0, 0),
                new BasicAttributes(0, "BlueSize=3", 0, Color.blue, ColorModel.STATIC, 0, 0, 1, 3, 4),
                (ArrayList<Motivation>) motivations.clone());
        testAgent.getAttributes().calculateAttributes();

        BasicAgent potentialPrey = new BasicAgent(
                new Location(0, 0),
                new BasicAttributes(0, "BlueSize=3", 0, Color.blue, ColorModel.STATIC, 0, 0, 1, 2, 4),
                (ArrayList<Motivation>) motivations.clone());
        potentialPrey.getAttributes().calculateAttributes();

        AgentVision occupiedAgentVision = new AgentVision(8, true, new Location(1, 1), potentialPrey.getAttributes(), potentialPrey.getScores());
        int desiredScore = -1;
        AgentDecision agentDecision = testAgent.getMotivations().get(0).run(occupiedAgentVision, testAgent.getAttributes(), testAgent.getScores());
        assertEquals(agentDecision.decisionScore(), desiredScore);
        assertEquals(agentDecision.agentAction(), AgentAction.NONE);
    }

    @Test
    void runOccupiedLarger() {
        PredatorMotivation predatorMotivation = new PredatorMotivation(10, 2);
        ArrayList<Motivation> motivations = new ArrayList<>();
        motivations.add(predatorMotivation);

        BasicAgent testAgent = new BasicAgent(
                new Location(0, 0),
                new BasicAttributes(0, "BlueSize=3", 0, Color.blue, ColorModel.STATIC, 0, 0, 1, 3, 4),
                (ArrayList<Motivation>) motivations.clone());
        testAgent.getAttributes().calculateAttributes();

        BasicAgent potentialPrey = new BasicAgent(
                new Location(0, 0),
                new BasicAttributes(0, "BlueSize=3", 0, Color.blue, ColorModel.STATIC, 0, 0, 1, 4, 4),
                (ArrayList<Motivation>) motivations.clone());
        potentialPrey.getAttributes().calculateAttributes();

        AgentVision occupiedAgentVision = new AgentVision(8, true, new Location(1, 1), potentialPrey.getAttributes(), potentialPrey.getScores());
        int desiredScore = -1;
        AgentDecision agentDecision = testAgent.getMotivations().get(0).run(occupiedAgentVision, testAgent.getAttributes(), testAgent.getScores());
        assertEquals(agentDecision.decisionScore(), desiredScore);
        assertEquals(agentDecision.agentAction(), AgentAction.NONE);
    }

    @Test
    void runUnoccupied() {
        PredatorMotivation predatorMotivation = new PredatorMotivation(10, 2);
        ArrayList<Motivation> motivations = new ArrayList<>();
        motivations.add(predatorMotivation);

        BasicAgent testAgent = new BasicAgent(
                new Location(0, 0),
                new BasicAttributes(0, "BlueSize=3", 0, Color.blue, ColorModel.STATIC, 0, 0, 1, 3, 4),
                (ArrayList<Motivation>) motivations.clone());
        testAgent.getAttributes().calculateAttributes();

        AgentVision occupiedAgentVision = new AgentVision(8, false, new Location(1, 1));
        int desiredScore = 1;
        AgentDecision agentDecision = testAgent.getMotivations().get(0).run(occupiedAgentVision, testAgent.getAttributes(), testAgent.getScores());
        assertEquals(agentDecision.decisionScore(), desiredScore);
        assertEquals(agentDecision.agentAction(), AgentAction.MOVE);
    }
}