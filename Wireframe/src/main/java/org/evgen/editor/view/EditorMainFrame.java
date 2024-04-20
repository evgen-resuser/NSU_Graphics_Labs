package org.evgen.editor.view;

import lombok.Getter;
import lombok.Setter;
import org.evgen.components.AboutWindow;
import org.evgen.components.HelpWindow;
import org.evgen.components.OpenScreen;
import org.evgen.components.SaveScreen;
import org.evgen.editor.BSpline;
import org.evgen.editor.file.FileBuilder;
import org.evgen.editor.file.FileReader;
import org.evgen.utils.observer.Observer;
import org.evgen.utils.observer.Subject;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class EditorMainFrame extends JFrame implements Subject {

    private final EditorViewerPanel viewer;
    private final EditorSettingsPanel settings;

    private final AboutWindow aboutWindow = new AboutWindow();
    private final HelpWindow helpWindow = new HelpWindow();

    @Getter
    private final FileReader reader = new FileReader();

    @Getter
    @Setter
    private BSpline spline;

    public EditorMainFrame() {
        this.setPreferredSize(new Dimension(640, 640));
        this.setMinimumSize(new Dimension(640, 480));
        this.setTitle("Editor");
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setJMenuBar(initMenu());
        this.pack();

        loadExample();
        viewer = new EditorViewerPanel(spline);
        settings = new EditorSettingsPanel(this, spline);
        settings.updateSettings(this);

        this.getContentPane().add(viewer, BorderLayout.CENTER);
        this.getContentPane().add(settings, BorderLayout.SOUTH);
        this.getContentPane().revalidate();
        this.getContentPane().repaint();
    }

    public void load(String name) {
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

        JMenu help = new JMenu("Help");

        JMenuItem helpButton = new JMenuItem("Help");
        helpButton.addActionListener(e -> helpWindow.setVisible(true));

        JMenuItem aboutButton = new JMenuItem("About");
        aboutButton.addActionListener( e -> aboutWindow.setVisible(true));

        help.add(helpButton);
        help.add(aboutButton);

        menuBar.add(help);

        return menuBar;
    }

    private final ArrayList<Observer> observers = new ArrayList<>();

    @Override
    public void registerObserver(Observer observer) {
        observers.add(observer);
    }

    @Override
    public void notifyObservers() {
        for (Observer o : observers) {
            o.update(spline);
        }
    }
}
