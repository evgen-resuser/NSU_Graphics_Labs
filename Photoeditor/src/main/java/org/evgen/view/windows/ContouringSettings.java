package org.evgen.view.windows;

import org.evgen.instruments.filters.contouring.ContouringRecord;
import org.evgen.instruments.interfaces.IFilterSettings;
import org.evgen.view.components.IntInput;

import javax.swing.*;

public class ContouringSettings implements ISettings{

    private int state;

    JRadioButton roberts = new JRadioButton("Roberts Operator");
    JRadioButton sobel = new JRadioButton("Sobel Operator");
    IntInput param = new IntInput(1, 255, 100, 5, 1, "Threshold parameter: ");

    private int lastParam = 100;
    private int mode = 0;

    public ContouringSettings() {
        ButtonGroup group = new ButtonGroup();
        roberts.setSelected(true);
        group.add(roberts);
        group.add(sobel);
    }

    @Override
    public IFilterSettings showWindow() {

        param.setValue(lastParam);
        if ((mode == 0)) {
            roberts.setSelected(true);
        } else {
            sobel.setSelected(true);
        }

        Object[] inputs = {roberts, sobel, param};

        state = JOptionPane.showConfirmDialog(
                null, inputs, "Options", JOptionPane.OK_CANCEL_OPTION,
                JOptionPane.QUESTION_MESSAGE);

        if (state == JOptionPane.OK_OPTION) {
            mode = roberts.isSelected() ? 0 : 1;
            lastParam = param.getValue();
            return new ContouringRecord(mode, lastParam);
        }

        return null;
    }

    @Override
    public int getState() {
        return state;
    }
}
