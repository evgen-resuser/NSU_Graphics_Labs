package org.evgen.tools.fill;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Stack;

public class FillTool {

    private final Stack<Span> stack = new Stack<>();

    private BufferedImage image;
    private int oldColor;
    private int upperBorder;
    private int bottomBorder;
    private Graphics graphics;

    /**
     * Fill the specified BufferedImage with a new color starting from the given seed point.
     *
     * @param  image    the BufferedImage to be filled
     * @param  seed     the starting point for the fill operation
     * @param  newColor the new color to be applied to the image
     */
    public void fill(BufferedImage image, Point seed, Color newColor) {

        this.image = image;
        int newColor1 = newColor.getRGB();
        this.oldColor = image.getRGB(seed.x, seed.y);

        if (newColor1 == oldColor) return;

        this.graphics = image.getGraphics();
        graphics.setColor(newColor);
        addNewSpan(seed);

        while(!stack.isEmpty()) {
            Span cur = stack.pop();
            graphics.drawLine(cur.intGetX1(), cur.intGetY1(), cur.intGetX2(), cur.intGetY2());

            for (int x = cur.intGetX1(); x < cur.intGetX2(); x++) {
                findNewSpan(x, cur.intGetY1());
            }

            bottomBorder = 0;
            upperBorder = 0;
        }

    }

    private void addNewSpan(Point seed){
        Point spanStart = new Point(seed);
        while(spanStart.x > 0 && image.getRGB(spanStart.x, spanStart.y) == oldColor) {
            spanStart.x--;
        }

        Point spanEnd = new Point(seed);
        while(spanEnd.x < image.getWidth() && image.getRGB(spanEnd.x, spanEnd.y) == oldColor) {
            spanEnd.x++;
        }

        stack.push(new Span(spanStart, spanEnd));
    }


    private void findNewSpan(int x, int y) {
        if((y - 1) >= 0 && (y + 1) < image.getHeight()) {
            if(x > upperBorder && (image.getRGB(x, y + 1) == oldColor)) {
                    addNewSpan(new Point(x, y + 1));
                    upperBorder = stack.peek().intGetX2();

            }
            if(x > bottomBorder && (image.getRGB(x, y - 1) == oldColor)) {
                    addNewSpan(new Point(x, y - 1));
                    bottomBorder = stack.peek().intGetX2();

            }
        }
    }
}
