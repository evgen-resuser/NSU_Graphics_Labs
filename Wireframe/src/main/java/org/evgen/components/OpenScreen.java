package org.evgen.components;

import javax.swing.*;

public class OpenScreen {

    private OpenScreen(){}

    public static String openFile() {

        JFileChooser fileChooser = new JFileChooser();
        int returnValue = fileChooser.showSaveDialog(null);
        if (returnValue == JFileChooser.APPROVE_OPTION) {
            return fileChooser.getSelectedFile().getAbsolutePath();
        }
        return "";
    }
}
