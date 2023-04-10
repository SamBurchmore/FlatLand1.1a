package View;

import Simulation.Environment.Location;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;

public class TerrainControlPanel extends JFrame {

    private final ButtonGroup buttonGroup;

    private final SquareButton paintPointButton;
    private final SquareButton paintSquareButton;
    private final SquareButton paintCircleButton;
    private final SquareButton paintLineButton;
    private final SquareButton paintBendingLineButton;
    private final SquareButton paintRadiantLineButton;

    private final JToggleButton togglePaintFlagButton;
    private final JSpinner x1LocationSpinner;
    private final JSpinner y1LocationSpinner;
    private final JSpinner x2LocationSpinner;
    private final JSpinner y2LocationSpinner;
    private final JButton fillButton;
    private final JButton clearButton;

    private final JSpinner sizeSpinner;
    private final JSpinner dxSpinner;
    private final JSpinner dySpinner;
    private final JSpinner dxBendRange;
    private final JSpinner dyBendRange;
    private final JSpinner upperBoundSpinner;
    private final JSpinner lowerBoundSpinner;
    private final JSpinner varianceSpinner;

    private final SpinnerNumberModel locationModel = new SpinnerNumberModel(300, 0, 600, 1);

    public TerrainControlPanel() throws IOException {
        setName("FlatLand 1.0a");
        getContentPane().setLayout(new FlowLayout());
        setIconImage(ImageIO.read((new File("src\\images\\tool-icon-v1.png").toURI()).toURL()));
        JPanel controlPanel = new JPanel();
        controlPanel.setLayout(new BoxLayout(controlPanel, BoxLayout.PAGE_AXIS));
        controlPanel.setPreferredSize(new Dimension(600, 400));


        buttonGroup = new ButtonGroup(){
        @Override
        public void setSelected(ButtonModel model, boolean selected) {
            if (selected) {
                super.setSelected(model, selected);
            } else {
                clearSelection();
            }
        }
        };

        JPanel shapePanel = new JPanel(new GridLayout(1, 5));
        JScrollPane shapeScrollPane = new JScrollPane(shapePanel);
        paintPointButton = new SquareButton("Paint Point");
        paintSquareButton = new SquareButton("Paint Square");
        paintCircleButton = new SquareButton("Paint Circle");
        paintLineButton = new SquareButton("Paint Line");
        paintBendingLineButton = new SquareButton("Paint Bending Line");
        paintRadiantLineButton = new SquareButton("Paint Radiant Line");
//        buttonGroup.add(paintPointButton);
//        buttonGroup.add(paintSquareButton);
//        buttonGroup.add(paintCircleButton);
        shapePanel.add(paintPointButton);
        shapePanel.add(paintSquareButton);
        shapePanel.add(paintCircleButton);
        shapePanel.add(paintLineButton);
        shapePanel.add(paintBendingLineButton);
        shapePanel.add(paintRadiantLineButton);

        JPanel settingsPanel = new JPanel(new GridLayout(6, 3));
        togglePaintFlagButton = new JToggleButton("Toggle Paint");
        togglePaintFlagButton.setSelected(true);

        x1LocationSpinner = new JSpinner(new SpinnerNumberModel(300, 0, 600, 1));
        y1LocationSpinner = new JSpinner(new SpinnerNumberModel(300, 0, 600, 1));
        x2LocationSpinner = new JSpinner(new SpinnerNumberModel(300, 0, 600, 1));
        y2LocationSpinner = new JSpinner(new SpinnerNumberModel(300, 0, 600, 1));

        sizeSpinner = new JSpinner(new SpinnerNumberModel(100, 1, 600, 1));
        dxSpinner = new JSpinner(new SpinnerNumberModel(1, -10, 10, 1));
        dySpinner = new JSpinner(new SpinnerNumberModel(1, -10, 10, 1));
        dxBendRange = new JSpinner(new SpinnerNumberModel(1, -10, 10, 1));
        dyBendRange = new JSpinner(new SpinnerNumberModel(1, -10, 10, 1));
        upperBoundSpinner = new JSpinner(new SpinnerNumberModel(6, 1, 100, 1));
        lowerBoundSpinner = new JSpinner(new SpinnerNumberModel(3, 1, 100, 1));
        varianceSpinner = new JSpinner(new SpinnerNumberModel(3, 1, 100, 1));

        fillButton = new JButton("Fill");
        clearButton = new JButton("Clear");

        JPanel x1LocationPanel = new JPanel();
        x1LocationPanel.add(new JLabel("X:"));
        x1LocationPanel.add(x1LocationSpinner);
        settingsPanel.add(x1LocationPanel);

        JPanel y1LocationPanel = new JPanel();
        y1LocationPanel.add(new JLabel("Y:"));
        y1LocationPanel.add(y1LocationSpinner);
        settingsPanel.add(y1LocationPanel);

        settingsPanel.add(togglePaintFlagButton);

        JPanel x2LocationPanel = new JPanel();
        x2LocationPanel.add(new JLabel("X2:"));
        x2LocationPanel.add(x2LocationSpinner);
        settingsPanel.add(x2LocationPanel);

        JPanel y2LocationPanel = new JPanel();
        y2LocationPanel.add(new JLabel("Y2:"));
        y2LocationPanel.add(y2LocationSpinner);
        settingsPanel.add(y2LocationPanel);

        settingsPanel.add(fillButton);

        JPanel dxPanel = new JPanel();
        dxPanel.add(new JLabel("DX:"));
        dxPanel.add(dxSpinner);
        settingsPanel.add(dxPanel);

        JPanel dyPanel = new JPanel();
        dyPanel.add(new JLabel("DY:"));
        dyPanel.add(dySpinner);
        settingsPanel.add(dyPanel);

        settingsPanel.add(clearButton);

        JPanel dxBendRangePanel = new JPanel();
        dxBendRangePanel.add(new JLabel("DX Bend Range:"));
        dxBendRangePanel.add(dxBendRange);
        settingsPanel.add(dxBendRangePanel);

        JPanel dyBendRangePanel = new JPanel();
        dyBendRangePanel.add(new JLabel("DY Bend Range:"));
        dyBendRangePanel.add(dyBendRange);
        settingsPanel.add(dyBendRangePanel);

        JPanel sizePanel = new JPanel();
        sizePanel.add(new JLabel("Size:"));
        sizePanel.add(sizeSpinner);
        settingsPanel.add(sizePanel);

        JPanel upperBoundPanel = new JPanel();
        upperBoundPanel.add(new JLabel("Upper Line Size:"));
        upperBoundPanel.add(upperBoundSpinner);
        settingsPanel.add(upperBoundPanel);

        JPanel lowerBoundPanel = new JPanel();
        lowerBoundPanel.add(new JLabel("Lower Line Size:"));
        lowerBoundPanel.add(lowerBoundSpinner);
        settingsPanel.add(lowerBoundPanel);

        JPanel variancePanel = new JPanel();
        variancePanel.add(new JLabel("Line Size Oscillation:"));
        variancePanel.add(varianceSpinner);
        settingsPanel.add(variancePanel);

        controlPanel.add(shapeScrollPane);
        controlPanel.add(settingsPanel);
        add(controlPanel);

        setResizable(false);
        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }

    public SquareButton getPaintSquareButton() {
        return paintSquareButton;
    }

    public SquareButton getPaintCircleButton() {
        return paintCircleButton;
    }

    public SquareButton getPaintPointButton() {
        return paintPointButton;
    }

    public JToggleButton getTogglePaintFlagButton() {
        return togglePaintFlagButton;
    }

    public JSpinner getX1LocationSpinner() {
        return x1LocationSpinner;
    }

    public JSpinner getY1LocationSpinner() {
        return y1LocationSpinner;
    }

    public JButton getFillButton() {
        return fillButton;
    }

    public JButton getClearButton() {
        return clearButton;
    }

    public SquareButton getPaintLineButton() {
        return paintLineButton;
    }

    public SquareButton getPaintBendingLineButton() {
        return paintBendingLineButton;
    }

    public void setLocation1Value(Location location) {
        x1LocationSpinner.setValue(location.getX());
        y1LocationSpinner.setValue(location.getY());
    }

    public void setLocation2Value(Location location) {
        x2LocationSpinner.setValue(location.getX());
        y2LocationSpinner.setValue(location.getY());
    }

    public Location getLocation1Value() {
        return new Location((int) x1LocationSpinner.getValue(), (int) y1LocationSpinner.getValue());
    }

    public Location getLocation2Value() {
        return new Location((int) x1LocationSpinner.getValue(), (int) y1LocationSpinner.getValue());
    }

    public boolean getPaintFlagValue() {
        return togglePaintFlagButton.isSelected();
    }

    public int getSizeValue() {
        return (int) sizeSpinner.getValue();
    }

    public int getX2LocationValue() {
        return (int) x2LocationSpinner.getValue();
    }

    public int getY2LocationValue() {
        return (int) y2LocationSpinner.getValue();
    }

    public int getDxValue() {
        return (int) dxSpinner.getValue();
    }

    public int getDyValue() {
        return (int) dySpinner.getValue();
    }

    public int getDxBendRangeValue() {
        return (int) dxBendRange.getValue();
    }

    public int getDyBendRangeValue() {
        return (int) dyBendRange.getValue();
    }

    public int getUpperBoundValue() {
        return (int) upperBoundSpinner.getValue();
    }

    public int getLowerBoundValue() {
        return (int) lowerBoundSpinner.getValue();
    }

    public int getVarianceValue() {
        return (int) varianceSpinner.getValue();
    }

    private static SpinnerNumberModel getLocationModel(int max) {
        return new SpinnerNumberModel(max/2, 0, max, 1);
    }

    public void updateLocationModelMax(int max) {
        x1LocationSpinner.setModel(getLocationModel(max));
        y1LocationSpinner.setModel(getLocationModel(max));
        x2LocationSpinner.setModel(getLocationModel(max));
        y2LocationSpinner.setModel(getLocationModel(max));
    }

    public int getSelectedShape() {
        ButtonModel buttonModel = buttonGroup.getSelection();
        if (buttonModel.equals(paintPointButton.getModel())) {
            return 2;
        }
        if (buttonModel.equals(paintSquareButton.getModel())) {
            return 3;
        }
        if (buttonModel.equals(paintCircleButton.getModel())) {
            return 4;
        }
        return 99;
    }

    public JSpinner getSizeSpinner() {
        return sizeSpinner;
    }

    public SquareButton getPaintRadiantLineButton() {
        return paintRadiantLineButton;
    }
}
