package org.evgen.instruments.filters.dithering;

import org.evgen.instruments.interfaces.IFilter;
import org.evgen.instruments.interfaces.IFilterSettings;
import org.evgen.util.ColorUtil;

import java.awt.*;
import java.awt.image.BufferedImage;

public class FloydSteinbergDithering implements IFilter {

    private int redCoeff;
    private int blueCoeff;
    private int greenCoeff;

    private int[] redShades;
    private int[] greenShades;
    private int[] blueShades;

    @Override
    public BufferedImage apply(BufferedImage image) {
        result = new BufferedImage(image.getWidth(), image.getHeight(), image.getType());
        Graphics2D g2d = result.createGraphics();
        g2d.drawImage(image, null, 0, 0);

        int width = image.getWidth();
        int height = image.getHeight();
        initPalettes();

        for (int x = 0; x < width-1; x++){
            for (int y = 0; y < height - 1; y++) {
                int color = result.getRGB(x, y);

                int red = ColorUtil.getRed(color);
                int green = ColorUtil.getGreen(color);
                int blue = ColorUtil.getBlue(color);

                int newR = findClosest(redShades, red);
                int newG = findClosest(greenShades, green);
                int newB = findClosest(blueShades, blue);

                int newColor = ColorUtil.getColor(newR, newG, newB);
                result.setRGB(x, y, newColor);

                int errR = red - newR;
                int errG = green - newG;
                int errB = blue - newB;

                if(x < width - 1) {
                    proceedNeighbour(x+1, y, errR, errG, errB, 7);
                }
                if(x > 0 && y < height - 1) {
                    proceedNeighbour( x-1, y+1, errR, errG, errB, 3);
                }
                if(y < height - 1) {
                    proceedNeighbour( x, y+1, errR, errG, errB, 5);
                }
                if(x < width - 1 && y < height - 1) {
                    proceedNeighbour( x+1, y+1, errR, errG, errB, 1);
                }

            }
        }

        return result;

    }

    private BufferedImage result;
    private void proceedNeighbour(int x, int y, int errR, int errG, int errB, int coeff) {
        int color;
        int neighbour = result.getRGB(x, y);
        int neighbourR = ColorUtil.getRed(neighbour);
        int neighbourG = ColorUtil.getGreen(neighbour);
        int neighbourB = ColorUtil.getBlue(neighbour);

        neighbourR = (int) (neighbourR + errR * coeff/16.0);
        neighbourG = (int) (neighbourG + errG * coeff/16.0);
        neighbourB = (int) (neighbourB + errB * coeff/16.0);

        color = getColor(neighbourR, neighbourG, neighbourB);

        result.setRGB(x, y, color);
    }

    private int getColor(int neighbourR, int neighbourG, int neighbourB) {
        neighbourR = Math.max(Math.min(neighbourR, 255), 0);
        neighbourG = Math.max(Math.min(neighbourG, 255), 0);
        neighbourB = Math.max(Math.min(neighbourB, 255), 0);

        return ColorUtil.getColor(neighbourR, neighbourG, neighbourB);
    }

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


    @Override
    public void setParams(IFilterSettings params) {

        if (params == null) return;

        DitheringRecord rec = (DitheringRecord) params;
        redCoeff = rec.red();
        greenCoeff = rec.green();
        blueCoeff = rec.blue();
    }
}
