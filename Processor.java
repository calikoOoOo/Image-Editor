/**
 * Starter code for Processor - the class that processes images.
 * <p> This class manipulated Java BufferedImages, which are effectively 2d arrays
 * of pixels. Each pixel is a single integer packed with 4 values inside it.</p>
 * 
 * <p>All methods added to this class should be static. In other words, you do not
 *    have to instantiate (create an object of) this class to use it - simply call
 *    the methods with Processor.methodName and pass a GreenfootImage to be manipulated.
 *    Note that you do not have to return the processed image, as you will be passing a
 *    reference to your image, and it will be altered by the method, as seen in the Blueify
 *    example.</p>
 *    
 * <p>Some methods, especially methods that change the size of the image (such as rotation
 *    or scaling) may require a GreenfootImage return type. This is because while it is
 *    possible to alter an image passed as a parameter, it is not possible to re-instantiate it, 
 *    which is required to change the size of a GreenfootImage</p>
 * 
 * <p>
 * I have included two useful methods for dealing with bit-shift operators so
 * you don't have to. These methods are unpackPixel() and packagePixel() and do
 * exactly what they say - extract red, green, blue and alpha values out of an
 * int, and put the same four integers back into a special packed integer. </p>
 * 
 * @author Calista Kurniawan
 * @version 2022
 */

import java.awt.image.BufferedImage;
import java.awt.Graphics2D;
import greenfoot.*;

import java.awt.image.BufferedImageOp;
import java.awt.image.ConvolveOp;
import java.awt.image.Kernel;

public class Processor  
{
    /**
     * Example colour altering method by Mr. Cohen. This method will
     * increase the blue value while reducing the red and green values.
     * 
     * Demonstrates use of packagePixel() and unpackPixel() methods.
     * 
     * @param bi    The BufferedImage (passed by reference) to change.
     */
    public static BufferedImage blueify (BufferedImage bi)
    {
        // Get image size to use in for loops
        int xSize = bi.getWidth();
        int ySize = bi.getHeight();
        
        BufferedImage newBi = new BufferedImage (xSize, ySize, 3);
        
        // Using array size as limit
        for (int y = 0; y < ySize; y++)
        {
            for (int x = 0; x < xSize; x++)
            {
                // Calls method in BufferedImage that returns R G B and alpha values
                // encoded together in an integer
                int rgba = bi.getRGB(x, y);
                
                // Call the unpackPixel method to retrieve the four integers for
                // R, G, B and alpha and assign them each to their own integer
                int[] rgbValues = unpackPixel (rgba);

                int alpha = rgbValues[0]; // 0-255
                int red = rgbValues[1];
                int green = rgbValues[2];
                int blue = rgbValues[3];

                // make the pic BLUE-er
                if (blue < 253)
                    blue += 2;
                if (red >= 50)
                    red--;
                if (green >= 50)
                    green--;

                int newColour = packagePixel (red, green, blue, alpha);
                newBi.setRGB (x, y, newColour);
            }
        }
        return newBi;
    }

    public static BufferedImage rotate90Clockwise (BufferedImage bi){
        BufferedImage newBi = new BufferedImage (bi.getHeight(), bi.getWidth(), 3);

        return newBi;
    }

    public static BufferedImage flipHorizontal (BufferedImage bi){
        int xSize = bi.getWidth();
        int ySize = bi.getHeight();

        // Temp image, to store pixels as we reverse everything
        BufferedImage newBi = new BufferedImage (xSize, ySize, 3);
       
        for (int y = 0; y < ySize; y++){
            for (int x = 0; x < xSize/2; x++){
                int rgba1 = bi.getRGB(x, y);
                int rgba2 = bi.getRGB(xSize - x - 1, y);
               
                int[] rgbVal1 = unpackPixel(rgba1);
                int[] rgbVal2 = unpackPixel(rgba2);
               
                int alpha1 = rgbVal1[0];
                int red1 = rgbVal1[1];
                int green1 = rgbVal1[2];
                int blue1 = rgbVal1[3];
               
                int newColour1 = packagePixel(red1, green1, blue1, alpha1);
               
                int alpha2 = rgbVal2[0];
                int red2 = rgbVal2[1];
                int green2 = rgbVal2[2];
                int blue2 = rgbVal2[3];
               
                int newColour2 = packagePixel(red2, green2, blue2, alpha2);
                newBi.setRGB(x, y, newColour2);
                newBi.setRGB(xSize - 1 - x, y, newColour1);
            }
        }
        return newBi;
        
        /**
         * INSERT TWO LOOPS HERE:
         * - FIRST LOOP MOVES DATA INTO A SECOND, TEMPORARY IMAGE WITH PIXELS FLIPPED
         *   HORIZONTALLY
         * - SECOND LOOP MOVES DATA BACK INTO ORIGINAL IMAGE
         *
         * Note: Due to a limitation in Greenfoot, you can get the backing BufferedImage from
         *       a GreenfootImage, but you cannot set or create a GreenfootImage based on a
         *       BufferedImage. So, you have to use a temporary BufferedImage (as declared for
         *       you, above) and then copy it, pixel by pixel, back to the original image.
         *       Changes to bi in this method will affect the GreenfootImage automatically.
         */

    }
    
    public static BufferedImage flipVertical (BufferedImage bi){
        int xSize = bi.getWidth();
        int ySize = bi.getHeight();
        
        BufferedImage newBi = new BufferedImage (xSize, ySize, 3);
        
        for (int x = 0; x < xSize; x++){
            for (int y = 0; y < ySize/2; y++){
                int rgba1 = bi.getRGB(x, y);
                int rgba2 = bi.getRGB(x, ySize - 1 - y);
                
                newBi.setRGB(x, y, rgba2);
                newBi.setRGB(x, ySize - 1 - y,rgba1);
            }
            if (ySize % 2 == 1){
                newBi.setRGB(x, ySize/2, bi.getRGB(x, ySize/2));
            }
        }
        return newBi;
    }
    
    public static BufferedImage sepia(BufferedImage bi){
        int xSize = bi.getWidth();
        int ySize = bi.getHeight();
        
        BufferedImage newBi = new BufferedImage (xSize, ySize, 3);
        
        for (int y = 0; y < ySize; y++)
        {
            for (int x = 0; x < xSize; x++)
            {
                // Calls method in BufferedImage that returns R G B and alpha values
                // encoded together in an integer
                int rgba = bi.getRGB(x, y);
                
                // Call the unpackPixel method to retrieve the four integers for
                // R, G, B and alpha and assign them each to their own integer
                int[] rgbValues = unpackPixel (rgba);

                int alpha = rgbValues[0]; // 0-255
                int red = rgbValues[1];
                int green = rgbValues[2];
                int blue = rgbValues[3];
                
                
                int newRed = (int)(0.393 * red + 0.769 * green + 0.189 * blue);
                int newGreen = (int)(0.349 * red + 0.686 * green + 0.168 * blue);
                int newBlue = (int)(0.272 * red + 0.534 * green + 0.131 * blue);
                                    
                // make the pic sepia
                    if (newRed > 255)
                    red = 255;
                else
                    red = newRed;
  
                if (newGreen > 255)
                    green = 255;
                else
                    green = newGreen;
  
                if (newBlue > 255)
                    blue = 255;
                else
                    blue = newBlue;
                    
                int newColour = packagePixel (red, green, blue, alpha);
                newBi.setRGB (x, y, newColour);
            }
        }
        
        return newBi;
    }
    
    public static BufferedImage negative(BufferedImage bi){
        int xSize = bi.getWidth();
        int ySize = bi.getHeight();
        
        BufferedImage newBi = new BufferedImage (xSize, ySize, 3);
        
        for (int y = 0; y < ySize; y++)
        {
            for (int x = 0; x < xSize; x++)
            {
                // Calls method in BufferedImage that returns R G B and alpha values
                // encoded together in an integer
                int rgba = bi.getRGB(x, y);
                
                // Call the unpackPixel method to retrieve the four integers for
                // R, G, B and alpha and assign them each to their own integer
                int[] rgbValues = unpackPixel (rgba);

                int alpha = rgbValues[0]; // 0-255
                int red = rgbValues[1];
                int green = rgbValues[2];
                int blue = rgbValues[3];
                
                
                int newRed = 255 - red;
                int newGreen = 255 - green;
                int newBlue = 255 - blue;
                                    
                // make the pic sepia
                    if (newRed > 255)
                    red = 255;
                else
                    red = newRed;
  
                if (newGreen > 255)
                    green = 255;
                else
                    green = newGreen;
  
                if (newBlue > 255)
                    blue = 255;
                else
                    blue = newBlue;
                    
                int newColour = packagePixel (red, green, blue, alpha);
                newBi.setRGB (x, y, newColour);
            }
        }
        return newBi;
    }
    
    public static BufferedImage greyScale(BufferedImage bi){
        int xSize = bi.getWidth();
        int ySize = bi.getHeight();
        
        BufferedImage newBi = new BufferedImage (xSize, ySize, 3);
        
        for (int y = 0; y < ySize; y++)
        {
            for (int x = 0; x < xSize; x++)
            {
                // Calls method in BufferedImage that returns R G B and alpha values
                // encoded together in an integer
                int rgba = bi.getRGB(x, y);
                
                // Call the unpackPixel method to retrieve the four integers for
                // R, G, B and alpha and assign them each to their own integer
                int[] rgbValues = unpackPixel (rgba);

                int alpha = rgbValues[0]; // 0-255
                int red = rgbValues[1];
                int green = rgbValues[2];
                int blue = rgbValues[3];
                
                int avg = (red + blue + green) / 3;
                
                                  
                // make the pic sepia
                    if (avg > 255)
                    red = 255;
                else
                    red = avg;
  
                if (avg > 255)
                    green = 255;
                else
                    green = avg;
  
                if (avg > 255)
                    blue = 255;
                else
                    blue = avg;
                    
                int newColour = packagePixel (red, green, blue, alpha);
                newBi.setRGB (x, y, newColour);
            }
        }
        
        return newBi;
    }
    
    public static BufferedImage warmer(BufferedImage bi){
        int xSize = bi.getWidth();
        int ySize = bi.getHeight();
        
        BufferedImage newBi = new BufferedImage (xSize, ySize, 3);
        
        for (int y = 0; y < ySize; y++)
        {
            for (int x = 0; x < xSize; x++)
            {
                // Calls method in BufferedImage that returns R G B and alpha values
                // encoded together in an integer
                int rgba = bi.getRGB(x, y);
                
                // Call the unpackPixel method to retrieve the four integers for
                // R, G, B and alpha and assign them each to their own integer
                int[] rgbValues = unpackPixel (rgba);

                int alpha = rgbValues[0]; // 0-255
                int red = rgbValues[1];
                int green = rgbValues[2];
                int blue = rgbValues[3];
                
                
                int newRed = Math.min(240, red + 6);
                int newGreen = green;
                int newBlue = Math.max(10, blue - 6);
                                    
                // make the pic sepia
                    if (newRed > 255)
                    red = 255;
                else
                    red = newRed;
  
                if (newGreen > 255)
                    green = 255;
                else
                    green = newGreen;
  
                if (newBlue > 255)
                    blue = 255;
                else
                    blue = newBlue;
                    
                int newColour = packagePixel (red, green, blue, alpha);
                newBi.setRGB (x, y, newColour);
            }
        }
        return newBi;
    }
    
    public static BufferedImage cooler(BufferedImage bi){
        int xSize = bi.getWidth();
        int ySize = bi.getHeight();
        
        BufferedImage newBi = new BufferedImage (xSize, ySize, 3);
        
        for (int y = 0; y < ySize; y++)
        {
            for (int x = 0; x < xSize; x++)
            {
                // Calls method in BufferedImage that returns R G B and alpha values
                // encoded together in an integer
                int rgba = bi.getRGB(x, y);
                
                // Call the unpackPixel method to retrieve the four integers for
                // R, G, B and alpha and assign them each to their own integer
                int[] rgbValues = unpackPixel (rgba);

                int alpha = rgbValues[0]; // 0-255
                int red = rgbValues[1];
                int green = rgbValues[2];
                int blue = rgbValues[3];
                
                
                int newRed = red;
                int newGreen = Math.max(10, green - 6);
                int newBlue = Math.min(240, blue + 6);
                
                                    
                // make the pic sepia
                    if (newRed > 255)
                    red = 255;
                else
                    red = newRed;
  
                if (newGreen > 255)
                    green = 255;
                else
                    green = newGreen;
  
                if (newBlue > 255)
                    blue = 255;
                else
                    blue = newBlue;
                    
                int newColour = packagePixel (red, green, blue, alpha);
                newBi.setRGB (x, y, newColour);
            }
        }
        return newBi;
    }
    
    public static BufferedImage redify (BufferedImage bi)
    {
        // Get image size to use in for loops
        int xSize = bi.getWidth();
        int ySize = bi.getHeight();
        
        BufferedImage newBi = new BufferedImage (xSize, ySize, 3);
        
        // Using array size as limit
        for (int y = 0; y < ySize; y++)
        {
            for (int x = 0; x < xSize; x++)
            {
                // Calls method in BufferedImage that returns R G B and alpha values
                // encoded together in an integer
                int rgba = bi.getRGB(x, y);
                
                // Call the unpackPixel method to retrieve the four integers for
                // R, G, B and alpha and assign them each to their own integer
                int[] rgbValues = unpackPixel (rgba);

                int alpha = rgbValues[0]; // 0-255
                int red = rgbValues[1];
                int green = rgbValues[2];
                int blue = rgbValues[3];

                // make the pic BLUE-er
                if (red < 253)
                    red += 2;
                if (blue >= 50)
                    blue -= 2;
                if (green >= 50)
                    green -= 2;

                int newColour = packagePixel (red, green, blue, alpha);
                newBi.setRGB (x, y, newColour);
            }
        }
        return newBi;
    }
    
    public static BufferedImage greenify (BufferedImage bi)
    {
        // Get image size to use in for loops
        int xSize = bi.getWidth();
        int ySize = bi.getHeight();
        
        BufferedImage newBi = new BufferedImage (xSize, ySize, 3);
        
        // Using array size as limit
        for (int y = 0; y < ySize; y++)
        {
            for (int x = 0; x < xSize; x++)
            {
                // Calls method in BufferedImage that returns R G B and alpha values
                // encoded together in an integer
                int rgba = bi.getRGB(x, y);
                
                // Call the unpackPixel method to retrieve the four integers for
                // R, G, B and alpha and assign them each to their own integer
                int[] rgbValues = unpackPixel (rgba);

                int alpha = rgbValues[0]; // 0-255
                int red = rgbValues[1];
                int green = rgbValues[2];
                int blue = rgbValues[3];

                // make the pic BLUE-er
                if (red >= 50)
                    red -= 2;
                if (blue >= 50)
                    blue -= 2;
                if (green < 253)
                    green += 2;

                int newColour = packagePixel (red, green, blue, alpha);
                newBi.setRGB (x, y, newColour);
            }
        }
        return newBi;
    }
    
    public static BufferedImage yellowify (BufferedImage bi)
    {
        // Get image size to use in for loops
        int xSize = bi.getWidth();
        int ySize = bi.getHeight();
        
        BufferedImage newBi = new BufferedImage (xSize, ySize, 3);
        
        // Using array size as limit
        for (int y = 0; y < ySize; y++)
        {
            for (int x = 0; x < xSize; x++)
            {
                // Calls method in BufferedImage that returns R G B and alpha values
                // encoded together in an integer
                int rgba = bi.getRGB(x, y);
                
                // Call the unpackPixel method to retrieve the four integers for
                // R, G, B and alpha and assign them each to their own integer
                int[] rgbValues = unpackPixel (rgba);

                int alpha = rgbValues[0]; // 0-255
                int red = rgbValues[1];
                int green = rgbValues[2];
                int blue = rgbValues[3];

                // make the pic BLUE-er
                if (red < 253)
                    red += 2;
                if (blue >= 50)
                    blue -= 2;
                if (green < 253)
                    green += 2;

                int newColour = packagePixel (red, green, blue, alpha);
                newBi.setRGB (x, y, newColour);
            }
        }
        return newBi;
    }
    
    public static BufferedImage magentify (BufferedImage bi)
    {
        // Get image size to use in for loops
        int xSize = bi.getWidth();
        int ySize = bi.getHeight();
        
        BufferedImage newBi = new BufferedImage (xSize, ySize, 3);
        
        // Using array size as limit
        for (int y = 0; y < ySize; y++)
        {
            for (int x = 0; x < xSize; x++)
            {
                // Calls method in BufferedImage that returns R G B and alpha values
                // encoded together in an integer
                int rgba = bi.getRGB(x, y);
                
                // Call the unpackPixel method to retrieve the four integers for
                // R, G, B and alpha and assign them each to their own integer
                int[] rgbValues = unpackPixel (rgba);

                int alpha = rgbValues[0]; // 0-255
                int red = rgbValues[1];
                int green = rgbValues[2];
                int blue = rgbValues[3];

                // make the pic BLUE-er
                if (red < 253)
                    red += 2;
                if (blue < 253)
                    blue += 2;
                if (green >= 50)
                    green -= 2;

                int newColour = packagePixel (red, green, blue, alpha);
                newBi.setRGB (x, y, newColour);
            }
        }
        return newBi;
    }
    
    public static BufferedImage cyanify (BufferedImage bi)
    {
        // Get image size to use in for loops
        int xSize = bi.getWidth();
        int ySize = bi.getHeight();
        
        BufferedImage newBi = new BufferedImage (xSize, ySize, 3);
        
        // Using array size as limit
        for (int y = 0; y < ySize; y++)
        {
            for (int x = 0; x < xSize; x++)
            {
                // Calls method in BufferedImage that returns R G B and alpha values
                // encoded together in an integer
                int rgba = bi.getRGB(x, y);
                
                // Call the unpackPixel method to retrieve the four integers for
                // R, G, B and alpha and assign them each to their own integer
                int[] rgbValues = unpackPixel (rgba);

                int alpha = rgbValues[0]; // 0-255
                int red = rgbValues[1];
                int green = rgbValues[2];
                int blue = rgbValues[3];

                // make the pic BLUE-er
                if (red >= 50)
                    red -= 2;
                if (blue < 253)
                    blue += 2;
                if (green < 253)
                    green += 2;

                int newColour = packagePixel (red, green, blue, alpha);
                newBi.setRGB (x, y, newColour);
            }
        }
        return newBi;
    }
    
    public static BufferedImage rotateNinetyCW (BufferedImage bi){
        int xSize = bi.getWidth();
        int ySize = bi.getHeight();
        
        BufferedImage newBi = new BufferedImage (ySize, xSize, 3);
        
        for (int y = 0; y < ySize; y++){
            for (int x = 0; x < xSize; x++){
                int rgba = bi.getRGB(x, y);
                newBi.setRGB(ySize - y - 1, x, rgba);
            }
        }
        return newBi;
    }
    
    public static BufferedImage rotateOneEightyCW (BufferedImage bi){
        return rotateNinetyCW(rotateNinetyCW(bi));
    }
    
    public static BufferedImage rotateNinetyCCW (BufferedImage bi){
        return rotateNinetyCW(rotateNinetyCW(rotateNinetyCW(bi)));
    }
    
    
    public static BufferedImage blur (BufferedImage bi){
        int xSize = bi.getWidth();
        int ySize = bi.getHeight();
        
        BufferedImage newBi = new BufferedImage (xSize, ySize, 3);
        
        Kernel kernel = new Kernel(3, 3, new float[] { 1f / 9f, 1f / 9f, 1f / 9f, 1f / 9f, 1f / 9f, 1f / 9f, 1f / 9f, 1f / 9f, 1f / 9f });
        BufferedImageOp op = new ConvolveOp(kernel);
        newBi = op.filter(bi, null);
        
        return newBi;
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

    /**
     * Takes in an rgb value - the kind that is returned from BufferedImage's
     * getRGB() method - and returns 4 integers for easy manipulation.
     * 
     * By Jordan Cohen
     * Version 0.2
     * 
     * @param rgbaValue The value of a single pixel as an integer, representing<br>
     *                  8 bits for red, green and blue and 8 bits for alpha:<br>
     *                  <pre>alpha   red     green   blue</pre>
     *                  <pre>00000000000000000000000000000000</pre>
     * @return int[4]   Array containing 4 shorter ints<br>
     *                  <pre>0       1       2       3</pre>
     *                  <pre>alpha   red     green   blue</pre>
     */
    public static int[] unpackPixel (int rgbaValue)
    {
        int[] unpackedValues = new int[4];
        // alpha
        unpackedValues[0] = (rgbaValue >> 24) & 0xFF;
        // red
        unpackedValues[1] = (rgbaValue >> 16) & 0xFF;
        // green
        unpackedValues[2] = (rgbaValue >>  8) & 0xFF;
        // blue
        unpackedValues[3] = (rgbaValue) & 0xFF;

        return unpackedValues;
    }

    /**
     * Takes in a red, green, blue and alpha integer and uses bit-shifting
     * to package all of the data into a single integer.
     * 
     * @param   int red value (0-255)
     * @param   int green value (0-255)
     * @param   int blue value (0-255)
     * @param   int alpha value (0-255)
     * 
     * @return int  Integer representing 32 bit integer pixel ready
     *              for BufferedImage
     */
    public static int packagePixel (int r, int g, int b, int a)
    {
        int newRGB = (a << 24) | (r << 16) | (g << 8) | b;
        return newRGB;
    }
}
