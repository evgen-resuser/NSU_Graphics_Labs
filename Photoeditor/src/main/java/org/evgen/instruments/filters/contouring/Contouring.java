package org.evgen.instruments.filters.contouring;

import org.evgen.instruments.interfaces.IFilter;
import org.evgen.instruments.interfaces.IFilterSettings;
import org.evgen.util.ColorUtil;

import java.awt.image.BufferedImage;
import java.util.Objects;

public class Contouring implements IFilter {

    private enum Mode {
        ROBERTS, SOBEL
    }

    private Mode mode;
    private int param;

    @Override
    public BufferedImage apply(BufferedImage image) {

        if (Objects.requireNonNull(mode) == Mode.ROBERTS) {
            return roberts(image);
        } else if (mode == Mode.SOBEL) {
            return sobel(image);
        }
        return null;
    }

    private BufferedImage sobel(BufferedImage oldImage) {

        int width = oldImage.getWidth();
        int height = oldImage.getHeight();

        BufferedImage newImage = new BufferedImage(width, height, oldImage.getType());

        // a0 b1 c2 d3 e4 f5 g6 h7 i8
        int indx;
        int xx, yy;
        int color;

        int[] pixelsR = new int[9];
        int[] pixelsG = new int[9];
        int[] pixelsB = new int[9];

        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++){

                indx = 0;

                for (int i = -1; i <= 1; i++){
                    for (int j = -1; j <= 1; j++) {

                        xx = x + i;
                        yy = y + j;

                        if (xx < 0 || xx >= width || yy < 0 || yy >= height){
                            color = oldImage.getRGB(x, y);
                        } else color = oldImage.getRGB(xx, yy);

                        pixelsR[indx] = ColorUtil.getRed(color);
                        pixelsG[indx] = ColorUtil.getGreen(color);
                        pixelsB[indx] = ColorUtil.getBlue(color);

                        indx++;
                    }
                }

                int red = sobelIteration(pixelsR);
                int green = sobelIteration(pixelsG);
                int blue = sobelIteration(pixelsB);

                if (red > param && green > param && blue > param) {
                    newImage.setRGB(x, y, ColorUtil.getColor(255, 255, 255));
                } else newImage.setRGB(x, y, ColorUtil.getColor(0, 0, 0));

            }
        }

        return newImage;
    }

    private int sobelIteration(int[] pixels){
        // a0 b1 c2 d3 e4 f5 g6 h7 i8
        //Sx = (c2 + 2f5 + i8) − (a0 + 2d3 + g6)
        //Sy = (g6 + 2h7 + i8) − (a0 + 2b1 + c2)

        int sx = (pixels[2] + 2*pixels[5] + pixels[8]) - (pixels[0] + 2*pixels[3] + pixels[6]);
        int sy = (pixels[6] + 2*pixels[7] + pixels[8]) - (pixels[0] + 2*pixels[1] + pixels[2]);

        return (int)Math.sqrt((sx*sx) + (sy*sy));
    }

    private BufferedImage roberts(BufferedImage image) {
        BufferedImage result = new BufferedImage(image.getWidth(), image.getHeight(), image.getType());

        int color1, color2, color3, color4;
        int res1, res2, res3;
        for (int x = 0; x < image.getWidth()-1; x++) {
            for (int y = 0; y < image.getHeight()-1; y++) {

                color1 = image.getRGB(x, y);
                color3 = image.getRGB(x+1, y+1);
                color2 = image.getRGB(x, y+1);
                color4 = image.getRGB(x+1, y);

                res1 = robertsIteration(ColorUtil.getRed(color1), ColorUtil.getRed(color2),
                        ColorUtil.getRed(color3), ColorUtil.getRed(color4));
                res2 = robertsIteration(ColorUtil.getGreen(color1), ColorUtil.getGreen(color2),
                        ColorUtil.getGreen(color3), ColorUtil.getGreen(color4));
                res3 = robertsIteration(ColorUtil.getBlue(color1), ColorUtil.getBlue(color2),
                        ColorUtil.getBlue(color3), ColorUtil.getBlue(color4));

                if (res1 > param && res2 > param && res3 > param) {
                    result.setRGB(x, y, ColorUtil.getColor(255, 255, 255));
                } else result.setRGB(x, y, ColorUtil.getColor(0, 0, 0));
            }
        }

        return result;
    }

    private int robertsIteration(int x1, int x2, int x3, int x4) {
        int gx = x3 - x1;
        int gy = x4 - x2;

        return (int)Math.sqrt((gx*gx) + (gy*gy));
    }

    @Override
    public void setParams(IFilterSettings params) {
        ContouringRecord rec = (ContouringRecord) params;
        param = rec.param();
        mode = rec.mode() == 0 ? Mode.ROBERTS : Mode.SOBEL;
    }
}
