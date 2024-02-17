package org.evgen.tools.star;

import org.evgen.interfaces.IPolygon;

public record StarSettings(int rotateAngle, int count, int thickness, int outerRadi, int innerRadi) implements IPolygon {
}
