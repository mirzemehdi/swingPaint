package com.mnk;

import com.mnk.Enums.ShapeType;
import com.mnk.Model.*;
import com.mnk.Model.Rectangle;
import com.mnk.Model.Shape;

import javax.swing.*;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.Stack;

public class DrawPanel extends JPanel {
    private MouseAdapter mouseAdapter;
    private Shape currentShape;
    private Color currentColor;
    private int currentStrokeWidth;
    private ShapeType shapeType;
    private Stack<Shape>currentShapes;
    private Stack<Shape>removedShapes;

    public DrawPanel() {
        setBackground(Color.WHITE);
        currentColor=Color.BLACK;
        currentShapes=new Stack<>();
        removedShapes=new Stack<>();
        shapeType=ShapeType.LINE;
        currentStrokeWidth=5;
        initMouseAdapter();
        addMouseListener(mouseAdapter);
        addMouseMotionListener(mouseAdapter);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        for (Shape shape: currentShapes){
            shape.draw(g);
        }

        if (currentShape!=null)
            currentShape.draw(g);
    }

    public MouseAdapter getMouseAdapter() {
        return mouseAdapter;
    }

    public void setCurrentColor(Color currentColor) {
        this.currentColor = currentColor;
    }

    public void setMouseAdapter(MouseAdapter mouseAdapter) {
        this.mouseAdapter = mouseAdapter;
    }

    public Stack<Shape> getCurrentShapes() {
        return currentShapes;
    }

    public void setCurrentShapes(Stack<Shape> currentShapes) {
        this.currentShapes = currentShapes;
    }

    public ShapeType getShapeType() {
        return shapeType;
    }

    public void setShapeType(ShapeType shapeType) {
        this.shapeType = shapeType;
    }





    public void clearLastShape(){
        if (!currentShapes.empty()){
            removedShapes.push(currentShapes.pop());// Removes from Current Shapes and adds to removedShapes
            repaint();
        }
    }

    public void addLastClearedShape(){
        if (!removedShapes.empty()){
            currentShapes.push(removedShapes.pop()); //Removes from Removed Shapes and adds to currentShapes
            repaint();
        }
    }

    public void clearAll(){
        removedShapes.clear();
        currentShapes.clear();

        repaint();
    }

    private void initMouseAdapter() {
        mouseAdapter=new MouseAdapter() {


            @Override
            public void mousePressed(MouseEvent e) {
                super.mousePressed(e);
                if (shapeType!=null) {
                    switch (shapeType) {
                        case OVAL:
                            currentShape = new Oval(e.getX(), e.getY(),currentStrokeWidth, currentColor);
                            break;
                        case LINE:
                            currentShape=new Line(e.getX(),e.getY(),currentStrokeWidth,currentColor);
                            break;
                        case RECTANGLE:
                            currentShape=new Rectangle(e.getX(),e.getY(),currentStrokeWidth,currentColor);
                            break;
                        case ROUNDEDRECTANGLE:
                            currentShape=new RoundedRectangle(e.getX(),e.getY(),currentStrokeWidth,currentColor);
                            break;

                        default:
                            System.out.println("Please , select a shape");
                            break;
                    }
                }
                else {
                    System.out.println("Please, select a shape");
                }

            }

            @Override
            public void mouseReleased(MouseEvent e) {
                super.mouseReleased(e);
                if (currentShape!=null) {
                    currentShape.setX2(e.getX());
                    currentShape.setY2(e.getY());
                    currentShapes.add(currentShape);
                    currentShape = null;
                    removedShapes.clear();
                    repaint();
                }
            }

            @Override
            public void mouseDragged(MouseEvent e) {
                super.mouseDragged(e);
                if (currentShape!=null) {
                    currentShape.setX2(e.getX());
                    currentShape.setY2(e.getY());
                    repaint();
                }
            }


        };
    }


}
