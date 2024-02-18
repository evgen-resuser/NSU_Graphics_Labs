package org.evgen.gui.components;

import org.evgen.interfaces.ColorReceiver;
import org.evgen.gui.windows.ColorPickerFrame;

import javax.swing.*;
import javax.swing.border.LineBorder;
import javax.swing.plaf.basic.BasicButtonUI;
import java.awt.*;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;

public class ColorPickerButton extends JToggleButton {

    private final ColorPickerFrame colorPickerFrame;

    public ColorPickerButton(ColorReceiver receiver) {
        this.colorPickerFrame = new ColorPickerFrame(receiver);
        this.setText(" ... ");
        this.setFocusPainted(false);
        this.setUI(new CustomButtonUI());

        this.addActionListener( e -> colorPickerFrame.setVisible(true));

        this.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                change();
            }

            @Override
            public void focusLost(FocusEvent e) {
                //not interested in
            }
        });
    }

    private void change() {
        this.setBackground(colorPickerFrame.getColor());
        this.repaint();
    }

    static class CustomButtonUI extends BasicButtonUI {
        @Override
        protected void paintButtonPressed(Graphics g, AbstractButton b) {
            //ignoring
        }
    }
}
