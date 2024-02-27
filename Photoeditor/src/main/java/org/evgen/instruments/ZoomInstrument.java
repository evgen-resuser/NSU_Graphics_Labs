package org.evgen.instruments;

import java.awt.*;
import java.awt.image.BufferedImage;

public class ZoomInstrument implements IInstrument{

    private int percent = 100;
    private int h;
    private int w;

    public void setParams(int percent, int w, int h) {
        this.percent = percent;
        this.w = w;
        this.h = h;
    }

    @Override
    public BufferedImage apply(BufferedImage image) {

        int newWidth = (w * percent) / 100;
        int newHeight = (h * percent) / 100;

        BufferedImage newImage = new BufferedImage(newWidth, newHeight, image.getType());
        Graphics2D g = newImage.createGraphics();
        g.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
        g.drawImage(image, 0, 0, newWidth, newHeight,
                0, 0, image.getWidth(), image.getHeight(), null);

        return newImage;
    }
}
