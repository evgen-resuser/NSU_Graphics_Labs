package org.evgen.view.windows;

import javax.swing.*;
import javax.swing.event.HyperlinkEvent;
import java.awt.*;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;

public class AboutPageFrame extends JFrame {

    public AboutPageFrame() {

        this.setTitle("About");
        this.setMinimumSize(new Dimension(300, 300));
        this.setResizable(false);

        JEditorPane editorPane = new JEditorPane();
        editorPane.setEditable(false);

        editorPane.addHyperlinkListener(e -> {
            if(Desktop.isDesktopSupported() && e.getEventType() == HyperlinkEvent.EventType.ACTIVATED) {
                try {
                    Desktop.getDesktop().browse(e.getURL().toURI());
                } catch (IOException | URISyntaxException ex) {
                    JOptionPane.showMessageDialog(this, ex.getMessage(), "error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        try {
            URL fileUrl = HelpPageFrame.class.getClassLoader().getResource("about.html");
            editorPane.setPage(fileUrl);
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "File is missing!", "Error", JOptionPane.ERROR_MESSAGE);
        }

        this.add(editorPane, BorderLayout.CENTER);

        JButton close = new JButton("Close");
        close.addActionListener( e -> this.setVisible(false));
        JPanel bPanel = new JPanel();
        bPanel.add(close);
        this.add(bPanel, BorderLayout.SOUTH);
    }

}
