package com.mnk;

import com.mnk.Enums.ShapeType;
import com.mnk.Model.Shape;
import com.mnk.Utils.ColorChooserButton;
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
            ,clearAllBtn,colorChooserBtn,saveBtn,openBtn,exportBtn,newBtn;
    private JButton colorBtn1,colorBtn2,colorBtn3,colorBtn4,colorBtn5,colorBtn6;
    private List<JButton>shapeButtonsList=new ArrayList<>();

    private ColorChooserButton colorChooserButton;
    private DrawPanel drawPanel;
    private Color[] sampleColors=new Color[]{Color.GREEN,Color.RED,Color.BLACK,Color.YELLOW,Color.BLUE,Color.MAGENTA};

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

        //North part
        JPanel operationsPanel= initOperationsView();
        //East part
        JPanel shapesColorsPanel=initShapesColorsPanel();
        //Center part
        drawPanel=new DrawPanel();



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

        JPanel shapesPanel=initShapesPanel();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        verticalPanel.add(shapesPanel,gridBagConstraints);



        JPanel sampleColorsPanel=initColorsPanel();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        verticalPanel.add(sampleColorsPanel,gridBagConstraints);

        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        verticalPanel.add(colorChooserBtn,gridBagConstraints);

        shapesColorsPanel.add(verticalPanel);
        return shapesColorsPanel;


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

        colorChooserBtn=new JButton();
        colorChooserBtn.setBackground(Color.BLACK);
        colorChooserBtn.setBorder(new LineBorder(Color.BLACK,5));
        colorChooserBtn.setPreferredSize(new Dimension(35,35));
        colorChooserBtn.addActionListener(this);
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
        else if (e.getSource()==colorChooserBtn){
            System.out.println("Called ");
            Color selectedColor=JColorChooser.showDialog(null, "Choose a color", colorChooserBtn.getBackground());
            colorChooserBtn.setBackground(selectedColor);
            drawPanel.setCurrentColor(selectedColor);
        }

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
            changeColor(0);
        else if (e.getSource()==colorBtn2)
            changeColor(1);
        else if (e.getSource()==colorBtn3)
            changeColor(2);
        else if (e.getSource()==colorBtn4)
            changeColor(3);
        else if (e.getSource()==colorBtn5)
            changeColor(4);
        else if (e.getSource()==colorBtn6)
            changeColor(5);




    }

    private void shapeBtnClicked(JButton selectedBtn, ShapeType shapeType) {
        drawPanel.setShapeType(shapeType);
        for (JButton button: shapeButtonsList) {
            if (selectedBtn==button)
                button.setBorder(new LineBorder(Color.RED, 3));
            else
                button.setBorder(null);
        }

    }

    private void newPaint() {
        Main main=new Main("New Paint");
        main.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
    }

    private void changeColor(int index){
        drawPanel.setCurrentColor(sampleColors[index]);
        colorChooserBtn.setBackground(sampleColors[index]);
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

