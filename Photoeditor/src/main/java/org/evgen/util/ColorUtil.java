package org.evgen.util;

public class ColorUtil {

    private ColorUtil(){}

    public static int getRed(int rgb) {
        return ((rgb >> 16) & 0xFF);
    }

    public static int getGreen(int rgb) {
        return ((rgb >> 8) & 0xFF);
    }

    public static int getBlue(int rgb) {
        return (rgb & 0xFF);
    }

    public static int getColor(int red, int green, int blue) {
        return (255 << 24 | red << 16 | green << 8 | blue);
    }

}
