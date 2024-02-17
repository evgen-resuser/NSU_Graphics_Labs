package org.evgen.gui.windows;

import org.evgen.interfaces.ColorReceiver;

import javax.swing.*;
import java.awt.*;

public class ColorPickerFrame extends JFrame{

    private final JColorChooser colorChooser = new JColorChooser(Color.BLACK);
    private final ColorReceiver receiver;

    public ColorPickerFrame(ColorReceiver panel) {
        this.setMinimumSize(new Dimension(200, 150));
        this.setResizable(false);
        this.receiver = panel;
        this.setTitle("Color picker");

        this.add(colorChooser);
        this.add(initButtons(), BorderLayout.SOUTH);

        this.pack();
    }

    public Color getColor() {
        return colorChooser.getColor();
    }

    public JPanel initButtons() {
        JPanel panel = new JPanel();

        JButton ok = new JButton("OK");
        JButton cancel = new JButton("Cancel");

        ok.addActionListener( e -> {
            receiver.setChosenColor(colorChooser.getColor());
            this.setVisible(false);
        });
        cancel.addActionListener( e -> this.setVisible(false));

        panel.add(ok);
        panel.add(cancel);

        return panel;
    }

}
