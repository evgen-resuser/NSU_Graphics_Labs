package org.evgen.tools.line;

import java.awt.*;
import java.awt.image.BufferedImage;

public class LineDrawer {

    /**
     * Draws a line on the given BufferedImage with the specified thickness and color, between the given start and end points.
     *
     * @param  image     the BufferedImage on which to draw the line
     * @param  thickness the thickness of the line
     * @param  color     the color of the line
     * @param  start     the starting point of the line
     * @param  end       the ending point of the line
     */
    public static void drawLine(BufferedImage image, int thickness, Color color, Point start, Point end) {
        drawLine(image, thickness, color, start.x, start.y, end.x, end.y);
    }

    /**
     * Draws a line on the given BufferedImage with the specified thickness and color, between the given start and end points
     * by its coordinates.
     *
     * @param  image      the image on which the line will be drawn
     * @param  thickness  the thickness of the line
     * @param  color      the color of the line
     * @param  x1         the x-coordinate of the start point
     * @param  y1         the y-coordinate of the start point
     * @param  x2         the x-coordinate of the end point
     * @param  y2         the y-coordinate of the end point
     */
    public static void drawLine(BufferedImage image, int thickness, Color color, int x1, int y1, int x2, int y2) {
        Graphics2D g2d = (Graphics2D) image.getGraphics();

        if (thickness > 1) {
            g2d.setColor(color);
            g2d.setStroke(new BasicStroke(thickness));
            g2d.drawLine(x1, y1, x2, y2);
        } else {

            // setting start point but other points will be calculated by Brezenhem algorithm
            drawPixel(x1, y1, color, image);

            int dx = Math.abs(x2 - x1);
            int dy = Math.abs(y1 - y2);

            int err = -dx;

            if(dx >= dy)
            {
                if((x2 - x1) >= 0 && (y2 - y1) >= 0)
                {
                    int x = Math.min(x1, x2);
                    int y = Math.min(y1, y2);

                    for(int i = 0; i < dx; i++)
                    {
                        x++;
                        err += 2*dy;
                        if(err > 0)
                        {
                            y++;
                            err -= 2*dx;
                        }
                        drawPixel(x, y, color, image);
                    }
                }
                else if((x2 - x1) >= 0 && (y2 - y1) <= 0)
                {
                    int x = Math.min(x1, x2);
                    int y = Math.max(y1, y2);

                    for(int i = 0; i < dx; i++)
                    {
                        x++;
                        err += 2*dy;
                        if(err > 0)
                        {
                            y--;
                            err -= 2*dx;
                        }
                        drawPixel(x, y, color, image);
                    }
                }
                else if ((x2 - x1) <= 0 && (y2 - y1) >= 0)
                {
                    int x = Math.max(x1, x2);
                    int y = Math.min(y1, y2);

                    for(int i = 0; i < dx; i++)
                    {
                        x--;
                        err += 2*dy;
                        if(err > 0)
                        {
                            y++;
                            err -= 2*dx;
                        }
                        drawPixel(x, y, color, image);
                    }
                }
                else if ((x2 - x1) <= 0 && (y2 - y1) <= 0)
                {
                    int x = Math.max(x1, x2);
                    int y = Math.max(y1, y2);

                    for(int i = 0; i < dx; i++)
                    {
                        x--;
                        err += 2*dy;
                        if(err > 0)
                        {
                            y--;
                            err -= 2*dx;
                        }
                        drawPixel(x, y, color, image);
                    }
                }
            }
            else
            {
                if((x2 - x1) >= 0 && (y2 - y1) >= 0)
                {
                    int x = Math.min(x1, x2);
                    int y = Math.min(y1, y2);

                    for(int i = 0; i < dy; i++)
                    {
                        y++;
                        err += 2*dx;
                        if(err > 0)
                        {
                            x++;
                            err -= 2*dy;
                        }
                        drawPixel(x, y, color, image);
                    }
                }
                else if((x2 - x1) >= 0 && (y2 - y1) <= 0)
                {
                    int x = Math.min(x1, x2);
                    int y = Math.max(y1, y2);

                    for(int i = 0; i < dy; i++)
                    {
                        y--;
                        err += 2*dx;
                        if(err > 0)
                        {
                            x++;
                            err -= 2*dy;
                        }
                        drawPixel(x, y, color, image);
                    }
                }
                else if ((x2 - x1) <= 0 && (y2 - y1) >= 0)
                {
                    int x = Math.max(x1, x2);
                    int y = Math.min(y1, y2);

                    for(int i = 0; i < dy; i++)
                    {
                        y++;
                        err += 2*dx;
                        if(err > 0)
                        {
                            x--;
                            err -= 2*dy;
                        }
                        drawPixel(x, y, color, image);
                    }
                }
                else if ((x2 - x1) <= 0 && (y2 - y1) <= 0)
                {
                    int x = Math.max(x1, x2);
                    int y = Math.max(y1, y2);

                    for(int i = 0; i < dy; i++)
                    {
                        y--;
                        err += 2*dx;
                        if(err > 0)
                        {
                            x--;
                            err -= 2*dy;
                        }
                        drawPixel(x, y, color, image);
                    }
                }
            }
        }
    }

    private static void drawPixel(int x, int y, Color color, BufferedImage image) {
        if (x < image.getWidth() && x >= 0 && y >= 0 && y < image.getHeight())
            image.setRGB(x, y, color.getRGB());
    }
}
