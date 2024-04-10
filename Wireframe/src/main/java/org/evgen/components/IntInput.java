package org.evgen.components;

import javax.swing.*;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.ActionListener;

public class IntInput extends JPanel{

    private final JSlider slider;
    private final JSpinner spinner;

    private static final String INC = "increment";
    private static final String DEC = "decrement";

    public IntInput(int min, int max, int value, int spacing, int step, String label) {
        slider = new JSlider(min, max, value);
        spinner = new JSpinner(new SpinnerNumberModel(value, min, max, step));

        slider.setMajorTickSpacing(spacing);

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
            int x = (int)source.getValue();
            slider.setValue(x);
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
        return (int) spinner.getValue();
    }

    public void setColor(Color color) {
        this.slider.setBackground(color);
    }

    public void setValue(int v) {
        this.slider.setValue(v);
        this.spinner.setValue(v);
    }

    public void addListener(ChangeListener listener) {
        this.slider.addChangeListener(listener);
        this.spinner.addChangeListener(listener);
    }

}
