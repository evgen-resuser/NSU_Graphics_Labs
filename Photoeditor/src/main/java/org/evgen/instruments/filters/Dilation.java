package org.evgen.instruments.filters;

import org.evgen.instruments.interfaces.IFilter;
import org.evgen.instruments.interfaces.IFilterSettings;
import org.evgen.util.ColorUtil;

import java.awt.image.BufferedImage;

public class Dilation implements IFilter {

        private final int[][] kernel = {
            {0, 1, 0},
            {1, 1, 1},
            {0, 1, 0}
    };

    @Override
    public BufferedImage apply(BufferedImage image) {
        BufferedImage result = new BufferedImage(image.getWidth(), image.getHeight(), image.getType());

        for (int y = 0; y < image.getHeight(); y++) {
            for (int x = 0; x < image.getWidth(); x++) {
                int maxR = 0, maxG = 0, maxB = 0;
                for (int ky = -1; ky <= 1; ky++) {
                    for (int kx = -1; kx <= 1; kx++) {

                        int xx = x + kx;
                        int yy = y + ky;

                        int pixel;

                        if (xx <= 0 || xx >= image.getWidth() || yy <= 0 || yy >= image.getHeight())
                            pixel = image.getRGB(x, y);
                        else
                            pixel = image.getRGB(xx, yy);

                        if (kernel[ky + 1][kx + 1] == 1) {
                            maxR = Math.max(ColorUtil.getRed(pixel), maxR);
                            maxG = Math.max(ColorUtil.getGreen(pixel), maxG);
                            maxB = Math.max(ColorUtil.getBlue(pixel), maxB);
                        }
                    }
                }
                result.setRGB(x, y, ColorUtil.getColor(maxR, maxG, maxB));
            }
        }

        return result;
    }

    @Override
    public void setParams(IFilterSettings params) {
        //no params
    }
}
