package View;

import Simulation.SimulationUtility.SimulationSettings.TerrainSettings;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/**
 * The main gui component where all the subcomponents are grouped.
 *
 * @author Sam Burchmore
 * @version 1.0a
 * @since 1.0a
 */
public class UserInterface extends JFrame {

    JFileChooser fileChooser;

    private final JButton saveAgentsMenuButton;
    private final JButton loadAgentsMenuButton;
    private final JButton saveEnvironmentSettingsMenuButton;
    private final JButton loadEnvironmentSettingsMenuButton;
    private final JButton saveSettingsMenuButton;
    private final JButton loadSettingsMenuButton;
    private final JButton loadTerrainSettingsMenuButton;
    private final JButton saveTerrainSettingsMenuButton;
    private final JButton saveTerrainMenuButton;
    private final JButton loadTerrainMenuButton;

    private final JButton preset1Button;
    private final JButton preset2Button;
    private final JButton preset3Button;
    private final JButton preset4Button;
    private final JButton preset5Button;
    private final JButton preset6Button;

    private final JButton toggleControlsButton;
    private final JButton toggleRightPanelButton;
    private final JButton toggleLeftPanelButton;

    private final JButton clearTerrain;
    private final JButton fillTerrain;
    private final JButton generateCave;
    private final JButton generateCaveSystem;
    private final JButton terrainSettings;

    private final EnvironmentSettingsPanel environmentSettingsPanel;
    private final SimulationControlPanel simulationControlPanel;
    private final AgentEditorPanel agentEditorPanel;
    private final SimulationPanel simulationPanel;
    private final AgentSelectorPanel agentSelectorPanel;
    private final DiagnosticsPanel diagnosticsPanel;

    private final JPanel leftPanel;
    private final JPanel rightPanel;

    private final TerrainControlPanel terrainControlPanel;

    public UserInterface() throws IOException {
        setName("FlatLand 1.0a");
        getContentPane().setLayout(new FlowLayout());
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setIconImage(ImageIO.read((new File("src\\images\\tool-icon-v1.png").toURI()).toURL()));

        terrainControlPanel = new TerrainControlPanel();

        // Build the file chooser, ensuring it filters for the .dat file extension
        fileChooser = new JFileChooser();
        fileChooser.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
        File workingDirectory = new File(System.getProperty("user.dir"));
        fileChooser.setCurrentDirectory(workingDirectory);
        fileChooser.setFileFilter(new FileNameExtensionFilter("DAT file", "dat"));

        // Define the menu bar and its menus
        JMenuBar menuBar = new JMenuBar();
        JMenu saveMenu = new JMenu("Save");
        saveMenu.setMnemonic(KeyEvent.VK_S);
        JMenu loadMenu = new JMenu("Load");
        loadMenu.setMnemonic(KeyEvent.VK_L);
        JMenu presetsMenu = new JMenu("Presets");
        JMenu toolSettingsMenu = new JMenu("Settings");
        JMenu terrainMenu = new JMenu("Terrain");

        // Build the save menu
        saveSettingsMenuButton = new JButton("Save Settings");
        saveAgentsMenuButton = new JButton("Save Agents");
        saveEnvironmentSettingsMenuButton = new JButton("Save Environment");
        saveTerrainSettingsMenuButton = new JButton("Save Terrain Settings");
        saveTerrainMenuButton = new JButton("Save Terrain");
        saveMenu.add(saveSettingsMenuButton);
        saveMenu.add(saveAgentsMenuButton);
        saveMenu.add(saveEnvironmentSettingsMenuButton);
        saveMenu.add(saveTerrainSettingsMenuButton);
        saveMenu.add(saveTerrainMenuButton);

        // Build the load menu
        loadSettingsMenuButton = new JButton("Load Settings");
        loadAgentsMenuButton = new JButton("Load Agents");
        loadEnvironmentSettingsMenuButton = new JButton("Load Environment");
        loadTerrainSettingsMenuButton = new JButton("Load Terrain Settings");
        loadTerrainMenuButton = new JButton("Load Terrain");
        loadMenu.add(loadSettingsMenuButton);
        loadMenu.add(loadAgentsMenuButton);
        loadMenu.add(loadEnvironmentSettingsMenuButton);
        loadMenu.add(loadTerrainSettingsMenuButton);
        loadMenu.add(loadTerrainMenuButton);


        // Build the presets menu
        preset1Button = new JButton("Rainbow");
        preset2Button = new JButton("Predator Prey");
        preset3Button = new JButton("Mutating Rainbow");
        preset4Button = new JButton("Colour Cavern");
        preset5Button = new JButton("Predator Prey Evolution");
        preset6Button = new JButton("Default");
        presetsMenu.add(preset1Button);
        presetsMenu.add(preset2Button);
        presetsMenu.add(preset3Button);
        presetsMenu.add(preset4Button);
        presetsMenu.add(preset5Button);
        presetsMenu.add(preset6Button);

        // Build the tool settings menu
        toggleControlsButton = new JButton("Toggle Controls");
        toolSettingsMenu.add(toggleControlsButton);

        toggleRightPanelButton = new JButton("Toggle Right Panel");
        toolSettingsMenu.add(toggleRightPanelButton);

        toggleLeftPanelButton = new JButton("Toggle Left Panel");
        toolSettingsMenu.add(toggleLeftPanelButton);

        generateCave = new JButton("Generate Cave");
        terrainMenu.add(generateCave);

        generateCaveSystem = new JButton("Generate Cave System");
        terrainMenu.add(generateCaveSystem);

        fillTerrain = new JButton("Fill Terrain");
        fillTerrain.setBackground(new Color(230, 230, 230));
        terrainMenu.add(fillTerrain);

        clearTerrain = new JButton("Clear Terrain");
        clearTerrain.setBackground(new Color(230, 230, 230));
        terrainMenu.add(clearTerrain);

        terrainSettings = new JButton("Terrain Settings");
        terrainSettings.setBackground(new Color(200, 200, 200));
        terrainMenu.add(terrainSettings);

        menuBar.add(new JLabel("|"));
        menuBar.add(saveMenu);
        menuBar.add(new JLabel("|"));
        menuBar.add(loadMenu);
        menuBar.add(new JLabel("|"));
        menuBar.add(presetsMenu);
        menuBar.add(new JLabel("|"));
        menuBar.add(toolSettingsMenu);
        menuBar.add(new JLabel("|"));
        menuBar.add(terrainMenu);
        menuBar.add(new JLabel("|"));
        menuBar.setFont(new Font("Dialog", Font.BOLD, 12));
        setJMenuBar(menuBar);

        // Build the left panel and then add it to the frame
        agentEditorPanel = new AgentEditorPanel();
        environmentSettingsPanel = new EnvironmentSettingsPanel();
        leftPanel = new JPanel(new GridLayout(2, 1));
        leftPanel.setBackground(Color.red);
        leftPanel.add(agentEditorPanel);
        leftPanel.add(environmentSettingsPanel);
        add(leftPanel);

        // Build the centre panel and add it to the frame
        JPanel centerPanel = new JPanel();
        centerPanel.setLayout(new GridBagLayout());
        agentSelectorPanel = new AgentSelectorPanel();
        simulationPanel = new SimulationPanel();
        simulationControlPanel = new SimulationControlPanel();
        GridBagConstraints c = new GridBagConstraints();
        c.weightx = 1;
        c.weighty = 1;
        centerPanel.add(agentSelectorPanel, c);
        c.gridy = 1;
        centerPanel.add(simulationPanel, c);
        c.gridy = 2;
        centerPanel.add(simulationControlPanel, c);
        this.add(centerPanel);

        // Build the right panel and add it to the frame
        rightPanel = new JPanel(new GridLayout(1, 1));
        diagnosticsPanel = new DiagnosticsPanel();
        rightPanel.add(diagnosticsPanel);
        this.add(rightPanel);

        setResizable(true);
        pack();
        setLocationRelativeTo(null);
    }

    /**
     * Updates the simulationPanels image and then repaints the frame.
     */
    public void updateSimulationPanel(BufferedImage worldImage) {
        simulationPanel.updateWorldImage(worldImage);
        repaint();
    }

    /**
     * Toggles the visibility of all the subviews.
     */
    public void toggleControls() {
        rightPanel.setVisible(!rightPanel.isVisible());
        leftPanel.setVisible(!leftPanel.isVisible());
        agentSelectorPanel.setVisible(!agentSelectorPanel.isVisible());
        simulationControlPanel.setVisible(!simulationControlPanel.isVisible());
        pack();
    }

    public void toggleRightPanel() {
        rightPanel.setVisible(!rightPanel.isVisible());
        pack();
    }

    public void toggleLeftPanel() {
        leftPanel.setVisible(!leftPanel.isVisible());
        pack();
    }

    /**
     * Opens the terrain settings dialog and returns the terrain settings when the dialog is closed.
     */
    public TerrainSettings openTerrainSettings(TerrainSettings terrainSettings) {
        TerrainSettingsDialog terrainSettingsDialog = new TerrainSettingsDialog(this, terrainSettings);
        return terrainSettingsDialog.getTerrainSettings();
    }

    public AgentEditorPanel getAgentEditorPanel() {
        return agentEditorPanel;
    }

    public AgentSelectorPanel getActiveAgentsPanel() {
        return agentSelectorPanel;
    }

    public DiagnosticsPanel getDiagnosticsPanel() {
        return diagnosticsPanel;
    }

    public SimulationControlPanel getSimulationControlPanel() {
        return simulationControlPanel;
    }

    public EnvironmentSettingsPanel getEnvironmentSettingsPanel() {
        return environmentSettingsPanel;
    }

    public JButton getSaveAgentsMenuButton() {
        return saveAgentsMenuButton;
    }

    public JButton getLoadAgentsMenuButton() {
        return loadAgentsMenuButton;
    }

    public JButton getSaveEnvironmentSettingsMenuButton() {
        return saveEnvironmentSettingsMenuButton;
    }

    public JButton getLoadEnvironmentSettingsMenuButton() {
        return loadEnvironmentSettingsMenuButton;
    }

    public JFileChooser getFileChooser() {
        return fileChooser;
    }

    public JButton getToggleControlsButton() {
        return toggleControlsButton;
    }

    public JButton getSaveSettingsMenuButton() {
        return saveSettingsMenuButton;
    }

    public JButton getLoadSettingsMenuButton() {
        return loadSettingsMenuButton;
    }

    public JButton getTerrainSettings() {
        return terrainSettings;
    }

    public JButton getClearTerrain() {
        return clearTerrain;
    }

    public JButton getFillTerrain() {
        return fillTerrain;
    }

    public JButton getGenerateCave() {
        return generateCave;
    }

    public JButton getGenerateCaveSystem() {
        return generateCaveSystem;
    }

    public JButton getPreset1Button() {
        return preset1Button;
    }

    public JButton getPreset2Button() {
        return preset2Button;
    }

    public JButton getPreset3Button() {
        return preset3Button;
    }

    public JButton getPreset4Button() {
        return preset4Button;
    }

    public JButton getPreset5Button() {
        return preset5Button;
    }

    public JButton getPreset6Button() {
        return preset6Button;
    }

    public JButton getLoadTerrainSettingsMenuButton() {
        return loadTerrainSettingsMenuButton;
    }

    public JButton getSaveTerrainSettingsMenuButton() {
        return saveTerrainSettingsMenuButton;
    }

    public JButton getSaveTerrainMenuButton() {
        return saveTerrainMenuButton;
    }

    public JButton getLoadTerrainMenuButton() {
        return loadTerrainMenuButton;
    }

    public JButton getToggleRightPanelButton() {
        return toggleRightPanelButton;
    }

    public JButton getToggleLeftPanelButton() {
        return toggleLeftPanelButton;
    }

    public TerrainControlPanel getTerrainControlPanel() {
        return terrainControlPanel;
    }

    public SimulationPanel getSimulationPanel() {
        return simulationPanel;
    }
}