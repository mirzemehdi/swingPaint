package com.mnk;

import com.mnk.CustomListeners.AddedShapeListener;
import com.mnk.CustomListeners.SelectFinishedListener;
import com.mnk.Enums.ShapeType;
import com.mnk.Model.*;
import com.mnk.Model.Rectangle;
import com.mnk.Model.Shape;
import com.mnk.Utils.Common;

import javax.swing.*;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Stack;

public class DrawPanel extends JPanel {
    private MouseAdapter mouseAdapter;
    private Shape currentShape;
    private Color currentColor;
    private int currentStrokeWidth;
    private ShapeType shapeType;
    private Stack<Shape>currentShapes;
    private Stack<Shape>removedShapes;
    private boolean isSelected=false;
    private boolean isMoved=false;
    private AddedShapeListener shapeListener;
    private SelectFinishedListener selectFinishedListener;
    private Shape selectedShape=null;

    public DrawPanel() {
        setBackground(Color.WHITE);
        currentColor=Color.BLACK;
        currentShapes=new Stack<>();
        removedShapes=new Stack<>();
        shapeType=ShapeType.LINE;
        currentStrokeWidth= Common.SMALL_LINE_STROKE_WIDTH;
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
        if (selectedShape!=null)
            selectedShape.addSelectedBorder(g,selectedShape);
    }

    public MouseAdapter getMouseAdapter() {
        return mouseAdapter;
    }

    public boolean isMoved() {
        return isMoved;
    }

    public void setMoved(boolean moved) {
        isMoved = moved;
    }

    public void setCurrentColor(Color currentColor) {
        this.currentColor = currentColor;
    }

    public void setShapeListener(AddedShapeListener shapeListener) {
        this.shapeListener = shapeListener;
    }

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelectFinishedListener(SelectFinishedListener selectFinishedListener) {
        this.selectFinishedListener = selectFinishedListener;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }

    public void setMouseAdapter(MouseAdapter mouseAdapter) {
        this.mouseAdapter = mouseAdapter;
    }

    public Stack<Shape> getRemovedShapes() {
        return removedShapes;
    }


    public void setRemovedShapes(Stack<Shape> removedShapes) {
        this.removedShapes = removedShapes;
    }

    public int getCurrentStrokeWidth() {
        return currentStrokeWidth;
    }

    public void setCurrentStrokeWidth(int currentStrokeWidth) {
        this.currentStrokeWidth = currentStrokeWidth;
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
                if (isSelected||isMoved){
                    selectShape(e);
                }
               else if (shapeType!=null) {
                   shapeListener.onAdded();
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
                    isSelected=false;
                    selectFinishedListener.onFinish();
                    selectedShape=null;
                    removedShapes.clear();
                    repaint();

                }
            }

            @Override
            public void mouseDragged(MouseEvent e) {
                super.mouseDragged(e);

                if(selectedShape!=null){
                    if (isSelected) {
                        selectedShape.setCenterX(e.getX());
                        selectedShape.setCenterY(e.getY());
                        repaint();
                    }
                    else if (isMoved){
                        resizeShape(e);
                        repaint();
                    }
                }

                if (currentShape!=null) {
                    currentShape.setX2(e.getX());
                    System.out.println("Called Current Shape");
                    currentShape.setY2(e.getY());
                    repaint();
                }
            }


        };
    }

    private void resizeShape(MouseEvent e) {
        System.out.println("x: "+e.getX()+ " y: "+e.getY());
        System.out.println("SelectX: "+selectedShape.getEndX()+" SelectY: "+selectedShape.getEndY());
            if ( (e.getX()>=selectedShape.getEndX()-5&&e.getX()<=selectedShape.getEndX()+5)
                    && (e.getY()<=selectedShape.getEndY()+5&& e.getY()>=selectedShape.getEndY()-5 )) {
                System.out.println("Right Bottom Corner ");

                if (selectedShape.getEndY()==selectedShape.getY1())
                    selectedShape.setY1(e.getY());
                else
                    selectedShape.setY2(e.getY());
                if (selectedShape.getEndX()==selectedShape.getX1())
                    selectedShape.setX1(e.getX());
                else
                    selectedShape.setX2(e.getX());

            }
        else if ( (e.getX()>=selectedShape.getEndX()-5&&e.getX()<=selectedShape.getEndX()+5)
                && (e.getY()<=selectedShape.getStartY()+5&& e.getY()>=selectedShape.getStartY()-5 )) {
            System.out.println("Right Top Corner ");
                if (selectedShape.getStartY()==selectedShape.getY1())
                    selectedShape.setY1(e.getY());
                else
                    selectedShape.setY2(e.getY());
                if (selectedShape.getEndX()==selectedShape.getX1())
                    selectedShape.setX1(e.getX());
                else
                    selectedShape.setX2(e.getX());
        }

            else if ( (e.getX()>=selectedShape.getStartX()-5&&e.getX()<=selectedShape.getStartX()+5)
                    && (e.getY()<=selectedShape.getEndY()+5&& e.getY()>=selectedShape.getEndY()-5 )) {
                System.out.println("Left Bottom Corner ");
                if (selectedShape.getEndY()==selectedShape.getY1())
                    selectedShape.setY1(e.getY());
                else
                    selectedShape.setY2(e.getY());
                if (selectedShape.getStartX()==selectedShape.getX1())
                    selectedShape.setX1(e.getX());
                else
                    selectedShape.setX2(e.getX());
            }
//
            else if ( (e.getX()>=selectedShape.getStartX()-5&&e.getX()<=selectedShape.getStartX()+5)
                    && (e.getY()<=selectedShape.getStartY()+5&& e.getY()>=selectedShape.getStartY()-5 )) {
                System.out.println("Left Top Corner ");
                if (selectedShape.getStartY()==selectedShape.getY1())
                    selectedShape.setY1(e.getY());
                else
                    selectedShape.setY2(e.getY());
                if (selectedShape.getStartX()==selectedShape.getX1())
                    selectedShape.setX1(e.getX());
                else
                    selectedShape.setX2(e.getX());
            }


    }

    public Shape getSelectedShape() {
        return selectedShape;
    }

    public void setSelectedShape(Shape selectedShape) {
        this.selectedShape = selectedShape;
    }

    private void selectShape(MouseEvent e) {
        for (Shape shape: currentShapes){
            if (shape.contains(e.getX(),e.getY())){

//                currentShape=shape;
                selectedShape=shape;
                repaint();


            }

        }
    }


}
