package org.evgen.view.windows;

import org.evgen.instruments.filters.contouring.ContouringRecord;
import org.evgen.instruments.interfaces.IFilterSettings;
import org.evgen.view.components.IntInput;

import javax.swing.*;

public class ContouringSettings implements ISettings{

    private int state;

    @Override
    public IFilterSettings showWindow() {

        JRadioButton roberts = new JRadioButton("Roberts Operator");
        JRadioButton sobel = new JRadioButton("Sobel Operator");

        ButtonGroup group = new ButtonGroup();
        roberts.setSelected(true);
        group.add(roberts);
        group.add(sobel);

        IntInput param = new IntInput(1, 20, 10, 5, 1, "Parameter: ");

        Object[] inputs = {roberts, sobel, param};

        state = JOptionPane.showConfirmDialog(
                null, inputs, "Options", JOptionPane.OK_CANCEL_OPTION,
                JOptionPane.QUESTION_MESSAGE);

        if (state == JOptionPane.OK_OPTION) {
            int mode = roberts.isSelected() ? 0 : 1;
            return new ContouringRecord(mode, param.getValue());
        }

        return null;
    }

    @Override
    public int getState() {
        return state;
    }
}
