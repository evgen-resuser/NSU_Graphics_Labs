package org.evgen.utils;

public class Matrix {

    private final double[][] data;
    private final int h;
    private final int w;

    public Matrix(double[][] matrix) {
        this.w = matrix[0].length;
        this.h = matrix.length;
        this.data = new double[h][w];

        for(int i = 0; i < matrix.length; i++) {
            System.arraycopy(matrix[i], 0, this.data[i], 0, matrix[0].length);
        }
    }

    public Vector mulByVector (Vector v) {
        double[] pointArr = new double[]{v.getX(), v.getY(), v.getZ(), v.getW()};
        double[] result = new double[4];
        for(int i = 0; i < h; i++)
            for(int j = 0; j < w; j++)
                result[i] += data[i][j] * pointArr[j];
        return new Vector(result[0] / result[3], result[1] / result[3], result[2]/result[3], 1.0);
    }

    public Matrix mulByMatrix(Matrix matrix) {
        Matrix result = new Matrix(new double[][]{
                {0, 0, 0, 0},
                {0, 0, 0, 0},
                {0, 0, 0, 0},
                {0, 0, 0, 0}
        });
        for(int i = 0; i < h; i++)
            for(int j = 0; j < w; j++)
                for(int k = 0; k < 4; k++)
                    result.data[i][j] += data[i][k] * matrix.data[k][j];
        return result;
    }

    public double[] mulByArray(double[] vec) {
        double[] result = new double[vec.length];
        for(int i = 0; i < h; i++)
            for(int j = 0; j < w; j++)
                result[i] += data[i][j] * vec[j];
        return result;
    }

}
