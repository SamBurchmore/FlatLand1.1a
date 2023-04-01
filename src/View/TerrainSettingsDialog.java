package View;

import Simulation.SimulationUtility.SimulationSettings.TerrainSettings;

import javax.swing.*;
import java.awt.*;

/**
 * The dialog where the terrain settings are viewed and edited.
 *
 * @author Sam Burchmore
 * @version 1.0a
 * @since 1.0a
 */
public class TerrainSettingsDialog extends JDialog {

    private final JSpinner rockSizeSpinner;
    private final JSpinner caveDensitySpinner;
    private final JSpinner caveLengthSpinner;
    private final JSpinner lineDensitySpinner;
    private final JSpinner bendDensitySpinner;
    private final JSpinner terrainNumberSpinner;
    private final JSpinner cavernSizeSpinner;
    private final JSpinner upperCaveSizeSpinner;
    private final JSpinner lowerCaveSizeSpinner;
    private final JSpinner caveWaveSpinner;
    private final JCheckBox isTerrainCheckBox;

    public TerrainSettingsDialog(JFrame parent, TerrainSettings terrainSettings) {
        super(parent);
        setSize(new Dimension(400, 500));
        JPanel settingsPanel = new JPanel();
        settingsPanel.setLayout(new BoxLayout(settingsPanel, BoxLayout.PAGE_AXIS));
        settingsPanel.setPreferredSize(new Dimension(400, 300));

        rockSizeSpinner = new JSpinner(new SpinnerNumberModel(3, 1, 100, 1));
        JLabel rockSizeLabel = new JLabel("Rock Size");
        rockSizeLabel.setHorizontalAlignment(SwingConstants.CENTER);
        JPanel rockSizePanel = new JPanel();
        rockSizePanel.add(rockSizeLabel);
        rockSizePanel.add(rockSizeSpinner);

        caveDensitySpinner = new JSpinner(new SpinnerNumberModel(10.0, 0.0001, 100.0, 0.001));
        JLabel caveDensityLabel = new JLabel("Cave Density");
        caveDensityLabel.setHorizontalAlignment(SwingConstants.CENTER);
        JPanel caveDensityPanel = new JPanel();
        caveDensityPanel.add(caveDensityLabel);
        caveDensityPanel.add(caveDensitySpinner);
        caveDensityPanel.add(new JLabel("%"));

        caveLengthSpinner = new JSpinner(new SpinnerNumberModel(400, 1, 20000, 1));
        JLabel caveLengthLabel = new JLabel("Cave Length");
        caveLengthLabel.setHorizontalAlignment(SwingConstants.CENTER);
        JPanel caveLengthPanel = new JPanel();
        caveLengthPanel.add(caveLengthLabel);
        caveLengthPanel.add(caveLengthSpinner);

        lineDensitySpinner = new JSpinner(new SpinnerNumberModel(10.0, 0.001, 100.0, 0.001));
        JLabel lineDensityLabel = new JLabel("Cave Weight");
        lineDensityLabel.setHorizontalAlignment(SwingConstants.CENTER);
        JPanel lineDensityPanel = new JPanel();
        lineDensityPanel.add(lineDensityLabel);
        lineDensityPanel.add(lineDensitySpinner);
        lineDensityPanel.add(new JLabel("%"));

        bendDensitySpinner = new JSpinner(new SpinnerNumberModel(10.0, 0.0001, 100.0, 0.001));
        JLabel bendDensityLabel = new JLabel("Bend Density");
        bendDensityLabel.setHorizontalAlignment(SwingConstants.CENTER);
        JPanel bendDensityPanel = new JPanel();
        bendDensityPanel.add(bendDensityLabel);
        bendDensityPanel.add(bendDensitySpinner);
        bendDensityPanel.add(new JLabel("%"));

        terrainNumberSpinner = new JSpinner(new SpinnerNumberModel(1, 1, 1000.0, 1));
        JLabel terrainNumberLabel = new JLabel("Cave No.");
        terrainNumberLabel.setHorizontalAlignment(SwingConstants.CENTER);
        JPanel terrainNumberPanel = new JPanel();
        terrainNumberPanel.add(terrainNumberLabel);
        terrainNumberPanel.add(terrainNumberSpinner);

        cavernSizeSpinner = new JSpinner(new SpinnerNumberModel(1, 1, 100, 1));
        JLabel cavernSizeLabel = new JLabel("Cavern Size");
        cavernSizeLabel.setHorizontalAlignment(SwingConstants.CENTER);
        JPanel cavernSizePanel = new JPanel();
        cavernSizePanel.add(cavernSizeLabel);
        cavernSizePanel.add(cavernSizeSpinner);

        upperCaveSizeSpinner = new JSpinner(new SpinnerNumberModel(10, 1, 100, 1));
        JLabel upperCaveSizeLabel = new JLabel("Upper Cave Size");
        upperCaveSizeLabel.setHorizontalAlignment(SwingConstants.CENTER);
        JPanel upperCaveSizePanel = new JPanel();
        upperCaveSizePanel.add(upperCaveSizeLabel);
        upperCaveSizePanel.add(upperCaveSizeSpinner);

        lowerCaveSizeSpinner = new JSpinner(new SpinnerNumberModel(1, 1, 100, 1));
        JLabel lowerCaveSizeLabel = new JLabel("Lower Cave Size");
        lowerCaveSizeLabel.setHorizontalAlignment(SwingConstants.CENTER);
        JPanel lowerCaveSizePanel = new JPanel();
        lowerCaveSizePanel.add(lowerCaveSizeLabel);
        lowerCaveSizePanel.add(lowerCaveSizeSpinner);

        caveWaveSpinner = new JSpinner(new SpinnerNumberModel(10, 1, 100, 1));
        JLabel caveWaveLabel = new JLabel("Cave Wave");
        caveWaveLabel.setHorizontalAlignment(SwingConstants.CENTER);
        JPanel caveWavePanel = new JPanel();
        caveWavePanel.add(caveWaveLabel);
        caveWavePanel.add(caveWaveSpinner);

        isTerrainCheckBox = new JCheckBox("Fill");
        JPanel isTerrainPanel = new JPanel();
        isTerrainPanel.add(isTerrainCheckBox);

        setTerrainSettings(terrainSettings);

        JPanel fixedCavePanel = new JPanel(new GridLayout(3, 3));

        fixedCavePanel.add(rockSizePanel);
        fixedCavePanel.add(cavernSizePanel);
        fixedCavePanel.add(caveDensityPanel);
        fixedCavePanel.add(bendDensityPanel);
        fixedCavePanel.add(caveLengthPanel);
        fixedCavePanel.add(lineDensityPanel);
        fixedCavePanel.add(terrainNumberPanel);

        JPanel variableCavePanel = new JPanel(new GridLayout(1, 3));

        variableCavePanel.add(upperCaveSizePanel);
        variableCavePanel.add(lowerCaveSizePanel);
        variableCavePanel.add(caveWavePanel);

        JPanel okButtonPanel = new JPanel(new GridLayout(1, 2));

        JButton okButton = new JButton("OK");
        okButton.addActionListener(e -> TerrainSettingsDialog.this.dispose());

        okButtonPanel.add(isTerrainPanel);
        okButtonPanel.add(okButton);

        settingsPanel.add(fixedCavePanel);
        settingsPanel.add(variableCavePanel);
        settingsPanel.add(okButtonPanel);

        add(settingsPanel);

        setName("Terrain Settings");
        setModal(true);
        setVisible(true);
        pack();
        setLocationRelativeTo(parent);
    }

    /**
     * Returns a TerrainSettings object with values corresponding to those in the view components.
     */
    public TerrainSettings getTerrainSettings() {
        return new TerrainSettings(
                (int) rockSizeSpinner.getValue(),
                (int) ((double) caveDensitySpinner.getValue() * 100),
                (int) ((double) bendDensitySpinner.getValue() * 100),
                (int) caveLengthSpinner.getValue(),
                (int) ((double) lineDensitySpinner.getValue() * 100),
                (int) cavernSizeSpinner.getValue(),
                (int) upperCaveSizeSpinner.getValue(),
                (int) lowerCaveSizeSpinner.getValue(),
                (int) caveWaveSpinner.getValue(),
                (int) ((double) terrainNumberSpinner.getValue()),
                isTerrainCheckBox.isSelected()
        );
    }

    /**
     * Sets the components values to match those of the input TerrainSettings object.
     */
    public void setTerrainSettings(TerrainSettings terrainSettings) {
        rockSizeSpinner.setValue(terrainSettings.rockSize());
        caveDensitySpinner.setValue((double) terrainSettings.caveDensity() / 100);
        bendDensitySpinner.setValue((double) terrainSettings.bendDensity() / 100);
        caveLengthSpinner.setValue(terrainSettings.caveLength());
        lineDensitySpinner.setValue((double) terrainSettings.caveWeight() / 100);
        cavernSizeSpinner.setValue(terrainSettings.cavernSize());
        upperCaveSizeSpinner.setValue(terrainSettings.upperCaveSize());
        lowerCaveSizeSpinner.setValue(terrainSettings.lowerCaveSize());
        caveWaveSpinner.setValue(terrainSettings.caveWave());
        terrainNumberSpinner.setValue((double) terrainSettings.caveAmount());
        isTerrainCheckBox.setSelected(terrainSettings.isTerrain());
    }
}
