package org.evgen.view.windows;


import org.evgen.instruments.filters.gaussian.GaussianRecord;
import org.evgen.instruments.interfaces.IFilterSettings;
import org.evgen.view.components.DoubleInput;


import javax.swing.*;

public class GaussianSettingsFrame implements ISettings{

    private int option;
    Integer[] sizes = {3, 5, 7, 9, 11};
    JComboBox<Integer> sizeBox = new JComboBox<>(sizes);
    DoubleInput input = new DoubleInput(0.1, 20.0, 1, 1, 0.1, "Sigma: ");
    private int size = 0;
    private double sigma = 1;

    public IFilterSettings showWindow(){

        input.setValue(sigma);
        sizeBox.setSelectedIndex(size);

        Object[] inputs = {
                "size: ", sizeBox, input
        };

        option = JOptionPane.showConfirmDialog(null, inputs, "Parameter Input",
                JOptionPane.OK_CANCEL_OPTION);

        if (option == JOptionPane.OK_OPTION) {
            size = sizeBox.getSelectedIndex();
            sigma = input.getValue();
            return new GaussianRecord(sizes[size], sigma);
        }
        return null;
    }

    @Override
    public int getState() {
        return option;
    }

}
