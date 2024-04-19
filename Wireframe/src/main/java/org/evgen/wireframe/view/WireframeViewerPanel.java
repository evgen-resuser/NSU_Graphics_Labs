package org.evgen.wireframe.view;

import org.evgen.editor.BSpline;
import org.evgen.utils.Matrix;
import org.evgen.utils.MatrixUtils;
import org.evgen.utils.SplinePoint;
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

    private double zf = 100;
    private double zb = 100;

    private final Vector[] axis = new Vector[]{
            new Vector(5, 0, 0, 1),
            new Vector(0, 5, 0, 1),
            new Vector(0, 0, 5, 1),
    };
    private final Color[] axisColors = new Color[]{
            Color.RED, Color.BLUE, Color.GREEN
    };

    public WireframeViewerPanel(BSpline spline) {
        this.spline = spline;
    }

    public void setSpline(BSpline spline) {
        this.spline = spline;
        this.wireframe = new Wireframe();

        addMouseListener(this);
        addMouseMotionListener(this);
        addMouseWheelListener(this);
    }

    Matrix cameraTranslateMatrix = new Matrix(new double[][]{
        {1, 0, 0, 0},
        {0, 1, 0, 0},
        {0, 0, 1, 20},
        {0, 0, 0, 1}
    });

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        Graphics2D g2d = (Graphics2D) g;
        g2d.setStroke(new BasicStroke(2));

        int centerX = getWidth()/2;
        int centerY = getHeight()/2;

        if (spline.getControlPoints().size() < 4 || spline.getSplinePoints() == null) {
            var cube = wireframe.getCube();
            var cubePoints = cube.get(0);
            var cubeEdges = cube.get(1);


            for(SplinePoint e : cubeEdges)
            {

                g2d.drawLine(
                        (int) (centerX + cubePoints.get((int) e.getX()).getX()*200),
                        (int) (centerY - cubePoints.get((int) e.getX()).getY()*200),
                        (int) (centerX + cubePoints.get((int) e.getY()).getX()*200),
                        (int) (centerY - cubePoints.get((int) e.getY()).getY()*200)
                );
            }
        } else {
            wireframe.createWireframePoints(spline);

            Matrix resultMatrix;

            Matrix mproj = MatrixUtils.getMproj(8, 8, zf, zb);
            Matrix rotatedX = MatrixUtils.getRotatedX(Math.toRadians(0));
            Matrix rotatedY = MatrixUtils.getRotatedY(Math.toRadians(125));
            Matrix rotatedZ = MatrixUtils.getRotatedZ(Math.toRadians(235));
            Matrix rox1 = MatrixUtils.getRotatedX(0);
            Matrix sum = rotatedX.mulByMatrix(rox1).mulByMatrix(rotatedY).mulByMatrix(rotatedZ);

            resultMatrix = mproj
                    .mulByMatrix(cameraTranslateMatrix)
                    .mulByMatrix(sum)
            ;

            Matrix axisM = sum.mulByMatrix(MatrixUtils.getScaleMatrix(10));

            int pointsCount = spline.getSplinePoints().size();

            double degGen = 360.0 / spline.getGeneratrixCount();

            ArrayList<Vector> points = new ArrayList<>();
            for (int  j = 0; j < spline.getGeneratrixCount(); j++){
                double deg = j * degGen;
                Matrix Rz = MatrixUtils.getRotatedX(Math.toRadians(deg)); //перепутаны оси!!!!!
                Matrix R = resultMatrix.mulByMatrix(Rz);

                for (int i = 0; i < pointsCount - 1; i++){
                    Vector p1 = spline.getSplinePoints().get(i).toVector();
                    Vector p2 = spline.getSplinePoints().get(i+1).toVector();
                    Vector r1 = R.mulByVector(p1);
                    Vector r2 = R.mulByVector(p2);
                    r1.normalize();
                    r2.normalize();
                    int x1 = (int)r1.getX();
                    int y1 = (int)r1.getY();
                    int x2 = (int)r2.getX();
                    int y2 = (int)r2.getY();

                    g.drawLine(x1 + getWidth() / 2, y1 + getHeight() / 2,
                            x2 + getWidth() / 2, y2 + getHeight() / 2);
                }
            }

            for (int i = 0; i < 3; i++) {
                g.setColor(axisColors[i]);
                Vector r2 = axisM.mulByVector(axis[i]);
                r2.normalize();
                int x2 = (int)r2.getX();
                int y2 = (int)r2.getY();
                g.drawLine(50, 50, x2 + 50, y2 + 50);
            }

        }
    }

    @Override
    public void mouseClicked(MouseEvent e) {

    }

    @Override
    public void mousePressed(MouseEvent e) {
        prevPoint = e.getPoint();
    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }

    @Override
    public void mouseDragged(MouseEvent e) {

    }

    @Override
    public void mouseMoved(MouseEvent e) {

    }

    @Override
    public void mouseWheelMoved(MouseWheelEvent e) {

        int coeff = e.getWheelRotation() * 5;
        if (zb - coeff <= 0 || zf - coeff <= 0)
            return;

        zf -= coeff;
        repaint();
    }
}
