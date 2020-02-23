package com.mnk.Model;

import java.awt.*;

public class Oval extends Shape {

    public Oval(int x1, int y1, int strokeWidth, Color color) {
        super(x1, y1, strokeWidth, color);
    }

    public Oval(int x1, int x2, int y1, int y2, Color color) {
        super(x1, x2, y1, y2, color);
    }

    @Override
    public void drawShape(Graphics graphics) {

        graphics.drawOval(getStartX(),getStartY(),getWidth(),getHeight());
    }


}
