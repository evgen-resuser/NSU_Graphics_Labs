package org.evgen.view.windows;

import org.evgen.instruments.filters.gamma.GammaRecord;
import org.evgen.instruments.interfaces.IFilterSettings;
import org.evgen.view.components.DoubleInput;

import javax.swing.*;

public class GammaSettingsFrame implements ISettings{

    private int option;
    private final DoubleInput input = new DoubleInput(0.1, 10, 1, 1, 0.1, "Gamma: ");
    private double lastGamma = 1;

    @Override
    public IFilterSettings showWindow() {

        input.setValue(lastGamma);

        option = JOptionPane.showConfirmDialog(null, input, "Parameter Input",
                JOptionPane.OK_CANCEL_OPTION);

        if (option == JOptionPane.OK_OPTION) {
            lastGamma = input.getValue();
            return new GammaRecord(lastGamma);
        }
        return null;
    }

    @Override
    public int getState() {
        return option;
    }
}
