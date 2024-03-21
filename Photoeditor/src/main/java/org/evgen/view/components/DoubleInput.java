package org.evgen.view.components;

import javax.swing.*;
import java.awt.*;

public class DoubleInput extends JPanel{

    private final JSlider slider;
    private final JSpinner spinner;

    public DoubleInput(double min, double max, double value, int spacing, double step, String label) {

        slider = new JSlider((int)(min*10), (int)(max*10), (int)(value*10));
        spinner = new JSpinner(new SpinnerNumberModel(value, min, max, step));

        slider.setMajorTickSpacing(spacing*10);
        slider.setPaintTicks(true);

        slider.addChangeListener( e -> {
            if (!slider.getValueIsAdjusting()) {
                int a = slider.getValue();
                spinner.setValue((double)a / 10);
            }
        });

        spinner.addChangeListener( e -> {
            double aa = (Double)spinner.getValue() * 10;
            slider.setValue((int)aa);
        });

        this.add(new JLabel(label), BorderLayout.WEST);
        this.add(slider, BorderLayout.CENTER);
        this.add(spinner, BorderLayout.EAST);

    }

    public double getValue() {
        return (double) spinner.getValue();
    }

    public void setValue(double v) {
        slider.setValue((int)(v*10));
        spinner.setValue(v);
    }

}
