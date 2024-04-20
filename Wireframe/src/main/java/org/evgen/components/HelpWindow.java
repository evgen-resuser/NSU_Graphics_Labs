package org.evgen.components;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.net.URL;

public class HelpWindow extends JFrame {

    public HelpWindow() {
        JEditorPane editorPane = new JEditorPane();
        editorPane.setEditable(false);
        this.setTitle("Help");

        try {
            URL fileUrl = HelpWindow.class.getClassLoader().getResource("help.html");
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
