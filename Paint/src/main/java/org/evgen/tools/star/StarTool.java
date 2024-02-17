package org.evgen.tools.star;

import org.evgen.tools.line.LineDrawer;

import java.awt.*;
import java.awt.image.BufferedImage;

import static java.lang.Math.*;

public class StarTool {

    private StarTool(){}

    public static void draw(BufferedImage image, Point point, Color color, StarSettings settings){

        int[] xCoords = new int[settings.count()*2];
        int[] yCoords = new int[settings.count()*2];

        for(int i = 0; i < settings.count()*2; i++) {
            if(i % 2 == 0) {
                xCoords[i] = (int) (point.x + settings.outerRadi() * cos(degToRad(settings.rotateAngle())
                        + (i*2*Math.PI)/(2*settings.count())));
                yCoords[i] = (int) (point.y + settings.outerRadi() * sin(degToRad(settings.rotateAngle())
                        + (i*2*Math.PI)/(2*settings.count())));
            }
            else {
                xCoords[i] = (int) (point.x + settings.innerRadi()/2.0 * cos(degToRad(settings.rotateAngle())
                        + (i*2*Math.PI)/(2*settings.count())));
                yCoords[i] = (int) (point.y + settings.innerRadi()/2.0 * sin(degToRad(settings.rotateAngle())
                        + (i*2*Math.PI)/(2*settings.count())));
            }
        }

        Graphics g2d = image.getGraphics();
        g2d.setColor(color);

        int i;
        for (i = 1; i != settings.count() * 2; i++)
            LineDrawer.drawLine(image, settings.thickness(), color, xCoords[i-1], yCoords[i-1], xCoords[i], yCoords[i]);

        LineDrawer.drawLine(image, settings.thickness(), color, xCoords[i-1], yCoords[i-1], xCoords[0], yCoords[0]);
    }

    private static double degToRad(int deg) {
        return deg * PI / 180;
    }

}
