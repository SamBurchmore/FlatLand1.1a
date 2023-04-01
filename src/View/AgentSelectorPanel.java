package View;

import javax.swing.*;
import java.awt.*;

/**
 * This class displays 8 buttons, each corresponding to an agent in the AgentEditor.
 *
 * @author Sam Burchmore
 * @version 1.0a
 * @since 1.0a
 */
public class AgentSelectorPanel extends JPanel {

    // The labels which display the agents names
    final private JLabel agent0NameLabel;
    final private JLabel agent1NameLabel;
    final private JLabel agent2NameLabel;
    final private JLabel agent3NameLabel;
    final private JLabel agent4NameLabel;
    final private JLabel agent5NameLabel;
    final private JLabel agent6NameLabel;
    final private JLabel agent7NameLabel;

    // The labels which display the agents colours
    final private SquareButton agent0Button;
    final private SquareButton agent1Button;
    final private SquareButton agent2Button;
    final private SquareButton agent3Button;
    final private SquareButton agent4Button;
    final private SquareButton agent5Button;
    final private SquareButton agent6Button;
    final private SquareButton agent7Button;

    public AgentSelectorPanel() {
        super();
        setLayout(new GridLayout(1, 8));
        final String emptyName = "_";
        final Color emptyColour = Color.WHITE;

        agent0NameLabel = new JLabel(emptyName);
        agent0NameLabel.setHorizontalAlignment(SwingConstants.CENTER);
        agent0Button = new SquareButton();
        agent0Button.setBackground(emptyColour);
        agent0Button.setOpaque(true);
        agent0Button.setPreferredSize(new Dimension((int) agent0Button.getPreferredSize().getWidth() - 20, (int) agent0Button.getPreferredSize().getHeight() - 20));
        JPanel agent0Panel = new JPanel(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        c.insets = new Insets(0, 10, 0, 10);
        c.gridy = 0;
        agent0Panel.add(agent0NameLabel, c);
        c.gridy = 1;
        agent0Panel.add(agent0Button, c);

        agent1NameLabel = new JLabel(emptyName);
        agent1NameLabel.setHorizontalAlignment(SwingConstants.CENTER);
        agent1Button = new SquareButton();
        agent1Button.setBackground(emptyColour);
        agent1Button.setOpaque(true);
        agent1Button.setPreferredSize(new Dimension((int) agent1Button.getPreferredSize().getWidth() - 20, (int) agent1Button.getPreferredSize().getHeight() - 20));
        JPanel agent1Panel = new JPanel(new GridBagLayout());
        c.gridy = 0;
        agent1Panel.add(agent1NameLabel, c);
        c.gridy = 1;
        agent1Panel.add(agent1Button, c);

        agent2NameLabel = new JLabel(emptyName);
        agent2NameLabel.setHorizontalAlignment(SwingConstants.CENTER);
        agent2Button = new SquareButton();
        agent2Button.setBackground(emptyColour);
        agent2Button.setOpaque(true);
        agent2Button.setPreferredSize(new Dimension((int) agent2Button.getPreferredSize().getWidth() - 20, (int) agent2Button.getPreferredSize().getHeight() - 20));
        JPanel agent2Panel = new JPanel(new GridBagLayout());
        c.gridy = 0;
        agent2Panel.add(agent2NameLabel, c);
        c.gridy = 1;
        agent2Panel.add(agent2Button, c);

        agent3NameLabel = new JLabel(emptyName);
        agent3NameLabel.setHorizontalAlignment(SwingConstants.CENTER);
        agent3Button = new SquareButton();
        agent3Button.setBackground(emptyColour);
        agent3Button.setOpaque(true);
        agent3Button.setPreferredSize(new Dimension((int) agent3Button.getPreferredSize().getWidth() - 20, (int) agent3Button.getPreferredSize().getHeight() - 20));
        JPanel agent3Panel = new JPanel(new GridBagLayout());
        c.gridy = 0;
        agent3Panel.add(agent3NameLabel, c);
        c.gridy = 1;
        agent3Panel.add(agent3Button, c);

        agent4NameLabel = new JLabel(emptyName);
        agent4NameLabel.setHorizontalAlignment(SwingConstants.CENTER);
        agent4Button = new SquareButton();
        agent4Button.setBackground(emptyColour);
        agent4Button.setOpaque(true);
        agent4Button.setPreferredSize(new Dimension((int) agent4Button.getPreferredSize().getWidth() - 20, (int) agent4Button.getPreferredSize().getHeight() - 20));
        JPanel agent4Panel = new JPanel(new GridBagLayout());
        c.gridy = 0;
        agent4Panel.add(agent4NameLabel, c);
        c.gridy = 1;
        agent4Panel.add(agent4Button, c);

        agent5NameLabel = new JLabel(emptyName);
        agent5NameLabel.setHorizontalAlignment(SwingConstants.CENTER);
        agent5Button = new SquareButton();
        agent5Button.setBackground(emptyColour);
        agent5Button.setOpaque(true);
        agent5Button.setPreferredSize(new Dimension((int) agent5Button.getPreferredSize().getWidth() - 20, (int) agent5Button.getPreferredSize().getHeight() - 20));
        JPanel agent5Panel = new JPanel(new GridBagLayout());
        c.gridy = 0;
        agent5Panel.add(agent5NameLabel, c);
        c.gridy = 1;
        agent5Panel.add(agent5Button, c);

        agent6NameLabel = new JLabel(emptyName);
        agent6NameLabel.setHorizontalAlignment(SwingConstants.CENTER);
        agent6Button = new SquareButton();
        agent6Button.setBackground(emptyColour);
        agent6Button.setOpaque(true);
        agent6Button.setPreferredSize(new Dimension((int) agent6Button.getPreferredSize().getWidth() - 20, (int) agent6Button.getPreferredSize().getHeight() - 20));
        JPanel agent6Panel = new JPanel(new GridBagLayout());
        c.gridy = 0;
        agent6Panel.add(agent6NameLabel, c);
        c.gridy = 1;
        agent6Panel.add(agent6Button, c);

        agent7NameLabel = new JLabel(emptyName);
        agent7NameLabel.setHorizontalAlignment(SwingConstants.CENTER);
        agent7Button = new SquareButton();
        agent7Button.setBackground(emptyColour);
        agent7Button.setOpaque(true);
        agent7Button.setPreferredSize(new Dimension((int) agent7Button.getPreferredSize().getWidth() - 20, (int) agent7Button.getPreferredSize().getHeight() - 20));
        JPanel agent7Panel = new JPanel(new GridBagLayout());
        c.gridy = 0;
        agent7Panel.add(agent7NameLabel, c);
        c.gridy = 1;
        agent7Panel.add(agent7Button, c);

        add(agent0Panel);
        add(agent1Panel);
        add(agent2Panel);
        add(agent3Panel);
        add(agent4Panel);
        add(agent5Panel);
        add(agent6Panel);
        add(agent7Panel);
    }

    /**
     * Updates one of the agent selector buttons with a new color and name.
     *
     * @param index which selector button to be updated
     * @param color the new color
     * @param name  the new name
     */
    public void setAgentSelector(int index, Color color, String name) {
        // Pad the name with spaces if necessary
        if (name.length() < 3) {
            name = name.concat("  ");
        }
        switch (index) {
            case 0 -> {
                this.agent0Button.setBackground(color);
                this.agent0NameLabel.setText(name);
            }
            case 1 -> {
                this.agent1Button.setBackground(color);
                this.agent1NameLabel.setText(name);
            }
            case 2 -> {
                this.agent2Button.setBackground(color);
                this.agent2NameLabel.setText(name);
            }
            case 3 -> {
                this.agent3Button.setBackground(color);
                this.agent3NameLabel.setText(name);
            }
            case 4 -> {
                this.agent4Button.setBackground(color);
                this.agent4NameLabel.setText(name);
            }
            case 5 -> {
                this.agent5Button.setBackground(color);
                this.agent5NameLabel.setText(name);
            }
            case 6 -> {
                this.agent6Button.setBackground(color);
                this.agent6NameLabel.setText(name);
            }
            case 7 -> {
                this.agent7Button.setBackground(color);
                this.agent7NameLabel.setText(name);
            }
        }
    }

    public SquareButton getAgent0Button() {
        return agent0Button;
    }

    public SquareButton getAgent1Button() {
        return agent1Button;
    }

    public SquareButton getAgent2Button() {
        return agent2Button;
    }

    public SquareButton getAgent3Button() {
        return agent3Button;
    }

    public SquareButton getAgent4Button() {
        return agent4Button;
    }

    public SquareButton getAgent5Button() {
        return agent5Button;
    }

    public SquareButton getAgent6Button() {
        return agent6Button;
    }

    public SquareButton getAgent7Button() {
        return agent7Button;
    }

}
