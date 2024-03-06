package org.evgen.view.windows;

import org.evgen.instruments.filters.dithering.DitheringRecord;
import org.evgen.instruments.interfaces.IFilterSettings;
import org.evgen.view.components.IntInput;

import javax.swing.*;
import java.awt.*;

public class DitheringSettingsFrame implements ISettings {

    private int option;

    @Override
    public IFilterSettings showWindow() {

        IntInput red = new IntInput(2, 128, 8, 1, 1, "Red    ");
        red.setColor(Color.RED);
        IntInput green = new IntInput(2, 128, 8, 1, 1, "Green");
        green.setColor(Color.GREEN);
        IntInput blue = new IntInput(2, 128, 8, 1, 1, "Blue   ");
        blue.setColor(Color.BLUE);

        Object[] input = {red, green, blue};

        option = JOptionPane.showConfirmDialog(null, input, "Parameter Input",
                JOptionPane.OK_CANCEL_OPTION);

        if (option == JOptionPane.OK_OPTION) {
            return new DitheringRecord(red.getValue(), green.getValue(), blue.getValue());
        }
        return null;
    }

    @Override
    public int getState() {
        return option;
    }

}
