package org.evgen.gui.windows;

import org.evgen.gui.DrawPanel;
import org.evgen.gui.components.Input;
import org.evgen.tools.polygon.PolygonSettings;

import javax.swing.*;
import java.awt.*;

public class PolygonSettingsFrame extends JFrame {

    private final Input radiInput;
    private final Input rotateInput;
    private final Input countInput;
    private final Input thicknessInput;

    private final DrawPanel mainFrame;

    public PolygonSettingsFrame(DrawPanel frame) {

        this.mainFrame = frame;

        this.setMinimumSize(new Dimension(200, 300));
        this.setTitle("Custom polygon");

        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

        radiInput = new Input(1, 200, 50, 20, "Radius ");
        panel.add(radiInput);

        rotateInput = new Input(0, 360, 45, 15, "Rotate angle ");
        panel.add(rotateInput);

        countInput = new Input(3, 16, 3, 1, "Count of angles ");
        panel.add(countInput);

        thicknessInput = new Input(1, 20, 2, 2, "Thickness ");
        panel.add(thicknessInput);

        panel.add(initButtons());

        this.add(panel);
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

    public void applySettings() {
        PolygonSettings setting = new PolygonSettings(radiInput.getValue(), rotateInput.getValue(),
                countInput.getValue(), thicknessInput.getValue());
        mainFrame.setPolygonSettings(setting);
    }

}
