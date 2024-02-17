package org.evgen.gui.windows;

import org.evgen.gui.DrawPanel;
import org.evgen.gui.components.Input;
import org.evgen.tools.star.StarSettings;

import javax.swing.*;
import java.awt.*;

public class StarSettingsFrame extends JFrame {

    private final Input rotateInput;
    private final Input countInput;
    private final Input thicknessInput;
    private final Input radi1;
    private final Input radi2;

    private final DrawPanel mainFrame;

    public StarSettingsFrame(DrawPanel frame) {

        this.mainFrame = frame;
        this.setTitle("Custom star");

        this.setMinimumSize(new Dimension(200, 300));

        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

        radi1 = new Input(1, 200, 50, 20, "Radius 1");
        panel.add(radi1);

        radi2 = new Input(1, 200, 50, 20, "Radius 2");
        panel.add(radi2);

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
        StarSettings setting = new StarSettings(rotateInput.getValue(), countInput.getValue(),
                thicknessInput.getValue(), radi1.getValue(), radi2.getValue());
        mainFrame.setPolygonSettings(setting);
    }

}
