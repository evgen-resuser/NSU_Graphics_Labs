package org.evgen.instruments.filters;

import org.evgen.instruments.interfaces.IFilter;
import org.evgen.instruments.interfaces.IFilterSettings;
import org.evgen.util.ColorUtil;

import java.awt.*;
import java.awt.image.BufferedImage;

public class WhiteBlackFilter implements IFilter {

    private static final float RED_PORTION = 0.299f;
    private static final float GREEN_PORTION = 0.587f;
    private static final float BLUE_PORTION = 0.114f;

    @Override
    public BufferedImage apply(BufferedImage image) {
        BufferedImage res = new BufferedImage(image.getWidth(), image.getHeight(), BufferedImage.TYPE_INT_RGB);

        for (int x = 0; x < image.getWidth(); x++) {
            for (int y = 0; y < image.getHeight(); y++) {
                int rgb = image.getRGB(x, y);

                int gray = (int)(ColorUtil.getRed(rgb) * RED_PORTION + ColorUtil.getGreen(rgb) * GREEN_PORTION
                        + ColorUtil.getBlue(rgb) * BLUE_PORTION);

                res.setRGB(x, y, ColorUtil.getColor(gray, gray, gray));
            }
        }

        return res;
    }

    @Override
    public void setParams(IFilterSettings params) {
        //no params
    }
}
