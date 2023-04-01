package Simulation.Agent.AgentInterfaces;

import Simulation.Agent.AgentStructs.ColorModel;

import java.awt.*;
import java.util.Random;

/**
 * Provides an interface for agent attributes.
 *
 * @author Sam Burchmore
 * @version 1.0a
 * @since 1.0a
 */
public interface Attributes {

    /**
     * A template method for initialising a new set of Attributes with two input sets.
     * <p>
     * The default implementation can be found in AgentBaseComponents.BaseAttributes.generateAttributes.
     * This method should be called from the constructor in a concrete implementation of this interface.
     *
     * @param attributesA the first set of Attributes
     * @param attributesB the second set of Attributes
     */
    void generateAttributes(Attributes attributesA, Attributes attributesB);

    /**
     * Provides an interface for producing an exact copy.
     */
    Attributes copy();

    /**
     * Provides an interface for producing a new set of attributes from two initial sets.
     */
    Attributes combine(Attributes attributes);

    /**
     * Provides an interface for changing this classes mutating color based on three double percent values and one integer constant.
     * <p>
     * The intention is that the three double values represent some change in the agents attributes after mutating.
     * The default implementation can be found in AgentBaseComponents.BaseAttributes.mutateAttributesColor().
     */
    void mutateAttributesColor(double a, double b, double c, int constant);

    /**
     * Provides an interface for changing this classes seed color.
     * <p>
     * The default implementation can be found in AgentBaseComponents.BaseAttributes.mutateSeedColor().
     */
    void mutateSeedColor();

    /**
     * A template method intended to be called at the end of the Attributes creation process.
     * <p>
     * It should initialise the remaining attributes using those which have been assigned values.
     * The default implementation can be found in AgentBaseComponents.BaseAttributes.calculateAttributes().
     */
    void calculateAttributes();

    /**
     * A template method which returns the intended display color.
     * <p>
     * Implementations of this method should return the correct display color, i.e. the color that should be displayed
     * to represent this agent on the simulation view. The default implementation can be found in
     * AgentBaseComponents.BaseAttributes.getColor().
     */
    Color getColor();

    /**
     * A template method which alters the objects fields.
     * <p>
     * Implementations of this method should either alter the objects fields in some way 'mutating' them, or if the concrete
     * instance should not mutate, then it should be left blank.
     */
    void mutate();

    int getRange();

    int getSize();

    int getLifespan();

    int getCreationAge();

    int getCreationSize();

    int getCreationDelay();

    int getEnergyCapacity();

    int getEnergyLostPerTile();

    int getEatAmount();

    int getCreationCost();

    String getName();

    Integer getID();

    Color getSeedColor();

    int getSpawningWeight();

    int getMutationChance();

    void setRange(int range);

    void setSize(int size);

    void setLifespan(int lifespan);

    void setCreationAge(int creationAge);

    void setCreationSize(int creationSize);

    void setCreationDelay(int creationDelay);

    void setName(String name);

    void setID(int code);

    void setSeedColor(Color seedColor);

    void setSpawningWeight(int spawningWeight);

    void setMutationChance(int mutationChance);

    Color getMutatingColor();

    void setEnergyCapacity(int energyCapacity);

    void setEatAmount(int eatAmount);

    void setCreationCost(int creationCost);

    void setEnergyLostPerTile(int energyLostPerTile);

    void setMutatingColor(Color mutatingColor);

    ColorModel getColorModel();

    void setColorModel(ColorModel colorModel);

    int getRandomColorModelMagnitude();

    void setRandomColorModelMagnitude(int randomColorModelMagnitude);

    Random getRandom();
}
