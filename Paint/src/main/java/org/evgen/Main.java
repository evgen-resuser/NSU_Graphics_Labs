package org.evgen;

import org.evgen.gui.MainFrame;

import javax.swing.*;
import java.io.IOException;
import java.util.Arrays;

public class Main {
    public static void main(String[] args) {
        try {
            new MainFrame();
        } catch (IOException e) {
            new JOptionPane();
            JOptionPane.showMessageDialog(null,
                    "Error: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }
}