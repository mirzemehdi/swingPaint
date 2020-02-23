package com.mnk.Model;

import java.awt.*;
import java.io.Serializable;

public abstract  class Shape implements Serializable  {
    private int x1,x2,y1,y2;
    private Color color;
    private int strokeWidth;

    public Shape(int x1, int y1,int strokeWidth, Color color) {
        this.x1 = x1;
        this.y1 = y1;
        this.x2=x1;
        this.y2=y1;
        this.strokeWidth=strokeWidth;
        this.color = color;
    }

    public Shape(int x1, int x2, int y1, int y2, Color color) {
        this.x1 = x1;
        this.x2 = x2;
        this.y1 = y1;
        this.y2 = y2;
        this.color = color;
    }

    public int getX1() {
        return x1;
    }

    public int getStrokeWidth() {
        return strokeWidth;
    }

    public void setStrokeWidth(int strokeWidth) {
        this.strokeWidth = strokeWidth;
    }

    public void setX1(int x1) {
        this.x1 = x1;
    }

    public int getX2() {
        return x2;
    }

    public void setX2(int x2) {
        this.x2 = x2;
    }

    public int getY1() {
        return y1;
    }

    public void setY1(int y1) {
        this.y1 = y1;
    }

    public int getY2() {
        return y2;
    }

    public void setY2(int y2) {
        this.y2 = y2;
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public int getStartX(){return Math.min(getX1(),getX2());}
    public int getStartY(){return Math.min(getY1(),getY2());}
    public int getWidth(){return Math.abs(getX1()-getX2());}
    public int getHeight(){return Math.abs(getY1()-getY2());}

    public void draw(Graphics graphics){
        Graphics2D graphics2D=(Graphics2D)graphics;
        graphics2D.setColor(getColor());
        graphics2D.setStroke(new BasicStroke(getStrokeWidth()));
        drawShape(graphics);
    }

    public abstract void drawShape(Graphics graphics);
}
