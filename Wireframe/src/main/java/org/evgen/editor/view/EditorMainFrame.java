package org.evgen.editor.view;

import org.evgen.components.OpenScreen;
import org.evgen.components.SaveScreen;
import org.evgen.editor.BSpline;
import org.evgen.editor.file.FileBuilder;
import org.evgen.editor.file.FileReader;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;

public class EditorMainFrame extends JFrame {

    private final EditorViewerPanel viewer;
    private final EditorSettingsPanel settings;

    private final FileReader reader = new FileReader();

    private BSpline spline;

    public EditorMainFrame() {
        this.setPreferredSize(new Dimension(640, 480));
        this.setMinimumSize(new Dimension(640, 480));
        this.setVisible(true);
        this.setTitle("Editor");
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setJMenuBar(initMenu());

        //load("saves/example.txt");
        //spline = new BSpline();
        loadExample();
        viewer = new EditorViewerPanel(spline);
        settings = new EditorSettingsPanel(this, spline);
        settings.updateSettings(this);

        this.getContentPane().add(viewer, BorderLayout.CENTER);
        this.getContentPane().add(settings, BorderLayout.SOUTH);
        this.getContentPane().revalidate();
        this.getContentPane().repaint();
    }

    private void load(String name) {
        try {
            spline = reader.readSplineFromFile(new File(name));
        } catch (IOException e) {
            System.out.println("corrupted file!");
            JOptionPane.showMessageDialog(this, "Error while reading the file:\n" + e.getMessage(),
                    "Error", JOptionPane.ERROR_MESSAGE);
            spline = new BSpline();
        }
        spline.createSpline();
        viewer.setSpline(spline);
        settings.setSpline(spline);
        settings.updateSettings(this);
    }

    private void loadExample() {
        try {
            spline = reader.loadExample();
        } catch (IOException e) {
            System.out.println("corrupted file!");
            JOptionPane.showMessageDialog(this, "Error while reading the file:\n" + e.getMessage(),
                    "Error", JOptionPane.ERROR_MESSAGE);
            spline = new BSpline();
        }
//        spline.createSpline();
//        viewer.setSpline(spline);
//        settings.setSpline(spline);
//        settings.updateSettings(this);
    }

    private JMenuBar initMenu() {
        JMenuBar menuBar = new JMenuBar();

        JMenu file = new JMenu("File");

        JMenuItem save = new JMenuItem("Save as...");
        save.addActionListener( e -> FileBuilder.saveFile(SaveScreen.saveFile(), spline));
        file.add(save);

        JMenuItem open = new JMenuItem("Open...");
        open.addActionListener( e -> load(OpenScreen.openFile()));
        file.add(open);

        menuBar.add(file);

        return menuBar;
    }
}
