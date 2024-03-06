package org.evgen.view.windows;

import org.evgen.view.WorkPanel;
import org.evgen.view.components.ZoomPanel;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.io.File;
import java.io.IOException;

public class OpenScreen {

    public static void openFile(WorkPanel workPanel, ZoomPanel zoomPanel) throws IOException {
        JFileChooser chooser = new JFileChooser();
        FileNameExtensionFilter filter =
                new FileNameExtensionFilter("Image files", "jpg", "png", "gif");
        chooser.setFileFilter(filter);
        int ret = chooser.showOpenDialog(workPanel);
        if (ret == JFileChooser.APPROVE_OPTION) {
            zoomPanel.setDefaults();
            File file = chooser.getSelectedFile();
            workPanel.setOriginal(ImageIO.read(file));
        }
    }

}
