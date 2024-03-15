package org.evgen.instruments;

import org.evgen.instruments.interfaces.IInstrument;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Rotate implements IInstrument {

    private int angle;

    @Override
    public BufferedImage apply(BufferedImage image) {

        int w = image.getWidth();
        int h = image.getHeight();

        double radians = angle * (Math.PI / 180);

        double sin = Math.sin(radians);
        double cos = Math.cos(radians);


        int newH = (int) (image.getWidth()*Math.abs(sin) + image.getHeight()*Math.abs(cos));
        int newW = (int) (image.getWidth()*Math.abs(cos) + image.getHeight()*Math.abs(sin));

        BufferedImage result = new BufferedImage(newW, newH, image.getType());
        Graphics2D graphics2D = result.createGraphics();
        graphics2D.fillRect(0, 0, newW, newH);

//        for (int x = 0; x < w; x++) {
//            for (int y = 0; y < h; y++){
//                pos1 = (int) (x * cos + y * (-sin));
//                pos2 = (int)(x * sin + y * cos);
//                if (pos1 >= w || pos1 < 0 || pos2 >= h || pos2 < 0) continue;
//                //System.out.println(x + " " + y + " -> " + pos1 + " " + pos2);
//                result.setRGB(pos1, pos2, image.getRGB(x, y));
//            }
//        }

        for(int x = 0; x < newW; x++)
        {
            for(int y = 0; y < newH; y++)
            {
                int newX = (int) ((x - newW/2)*cos - (y - newH/2)*sin) + image.getWidth() / 2;
                int newY = (int) ((x - newW/2)*sin + (y - newH/2)*cos) + image.getHeight() / 2;

                int color = 0;
                if(newX > 0 && newY > 0 && newX < image.getWidth() && newY < image.getHeight())
                    color = image.getRGB(newX, newY);
                else
                    color = -1;

                result.setRGB(x, y, color);
            }
        }

        return result;
    }

    public void setAngle(int angle) {
        this.angle = angle;
    }
}
