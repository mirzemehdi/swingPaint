package com.mnk;

import com.mnk.Enums.ShapeType;
import com.mnk.Model.Shape;
import com.mnk.Utils.Common;
import com.mnk.Utils.SaveRestoreObjectFromFile;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

public class Main extends JFrame implements ActionListener {

    private JButton undoBtn,redoBtn,circleBtn,rectangleBtn,lineBtn,roundedRectangleBtn
            ,clearAllBtn,colorChooserBtn,saveBtn,openBtn,exportBtn,newBtn,smallLineBtn,middleLineBtn,largeLineBtn;
    private JButton colorBtn1,colorBtn2,colorBtn3,colorBtn4,colorBtn5,colorBtn6;
    private List<JButton>shapeButtonsList=new ArrayList<>();
    private List<JButton>lineButtonsList=new ArrayList<>();
    private List<JButton>sampleColorsButtonList=new ArrayList<>();


    private DrawPanel drawPanel;
    private Color[] sampleColors=new Color[]{Color.BLACK,Color.RED,Color.GREEN,Color.YELLOW,Color.BLUE,Color.MAGENTA};

    public static void main(String[] args) {
	// write your code here
        Main main=new Main("New Paint");



    }

    public Main(String title) throws HeadlessException {
        super(title) ;

        /**
         * Main View is divided to three parts East,North and Center
         * so it is better to use BorderLayout
         */
        JPanel mainPanel=new JPanel(new BorderLayout());

        //Center part
        drawPanel=new DrawPanel();
        //North part
        JPanel operationsPanel= initOperationsView();
        //East part
        JPanel shapesColorsPanel=initShapesColorsPanel();




        mainPanel.add(operationsPanel,BorderLayout.NORTH);
        mainPanel.add(shapesColorsPanel,BorderLayout.EAST);
        mainPanel.add(drawPanel,BorderLayout.CENTER);


        //Finally adding it to Fragment
        add(mainPanel);

        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setSize(900,600);
        setMinimumSize(new Dimension(500,300));
        setVisible(true);

    }

    private JPanel initShapesColorsPanel() {
        JPanel shapesColorsPanel=new JPanel(new FlowLayout());

        GridBagLayout gridBagLayout=new GridBagLayout();
        JPanel verticalPanel=new JPanel(gridBagLayout);
        GridBagConstraints gridBagConstraints=new GridBagConstraints();
        gridBagConstraints.fill=GridBagConstraints.VERTICAL;
        gridBagConstraints.insets=new Insets(3,3,3,3);

        //Adding Shapes View
        JPanel shapesPanel=initShapesPanel();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        verticalPanel.add(shapesPanel,gridBagConstraints);

        //Adding LineStrokeView
        JPanel lineStrokesPanel=initLineStrokesPanel();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        verticalPanel.add(lineStrokesPanel,gridBagConstraints);

        //Adding sample colors View
        JPanel sampleColorsPanel=initColorsPanel();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        verticalPanel.add(sampleColorsPanel,gridBagConstraints);

        //Adding Color Picker
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 3;
        verticalPanel.add(colorChooserBtn,gridBagConstraints);

        shapesColorsPanel.add(verticalPanel);
        return shapesColorsPanel;


    }

    private JPanel initLineStrokesPanel() {
        JPanel lineStrokesPanel=new JPanel(new GridLayout(3,1,5,5));
        lineStrokesPanel.setBackground(Color.WHITE);
        lineStrokesPanel.setPreferredSize(new Dimension(90,90));
        //lineStrokesPanel.setBorder(new LineBorder(Color.GRAY,2));
        initLineButtons();
        lineStrokesPanel.add(smallLineBtn);
        lineStrokesPanel.add(middleLineBtn);
        lineStrokesPanel.add(largeLineBtn);
        lineBtnClicked(smallLineBtn,Common.SMALL_LINE_STROKE_WIDTH);
        return lineStrokesPanel;
    }

    private void initLineButtons() {
        smallLineBtn=new JButton();
        ImageIcon smallLineIcon= Common.createLineIcon(Color.BLACK,50,3);
        smallLineBtn.setIcon(smallLineIcon);
        smallLineBtn.setBackground(null);
        smallLineBtn.addActionListener(this);

        middleLineBtn=new JButton();
        ImageIcon middleLineIcon= Common.createLineIcon(Color.BLACK,60,5);
        middleLineBtn.setIcon(middleLineIcon);
        middleLineBtn.setBackground(null);
        middleLineBtn.addActionListener(this);

        largeLineBtn=new JButton();
        ImageIcon largeLineIcon= Common.createLineIcon(Color.BLACK,70,10);
        largeLineBtn.setBackground(null);
        largeLineBtn.setIcon(largeLineIcon);
        largeLineBtn.addActionListener(this);

        lineButtonsList.add(smallLineBtn);
        lineButtonsList.add(middleLineBtn);
        lineButtonsList.add(largeLineBtn);

    }

    private JPanel initColorsPanel() {

        JPanel sampleColorsPanel=new JPanel(new GridLayout(2,3,3,3));
        initColorsBtn();
        sampleColorsPanel.add(colorBtn1);
        sampleColorsPanel.add(colorBtn2);
        sampleColorsPanel.add(colorBtn3);
        sampleColorsPanel.add(colorBtn4);
        sampleColorsPanel.add(colorBtn5);
        sampleColorsPanel.add(colorBtn6);
        return sampleColorsPanel;
    }

    private JPanel initShapesPanel() {
        JPanel shapesPanel=new JPanel(new GridLayout(2,2,5,5));
        initShapeButtons();
        shapesPanel.add(lineBtn);
        shapesPanel.add(circleBtn);
        shapesPanel.add(rectangleBtn);
        shapesPanel.add(roundedRectangleBtn);
        shapeBtnClicked(lineBtn,ShapeType.LINE);
        return shapesPanel;
    }

    private void initShapeButtons() {
        circleBtn=new JButton(createIcon("icons/circle.png"));
        circleBtn.setPreferredSize(new Dimension(40,40));
        circleBtn.setBackground(Color.WHITE);
        circleBtn.addActionListener(this);

        rectangleBtn=new JButton(createIcon("icons/rectangle.png"));
        rectangleBtn.addActionListener(this);
        rectangleBtn.setPreferredSize(new Dimension(40,40));
        rectangleBtn.setBackground(Color.WHITE);

        lineBtn=new JButton(createIcon("icons/line.png"));
        lineBtn.setPreferredSize(new Dimension(40,40));
        lineBtn.setBackground(Color.WHITE);
        lineBtn.addActionListener(this);

        roundedRectangleBtn=new JButton(createIcon("icons/roundedrectangle.png"));
        roundedRectangleBtn.setPreferredSize(new Dimension(40,40));
        roundedRectangleBtn.setBackground(Color.WHITE);
        roundedRectangleBtn.addActionListener(this);

        shapeButtonsList.add(circleBtn);
        shapeButtonsList.add(lineBtn);
        shapeButtonsList.add(roundedRectangleBtn);
        shapeButtonsList.add(rectangleBtn);
    }

    private JPanel initOperationsView() {
        /**
         * Operation Buttons are added horizontally that's why
         * it is chosen FlowLayout
         */
        JPanel operationsPanel=new JPanel(new FlowLayout());
        initOperationButtons();
        operationsPanel.add(newBtn);
        operationsPanel.add(openBtn);
        operationsPanel.add(saveBtn);
        operationsPanel.add(clearAllBtn);
        operationsPanel.add(exportBtn);
        operationsPanel.add(undoBtn);
        operationsPanel.add(redoBtn);

        return operationsPanel;

    }

    private void initOperationButtons() {


        newBtn=new JButton("New");
        newBtn.setPreferredSize(new Dimension(80,30));
        newBtn.setBackground(Color.WHITE);
        newBtn.addActionListener(this);

        openBtn=new JButton("Open");
        openBtn.setPreferredSize(new Dimension(80,30));
        openBtn.setBackground(Color.WHITE);
        openBtn.addActionListener(this);

        saveBtn=new JButton("Save",createIcon("icons/save.png"));
        saveBtn.setPreferredSize(new Dimension(100,30));
        saveBtn.setBackground(Color.WHITE);
        saveBtn.addActionListener(this);

        clearAllBtn=new JButton("Clear");
        clearAllBtn.setPreferredSize(new Dimension(80,30));
        clearAllBtn.setBackground(Color.WHITE);
        clearAllBtn.addActionListener(this);


        exportBtn=new JButton("Export");
        exportBtn.setPreferredSize(new Dimension(80,30));
        exportBtn.setBackground(Color.WHITE);
        exportBtn.addActionListener(this);

        undoBtn=new JButton(createIcon("icons/undo.png"));
        undoBtn.setPreferredSize(new Dimension(60,30));
        undoBtn.setBackground(Color.WHITE);
        undoBtn.addActionListener(this);

        redoBtn=new JButton(createIcon("icons/redo.png"));
        redoBtn.setPreferredSize(new Dimension(60,30));
        redoBtn.setBackground(Color.WHITE);
        redoBtn.addActionListener(this);


    }



    private void initColorsBtn() {

        colorChooserBtn=new JButton();
        colorChooserBtn.setBackground(Color.BLACK);
        colorChooserBtn.setBorder(new LineBorder(Color.BLACK,5));
        colorChooserBtn.setPreferredSize(new Dimension(35,35));
        colorChooserBtn.addActionListener(this);

        colorBtn1=new JButton();
        colorBtn1.setBackground(sampleColors[0]);
        colorBtn1.setPreferredSize(new Dimension(25,25));
        colorBtn1.addActionListener(this);

        colorBtn2=new JButton();
        colorBtn2.setBackground(sampleColors[1]);
        colorBtn2.setPreferredSize(new Dimension(25,25));
        colorBtn2.addActionListener(this);

        colorBtn3=new JButton();
        colorBtn3.setBackground(sampleColors[2]);
        colorBtn3.setPreferredSize(new Dimension(25,25));
        colorBtn3.addActionListener(this);

        colorBtn4=new JButton();
        colorBtn4.setBackground(sampleColors[3]);
        colorBtn4.setPreferredSize(new Dimension(25,25));
        colorBtn4.addActionListener(this);

        colorBtn5=new JButton();
        colorBtn5.setBackground(sampleColors[4]);
        colorBtn5.setPreferredSize(new Dimension(25,25));
        colorBtn5.addActionListener(this);

        colorBtn6=new JButton();
        colorBtn6.setBackground(sampleColors[5]);
        colorBtn6.setPreferredSize(new Dimension(25,25));
        colorBtn6.addActionListener(this);

        sampleColorsButtonList.add(colorBtn1);
        sampleColorsButtonList.add(colorBtn2);
        sampleColorsButtonList.add(colorBtn3);
        sampleColorsButtonList.add(colorBtn4);
        sampleColorsButtonList.add(colorBtn5);
        sampleColorsButtonList.add(colorBtn6);


    }

    private void sampleColorButtonClicked(JButton clickedBtn, Color color) {
        drawPanel.setCurrentColor(color);
        colorChooserBtn.setBackground(color);
        for (JButton button: sampleColorsButtonList) {
            if (clickedBtn==button)
                button.setBorder(new LineBorder(Color.RED, 2));
            else
                button.setBorder(null);
        }
    }

    private ImageIcon createIcon(String filePath){
        ImageIcon icon=new ImageIcon(filePath);
        icon=new ImageIcon(icon.getImage().getScaledInstance(20,20,Image.SCALE_SMOOTH));
        return icon;
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        if (e.getSource()==redoBtn)
            drawPanel.addLastClearedShape();
        else if(e.getSource()==undoBtn)
            drawPanel.clearLastShape();
        else if (e.getSource()==circleBtn)
            shapeBtnClicked(circleBtn,ShapeType.OVAL);
        else if (e.getSource()==lineBtn)
            shapeBtnClicked(lineBtn, ShapeType.LINE);
        else if (e.getSource()==rectangleBtn)
           shapeBtnClicked(rectangleBtn, ShapeType.RECTANGLE);
        else if (e.getSource()==roundedRectangleBtn)
            shapeBtnClicked(roundedRectangleBtn, ShapeType.ROUNDEDRECTANGLE);
        else if (e.getSource()==clearAllBtn)
            drawPanel.clearAll();


        else if (e.getSource()==newBtn)
            newPaint();
        else if (e.getSource()==saveBtn)
            saveImage();
        else if (e.getSource()==openBtn) {
            openImage();
        }
        else if (e.getSource()==exportBtn)
            exportImage();

        //SampleColors
        else if (e.getSource()==colorBtn1)
            sampleColorButtonClicked(colorBtn1,sampleColors[0]);
        else if (e.getSource()==colorBtn2)
            sampleColorButtonClicked(colorBtn2,sampleColors[1]);
        else if (e.getSource()==colorBtn3)
            sampleColorButtonClicked(colorBtn3,sampleColors[2]);
        else if (e.getSource()==colorBtn4)
            sampleColorButtonClicked(colorBtn4,sampleColors[3]);
        else if (e.getSource()==colorBtn5)
            sampleColorButtonClicked(colorBtn5,sampleColors[4]);
        else if (e.getSource()==colorBtn6)
            sampleColorButtonClicked(colorBtn6,sampleColors[5]);

        else if (e.getSource()==colorChooserBtn){
            Color selectedColor=JColorChooser.showDialog(null, "Choose a color", colorChooserBtn.getBackground());
            sampleColorButtonClicked(colorChooserBtn,selectedColor);
        }


        //Line Stroke Buttons
        else if(e.getSource()==smallLineBtn)
            lineBtnClicked(smallLineBtn,Common.SMALL_LINE_STROKE_WIDTH);
        else if(e.getSource()==middleLineBtn)
            lineBtnClicked(middleLineBtn,Common.MIDDLE_LINE_STROKE_WIDTH);
        else if(e.getSource()==largeLineBtn)
            lineBtnClicked(largeLineBtn,Common.LARGE_LINE_STROKE_WIDTH);




    }

    private void lineBtnClicked(JButton clickedBtn, int strokeWidth) {
        drawPanel.setCurrentStrokeWidth(strokeWidth);

        for (JButton button: lineButtonsList) {
            if (clickedBtn==button)
                button.setBorder(new LineBorder(Color.RED, 2));
            else
                button.setBorder(null);
        }
    }

    private void shapeBtnClicked(JButton selectedBtn, ShapeType shapeType) {
        drawPanel.setShapeType(shapeType);
        for (JButton button: shapeButtonsList) {
            if (selectedBtn==button)
                button.setBorder(new LineBorder(Color.RED, 2));
            else
                button.setBorder(null);
        }

    }

    private void newPaint() {
        Main main=new Main("New Paint");
        main.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
    }



    private void saveImage() {
        JFileChooser fileChooser=new JFileChooser();
        fileChooser.setDialogTitle("Choose a directory to save your file: ");
        int returnValue=fileChooser.showOpenDialog(null);
        if (returnValue==JFileChooser.APPROVE_OPTION) {
            File file=fileChooser.getSelectedFile();
            SaveRestoreObjectFromFile.saveToFile(file.getPath()+".paint", drawPanel.getCurrentShapes());
        }

    }

    private void exportImage(){
        JFileChooser fileChooser=new JFileChooser();
        fileChooser.setDialogTitle("Choose a directory to save your image: ");
        int returnValue=fileChooser.showOpenDialog(null);
        if (returnValue==JFileChooser.APPROVE_OPTION) {
            File file=fileChooser.getSelectedFile();

            BufferedImage im = new BufferedImage(drawPanel.getWidth(), drawPanel.getHeight(), BufferedImage.TYPE_INT_ARGB);
            try {
                drawPanel.paint(im.getGraphics());
                ImageIO.write(im, "png", new File(file+".png"));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void openImage(){

        JFileChooser fileChooser=new JFileChooser();
        fileChooser.setDialogTitle("Choose a directory to open your file: ");
        int returnValue=fileChooser.showOpenDialog(null);
        if (returnValue==JFileChooser.APPROVE_OPTION) {
            File file=fileChooser.getSelectedFile();

            try {
                Stack<Shape> savedShapes = (Stack<Shape>) SaveRestoreObjectFromFile.restoreFromFile(file.getPath());
                drawPanel.setCurrentShapes(savedShapes);
                repaint();
                setTitle(file.getName());
            }
            catch (Exception e){
                System.out.println("Error while opening file");
            }
        }



    }
}
