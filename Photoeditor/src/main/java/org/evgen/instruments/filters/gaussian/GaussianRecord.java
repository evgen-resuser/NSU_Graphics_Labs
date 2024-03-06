package org.evgen.instruments.filters.gaussian;


import org.evgen.instruments.interfaces.IFilterSettings;

public record GaussianRecord(int size, double sigma) implements IFilterSettings {
}
