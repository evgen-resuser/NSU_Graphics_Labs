package org.evgen.wireframe;

import lombok.Getter;
import lombok.Setter;
import org.evgen.editor.BSpline;
import org.evgen.utils.Matrix;
import org.evgen.utils.MatrixUtils;
import org.evgen.utils.SplinePoint;
import org.evgen.utils.Vector;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Getter
@Setter
public class Wireframe {

    private Matrix rotationMatrix;
    private Matrix cameraTranslateMatrix;
    private Matrix cameraPerspectiveMatrix;
    private Matrix translateMatrix;
    private Matrix normalizeMatrix;

    private List<Vector> wireframePoints;
    private List<Integer> edges;

    private double nearClip = 5.0; //for zoom
    private double r = 0;
    private double zoom = 5;

    public Wireframe(){

        this.rotationMatrix = new Matrix(new double[][]{
                {1, 0, 0, 0},
                {0, 0.5, -0.83, 0},
                {0, -0.83, 0.5, 0},
                {0, 0, 0, 1.0}
        });

        this.translateMatrix = new Matrix(new double[][]{
                {1.0, 0, 0, 0},
                {0, 1.0, 0, 0},
                {0, 0, 1.0, 0},
                {0, 0, 0, 1.0}
        });

        this.cameraTranslateMatrix = new Matrix(new double[][]{
                {1, 0, 0, 0},
                {0, 1, 0, 0},
                {0, 0, 1, zoom},
                {0, 0, 0, 1}
        });

        cameraPerspectiveMatrix = new Matrix(new double[][]{ //sh = sw = d = 1
                {2 * nearClip, 0, 0, 0},
                {0, 2 * nearClip, 0, 0},
                {0, 0, 1.0, 0},
                {0, 0, 1.0, 0}
        });
    }

    public void createWireframePoints(BSpline spline) {
        wireframePoints = new ArrayList<>();
        //translateMatrix = rotationMatrix.mulByMatrix(translateMatrix);
        //cameraTranslateMatrix = translateMatrix.mulByMatrix(cameraTranslateMatrix);

        int tmp = spline.getGeneratrixCount();

        double angle = 360.0 / tmp;
        double curAngle, cos, sin;
        List<SplinePoint> splinePoints = spline.getSplinePoints();

        for (int i = 0; i < tmp; i++) {
            curAngle = angle * i;
            sin = Math.sin(degToRad(curAngle));
            cos = Math.cos(degToRad(curAngle));

            for (SplinePoint p : splinePoints) {
                wireframePoints.add(new Vector(p.getY()*cos, p.getY()*sin, p.getX(), 1));
            }
        }
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

    private double degToRad(double deg) {
        return deg * 3.14 / 180;
    }

    public Map<Integer, List<SplinePoint>> getCube()
    {
        List<Vector> cubePoints = new ArrayList<>();
        cubePoints.add(new Vector(1.0, 1.0, 1.0, 1.0));
        cubePoints.add(new Vector(1.0, 1.0, -1.0, 1.0));
        cubePoints.add(new Vector(1.0, -1.0, 1.0, 1.0));
        cubePoints.add(new Vector(1.0, -1.0, -1.0, 1.0));
        cubePoints.add(new Vector(-1.0, 1.0, 1.0, 1.0));
        cubePoints.add(new Vector(-1.0, 1.0, -1.0, 1.0));
        cubePoints.add(new Vector(-1.0, -1.0, 1.0, 1.0));
        cubePoints.add(new Vector(-1.0, -1.0, -1.0, 1.0));

        wireframePoints = new ArrayList<>(cubePoints);
        setNormalizeMatrix();

        translateMatrix = MatrixUtils.getRotatedX(30)
                .mulByMatrix(normalizeMatrix)
        ;

        List<SplinePoint> wireframePoints = new ArrayList<>();
        for (Vector v : cubePoints)
        {
            Vector planePoint;

            planePoint = translateMatrix.mulByVector(v);

            wireframePoints.add(new SplinePoint(planePoint.getX(), planePoint.getY()));
        }

        List<SplinePoint> edges = new ArrayList<>();
        for (int i = 0; i < cubePoints.size(); i++)
        {
            for (int j = i + 1; j < cubePoints.size(); j++)
            {
                int diff = 0;
                if (Math.abs(cubePoints.get(i).getX() - cubePoints.get(j).getX()) == 2)
                    diff++;
                if (Math.abs(cubePoints.get(i).getY() - cubePoints.get(j).getY()) == 2)
                    diff++;
                if (Math.abs(cubePoints.get(i).getZ() - cubePoints.get(j).getZ()) == 2)
                    diff++;

                if (diff == 1)
                    edges.add(new SplinePoint(i, j));
            }
        }

        Map<Integer, List<SplinePoint>> cube = new HashMap<>();
        cube.put(0, wireframePoints);
        cube.put(1, edges);

        return cube;
    }




}