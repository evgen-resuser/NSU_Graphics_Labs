package org.evgen.gui.windows;

import org.evgen.gui.DrawPanel;
import org.evgen.gui.components.Input;
import org.evgen.tools.polygon.PolygonSettings;

import javax.swing.*;
import java.awt.*;

public class ShapeSettingsFrame extends JFrame {

    Input thickness = new Input(1, 15, 2, 2, "Thickness");
    Input rotate = new Input(0, 360, 45, 15, "Rotation angle");
    Input radi = new Input(1, 200, 50, 20, "Radius ");

    private int count = 3;

    private final DrawPanel mainFrame;

    public ShapeSettingsFrame(DrawPanel panel) {
        this.mainFrame = panel;
        this.setTitle("Shape settings");

        JPanel p = new JPanel();

        p.setLayout(new BoxLayout(p, BoxLayout.Y_AXIS));

        p.setMinimumSize(new Dimension(200, 200));

        p.add(thickness);
        p.add(rotate);
        p.add(radi);
        p.add(initButtons());

        this.add(p);
        this.setVisible(false);
        this.pack();
    }

    private JPanel initButtons() {
        JPanel panel = new JPanel();

        JButton ok = new JButton("OK");
        JButton cancel = new JButton("Cancel");

        ok.addActionListener( e -> {
            applySettings();
            this.setVisible(false);
        });
        cancel.addActionListener( e -> this.setVisible(false));

        panel.add(ok);
        panel.add(cancel);

        return panel;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public void applySettings() {
        mainFrame.setPolygonSettings(new PolygonSettings(radi.getValue(), rotate.getValue(), count, thickness.getValue()));
    }
}
