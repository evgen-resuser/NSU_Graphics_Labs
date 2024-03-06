package org.evgen.view;

import org.evgen.instruments.InstrumentsHandler;
import org.evgen.instruments.FitImage;
import org.evgen.instruments.ZoomInstrument;
import org.evgen.instruments.interfaces.IFilter;
import org.evgen.instruments.interfaces.IFilterSettings;
import org.evgen.view.windows.ISettings;

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

        scrollPane.revalidate();
        repaint();
    }

    public BufferedImage getOriginal() {
        return original;
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

    public void fitPic(int type) {
        if(original != null) {
            int w = scrollPane.getHorizontalScrollBar().getWidth();
            int h = scrollPane.getVerticalScrollBar().getHeight();

            if (w <= 0) w = scrollPane.getWidth();
            if (h <= 0) h = scrollPane.getHeight();

            setPreferredSize(new Dimension(w, h));

            FitImage fit = (FitImage) instruments.getInstrumentByName("FitToScreen");
            fit.setSettings(type, w, h);

            fitOriginal = fit.apply(original);
            fitFiltered = fit.apply(filtered);

            repaint();
        } else {
            JOptionPane.showMessageDialog(this, "Image has not been selected!",
                    "Error", JOptionPane.QUESTION_MESSAGE);
        }
    }

    public void zoomBySpinner(int value) {

        if (original == null) return;

        ZoomInstrument zoom = (ZoomInstrument) instruments.getInstrumentByName("Zoom");
        zoom.setParams(value, original.getWidth(), original.getHeight());

        fitFiltered = zoom.apply(filtered);
        fitOriginal = zoom.apply(original);

        setPreferredSize(new Dimension(zoom.getNewW(), zoom.getNewH()));

        repaint();
    }

    public void rotatePic(int angle) {

    }

    private final HashMap<String, IFilterSettings> savedSettings = new HashMap<>();
    public void applyFilter(String name, IFilterSettings settings) {

        if (original == null) return;

        IFilter filter = instruments.getFilterByName(name);
        filter.setParams(settings);
        savedSettings.put(name, settings);

        filtered = filter.apply(original);
        fitFiltered = filtered;
        showFiltered = true;

        repaint();
    }

    public void applyFilter(String name) {

        if (original == null) return;

        IFilter filter = instruments.getFilterByName(name);

        IFilterSettings settings = savedSettings.get(name);
        filter.setParams(settings);
        savedSettings.put(name, settings);

        filtered = filter.apply(original);
        fitFiltered = filtered;
        showFiltered = true;

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
