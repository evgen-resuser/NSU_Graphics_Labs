package org.evgen.instruments.filters.dithering;

import org.evgen.instruments.interfaces.IFilterSettings;

public record DitheringRecord(int red, int green, int blue) implements IFilterSettings {
}
