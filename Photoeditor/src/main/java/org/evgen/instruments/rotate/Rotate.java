package org.evgen.instruments.rotate;

import org.evgen.instruments.interfaces.IInstrument;

import java.awt.image.BufferedImage;

public class Rotate implements IInstrument {

    private int angle;

    @Override
    public BufferedImage apply(BufferedImage image) {
        return null;
    }

    public void setAngle(int angle) {
        this.angle = angle;
    }
}
