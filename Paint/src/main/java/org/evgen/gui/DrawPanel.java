package org.evgen.gui;

import org.evgen.Mode;
import org.evgen.history.LimitedList;
import org.evgen.interfaces.ColorReceiver;
import org.evgen.tools.fill.FillTool;
import org.evgen.tools.line.LineDrawer;
import org.evgen.interfaces.IPolygon;
import org.evgen.tools.polygon.PolygonSettings;
import org.evgen.tools.polygon.PolygonTool;
import org.evgen.tools.star.StarSettings;
import org.evgen.tools.star.StarTool;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.awt.image.Raster;

public class DrawPanel extends JPanel implements MouseListener, MouseMotionListener, ColorReceiver {

    private BufferedImage image;
    private Graphics2D graphics;

    private int minWidth = 640;
    private int minHeight = 480;

    private final FillTool fillTool = new FillTool();

    private Mode penMode = Mode.LINE;
    private Color chosenColor = Color.RED;
    private int thickness = 5;

    private IPolygon polygonSettings = new PolygonSettings(50, 45, 3, 2);


    private final LimitedList<Raster> history = new LimitedList<>(15);

    public DrawPanel() {
        image = new BufferedImage(minWidth, minHeight, BufferedImage.TYPE_INT_RGB);
        graphics = image.createGraphics();

        fillWhite();
        history.add(image.getData());

        addMouseListener(this);
        addMouseMotionListener(this);

        this.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                super.componentResized(e);
                resizeImage(DrawPanel.super.getWidth(), DrawPanel.super.getHeight());
            }
        });
    }

    public void fillWhite() {
        graphics.setBackground(Color.WHITE);
        graphics.setBackground(Color.WHITE);
        graphics.fillRect(0, 0, minWidth, minHeight);
        repaint();
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(image, 0, 0, this);
    }

    private Point lastClick = null;
    @Override
    public void mouseClicked(MouseEvent e) {
        switch (penMode) {
            case LINE, CURVE -> LineDrawer.drawLine(image, thickness, chosenColor, e.getPoint(), e.getPoint());
            case FILL -> fillTool.fill(image, e.getPoint(), chosenColor);
            case POLYGON, SHAPE -> PolygonTool.draw(image, e.getPoint(), chosenColor, (PolygonSettings) polygonSettings);
            case STAR -> StarTool.draw(image, e.getPoint(), chosenColor, (StarSettings) polygonSettings);
        }
        repaint();
    }

    @Override
    public void mousePressed(MouseEvent e) {
        if (penMode == Mode.LINE || penMode == Mode.CURVE) {
            lastClick = e.getPoint();
        }
        isPressed = true;
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        history.add(image.getData());
        if (penMode == Mode.LINE && lastClick != null) {
            LineDrawer.drawLine(image, thickness, chosenColor, lastClick, e.getPoint());
            repaint();
            lastClick = null;
        }
        isPressed = false;
    }

    @Override
    public void mouseEntered(MouseEvent e) {
        // no supported
    }

    @Override
    public void mouseExited(MouseEvent e) {
        // no supported
    }

    private boolean isPressed = false;

    @Override
    public void mouseDragged(MouseEvent e) {
        if (penMode != Mode.CURVE || !isPressed) return;

        Point tmp = e.getPoint();
        LineDrawer.drawLine(image, thickness, chosenColor, lastClick, tmp);
        lastClick = tmp;
        repaint();
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        // no supported
    }

    public void setPenMode(Mode penMode) {
        this.penMode = penMode;
    }

    public Mode getPenMode() {
        return penMode;
    }

    public void setChosenColor(Color chosenColor) {
        lastColor = chosenColor;
        this.chosenColor = chosenColor;
    }

    public void setThickness(int thickness) {
        this.thickness = thickness;
    }

    public void setPolygonSettings(IPolygon settings) {
        this.polygonSettings = settings;
    }


    public void resizeImage(int width, int height) {
        this.minWidth = width;
        this.minHeight = height;
        this.setPreferredSize(new Dimension(width, height));

        BufferedImage newImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        this.graphics = newImage.createGraphics();
        fillWhite();
        newImage.setData(image.getData());
        this.image = newImage;

        repaint();
    }

    private Color lastColor = chosenColor;
    public void switchErase() {
        lastColor = chosenColor;
        chosenColor = Color.WHITE;
        penMode = Mode.CURVE;
    }

    public void switchDrawing(Mode mode) {
        penMode = mode;
        chosenColor = lastColor;
    }

    public void switchColor(Color color) {
        lastColor = color;
        chosenColor = color;
    }

    public void undo() {
        Raster save = history.getLast();
        if (save != null) {
            fillWhite();
            image.setData(save);
            repaint();
        }
    }

    public void clearAll() {
        history.add(image.getData());
        fillWhite();
    }

    public BufferedImage getImage() {
        return image;
    }

    public void setImage(BufferedImage image) {
        this.image = image;
        repaint();
    }
}
