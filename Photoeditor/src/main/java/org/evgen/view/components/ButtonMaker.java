package org.evgen.view.components;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;

public class ButtonMaker {

    private ButtonMaker(){}

    public static JToggleButton initToggleButton(String desc, ImageIcon icon) {
        JToggleButton b = new JToggleButton(icon);

        b.setToolTipText(desc);
        b.setFocusPainted(false);
        b.setBorder(new LineBorder(Color.LIGHT_GRAY, 1));

        return b;
    }

    public static JButton initButton(String desc, ImageIcon icon) {
        JButton b = new JButton(icon);

        b.setToolTipText(desc);
        b.setFocusPainted(false);
        b.setBorder(new LineBorder(Color.LIGHT_GRAY, 1));

        return b;
    }

}
