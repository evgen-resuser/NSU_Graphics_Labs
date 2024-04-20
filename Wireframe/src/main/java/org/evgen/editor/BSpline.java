package org.evgen.editor;

import lombok.Getter;
import lombok.Setter;
import org.evgen.utils.SplinePoint;
import org.evgen.utils.Matrix;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class BSpline {

    private int segmentsNum = 4;
    private int pointsCount = 4;
    private int generatrixCount;
    private int linesInCircle;

    private double x;
    private double y;
    private double z;
    private double zf;

    private final Matrix splineMatrix = new Matrix(new double[][]{
                    {-1.0/6, 3.0/6, -3.0/6, 1.0/6},
                    {3.0/6, -6.0/6, 3.0/6, 0},
                    {-3.0/6, 0, 3.0/6, 0},
                    {1.0/6, 4.0/6, 1.0/6, 0}
            });

    private final List<SplinePoint> controlPoints = new ArrayList<>();
    private ArrayList<SplinePoint> splinePoints;

    public void createSpline() {
        if (controlPoints.size() < 4) return;

        int controlPointsCount = controlPoints.size();

        double step = 1.0 / segmentsNum;
        double x, y;
        double[] xs, ys, xVec, yVec;

        splinePoints = new ArrayList<>();

        for (int i = 1; i < controlPointsCount - 2; i++) {
            // r = T * M * G

            // M * G
            xs = new double[]{
                    controlPoints.get(i-1).getX(),
                    controlPoints.get(i).getX(),
                    controlPoints.get(i+1).getX(),
                    controlPoints.get(i+2).getX()
            };
            ys = new double[]{
                    controlPoints.get(i-1).getY(),
                    controlPoints.get(i).getY(),
                    controlPoints.get(i+1).getY(),
                    controlPoints.get(i+2).getY()
            };
            xVec = splineMatrix.mulByArray(xs);
            yVec = splineMatrix.mulByArray(ys);

            // MG * T
            double t;
            for (int j = 0; j <= segmentsNum; j++){
                t = j * step;
                x = t*t*t * xVec[0] + t*t * xVec[1] + t * xVec[2] + xVec[3];
                y = t*t*t * yVec[0] + t*t * yVec[1] + t * yVec[2] + yVec[3];
                splinePoints.add(new SplinePoint(x, y));
            }

        }
    }

    /////////////////

    public void addPoint(SplinePoint splinePoint) {
        controlPoints.add(splinePoint);
        createSpline();
    }

    public void deleteDot() {
        if (controlPoints.isEmpty()) return;
        controlPoints.remove(controlPoints.size() - 1);
    }

    public void updatePoint(int i, SplinePoint point) {
        if (i < 0 || i > controlPoints.size()) return;
        controlPoints.set(i, point);
    }

}
