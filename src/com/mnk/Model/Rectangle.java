package com.mnk.Model;

import java.awt.*;

public class Rectangle extends Shape {
    public Rectangle(int x1, int y1, int strokeWidth, Color color) {
        super(x1, y1, strokeWidth, color);
    }

    public Rectangle(int x1, int x2, int y1, int y2, Color color) {
        super(x1, x2, y1, y2, color);
    }

    @Override
    public void drawShape(Graphics graphics) {
        graphics.drawRect(getStartX(),getStartY(),getWidth(),getHeight());
    }

    @Override
    public boolean contains(int x, int y) {
        if (getStartX()<x && getStartY()<y &&
                getStartX()+getWidth()>x && getStartY()+getStartY()+getHeight()>y){
            return true;
        }
        return false;
    }
}
