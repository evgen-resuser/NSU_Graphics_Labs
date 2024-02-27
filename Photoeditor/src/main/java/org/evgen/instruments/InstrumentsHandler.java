package org.evgen.instruments;

import org.evgen.instruments.filters.IFilter;

import java.util.HashMap;

public class InstrumentsHandler {

    private HashMap<String, IFilter> filterMap = new HashMap<>();
    private HashMap<String, IInstrument> instrumentMap = new HashMap<>();

    public InstrumentsHandler() {

        instrumentMap.put("FitToScreen", new FitImage());
        instrumentMap.put("Zoom", new ZoomInstrument());

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
