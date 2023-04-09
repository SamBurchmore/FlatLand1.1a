package View;

import javax.swing.*;
import java.awt.*;

/**
 * A button where width and height are equal to the larger.
 *
 * @author Sam Burchmore
 * @version 1.0a
 * @since 1.0a
 */
class SquareToggleButton extends JToggleButton {

    SquareToggleButton() {
        super();
    }

    SquareToggleButton(String name) {
        super(name);
    }

    @Override
    public Dimension getPreferredSize() {
        Dimension dimension = super.getPreferredSize();
        int size = (int) (Math.max(dimension.getWidth(), dimension.getHeight()));
        return new Dimension(size, size);
    }
}