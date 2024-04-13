package org.evgen.wireframe.view;

import org.evgen.editor.BSpline;
import org.evgen.editor.view.EditorMainFrame;
import org.evgen.utils.observer.Observer;

import javax.swing.*;
import java.awt.*;

public class WireframeViewerMainFrame extends JFrame implements Observer {

    private BSpline spline;

    private int generatrixCount;
    private int linesInCircle;

    private final WireframeViewerPanel viewerPanel;

    public WireframeViewerMainFrame(EditorMainFrame editor) {
        this.viewerPanel = new WireframeViewerPanel();
        this.setTitle("Wireframe");

        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setMinimumSize(new Dimension(640, 480));
        this.setPreferredSize(new Dimension(750, 550));
        this.add(new ViewerToolBar(this, editor), BorderLayout.NORTH);
        this.add(viewerPanel, BorderLayout.CENTER);
        this.pack();
    }

    @Override
    public void update(Object message) {
        this.spline = (BSpline)message;
        this.generatrixCount = spline.getGeneratrixCount();
        this.linesInCircle = spline.getLinesInCircle();
    }

}
