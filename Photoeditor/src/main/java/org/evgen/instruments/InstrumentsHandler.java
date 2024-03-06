package org.evgen.instruments;

import org.evgen.instruments.filters.*;
import org.evgen.instruments.filters.contouring.Contouring;
import org.evgen.instruments.filters.dithering.FloydSteinbergDithering;
import org.evgen.instruments.filters.dithering.Ordered;
import org.evgen.instruments.interfaces.IFilter;
import org.evgen.instruments.interfaces.IInstrument;
import org.evgen.instruments.filters.gamma.Gamma;
import org.evgen.instruments.filters.gaussian.Gaussian;
import org.evgen.instruments.rotate.Rotate;

import java.util.HashMap;

public class InstrumentsHandler {

    private final HashMap<String, IFilter> filterMap = new HashMap<>();
    private final HashMap<String, IInstrument> instrumentMap = new HashMap<>();

    public InstrumentsHandler() {

        instrumentMap.put("FitToScreen", new FitImage());
        instrumentMap.put("Zoom", new ZoomInstrument());
        instrumentMap.put("Rotate", new Rotate());

        filterMap.put("WhiteBlack", new WhiteBlackFilter());
        filterMap.put("Inversion", new Inversion());
        filterMap.put("Gaussian", new Gaussian());
        filterMap.put("Sharpening", new Sharpening());
        filterMap.put("Embossing", new Embossing());
        filterMap.put("Gamma", new Gamma());
        filterMap.put("FloydSteinberg", new FloydSteinbergDithering());
        filterMap.put("Ordered", new Ordered());
        filterMap.put("Contouring", new Contouring());
        filterMap.put("Dilation", new Dilation());
        filterMap.put("Watercolor", new Watercolor());
    }

    public IFilter getFilterByName(String name) throws IllegalArgumentException{
        if (!filterMap.containsKey(name)) {
            throw new IllegalArgumentException("No such filter!");
        }
        return filterMap.get(name);
    }

    public IInstrument getInstrumentByName(String name) throws IllegalArgumentException{
        if (!instrumentMap.containsKey(name)) {
            throw new IllegalArgumentException("No such instrument!");
        }
        return instrumentMap.get(name);
    }

}
