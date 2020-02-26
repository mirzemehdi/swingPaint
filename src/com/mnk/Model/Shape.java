package com.mnk.Model;

import java.awt.*;
import java.io.Serializable;

public abstract  class Shape implements Serializable  {
    private int x1,x2,y1,y2;
    private Color color;
    private int strokeWidth;
    private boolean isSelected;
    private boolean isFilled;

    public Shape(int x1, int y1,int strokeWidth, Color color) {
        this.x1 = x1;
        this.y1 = y1;
        this.x2=x1;
        this.y2=y1;
        isSelected=false;
        this.strokeWidth=strokeWidth;
        this.color = color;
        isFilled=false;
    }

    public Shape(int x1, int x2, int y1, int y2, Color color) {
        this.x1 = x1;
        this.x2 = x2;
        this.y1 = y1;
        this.y2 = y2;
        this.color = color;
    }

    public boolean isFilled() {
        return isFilled;
    }

    public void setFilled(boolean filled) {
        isFilled = filled;
    }

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
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
    public int getEndX(){return Math.max(getX1(),getX2());}
    public int getEndY(){return Math.max(getY1(),getY2());}
    public int getWidth(){return Math.abs(getX1()-getX2());}
    public int getHeight(){return Math.abs(getY1()-getY2());}



    public void draw(Graphics graphics){
        Graphics2D graphics2D=(Graphics2D)graphics;
        graphics2D.setColor(getColor());
        graphics2D.setStroke(new BasicStroke(getStrokeWidth()));
        drawShape(graphics, isFilled);
    }

    public abstract void drawShape(Graphics graphics, boolean isFilled);
    public  boolean isEdge(int x,int y){
        if (    x==getStartX() && (y>getStartY() &&y<getEndY())
                || x==getEndX() && (y>getStartY() &&y<getEndY())
                || y==getStartY() && (x>getStartX() &&x<getEndX())
                || y==getEndY() && (x>getStartX() &&y<getEndX())
            ){
            return true;
        }
        return false;
    }
    public abstract boolean contains(int x, int y);

    public void addSelectedBorder(Graphics g,Shape selectedShape) {
        Graphics2D graphics2D=(Graphics2D)g;
        graphics2D.setColor(Color.BLUE);
        graphics2D.setStroke(new BasicStroke(getStrokeWidth()));
        graphics2D.drawRect(selectedShape.getStartX(),selectedShape.getStartY(),getWidth(),getHeight());
    }


    public void setCenterX(int x) {
        int coef=(x1<x2)? 1: -1;
        int width=getWidth();
        x1=x-coef*width/2;
        x2=x+coef*width/2;
    }
    public void setCenterY(int y) {
        int coef=(y1<y2)? 1: -1;
        int height=getHeight();
        y1=y-coef*height/2;
        y2=y+coef*height/2;
    }
}
