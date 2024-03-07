package org.evgen.instruments.filters;

import org.evgen.instruments.interfaces.IFilter;
import org.evgen.instruments.interfaces.IFilterSettings;
import org.evgen.util.ColorUtil;

import java.awt.image.BufferedImage;
import java.util.Arrays;

public class Watercolor implements IFilter {

    private static final int MEDIAN_SIZE = 25;
    private static final int MEDIAN_INDEX = 13;

    private int median(BufferedImage image, int x, int y) {
        int[] redArr = new int[MEDIAN_SIZE];
        int[] greenArr = new int[MEDIAN_SIZE];
        int[] blueArr = new int[MEDIAN_SIZE];

        int indx = 0;

        int color;
        int w = image.getWidth();
        int h = image.getHeight();

        for (int i = -2; i != 2; i++){
            for (int j = -2; j != 2; j++){
                int xx = x + i;
                int yy = y + j;

                if (xx < 0 || xx >= w || yy < 0 || yy >= h) {
                    color = image.getRGB(x, y);
                    redArr[indx] = ColorUtil.getRed(color);
                    greenArr[indx] = ColorUtil.getGreen(color);
                    blueArr[indx] = ColorUtil.getBlue(color);
                    indx++;
                    continue;
                }

                color = image.getRGB(xx, yy);
                redArr[indx] = ColorUtil.getRed(color);
                greenArr[indx] = ColorUtil.getGreen(color);
                blueArr[indx] = ColorUtil.getBlue(color);
                indx++;
            }
        }

        Arrays.sort(redArr);
        Arrays.sort(greenArr);
        Arrays.sort(blueArr);

        return ColorUtil.getColor(redArr[MEDIAN_INDEX], greenArr[MEDIAN_INDEX], blueArr[MEDIAN_INDEX]);

    }

    @Override
    public BufferedImage apply(BufferedImage image) {
        BufferedImage result = new BufferedImage(image.getWidth(), image.getHeight(), image.getType());

        for (int x = 0; x < image.getWidth(); x++) {
            for (int y = 0; y < image.getHeight(); y++) {
                result.setRGB(x, y, median(image, x, y));
            }
        }

        result = (new Sharpening()).apply(result);

        return result;
    }

    @Override
    public void setParams(IFilterSettings params) {
        //no params
    }
}
