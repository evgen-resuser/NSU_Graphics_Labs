package org.evgen.view;

import javax.swing.*;
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
            Objects.requireNonNull(IconsHandler.class.getClassLoader().getResource("icons/full.png")));

}
