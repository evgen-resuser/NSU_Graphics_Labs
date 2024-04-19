package org.evgen.utils;

import java.util.List;

public class MatrixUtils {

    private MatrixUtils(){}

    public static Matrix getScaleMatrix(double scale) {
        return new Matrix(new double[][]{
                {scale, 0, 0, 0},
                {0, scale, 0, 0},
                {0, 0, scale, 0},
                {0, 0, 0, 1}
        });
    }

    public static Matrix getRotatedX(double angle) {
        return new Matrix(new double[][]{
                {1, 0, 0, 0},
                {0, Math.cos(angle), -Math.sin(angle), 0},
                {0, Math.sin(angle), Math.cos(angle), 0},
                {0, 0, 0, 1}
        });
    }

    public static Matrix getRotatedY(double angle) {
        return new Matrix(new double[][]{
                {Math.cos(angle), 0, Math.sin(angle), 0},
                {0, 1, 0, 0},
                {-Math.sin(angle), 0, Math.cos(angle), 0},
                {0, 0, 0, 1}
        });
    }

    public static Matrix getRotatedZ(double angle) {
        return new Matrix(new double[][]{
                {Math.cos(angle), -Math.sin(angle), 0, 0},
                {Math.sin(angle), Math.cos(angle), 0, 0},
                {0, 0, 1, 0},
                {0, 0, 0, 1}
        });
    }

    /**
     * Conversion to a half cube
     *
     * @param  Sw    front clipping plane width
     * @param  Sh    front clipping plane height
     * @param  zf    distance from the origin of the coordinates to the front clipping plane
     * @param  zb    distance from the origin of the coordinates to the back clipping plane
     * @return       Matrix of Conversion to a half cube
     */
    public static Matrix getMproj(double Sw, double Sh, double zf, double zb) {
        return new Matrix(new double[][]{
                {2.0 * zf * Sw, 0, 0, 0},
                {0, 2.0 * zf * Sh, 0, 0},
                {0, 0, zb / (zb - zf), (-zf * zb) / (zb - zf)},
                {0, 0, 1, 0}
        }
        );
    }

    public static Matrix setNormalizeMatrix(List<Vector> wireframePoints) {

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

        return new Matrix(new double[][]{
                {1.0/res, 0, 0, 0},
                {0, 1.0/res, 0, 0},
                {0, 0, 1.0/res, 0},
                {0, 0, 0, 1.0}
        });
    }

}
