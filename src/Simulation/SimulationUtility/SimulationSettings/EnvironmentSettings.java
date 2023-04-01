package Simulation.SimulationUtility.SimulationSettings;

import java.awt.*;
import java.io.Serializable;

/**
 * Groups environment parameters into one class.
 *
 * @author Sam Burchmore
 * @version 1.0a
 * @since 1.0a
 */
public class EnvironmentSettings implements Serializable {

    private int size;
    private int maxEnergyLevel;
    private int minEnergyLevel;
    private double energyRegenChance;
    private int energyRegenAmount;
    private Color[] environmentColors;

    public EnvironmentSettings(int size, int maxEnergyLevel, int minEnergyLevel, double energyRegenChance, int energyRegenAmount, Color[] environmentColors) {
        this.size = size;
        this.maxEnergyLevel = maxEnergyLevel;
        this.minEnergyLevel = minEnergyLevel;
        this.energyRegenChance = energyRegenChance;
        this.energyRegenAmount = energyRegenAmount;
        this.environmentColors = environmentColors;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public int getMaxEnergyLevel() {
        return maxEnergyLevel;
    }

    public void setMaxEnergyLevel(int maxEnergyLevel) {
        this.maxEnergyLevel = maxEnergyLevel;
    }

    public int getMinEnergyLevel() {
        return minEnergyLevel;
    }

    public void setMinEnergyLevel(int minEnergyLevel) {
        this.minEnergyLevel = minEnergyLevel;
    }

    public int getEnergyRegenAmount() {
        return energyRegenAmount;
    }

    public void setEnergyRegenAmount(int energyRegenAmount) {
        this.energyRegenAmount = energyRegenAmount;
    }

    public double getEnergyRegenChance() {
        return energyRegenChance;
    }

    public void setEnergyRegenChance(double energyRegenChance) {
        this.energyRegenChance = energyRegenChance;
    }

    public Color[] getEnvironmentColors() {
        return environmentColors;
    }

    public void setEnvironmentColors(Color[] environmentColors) {
        this.environmentColors = environmentColors;
    }
}
