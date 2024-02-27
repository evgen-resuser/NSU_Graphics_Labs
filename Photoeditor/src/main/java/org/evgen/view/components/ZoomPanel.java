package org.evgen.view.components;

import org.evgen.view.IconsHandler;
import org.evgen.view.WorkPanel;

import javax.swing.*;
import java.awt.*;

public class ZoomPanel extends JToolBar {

    private JSpinner scaleSpinner;

    public ZoomPanel(WorkPanel workPanel) {

        JButton fitButton = ButtonMaker.initButton("Fit the image to screen size", IconsHandler.FIT);
        fitButton.addActionListener(e -> workPanel.fitPic());

        JButton fullSize = ButtonMaker.initButton("Pixel to Pixel", IconsHandler.FULL);
        fullSize.addActionListener(e -> workPanel.fullSize());

        initSpinner(workPanel);

        this.setFloatable(false);

        this.addSeparator();
        this.add(new JLabel("Zoom, %: "));
        this.add(scaleSpinner);
        this.addSeparator();
        this.add(fitButton);
        this.add(fullSize);
    }

    private void initSpinner(WorkPanel workPanel) {
        scaleSpinner = new JSpinner(new SpinnerNumberModel(100, 10, 200, 1));
        scaleSpinner.setPreferredSize(new Dimension(60, 25));
        scaleSpinner.setMaximumSize(new Dimension(60, 30));

        scaleSpinner.addChangeListener(e -> {
            if (workPanel.getOriginal() == null) {
                JOptionPane.showMessageDialog(workPanel, "Nothing to zoom!");
                return;
            }
            workPanel.zoomBySpinner((int)scaleSpinner.getValue());
        });
    }

    public void setDefaults() {
        scaleSpinner.setValue(100);
    }

}
