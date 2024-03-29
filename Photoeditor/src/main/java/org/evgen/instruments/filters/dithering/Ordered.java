package org.evgen.instruments.filters.dithering;

import org.evgen.instruments.interfaces.IFilter;
import org.evgen.instruments.interfaces.IFilterSettings;
import org.evgen.util.ColorUtil;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Arrays;

public class Ordered implements IFilter {

    private int redCoeff;
    private int blueCoeff;
    private int greenCoeff;

    private final double[][] baseMatrix = {
            {0, 2},
            {3, 1}
    };

    private int[] redShades;
    private int[] greenShades;
    private int[] blueShades;
    private int matrixSize;

    private void initPalettes() {
        redShades = new int[redCoeff];
        initShades(redShades, redCoeff);

        greenShades = new int[greenCoeff];
        initShades(greenShades, greenCoeff);

        blueShades = new int[blueCoeff];
        initShades(blueShades, blueCoeff);

        System.out.println(Arrays.toString(redShades) + "\n" + Arrays.toString(greenShades) + "\n" + Arrays.toString(blueShades));
    }

    private void initShades(int[] palette, int number) {
        int step = (256 / (number-1));
        int shade = 0;
        for (int i = 0; i < number; i++) {
            palette[i] = Math.min(shade, 255);
            shade += step;
        }
    }

    private int findClosest(int[] shades, int color) {
        int indx = 0;
        int min = 256;

        for (int i = 0; i < shades.length; i++) {
            if(Math.abs(shades[i] - color) < min) {
                min = Math.abs(shades[i] - color);
                indx = i;
            }
        }

//        int step = (256 / (shades.length - 1));
//        int indx = color / step;
//        int res;
//
//        if (indx + 1 < shades.length && (color - shades[indx] > shades[indx+1] - color)) {
//            res = indx + 1;
//        } else res = indx;

        return shades[indx];
    }

    private int getMatrixSize() {
        int sizeR = 255 / (redCoeff);
        int sizeG = 255 / (greenCoeff);
        int sizeB = 255 / (blueCoeff);
        int maxSize = Math.max(Math.max(sizeG, sizeR), sizeB);

        int[] sizes = {2, 4, 8};
        int res = 0;
        for (int i : sizes) {
            if (maxSize <= i*i) {
                res = i;
                break;
            }
        }
        res = (res == 0) ? 8 : res;
        System.out.println(res);
        return res;
    }

    private double[][] calculateMatrix(int size){
        if (size == 2) return baseMatrix;

        double[][] prevMatrix = calculateMatrix(size / 2);
        int len = prevMatrix[0].length;
        double[][] newMatrix = new double[2*len][2*len];

        for (int x = 0; x < len; x++) {
            for (int y = 0; y < len; y++) {
                newMatrix[x][y] = prevMatrix[x % len][y % len] * 4;
            }
        }
        for (int x = len; x < len*2; x++) {
            for (int y = 0; y < len; y++) {
                newMatrix[x][y] = prevMatrix[x % len][y % len] * 4 + 2;
            }
        }
        for (int x = 0; x < len; x++) {
            for (int y = len; y < len*2; y++) {
                newMatrix[x][y] = prevMatrix[x % len][y % len] * 4 + 3;
            }
        }
        for (int x = len; x < len*2; x++) {
            for (int y = len; y < len*2; y++) {
                newMatrix[x][y] = prevMatrix[x % len][y % len] * 4 + 1;
            }
        }

        return newMatrix;
    }

    private double[][] norm(double[][] matrix) {
        for (int x = 0; x < matrixSize; x++) {
            for(int y = 0; y < matrixSize; y++) {
                matrix[x][y] = (matrix[x][y]) / (matrixSize*matrixSize);
            }
        }
        return matrix;
    }

    @Override
    public BufferedImage apply(BufferedImage image) {

        BufferedImage result = new BufferedImage(image.getWidth(), image.getHeight(), image.getType());
        Graphics2D graphics2D = result.createGraphics();
        graphics2D.drawImage(image, null, 0, 0);

        matrixSize = getMatrixSize();
        double[][] bayersMatrix = calculateMatrix(matrixSize);
        bayersMatrix = norm(bayersMatrix);

        initPalettes();

        double stepR = 255.0 / (redShades.length - 1);
        double stepG = 255.0 / (greenShades.length - 1);
        double stepB = 255.0 / (blueShades.length - 1);

        int curColor, red, green, blue, newR, newG, newB;
        double tmp;
        double r, g, b;

        for (int x = 0; x < image.getWidth(); x++) {
            for (int y = 0; y < image.getHeight(); y++) {
                curColor = image.getRGB(x, y);
                red = ColorUtil.getRed(curColor);
                green = ColorUtil.getGreen(curColor);
                blue = ColorUtil.getBlue(curColor);

                tmp = bayersMatrix[y % matrixSize][x % matrixSize] - 0.5;

                r = red + stepR * tmp;
                g = green + stepG * tmp;
                b = blue + stepB * tmp;

                newR = findClosest(redShades, (int) (r));
                newG = findClosest(greenShades, (int) (g));
                newB = findClosest(blueShades, (int) (b));

                int res = ColorUtil.getColor(newR, newG, newB);
                result.setRGB(x, y, res);
            }
        }

        return result;

    }

    @Override
    public void setParams(IFilterSettings params) {

        if (params == null) return;

        DitheringRecord rec = (DitheringRecord) params;
        redCoeff = rec.red();
        greenCoeff = rec.green();
        blueCoeff = rec.blue();
    }
}
