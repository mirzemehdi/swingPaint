package com.mnk.Model;

import java.awt.*;

public class Line extends Shape {
    public Line(int x1, int y1, int strokeWidth, Color color) {

        super(x1, y1, strokeWidth, color);
    }

    public Line(int x1, int x2, int y1, int y2, Color color) {

        super(x1, x2, y1, y2, color);
    }

    @Override
    public void drawShape(Graphics graphics) {
        graphics.drawLine(getX1(), getY1(), getX2(), getY2());
    }



    @Override
    public boolean contains(int x, int y) {
        System.out.println("x: "+x+ " y: "+y);
        System.out.println("getStartX: "+getStartX()+ " getEndX: "+getEndX()
        +                   "getStartY: "+getStartY()+ " getEndY: "+getEndY());

        if (getStartX()<=x && getEndX()>=x && getStartY()-5<=y&& getEndY()+5>=y ){
            return true;
        }
        return false;
    }
}
