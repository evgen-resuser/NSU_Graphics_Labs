package org.evgen.view;

import org.evgen.instruments.InstrumentsHandler;
import org.evgen.util.ButtonMaker;
import org.evgen.view.components.MenuBar;
import org.evgen.view.components.ZoomPanel;
import org.evgen.view.windows.*;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.IOException;


public class MainFrame extends JFrame{

    private final JToolBar toolBar = new JToolBar(SwingConstants.VERTICAL);
    private final WorkPanel workPanel;
    private final ZoomPanel zoomBar;

    public MainFrame() throws IOException{
        this.setVisible(true);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setMinimumSize(new Dimension(640, 480));
        this.setTitle("Photo Editor");

        JScrollPane imageScrollPane = new JScrollPane();
        InstrumentsHandler instruments = new InstrumentsHandler();
        workPanel = new WorkPanel(instruments, imageScrollPane);
        workPanel.setVisible(true);
        zoomBar = new ZoomPanel(workPanel);

        add(imageScrollPane);
        setJMenuBar(new MenuBar(workPanel, zoomBar));

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
                JOptionPane.showMessageDialog(this,
                        ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        JButton saveButton = ButtonMaker.initButton("Save the file", IconsHandler.SAVE);
        saveButton.addActionListener(e -> saveFile());

        JToggleButton showOriginal =
                ButtonMaker.initToggleButton("Switch original/filtered image", IconsHandler.SWITCH);
        showOriginal.addActionListener(e -> workPanel.switchMode());

        toolBar.add(openButton);
        toolBar.add(saveButton);
        toolBar.addSeparator();
        toolBar.add(showOriginal);
        toolBar.addSeparator();

        initButtons();


        this.add(toolBar, BorderLayout.WEST);
        this.add(zoomBar, BorderLayout.SOUTH);

    }

    private void openFile() throws IOException {
        JFileChooser chooser = new JFileChooser();
        FileNameExtensionFilter filter =
                new FileNameExtensionFilter("Image files", "jpg", "png", "gif");
        chooser.setFileFilter(filter);
        int ret = chooser.showOpenDialog(this);
        if (ret == JFileChooser.APPROVE_OPTION) {
            zoomBar.setDefaults();
            File file = chooser.getSelectedFile();
            workPanel.setOriginal(ImageIO.read(file));
        }
    }

    private void saveFile() {
        if (workPanel.getFiltered() == null) {
            JOptionPane.showMessageDialog(this, "There is no changes to save.");
            return;
        }

        JFileChooser fileChooser = new JFileChooser();
        int returnValue = fileChooser.showSaveDialog(this);
        if (returnValue == JFileChooser.APPROVE_OPTION) {
            File image = new File(fileChooser.getSelectedFile() + ".png");
            try {
                ImageIO.write(workPanel.getFiltered(), "png", image);
            } catch (IOException e) {
                    JOptionPane.showMessageDialog(this, e.getMessage());
            }
        }
    }

    private void initButtons() {

        toolBar.add(createButton("WhiteBlack", "Apply white/black filter",
                IconsHandler.WHITE_BLACK, null));

        toolBar.add(createButton("Inversion", "Invert colors",
                IconsHandler.INVERSION, null));

        toolBar.add(createButton("Gaussian", "Gaussian blur",
                IconsHandler.GAUSSIAN, new GaussianSettingsFrame()));

        toolBar.add(createButton("Sharpening", "Sharpening tool",
                IconsHandler.SHARPENING, null));

        toolBar.add(createButton("Embossing", "Embossing effect",
                IconsHandler.EMBOSSING, null));

        toolBar.add(createButton("Gamma", "Gamma correction",
                IconsHandler.GAMMA, new GammaSettingsFrame()));

        toolBar.add(createButton("FloydSteinberg", "Floyd-Steinberg dithering tool",
                IconsHandler.DITHERING_ROUND, new DitheringSettingsFrame()));

        toolBar.add(createButton("Ordered", "Ordered dithering tool",
                IconsHandler.DITHERING_SQUARE, new DitheringSettingsFrame()));

        toolBar.add(createButton("Contouring", "Contour selection filter",
                IconsHandler.BORDERS, new ContouringSettings()));

        toolBar.add(createButton("Watercolor", "Watercolor filter",
                IconsHandler.WATERCOLOR, null));

        toolBar.add(createButton("Dilation", "Dilation filter",
                IconsHandler.DILATION, null));
    }

    private JButton createButton(String name, String desc, ImageIcon icon, ISettings settings) {
        JButton button = ButtonMaker.initButton(desc, icon);
        button.addMouseListener(new ButtonListener(name, settings));
        return button;
    }

    private class ButtonListener extends MouseAdapter {

        private final ISettings settings;
        private final String name;

        public ButtonListener(String name, ISettings settings) {
            this.name = name;
            this.settings = settings;
        }

        @Override
        public void mouseClicked(MouseEvent e) {
            if (settings == null) {
                zoomBar.setDefaults();
                workPanel.applyFilter(name);
                return;
            }

            boolean isSaved = workPanel.isSaved(name);
            if (e.getButton() == MouseEvent.BUTTON3 || !isSaved) {
                workPanel.saveSettings(name, settings.showWindow());
                if (settings.getState() == JOptionPane.OK_OPTION) {
                    zoomBar.setDefaults();
                    workPanel.applyFilter(name);
                }
            } else {
                zoomBar.setDefaults();
                workPanel.applyFilter(name);
            }

        }
    }
}
