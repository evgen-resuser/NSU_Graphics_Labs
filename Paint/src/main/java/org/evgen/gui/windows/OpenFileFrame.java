package org.evgen.gui.windows;

import org.evgen.gui.DrawPanel;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;


public class OpenFileFrame {

    private final DrawPanel panel;

    public OpenFileFrame(DrawPanel panel) {
        this.panel = panel;
    }

    public void openFile() {
        BufferedImage image;

        FileDialog dialog = new FileDialog(new JFrame(), "Open", FileDialog.LOAD);

        dialog.setVisible(true);
        String file = dialog.getDirectory()+dialog.getFile();

        if (dialog.getFile() == null) return;
        try {

            image = ImageIO.read(new File(file));
            panel.setImage(image);
            panel.clearHistory();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(panel, "Error while opening occurred!\n"+e.getLocalizedMessage(),
                    "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}
