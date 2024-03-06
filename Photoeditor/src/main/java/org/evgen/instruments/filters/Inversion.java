package org.evgen.instruments.filters;

import org.evgen.instruments.interfaces.IFilter;
import org.evgen.instruments.interfaces.IFilterSettings;
import org.evgen.util.ColorUtil;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Inversion implements IFilter {

    private static final int MAX_INTENSITY = 255;

    @Override
    public BufferedImage apply(BufferedImage image) {
        BufferedImage res = new BufferedImage(image.getWidth(), image.getHeight(), BufferedImage.TYPE_INT_ARGB);

        for (int x = 0; x < image.getWidth(); x++) {
            for (int y = 0; y < image.getHeight(); y++) {
                int rgb = image.getRGB(x, y);

                int red = MAX_INTENSITY - ColorUtil.getRed(rgb);
                int green = MAX_INTENSITY - ColorUtil.getGreen(rgb);
                int blue = MAX_INTENSITY - ColorUtil.getBlue(rgb);

                res.setRGB(x, y, ColorUtil.getColor(red, green, blue));
            }
        }

        return res;
    }

    @Override
    public void setParams(IFilterSettings params) {
        //no params
    }
}
