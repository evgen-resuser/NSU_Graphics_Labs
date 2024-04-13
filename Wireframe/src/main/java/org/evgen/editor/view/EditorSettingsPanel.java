package org.evgen.editor.view;

import org.evgen.components.IntInput;
import org.evgen.editor.BSpline;
import org.evgen.utils.SplinePoint;

import javax.swing.*;
import java.awt.*;

public class EditorSettingsPanel extends JPanel {

    private BSpline spline;

    private final IntInput segmentsCountInput = new IntInput(1, 10, 4, 1, 1,
            "Segments count (N):");
    private final IntInput controlPointsCount = new IntInput(4, 20, 4, 1, 1,
            "Control points count (K):");
    private final IntInput generatrixInput = new IntInput(2, 20, 2, 1, 1,
            "Generatrix count (M):");
    private final IntInput linesInCircleCount = new IntInput(1, 12, 3, 1, 1,
            "Lines between generatrix lines (M1):");

    public EditorSettingsPanel(EditorMainFrame mainFrame, BSpline spline) {
        this.spline = spline;

        int h = (int) (mainFrame.getHeight() * 0.25);
        this.setPreferredSize(new Dimension(0, h));

        JPanel slidersPanel = new JPanel();

        slidersPanel.add(segmentsCountInput);
        this.segmentsCountInput.addListener( e -> {
            spline.setSegmentsNum(segmentsCountInput.getValue());
            spline.createSpline();
            mainFrame.repaint();
        });

        slidersPanel.add(controlPointsCount);
        this.controlPointsCount.addListener( e -> {
            spline.setPointsCount(controlPointsCount.getValue());
            spline.createSpline();
            mainFrame.repaint();
        });

        slidersPanel.add(generatrixInput);
        this.generatrixInput.addListener( e -> {

        });

        slidersPanel.add(linesInCircleCount);
        this.linesInCircleCount.addListener( e -> {

        });

        slidersPanel.setLayout(new BoxLayout(slidersPanel, BoxLayout.Y_AXIS));
        this.add(slidersPanel);

        JButton applyButton = new JButton("Apply");
        applyButton.addActionListener( e -> {
            mainFrame.notifyObservers();
        });

        JButton okButton = new JButton("OK");
        okButton.addActionListener( e -> {
            mainFrame.setVisible(false);
            mainFrame.notifyObservers();
        });

        this.add(manualPoint(mainFrame));

        JPanel buttonsPanel = new JPanel();
        buttonsPanel.add(okButton);
        buttonsPanel.add(applyButton);

        this.add(buttonsPanel);
    }

    public void updateSettings(EditorMainFrame frame) {
        segmentsCountInput.setValue(spline.getSegmentsNum());
        controlPointsCount.setValue(spline.getPointsCount());
        generatrixInput.setValue(spline.getGeneratrixCount());
        linesInCircleCount.setValue(spline.getLinesInCircle());
        frame.repaint();
    }

    private JPanel manualPoint(EditorMainFrame mainFrame) {
        JPanel panel = new JPanel();

        JSpinner x = new JSpinner(new SpinnerNumberModel(0.0, -100, 100, 0.1));
        JSpinner y = new JSpinner(new SpinnerNumberModel(0.0, -100, 100, 0.1));

        JButton add = new JButton("Add");
        add.addActionListener( e -> {
                    spline.addPoint(new SplinePoint((Double) x.getValue(), (Double) y.getValue()));
                    spline.createSpline();
                    mainFrame.repaint();
        });

        panel.add(new JLabel("x:"));
        panel.add(x);
        panel.add(new JLabel("y:"));
        panel.add(y);
        panel.add(add);

        return panel;
    }

    public void setSpline(BSpline spline) {
        this.spline = spline;
    }
}
