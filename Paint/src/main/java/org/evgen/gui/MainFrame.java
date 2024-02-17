package org.evgen.gui;

import org.evgen.Main;
import org.evgen.Mode;
import org.evgen.gui.windows.*;
import org.evgen.gui.components.ColorPickerButton;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.Objects;

public class MainFrame extends JFrame {

    private final ImageIcon lineIcon = new ImageIcon(
            Objects.requireNonNull(Main.class.getClassLoader().getResource("icons/line.png")));
    private final ImageIcon curveIcon = new ImageIcon(Objects.requireNonNull(Main.class.getClassLoader().getResource("icons/curve.png")));
    private final ImageIcon fillIcon = new ImageIcon(Objects.requireNonNull(Main.class.getClassLoader().getResource("icons/fill.png")));
    private final ImageIcon eraseIcon = new ImageIcon(Objects.requireNonNull(Main.class.getClassLoader().getResource("icons/erase.png")));
    private final ImageIcon undoIcon = new ImageIcon(Objects.requireNonNull(Main.class.getClassLoader().getResource("icons/undo.png")));
    private final ImageIcon saveIcon = new ImageIcon(Objects.requireNonNull(Main.class.getClassLoader().getResource("icons/save.png")));
    private final ImageIcon settingsIcon = new ImageIcon(Objects.requireNonNull(Main.class.getClassLoader().getResource("icons/settings.png")));
    private final ImageIcon triangleIcon = new ImageIcon(Objects.requireNonNull(Main.class.getClassLoader().getResource("icons/triangle.png")));
    private final ImageIcon squareIcon = new ImageIcon(Objects.requireNonNull(Main.class.getClassLoader().getResource("icons/square.png")));
    private final ImageIcon pentagonIcon = new ImageIcon(Objects.requireNonNull(Main.class.getClassLoader().getResource("icons/pentagon.png")));
    private final ImageIcon polygonIcon = new ImageIcon(Objects.requireNonNull(Main.class.getClassLoader().getResource("icons/polygon.png")));
    private final ImageIcon eraseAllIcon = new ImageIcon(Objects.requireNonNull(Main.class.getClassLoader().getResource("icons/erase.png")));
    private final ImageIcon starIcon = new ImageIcon(Objects.requireNonNull(Main.class.getClassLoader().getResource("icons/star.png")));

    private final DrawPanel drawPanel = new DrawPanel();
    private final JToolBar toolBar = new JToolBar();

    private final PolygonSettingsFrame polygonSettingsFrame = new PolygonSettingsFrame(drawPanel);
    private final LineSettingsFrame lineSettingsFrame = new LineSettingsFrame(drawPanel);
    private final AboutPageFrame aboutPageFrame = new AboutPageFrame();
    private final HelpPageFrame helpPageFrame = new HelpPageFrame();
    private final ShapeSettingsFrame shapeSettingsFrame = new ShapeSettingsFrame(drawPanel);
    private final StarSettingsFrame starSettingsFrame = new StarSettingsFrame(drawPanel);
    private final SaveFileFrame saveFrame = new SaveFileFrame(drawPanel);
    private final OpenFileFrame openFileFrame = new OpenFileFrame(drawPanel);

    public MainFrame() throws IOException {
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setTitle("Paint");

        initToolBar();
        initMenu();

        JScrollPane pane = new JScrollPane(drawPanel);
        this.add(pane);

        this.add(toolBar, BorderLayout.NORTH);
        
        this.pack();

        this.setPreferredSize(new Dimension(640, 480));
        this.setMinimumSize(new Dimension(640, 480));
        this.setVisible(true);
    }

    private void initToolBar(){

        toolBar.setFloatable(false);

        JButton save = initButton("Save the image", saveIcon);
        save.addActionListener(saveAction);

        JButton clearAll = initButton("Erase all", eraseAllIcon);
        clearAll.addActionListener( e -> drawPanel.clearAll());

        JButton settings = initButton("Settings of chosen tool", settingsIcon);
        settings.addActionListener(settingsAction);

        JToggleButton line = initToggleButton("Straight line tool", lineIcon);
        JToggleButton polygon = initToggleButton("Draw a custom polygon", polygonIcon);
        JToggleButton curve = initToggleButton("Curve line tool", curveIcon);
        JToggleButton fill = initToggleButton("Fill area with a color", fillIcon);
        JToggleButton eraser = initToggleButton("Eraser tool", eraseIcon);
        JToggleButton triangle = initToggleButton("Draw a triangle", triangleIcon);
        JToggleButton square = initToggleButton("Draw a square", squareIcon);
        JToggleButton pentagon = initToggleButton("Draw a pentagon", pentagonIcon);
        JToggleButton star = initToggleButton("Draw a custom star", starIcon);

        JToggleButton red = new JToggleButton("    ");
        red.setSelected(true);
        red.setBackground(Color.RED);
        JToggleButton yellow = new JToggleButton("    ");
        yellow.setBackground(Color.YELLOW);
        JToggleButton green = new JToggleButton("    ");
        green.setBackground(Color.GREEN);
        JToggleButton blue = new JToggleButton("    ");
        blue.setBackground(Color.BLUE);
        JToggleButton black = new JToggleButton("    ");
        black.setBackground(Color.BLACK);
        ColorPickerButton custom = new ColorPickerButton(drawPanel);

        JButton undo = initButton("Undo last change", undoIcon);

        red.addActionListener( new ColorListener(Color.RED));
        yellow.addActionListener(new ColorListener(Color.YELLOW));
        green.addActionListener(new ColorListener(Color.GREEN));
        blue.addActionListener(new ColorListener(Color.BLUE));
        black.addActionListener(new ColorListener(Color.BLACK));

        line.addActionListener(e -> drawPanel.switchDrawing(Mode.LINE));
        line.setSelected(true);

        polygon.addActionListener(polyAction);

        star.addActionListener(starAction);

        curve.addActionListener(e -> drawPanel.switchDrawing(Mode.CURVE));

        fill.addActionListener(e -> drawPanel.switchDrawing(Mode.FILL));

        eraser.addActionListener(e -> drawPanel.switchErase());

        triangle.addActionListener(new ShapeAction(3));
        square.addActionListener(new ShapeAction(4));
        pentagon.addActionListener(new ShapeAction(5));

        undo.addActionListener( e -> drawPanel.undo());

        ButtonGroup group = new ButtonGroup();
        group.add(line);
        group.add(polygon);
        group.add(fill);
        group.add(curve);
        group.add(eraser);
        group.add(triangle);
        group.add(square);
        group.add(pentagon);
        group.add(star);

        ButtonGroup colors = new ButtonGroup();
        colors.add(red);
        colors.add(yellow);
        colors.add(green);
        colors.add(blue);
        colors.add(black);
        colors.add(custom);

        toolBar.add(save);
        toolBar.add(clearAll);

        toolBar.addSeparator();

        toolBar.add(settings);
        toolBar.addSeparator();

        toolBar.add(line);
        toolBar.add(curve);
        toolBar.add(fill);
        toolBar.add(eraser);

        toolBar.addSeparator();

        toolBar.add(triangle);
        toolBar.add(square);
        toolBar.add(pentagon);
        toolBar.add(polygon);

        toolBar.addSeparator();

        toolBar.add(star);

        toolBar.addSeparator();

        toolBar.add(red);
        toolBar.add(yellow);
        toolBar.add(green);
        toolBar.add(blue);
        toolBar.add(black);
        toolBar.add(custom);

        toolBar.addSeparator();

        toolBar.add(undo);

    }

    private class ColorListener implements ActionListener {

        private final Color color;

        ColorListener(Color color) {
            this.color = color;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            drawPanel.switchColor(color);
        }
    }

    private JToggleButton initToggleButton(String desc, ImageIcon icon) {
        JToggleButton b = new JToggleButton(icon);

        b.setToolTipText(desc);
        b.setFocusPainted(false);
        b.setBorder(new LineBorder(Color.LIGHT_GRAY, 1));

        return b;
    }

    private JButton initButton(String desc, ImageIcon icon) {
        JButton b = new JButton(icon);

        b.setToolTipText(desc);
        b.setFocusPainted(false);
        b.setBorder(new LineBorder(Color.LIGHT_GRAY, 1));

        return b;
    }

    private void  initMenu() {
        JMenuBar menuBar = new JMenuBar();
        this.setJMenuBar(menuBar);
        menuBar.setVisible(true);
        menuBar.add(createFileMenu());
        menuBar.add(createEditMenu());
        menuBar.add(createHelpMenu());
    }

    private JMenu createFileMenu() {
        JMenu file = new JMenu("File");

        JMenuItem open = new JMenuItem("Open");
        open.addActionListener( e -> openFileFrame.openFile());
        file.add(open);

        JMenuItem save = new JMenuItem("Save");
        save.addActionListener(saveAction);
        file.add(save);

        JMenuItem exit = new JMenuItem("Exit");
        exit.addActionListener(e -> exit());
        file.add(exit);

        return file;
    }

    private void exit() {
        int choice = JOptionPane.showConfirmDialog(this,
                "Do you want to save changes before leaving?", "Exit", JOptionPane.YES_NO_OPTION);
        if (choice == JOptionPane.YES_OPTION) {
            saveFrame.saveImage();
            System.exit(0);
        } else if (choice == JOptionPane.NO_OPTION) {
            System.exit(0);
        }
    }

    private JMenu createEditMenu() {
        JMenu edit = new JMenu("Edit");

        JMenuItem tools = new JMenu("Tools");

        JMenuItem line = new JMenuItem("Line");
        tools.add(line);
        line.addActionListener(e -> drawPanel.switchDrawing(Mode.LINE));

        JMenuItem curve = new JMenuItem("Curve");
        tools.add(curve);
        curve.addActionListener(e -> drawPanel.switchDrawing(Mode.CURVE));

        JMenuItem fill = new JMenuItem("Fill");
        tools.add(fill);
        fill.addActionListener(e -> drawPanel.switchDrawing(Mode.FILL));

        JMenuItem erase = new JMenuItem("Erase");
        tools.add(erase);
        erase.addActionListener(e -> drawPanel.switchErase());

        JMenuItem shapes = new JMenu("Shapes");

        JMenuItem tri = new JMenuItem("Triangle");
        shapes.add(tri);
        tri.addActionListener(new ShapeAction(3));

        JMenuItem squ = new JMenuItem("Square");
        shapes.add(squ);
        squ.addActionListener(new ShapeAction(4));

        JMenuItem penta = new JMenuItem("Pentagon");
        shapes.add(penta);
        penta.addActionListener(new ShapeAction(5));

        JMenuItem poly = new JMenuItem("Custom Polygon");
        shapes.add(poly);
        poly.addActionListener(polyAction);

        JMenuItem star = new JMenuItem("Star");
        star.addActionListener(starAction);

        JMenuItem settings = new JMenuItem("Settings", settingsIcon);
        settings.addActionListener(settingsAction);

        edit.add(tools);
        edit.add(shapes);
        edit.add(star);
        edit.addSeparator();
        edit.add(settings);

        return edit;
    }

    private JMenu createHelpMenu() {
        JMenu helpMenu = new JMenu("Help");

        JMenuItem help = new JMenuItem("Help", new ImageIcon("src/main/resources/icons/help.png"));
        help.addActionListener(e -> helpPageFrame.setVisible(true));

        JMenuItem about = new JMenuItem("About", new ImageIcon("src/main/resources/icons/info.png"));
        about.addActionListener( e -> aboutPageFrame.setVisible(true));

        helpMenu.add(help);
        helpMenu.add(about);

        return helpMenu;
    }

    private final AbstractAction saveAction = new AbstractAction() {
        public void actionPerformed(ActionEvent e) {
            saveFrame.saveImage();
        }
    };

    private final AbstractAction polyAction = new AbstractAction() {
        @Override
        public void actionPerformed(ActionEvent e) {
            drawPanel.switchDrawing(Mode.POLYGON);
            polygonSettingsFrame.applySettings();
        }
    };

    private final AbstractAction starAction = new AbstractAction() {
        @Override
        public void actionPerformed(ActionEvent e) {
            drawPanel.switchDrawing(Mode.STAR);
            starSettingsFrame.applySettings();
        }
    };

    private final AbstractAction settingsAction = new AbstractAction() {
        @Override
        public void actionPerformed(ActionEvent e) {
            switch (drawPanel.getPenMode()) {
                case LINE, CURVE -> lineSettingsFrame.setVisible(true);
                case FILL -> {
                    //no support
                }
                case POLYGON -> polygonSettingsFrame.setVisible(true);
                case SHAPE -> shapeSettingsFrame.setVisible(true);
                case STAR -> starSettingsFrame.setVisible(true);
            }
        }
    };

    private class ShapeAction extends AbstractAction {
        private final int count;

        public ShapeAction(int count) {
            this.count = count;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            shapeSettingsFrame.setCount(count);
            shapeSettingsFrame.applySettings();
            drawPanel.switchDrawing(Mode.SHAPE);
        }
    }
}
