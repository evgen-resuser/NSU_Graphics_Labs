package org.evgen.editor.view;

import org.evgen.components.IntInput;
import org.evgen.editor.BSpline;

import javax.swing.*;
import java.awt.*;

public class EditorSettingsPanel extends JPanel {

    private BSpline spline;

    private final IntInput segmentsCountInput = new IntInput(2, 10, 4, 1, 1,
            "Segments count (N):");
    private final IntInput controlPointsCount = new IntInput(4, 20, 4, 1, 1,
            "Control points count (K):");

    public EditorSettingsPanel(EditorMainFrame mainFrame, BSpline spline) {
        this.spline = spline;

        int h = (int) (mainFrame.getHeight() * 0.2);
        this.setPreferredSize(new Dimension(0, h));

        this.add(segmentsCountInput);
        this.segmentsCountInput.addListener( e -> {
            spline.setSegmentsNum(segmentsCountInput.getValue());
            spline.createSpline();
            mainFrame.repaint();
        });

        this.add(controlPointsCount);
        this.controlPointsCount.addListener( e -> {
//            spline.setSegmentsNum(segmentsCountInput.getValue());
//            spline.createSpline();
//            mainFrame.repaint();
        });


    }

    public void updateSettings(EditorMainFrame frame) {
        segmentsCountInput.setValue(spline.getSegmentsNum());
        controlPointsCount.setValue(5);
        frame.repaint();
    }

    public void setSpline(BSpline spline) {
        this.spline = spline;
    }
}
