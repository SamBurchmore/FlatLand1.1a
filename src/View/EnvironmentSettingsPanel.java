package View;

import Simulation.SimulationUtility.SimulationSettings.EnvironmentSettings;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * This class contains gui components corresponding to environment settings. It can produce an EnvironmentSettings object
 * from its components values and set its components values to an input EnvironmentSettings objects values.
 *
 * @author Sam Burchmore
 * @version 1.0a
 * @since 1.0a
 */
public class EnvironmentSettingsPanel extends JPanel implements ActionListener {

    private final JSpinner maxEnergySpinner;
    private final JSpinner minEnergySpinner;
    private final JSpinner energyRegenChanceSpinner;
    private final JSpinner energyRegenAmountSpinner;
    private final JSpinner environmentSizeSpinner;

    private final SquareButton maxColorButton;
    private final SquareButton highColorButton;
    private final SquareButton mediumHighColorButton;
    private final SquareButton mediumLowColorButton;
    private final SquareButton lowColorButton;
    private final SquareButton minColorButton;
    private final SquareButton terrainColorButton;

    private final JButton refreshSettingsButton;

    public EnvironmentSettingsPanel() {
        super();
        setLayout(new GridBagLayout());

        JPanel environmentSettingsPanel = new JPanel(new GridLayout(3, 2));

        // The environment settings panel is built
        JPanel maxEnergyPanel = new JPanel();
        JLabel maxEnergyLabel = new JLabel("Max Energy:         ");
        maxEnergySpinner = new JSpinner(new SpinnerNumberModel(5, 0, 999, 1));
        maxEnergyPanel.add(maxEnergyLabel);
        maxEnergyPanel.add(maxEnergySpinner);

        JLabel minEnergyLabel = new JLabel("Min Energy:           ");
        minEnergySpinner = new JSpinner(new SpinnerNumberModel(0, 0, 999, 1));
        JPanel minEnergyPanel = new JPanel();
        minEnergyPanel.add(minEnergyLabel);
        minEnergyPanel.add(minEnergySpinner);

        JLabel energyRegenChanceLabel = new JLabel("Regen Chance:     ");
        energyRegenChanceSpinner = new JSpinner(new SpinnerNumberModel(100.0, 0, 100.0, 0.01));
        JPanel energyRegenChancePanel = new JPanel();
        energyRegenChancePanel.add(energyRegenChanceLabel);
        energyRegenChancePanel.add(energyRegenChanceSpinner);

        JLabel energyRegenAmountLabel = new JLabel("Regen Amount:     ");
        energyRegenAmountSpinner = new JSpinner(new SpinnerNumberModel(1, 0, 999, 1));
        JPanel energyRegenAmountPanel = new JPanel();
        energyRegenAmountPanel.add(energyRegenAmountLabel);
        energyRegenAmountPanel.add(energyRegenAmountSpinner);

        JLabel environmentSizeLabel = new JLabel("Environment Size:");
        environmentSizeSpinner = new JSpinner(new SpinnerNumberModel(300, 1, 600, 1));
        JPanel environmentSizePanel = new JPanel();
        environmentSizePanel.add(environmentSizeLabel);
        environmentSizePanel.add(environmentSizeSpinner);

        environmentSettingsPanel.add(maxEnergyPanel);
        environmentSettingsPanel.add(minEnergyPanel);
        environmentSettingsPanel.add(energyRegenChancePanel);
        environmentSettingsPanel.add(energyRegenAmountPanel);
        environmentSettingsPanel.add(environmentSizePanel);

        // The colors panel is built
        maxColorButton = new SquareButton("100%");
        maxColorButton.addActionListener(this);
        highColorButton = new SquareButton(">75%");
        highColorButton.addActionListener(this);
        mediumHighColorButton = new SquareButton(">50%");
        mediumHighColorButton.addActionListener(this);
        mediumLowColorButton = new SquareButton(">25%");
        mediumLowColorButton.addActionListener(this);
        lowColorButton = new SquareButton(" >0%");
        lowColorButton.addActionListener(this);
        minColorButton = new SquareButton("  0%");
        minColorButton.addActionListener(this);
        JPanel colorsPanel = new JPanel(new GridLayout());
        colorsPanel.add(maxColorButton);
        colorsPanel.add(highColorButton);
        colorsPanel.add(mediumHighColorButton);
        colorsPanel.add(mediumLowColorButton);
        colorsPanel.add(lowColorButton);
        colorsPanel.add(minColorButton);

        // The terrain color panel is built
        terrainColorButton = new SquareButton("<html>Terrain<br>Colour</html>");
        terrainColorButton.addActionListener(this);
        JPanel terrainColorPanel = new JPanel(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        c.weightx = 1;
        c.weighty = 1;
        c.gridwidth = 5;
        c.gridx = 0;
        c.fill = GridBagConstraints.HORIZONTAL;
        JLabel terrainColorLabel = new JLabel("Environment Colours: ");
        terrainColorLabel.setHorizontalAlignment(SwingConstants.RIGHT);
        terrainColorLabel.setForeground(new Color(51, 51, 51));
        terrainColorLabel.setFont(new Font("Dialog", Font.BOLD, 26));
        terrainColorPanel.add(terrainColorLabel, c);
        c.anchor = GridBagConstraints.LINE_END;
        c.fill = GridBagConstraints.NONE;
        c.gridx = 5;
        c.gridwidth = 1;
        terrainColorPanel.add(terrainColorButton, c);

        refreshSettingsButton = new JButton("Refresh Environment Settings");
        refreshSettingsButton.setPreferredSize(new Dimension(refreshSettingsButton.getWidth(), 50));

        // The GridBag constraints we'll be using to build this panel
        c = new GridBagConstraints();
        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridwidth = 1;
        c.weightx = 1;
        c.weighty = 1;

        // All components are now added
        c.gridy = 0;
        c.gridx = 0;
        add(environmentSettingsPanel, c);
        c.gridy = 1;
        add(terrainColorPanel, c);
        c.gridy = 2;
        add(colorsPanel, c);
        c.gridy = 3;
        add(refreshSettingsButton, c);
    }

    /**
     * Calls one of setXColor() methods depending on what the action source is.
     *
     * @param actionEvent the event to be processed
     */
    @Override
    public void actionPerformed(ActionEvent actionEvent) {
        if (actionEvent.getSource().equals(maxColorButton)) {
            setMaxColor();
        }
        if (actionEvent.getSource().equals(highColorButton)) {
            setHighColor();
        }
        if (actionEvent.getSource().equals(mediumHighColorButton)) {
            setMediumHighColor();
        }
        if (actionEvent.getSource().equals(mediumLowColorButton)) {
            setMediumLowColor();
        }
        if (actionEvent.getSource().equals(lowColorButton)) {
            setLowColor();
        }
        if (actionEvent.getSource().equals(minColorButton)) {
            setMinColor();
        }
        if (actionEvent.getSource().equals(terrainColorButton)) {
            setTerrainColor();
        }
    }

    /**
     * Returns Color.WHITE if the sum of the input colors chanels is less than 450, otherwise Color.BLACK is returned.
     */
    private static Color getContrastingColor(Color color) {
        if (color.getRed() + color.getGreen() + color.getBlue() < 450) {
            return Color.WHITE;
        } else return Color.BLACK;
    }

    /**
     * Packages and returns the colors as an array.
     */
    private Color[] getColors() {
        Color[] colors = new Color[7];
        colors[0] = minColorButton.getBackground();
        colors[1] = lowColorButton.getBackground();
        colors[2] = mediumLowColorButton.getBackground();
        colors[3] = mediumHighColorButton.getBackground();
        colors[4] = highColorButton.getBackground();
        colors[5] = maxColorButton.getBackground();
        colors[6] = terrainColorButton.getBackground();
        return colors;
    }

    /**
     * Sets the button colors to match those input. Additionally, updates the foreground color using getContrastingColor().
     */
    private void setColors(Color[] colors) {
        minColorButton.setBackground(colors[0]);
        minColorButton.setForeground(getContrastingColor(colors[0]));
        lowColorButton.setBackground(colors[1]);
        lowColorButton.setForeground(getContrastingColor(colors[1]));
        mediumLowColorButton.setBackground(colors[2]);
        mediumLowColorButton.setForeground(getContrastingColor(colors[2]));
        mediumHighColorButton.setBackground(colors[3]);
        mediumHighColorButton.setForeground(getContrastingColor(colors[3]));
        highColorButton.setBackground(colors[4]);
        highColorButton.setForeground(getContrastingColor(colors[4]));
        maxColorButton.setBackground(colors[5]);
        maxColorButton.setForeground(getContrastingColor(colors[5]));
        terrainColorButton.setBackground(colors[6]);
        terrainColorButton.setForeground(getContrastingColor(colors[6]));
    }

    /**
     * Returns an EnvironmentSettings object with values corresponding to those in the view components.
     */
    public EnvironmentSettings getEnvironmentSettings() {
        return new EnvironmentSettings(
                (int) getEnvironmentSizeSpinner().getValue(),
                (int) getMaxEnergySpinner().getValue(),
                (int) getMinEnergySpinner().getValue(),
                (double) getEnergyRegenChanceSpinner().getValue(),
                (int) getEnergyRegenAmountSpinner().getValue(),
                getColors());
    }

    /**
     * Sets the components values to match those of the input EnvironmentSettings object.
     */
    public void setEnvironmentSettings(EnvironmentSettings environmentSettings) {
        this.environmentSizeSpinner.setValue(environmentSettings.getSize());
        this.getMaxEnergySpinner().setValue(environmentSettings.getMaxEnergyLevel());
        this.getMinEnergySpinner().setValue(environmentSettings.getMinEnergyLevel());
        this.getEnergyRegenChanceSpinner().setValue(environmentSettings.getEnergyRegenChance());
        this.getEnergyRegenAmountSpinner().setValue(environmentSettings.getEnergyRegenAmount());
        setColors(environmentSettings.getEnvironmentColors());
    }

    /**
     * These methods show a color chooser dialog and if the return value is not null, sets the appropriate button color.
     */
    private void setTerrainColor() {
        Color color = JColorChooser.showDialog(null, "Select Terrain Colour", Color.white);
        if (color != null) {
            terrainColorButton.setBackground(color);
            terrainColorButton.setForeground(getContrastingColor(color));
        }
    }

    private void setMaxColor() {
        Color color = JColorChooser.showDialog(null, "Select Max Colour", Color.white);
        if (color != null) {
            maxColorButton.setBackground(color);
            maxColorButton.setForeground(getContrastingColor(color));
        }
    }

    private void setHighColor() {
        Color color = JColorChooser.showDialog(null, "Select High Colour", Color.white);
        if (color != null) {
            highColorButton.setBackground(color);
            highColorButton.setForeground(getContrastingColor(color));
        }
    }

    private void setMediumHighColor() {
        Color color = JColorChooser.showDialog(null, "Select Medium High Colour", Color.white);
        if (color != null) {
            mediumHighColorButton.setBackground(color);
            mediumHighColorButton.setForeground(getContrastingColor(color));
        }
    }

    private void setMediumLowColor() {
        Color color = JColorChooser.showDialog(null, "Select Medium Low Colour", Color.white);
        if (color != null) {
            mediumLowColorButton.setBackground(color);
            mediumLowColorButton.setForeground(getContrastingColor(color));
        }
    }

    private void setLowColor() {
        Color color = JColorChooser.showDialog(null, "Select Low Colour", Color.white);
        if (color != null) {
            lowColorButton.setBackground(color);
            lowColorButton.setForeground(getContrastingColor(color));
        }
    }

    private void setMinColor() {
        Color color = JColorChooser.showDialog(null, "Select Min Colour", Color.white);
        if (color != null) {
            minColorButton.setBackground(color);
            minColorButton.setForeground(getContrastingColor(color));
        }
    }

    public JSpinner getMaxEnergySpinner() {
        return maxEnergySpinner;
    }

    public JSpinner getMinEnergySpinner() {
        return minEnergySpinner;
    }

    public JSpinner getEnergyRegenChanceSpinner() {
        return energyRegenChanceSpinner;
    }

    public JSpinner getEnergyRegenAmountSpinner() {
        return energyRegenAmountSpinner;
    }

    public JButton getRefreshSettingsButton() {
        return refreshSettingsButton;
    }

    public JSpinner getEnvironmentSizeSpinner() {
        return environmentSizeSpinner;
    }
}
