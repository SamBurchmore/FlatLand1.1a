package Simulation.Agent.AgentConcreteComponents;

import Simulation.Agent.AgentBaseComponents.BaseAttributes;
import Simulation.Agent.AgentInterfaces.Attributes;
import Simulation.Agent.AgentStructs.ColorModel;

import java.awt.*;

/**
 * Makes up the agents attributes. Attributes are set values that don't change during the agent's life, except if
 * the agent mutates. Attributes define the agents qualities and behavior as well as their color and name.
 *
 * @author Sam Burchmore
 * @version 1.0a
 * @since 1.0a
 */
public class MutatingAttributes extends BaseAttributes {

    /**
     * Constructs a BasicAttributes object using the input parameters.
     * <p>
     * The constructor simply takes the input parameters and assigns them
     * to the new BasicAttributes instance. Then it calls the calculateAttributes()
     * method to calculate the rest of the attributes. If the attributes mutationMagnitude
     * is greater than zero, it will generate its mutating color from its seed color.
     *
     * @param spawningWeight the weight used for this agent when populating the environment
     * @param name           the string that identifies the agent in the diagnostics view
     * @param ID             the agents unique identifier, an agent will breed with and not eat agents with the same code
     * @param seedColor      the agents initial color
     * @param mutationChance the percentage value for how likely the attributes are to mutate
     * @param range          how far the agent can see and move in one turn
     * @param size           used to calculate all the agents calculated stats
     * @param creationSize   how many adjacent squares the agent will try to have children in, also used to calculate the creation cost.
     */
    public MutatingAttributes(int spawningWeight, String name, int ID, Color seedColor, ColorModel colorModel, int randomColorModelMagnitude, int mutationChance, int range, int size, int creationSize) {
        super(spawningWeight, name, ID, seedColor, colorModel, randomColorModelMagnitude, mutationChance, range, size, creationSize);
        mutateAttributesColor(
                getSize() / 100.0,
                getCreationSize() / 8.0,
                getRange() / 5.0,
                125);
    }

    /**
     * Constructs a MutatingAttributes object using two Attributes objects.
     * <p>
     * The constructor takes two Attributes objects and passes them to the
     * generateAttributes() method. It then calls the mutate() method which will
     * randomly mutate size, creationSize, or Range depending on the mutationMagnitude.
     * Finally, the calculateAttributes method is called initialising the remaining values.
     */
    public MutatingAttributes(Attributes attributesA, Attributes attributesB) {
        super(attributesA, attributesB);
    }

    /**
     * Returns a new instance of MutatingAttributes fields initialised using this instances fields
     * combined with the fields from attributesB.
     */
    @Override
    public Attributes combine(Attributes attributesB) {
        return new MutatingAttributes(this, attributesB);
    }

    /**
     * Returns one of the agents 2 colors.
     * <p>
     * If the agents mutationMagnitude exceeds 0 then mutatingColor is returned,
     * else seedColor is returned.
     */
    @Override
    public Color getColor() {
        if (getMutationChance() > 0 && getColorModel().equals(ColorModel.ATTRIBUTES)) {
            return getMutatingColor();
        } else {
            return getSeedColor();
        }
    }

    /**
     * Mutates the input attributes object.
     * <p>
     * Has an 80% chance of mutating size, and a 10% chance for range or creationSize. If the diagnostics verbosity is
     * set to high, then a message with the agents name, mutated attribute and how much it mutated by is added to the log queue.
     */
    @Override
    public void mutate() {
        if (getRandom().nextInt(100) < getMutationChance()) {
            int ran = getRandom().nextInt(113);
            int oldSize = getSize();
            int oldCreationSize = getCreationSize();
            int oldRange = getRange();
            if (ran < 100) {
                // Mutate size
                setSize(Math.min(Math.max(getSize() + getNegOneOrPosOne(), 0), 100));
            } else if (ran < 108) {
                // Mutate creationAmount
                setCreationSize(Math.min(Math.max(getCreationSize() + getNegOneOrPosOne(), 1), 8));

            } else {
                // Mutate range
                setRange(Math.min(Math.max(getRange() + getNegOneOrPosOne(), 0), 5));

            }
            if (getColorModel().equals(ColorModel.ATTRIBUTES)) {
                mutateAttributesColor(
                        (getSize() / 100.0) - (oldSize / 100.0),
                        (getCreationSize() / 8.0) - (oldCreationSize / 8.0),
                        (getRange() / 5.0) - (oldRange / 5.0),
                        125);
            }
        }
    }

    /**
     * Returns either -1 or 1 with an equal distribution.
     */
    private int getNegOneOrPosOne() {
        if (getRandom().nextBoolean()) {
            return 1;
        }
        return -1;
    }

    @Override
    public String toString() {
        return "Mutating: \n" + super.toString();
    }

    @Override
    public Attributes copy() {
        return new MutatingAttributes(
                getSpawningWeight(),
                getName(),
                getID(),
                getSeedColor(),
                getColorModel(),
                getRandomColorModelMagnitude(),
                getMutationChance(),
                getRange(),
                getSize(),
                getCreationSize());
    }

}