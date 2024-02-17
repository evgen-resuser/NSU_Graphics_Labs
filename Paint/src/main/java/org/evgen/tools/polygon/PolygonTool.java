package org.evgen.tools.polygon;

import org.evgen.tools.line.LineDrawer;
import org.evgen.tools.line.LineSettings;

import java.awt.*;
import java.awt.image.BufferedImage;

import static java.lang.Math.*;

public class PolygonTool {

    private PolygonTool(){}

    /**
     * Draws a polygon on the given image at the specified point with the given color and settings.
     *
     * @param  image     the image on which the polygon will be drawn
     * @param  point     the point at which the polygon will be centered
     * @param  color     the color of the polygon
     * @param  settings  the settings for the polygon, including count, radius, rotateAngle, and thickness
     */
    public static void draw(BufferedImage image, Point point, Color color, PolygonSettings settings){

        int[] xCoords = new int[settings.count()];
        int[] yCoords = new int[settings.count()];

        for(int i = 0; i < settings.count(); i++) {
            xCoords[i] = (int) (point.x + settings.radius() * cos(degToRad(settings.rotateAngle()) +
                    (i*2*Math.PI)/(settings.count())));
            yCoords[i] = (int) (point.y + settings.radius() * sin(degToRad(settings.rotateAngle()) +
                    (i*2*Math.PI)/(settings.count())));
        }

        Graphics g2d = image.getGraphics();
        g2d.setColor(color);

        int i;
        for (i = 1; i != settings.count(); i++)
            LineDrawer.drawLine(image, settings.thickness(), color, xCoords[i-1], yCoords[i-1], xCoords[i], yCoords[i]);

        LineDrawer.drawLine(image, settings.thickness(), color, xCoords[i-1], yCoords[i-1], xCoords[0], yCoords[0]);
    }

    private static double degToRad(int deg) {
        return deg * PI / 180;
    }

}
