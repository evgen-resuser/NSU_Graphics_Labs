package org.evgen.gui.windows;

import org.evgen.gui.DrawPanel;
import org.evgen.gui.components.Input;

import javax.swing.*;
import java.awt.*;

public class LineSettingsFrame extends JFrame {

    Input thickness = new Input(1, 15, 2, 2, "Line thickness:");

    private final DrawPanel mainFrame;

    public LineSettingsFrame(DrawPanel panel) {
        this.mainFrame = panel;

        JPanel p = new JPanel();

        p.setLayout(new BoxLayout(p, BoxLayout.Y_AXIS));

        p.setMinimumSize(new Dimension(200, 200));

        p.add(thickness);
        p.add(initButtons());

        this.add(p);
        this.setTitle("Line settings");
        this.setVisible(false);
        this.pack();
    }

    private JPanel initButtons() {
        JPanel panel = new JPanel();

        JButton ok = new JButton("OK");
        JButton cancel = new JButton("Cancel");

        ok.addActionListener( e -> {
            mainFrame.setThickness(thickness.getValue());
            this.setVisible(false);
        });
        cancel.addActionListener( e -> this.setVisible(false));

        panel.add(ok);
        panel.add(cancel);

        return panel;
    }

}
