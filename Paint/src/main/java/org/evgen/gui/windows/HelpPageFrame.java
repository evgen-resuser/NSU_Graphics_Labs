package org.evgen.gui.windows;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.net.URL;

public class HelpPageFrame extends JFrame{

    public HelpPageFrame() {
        JEditorPane editorPane = new JEditorPane();
        editorPane.setEditable(false);
        this.setTitle("Help");

        try {
            URL fileUrl = HelpPageFrame.class.getClassLoader().getResource("help.html");
            //File htmlFile = new File(fileUrl);
            editorPane.setPage(fileUrl);
        } catch (IOException e) {
            e.printStackTrace();
        }

        JScrollPane scrollPane = new JScrollPane(editorPane);
        this.add(scrollPane, BorderLayout.CENTER);

        this.setSize(450, 400);
        this.setVisible(false);
    }

}
