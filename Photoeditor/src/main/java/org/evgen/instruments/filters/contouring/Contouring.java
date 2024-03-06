package org.evgen.instruments.filters.contouring;

import org.evgen.instruments.interfaces.IFilter;
import org.evgen.instruments.interfaces.IFilterSettings;

import java.awt.image.BufferedImage;
import java.util.Objects;

public class Contouring implements IFilter {

    private enum Mode {
        ROBERTS, SOBEL
    }

    private Mode mode;
    private int param;

    @Override
    public BufferedImage apply(BufferedImage image) {

        if (Objects.requireNonNull(mode) == Mode.ROBERTS) {
            return roberts(image);
        } else if (mode == Mode.SOBEL) {
            return sobel(image);
        }
        return null;
    }

    private BufferedImage sobel(BufferedImage image) {
        BufferedImage result = new BufferedImage(image.getWidth(), image.getHeight(), image.getType());


        return result;
    }

    private BufferedImage roberts(BufferedImage image) {
        BufferedImage result = new BufferedImage(image.getWidth(), image.getHeight(), image.getType());


        return result;
    }

    @Override
    public void setParams(IFilterSettings params) {
        ContouringRecord rec = (ContouringRecord) params;
        param = rec.param();
        mode = rec.param() == 0 ? Mode.ROBERTS : Mode.SOBEL;
    }
}
