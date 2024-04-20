package org.evgen.editor.view;

import org.evgen.editor.BSpline;
import org.evgen.utils.SplinePoint;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.List;

public class EditorViewerPanel extends JPanel implements MouseMotionListener, MouseListener, MouseWheelListener {

    private static final int DEFAULT_UNIT_PX = 30;
    private static final int RADIUS = 15;
    private static final int RADIUS_EXT = 20;

    private int unit = DEFAULT_UNIT_PX;

    private int centerX;
    private int centerY;
    private int offsetX;
    private int offsetY;
    private int h;
    private int w;

    private BSpline spline;
    private SplinePoint selected;
    private int selectedIndx;

    public EditorViewerPanel(BSpline spline) {
        this.spline = spline;
        this.setBackground(Color.BLACK);

        h = getHeight();
        w = getWidth();

        addMouseListener(this);
        addMouseMotionListener(this);
        addMouseWheelListener(this);

    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        w = getWidth() + offsetX;
        h = getHeight() + offsetY;

        centerX = w / 2;
        centerY = h / 2;

        drawAxis(g);
        drawControlPoints(g);
        drawSpline(g);

    }

    private void drawControlPoints(Graphics g) {
        g.setColor(Color.RED);
        ((Graphics2D)g).setStroke(new BasicStroke(1));
        List<SplinePoint> points = spline.getControlPoints();

        for (SplinePoint p : points) {
            g.drawOval((int)(centerX + p.getX() * unit - RADIUS /2.0),
                    (int)(centerY - p.getY() * unit - RADIUS /2.0),
                    RADIUS, RADIUS);
        }
        if (selected != null) {
            g.fillOval((int)(centerX + selected.getX() * unit - RADIUS /2.0),
                    (int)(centerY - selected.getY() * unit - RADIUS /2.0), RADIUS, RADIUS);
        }

        ((Graphics2D)g).setStroke(new BasicStroke(1, BasicStroke.CAP_BUTT, BasicStroke.JOIN_ROUND, 0, new float[]{2, 2}, 0));

        for(int i = 0; i < points.size() - 1; i++)
        {
            SplinePoint p1 = transformPoint(points.get(i));
            SplinePoint p2 = transformPoint(points.get(i+1));

            g.drawLine((int) p1.getX(), (int) p1.getY(), (int) p2.getX(), (int) p2.getY());
        }
    }

    private void drawSpline(Graphics g) {
        List<SplinePoint> points = spline.getSplinePoints();
        if (points == null) {
            return;
        }

        g.setColor(Color.CYAN);
        ((Graphics2D)g).setStroke(new BasicStroke(1));

        for(int i = 0; i < points.size() - 1; i++)
        {
            SplinePoint p1 = transformPoint(points.get(i));
            SplinePoint p2 = transformPoint(points.get(i+1));

            g.drawLine((int) p1.getX(), (int) p1.getY(), (int) p2.getX(), (int) p2.getY());
        }
    }

    private void drawAxis(Graphics g) {
        if (unit == 0) return;

        g.setColor(new Color(35, 35, 35));
        ((Graphics2D)g).setStroke(new BasicStroke(1));

        for (int x = centerX % unit; x < getWidth(); x += unit) {
            g.drawLine(x, 0, x, getHeight());
        }

        for (int y = centerY % unit; y < getHeight(); y += unit) {
            g.drawLine(0, y, getWidth(), y);
        }

        ((Graphics2D)g).setStroke(new BasicStroke(2));
        g.setColor(Color.GRAY);
        g.drawString("v", centerX+5, 15);
        g.drawString("u", getWidth() - 15, centerY-5);
        g.drawLine(centerX, 0, centerX, getHeight());
        g.drawLine(0, centerY, getWidth(), centerY);

    }

    private SplinePoint transformPoint(SplinePoint p) {

        double x = centerX + p.getX()* unit;
        double y = centerY - p.getY()* unit;

        return new SplinePoint(x, y);
    }

    private Pos type;
    private enum Pos {
        FIELD, POINT
    }
    private Pos checkPos(Point point) {

        var anchorPoints = spline.getControlPoints();

        int i = 0;
        for(SplinePoint p : anchorPoints)
        {
            double globalX = p.getX()*unit + centerX;
            double globalY = centerY - p.getY()*unit;

            if(point.x <= globalX+RADIUS_EXT && point.x >= globalX-RADIUS_EXT &&
                    point.y <= globalY+RADIUS_EXT && point.y >= globalY-RADIUS_EXT) {
                selectedIndx = i;
                selected = p;
                return Pos.POINT;
            }
            i++;
        }
        return Pos.FIELD;
    }

    @Override
    public void mouseClicked(MouseEvent e) {

        if(e.getButton() == MouseEvent.BUTTON1) {
            if (type == Pos.FIELD) {
                spline.addPoint(new SplinePoint((e.getX() - (double)centerX) / unit, ((double)centerY - e.getY()) / unit));
                selected = null;
                selectedIndx = -1;
            }
        }
        if(e.getButton() == MouseEvent.BUTTON3) {
            selected = null;
            selectedIndx = -1;

            if (spline.getControlPoints().size() <= 4) return;
            spline.deleteDot();
        }

        spline.createSpline();
        repaint();
    }

    int curX;
    int curY;
    @Override
    public void mousePressed(MouseEvent e) {
        curX = e.getX();
        curY = e.getY();

        type = checkPos(e.getPoint());
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        //no use
    }

    @Override
    public void mouseEntered(MouseEvent e) {
        //no use
    }

    @Override
    public void mouseExited(MouseEvent e) {
        //no use
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        if (type == Pos.POINT) {
            selected.setX((double) (e.getX() - centerX) / unit);
            selected.setY((double) (centerY - e.getY()) / unit);
            spline.updatePoint(selectedIndx, selected);
            spline.createSpline();
        } else {
            int dx = e.getX() - curX;
            int dy = e.getY() - curY;

            offsetX += dx / unit;
            offsetY += dy / unit;
        }
        repaint();
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        //no use
    }

    @Override
    public void mouseWheelMoved(MouseWheelEvent e) {
        int change = e.getWheelRotation();
        if (unit - change <= 0) return;
        unit -= change;
        repaint();
    }

    public void setSpline(BSpline spline) {
        this.spline = spline;
    }
}
