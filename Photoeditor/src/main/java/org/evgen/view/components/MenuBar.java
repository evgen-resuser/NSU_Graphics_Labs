package org.evgen.view.components;

import org.evgen.view.WorkPanel;
import org.evgen.view.windows.*;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

public class MenuBar extends JMenuBar {

    private final WorkPanel workPanel;
    private final ZoomPanel zoomPanel;
    private final HelpPageFrame helpPageFrame = new HelpPageFrame();
    private final AboutPageFrame aboutPageFrame = new AboutPageFrame();

    public MenuBar(WorkPanel workPanel, ZoomPanel zoomPanel) {
        this.workPanel = workPanel;
        this.zoomPanel = zoomPanel;
        initButtons();
    }

    private void initButtons() {
        add(initFile());
        add(initView());
        add(initFilters());
        add(initHelp());
    }

    private int lastType = 2;
    private JMenu initView() {
        JMenu view = new JMenu("View");

        JMenuItem fit = new JMenuItem("Fit to screen");
        fit.addActionListener(e -> workPanel.fitPic(lastType));
        view.add(fit);

        JMenuItem full = new JMenuItem("Full size");
        full.addActionListener(e -> workPanel.fullSize());
        view.add(full);

        JMenuItem rotate = new JMenuItem("Rotate image");
        rotate.addActionListener(e -> {
            int angle = RotateSettingsWindow.showWindow();
            workPanel.rotatePic(angle);
        });
        view.add(rotate);

        JMenu fitModes = new JMenu("Fit to screen modes");

        ButtonGroup types = new ButtonGroup();

        JRadioButton nearest = new JRadioButton("Nearest Neighbour");
        nearest.addActionListener(e -> {
            zoomPanel.setType(1);
            lastType = 1;
        });
        fitModes.add(nearest);
        types.add(nearest);
        nearest.setSelected(false);

        JRadioButton bilinear = new JRadioButton("Bilinear");
        bilinear.addActionListener(e -> {
            zoomPanel.setType(2);
            lastType = 2;
        });
        fitModes.add(bilinear);
        types.add(bilinear);
        bilinear.setSelected(true);

        JRadioButton bicubic = new JRadioButton("Bicubic");
        bicubic.addActionListener(e -> {
            zoomPanel.setType(3);
            lastType = 3;
        });
        fitModes.add(bicubic);
        types.add(bicubic);

        view.add(fitModes);

        return view;
    }

    private JMenu initHelp() {
        JMenu helpMenu = new JMenu("Help");

        JMenuItem help = new JMenuItem("Help");
        help.addActionListener(e -> helpPageFrame.setVisible(true));
        helpMenu.add(help);

        JMenuItem about = new JMenuItem("About");
        about.addActionListener(e -> aboutPageFrame.setVisible(true));
        helpMenu.add(about);

        return helpMenu;
    }

    private JMenu initFile() {
        JMenu files = new JMenu("File");

        JMenuItem open = new JMenuItem("Open");
        open.addActionListener(e -> {
            try {
                OpenScreen.openFile(workPanel, zoomPanel);
            } catch (IOException ex) {
                JOptionPane.showMessageDialog(workPanel, ex.getMessage());
            }
        });
        files.add(open);

        JMenuItem save = new JMenuItem("Save");
        save.addActionListener(e -> SaveScreen.saveFile(workPanel));
        files.add(save);

        return files;
    }

    private JMenu initFilters() {
        JMenu filters = new JMenu("Filters");

        JMenuItem whBlButton = new JMenuItem("White-Black filter");
        whBlButton.addActionListener(e -> workPanel.applyFilter("WhiteBlack"));
        filters.add(whBlButton);

        JMenuItem inversion = new JMenuItem("Invert colors");
        inversion.addActionListener(e -> workPanel.applyFilter("Inversion"));
        filters.add(inversion);

        JMenuItem sharpening = new JMenuItem("Sharpen image");
        sharpening.addActionListener(e -> workPanel.applyFilter("Sharpening"));
        filters.add(sharpening);

        JMenuItem embossing = new JMenuItem("Embossing filter");
        embossing.addActionListener(e -> workPanel.applyFilter("Embossing"));
        filters.add(embossing);

        filters.add(initEditableFilter("Gaussian Blur", "Gaussian", new GaussianSettingsFrame()));
        filters.add(initEditableFilter("Gamma Correction", "Gamma", new GammaSettingsFrame()));
        filters.add(initEditableFilter("Floyd-Steinberg Dithering", "FloydSteinberg", new DitheringSettingsFrame()));
        filters.add(initEditableFilter("Ordered Dithering", "Ordered", new DitheringSettingsFrame()));
        filters.add(initEditableFilter("Contouring filter", "Contouring", new ContouringSettings()));

        JMenuItem watercolors = new JMenuItem("Watercolor filter");
        watercolors.addActionListener(e -> workPanel.applyFilter("Watercolor"));
        filters.add(watercolors);

        JMenuItem dilation = new JMenuItem("Dilation filter");
        dilation.addActionListener(e -> workPanel.applyFilter("Dilation"));
        filters.add(dilation);

        return filters;
    }

    private JMenu initEditableFilter(String groupName, String filerName, ISettings settings) {
        JMenu newMenu = new JMenu(groupName);

        JMenuItem item = new JMenuItem("Apply "+groupName);
        item.addActionListener(new ButtonListener(filerName, settings));
        newMenu.add(item);

        JMenuItem settingsItem = new JMenuItem("Settings");
        settingsItem.addActionListener(e -> workPanel.saveSettings(filerName, settings.showWindow()));
        newMenu.add(settingsItem);

        return newMenu;
    }

    private class ButtonListener implements ActionListener{

        private final String name;
        private final ISettings settings;

        public ButtonListener(String name, ISettings settings) {
            this.name = name;
            this.settings = settings;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            boolean isSaved = workPanel.isSaved(name);
            if (!isSaved) {
                workPanel.saveSettings(name, settings.showWindow());
            }
            if (settings.getState() == JOptionPane.OK_OPTION || isSaved) {
                workPanel.applyFilter(name);
            }
        }
    }

}
