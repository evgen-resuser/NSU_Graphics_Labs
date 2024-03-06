package org.evgen.instruments.filters.gaussian;


import org.evgen.instruments.interfaces.IFilter;
import org.evgen.instruments.interfaces.IFilterSettings;
import org.evgen.util.ColorUtil;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Gaussian implements IFilter {

    private int size;
    private double sigma;
    private int radius;

    private double[][] kernel;

    @Override
    public BufferedImage apply(BufferedImage image) {
        BufferedImage newImage = new BufferedImage(image.getWidth(), image.getHeight(), image.getType());
        initKernel();

        double kernelValue;
        int color;

        for (int x = 0; x < image.getWidth(); x++){
            for (int y = 0; y < image.getHeight(); y++){
                double red1 = 0.0;
                double green1 = 0.0;
                double blue1 = 0.0;

                for (int kernelX = -radius; kernelX < radius; kernelX++){
                    for (int kernelY = -radius; kernelY < radius; kernelY++){
                        int xx = x + kernelX;
                        int yy = y + kernelY;

                        if (xx <= 0 || xx >= image.getWidth() || yy <= 0 || yy >= image.getHeight())
                            color = image.getRGB(x, y);
                        else
                            color = image.getRGB(x+kernelX, y+kernelY);

                        kernelValue = kernel[kernelX+radius][kernelY+radius];

                        red1 += ColorUtil.getRed(color) * kernelValue;
                        green1 += ColorUtil.getGreen(color) * kernelValue;
                        blue1 += ColorUtil.getBlue(color) * kernelValue;
                    }
                }
                newImage.setRGB(x, y, ColorUtil.getColor((int)red1, (int)green1, (int)blue1));
            }
        }

        return newImage;
    }

    @Override
    public void setParams(IFilterSettings params) {
        if (params == null) return;
        size = ((GaussianRecord)params).size();
        sigma = ((GaussianRecord)params).sigma();
        radius = size / 2;
        System.out.println(params);
    }

    private void initKernel() {
        kernel = new double[size][size];
        double sum = 0.0;

        for (int x = -radius; x < radius; x++) {
            for (int y = -radius; y < radius; y++) {
                double expNumerator = - (x * x + y * y);
                double expDenominator = 2 * sigma * sigma;

                double e = Math.exp(expNumerator / expDenominator);
                double kernelValue = e / ( 2 * 3.14 * sigma * sigma);

                kernel[x+radius][y+radius] = kernelValue;
                sum += kernelValue;
            }
        }

        if (sum == 0) return;
        //normalize
        for(int x  = 0; x < size; x++) {
            for(int y = 0; y < size; y++) {
                kernel[x][y] /= sum;
            }
        }
    }
}
