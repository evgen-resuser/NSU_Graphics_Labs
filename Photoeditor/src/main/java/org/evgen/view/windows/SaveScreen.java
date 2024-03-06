package org.evgen.view.windows;

import org.evgen.view.WorkPanel;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.io.File;
import java.io.IOException;

public class SaveScreen {

    public static void saveFile(WorkPanel workPanel) {
        if (workPanel.getFiltered() == null) return;

        JFileChooser fileChooser = new JFileChooser();
        int returnValue = fileChooser.showSaveDialog(workPanel);
        if (returnValue == JFileChooser.APPROVE_OPTION) {
            File image = new File(fileChooser.getSelectedFile() + ".png");
            try {
                ImageIO.write(workPanel.getFiltered(), "png", image);
            } catch (IOException e) {
                JOptionPane.showMessageDialog(workPanel, e.getMessage());
            }
        }
    }
}
