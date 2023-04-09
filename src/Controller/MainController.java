package Controller;

import Simulation.Environment.Location;
import Simulation.Simulation;
import Simulation.SimulationUtility.SimulationSettings.*;
import Simulation.SimulationUtility.Terrain.*;
import Simulation.SimulationUtility.Terrain.Point;
import View.UserInterface;
import org.apache.commons.io.FilenameUtils;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.MalformedURLException;

/**
 * Controls the Simulation and UserInterface classes. Listens for user input from the UserInterface class and controls
 * the Simulation class using it.
 *
 * @author Sam Burchmore
 * @version 1.0a
 * @since 1.0a
 */
public class MainController {

    // The controllers instance of the UserInterface
    final private UserInterface userInterface;
    // The controllers instance of the simulation
    final private Simulation simulation;

    // If the simulation is in the process of being cycled, this flag is true, otherwise it is false.
    private boolean runFlag;
    // If the simulation is currently in a cycle, this flag is true, otherwise it is false.
    private boolean cycleFlag;
    /*
     Set to true when the simulation is run for a set amount of steps, set to false if the stop/start button is clicked.
     runSimulation(int stepsToRun) will stop running if runningNSteps is false. This is allows the simulation to be paused when
     being run for a set amount of steps.
     */
    private boolean runningNSteps;
    // The scale the simulation view should be painted at.
    private int scale;
    // The dialog which displays the loading image while the constructor is running.
    private JDialog loadingDialog;

    final private SimulationController simulationController;
    final private ViewController viewController;
    final private FileHandler fileHandler;

    /**
     * Constructs and initialises all component objects while displaying the loadingDialog.
     * <p>
     *
     * @param size              initial simulation size
     * @param minEnergyLevel    initial minimum energy level
     * @param maxEnergyLevel    initial maximum energy level
     * @param energyRegenChance initial energy regeneration chance
     * @param energyRegenAmount initial energy regeneration amount
     * @throws IOException in the event the loadingDialog image cannot be found
     */
    public MainController(int size, int minEnergyLevel, int maxEnergyLevel, double energyRegenChance, int energyRegenAmount) throws IOException, InterruptedException {
        this.viewController = new ViewController();
        this.fileHandler = new FileHandler();
        viewController.showLoadingDialog();
        this.scale = 600 / size;
        this.userInterface = new UserInterface();
        this.simulation = new Simulation(size, minEnergyLevel, maxEnergyLevel, energyRegenChance, energyRegenAmount);
        this.simulationController = new SimulationController();
        simulationController.initDiagnostics();
        initController();
        fileHandler.loadOnOpen();
        viewController.initView();
        viewController.deleteLoadingDialog();
        this.runFlag = false;
        this.cycleFlag = false;
        this.runningNSteps = false;
    }

    /**
     * Adds action listeners to all the UserInterface buttons along with a window listener for the frame being closed.
     */
    public void initController() {
        // Add action listeners to Simulation Control Panel
        userInterface.getSimulationControlPanel().getRunStepButton().addActionListener(e -> simulationController.runOneStep());
        userInterface.getSimulationControlPanel().getPopulateButton().addActionListener(e -> simulationController.populateEnvironment());
        userInterface.getSimulationControlPanel().getClearButton().addActionListener(e -> simulationController.clearAgents());
        userInterface.getSimulationControlPanel().getRunNStepsButton().addActionListener(e -> simulationController.runNSteps());
        userInterface.getSimulationControlPanel().getStopStartButton().addActionListener(e -> simulationController.toggleSimulation());
        userInterface.getSimulationControlPanel().getReplenishEnvironmentEnergyButton().addActionListener(e -> simulationController.replenishEnvironment());

        // Add action listeners to Environment Control Panel
        userInterface.getEnvironmentSettingsPanel().getRefreshSettingsButton().addActionListener(e -> simulationController.updateEnvironmentSettings());

        // Add action listeners to Active Agents Panel
        ((JButton) userInterface.getActiveAgentsPanel().getAgent0Button()).addActionListener(e -> setEditingAgent(0));
        ((JButton) userInterface.getActiveAgentsPanel().getAgent1Button()).addActionListener(e -> setEditingAgent(1));
        ((JButton) userInterface.getActiveAgentsPanel().getAgent2Button()).addActionListener(e -> setEditingAgent(2));
        ((JButton) userInterface.getActiveAgentsPanel().getAgent3Button()).addActionListener(e -> setEditingAgent(3));
        ((JButton) userInterface.getActiveAgentsPanel().getAgent4Button()).addActionListener(e -> setEditingAgent(4));
        ((JButton) userInterface.getActiveAgentsPanel().getAgent5Button()).addActionListener(e -> setEditingAgent(5));
        ((JButton) userInterface.getActiveAgentsPanel().getAgent6Button()).addActionListener(e -> setEditingAgent(6));
        ((JButton) userInterface.getActiveAgentsPanel().getAgent7Button()).addActionListener(e -> setEditingAgent(7));

        // Add action listener to Agent Editor Panel
        userInterface.getAgentEditorPanel().getUpdateSettingsButton().addActionListener(e -> setEditingAgent(simulation.getAgentEditor().getEditingAgentIndex()));

        // Add action listeners to Diagnostics Panel
        userInterface.getDiagnosticsPanel().getClearLogButton().addActionListener(e -> viewController.clearLog());

        // Add action listeners to menu buttons
        userInterface.getSaveAgentsMenuButton().addActionListener(e -> fileHandler.saveAgents());
        userInterface.getLoadAgentsMenuButton().addActionListener(e -> fileHandler.loadAgents());
        userInterface.getSaveEnvironmentSettingsMenuButton().addActionListener(e -> fileHandler.saveEnvironment());
        userInterface.getLoadEnvironmentSettingsMenuButton().addActionListener(e -> fileHandler.loadEnvironment());
        userInterface.getSaveSettingsMenuButton().addActionListener(e -> fileHandler.saveSettings());
        userInterface.getLoadSettingsMenuButton().addActionListener(e -> fileHandler.loadSettings());
        userInterface.getLoadTerrainSettingsMenuButton().addActionListener(e -> fileHandler.loadTerrainSettings());
        userInterface.getSaveTerrainSettingsMenuButton().addActionListener(e -> fileHandler.saveTerrainSettings());
        userInterface.getSaveTerrainMenuButton().addActionListener(e -> fileHandler.saveTerrainMask());
        userInterface.getLoadTerrainMenuButton().addActionListener(e -> fileHandler.loadTerrainMask());
        userInterface.getToggleControlsButton().addActionListener(e -> viewController.toggleControls());
        userInterface.getTerrainSettings().addActionListener(e -> viewController.openTerrainSettings());
        userInterface.getClearTerrain().addActionListener(e -> simulationController.getTerrainController().clearTerrain());
        userInterface.getFillTerrain().addActionListener(e -> simulationController.getTerrainController().fillTerrain());
        userInterface.getGenerateCave().addActionListener(e -> simulationController.paintCave());
        userInterface.getGenerateCaveSystem().addActionListener(e -> simulationController.paintCaveSystem());
        userInterface.getPreset1Button().addActionListener(e -> fileHandler.loadPreset(0));
        userInterface.getPreset2Button().addActionListener(e -> fileHandler.loadPreset(1));
        userInterface.getPreset3Button().addActionListener(e -> fileHandler.loadPreset(2));
        userInterface.getPreset4Button().addActionListener(e -> fileHandler.loadPreset(3));
        userInterface.getPreset5Button().addActionListener(e -> fileHandler.loadPreset(4));
        userInterface.getPreset6Button().addActionListener(e -> fileHandler.loadPreset(5));
        // Add window listener to mainView
        userInterface.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                fileHandler.saveOnClose();
            }
        });
        userInterface.getToggleRightPanelButton().addActionListener(e -> userInterface.toggleRightPanel());
        userInterface.getToggleLeftPanelButton().addActionListener(e -> userInterface.toggleLeftPanel());
        userInterface.getTerrainControlPanel().getFillButton().addActionListener(e -> simulationController.getTerrainController().paintTerrain(0));
        userInterface.getTerrainControlPanel().getClearButton().addActionListener(e -> simulationController.getTerrainController().paintTerrain(1));
        ((JButton) userInterface.getTerrainControlPanel().getPaintPointButton()).addActionListener(e -> simulationController.getTerrainController().paintTerrain(2));
        ((JButton) userInterface.getTerrainControlPanel().getPaintSquareButton()).addActionListener(e -> simulationController.getTerrainController().paintTerrain(3));
        ((JButton) userInterface.getTerrainControlPanel().getPaintCircleButton()).addActionListener(e -> simulationController.getTerrainController().paintTerrain(4));
        ((JButton) userInterface.getTerrainControlPanel().getPaintLineButton()).addActionListener(e -> simulationController.getTerrainController().paintTerrain(5));
        ((JButton) userInterface.getTerrainControlPanel().getPaintBendingLineButton()).addActionListener(e -> simulationController.getTerrainController().paintTerrain(6));
        userInterface.getSimulationPanel().addMouseListener(new MouseListener());
        userInterface.getTerrainControlPanel().getSizeSpinner().addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                simulationController.getTerrainController().setSize(userInterface.getTerrainControlPanel().getSizeValue());
            }
        });

    }

    public class MouseListener implements java.awt.event.MouseListener {
        @Override
        public void mouseClicked(MouseEvent e) {
            if (SwingUtilities.isLeftMouseButton(e)) {
                userInterface.getTerrainControlPanel().setLocation1Value(new Location(e.getX(), e.getY()));
            }
            if (SwingUtilities.isRightMouseButton(e)) {
                userInterface.getTerrainControlPanel().setLocation2Value(new Location(e.getX(), e.getY()));
            }
        }

        @Override
        public void mousePressed(MouseEvent e) {

        }

        @Override
        public void mouseReleased(MouseEvent e) {

        }

        @Override
        public void mouseEntered(MouseEvent e) {

        }

        @Override
        public void mouseExited(MouseEvent e) {

        }
    }

    /**
     * Saves the open agents settings and then opens and displays the agent in the agent editor which corresponds the index param.
     *
     * @param index the agent in the editor to open.
     */
    public void setEditingAgent(int index) {
        // Store the old agent settings
        AgentSettings agentSettings = userInterface.getAgentEditorPanel().getAgentSettings();
        if (agentSettings.getName().isEmpty()) {
            agentSettings.setName(simulation.getAgentEditor().getAgentSettings(index).getName());
        }
        simulation.getAgentEditor().setEditingAgentSettings(agentSettings);

        // Update the agent selector panel
        userInterface.getActiveAgentsPanel().setAgentSelector(simulation.getAgentEditor().getEditingAgentIndex(), agentSettings.getSeedColor(), agentSettings.getName());

        // Update the agent editor
        simulation.getAgentEditor().setEditingAgentIndex(index);

        // update the agent editor panel
        userInterface.getAgentEditorPanel().setAgentSettings(simulation.getAgentEditor().getEditingAgentSettings());
        simulation.updateAgentNames();
        userInterface.getDiagnosticsPanel().setAgentStats(simulation.getDiagnostics().getAgentStats());
    }

    /**
     * Groups methods which control the Simulation instance.
     *
     * @author Sam Burchmore
     * @version 1.0a
     * @since 1.0a
     */
    public class SimulationController {

        private final TerrainController terrainController;

        public SimulationController() {
            this.terrainController = new TerrainController();
        }

        public TerrainController getTerrainController() {
            return terrainController;
        }

        /**
         * Starts the simulation if it's not running, otherwise stops it.
         */
        public void toggleSimulation() {
            // If the simulation is being run for a set amount of times, then stop it.
            if (runningNSteps) {
                runningNSteps = false;
                runFlag = false;
            } else {
                runFlag = !runFlag;
                // If the runFlag is now true, then update the controls and start the simulation.
                if (runFlag) {
                    viewController.setSimulationControls(true);
                    runSimulation();
                } else {
                    viewController.setSimulationControls(false);
                }
            }
        }

        /**
         * Runs the simulation until the run flag is set to false.
         */
        public void runSimulation() {
            // Create and execute a swing worker which will run the simulation in the background.
            SwingWorker<Void, BufferedImage> swingWorker = new SwingWorker<>() {
                @Override
                protected Void doInBackground() {
                    while (runFlag) {
                        cycleFlag = true;
                        runStep();
                        cycleFlag = false;
                    }
                    return null;
                }
            };
            swingWorker.execute();
        }

        /**
         * Runs the simulation for a set number of steps or until the toggleSimulation() method is call-ed.
         *
         * @param stepsToRun how many steps to run for
         */
        public void runSimulation(int stepsToRun) {
            // Create and execute a swing worker which runs the simulation for a set amount of steps.
            SwingWorker<Void, BufferedImage> swingWorker = new SwingWorker<>() {
                @Override
                protected Void doInBackground() {
                    runningNSteps = true;
                    viewController.setSimulationControls(true);
                    for (int i = 0; i < stepsToRun; i++) {
                        if (!runningNSteps) {
                            viewController.setSimulationControls(false);
                            viewController.logMsg("[SIMULATION]: Simulation ran for " + i + " steps.");
                            break;
                        }
                        cycleFlag = true;
                        runStep();
                        cycleFlag = false;
                    }
                    viewController.setSimulationControls(false);
                    runningNSteps = false;
                    return null;
                }
            };
            swingWorker.execute();
        }

        /**
         * Runs the simulation for one step and updates the environment image after.
         */
        public void runStep() {
            simulation.setDiagnosticsVerbosity(userInterface.getDiagnosticsPanel().getDiagnosticsVerbosity());
            simulation.cycle();
            simulation.getDiagnostics().iterateStep();
            viewController.updateDiagnosticsPanel();
            viewController.updateSimulationView();
            if (simulation.getDiagnostics().logMessagesInQueue()) {
                viewController.logMsg(simulation.getDiagnostics().printLogQueue());
            }
        }

        /**
         * Calls the runStep method once if the simulation is not currently running.
         */
        public void runOneStep() {
            if (!cycleFlag) {
                runStep();
            } else {
                viewController.logMsg("[SIMULATION]: Simulation is already running.");
            }
        }

        /**
         * If the simulation is not currently running, runs the simulation for a set number of steps.
         */
        public void runNSteps() {
            if (!cycleFlag) {
                int n = (int) userInterface.getSimulationControlPanel().getRunNStepsSpinner().getValue();
                runSimulation(n);
            } else {
                viewController.logMsg("[SIMULATION]: Simulation is already running.");
            }
        }

        /**
         * Populates the environment with agents if the simulation is not currently running.
         */
        public void populateEnvironment() {
            if (!cycleFlag) {
                simulation.populate((double) userInterface.getSimulationControlPanel().getPopulationDensitySpinner().getValue());
                viewController.updateSimulationView();
                viewController.updateDiagnosticsPanel();
                viewController.logMsg("[ENVIRONMENT]: Environment populated at a density of " + userInterface.getSimulationControlPanel().getPopulationDensitySpinner().getValue() + "%.");
                simulation.getDiagnostics().setExtinctFlags(0);
            } else {
                viewController.logMsg("[SIMULATION]: Please stop the simulation first.");
            }
        }

        /**
         * Removes all agents from the environment and clears the agents stats from the diagnostics panel
         */
        public void clearAgents() {
            if (!cycleFlag) {
                simulation.clearAgents();
                viewController.updateSimulationView();
                simulation.getDiagnostics().clearDiagnostics();
                userInterface.getDiagnosticsPanel().setAgentStats(simulation.getDiagnostics().getAgentStats());
                userInterface.getDiagnosticsPanel().clearStepLabel();
                viewController.logMsg("[ENVIRONMENT]: Agents cleared.");
            } else {
                viewController.logMsg("[SIMULATION]: Please stop the simulation first.");
            }
        }

        /**
         * Restores every tile in the environment to its maximum energy.
         */
        public void replenishEnvironment() {
            simulation.replenishEnvironmentEnergy();
            viewController.updateSimulationView();
            userInterface.getDiagnosticsPanel().addLogMessage("[ENVIRONMENT]: Energy Replenished.");
            viewController.updateDiagnosticsPanel();
        }

        /**
         * Updates the environments settings to reflect those in the environment settings panel.
         */
        public void updateEnvironmentSettings() {
            EnvironmentSettings environmentSettings = userInterface.getEnvironmentSettingsPanel().getEnvironmentSettings();
            if (environmentSettings.getSize() != simulation.getEnvironment().getSize() && cycleFlag) {
                environmentSettings.setSize(simulation.getEnvironment().getSize());
                viewController.logMsg("[ENVIRONMENT]: Environment size cannot be changed while the simulation is running.");
                userInterface.getEnvironmentSettingsPanel().getEnvironmentSizeSpinner().setValue(environmentSettings.getSize());
            }
            if (environmentSettings.getSize() > 600) {
                environmentSettings.setSize(simulation.getEnvironment().getSize());
                viewController.logMsg("[ENVIRONMENT]: Environment size must be less than 600.");
                userInterface.getEnvironmentSettingsPanel().getEnvironmentSizeSpinner().setValue(environmentSettings.getSize());
            }
            if (environmentSettings.getSize() < 1) {
                environmentSettings.setSize(simulation.getEnvironment().getSize());
                viewController.logMsg("[ENVIRONMENT]: Environment size must be more than 0.");
                userInterface.getEnvironmentSettingsPanel().getEnvironmentSizeSpinner().setValue(environmentSettings.getSize());
            }
            if (600.0 % environmentSettings.getSize() != 0) {
                environmentSettings.setSize(simulation.getEnvironment().getSize());
                viewController.logMsg("[ENVIRONMENT]: Environment size must be a factor of 600.");
                userInterface.getEnvironmentSettingsPanel().getEnvironmentSizeSpinner().setValue(environmentSettings.getSize());
            }
            simulation.setEnvironmentSettings(environmentSettings);
            scale = 600 / environmentSettings.getSize();
            viewController.updateSimulationView();
            viewController.logMsg("[ENVIRONMENT]: Settings updated to- \n" + simulation.getEnvironment().toString());
            viewController.updateDiagnosticsPanel();
            viewController.updateSimulationView();
        }

        /**
         * Retrieves an instance of the current simulation settings.
         *
         * @return the new simulation settings
         */
        public SimulationSettings getSimulationSettings() {
            return simulation.getSimulationSettings();
        }

        /**
         * Sets the simulation settings to the input preset.
         *
         * @param simulationSettings the simulation settings to update the simulation to
         */
        public void setSimulationSettings(SimulationSettings simulationSettings) {
            scale = 600 / simulationSettings.environmentSettings().getSize();
            simulation.setSimulationSettings(simulationSettings);
        }

        /**
         * Initialises the diagnostics panel with the agent names and environment energy.
         */
        public void initDiagnostics() {
            simulation.getDiagnostics().setAgentNames(simulation.getAgentEditor().getAgentNames());
            simulation.getDiagnostics().setMaxEnvironmentEnergy(simulation.getEnvironment().getMaxEnergyLevel() * simulation.getEnvironment().getSize() * simulation.getEnvironment().getSize());
            simulation.getDiagnostics().resetCurrentEnvironmentEnergy();
        }

        /**
         * paints a cave on the environment.
         */
        public void paintCave() {
//            Square square300 = new Square(new Point(), 300);
//            Square square275 = new Square(new Point(), 275);
//            Square square250 = new Square(new Point(), 250);
//            Square square225 = new Square(new Point(), 225);
//            Square square200 = new Square(new Point(), 200);
//            Square square175 = new Square(new Point(), 175);
//            Square square150 = new Square(new Point(), 150);
//            Square square125 = new Square(new Point(), 125);
//            Square square100 = new Square(new Point(), 100);
//            Square square75 = new Square(new Point(), 75);
//            Square square50 = new Square(new Point(), 50);
//            Square square25 = new Square(new Point(), 25);
//            simulation.getTerrainGenerator().fillTerrain();
//            //simulation.getTerrainGenerator().paintTerrain(square300, false);
//            simulation.getTerrainGenerator().paintTerrain(square275, false);
//            simulation.getTerrainGenerator().paintTerrain(square250, true);
//            simulation.getTerrainGenerator().paintTerrain(square225, false);
//            simulation.getTerrainGenerator().paintTerrain(square200, true);
//            simulation.getTerrainGenerator().paintTerrain(square175, false);
//            simulation.getTerrainGenerator().paintTerrain(square150, true);
//            simulation.getTerrainGenerator().paintTerrain(square125, false);
//            simulation.getTerrainGenerator().paintTerrain(square100, true);
//            simulation.getTerrainGenerator().paintTerrain(square75, false);
//            simulation.getTerrainGenerator().paintTerrain(square50, true);
//            //simulation.getTerrainGenerator().paintTerrain(square25, true);
            viewController.updateSimulationView();
            viewController.logMsg("[TERRAIN]: Cave generated with - \n" + simulation.getTerrainGenerator().getTerrainSettings().toString());
        }

        /**
         * Generates a cave system on the environment.
         */
        public void paintCaveSystem() {
            viewController.updateSimulationView();
            viewController.logMsg("[TERRAIN]: Cave System generated with - \n" + simulation.getTerrainGenerator().getTerrainSettings().toString());
        }

        public class TerrainController {

            private int size;

            public TerrainController() {
                size = 100;
            }

            public void paintTerrain(int shapeID) {
                Location location = userInterface.getTerrainControlPanel().getLocation1Value();
                boolean paintFlag = userInterface.getTerrainControlPanel().getPaintFlagValue();
                switch (shapeID) {
                    case 0 -> simulation.getTerrainGenerator().fillTerrain();
                    case 1 -> simulation.getTerrainGenerator().clearTerrain();
                    case 2 -> simulation.getTerrainGenerator().paintTerrain(new Point(), paintFlag, location);
                    case 3 -> simulation.getTerrainGenerator().paintTerrain(new Square(new Point(), size), paintFlag, location);
                    case 4 -> simulation.getTerrainGenerator().paintTerrain(new Circle(new Point(), size), paintFlag, location);
                    case 5 -> simulation.getTerrainGenerator().paintTerrain(new RandomVariableBasicLine(
                            new ClusterCircle(new Circle(new Point(), 2), 5, 1500), size,
                                    new Direction(userInterface.getTerrainControlPanel().getDxValue(), userInterface.getTerrainControlPanel().getDyValue()),
                                    new Variance(userInterface.getTerrainControlPanel().getUpperBoundValue(),
                                            userInterface.getTerrainControlPanel().getLowerBoundValue(),
                                            userInterface.getTerrainControlPanel().getVarianceValue())),
                            paintFlag, location);
//                    case 5 ->
//                        simulation.getTerrainGenerator().paintTerrain(new BasicLine(new Circle(new Point(), 5), size,
//                                new Direction(userInterface.getTerrainControlPanel().getDxValue(), userInterface.getTerrainControlPanel().getDyValue())),
//                                paintFlag,
//                                location);
                    case 6 -> simulation.getTerrainGenerator().paintTerrain(new BendingCompoundLine(new RandomVariableBasicLine(
                                    new ClusterCircle(new Circle(new Point(), 2), 5, 1500), size/10,
                                    new Direction(userInterface.getTerrainControlPanel().getDxValue(), userInterface.getTerrainControlPanel().getDyValue()),
                                    new Variance(userInterface.getTerrainControlPanel().getUpperBoundValue(),
                                            userInterface.getTerrainControlPanel().getLowerBoundValue(),
                                            userInterface.getTerrainControlPanel().getVarianceValue())),
                                    size, new Direction(userInterface.getTerrainControlPanel().getDxBendRangeValue(), userInterface.getTerrainControlPanel().getDyBendRangeValue()),
                                    10000),
                            paintFlag, location);
                }
                viewController.updateView();
            }

            /**
             * Clears the environment of all terrain.
             */
            public void clearTerrain() {
                simulation.getTerrainGenerator().clearTerrain();
                viewController.updateSimulationView();
                viewController.logMsg("[TERRAIN]: Terrain cleared.");
            }

            /**
             * Sets the environment to all terrain.
             */
            public void fillTerrain() {
                simulation.getTerrainGenerator().fillTerrain();
                viewController.updateSimulationView();
                viewController.logMsg("[TERRAIN]: Terrain filled.");
            }

            public int getSize() {
                return size;
            }

            public void setSize(int size) {
                this.size = size;
            }
        }
    }

    /**
     * Groups methods which control the UserInterface instance.
     *
     * @author Sam Burchmore
     * @version 1.0a
     * @since 1.0a
     */
    public class ViewController {
        /**
         * Updates all panels.
         */
        public void updateView() {
            updateSimulationView();
            updateActiveAgentsPanel();
            updateEnvironmentSettingsPanel();
            updateDiagnosticsPanel();
            updateAgentEditorPanel();
        }

        /**
         * Updates the environment image to reflect the environment in the model.
         */
        public void updateSimulationView() {
            userInterface.updateSimulationPanel(simulation.getEnvironment().toBufferedImage(scale));
        }

        /**
         * Add a log message to the diagnostics panels text log.
         *
         * @param logMsg the string to log
         */
        public void logMsg(String logMsg) {
            userInterface.getDiagnosticsPanel().addLogMessage(logMsg);
        }

        /**
         * Updates the values in the environment settings panel to match those of the current environment.
         */
        public void updateEnvironmentSettingsPanel() {
            userInterface.getEnvironmentSettingsPanel().setEnvironmentSettings(simulation.getEnvironment().getEnvironmentSettings());
        }

        /**
         * Updates the data in the diagnostics panel to match the data in the diagnostics panel.
         */
        public void updateDiagnosticsPanel() {
            userInterface.getDiagnosticsPanel().setAgentStats(simulation.getDiagnostics().getAgentStats());
            userInterface.getDiagnosticsPanel().setStepLabel(simulation.getDiagnostics().getStep());
            userInterface.getDiagnosticsPanel().setEnvironmentStats(simulation.getDiagnostics().getEnvironmentStats());
        }

        /**
         * Updates the values in the agent editor panel to match those of the currently editing agent.
         */
        public void updateAgentEditorPanel() {
            userInterface.getAgentEditorPanel().setAgentSettings(simulation.getAgentEditor().getEditingAgentSettings());
        }

        /**
         * Updates the data in the active agents panel to match the active agents in the agent editor class.
         */
        public void updateActiveAgentsPanel() {
            for (int i = 0; i < 8; i++) {
                userInterface.getActiveAgentsPanel().setAgentSelector(i, simulation.getAgentEditor().getAgent(i).getAttributes().getSeedColor(), simulation.getAgentEditor().getAgent(i).getAttributes().getName());
            }
        }

        /**
         * Clear all text from the info log.
         */
        public void clearLog() {
            userInterface.getDiagnosticsPanel().clearLog();
        }

        /**
         * Toggles the control and settings panels.
         */
        public void toggleControls() {
            userInterface.toggleControls();
        }

        /**
         * Initialises the view and then sets it to visible.
         */
        public void initView() {
            viewController.updateSimulationView();
            viewController.updateActiveAgentsPanel();
            viewController.updateAgentEditorPanel();
            viewController.updateEnvironmentSettingsPanel();
            viewController.updateDiagnosticsPanel();
            userInterface.setVisible(true);
            toggleControls();
        }

        /**
         * Opens the terrain settings dialog, updates the simulations terrain settings when its closed.
         */
        public void openTerrainSettings() {
            simulation.getTerrainGenerator().setTerrainSettings(userInterface.openTerrainSettings(simulation.getTerrainGenerator().getTerrainSettings()));
        }

        /**
         * Displays the loading dialog while the system is initialised.
         *
         * @throws MalformedURLException if the image URL is malformed
         */
        private void showLoadingDialog() throws MalformedURLException {
            loadingDialog = new JDialog();
            JLabel loadingIcon = new JLabel();
            loadingIcon.setIcon(new ImageIcon(new File("src\\images\\loading-image.png").toURI().toURL()));
            loadingDialog.add(loadingIcon);
            loadingDialog.setUndecorated(true);
            loadingDialog.pack();
            loadingDialog.setLocationRelativeTo(null);
            loadingDialog.setVisible(true);
        }

        /**
         * Disposes of the loading dialog when the system is finished loading.
         */
        private void deleteLoadingDialog() {
            loadingDialog.dispose();
        }

        /**
         * Sets the simulation controls to running mode, or not running mode.
         * <p>
         * If the param is false, then all simulation controls bar replenish energy will be disabled and the startStop button will display stop.
         * If the param is true then all controls are enabled, and startStop is set to display true.
         *
         * @param toggle false if controls should be in running mode, true if not
         */
        public void setSimulationControls(boolean toggle) {
            if (toggle) {
                userInterface.getSimulationControlPanel().getStopStartButton().setBackground(new Color(200, 50, 20));
                userInterface.getSimulationControlPanel().getStopStartButton().setText("Stop");
            } else {
                userInterface.getSimulationControlPanel().getStopStartButton().setBackground(new Color(100, 220, 10));
                userInterface.getSimulationControlPanel().getStopStartButton().setText("Start");
            }
            userInterface.getSimulationControlPanel().getClearButton().setEnabled(!toggle);
            userInterface.getSimulationControlPanel().getRunStepButton().setEnabled(!toggle);
            userInterface.getSimulationControlPanel().getPopulateButton().setEnabled(!toggle);
            userInterface.getSimulationControlPanel().getRunNStepsButton().setEnabled(!toggle);
        }

    }

    /**
     * Groups methods which handle the saving and loading of settings.
     *
     * @author Sam Burchmore
     * @version 1.0a
     * @since 1.0a
     */
    public class FileHandler {
        /**
         * Tries to save the simulation settings at the \data location.
         */
        public void saveOnClose() {
            try {
                File file = new File("src\\data\\settings.dat");
                FileOutputStream fileOutputStream = new FileOutputStream(file);
                BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(fileOutputStream);
                ObjectOutputStream objectOutputStream = new ObjectOutputStream(bufferedOutputStream);

                SimulationSettings simulationSettings = simulationController.getSimulationSettings();

                objectOutputStream.writeObject(simulationSettings);
                objectOutputStream.close();

            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        /**
         * Trys to open the simulation settings found \data.
         */
        public void loadOnOpen() {
            try {
                File file = new File("src\\data\\settings.dat");
                loadSettings(file);

            } catch (IOException | ClassNotFoundException | ClassCastException e) {
                viewController.logMsg("[SYSTEM]: Something went wrong and the file could not be read.");
            }
        }

        /**
         * Asks the user for a location and then tries to save the current simulation settings there.
         */
        public void saveSettings() {
            if (runFlag) {
                viewController.logMsg("[SYSTEM]: Please pause the simulation first.");
                return;
            }
            if (userInterface.getFileChooser().showSaveDialog(userInterface) == JFileChooser.APPROVE_OPTION) { // User has provided a path
                File file = userInterface.getFileChooser().getSelectedFile();
                if (FilenameUtils.getExtension(file.getPath()).equals("dat")) {
                    try {
                        FileOutputStream fileOutputStream = new FileOutputStream(file);
                        BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(fileOutputStream);
                        ObjectOutputStream objectOutputStream = new ObjectOutputStream(bufferedOutputStream);

                        SimulationSettings simulationSettings = simulationController.getSimulationSettings();

                        objectOutputStream.writeObject(simulationSettings);
                        objectOutputStream.close();

                        viewController.logMsg("[SYSTEM]: Settings Saved.");

                    } catch (IOException e) {
                        viewController.logMsg("[SYSTEM]: Something has gone wrong, unable to save file.");
                    }
                } else {
                    viewController.logMsg("[SYSTEM]: File type must be .dat.");
                }
            }
        }

        /**
         * Asks the user for a location and then tries to load the simulation settings found there.
         */
        public void loadSettings() {
            if (runFlag) {
                viewController.logMsg("[SYSTEM]: Please pause the simulation first.");
                return;
            }
            if (userInterface.getFileChooser().showOpenDialog(userInterface) == JFileChooser.APPROVE_OPTION) { // User has provided a path
                File file = userInterface.getFileChooser().getSelectedFile();
                if (FilenameUtils.getExtension(file.getPath()).equals("dat")) {
                    try {
                        processSettings(file);
                    } catch (IOException | ClassNotFoundException | ClassCastException e) {
                        viewController.logMsg("[SYSTEM]: Something went wrong and the file could not be read.");
                    }
                } else {
                    viewController.logMsg("[SYSTEM]: File type must be .dat.");
                }
            }
        }

        /**
         * Asks the user for a location and then tries to save the current agent settings there.
         */
        public void saveAgents() {
            if (runFlag) {
                viewController.logMsg("[SYSTEM]: Please pause the simulation first.");
                return;
            }
            if (userInterface.getFileChooser().showSaveDialog(userInterface) == JFileChooser.APPROVE_OPTION) { // User has provided a path
                File file = userInterface.getFileChooser().getSelectedFile();
                if (FilenameUtils.getExtension(file.getPath()).equals("dat")) {
                    try {
                        FileOutputStream fileOutputStream = new FileOutputStream(file);
                        BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(fileOutputStream);
                        ObjectOutputStream objectOutputStream = new ObjectOutputStream(bufferedOutputStream);

                        ActiveAgentsSettings activeAgentsSettings = simulation.getAgentEditor().getActiveAgentsSettings();

                        objectOutputStream.writeObject(activeAgentsSettings);
                        objectOutputStream.close();

                        viewController.logMsg("[SYSTEM]: Agents Saved.");

                    } catch (IOException e) {
                        viewController.logMsg("[SYSTEM]: Something has gone wrong, unable to save file.");
                    }
                } else {
                    viewController.logMsg("[SYSTEM]: File type must be .dat.");
                }
            }
        }

        /**
         * Asks the user for a location and then tries to load the agent settings found there.
         */
        public void loadAgents() {
            if (runFlag) {
                viewController.logMsg("[SYSTEM]: Please pause the simulation first.");
                return;
            }
            if (userInterface.getFileChooser().showOpenDialog(userInterface) == JFileChooser.APPROVE_OPTION) { // User has provided a path
                File file = userInterface.getFileChooser().getSelectedFile();
                if (FilenameUtils.getExtension(file.getPath()).equals("dat")) {
                    try {
                        FileInputStream fileInputStream = new FileInputStream(file);
                        BufferedInputStream bufferedInputStream = new BufferedInputStream(fileInputStream);
                        ObjectInputStream objectInputStream = new ObjectInputStream(bufferedInputStream);

                        ActiveAgentsSettings activeAgentsSettings = (ActiveAgentsSettings) objectInputStream.readObject();

                        simulation.getAgentEditor().setActiveAgentsSettings(activeAgentsSettings);
                        viewController.updateView();

                        objectInputStream.close();

                        viewController.logMsg("[SYSTEM]: Agents Loaded.");
                    } catch (IOException | ClassNotFoundException | ClassCastException e) {
                        viewController.logMsg("[SYSTEM]: Something went wrong and the file could not be read.");
                    }
                } else {
                    viewController.logMsg("[SYSTEM]: File type must be .dat.");
                }
            }
        }

        /**
         * Asks the user for a location and then tries to save the current environment settings there.
         */
        public void saveEnvironment() {
            if (runFlag) {
                viewController.logMsg("[SYSTEM]: Please pause the simulation first.");
                return;
            }
            if (userInterface.getFileChooser().showSaveDialog(userInterface) == JFileChooser.APPROVE_OPTION) { // User has provided a path
                File file = userInterface.getFileChooser().getSelectedFile();
                if (FilenameUtils.getExtension(file.getPath()).equals("dat")) {
                    try {
                        FileOutputStream fileOutputStream = new FileOutputStream(file);
                        BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(fileOutputStream);
                        ObjectOutputStream objectOutputStream = new ObjectOutputStream(bufferedOutputStream);

                        EnvironmentSettings environmentSettings = simulation.getEnvironment().getEnvironmentSettings();

                        objectOutputStream.writeObject(environmentSettings);
                        objectOutputStream.close();

                        viewController.logMsg("[SYSTEM]: Environment Saved.");

                    } catch (IOException e) {
                        viewController.logMsg("[SYSTEM]: Something has gone wrong, unable to save file.");
                    }
                } else {
                    viewController.logMsg("[SYSTEM]: File type must be .dat.");
                }
            }
        }

        /**
         * Asks the user for a location and then tries to load the agent environment found there.
         */
        public void loadEnvironment() {
            if (runFlag) {
                viewController.logMsg("[SYSTEM]: Please pause the simulation first.");
                return;
            }
            if (userInterface.getFileChooser().showOpenDialog(userInterface) == JFileChooser.APPROVE_OPTION) { // User has provided a path
                File file = userInterface.getFileChooser().getSelectedFile();
                if (FilenameUtils.getExtension(file.getPath()).equals("dat")) {
                    try {
                        FileInputStream fileInputStream = new FileInputStream(file);
                        BufferedInputStream bufferedInputStream = new BufferedInputStream(fileInputStream);
                        ObjectInputStream objectInputStream = new ObjectInputStream(bufferedInputStream);

                        EnvironmentSettings environmentSettings = (EnvironmentSettings) objectInputStream.readObject();
                        simulationController.clearAgents();
                        scale = 600 / environmentSettings.getSize();
                        simulation.setEnvironmentSettings(environmentSettings);
                        viewController.updateView();

                        objectInputStream.close();

                        viewController.logMsg("[SYSTEM]: Environment Loaded.");
                    } catch (IOException | ClassNotFoundException | ClassCastException e) {
                        viewController.logMsg("[SYSTEM]: Something went wrong and the file could not be read.");
                    }
                } else {
                    viewController.logMsg("[SYSTEM]: File type must be .dat.");
                }
            }
        }

        public void saveTerrainMask() {
            if (runFlag) {
                viewController.logMsg("[SYSTEM]: Please pause the simulation first.");
                return;
            }
            if (userInterface.getFileChooser().showSaveDialog(userInterface) == JFileChooser.APPROVE_OPTION) { // User has provided a path
                File file = userInterface.getFileChooser().getSelectedFile();
                if (FilenameUtils.getExtension(file.getPath()).equals("dat")) {
                    try {
                        FileOutputStream fileOutputStream = new FileOutputStream(file);
                        BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(fileOutputStream);
                        ObjectOutputStream objectOutputStream = new ObjectOutputStream(bufferedOutputStream);

                        Boolean[] terrainMask = simulation.getEnvironment().getTerrainMask();

                        objectOutputStream.writeObject(terrainMask);
                        objectOutputStream.close();

                        viewController.logMsg("[SYSTEM]: Terrain Saved.");

                    } catch (IOException e) {
                        viewController.logMsg("[SYSTEM]: Something has gone wrong, unable to save file.");
                    }
                } else {
                    viewController.logMsg("[SYSTEM]: File type must be .dat.");
                }
            }
        }

        /**
         * Asks the user for a location and then tries to load the agent environment found there.
         */
        public void loadTerrainMask() {
            if (runFlag) {
                viewController.logMsg("[SYSTEM]: Please pause the simulation first.");
                return;
            }
            if (userInterface.getFileChooser().showOpenDialog(userInterface) == JFileChooser.APPROVE_OPTION) { // User has provided a path
                File file = userInterface.getFileChooser().getSelectedFile();
                if (FilenameUtils.getExtension(file.getPath()).equals("dat")) {
                    try {
                        FileInputStream fileInputStream = new FileInputStream(file);
                        BufferedInputStream bufferedInputStream = new BufferedInputStream(fileInputStream);
                        ObjectInputStream objectInputStream = new ObjectInputStream(bufferedInputStream);

                        Boolean[] terrainMask = (Boolean[]) objectInputStream.readObject();
                        simulation.getTerrainGenerator().paintTerrainMask(terrainMask);
                        viewController.updateView();

                        objectInputStream.close();

                        viewController.logMsg("[SYSTEM]: Terrain Loaded.");
                    } catch (IOException | ClassNotFoundException | ClassCastException e) {
                        viewController.logMsg("[SYSTEM]: Something went wrong and the file could not be read.");
                    }
                } else {
                    viewController.logMsg("[SYSTEM]: File type must be .dat.");
                }
            }
        }

        public void saveTerrainSettings() {
            if (runFlag) {
                viewController.logMsg("[SYSTEM]: Please pause the simulation first.");
                return;
            }
            if (userInterface.getFileChooser().showSaveDialog(userInterface) == JFileChooser.APPROVE_OPTION) { // User has provided a path
                File file = userInterface.getFileChooser().getSelectedFile();
                if (FilenameUtils.getExtension(file.getPath()).equals("dat")) {
                    try {
                        FileOutputStream fileOutputStream = new FileOutputStream(file);
                        BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(fileOutputStream);
                        ObjectOutputStream objectOutputStream = new ObjectOutputStream(bufferedOutputStream);

                        TerrainSettings terrainSettings = simulation.getTerrainGenerator().getTerrainSettings();

                        objectOutputStream.writeObject(terrainSettings);
                        objectOutputStream.close();

                        viewController.logMsg("[SYSTEM]: Terrain Settings Saved.");

                    } catch (IOException e) {
                        viewController.logMsg("[SYSTEM]: Something has gone wrong, unable to save file.");
                    }
                } else {
                    viewController.logMsg("[SYSTEM]: File type must be .dat.");
                }
            }
        }

        /**
         * Asks the user for a location and then tries to load the agent environment found there.
         */
        public void loadTerrainSettings() {
            if (runFlag) {
                viewController.logMsg("[SYSTEM]: Please pause the simulation first.");
                return;
            }
            if (userInterface.getFileChooser().showOpenDialog(userInterface) == JFileChooser.APPROVE_OPTION) { // User has provided a path
                File file = userInterface.getFileChooser().getSelectedFile();
                if (FilenameUtils.getExtension(file.getPath()).equals("dat")) {
                    try {
                        FileInputStream fileInputStream = new FileInputStream(file);
                        BufferedInputStream bufferedInputStream = new BufferedInputStream(fileInputStream);
                        ObjectInputStream objectInputStream = new ObjectInputStream(bufferedInputStream);

                        TerrainSettings terrainSettings = (TerrainSettings) objectInputStream.readObject();
                        simulationController.clearAgents();
                        simulation.getTerrainGenerator().setTerrainSettings(terrainSettings);
                        viewController.updateView();

                        objectInputStream.close();

                        viewController.logMsg("[SYSTEM]: Terrain Settings Loaded.");
                    } catch (IOException | ClassNotFoundException | ClassCastException e) {
                        viewController.logMsg("[SYSTEM]: Something went wrong and the file could not be read.");
                    }
                } else {
                    viewController.logMsg("[SYSTEM]: File type must be .dat.");
                }
            }
        }

        /**
         * Tries to load the preset corresponding to the index param.
         *
         * @param index the preset to load.
         */
        public void loadPreset(int index) {
            if (runFlag) {
                viewController.logMsg("[SYSTEM]: Please pause the simulation first.");
                return;
            }
            try {
                File file = new File("src\\data\\presets\\" + index + ".dat");
                loadSettings(file);
            } catch (IOException | ClassNotFoundException | ClassCastException e) {
                viewController.logMsg("[SYSTEM]: Something went wrong and the file could not be read.");
            }
        }

        /**
         * Checks if the file exists, processes it if so.
         *
         * @param file the file to check
         * @throws IOException            if processSettings() cannot read the file
         * @throws ClassNotFoundException if processSettings() finds the contents are not a simulation settings object.
         */
        private void loadSettings(File file) throws IOException, ClassNotFoundException {
            if (file.exists()) {
                processSettings(file);
            } else {
                viewController.logMsg("[SYSTEM]: No settings found.");
            }
        }

        /**
         * Processes a serialised simulation settings object.
         *
         * @param file the file containing the simulation settings
         * @throws IOException            if a problem reading the file is encountered
         * @throws ClassNotFoundException if the contents found do not match the simulations settings object
         */
        private void processSettings(File file) throws IOException, ClassNotFoundException {
            FileInputStream fileInputStream = new FileInputStream(file);
            BufferedInputStream bufferedInputStream = new BufferedInputStream(fileInputStream);
            ObjectInputStream objectInputStream = new ObjectInputStream(bufferedInputStream);

            SimulationSettings simulationSettings = (SimulationSettings) objectInputStream.readObject();
            simulationController.clearAgents();
            scale = 600 / simulationSettings.environmentSettings().getSize();
            simulationController.setSimulationSettings(simulationSettings);
            simulationController.initDiagnostics();
            viewController.updateView();

            objectInputStream.close();

            viewController.logMsg("[SYSTEM]: Settings Loaded.");
        }
    }

}

