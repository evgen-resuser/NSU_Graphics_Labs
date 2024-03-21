package org.evgen.view.windows;

import org.evgen.instruments.filters.dithering.DitheringRecord;
import org.evgen.instruments.interfaces.IFilterSettings;
import org.evgen.view.components.IntInput;

import javax.swing.*;
import java.awt.*;

public class DitheringSettingsFrame implements ISettings {

    private int option;
    private final IntInput red = new IntInput(2, 128, 8, 1, 1, "Red    ");
    private final IntInput green = new IntInput(2, 128, 8, 1, 1, "Green");
    private final IntInput blue = new IntInput(2, 128, 8, 1, 1, "Blue   ");

    private int prevR = 8;
    private int prevG = 8;
    private int prevB = 8;

    public DitheringSettingsFrame() {
        red.setColor(Color.RED);
        green.setColor(Color.GREEN);
        blue.setColor(Color.BLUE);
    }

    @Override
    public IFilterSettings showWindow() {

        Object[] input = {red, green, blue};
        red.setValue(prevR);
        green.setValue(prevG);
        blue.setValue(prevB);

        option = JOptionPane.showConfirmDialog(null, input, "Parameter Input",
                JOptionPane.OK_CANCEL_OPTION);

        if (option == JOptionPane.OK_OPTION) {
            prevR = red.getValue();
            prevG = green.getValue();
            prevB = blue.getValue();
            return new DitheringRecord(prevR, prevG, prevB);
        }
        return null;
    }

    @Override
    public int getState() {
        return option;
    }

}
