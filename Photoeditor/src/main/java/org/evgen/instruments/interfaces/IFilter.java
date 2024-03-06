package org.evgen.instruments.interfaces;

import org.evgen.instruments.interfaces.IFilterSettings;

import java.awt.image.BufferedImage;

public interface IFilter {
    BufferedImage apply(BufferedImage image);
    void setParams(IFilterSettings params);
}
