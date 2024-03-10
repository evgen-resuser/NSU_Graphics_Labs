package org.evgen.instruments;

import org.evgen.instruments.filters.*;
import org.evgen.instruments.filters.contouring.Contouring;
import org.evgen.instruments.filters.dithering.FloydSteinbergDithering;
import org.evgen.instruments.filters.dithering.Ordered;
import org.evgen.instruments.interfaces.IFilter;
import org.evgen.instruments.filters.gamma.Gamma;
import org.evgen.instruments.filters.gaussian.Gaussian;

import javax.swing.*;
import java.util.HashMap;

public class InstrumentsHandler {

    private final HashMap<String, IFilter> filterMap = new HashMap<>();

    public InstrumentsHandler() {
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
            JOptionPane.showMessageDialog(null, "Error", "No such filter", JOptionPane.ERROR_MESSAGE);
            throw new IllegalArgumentException("No such filter!");
        }
        return filterMap.get(name);
    }
}
