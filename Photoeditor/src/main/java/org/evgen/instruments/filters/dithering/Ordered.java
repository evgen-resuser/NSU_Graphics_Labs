package org.evgen.instruments.filters.dithering;

import org.evgen.instruments.interfaces.IFilter;
import org.evgen.instruments.interfaces.IFilterSettings;
import org.evgen.util.ColorUtil;

import java.awt.*;
import java.awt.image.BufferedImage;

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
    }

    private void initShades(int[] palette, int number) {
        int step = (256 / (number - 1));
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

        return shades[indx];
    }

    private int getMatrixSize() {
        int sizeR = 256 / redCoeff;
        int sizeG = 256 / greenCoeff;
        int sizeB = 256 / blueCoeff;
        int maxSize = Math.max(Math.max(sizeG, sizeR), sizeB);

        int[] sizes = {2, 4, 8, 16};
        int res = 0;
        for (int i : sizes) {
            if (maxSize <= i*i) {
                res = i;
                break;
            }
        }
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
                newMatrix[x][y] = prevMatrix[x % len][y % len] * 4 + 3;
            }
        }
        for (int x = 0; x < len; x++) {
            for (int y = len; y < len*2; y++) {
                newMatrix[x][y] = prevMatrix[x % len][y % len] * 4 + 2;
            }
        }
        for (int x = len; x < len*2; x++) {
            for (int y = len; y < len*2; y++) {
                newMatrix[x][y] = prevMatrix[x % len][y % len] * 4 + 1;
            }
        }

        if (size == matrixSize) {
            for (int x = 0; x < matrixSize; x++) {
                for(int y = 0; y < matrixSize; y++) {
                    newMatrix[x][y] = (newMatrix[x][y] + 1) / (matrixSize*matrixSize);
                }
            }
        }

        return newMatrix;
    }

    @Override
    public BufferedImage apply(BufferedImage image) {

        BufferedImage result = new BufferedImage(image.getWidth(), image.getHeight(), image.getType());
        Graphics2D graphics2D = result.createGraphics();
        graphics2D.drawImage(image, null, 0, 0);

        matrixSize = getMatrixSize();
        double[][] bayersMatrix = calculateMatrix(matrixSize);

        initPalettes();

        double stepR = 256.0 / redShades.length;
        double stepG = 256.0 / greenShades.length;
        double stepB = 256.0 / blueShades.length;

        int curColor, red, green, blue, newR, newG, newB;
        double tmp;

        for (int x = 0; x < image.getWidth(); x++) {
            for (int y = 0; y < image.getHeight(); y++) {
                curColor = image.getRGB(x, y);
                red = ColorUtil.getRed(curColor);
                green = ColorUtil.getGreen(curColor);
                blue = ColorUtil.getBlue(curColor);

                tmp = bayersMatrix[x % matrixSize][y % matrixSize];

                newR = findClosest(redShades, (int) (red + stepR * tmp));
                newG = findClosest(greenShades, (int) (green + stepG * tmp));
                newB = findClosest(blueShades, (int) (blue + stepB * tmp));

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
