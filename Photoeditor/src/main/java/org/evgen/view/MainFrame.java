package org.evgen.view;

import org.evgen.instruments.InstrumentsHandler;
import org.evgen.view.components.ButtonMaker;
import org.evgen.view.components.ZoomPanel;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.io.File;
import java.io.IOException;

public class MainFrame extends JFrame{

    private final JToolBar toolBar = new JToolBar(SwingConstants.VERTICAL);
    private final WorkPanel workPanel;
    private final JScrollPane imageScrollPane;
    private final InstrumentsHandler instruments = new InstrumentsHandler();
    private final ZoomPanel zoomBar;

    public MainFrame() throws IOException{
        this.setVisible(true);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setMinimumSize(new Dimension(640, 480));
        this.setTitle("Photo Editor");

        imageScrollPane = new JScrollPane();
        workPanel = new WorkPanel(instruments, imageScrollPane);
        workPanel.setVisible(true);
        zoomBar = new ZoomPanel(workPanel);

        add(imageScrollPane);

        initToolbar();

        this.pack();
    }

    private void initToolbar(){

        toolBar.setFloatable(false);

        JButton openButton = ButtonMaker.initButton("Open a file", IconsHandler.OPEN);
        openButton.addActionListener(e -> {
            try {
                openFile();
            } catch (IOException ex) {
                JOptionPane.showMessageDialog(this, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        JButton saveButton = ButtonMaker.initButton("Save the file", IconsHandler.SAVE);

        toolBar.add(openButton);
        toolBar.add(saveButton);


        this.add(toolBar, BorderLayout.WEST);
        this.add(zoomBar, BorderLayout.SOUTH);

    }

    private void openFile() throws IOException {
        JFileChooser chooser = new JFileChooser();
        FileNameExtensionFilter filter = new FileNameExtensionFilter("Image files", "jpg", "png", "gif");
        chooser.setFileFilter(filter);
        int ret = chooser.showOpenDialog(this);
        if (ret == JFileChooser.APPROVE_OPTION) {
            zoomBar.setDefaults();
            File file = chooser.getSelectedFile();
            workPanel.setOriginal(ImageIO.read(file));
        }
    }


}
