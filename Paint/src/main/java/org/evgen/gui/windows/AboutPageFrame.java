package org.evgen.gui.windows;

import javax.swing.*;
import java.awt.*;

public class AboutPageFrame extends JFrame {

    public AboutPageFrame() {

        this.setTitle("About");
        this.setMinimumSize(new Dimension(300, 200));
        this.setResizable(false);

        JLabel header = new JLabel("About");
        header.setFont(new Font("Arial", Font.BOLD, 14));
        header.setHorizontalAlignment(SwingConstants.CENTER);
        this.add(header, BorderLayout.NORTH);

        JLabel text = new JLabel(
                "<html><div style=\"margin:20px\">" +
                "Simple paint program.<br>" +
                "Syroezhkin Eugene, 2024<br>" +
                "Github: evgen-resuser" +
                "</div></html>");
        this.add(text);

        JButton close = new JButton("Close");
        close.addActionListener( e -> this.setVisible(false));
        JPanel bPanel = new JPanel();
        bPanel.add(close);
        this.add(bPanel, BorderLayout.SOUTH);
    }

}
