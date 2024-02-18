package org.evgen.tools.line;

import java.awt.*;
import java.awt.image.BufferedImage;

public class LineDrawer {

    /**
     * Draws a line using Bresenham's line algorithm on the given image between the specified start and end points.
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
     * Draws a line using Bresenham's line algorithm on the given image between the specified start and end points.
     *
     * @param  x1     the x-coordinate of the start point
     * @param  y1     the y-coordinate of the start point
     * @param  x2     the x-coordinate of the end point
     * @param  y2     the y-coordinate of the end point
     * @param  color  the color of the line
     * @param  image  the image on which the line will be drawn
     */
    public static void drawLine(BufferedImage image, int thickness, Color color, int x1, int y1, int x2, int y2) {
        if (thickness > 1) {
            Graphics2D g2d = (Graphics2D) image.getGraphics();
            g2d.setColor(color);
            g2d.setStroke(new BasicStroke(thickness));
            g2d.drawLine(x1, y1, x2, y2);
        } else{
            int dx = Math.abs(x2 - x1);  // Calculate the change in x-coordinate
            int dy = Math.abs(y1 - y2);  // Calculate the change in y-coordinate
            int err = -dx;  // Initialize the error value
            int x = x1;  // Set the initial x-coordinate
            int y = y1;  // Set the initial y-coordinate
            int sx = x1 < x2 ? 1 : -1;  // Set the step direction for x-coordinate
            int sy = y1 < y2 ? 1 : -1;  // Set the step direction for y-coordinate
            if (dx >= dy) {
                // If the change in x-coordinate is greater than or equal to the change in y-coordinate
                for (int i = 0; i < dx; i++) {
                    x += sx;
                    err += 2 * dy;  // Update the error value
                    if (err > 0) {
                        y += sy;  // Increment y-coordinate by the step direction for y
                        err -= 2 * dx;
                    }
                    drawPixel(x, y, color, image);  // Draw a pixel at the current coordinates
                }
            } else {
                // If the change in y-coordinate is greater than the change in x-coordinate
                for (int i = 0; i < dy; i++) {
                    y += sy;  // Increment y-coordinate by the step direction for y
                    err += 2 * dx;  // Update the error value
                    if (err > 0) {
                        x += sx;  // Increment x-coordinate by the step direction for x
                        err -= 2 * dy;  // Update the error value
                    }
                    drawPixel(x, y, color, image);  // Draw a pixel at the current coordinates
                }
            }
        }
    }


        private static void drawPixel(int x, int y, Color color, BufferedImage image) {
        if (x < image.getWidth() && x >= 0 && y >= 0 && y < image.getHeight())
            image.setRGB(x, y, color.getRGB());
    }
}
