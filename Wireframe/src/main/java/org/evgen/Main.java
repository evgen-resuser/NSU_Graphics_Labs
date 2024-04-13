package org.evgen;

import org.evgen.editor.view.EditorMainFrame;
import org.evgen.wireframe.view.WireframeViewerMainFrame;

public class Main {
    public static void main(String[] args) {
        EditorMainFrame editor = new EditorMainFrame();
        WireframeViewerMainFrame viewer = new WireframeViewerMainFrame(editor);

        editor.registerObserver(viewer);

        viewer.setVisible(true);
        editor.setVisible(true);
    }
}