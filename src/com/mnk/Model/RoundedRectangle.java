package com.mnk.Model;

import java.awt.*;

public class RoundedRectangle extends Shape {


    public RoundedRectangle(int x1, int y1, int strokeWidth, Color color) {
        super(x1, y1, strokeWidth, color);
    }

    @Override
    public void drawShape(Graphics graphics) {
        graphics.drawRoundRect(getStartX(),getStartY(),getWidth(),getHeight(),20,20);

    }

    @Override
    public boolean contains(int x, int y) {
        if (getStartX()<x && getStartY()<y &&
                getStartX()+getWidth()>x && getStartY()+getHeight()>y){
            return true;
        }
        return false;
    }
}
