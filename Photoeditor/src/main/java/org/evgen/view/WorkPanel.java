package org.evgen.view;

import org.evgen.instruments.InstrumentsHandler;
import org.evgen.instruments.FitImage;
import org.evgen.instruments.ZoomInstrument;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;

public class WorkPanel extends JPanel implements MouseListener, MouseMotionListener, MouseWheelListener {

    private static final int MARGIN = 5;

    private BufferedImage original;
    private BufferedImage filtered;
    private BufferedImage fitOriginal;

    private JScrollPane scrollPane;

    private int originalImageWidth;
    private int originalImageHeight;
    private int width;
    private int height;

    private Graphics2D g2d;

    private boolean showFiltered;

    private final InstrumentsHandler instruments;

    public WorkPanel(InstrumentsHandler instrumentsHandler, JScrollPane sp) {

        instruments = instrumentsHandler;

        scrollPane = sp;
        //scrollPane.setViewportView(this);
        scrollPane.setWheelScrollingEnabled(false);
        scrollPane.setDoubleBuffered(true);
        scrollPane.setViewportBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createEmptyBorder(MARGIN, MARGIN, MARGIN, MARGIN),
                BorderFactory.createDashedBorder(Color.BLACK, 5, 2))
        );

        scrollPane.revalidate();

        addMouseListener(this);
        addMouseMotionListener(this);
    }

    public void setOriginal(BufferedImage newImage) {
        original = newImage;
        fitOriginal = newImage;
        g2d = newImage.createGraphics();

        width = newImage.getWidth();
        height = newImage.getHeight();
        setPreferredSize(new Dimension(width, height));

        originalImageWidth = width;
        originalImageHeight = height;

        showFiltered = false;

        scrollPane.setViewportView(this);
        scrollPane.revalidate();
        repaint();
    }

    public BufferedImage getOriginal() {
        return original;
    }

    public void fullSize(){
        fitOriginal = original;
        repaint();
    }

    public void fitPic() {
        if(original != null) {
            int w = scrollPane.getHorizontalScrollBar().getWidth();
            int h = scrollPane.getVerticalScrollBar().getHeight();

            if (w <= 0) w = scrollPane.getWidth();
            if (h <= 0) h = scrollPane.getHeight();

            FitImage fit = (FitImage) instruments.getInstrumentByName("FitToScreen");
            fit.setSettings(2, w, h);
            fitOriginal = fit.apply(original);
            showFiltered = false;

            repaint();
        } else {
            JOptionPane.showMessageDialog(this, "Image has not been selected!",
                    "Error", JOptionPane.QUESTION_MESSAGE);
        }
    }

    public void zoomBySpinner(int value) {
        ZoomInstrument zoom = (ZoomInstrument) instruments.getInstrumentByName("Zoom");
        zoom.setParams(value, original.getWidth(), original.getHeight());

        fitOriginal = zoom.apply(original);
        showFiltered = false;

        repaint();
    }

    ////////////// OVERRIDE METHODS /////////////////

    @Override
    public void paintComponent(Graphics g) {
        scrollPane.revalidate(); //todo scroll pane fix
        super.paintComponent(g);

        if(!showFiltered) {
            g.drawImage(fitOriginal, 0, 0, this);
        } else {
            //g.drawImage(filteredImage, 0, 0, this);
        }
    }

    @Override
    public void mouseClicked(MouseEvent e) {

    }

    private Point pressed;

    @Override
    public void mousePressed(MouseEvent e) {
        pressed = e.getPoint();
    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {
        if (original != null)
            setCursor(Cursor.getPredefinedCursor(Cursor.MOVE_CURSOR));
    }

    @Override
    public void mouseExited(MouseEvent e) {

    }

    @Override
    public void mouseDragged(MouseEvent e) {
        Point move = scrollPane.getViewport().getViewPosition();
        move.x += (pressed.x - e.getX());
        move.y += (pressed.y - e.getY());

        scrollPane.getHorizontalScrollBar().setValue(move.x);
        scrollPane.getVerticalScrollBar().setValue(move.y);
        scrollPane.repaint();
    }

    @Override
    public void mouseMoved(MouseEvent e) {

    }

    @Override
    public void mouseWheelMoved(MouseWheelEvent e) {

    }
}
