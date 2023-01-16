import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
import java.io.File;
import javax.imageio.ImageIO;
import java.io.IOException;
import javax.swing.JOptionPane;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.awt.image.WritableRaster;
import java.awt.image.ColorModel;
import java.util.ArrayList;

import java.awt.image.BufferedImageOp;
import java.awt.image.ConvolveOp;
import java.awt.image.Kernel;

/**
 * 
 * The class Processor contains all of the code to actually perform
 * transformation. The rest of the classes serve to support that
 * capability. This World allows the user to choose transformations
 * and open files.
 * 
 * <p>Credit: blur algorithm - java2s.com | Demo Source and Support. http://www.java2s.com/Code/Java/2D-Graphics-GUI/BlurringaBufferedImage.htm
 * 
 * @author Calista Kurniawan
 * @version 2022
 */
public class Background extends World
{
    // Constants:
    private final String STARTING_FILE = "StarterImage.png";
    public static final int MAX_WIDTH = 1000;
    public static final int MAX_HEIGHT = 600;

    // Objects and Variables:
    private ImageHolder image;
    private TextButton colourTitle, flipTitle, rotationTitle, filterTitle, blueButton, hRevButton, vRevButton, sepiaButton, negButton, greyscaleButton, redButton, ninetyCWButton, oneEightyCWButton, ninetyCCWButton, blurButton, warmButton, coolButton, greenButton, yellowButton, magentaButton, cyanButton, undoButton, openButton, redoButton, saveButton;
    private SuperTextBox openFile;
    private String fileName;
    
    //history arraylist for undo and redo
    private ArrayList<BufferedImage> history = new ArrayList<BufferedImage>();
    private int currentImage = 0;
    
    /**
     * Constructor for objects of class Background.
     * 
     */
    public Background()
    {    
        // Create a new world with 600x400 cells with a cell size of 1x1 pixels.
        super(1024, 800, 1); 

        // Initialize buttons and the image --> Performed first so that the details can be retrieved and displayed
        image = new ImageHolder(STARTING_FILE); // The image holder constructor does the actual image loading

        // Set up UI elements --> See appropriate constructor API for details
        
        //colour buttons/labels
        colourTitle = new TextButton("Colourify", 8, 160, false, Color.WHITE, Color.BLUE, Color.BLACK, Color.BLACK, Color.WHITE, new Font ("Monaco",false ,false ,16));  
        greenButton = new TextButton("  ", 8, 35, true, Color.GREEN, Color.GREEN, Color.GREEN, Color.GREEN, Color.GREEN, new Font ("Comic Sans MS",false ,false ,10));
        yellowButton = new TextButton("  ", 8, 35, true, Color.YELLOW, Color.YELLOW, Color.YELLOW, Color.YELLOW, Color.YELLOW, new Font ("Comic Sans MS",false ,false ,10));
        blueButton = new TextButton("  ", 8, 35, true, Color.BLUE, Color.BLUE, Color.BLUE, Color.BLUE, Color.BLUE, new Font ("Comic Sans MS",false ,false ,10));
        redButton = new TextButton("  ", 8, 35, true, Color.RED, Color.RED, Color.RED, Color.RED, Color.RED, new Font ("Comic Sans MS",false ,false ,10));
        magentaButton = new TextButton("  ", 8, 35, true, Color.MAGENTA, Color.MAGENTA, Color.MAGENTA, Color.MAGENTA, Color.MAGENTA, new Font ("Comic Sans MS",false ,false ,10));
        cyanButton = new TextButton("  ", 8, 35, true, Color.CYAN, Color.CYAN, Color.CYAN, Color.CYAN, Color.CYAN, new Font ("Comic Sans MS",false ,false ,10));
        
        //flip buttons/labels
        flipTitle = new TextButton("Flip", 8, 160, false, Color.WHITE, Color.BLUE, Color.BLACK, Color.BLACK, Color.WHITE, new Font ("Monaco",false ,false ,16));
        hRevButton = new TextButton("Horizontal", 8, 160, true, Color.BLACK, Color.BLUE, Color.WHITE, Color.WHITE, Color.BLACK, new Font ("Monaco",false ,false ,16));  
        vRevButton = new TextButton ("Vertical", 8, 160, true, Color.BLACK, Color.BLUE, Color.WHITE, Color.WHITE, Color.BLACK, new Font ("Monaco",false ,false ,16));
        
        //rotation buttons/labels
        rotationTitle = new TextButton("Rotate", 8, 160, false, Color.WHITE, Color.BLUE, Color.BLACK, Color.BLACK, Color.WHITE, new Font ("Monaco",false ,false ,16));
        ninetyCWButton = new TextButton ("90 CW", 8, 80, true, Color.BLACK, Color.BLUE, Color.WHITE, Color.WHITE, Color.BLACK, new Font ("Monaco",false ,false ,16));
        oneEightyCWButton = new TextButton ("180 CW", 8, 160, true, Color.BLACK, Color.BLUE, Color.WHITE, Color.WHITE, Color.BLACK, new Font ("Monaco",false ,false ,16));
        ninetyCCWButton = new TextButton ("90 CCW", 8, 80, true, Color.BLACK, Color.BLUE, Color.WHITE, Color.WHITE, Color.BLACK, new Font ("Monaco",false ,false ,16));
        
        
        //filter buttons/labels
        filterTitle = new TextButton("Filters", 8, 500, false, Color.WHITE, Color.BLUE, Color.BLACK, Color.BLACK, Color.WHITE, new Font ("Monaco",false ,false ,16));
        sepiaButton = new TextButton ("Sepia", 8, 160, true, Color.BLACK, Color.BLUE, Color.WHITE, Color.WHITE, Color.BLACK, new Font ("Monaco",false ,false ,16));
        negButton = new TextButton ("Negative", 8, 160, true, Color.BLACK, Color.BLUE, Color.WHITE, Color.WHITE, Color.BLACK, new Font ("Monaco",false ,false ,16));
        greyscaleButton = new TextButton ("Greyscale", 8, 160, true, Color.BLACK, Color.BLUE, Color.WHITE, Color.WHITE, Color.BLACK, new Font ("Monaco",false ,false ,16));
        blurButton = new TextButton ("Blur", 8, 160, true, Color.BLACK, Color.BLUE, Color.WHITE, Color.WHITE, Color.BLACK, new Font ("Monaco",false ,false ,16));
        warmButton = new TextButton ("Warmer", 8, 160, true, Color.BLACK, Color.BLUE, Color.WHITE, Color.WHITE, Color.BLACK, new Font ("Monaco",false ,false ,16));
        coolButton = new TextButton ("Cooler", 8, 160, true, Color.BLACK, Color.BLUE, Color.WHITE, Color.WHITE, Color.BLACK, new Font ("Monaco",false ,false ,16));
        
        
        //undo redo buttons
        undoButton = new TextButton ("Undo", 8, 80, true, Color.BLACK, Color.BLUE, Color.WHITE, Color.WHITE, Color.BLACK, new Font ("Monaco",false ,false ,16));
        redoButton = new TextButton ("Redo", 8, 80, true, Color.BLACK, Color.BLUE, Color.WHITE, Color.WHITE, Color.BLACK, new Font ("Monaco",false ,false ,16));
        
        //open and save buttons
        openButton = new TextButton ("Open", 8, 80, true, Color.BLACK, Color.GREEN, Color.WHITE, Color.WHITE, Color.BLACK, new Font ("Monaco",false ,false ,16));
        saveButton = new TextButton ("Save", 8, 80, true, Color.BLACK, Color.GREEN, Color.WHITE, Color.WHITE, Color.BLACK, new Font ("Monaco",false ,false ,16));
        // Populate text box with details about the image
        openFile = new SuperTextBox(new String[]{"File: " + STARTING_FILE,"Scale: " + image.getScale() + " W: " + image.getRealWidth() + " H: " + image.getRealHeight()}, new Font ("Courier New", false, false, 16), 600, true);//new TextButton(" [ Open File: " + STARTING_FILE + " ] ");

        // Add objects to the screen (distance between each for y-axis is 42)
        addObject (image, 510, 430);
        
        //colour section 
        addObject (colourTitle, 100, 24);
        addObject (redButton, 38, 66);
        addObject (greenButton, 100, 66);
        addObject (blueButton, 162, 66);
        addObject (yellowButton, 38, 108);
        addObject (magentaButton, 100, 108);
        addObject (cyanButton, 162, 108);
        
        //flips
        addObject (flipTitle, 265, 24);
        addObject (hRevButton, 265, 66);
        addObject (vRevButton, 265, 108);
        
        //rotation
        addObject (rotationTitle, 430, 24);
        addObject (ninetyCWButton, 390, 66);
        addObject (ninetyCCWButton, 470, 66); 
        addObject (oneEightyCWButton, 430, 108); 
        
        //filters
        addObject (filterTitle, 770, 24);
        addObject (sepiaButton, 600, 66);
        addObject (negButton, 770, 66);
        addObject (greyscaleButton, 940, 66);
        addObject (warmButton, 600, 108);
        addObject (coolButton, 770, 108);
        addObject (blurButton, 940, 108);
        
        //add undo and redo
        addObject (undoButton, 900, 800 - openFile.getImage().getHeight() / 2);
        addObject (redoButton, 980, 800 - openFile.getImage().getHeight() / 2);
        
        // place the open file text box
        addObject (openFile, openFile.getImage().getWidth() / 2, 800 - openFile.getImage().getHeight() / 2);
        // place the open file button directly beside the open file text box
        addObject (openButton, openFile.getImage().getWidth()  + openButton.getImage().getWidth()/2 + 2, 800 - openFile.getImage().getHeight() / 2);
        addObject (saveButton, openFile.getImage().getWidth()  + openButton.getImage().getWidth() + saveButton.getImage().getWidth()/2 + 4,800 - openFile.getImage().getHeight() / 2);
        
        //add starting image to history
        history.add(image.getBufferedImage());
    }

    /**
     * Act() method just checks for mouse input
     */
    public void act ()
    {
        checkMouse();
    }

    /**
     * Check for user clicking on a button
     */
    private void checkMouse ()
    {
        // Avoid excess mouse checks - only check mouse if somethething is clicked.
        if (Greenfoot.mouseClicked(null))
        {
            if (Greenfoot.mouseClicked(blueButton)){
                image.setNewImage(createGreenfootImageFromBI(Processor.blueify(image.getBufferedImage())));
                image.redraw();
                openFile.update (image.getDetails());
                
                int arrayListSize = history.size();
                if(currentImage != arrayListSize - 1){
                    history.subList(currentImage + 1, arrayListSize).clear();
                }
                history.add(image.getBufferedImage());
                currentImage = history.size() - 1;
            }
            else if (Greenfoot.mouseClicked(hRevButton)){
                image.setNewImage(createGreenfootImageFromBI(Processor.flipHorizontal(image.getBufferedImage())));
                image.redraw();
                openFile.update (image.getDetails ());
                
                int arrayListSize = history.size();
                if(currentImage != arrayListSize - 1){
                    history.subList(currentImage + 1, arrayListSize).clear();
                }
                history.add(image.getBufferedImage());
                currentImage = history.size() - 1;
            }
            else if (Greenfoot.mouseClicked(vRevButton)){
                image.setNewImage(createGreenfootImageFromBI(Processor.flipVertical(image.getBufferedImage())));
                image.redraw();
                openFile.update (image.getDetails ());
                int arrayListSize = history.size();
                if(currentImage != arrayListSize - 1){
                    history.subList(currentImage + 1, arrayListSize).clear();
                }
                history.add(image.getBufferedImage());
                currentImage = history.size() - 1;
            }
            else if (Greenfoot.mouseClicked(sepiaButton)){
                image.setNewImage(createGreenfootImageFromBI(Processor.sepia(image.getBufferedImage())));
                image.redraw();
                openFile.update (image.getDetails ());
                int arrayListSize = history.size();
                if(currentImage != arrayListSize - 1){
                    history.subList(currentImage + 1, arrayListSize).clear();
                }
                history.add(image.getBufferedImage());
                currentImage = history.size() - 1;
            }
            else if (Greenfoot.mouseClicked(negButton)){
                image.setNewImage(createGreenfootImageFromBI(Processor.negative(image.getBufferedImage())));
                image.redraw();
                openFile.update (image.getDetails ());
                int arrayListSize = history.size();
                if(currentImage != arrayListSize - 1){
                    history.subList(currentImage + 1, arrayListSize).clear();
                }
                history.add(image.getBufferedImage());
                currentImage = history.size() - 1;
            }
            else if (Greenfoot.mouseClicked(greyscaleButton)){
                image.setNewImage(createGreenfootImageFromBI(Processor.greyScale(image.getBufferedImage())));
                image.redraw();
                openFile.update (image.getDetails ());
                int arrayListSize = history.size();
                if(currentImage != arrayListSize - 1){
                    history.subList(currentImage + 1, arrayListSize).clear();
                }
                history.add(image.getBufferedImage());
                currentImage = history.size() - 1;
            }
            else if (Greenfoot.mouseClicked(redButton)){
                image.setNewImage(createGreenfootImageFromBI(Processor.redify(image.getBufferedImage())));
                image.redraw();
                openFile.update (image.getDetails ());
                int arrayListSize = history.size();
                if(currentImage != arrayListSize - 1){
                    history.subList(currentImage + 1, arrayListSize).clear();
                }
                history.add(image.getBufferedImage());
                currentImage = history.size() - 1;
            }
            else if (Greenfoot.mouseClicked(greenButton)){
                image.setNewImage(createGreenfootImageFromBI(Processor.greenify(image.getBufferedImage())));
                image.redraw();
                openFile.update (image.getDetails ());
                int arrayListSize = history.size();
                if(currentImage != arrayListSize - 1){
                    history.subList(currentImage + 1, arrayListSize).clear();
                }
                history.add(image.getBufferedImage());
                currentImage = history.size() - 1;
            }
            else if (Greenfoot.mouseClicked(yellowButton)){
                image.setNewImage(createGreenfootImageFromBI(Processor.yellowify(image.getBufferedImage())));
                image.redraw();
                openFile.update (image.getDetails ());
                int arrayListSize = history.size();
                if(currentImage != arrayListSize - 1){
                    history.subList(currentImage + 1, arrayListSize).clear();
                }
                history.add(image.getBufferedImage());
                currentImage = history.size() - 1;
            }
            else if (Greenfoot.mouseClicked(magentaButton)){
                image.setNewImage(createGreenfootImageFromBI(Processor.magentify(image.getBufferedImage())));
                image.redraw();
                openFile.update (image.getDetails ());
                int arrayListSize = history.size();
                if(currentImage != arrayListSize - 1){
                    history.subList(currentImage + 1, arrayListSize).clear();
                }
                history.add(image.getBufferedImage());
                currentImage = history.size() - 1;
            }
            else if (Greenfoot.mouseClicked(cyanButton)){
                image.setNewImage(createGreenfootImageFromBI(Processor.cyanify(image.getBufferedImage())));
                image.redraw();
                openFile.update (image.getDetails ());
                int arrayListSize = history.size();
                if(currentImage != arrayListSize - 1){
                    history.subList(currentImage + 1, arrayListSize).clear();
                }
                history.add(image.getBufferedImage());
                currentImage = history.size() - 1;
            }
            else if (Greenfoot.mouseClicked(ninetyCWButton)){
                image.setNewImage(createGreenfootImageFromBI(Processor.rotateNinetyCW(image.getBufferedImage())));
                image.redraw();
                openFile.update (image.getDetails ());
                int arrayListSize = history.size();
                if(currentImage != arrayListSize - 1){
                    history.subList(currentImage + 1, arrayListSize).clear();
                }
                history.add(image.getBufferedImage());
                currentImage = history.size() - 1;
            }
            else if (Greenfoot.mouseClicked(oneEightyCWButton)){
                image.setNewImage(createGreenfootImageFromBI(Processor.rotateOneEightyCW(image.getBufferedImage())));
                image.redraw();
                openFile.update (image.getDetails ());
                int arrayListSize = history.size();
                if(currentImage != arrayListSize - 1){
                    history.subList(currentImage + 1, arrayListSize).clear();
                }
                history.add(image.getBufferedImage());
                currentImage = history.size() - 1;
            }
            else if (Greenfoot.mouseClicked(ninetyCCWButton)){
                image.setNewImage(createGreenfootImageFromBI(Processor.rotateNinetyCCW(image.getBufferedImage())));
                image.redraw();
                openFile.update (image.getDetails ());
                int arrayListSize = history.size();
                if(currentImage != arrayListSize - 1){
                    history.subList(currentImage + 1, arrayListSize).clear();
                }
                history.add(image.getBufferedImage());
                currentImage = history.size() - 1;
            }
            else if (Greenfoot.mouseClicked(blurButton)){
                image.setNewImage(createGreenfootImageFromBI(Processor.blur(image.getBufferedImage())));
                image.redraw();
                openFile.update (image.getDetails ());
                int arrayListSize = history.size();
                if(currentImage != arrayListSize - 1){
                    history.subList(currentImage + 1, arrayListSize).clear();
                }
                history.add(image.getBufferedImage());
                currentImage = history.size() - 1;
            }
            else if (Greenfoot.mouseClicked(warmButton)){
                image.setNewImage(createGreenfootImageFromBI(Processor.warmer(image.getBufferedImage())));
                image.redraw();
                openFile.update (image.getDetails ());
                int arrayListSize = history.size();
                if(currentImage != arrayListSize - 1){
                    history.subList(currentImage + 1, arrayListSize).clear();
                }
                history.add(image.getBufferedImage());
                currentImage = history.size() - 1;
            }
            else if (Greenfoot.mouseClicked(coolButton)){
                image.setNewImage(createGreenfootImageFromBI(Processor.cooler(image.getBufferedImage())));
                image.redraw();
                openFile.update (image.getDetails ());
                int arrayListSize = history.size();
                if(currentImage != arrayListSize - 1){
                    history.subList(currentImage + 1, arrayListSize).clear();
                }
                history.add(image.getBufferedImage());
                currentImage = history.size() - 1;
            }
            else if (Greenfoot.mouseClicked(undoButton)){
                if (currentImage - 1 >= 0){
                    currentImage--;
                    image.setNewImage(createGreenfootImageFromBI(history.get(currentImage)));
                    image.redraw();
                    openFile.update (image.getDetails ());
                }
                else{
                    System.out.println("Undo unavailable");
                }
            }
            else if (Greenfoot.mouseClicked(redoButton)){
                if (currentImage + 1 < history.size()){
                    currentImage++;
                    image.setNewImage(createGreenfootImageFromBI(history.get(currentImage)));
                    image.redraw();
                    openFile.update (image.getDetails ());
                }
                else{
                    System.out.println("Redo unavailable");
                }
            }
            else if (Greenfoot.mouseClicked(openButton))
            {
                openFile ();
                
                int arrayListSize = history.size();
                if(currentImage != arrayListSize - 1){
                    history.subList(currentImage + 1, arrayListSize).clear();
                }
                history.add(image.getBufferedImage());
                currentImage = history.size() - 1;
            }
            else if (Greenfoot.mouseClicked(saveButton))
            {
                saveFile ();
            }
        }
    }

    // Code provided, but not yet implemented - This will save image as a png.
    private void saveFile () {
        try{
            JFrame frame = new JFrame();
            JFileChooser fileChooser = new JFileChooser();
            int result = fileChooser.showOpenDialog(frame);
            if (result == JFileChooser.APPROVE_OPTION){
                File selectedFile = fileChooser.getSelectedFile();
                File file = new File (selectedFile.getAbsolutePath()+".png");
                ImageIO.write(image.getBufferedImage(),"png", file);
            }
        }
        catch (IOException e){
            // this code instead
            System.out.println("Unable to save file: " + e);
        }
    }

    /**
     * Allows the user to open a new image file.
     */
    private void openFile ()
    {
        // Create a UI frame (required to show a UI element from Java.Swing)
        JFrame frame = new JFrame();
        // Create a JFileChooser, a file chooser box included with Java 
        JFileChooser fileChooser = new JFileChooser();
        //fileChooser.setCurrentDirectory(new File(System.getProperty("user.home")));
        int result = fileChooser.showOpenDialog(frame);
        if (result == JFileChooser.APPROVE_OPTION){
            File selectedFile = fileChooser.getSelectedFile();
            //System.out.println("Selected file: " + selectedFile.getAbsolutePath());
            if (image.openFile (selectedFile.getAbsolutePath(), selectedFile.getName()))
            {
                //String display = " [ Open File: " + selectedFile.getName() + " ] ";
                openFile.update (image.getDetails ());
            }
        }
        // If the file opening operation is successful, update the text in the open file button
        /**if (image.openFile (fileName))
        {
        String display = " [ Open File: " + fileName + " ] ";
        openFile.update (display);
        }*/

    }

    /**
     * Takes in a BufferedImage and returns a GreenfootImage.
     * 
     * @param newBi The BufferedImage to convert.
     * 
     * @return GreenfootImage   A GreenfootImage built from the BufferedImage provided.
     */
    public static GreenfootImage createGreenfootImageFromBI (BufferedImage newBi)
    {
        GreenfootImage returnImage = new GreenfootImage (newBi.getWidth(), newBi.getHeight());
        BufferedImage backingImage = returnImage.getAwtImage();
        Graphics2D backingGraphics = (Graphics2D)backingImage.getGraphics();
        backingGraphics.drawImage(newBi, null, 0, 0);

        return returnImage;
    }

    public static BufferedImage deepCopy(BufferedImage bi) {
        ColorModel cm = bi.getColorModel();
        boolean isAlphaPremultip = cm.isAlphaPremultiplied();
        WritableRaster raster = bi.copyData(null);
        return new BufferedImage(cm, raster, isAlphaPremultip, null);
    }

}

