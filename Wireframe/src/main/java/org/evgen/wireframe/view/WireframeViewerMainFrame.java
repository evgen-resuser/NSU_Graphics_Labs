package org.evgen.wireframe.view;

import org.evgen.editor.BSpline;
import org.evgen.editor.view.EditorMainFrame;
import org.evgen.utils.observer.Observer;

import javax.swing.*;
import java.awt.*;

public class WireframeViewerMainFrame extends JFrame implements Observer {

    private BSpline spline;

    private final WireframeViewerPanel viewerPanel;

    public WireframeViewerMainFrame(EditorMainFrame editor) {
        this.viewerPanel = new WireframeViewerPanel(spline);
        this.setTitle("Wireframe");

        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setMinimumSize(new Dimension(640, 480));
        this.setPreferredSize(new Dimension(750, 550));
        this.add(new ViewerToolBar(viewerPanel, editor), BorderLayout.NORTH);
        this.add(viewerPanel, BorderLayout.CENTER);
        this.pack();
    }

    @Override
    public void update(Object message) {
        this.spline = (BSpline)message;
        this.viewerPanel.setSpline(spline);
        viewerPanel.repaint();
    }

}
