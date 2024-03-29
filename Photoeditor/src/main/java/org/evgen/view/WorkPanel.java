package org.evgen.view;

import org.evgen.instruments.InstrumentsHandler;
import org.evgen.instruments.FitImage;
import org.evgen.instruments.ZoomInstrument;
import org.evgen.instruments.interfaces.IFilter;
import org.evgen.instruments.interfaces.IFilterSettings;
import org.evgen.instruments.Rotate;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.util.HashMap;

public class WorkPanel extends JPanel implements MouseListener, MouseMotionListener, MouseWheelListener {

    private static final int MARGIN = 5;

    private BufferedImage original;
    private BufferedImage filtered;
    private BufferedImage fitOriginal;
    private BufferedImage fitFiltered;

    private final JScrollPane scrollPane;

    private int width;
    private int height;

    private boolean showFiltered;

    private final InstrumentsHandler instruments;

    public WorkPanel(InstrumentsHandler instrumentsHandler, JScrollPane sp) {

        instruments = instrumentsHandler;

        scrollPane = sp;
        scrollPane.setViewportView(this);
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

        width = newImage.getWidth();
        height = newImage.getHeight();
        setPreferredSize(new Dimension(width, height));

        showFiltered = false;

        repaint();
    }

    public BufferedImage getFiltered() {
        return filtered;
    }

    public void fullSize(){
        if (original == null) {
            JOptionPane.showMessageDialog(this, "Image has not been selected!",
                    "Error", JOptionPane.QUESTION_MESSAGE);
            return;
        }

        fitOriginal = original;
        fitFiltered = filtered;

        setPreferredSize(new Dimension(width, height));
        repaint();
    }

    private final FitImage fit = new FitImage();
    public void fitPic(int type) {

        if (original == null) {
            JOptionPane.showMessageDialog(this, "Image has not been selected!",
                    "Error", JOptionPane.QUESTION_MESSAGE);
            return;
        }

        int w = scrollPane.getHorizontalScrollBar().getWidth();
        int h = scrollPane.getVerticalScrollBar().getHeight();

        if (w <= 0) w = scrollPane.getWidth();
        if (h <= 0) h = scrollPane.getHeight();

        setPreferredSize(new Dimension(w, h));

        fit.setSettings(type, w, h);

        fitOriginal = fit.apply(original);
        fitFiltered = fit.apply(filtered);

        repaint();
    }

    private final ZoomInstrument zoom = new ZoomInstrument();
    public void zoomBySpinner(int value) {

        if (original == null) return;

        int newH, newHf = 0, newW, newWf = 0;

        zoom.setParams(value, original.getWidth(), original.getHeight());
        fitOriginal = zoom.apply(original);
        newH = zoom.getNewH();
        newW = zoom.getNewW();

        if (filtered != null) {
            zoom.setParams(value, filtered.getWidth(), filtered.getHeight());
            fitFiltered = zoom.apply(filtered);
            newHf = zoom.getNewH();
            newWf = zoom.getNewW();
        }

        if (showFiltered) setPreferredSize(new Dimension(newWf, newHf));
        else setPreferredSize(new Dimension(newW, newH));

        repaint();
    }

    private final Rotate rotate = new Rotate();
    public void rotatePic(int angle) {
        if (original == null) return;

        rotate.setAngle(angle);
        filtered = rotate.apply(original);
        fitFiltered = filtered;
        showFiltered = true;

        setPreferredSize(new Dimension(filtered.getWidth(), filtered.getHeight()));

        repaint();

    }

    private final HashMap<String, IFilterSettings> savedSettings = new HashMap<>();
    public void applyFilter(String name) {

        if (original == null) return;

        IFilter filter = instruments.getFilterByName(name);

        IFilterSettings settings = savedSettings.get(name);
        filter.setParams(settings);
        savedSettings.put(name, settings);

        filtered = filter.apply(original);
        fitFiltered = filtered;
        showFiltered = true;

        zoomBySpinner(100);

        repaint();
    }

    public void saveSettings(String filter, IFilterSettings settings){
        if (settings == null) return;
        this.savedSettings.put(filter, settings);
    }

    public boolean isSaved(String name) {
        return savedSettings.containsKey(name);
    }

    public void switchMode() {
        if (filtered == null) return;
        showFiltered = !showFiltered;

        repaint();
    }

    ////////////// OVERRIDE METHODS /////////////////

    @Override
    public void paintComponent(Graphics g) {
        scrollPane.revalidate();
        super.paintComponent(g);

        if(!showFiltered) {
            g.drawImage(fitOriginal, 0, 0, this);
        } else {
            g.drawImage(fitFiltered, 0, 0, this);
        }
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        //wip
    }

    private Point pressed;

    @Override
    public void mousePressed(MouseEvent e) {
        pressed = e.getPoint();
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        //wip
    }

    @Override
    public void mouseEntered(MouseEvent e) {
        //wip
    }

    @Override
    public void mouseExited(MouseEvent e) {
        //wip
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
        //wip
    }

    @Override
    public void mouseWheelMoved(MouseWheelEvent e) {
        //wip
    }
}
