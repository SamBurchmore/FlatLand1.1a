package Simulation.SimulationUtility.SimulationSettings;

import Simulation.Agent.AgentInterfaces.Attributes;
import Simulation.Agent.AgentInterfaces.Motivation;
import Simulation.Agent.AgentStructs.ColorModel;

import java.awt.*;
import java.io.Serializable;
import java.util.ArrayList;

/**
 * This class represents a single agents settings. It stores a collection of motivations and an Attributes object.
 *
 * @author Sam Burchmore
 * @version 1.0a
 * @since 1.0a
 */
public class AgentSettings implements Serializable {

    private Attributes attributes;
    private ArrayList<Motivation> motivations;

    public AgentSettings(Attributes attributes, ArrayList<Motivation> motivations) {
        this.attributes = attributes.copy();
        this.motivations = (ArrayList<Motivation>) motivations.clone();
    }

    public Attributes getAttributes() {
        return attributes;
    }

    public void setAttributes(Attributes agentAttributes) {
        this.attributes = agentAttributes;
    }

    public String getName() {
        return attributes.getName();
    }

    public void setName(String name) {
        attributes.setName(name);
    }

    public Color getColor() {
        return attributes.getColor();
    }

    public void setColor(Color color) {
        attributes.setSeedColor(color);
    }

    public Integer getEnergyCapacity() {
        return attributes.getEnergyCapacity();
    }

    public Integer getEatAmount() {
        return attributes.getEatAmount();
    }

    public Integer getLifespan() {
        return attributes.getLifespan();
    }

    public void setLifespan(int lifespan) {
        attributes.setLifespan(lifespan);
    }

    public int getRange() {
        return attributes.getRange();
    }

    public void setRange(int range) {
        attributes.setRange(range);
    }

    public int getSize() {
        return attributes.getSize();
    }

    public void setSize(int size) {
        attributes.setSize(size);
    }

    public Integer getCreationAge() {
        return attributes.getCreationAge();
    }

    public void setCreationAge(int creationAge) {
        attributes.setCreationAge(creationAge);
    }

    public Integer getEnergyLostPerTurn() {
        return attributes.getEnergyLostPerTile();
    }

    public int getCreationAmount() {
        return attributes.getCreationSize();
    }

    public void setCreationAmount(int creationAmount) {
        attributes.setCreationSize(creationAmount);
    }

    public Integer getCreationDelay() {
        return attributes.getCreationDelay();
    }

    public void setCreationDelay(int creationDelay) {
        attributes.setCreationDelay(creationDelay);
    }

    public void setCode(int code) {
        attributes.setID(code);
    }

    public Integer getCode() {
        return attributes.getID();
    }

    public int getSpawningWeight() {
        return attributes.getSpawningWeight();
    }

    public void setSpawningWeight(int spawningWeight) {
        attributes.setSpawningWeight(spawningWeight);
    }

    public ArrayList<Motivation> getMotivations() {
        return motivations;
    }

    public void setMotivations(ArrayList<Motivation> motivations) {
        this.motivations = motivations;
    }

    public int getCreationSize() {
        return attributes.getCreationSize();
    }

    public Integer getCreationCost() {
        return attributes.getCreationCost();
    }

    public ColorModel getColorModel() {
        return attributes.getColorModel();
    }

    public void setColorModel(ColorModel colorModel) {
        attributes.setColorModel(colorModel);
    }

    public int getRandomColorModelMagnitude() {
        return attributes.getRandomColorModelMagnitude();
    }

    public void setRandomColorModelMagnitude(int randomColorModelMagnitude) {
        attributes.setRandomColorModelMagnitude(randomColorModelMagnitude);
    }

    public Color getSeedColor() {
        return attributes.getSeedColor();
    }
}
