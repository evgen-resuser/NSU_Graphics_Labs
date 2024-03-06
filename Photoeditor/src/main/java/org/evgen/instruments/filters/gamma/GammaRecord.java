package org.evgen.instruments.filters.gamma;


import org.evgen.instruments.interfaces.IFilterSettings;

public record GammaRecord(double gamma) implements IFilterSettings {}
