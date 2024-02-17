package org.evgen.gui.windows;

import org.evgen.gui.DrawPanel;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;

public class SaveFileFrame {
    private final DrawPanel drawField;

    public SaveFileFrame(DrawPanel field) {
        drawField = field;
    }

    public void saveImage(){
        FileDialog save = new FileDialog(new JFrame(), "Save image", FileDialog.SAVE);
        save.setVisible(true);
        File image = new File(save.getDirectory() + save.getFile() + ".png");
        try {
            ImageIO.write(drawField.getImage(), "png", image);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
