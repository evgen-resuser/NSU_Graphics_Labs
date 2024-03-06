package org.evgen.instruments;

import org.evgen.instruments.interfaces.IInstrument;

import java.awt.*;
import java.awt.image.BufferedImage;

public class ZoomInstrument implements IInstrument {

    private int percent = 100;
    private int h;
    private int w;

    public void setParams(int percent, int w, int h) {
        this.percent = percent;
        this.w = w;
        this.h = h;
    }

    int newWidth;
    int newHeight;


    @Override
    public BufferedImage apply(BufferedImage image) {

        if (image == null) return null;

        newWidth = (w * percent) / 100;
        newHeight = (h * percent) / 100;

        BufferedImage newImage = new BufferedImage(newWidth, newHeight, image.getType());
        Graphics2D g = newImage.createGraphics();
        g.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
        g.drawImage(image, 0, 0, newWidth, newHeight,
                0, 0, image.getWidth(), image.getHeight(), null);

        return newImage;
    }

    public int getNewW() {
        return newWidth;
    }

    public int getNewH() {
        return newHeight;
    }
}
