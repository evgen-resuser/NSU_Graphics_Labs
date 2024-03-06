package org.evgen.instruments.filters;

import org.evgen.instruments.interfaces.IFilter;
import org.evgen.instruments.interfaces.IFilterSettings;
import org.evgen.util.ColorUtil;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Sharpening implements IFilter {

    private static final int[][] KERNEL =
                    {{0, -1, 0},
                    {-1, 5, -1},
                    {0, -1, 0}};

    @Override
    public BufferedImage apply(BufferedImage image) {
        BufferedImage newImage = new BufferedImage(image.getWidth(), image.getHeight(), image.getType());

        for (int x = 0; x < image.getWidth(); x++) {
            for (int y = 0; y < image.getHeight(); y++) {
                int red1 = 0;
                int green1 = 0;
                int blue1 = 0;

                for(int kernelX = -1; kernelX <= 1; kernelX++) {
                    for(int kernelY = -1; kernelY <= 1; kernelY++) {
                        int curColor;

                        if(x+kernelX > 0 && y+kernelY > 0 && x+kernelX < image.getWidth() && y+kernelY < image.getHeight()) {
                            curColor = image.getRGB(x + kernelX, y + kernelY);
                        } else {
                            curColor = image.getRGB(x, y);
                        }

                        red1 += ColorUtil.getRed(curColor) * KERNEL[kernelX + 1][kernelY + 1];
                        green1 += ColorUtil.getGreen(curColor) * KERNEL[kernelX + 1][kernelY + 1];
                        blue1 += ColorUtil.getBlue(curColor) * KERNEL[kernelX + 1][kernelY + 1];
                    }
                }

                red1 = Math.max(Math.min(red1, 255), 0);
                green1 = Math.max(Math.min(green1, 255), 0);
                blue1 = Math.max(Math.min(blue1, 255), 0);

                newImage.setRGB(x, y, ColorUtil.getColor(red1, green1, blue1));
            }
        }
        return newImage;
    }

    @Override
    public void setParams(IFilterSettings params) {
        //no params
    }
}
