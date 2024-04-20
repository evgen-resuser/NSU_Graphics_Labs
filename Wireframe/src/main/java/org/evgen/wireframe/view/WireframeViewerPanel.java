package org.evgen.wireframe.view;

import org.evgen.editor.BSpline;
import org.evgen.utils.Matrix;
import org.evgen.utils.MatrixUtils;
import org.evgen.utils.Vector;
import org.evgen.wireframe.Wireframe;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

public class WireframeViewerPanel extends JPanel implements MouseListener, MouseMotionListener, MouseWheelListener {

    private BSpline spline;
    private Wireframe wireframe;
    private Point prevPoint;

    private final Vector[] axis = new Vector[]{
            new Vector(5, 0, 0, 1),
            new Vector(0, 5, 0, 1),
            new Vector(0, 0, 5, 1),
    };
    private final Color[] axisColors = new Color[]{
            Color.RED, Color.BLUE, Color.GREEN
    };
    private final String[] axisNames = new String[]{"x", "y", "z"};

    public WireframeViewerPanel(BSpline spline) {
        this.spline = spline;
    }

    public void setSpline(BSpline spline) {
        this.spline = spline;
        this.wireframe = new Wireframe();
        //wireframe.initSum(spline.getX(), spline.getY(), spline.getZ());
        //wireframe.setZf(spline.getZf());
        //System.out.println("set: " + spline.getGeneratrixCount());
        repaint();

        addMouseListener(this);
        addMouseMotionListener(this);
        addMouseWheelListener(this);
    }

    int centerX;
    int centerY;
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        Graphics2D g2d = (Graphics2D) g;
        g2d.setStroke(new BasicStroke(2));

        //System.out.println(spline.getGeneratrixCount());

        //paint wireframe

        centerX = getWidth()/2;
        centerY = getHeight()/2;

        wireframe.createWireframePoints(spline);

        double med = (wireframe.getZMax() + wireframe.getZMin()) / 2 ;

        int n = spline.getSplinePoints().size();

        for (int j = 0; j < spline.getGeneratrixCount(); j++) {
            for (int i = 0; i < n-1; i++) {

                Vector p1 = wireframe.getWireframePoints().get(j * n + i);
                Vector p2 = wireframe.getWireframePoints().get(j * n + i+1);

                draw(g, p1, p2, Color.BLACK);

//                if (p1.getZ() <= med)
//                    draw(g, p1, p2, Color.GRAY);
//                else
//                    draw(g, p1, p2, Color.BLACK);

            }
        }

        ArrayList<ArrayList<Vector>> circles = wireframe.getCircles();

        for (int i =0; i < circles.size(); ++i) {
            for (int j = 0; j < circles.get(i).size() - 1; j++){
                Vector p1 = circles.get(i).get(j);
                Vector p2 = circles.get(i).get(j + 1);
                draw(g, p1, p2, Color.BLACK);
            }
        }


        printAxisHint(g);

    }

    private void draw(Graphics g, Vector p1, Vector p2, Color color) {
        g.setColor(color);
        g.drawLine(
                (int)p1.getX() + centerX, (int)p1.getY() + centerY,
                (int)p2.getX() + centerX, (int)p2.getY() + centerY
        );
    }

    private void printAxisHint(Graphics g) {
        Matrix axisM = wireframe.getSum().mulByMatrix(MatrixUtils.getScaleMatrix(10));
        for (int i = 0; i < 3; i++) {
            g.setColor(axisColors[i]);
            Vector r2 = axisM.mulByVector(axis[i]);
            r2.normalize();
            int x2 = (int)r2.getX();
            int y2 = (int)r2.getY();
            g.drawLine(60, 60, x2 + 60, y2 + 60);
            g.drawString(axisNames[i], x2 + 65, y2 + 65);
        }
    }

    public void resetRotate() {
        wireframe.setZf(100);
        wireframe.setSum(MatrixUtils.getRotatedY(Math.toRadians(90)));
        spline.setX(0);
        spline.setY(Math.toRadians(90));
        spline.setZ(0);
        spline.setZf(100);
        repaint();
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        //no use
    }

    @Override
    public void mousePressed(MouseEvent e) {
        prevPoint = e.getPoint();
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
        int dy = 0;
        int dx = 0;
        int dz = 0;
        if (SwingUtilities.isLeftMouseButton(e)) {
            dy = e.getY() - prevPoint.y;
            dx = e.getX() - prevPoint.x;

        }
        if (SwingUtilities.isRightMouseButton(e)) {
            dz = e.getY() - prevPoint.y + (e.getX() - prevPoint.x);
        }

        wireframe.setXAngle(dy * 2.0 / 3);
        spline.setX(dy * 2.0 / 3);
        wireframe.setYAngle(-dx * 2.0 / 3);
        spline.setY(-dx * 2.0 / 3);
        wireframe.setZAngle(dz * 2.0 / 3);
        spline.setZ(dz * 2.0 / 3);

        Matrix rox = MatrixUtils.getRotatedX(Math.toRadians((dy)) * 2 / 3);
        Matrix roy = MatrixUtils.getRotatedY(Math.toRadians((-dx)) * 2 / 3);
        Matrix roz = MatrixUtils.getRotatedZ(Math.toRadians(dz) * 2 / 3);
        wireframe.setSum(rox.mulByMatrix(roy).mulByMatrix(roz).mulByMatrix(wireframe.getSum()));
        prevPoint = e.getPoint();

        repaint();
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        //no use
    }

    @Override
    public void mouseWheelMoved(MouseWheelEvent e) {
        int coeff = e.getWheelRotation();
        wireframe.incZf(coeff);
        spline.setZf(wireframe.getZf());
        repaint();
    }
}
