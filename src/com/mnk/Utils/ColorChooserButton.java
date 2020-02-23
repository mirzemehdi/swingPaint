package com.mnk.Utils;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class ColorChooserButton extends JButton {

    private Color current;
    private ColorChangedListener colorChangedListener;

    public ColorChooserButton(Color c) {
        setSelectedColor(c);
        addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent arg0) {
                Color newColor = JColorChooser.showDialog(null, "Choose a color", current);
                setSelectedColor(newColor);
            }
        });
    }

    public Color getSelectedColor() {
        return current;
    }



    public void setSelectedColor(Color newColor) {

        if (newColor == null) return;
        current = newColor;
        setIcon(createIcon(current, 16, 16));
        if (colorChangedListener!=null)
             colorChangedListener.colorChanged(current);
    }

    public  interface ColorChangedListener {
         void colorChanged(Color newColor);
    }



    public void addColorChangedListener(ColorChangedListener colorChangedListener) {
        this.colorChangedListener=colorChangedListener;
    }

    public static  ImageIcon createIcon(Color main, int width, int height) {
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
