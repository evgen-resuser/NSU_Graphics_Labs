package org.evgen.view.windows;

import org.evgen.view.components.IntInput;

import javax.swing.*;

public class RotateSettingsWindow{

    public static int showWindow(){

        IntInput angle = new IntInput(-180, 180, 0, 10, 1, "Rotate angle (degrees): ");

        int option = JOptionPane.showConfirmDialog(null, angle, "Parameter Input",
                JOptionPane.OK_CANCEL_OPTION);

        if (option == JOptionPane.OK_OPTION) {
            return angle.getValue();
        }
        return -1;
    }

}
