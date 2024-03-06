package org.evgen.view.windows;

import org.evgen.instruments.filters.gamma.GammaRecord;
import org.evgen.instruments.interfaces.IFilterSettings;
import org.evgen.view.components.DoubleInput;

import javax.swing.*;

public class GammaSettingsFrame implements ISettings{

    private int option;

    @Override
    public IFilterSettings showWindow() {

        DoubleInput input = new DoubleInput(0.1, 10, 1, 1, 0.1, "Gamma: ");

        option = JOptionPane.showConfirmDialog(null, input, "Parameter Input",
                JOptionPane.OK_CANCEL_OPTION);

        if (option == JOptionPane.OK_OPTION) {
            return new GammaRecord(input.getValue());
        }
        return null;
    }

    @Override
    public int getState() {
        return option;
    }
}
