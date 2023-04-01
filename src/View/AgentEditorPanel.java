package View;

import Simulation.Agent.AgentConcreteComponents.*;
import Simulation.Agent.AgentInterfaces.Attributes;
import Simulation.Agent.AgentInterfaces.Motivation;
import Simulation.Agent.AgentStructs.ColorModel;
import Simulation.SimulationUtility.SimulationSettings.AgentSettings;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

/**
 * This class is the graphical view of the agent editor. It displays an agents settings and allows them to be edited.
 * This class can return an AgentSettings object constructed using the values in the gui components. It can also take
 * an AgentSettings object and set its gui components values to reflect the settings.
 *
 * @author Sam Burchmore
 * @version 1.0a
 * @since 1.0a
 */
public class AgentEditorPanel extends JPanel implements ActionListener {

    private final JTextField agentNameTextField;
    private final SquareButton seedColourChooserButton;
    private Color currentSeedColour;
    private final JLabel agentCodeValue;

    private final JSpinner rangeSpinner;
    private final JSpinner sizeSpinner;
    private final JSpinner creationAmountSpinner;
    private final JLabel energyCapacityValue;
    private final JLabel eatAmountValue;
    private final JLabel energyLostPerTurnValue;
    private final JLabel lifespanValue;
    private final JLabel creationAgeValue;
    private final JLabel creationDelayValue;
    private final JLabel creationCostValue;
    private final JLabel generatedColorValue;

    private final JCheckBox isGrazerToggle;
    private final JCheckBox isPredatorToggle;
    private final JSpinner grazerBiasSpinner;
    private final JSpinner predatorBiasSpinner;
    private final JSpinner grazerWeightSpinner;
    private final JSpinner predatorWeightSpinner;

    private final ButtonGroup colorModelButtonGroup;
    private final JRadioButton staticModelButton;
    private final JRadioButton attributesModelButton;
    private final JRadioButton randomModelButton;
    private final JSpinner randomMagnitudeSpinner;

    private final JSpinner mutationMagnitudeSpinner;
    private final JSpinner spawningWeightSpinner;

    private final JButton updateSettingsButton;

    public AgentEditorPanel() {
        super();
        setLayout(new GridBagLayout());

        GridBagConstraints c = new GridBagConstraints();

        // First the name and color section is built
        seedColourChooserButton = new SquareButton();
        seedColourChooserButton.addActionListener(this);
        currentSeedColour = new Color(255, 255, 255);
        seedColourChooserButton.setBackground(currentSeedColour);
        agentNameTextField = new JTextField("Agent Name");
        agentCodeValue = new JLabel("Agent Code: ");
        JLabel agentCodeLabel = new JLabel("Agent ID: ");
        JPanel agentCodePanel = new JPanel(new GridLayout(1, 2));
        agentCodePanel.add(agentCodeLabel);
        agentCodePanel.add(agentCodeValue);
        final JPanel nameColorPanel = new JPanel(new GridBagLayout());
        c.gridwidth = 2;
        nameColorPanel.add(agentNameTextField, c);
        nameColorPanel.add(seedColourChooserButton, c);
        nameColorPanel.add(new JPanel(), c);
        nameColorPanel.add(agentCodePanel, c);

        // Second, the attributes panel is built
        JPanel mutatingAttributesPanel = new JPanel();
        JLabel sizeLabel = new JLabel("Size: ");
        sizeLabel.setFont(new Font("Dialog", Font.PLAIN, 12));
        sizeSpinner = new JSpinner(new SpinnerNumberModel(2, 2, 100, 1));

        JLabel creationAmountLabel = new JLabel("Creation Size: ");
        creationAmountLabel.setFont(new Font("Dialog", Font.PLAIN, 12));
        creationAmountSpinner = new JSpinner(new SpinnerNumberModel(4, 1, 8, 1));

        JLabel rangeLabel = new JLabel("Range: ");
        rangeLabel.setFont(new Font("Dialog", Font.PLAIN, 12));
        rangeSpinner = new JSpinner(new SpinnerNumberModel(1, 1, 5, 1));

        mutatingAttributesPanel.add(sizeLabel);
        mutatingAttributesPanel.add(sizeSpinner);
        mutatingAttributesPanel.add(creationAmountLabel);
        mutatingAttributesPanel.add(creationAmountSpinner);
        mutatingAttributesPanel.add(rangeLabel);
        mutatingAttributesPanel.add(rangeSpinner);

        // Energy cap panel
        JPanel energyCapacityPanel = new JPanel();
        JLabel energyCapacityLabel = new JLabel("Max Energy:     ");
        energyCapacityLabel.setFont(new Font("Dialog", Font.PLAIN, 12));
        energyCapacityValue = new JLabel("0");
        energyCapacityValue.setHorizontalAlignment(SwingConstants.RIGHT);
        energyCapacityValue.setFont(new Font("Dialog", Font.BOLD, 12));
        energyCapacityPanel.add(energyCapacityLabel);
        energyCapacityPanel.add(energyCapacityValue);

        // Eat amount panel
        JPanel eatAmountPanel = new JPanel();
        JLabel eatAmountLabel = new JLabel("Eat Amount:         ");
        eatAmountLabel.setFont(new Font("Dialog", Font.PLAIN, 12));
        eatAmountValue = new JLabel("0");
        eatAmountValue.setHorizontalAlignment(SwingConstants.RIGHT);
        eatAmountValue.setFont(new Font("Dialog", Font.BOLD, 12));
        eatAmountPanel.add(eatAmountLabel);
        eatAmountPanel.add(eatAmountValue);

        // Energy lost per turn panel
        JPanel energyLostPerTurnPanel = new JPanel();
        JLabel energyLostPerTurnLabel = new JLabel("Energy Burned: ");
        energyLostPerTurnLabel.setFont(new Font("Dialog", Font.PLAIN, 12));
        energyLostPerTurnValue = new JLabel("0");
        energyLostPerTurnValue.setHorizontalAlignment(SwingConstants.RIGHT);
        energyLostPerTurnValue.setFont(new Font("Dialog", Font.BOLD, 12));
        energyLostPerTurnPanel.add(energyLostPerTurnLabel);
        energyLostPerTurnPanel.add(energyLostPerTurnValue);

        // Lifespan panel
        JPanel lifespanPanel = new JPanel();
        JLabel lifespanLabel = new JLabel("Lifespan:         ");
        lifespanLabel.setFont(new Font("Dialog", Font.PLAIN, 12));
        lifespanValue = new JLabel("0");
        lifespanValue.setHorizontalAlignment(SwingConstants.RIGHT);
        lifespanValue.setFont(new Font("Dialog", Font.BOLD, 12));
        lifespanPanel.add(lifespanLabel);
        lifespanPanel.add(lifespanValue);

        // Creation age panel
        JPanel creationAgePanel = new JPanel();
        JLabel creationAgeLabel = new JLabel("Creation Age:       ");
        creationAgeLabel.setFont(new Font("Dialog", Font.PLAIN, 12));
        creationAgeValue = new JLabel("0");
        creationAgeValue.setHorizontalAlignment(SwingConstants.RIGHT);
        creationAgeValue.setFont(new Font("Dialog", Font.BOLD, 12));
        creationAgePanel.add(creationAgeLabel);
        creationAgePanel.add(creationAgeValue);

        // Creation cost panel
        JPanel creationCostPanel = new JPanel();
        JLabel creationCostLabel = new JLabel("Creation Cost:   ");
        creationCostLabel.setFont(new Font("Dialog", Font.PLAIN, 12));
        creationCostValue = new JLabel("0");
        creationCostValue.setHorizontalAlignment(SwingConstants.RIGHT);
        creationCostValue.setFont(new Font("Dialog", Font.BOLD, 12));
        creationCostPanel.add(creationCostLabel);
        creationCostPanel.add(creationCostValue);

        // Creation delay panel
        JPanel creationDelayPanel = new JPanel();
        JLabel creationDelayLabel = new JLabel("Creation Delay: ");
        creationDelayLabel.setFont(new Font("Dialog", Font.PLAIN, 12));
        creationDelayValue = new JLabel("0");
        creationDelayValue.setHorizontalAlignment(SwingConstants.RIGHT);
        creationDelayValue.setFont(new Font("Dialog", Font.BOLD, 12));
        creationDelayPanel.add(creationDelayLabel);
        creationDelayPanel.add(creationDelayValue);

        // Generated color panel
        JPanel generatedColorPanel = new JPanel();
        JLabel generatedColorLabel = new JLabel("Mutating Color: ");
        generatedColorLabel.setFont(new Font("Dialog", Font.PLAIN, 12));
        generatedColorValue = new JLabel("      ");
        generatedColorValue.setHorizontalAlignment(SwingConstants.RIGHT);
        generatedColorValue.setFont(new Font("Dialog", Font.PLAIN, 12));
        generatedColorValue.setOpaque(true);
        generatedColorPanel.add(generatedColorLabel);
        generatedColorPanel.add(generatedColorValue);

        // Calculated attributes panel
        JPanel calculatedAttributesPanel = new JPanel(new GridLayout(3, 3, 5, 5));

        calculatedAttributesPanel.add(energyCapacityPanel);
        calculatedAttributesPanel.add(eatAmountPanel);
        calculatedAttributesPanel.add(energyLostPerTurnPanel);
        calculatedAttributesPanel.add(lifespanPanel);
        calculatedAttributesPanel.add(creationAgePanel);
        calculatedAttributesPanel.add(creationCostPanel);
        calculatedAttributesPanel.add(creationDelayPanel);
        calculatedAttributesPanel.add(generatedColorPanel);
        calculatedAttributesPanel.add(new JLabel());

        // The mutating and calculated attribute panels are grouped into one panel
        c = new GridBagConstraints(); // reset the constraints
        final JPanel attributesPanel = new JPanel(new GridBagLayout());
        attributesPanel.setName("Attributes");
        attributesPanel.add(mutatingAttributesPanel, c);
        c.gridy = 1;
        c.gridheight = 3;
        attributesPanel.add(calculatedAttributesPanel, c);

        // Third, the motivations panel is built
        final JPanel motivationsPanel = new JPanel(new GridLayout(1, 2));
        motivationsPanel.setName("Motivations");
        JPanel grazerPanel = new JPanel(new GridLayout(3, 2));
        JPanel predatorPanel = new JPanel(new GridLayout(3, 2));
        JLabel grazerBiasSpinnerLabel = new JLabel("Bias: ");
        JLabel predatorBiasSpinnerLabel = new JLabel("Bias: ");
        JLabel grazerWeightSpinnerLabel = new JLabel("Weight: ");
        JLabel predatorWeightSpinnerLabel = new JLabel("Weight: ");
        grazerBiasSpinner = new JSpinner(new SpinnerNumberModel(10, 0, 10, 1));
        predatorBiasSpinner = new JSpinner(new SpinnerNumberModel(10, 0, 10, 1));
        grazerWeightSpinner = new JSpinner(new SpinnerNumberModel(1, 0, 10, 1));
        predatorWeightSpinner = new JSpinner(new SpinnerNumberModel(1, 0, 10, 1));
        isGrazerToggle = new JCheckBox("Grazer");
        isPredatorToggle = new JCheckBox("Predator");
        grazerPanel.add(isGrazerToggle);
        grazerPanel.add(new JPanel());
        grazerPanel.add(grazerBiasSpinnerLabel);
        grazerPanel.add(grazerBiasSpinner);
        grazerPanel.add(grazerWeightSpinnerLabel);
        grazerPanel.add(grazerWeightSpinner);
        predatorPanel.add(isPredatorToggle);
        predatorPanel.add(new JPanel());
        predatorPanel.add(predatorBiasSpinnerLabel);
        predatorPanel.add(predatorBiasSpinner);
        predatorPanel.add(predatorWeightSpinnerLabel);
        predatorPanel.add(predatorWeightSpinner);
        motivationsPanel.add(grazerPanel);
        motivationsPanel.add(predatorPanel);

        // Forth, the mutation chance and spawning weight panel is built
        final JPanel mutationChanceAndSpawningPanel = new JPanel(new GridLayout(1, 2));
        mutationChanceAndSpawningPanel.setName("Mutation and Inheritance");
        JPanel mutationMagnitudePanel = new JPanel();
        JLabel mutationMagnitudeLabel = new JLabel("Mutation Chance: ");
        mutationMagnitudeSpinner = new JSpinner(new SpinnerNumberModel(0, 0, 100, 1));
        mutationMagnitudePanel.add(mutationMagnitudeLabel);
        mutationMagnitudePanel.add(mutationMagnitudeSpinner);
        mutationMagnitudePanel.add(new JLabel("%"));
        mutationChanceAndSpawningPanel.add(mutationMagnitudePanel);
        JPanel spawningWeightPanel = new JPanel();
        JLabel spawningWeightLabel = new JLabel("Spawn Weight: ");
        spawningWeightLabel.setHorizontalTextPosition(SwingConstants.RIGHT);
        spawningWeightLabel.setHorizontalAlignment(SwingConstants.RIGHT);
        spawningWeightSpinner = new JSpinner(new SpinnerNumberModel(1, 0, 100, 1));
        spawningWeightPanel.add(spawningWeightLabel);
        spawningWeightPanel.add(spawningWeightSpinner);
        spawningWeightPanel.add(new JLabel("%"));
        mutationChanceAndSpawningPanel.add(spawningWeightPanel);

        // Fifth, the color model panel is built
        JPanel colorModelPanel = new JPanel();
        colorModelPanel.setName("Colour Model");
        colorModelButtonGroup = new ButtonGroup();
        staticModelButton = new JRadioButton("Static");
        attributesModelButton = new JRadioButton("Attributes");
        randomModelButton = new JRadioButton("Random");
        colorModelButtonGroup.add(staticModelButton);
        colorModelButtonGroup.add(attributesModelButton);
        colorModelButtonGroup.add(randomModelButton);
        colorModelPanel.add(staticModelButton);
        colorModelPanel.add(attributesModelButton);
        colorModelPanel.add(randomModelButton);
        JPanel randomMagnitudePanel = new JPanel();
        randomMagnitudeSpinner = new JSpinner(new SpinnerNumberModel(1, 1, 255, 1));
        JLabel randomMagnitudeLabel = new JLabel("Random Magnitude: ");
        randomMagnitudePanel.add(randomMagnitudeLabel);
        randomMagnitudePanel.add(randomMagnitudeSpinner);
        colorModelPanel.add(randomMagnitudePanel);

        // Now the main pane is built and filled with the previously constructed objects
        final JTabbedPane mainPane = new JTabbedPane();
        mainPane.setBackground(new Color(224, 224, 224));
        mainPane.add(attributesPanel);
        mainPane.add(motivationsPanel);
        mainPane.add(colorModelPanel);

        updateSettingsButton = new JButton("Update Agent Settings");
        updateSettingsButton.setPreferredSize(new Dimension(updateSettingsButton.getWidth(), 50));

        c = new GridBagConstraints(); // reset the constraints
        c.fill = GridBagConstraints.BOTH;
        c.weightx = 1;
        c.weighty = 1;
        c.gridheight = 2;
        add(nameColorPanel, c);
        c.gridy = 2;
        c.gridheight = 4;
        add(mainPane, c);
        c.gridy = 6;
        c.gridheight = 1;
        add(mutationChanceAndSpawningPanel, c);
        c.gridy = 7;
        add(updateSettingsButton, c);
    }

    @Override
    public void actionPerformed(ActionEvent actionEvent) {
        if (actionEvent.getSource().equals(seedColourChooserButton)) {
            setCurrentSeedColour();
        }
    }

    /**
     * Displays a color chooser dialog and if not null, sets the seed color to the value returned.
     */
    private void setCurrentSeedColour() {
        currentSeedColour = JColorChooser.showDialog(null, "Select Agent Colour", Color.white);
        if (currentSeedColour != null) {
            seedColourChooserButton.setBackground(currentSeedColour);
        }
    }

    /**
     * Sets the components values to match those of the input AgentSettings object.
     */
    public void setAgentSettings(AgentSettings agentSettings) {
        spawningWeightSpinner.setValue(agentSettings.getSpawningWeight());
        agentNameTextField.setText(agentSettings.getName());
        seedColourChooserButton.setBackground(agentSettings.getSeedColor());
        agentCodeValue.setText(agentSettings.getCode().toString());
        rangeSpinner.setValue(agentSettings.getRange());
        sizeSpinner.setValue(agentSettings.getSize());
        creationAmountSpinner.setValue(agentSettings.getCreationAmount());
        energyCapacityValue.setText(agentSettings.getEnergyCapacity().toString());
        eatAmountValue.setText(agentSettings.getEatAmount().toString());
        energyLostPerTurnValue.setText(agentSettings.getEnergyLostPerTurn().toString());
        lifespanValue.setText(agentSettings.getLifespan().toString());
        creationAgeValue.setText(agentSettings.getCreationAge().toString());
        creationDelayValue.setText(agentSettings.getCreationDelay().toString());
        creationCostValue.setText(agentSettings.getCreationCost().toString());
        isGrazerToggle.setSelected(false);
        isPredatorToggle.setSelected(false);
        for (Motivation motivation : agentSettings.getMotivations()) {
            if (motivation.getCode() == 1) {
                isGrazerToggle.setSelected(true);
                grazerBiasSpinner.setValue(motivation.getBias());
                grazerWeightSpinner.setValue(motivation.getWeight());
            }
            if (motivation.getCode() == 2) {
                isPredatorToggle.setSelected(true);
                predatorBiasSpinner.setValue(motivation.getBias());
                predatorWeightSpinner.setValue(motivation.getWeight());
            }
        }
        mutationMagnitudeSpinner.setValue(agentSettings.getAttributes().getMutationChance());
        if (agentSettings.getAttributes().getMutationChance() > 0 && agentSettings.getAttributes().getColorModel().equals(ColorModel.ATTRIBUTES)) {
            generatedColorValue.setBackground(agentSettings.getAttributes().getMutatingColor());
        } else {
            generatedColorValue.setBackground(agentSettings.getAttributes().getSeedColor());
        }
        if (agentSettings.getColorModel().equals(ColorModel.STATIC)) {
            colorModelButtonGroup.setSelected(staticModelButton.getModel(), true);
        } else if (agentSettings.getColorModel().equals(ColorModel.ATTRIBUTES)) {
            colorModelButtonGroup.setSelected(attributesModelButton.getModel(), true);
        } else {
            colorModelButtonGroup.setSelected(randomModelButton.getModel(), true);
        }
        randomMagnitudeSpinner.setValue(agentSettings.getRandomColorModelMagnitude());
    }

    /**
     * Returns an AgentSettings object with values corresponding to those in the view components.
     */
    public AgentSettings getAgentSettings() {
        ArrayList<Motivation> motivations = new ArrayList<>();
        motivations.add(new CreatorMotivation(20, 1));
        if (isGrazerToggle.isSelected()) {
            motivations.add(new GrazerMotivation((int) grazerBiasSpinner.getValue(), (int) grazerWeightSpinner.getValue()));
        }
        if (isPredatorToggle.isSelected()) {
            motivations.add(new PredatorMotivation((int) predatorBiasSpinner.getValue(), (int) predatorWeightSpinner.getValue()));
        }
        ColorModel colorModel = ColorModel.STATIC;
        if (colorModelButtonGroup.getSelection().equals(attributesModelButton.getModel())) {
            colorModel = ColorModel.ATTRIBUTES;
        } else if (colorModelButtonGroup.getSelection().equals(randomModelButton.getModel())) {
            colorModel = ColorModel.RANDOM;
        }
        Attributes attributes;
        if ((int) mutationMagnitudeSpinner.getValue() <= 0) {
            attributes = new BasicAttributes(
                    (int) spawningWeightSpinner.getValue(),
                    agentNameTextField.getText(),
                    Integer.parseInt(agentCodeValue.getText()),
                    seedColourChooserButton.getBackground(),
                    colorModel,
                    (int) randomMagnitudeSpinner.getValue(),
                    (int) mutationMagnitudeSpinner.getValue(),
                    (int) rangeSpinner.getValue(),
                    (int) sizeSpinner.getValue(),
                    (int) creationAmountSpinner.getValue());
        } else {
            attributes = new MutatingAttributes(
                    (int) spawningWeightSpinner.getValue(),
                    agentNameTextField.getText(),
                    Integer.parseInt(agentCodeValue.getText()),
                    seedColourChooserButton.getBackground(),
                    colorModel,
                    (int) randomMagnitudeSpinner.getValue(),
                    (int) mutationMagnitudeSpinner.getValue(),
                    (int) rangeSpinner.getValue(),
                    (int) sizeSpinner.getValue(),
                    (int) creationAmountSpinner.getValue());
        }
        return new AgentSettings(attributes, motivations);
    }

    public JButton getUpdateSettingsButton() {
        return updateSettingsButton;
    }
}
