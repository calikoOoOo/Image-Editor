import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
import java.awt.image.BufferedImage;
import java.io.FileNotFoundException;

/**
 * <p>ImageHolder contains two images - a full size image and a display image.</p>
 * 
 * <p>Most cameras take photos that are at least 12 "Megapixels," which usually
 * means images that are 4000x3000 pixels or larger. Computer screens are usually 
 * somewhere between "1080p" (1920x1080) and "4K / UHD" (3840x2160). As well as the
 * computer displays being significantly smaller to start with, we also need room for
 * the user interface, the Windows/Mac task bar, etc. So, this class serves to manage
 * one image to actually alter and manipulate, and another to display.</p>
 * 
 * <p>If the image is smaller than the max displayable image size (set by constants in the Background)
 * then the display image and the full image will be the same, but will be processed in the same 
 * manner as if they were different.</p>
 * 
 * <p>When you apply an effect to the image, you must complete three steps as demonstrated in
 * the bluify example:</p>
 * 
 * <ol><li>Call the image manipulation method on getBufferedImage() from this class:<br>
 * <code>Processor.blueify(image.getBufferedImage());</code></li>
 * <li> Call the redraw method, to recalculate scale and update the displayed image:<br>
 * <code>image.redraw();</code>
 * <li> Update the info displayed in the World (optional - this might vary depending on your UI)<br>
 * <code>openFile.update (image.getDetails ());</li></ol>
 * 
 * @author Calista Kurniawan
 * @version 2022
 */
public class ImageHolder extends Actor
{
    // The two image objects
    private GreenfootImage displayImage; // image to display 
    private GreenfootImage fullImage; // image to process

    private double scale;
    private String openFileName;

    /**
     * Construct an ImageHolder with a file name. If there is an error, 
     * show a blank GreenfootImage.
     * 
     * @param fileName  Name of image file to be displayed.
     */
    public ImageHolder (String fileName)
    {
        openFile (fileName, fileName);
    }

    /**
     * Attempt to open a file and assign it as this Actor's image
     * 
     * @param fileName  Name of the image file to open (must be in this directory)
     * @return boolean  True if operation successful, otherwise false
     */
    public boolean openFile (String path, String fileName)
    {
        try {
            if (fileName != null)
            {
                fullImage = new GreenfootImage (path);
                openFileName = fileName;
            }
            else
                return false;
        }
        catch (IllegalArgumentException e)
        {
            return false;
        }
        redraw ();
        return true;
    }

    /**
     * Get the scale that was used to convert the full size image to the
     * display image. 
     * 
     * @return double   the scale, as a percentage
     */
    public double getScale () {
        return scale;
    }

    /**
     * Get the width of the full size backing image
     * 
     * @return int the actual width of the image, in pixels   
     */
    public int getRealWidth() {
        return fullImage.getWidth();
    }

    /**
     * Get the height of the full size backing image
     * 
     * @return int the actual height of the image, in pixels   
     */
    public int getRealHeight() {
        return fullImage.getHeight();
    }

    /**
     * Return two Strings in an array that contain details about the currently open file.
     * <p>
     * This is what you see in the text box in World. This should be called after redraw() to ensure
     * that the scale is up to date.
     * <p>
     * Note - you are welcome to make additional accessors to get at these details in a different format.
     * 
     * @return String[]     an array containing the file name on the first line and the scale, width and height on the second line
     */
    public String[] getDetails () {
        return new String[]{"File: " + openFileName,"Scale: " + scale + " W: " + fullImage.getWidth() + " H: " + fullImage.getHeight()};
    }

    /**
     * This method will <b>reclaculate scale</b> and <b>update the displayImage</b>. 
     * <p>
     * You should always update the fullImage before calling this method.
     * For example, blueify applies the blue effect and then calls this.
     * <p>
     * Note - this is a fairly new method. If you run into issues with 
     * the scaling algorithm, please let me know.
     */
    public void redraw(){
        // Recalculate scale:
        boolean tooWide = fullImage.getWidth() > Background.MAX_WIDTH;
        boolean tooTall = fullImage.getHeight() > Background.MAX_HEIGHT;
        // Width and Height are too big:
        if (tooWide && tooTall){
            
            double widthRatio = fullImage.getWidth() / (double)Background.MAX_WIDTH;
            double heightRatio = fullImage.getHeight() / (double)Background.MAX_HEIGHT;

            if (widthRatio >= heightRatio){
                scale = 1 / widthRatio;
            }  else {
                scale = 1 / heightRatio;
            }
            
        } else if (tooWide){
            double widthRatio = fullImage.getWidth() / (double)Background.MAX_WIDTH;
            scale = 1 / widthRatio;
        } else if (tooTall){
            double heightRatio = fullImage.getHeight() / (double)Background.MAX_HEIGHT;
            scale = 1 / heightRatio;
        }
        else {
            scale = 1.0;
        }

        displayImage = new GreenfootImage (fullImage);
        // Use scale to resize image proportionally 
        displayImage.scale ((int)((fullImage.getWidth() * scale) + 0.5), (int)((fullImage.getHeight() * scale) + 0.5));

        setImage(displayImage);
    }

    /**
     * Allows access to the full image for modification
     * 
     * @return BufferedImage returns the backing image for this Actor as an AwtImage
     */
    public BufferedImage getBufferedImage ()
    {
        return fullImage.getAwtImage();
    }

    /**
     * Accessor for the (low resolution) display image.
     * 
     * @return GreenfootImage the image being displayed. If there is a larger image that has been scaled, this will be the smaller, lower resolution image.
     */
    public GreenfootImage getDisplayImage (){
        return displayImage;
    }
    
    /**
     * Set a new image to this Image Holder. Provide a full size image and
     * redraw() will also update the scale and displayImage. Note that this
     * is only required when altering the current image is not possible (for example,
     * when rotating the image and thus creating an image of a new size).
     * 
     * @param GreenfootImage the new image to insert into this Image holder. 
     */
    public void setNewImage (GreenfootImage newImage){
        fullImage = new GreenfootImage (newImage);
        redraw();
    }
}
