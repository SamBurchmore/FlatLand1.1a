package View;

import javax.swing.*;
import java.awt.*;

/**
 * This class contains the components which directly control the simulation.
 *
 * @author Sam Burchmore
 * @version 1.0a
 * @since 1.0a
 */
public class SimulationControlPanel extends JPanel {

    private final JButton runStepButton;
    private final JButton stopStartButton;
    private final JButton runNStepsButton;
    private final JButton populateButton;
    private final JButton clearButton;
    private final JButton replenishEnvironmentEnergyButton;
    private final JSpinner runNStepsSpinner;
    private final JSpinner populationDensitySpinner;

    public SimulationControlPanel() {
        super();
        setLayout(new GridLayout(1, 6));
        setPreferredSize(new Dimension(600, 60));

        // View components built
        runStepButton = new JButton("Run Step");
        runStepButton.setFont(new Font("Dialog", Font.PLAIN, 16));

        stopStartButton = new JButton("Start");
        stopStartButton.setBackground(new Color(100, 220, 10));
        stopStartButton.setFont(new Font("Dialog", Font.PLAIN, 16));

        clearButton = new JButton("Clear");
        clearButton.setFont(new Font("Dialog", Font.PLAIN, 16));

        replenishEnvironmentEnergyButton = new JButton("<html>Replenish<br>Energy</html>");
        replenishEnvironmentEnergyButton.setFont(new Font("Dialog", Font.PLAIN, 14));

        runNStepsButton = new JButton("Run For: ");
        runNStepsButton.setFont(new Font("Dialog", Font.BOLD, 12));
        runNStepsSpinner = new JSpinner(new SpinnerNumberModel(10, 0, 1000000, 1));
        JPanel runNStepsPanel = new JPanel(new GridLayout(2, 0));
        runNStepsPanel.add(runNStepsButton);
        runNStepsPanel.add(runNStepsSpinner);

        populateButton = new JButton("Populate");
        populateButton.setFont(new Font("Dialog", Font.BOLD, 12));
        populationDensitySpinner = new JSpinner(new SpinnerNumberModel(1.0, 0, 100.0, 1));
        JPanel populatePanel = new JPanel(new GridLayout(2, 1));
        populatePanel.add(populateButton);
        populatePanel.add(populationDensitySpinner);

        // View components added
        add(runStepButton);
        add(stopStartButton);
        add(populatePanel);
        add(runNStepsPanel);
        add(replenishEnvironmentEnergyButton);
        add(clearButton);
    }

    public JButton getRunStepButton() {
        return this.runStepButton;
    }

    public JButton getRunNStepsButton() {
        return this.runNStepsButton;
    }

    public JSpinner getRunNStepsSpinner() {
        return this.runNStepsSpinner;
    }

    public JButton getPopulateButton() {
        return this.populateButton;
    }

    public JButton getClearButton() {
        return this.clearButton;
    }

    public JButton getReplenishEnvironmentEnergyButton() {
        return replenishEnvironmentEnergyButton;
    }

    public JSpinner getPopulationDensitySpinner() {
        return populationDensitySpinner;
    }

    public JButton getStopStartButton() {
        return stopStartButton;
    }
}
