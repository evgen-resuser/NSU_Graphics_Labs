package org.evgen.instruments.filters;

import java.awt.image.BufferedImage;

public interface IFilter {
    BufferedImage apply(BufferedImage image);
}
