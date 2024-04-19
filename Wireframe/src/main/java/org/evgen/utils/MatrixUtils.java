package org.evgen.utils;

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
}
