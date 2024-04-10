package org.evgen.components;

import javax.swing.*;

public class SaveScreen {

    private SaveScreen(){}

    public static String saveFile() {

        JFileChooser fileChooser = new JFileChooser();
        int returnValue = fileChooser.showSaveDialog(null);
        if (returnValue == JFileChooser.APPROVE_OPTION) {
            return fileChooser.getSelectedFile() + ".txt";
        }
        return "";
    }
}