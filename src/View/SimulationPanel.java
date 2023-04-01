package View;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

/**
 * This class displays the simulation image created the Simulation class. It contains a buffered image instance which
 * can be updated using the updateWorldImage() method.
 *
 * @author Sam Burchmore
 * @version 1.0a
 * @since 1.0a
 */
public class SimulationPanel extends JPanel {

    private final int size = 600;

    private BufferedImage worldImage;

    public SimulationPanel() {
        this.setPreferredSize(new Dimension(size, size));
        this.worldImage = new BufferedImage(size, size, BufferedImage.TYPE_INT_RGB);
    }

    /**
     * Overrides the paintComponent method so the worldImage is painted in the center.
     */
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        int x = (size - worldImage.getWidth(null)) / 2;
        int y = (size - worldImage.getHeight(null)) / 2;
        g.drawImage(this.worldImage, x, y, this);
    }

    public void updateWorldImage(BufferedImage worldImage) {
        this.worldImage = worldImage;
    }

}