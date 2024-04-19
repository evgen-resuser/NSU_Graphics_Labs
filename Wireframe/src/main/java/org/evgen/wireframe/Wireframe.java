package org.evgen.wireframe;

import lombok.Getter;
import lombok.Setter;
import org.evgen.editor.BSpline;
import org.evgen.utils.Matrix;
import org.evgen.utils.MatrixUtils;
import org.evgen.utils.SplinePoint;
import org.evgen.utils.Vector;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class Wireframe {

    private static final int DEFAULT_X_ANGLE = 0;
    private static final int DEFAULT_Y_ANGLE = 90;
    private static final int DEFAULT_Z_ANGLE = 0;

    private Matrix cameraTranslateMatrix;
    private Matrix normalizeMatrix;

    private List<Vector> wireframePoints;
    private List<Integer> edges;

    public Wireframe(){
        this.cameraTranslateMatrix = new Matrix(new double[][]{
                {1, 0, 0, 0},
                {0, 1, 0, 0},
                {0, 0, 1, 20},
                {0, 0, 0, 1}
        });
    }

    public void incZf(int v){
        if (zf + v < 0)
            return;
        zf += v;
    }

    private double xAngle = DEFAULT_X_ANGLE;
    private double yAngle = DEFAULT_Y_ANGLE;
    private double zAngle = DEFAULT_Z_ANGLE;
    private double zf = 16;
    private double zb = 100;
    Matrix resultMatrix;
    Matrix sum = MatrixUtils.getRotatedY(Math.toRadians(90));

    public void createWireframePoints(BSpline spline) {
        wireframePoints = new ArrayList<>();

        int m = spline.getGeneratrixCount();

        Matrix mProj = MatrixUtils.getMproj(8, 8, zf, zb);

        resultMatrix = mProj
                .mulByMatrix(cameraTranslateMatrix)
                .mulByMatrix(sum)
        ;

        double angle = 360.0 / m;
        double curAngle, cos, sin;
        List<SplinePoint> splinePoints = spline.getSplinePoints();

        for (int i = 0; i < m; i++) {
            curAngle = angle * i;
            sin = Math.sin(Math.toRadians(curAngle));
            cos = Math.cos(Math.toRadians(curAngle));

            for (SplinePoint p : splinePoints) {

                Vector point = new Vector(p.getY()*cos, p.getY()*sin, p.getX(), 1);
                point = resultMatrix.mulByVector(point);

                wireframePoints.add(point);
            }

        }

        setNormalizeMatrix();

    }

    private void setNormalizeMatrix()
    {
        double maxX = wireframePoints.get(0).getX(), minX = wireframePoints.get(0).getX();
        double maxY = wireframePoints.get(0).getY(), minY = wireframePoints.get(0).getY();
        double maxZ = wireframePoints.get(0).getZ(), minZ = wireframePoints.get(0).getZ();

        for(Vector v : wireframePoints)
        {
            if(v.getX() > maxX)
                maxX = v.getX();
            if(v.getY() > maxY)
                maxY = v.getY();
            if(v.getZ() > maxZ)
                maxZ = v.getZ();

            if(v.getX() < minX)
                minX = v.getX();
            if(v.getY() < minY)
                minY = v.getY();
            if(v.getZ() < minZ)
                minZ = v.getZ();
        }

        double distX = maxX - minX;
        double distY = maxY - minY;
        double distZ = maxZ - minZ;

        double res = Math.max(distX, Math.max(distY, distZ));
        if(res == 0.0)
            res = 1.0;

        normalizeMatrix = new Matrix(new double[][]{
                {1.0/res, 0, 0, 0},
                {0, 1.0/res, 0, 0},
                {0, 0, 1.0/res, 0},
                {0, 0, 0, 1.0}
        });
    }

}