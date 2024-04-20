package org.evgen.wireframe.view;

import org.evgen.components.OpenScreen;
import org.evgen.components.SaveScreen;
import org.evgen.editor.file.FileBuilder;
import org.evgen.editor.view.EditorMainFrame;

import javax.swing.*;

public class ViewerToolBar extends JToolBar {

    public ViewerToolBar(WireframeViewerPanel viewerPanel, EditorMainFrame editorMainFrame) {

        JButton editorButton = new JButton("EDIT");
        editorButton.addActionListener( e -> editorMainFrame.setVisible(!editorMainFrame.isVisible()));
        this.add(editorButton);

        this.addSeparator();

        JButton open = new JButton("OPEN");
        open.addActionListener( e -> editorMainFrame.load(OpenScreen.openFile()));
        this.add(open);

        JButton save = new JButton("SAVE");
        save.addActionListener( e -> FileBuilder.saveFile(SaveScreen.saveFile(), editorMainFrame.getSpline()));
        this.add(save);

        this.addSeparator();

        JButton reset = new JButton("RESET");
        reset.addActionListener( e-> viewerPanel.resetRotate());
        this.add(reset);

    }

}
