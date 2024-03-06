package org.evgen.instruments.filters.contouring;

import org.evgen.instruments.interfaces.IFilterSettings;

public record ContouringRecord(int mode, int param) implements IFilterSettings {
}
