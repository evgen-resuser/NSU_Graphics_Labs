package org.evgen.view.windows;

import org.evgen.view.components.IntInput;

import javax.swing.*;

public class RotateSettingsWindow{

    IntInput angle = new IntInput(-180, 180, 0, 10, 1, "Rotate angle (degrees): ");
    int lastAngle = 0;

    public int showWindow(){

        angle.setValue(lastAngle);

        int option = JOptionPane.showConfirmDialog(null, angle, "Parameter Input",
                JOptionPane.OK_CANCEL_OPTION);

        if (option == JOptionPane.OK_OPTION) {
            lastAngle = angle.getValue();
            return angle.getValue();
        }
        return -1;
    }

}
