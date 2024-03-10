package org.evgen.view;

import javax.swing.ImageIcon;
import java.util.Objects;

public class IconsHandler {

    private IconsHandler() {}

    public static final ImageIcon OPEN = new ImageIcon(
            Objects.requireNonNull(IconsHandler.class.getClassLoader().getResource("icons/open.png")));
    public static final ImageIcon SAVE = new ImageIcon(
            Objects.requireNonNull(IconsHandler.class.getClassLoader().getResource("icons/save.png")));
    public static final ImageIcon FIT = new ImageIcon(
            Objects.requireNonNull(IconsHandler.class.getClassLoader().getResource("icons/fit.png")));
    public static final ImageIcon FULL = new ImageIcon(
            Objects.requireNonNull(IconsHandler.class.getClassLoader().getResource("icons/real.png")));
    public static final ImageIcon WHITE_BLACK = new ImageIcon(
            Objects.requireNonNull(IconsHandler.class.getClassLoader().getResource("icons/wh-bl.png")));
    public static final ImageIcon SWITCH = new ImageIcon(
            Objects.requireNonNull(IconsHandler.class.getClassLoader().getResource("icons/restore.png")));
    public static final ImageIcon INVERSION = new ImageIcon(
            Objects.requireNonNull(IconsHandler.class.getClassLoader().getResource("icons/inversion.png")));
    public static final ImageIcon GAUSSIAN = new ImageIcon(
            Objects.requireNonNull(IconsHandler.class.getClassLoader().getResource("icons/gaussian.png")));
    public static final ImageIcon SHARPENING = new ImageIcon(
            Objects.requireNonNull(IconsHandler.class.getClassLoader().getResource("icons/sharpening.png")));
    public static final ImageIcon EMBOSSING = new ImageIcon(
            Objects.requireNonNull(IconsHandler.class.getClassLoader().getResource("icons/embossing.png")));
    public static final ImageIcon GAMMA = new ImageIcon(
            Objects.requireNonNull(IconsHandler.class.getClassLoader().getResource("icons/gamma.png")));
    public static final ImageIcon BORDERS = new ImageIcon(
            Objects.requireNonNull(IconsHandler.class.getClassLoader().getResource("icons/borders.png")));
    public static final ImageIcon DITHERING_ROUND = new ImageIcon(
            Objects.requireNonNull(IconsHandler.class.getClassLoader().getResource("icons/dithering2.png")));
    public static final ImageIcon DITHERING_SQUARE = new ImageIcon(
            Objects.requireNonNull(IconsHandler.class.getClassLoader().getResource("icons/dithering3.png")));
    public static final ImageIcon WATERCOLOR = new ImageIcon(
            Objects.requireNonNull(IconsHandler.class.getClassLoader().getResource("icons/watercolor.png")));
    public static final ImageIcon ROTATE = new ImageIcon(
            Objects.requireNonNull(IconsHandler.class.getClassLoader().getResource("icons/rotate.png")));
    public static final ImageIcon DILATION = new ImageIcon(
            Objects.requireNonNull(IconsHandler.class.getClassLoader().getResource("icons/shine.png")));
}
