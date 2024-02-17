package org.evgen.gui.components;

import javax.swing.*;
import javax.swing.event.ChangeListener;
import java.awt.*;

public class Input extends JPanel{

    private final JSlider slider;
    private final JSpinner spinner;

    private static final String INC = "increment";
    private static final String DEC = "decrement";

    public Input(int min, int max, int value, int spacing, String label) {
        slider = new JSlider(min, max, value);
        spinner = new JSpinner(new SpinnerNumberModel(value, min, max, 1));

        slider.setMajorTickSpacing(spacing);
        slider.setPaintTicks(true);

        ChangeListener sliderChangeListener = e -> {
            JSlider source = (JSlider) e.getSource();
            if (!source.getValueIsAdjusting()) {
                int value12 = source.getValue();
                spinner.setValue(value12);
            }
        };
        slider.addChangeListener(sliderChangeListener);

        ChangeListener spinnerChangeListener = e -> {
            JSpinner source = (JSpinner) e.getSource();
            int value1 = (int) source.getValue();
            slider.setValue(value1);
        };
        spinner.addChangeListener(spinnerChangeListener);

        slider.getInputMap().put(KeyStroke.getKeyStroke("UP"), INC);
        slider.getActionMap().put(INC, spinner.getActionMap().get(INC));
        slider.getInputMap().put(KeyStroke.getKeyStroke("DOWN"), DEC);
        slider.getActionMap().put(DEC, spinner.getActionMap().get(DEC));


        this.add(new JLabel(label), BorderLayout.WEST);
        this.add(slider, BorderLayout.CENTER);
        this.add(spinner, BorderLayout.EAST);

    }

    public int getValue() {
        return slider.getValue();
    }

}
