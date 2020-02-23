package com.mnk.Utils;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

public class Common {

    public static final int SMALL_LINE_STROKE_WIDTH=3;
    public static final int MIDDLE_LINE_STROKE_WIDTH=5;
    public static final int LARGE_LINE_STROKE_WIDTH=10;

    public static ImageIcon createLineIcon(Color main, int width, int height) {
        BufferedImage image = new BufferedImage(width, height, java.awt.image.BufferedImage.TYPE_INT_RGB);
        Graphics2D graphics = image.createGraphics();
        graphics.setColor(main);
        graphics.fillRect(0, 0, width, height);
        graphics.setXORMode(Color.DARK_GRAY);
        graphics.drawRect(0, 0, width-1, height-1);
        image.flush();
        ImageIcon icon = new ImageIcon(image);
        return icon;
    }
}
