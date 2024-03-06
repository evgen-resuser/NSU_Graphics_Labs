package org.evgen.instruments;


import org.evgen.instruments.interfaces.IInstrument;

import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;

public class FitImage implements IInstrument {

    private int type;
    private int w;
    private int h;

    public void setSettings(int type, int w, int h) {
        this.h = h;
        this.type = type;
        this.w = w;
    }

    @Override
    public BufferedImage apply(BufferedImage image) { //todo fix fit after filter applying
        if (image == null) return null;

        int originalWidth = image.getWidth();
        int originalHeight = image.getHeight();
        double scaleX = (double)w / originalWidth;
        double scaleY = (double)h / originalHeight;
        double scale = Math.min(scaleX, scaleY);

        AffineTransform at = AffineTransform.getScaleInstance(scale, scale);
        AffineTransformOp op = new AffineTransformOp(at, type);

        return op.filter(image, null);
    }
}
