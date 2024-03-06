package org.evgen.instruments.filters.gamma;

import org.evgen.instruments.interfaces.IFilter;
import org.evgen.instruments.interfaces.IFilterSettings;
import org.evgen.util.ColorUtil;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Gamma implements IFilter {

    private double gammaCoeff = 1.0;

    @Override
    public BufferedImage apply(BufferedImage image) {
        BufferedImage result = new BufferedImage(image.getWidth(), image.getHeight(), image.getType());

        int rgb;
        int red, green, blue;
        double tmp;

        for (int x = 0; x < image.getWidth(); x++) {
            for (int y = 0; y < image.getHeight(); y++) {
                rgb = image.getRGB(x, y);


                tmp = ColorUtil.getRed(rgb);
                red = (int)(Math.pow(tmp / 255, 1/gammaCoeff) * 255);

                tmp = ColorUtil.getGreen(rgb);
                green = (int) (Math.pow(tmp / 255, 1/gammaCoeff) * 255);

                tmp = ColorUtil.getBlue(rgb);
                blue = (int)( Math.pow(tmp / 255, 1/gammaCoeff) * 255);

                result.setRGB(x, y, ColorUtil.getColor(red, green, blue));
            }
        }

        return result;
    }

    @Override
    public void setParams(IFilterSettings params) {
        if (params == null) return;
        gammaCoeff = ((GammaRecord)params).gamma();
    }
}
