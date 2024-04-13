package org.evgen.wireframe.view;

import org.evgen.editor.view.EditorMainFrame;

import javax.swing.*;

public class ViewerToolBar extends JToolBar {

    public ViewerToolBar(WireframeViewerMainFrame main, EditorMainFrame editorMainFrame) {

        JButton editorButton = new JButton("EDIT");
        editorButton.addActionListener( e -> editorMainFrame.setVisible(!editorMainFrame.isVisible()));
        this.add(editorButton);

        this.addSeparator();

        JButton open = new JButton("OPEN");
        this.add(open);

        JButton save = new JButton("SAVE");
        this.add(save);

        this.addSeparator();

        JButton reset = new JButton("RESET");
        this.add(reset);

    }

}
