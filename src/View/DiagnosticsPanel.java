package View;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import java.awt.*;

/**
 * This class is the graphical view of the diagnostics class. It displays a live view of agent and environment statistics
 * and an info log for simulation and system messages.
 *
 * @author Sam Burchmore
 * @version 1.0a
 * @since 1.0a
 */
public class DiagnosticsPanel extends JPanel {

    private final JLabel currentStepLabel;

    private final JTable agentStatsTable;

    private final JLabel maxEnvironmentEnergyValueLabel;
    private final JLabel currentEnvironmentEnergyValueLabel;
    private final JLabel currentEnvironmentEnergyPercentValueLabel;

    private final JTextArea logTextArea;
    private final JRadioButton lowDiagnosticsRadioButton;
    private final ButtonGroup diagnosticsVerbosityButtonGroup;
    private final JButton clearLogButton;

    public DiagnosticsPanel() {
        super();
        setLayout(new GridBagLayout());

        // First the current step label is built
        currentStepLabel = new JLabel("Day 0");
        currentStepLabel.setHorizontalAlignment(SwingConstants.CENTER);
        currentStepLabel.setFont(new Font("Dialog", Font.BOLD, 20));

        // Second, the agent statistics table is built
        Object[][] agentStatistics = {
                {"Agent 1", 0, 0.0, 0.0, 0, 0.0, 0.0, 0.0},
                {"Agent 2", 0, 0.0, 0.0, 0, 0.0, 0.0, 0.0},
                {"Agent 3", 0, 0.0, 0.0, 0, 0.0, 0.0, 0.0},
                {"Agent 4", 0, 0.0, 0.0, 0, 0.0, 0.0, 0.0},
                {"Agent 5", 0, 0.0, 0.0, 0, 0.0, 0.0, 0.0},
                {"Agent 6", 0, 0.0, 0.0, 0, 0.0, 0.0, 0.0},
                {"Agent 7", 0, 0.0, 0.0, 0, 0.0, 0.0, 0.0},
                {"Agent 8", 0, 0.0, 0.0, 0, 0.0, 0.0, 0.0}
        };
        String[] agentStatNames = {
                "<html>Agent<br></html>",
                "<html>Population<br></html>",
                "<html>Average<br>Energy</html>",
                "<html>Average<br>Age</html>",
                "<html>Born Last<br>Step</html>",
                "<html>Average<br>Size</html>",
                "<html>Average<br>C-Size</html>",
                "<html>Average<br>Range</html>",};
        TableModel tableModel = new DefaultTableModel(agentStatistics, agentStatNames);
        agentStatsTable = new JTable(tableModel);
        agentStatsTable.setDefaultEditor(Object.class, null);
        agentStatsTable.getTableHeader().setFont(new Font("Dialog", Font.BOLD, 11));
        agentStatsTable.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        JScrollPane agentStatsTableScrollPane = new JScrollPane(agentStatsTable, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
        agentStatsTableScrollPane.setBorder(null);
        agentStatsTable.setPreferredScrollableViewportSize(new Dimension((int) agentStatsTable.getPreferredScrollableViewportSize().getWidth(), 175));

        // Third the environments statistics panel is built
        JPanel environmentStatsPanel = new JPanel(new GridLayout(3, 2, 3, 3));
        JLabel maxEnvironmentEnergyLabel = new JLabel("     Max Environment Energy: ");
        JLabel currentEnvironmentEnergyLabel = new JLabel("     Current Environment Energy: ");
        JLabel currentEnvironmentEnergyPercentLabel = new JLabel("     Available Energy Percent: ");
        maxEnvironmentEnergyValueLabel = new JLabel();
        maxEnvironmentEnergyLabel.setHorizontalAlignment(SwingConstants.LEFT);
        currentEnvironmentEnergyValueLabel = new JLabel();
        currentEnvironmentEnergyValueLabel.setHorizontalAlignment(SwingConstants.LEFT);
        currentEnvironmentEnergyPercentValueLabel = new JLabel();
        currentEnvironmentEnergyPercentValueLabel.setHorizontalAlignment(SwingConstants.LEFT);
        environmentStatsPanel.add(maxEnvironmentEnergyLabel);
        environmentStatsPanel.add(maxEnvironmentEnergyValueLabel);
        environmentStatsPanel.add(currentEnvironmentEnergyLabel);
        environmentStatsPanel.add(currentEnvironmentEnergyValueLabel);
        environmentStatsPanel.add(currentEnvironmentEnergyPercentLabel);
        environmentStatsPanel.add(currentEnvironmentEnergyPercentValueLabel);

        // Forth the information log is built
        logTextArea = new JTextArea(18, 40);
        JScrollPane logScrollPane = new JScrollPane(logTextArea);
        JLabel logTextAreaLabel = new JLabel("Info Log:");
        logTextAreaLabel.setFont(new Font("Dialog", Font.BOLD, 12));
        lowDiagnosticsRadioButton = new JRadioButton("Low");
        JRadioButton highDiagnosticsRadioButton = new JRadioButton("High");
        diagnosticsVerbosityButtonGroup = new ButtonGroup();
        diagnosticsVerbosityButtonGroup.add(lowDiagnosticsRadioButton);
        diagnosticsVerbosityButtonGroup.add(highDiagnosticsRadioButton);
        diagnosticsVerbosityButtonGroup.setSelected(lowDiagnosticsRadioButton.getModel(), true);
        JPanel diagnosticsVerbosityPanel = new JPanel(new GridLayout(1, 4));
        JLabel diagnosticsVerbosityLabel = new JLabel("Info Log Verbosity:");
        clearLogButton = new JButton("Clear");
        diagnosticsVerbosityPanel.add(diagnosticsVerbosityLabel);
        diagnosticsVerbosityPanel.add(lowDiagnosticsRadioButton);
        diagnosticsVerbosityPanel.add(highDiagnosticsRadioButton);
        diagnosticsVerbosityPanel.add(clearLogButton);


        // The GridBag constraints we'll be using to build this panel
        GridBagConstraints c = new GridBagConstraints();
        c.weightx = 1;
        c.weighty = 1;

        // Now we add the top row components
        c.gridy = 0;
        add(currentStepLabel, c);

        // Now we add the second row components
        c.gridy = 1;
        add(agentStatsTableScrollPane, c);

        // Now we add the third row components
        c.gridy = 2;
        add(environmentStatsPanel, c);

        // Now we add the forth row components
        c.gridy = 3;
        add(logTextAreaLabel, c);

        // Now we add the fifth row components
        c.gridy = 4;
        add(logScrollPane, c);

        // Now we add the sixth row components
        c.gridy = 5;
        add(diagnosticsVerbosityPanel, c);
    }

    /**
     * Updates the values on the agent statistics table with the input agentStats object.
     */
    public void setAgentStats(Object[][] agentStats) {
        DefaultTableModel model = (DefaultTableModel) agentStatsTable.getModel();
        Object[] row = new Object[8];
        for (int i = 0; i < 8; i++) {
            row[0] = agentStats[0][i];
            row[1] = agentStats[1][i];
            row[2] = agentStats[2][i];
            row[3] = agentStats[3][i];
            row[4] = agentStats[4][i];
            row[5] = agentStats[5][i];
            row[6] = agentStats[6][i];
            row[7] = agentStats[7][i];
            model.removeRow(i);
            model.insertRow(i, row);
        }
        agentStatsTable.setModel(model);
    }

    /**
     * Updates the values on the environment statistics panel with the input environmentStats object.
     */
    public void setEnvironmentStats(Object[] environmentStats) {
        maxEnvironmentEnergyValueLabel.setText(environmentStats[0].toString());
        currentEnvironmentEnergyValueLabel.setText(environmentStats[1].toString());
        currentEnvironmentEnergyPercentValueLabel.setText(environmentStats[2].toString() + "%");
    }

    /**
     * Adds the input string logMessage to the info log.
     */
    public void addLogMessage(String logMessage) {
        logTextArea.append(logMessage + "\n");
    }

    /**
     * Returns 0 if the low verbosity is selected and 1 otherwise.
     */
    public int getDiagnosticsVerbosity() {
        if (diagnosticsVerbosityButtonGroup.getSelection().equals(lowDiagnosticsRadioButton.getModel())) {
            return 0;
        }
        return 1;
    }

    public void setStepLabel(long step) {
        currentStepLabel.setText("Step: " + step);
    }

    public void clearStepLabel() {
        currentStepLabel.setText("Step: 0");
    }

    public void clearLog() {
        logTextArea.setText("");
    }

    public JButton getClearLogButton() {
        return clearLogButton;
    }

}
