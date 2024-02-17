package org.evgen.tools.polygon;

import org.evgen.interfaces.IPolygon;

public record PolygonSettings(int radius, int rotateAngle, int count, int thickness) implements IPolygon {
}
