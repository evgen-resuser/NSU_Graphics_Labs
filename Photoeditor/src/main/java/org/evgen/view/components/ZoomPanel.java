package org.evgen.view.components;

import org.evgen.util.ButtonMaker;
import  org.evgen.view.IconsHandler;
import org.evgen.view.WorkPanel;
import org.evgen.view.windows.RotateSettingsWindow;
import org.evgen.view.windows.WindowHandler;

import javax.swing.*;
import java.awt.*;

public class ZoomPanel extends JToolBar {

    private JSlider slider;
    private int type = 2;
    private final RotateSettingsWindow rotateSettingsWindow;

    public void setType(int t) {
        type = t;
    }

    public ZoomPanel(WorkPanel workPanel, RotateSettingsWindow rotateWindow) {

        this.rotateSettingsWindow = rotateWindow;

        JButton fitButton = ButtonMaker.initButton("Fit the image to screen size", IconsHandler.FIT);
        fitButton.addActionListener(e -> {
            workPanel.fitPic(type);
            setDefaults();
        });

        JButton fullSize = ButtonMaker.initButton("Pixel to Pixel", IconsHandler.FULL);
        fullSize.addActionListener(e -> {
            workPanel.fullSize();
            setDefaults();
        });

        initSlider(workPanel);

        RotateSettingsWindow window = new RotateSettingsWindow();

        JButton rotate = ButtonMaker.initButton("Rotate image", IconsHandler.ROTATE);
        rotate.addActionListener(e -> {
            int angle = window.showWindow();
            if (angle != -1) workPanel.rotatePic(angle);
        });

        this.setFloatable(false);

        this.addSeparator();
        this.add(new JLabel("Zoom: "));
        this.add(slider);
        this.addSeparator();
        this.add(fitButton);
        this.add(fullSize);
        this.addSeparator();
        this.add(rotate);
    }

    private void initSlider(WorkPanel workPanel) {
        slider = new JSlider(10, 200, 100);
        slider.setPreferredSize(new Dimension(150, 25));
        slider.setMaximumSize(new Dimension(150, 25));

        slider.addChangeListener(e -> workPanel.zoomBySpinner(slider.getValue()));
    }

    public void setDefaults() {
        slider.setValue(100);
    }

}
