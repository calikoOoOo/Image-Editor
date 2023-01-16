import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * A Generic Button to display text that is clickable. Owned by a World, which controls click
 * capturing. Can also be used as a simple field to display text (set clickable to false).
 * 
 * Massively improved over the previous version! Now modular, supports highlighting, etc. To implement
 * highlighting, ensure that this is subclassed to Control.
 * 
 * @author Calista Kurniawan
 * @version 2022
 */
public class TextButton extends Control
{
    // Declare private variables

    private String buttonText;

    private int padding;
    private int highlightPadding;
    private boolean fixedWidthEnabled;
    private boolean active; // ability to set and store inactive state
    private int fixedWidth;

    private Color textColor;
    private Color highlightedTextColor;
    private Color backColor;
    private Color highlightedBackColor;
    private Color disabledColor;
    private Color borderColor;

    private Font font;

    /**
     * Construct a TextButton with a given String at the default size
     * 
     * @param text  String value to display
     * 
     */
    public TextButton (String text)
    {
        this(text, new Font ("Comic Sans MS", false, false, 16));
        active = true;
    }

    
    
    /**
     * Construct a TextButton with a given String and a specified size
     * 
     * @param text  String value to display
     * @param textSize  size of text, as an integer
     * 
     */
    public TextButton (String text, Font font)
    {
        this (text, 8, true, font);
    }

    
    public TextButton (String text, int padding, boolean clickable, Font font){
        this (text,  padding, 0, clickable, new Color (40, 40, 250), new Color (80, 80, 250), new Color (245, 245, 245), new Color (255, 255, 255), Color.BLACK, font);  
    }

    /**
     * Primary constructor for objects of class TextButton - the most customizable version!
     * 
     * @param text
     * @param textSize
     * @param width         Width of desired button. Use 0 to automatically size around text.
     */
    public TextButton (String text, int padding, int width, boolean clickable, Color textColor, Color highlightedTextColor, Color backColor, Color highlightedBackColor, Color borderColor, Font font){

        this.textColor = textColor;
        this.highlightedTextColor = highlightedTextColor;
        this.backColor = backColor;
        this.highlightedBackColor = highlightedBackColor;
        this.borderColor = borderColor;

        this.font = font;
        
        disabledColor = new Color (100, 100, 100); // Grey

        fixedWidth = width;
        if (width == 0){
            fixedWidthEnabled = false;
        } else {
            fixedWidthEnabled = true; 
        }

        this.clickable = clickable;
        this.padding = padding;
        
        highlightPadding = padding + 2;
        buttonText = text;
        active = true;

        update();
        setImage(myImage);
    }

    public void addedToWorld (World w){
        super.addedToWorld (w);
        update();
    }

    public void setFixedWidth (int width){
        fixedWidthEnabled = true;
        fixedWidth = width;
        update();
    }

    public void setActive (boolean active)
    {
        this.active = active;
        update();
    }

    public boolean isActive (){
        return active;
    }

    public int getPadding (){
        return padding;
    }

    /**
     * This update method will change the displayed text.
     * 
     * @param   text    The text to display, as a String
     */
    public void update (String text){
        buttonText = text;
        update();
    }

    public void setColor (Color text, Color highlighted){
        this.textColor = text;
        this.highlightedTextColor = highlighted;
        update();
    }

    public void setFont (Font font){
        this.font = font;
        update();
    }

    /**
     * This primary update method will redraw this Actor's image based on current variable values
     */
    public void update ()
    {

        if (active)
            myImage = drawTextBox(buttonText, textColor, backColor, borderColor, padding);
        else
            myImage = drawTextBox(buttonText, disabledColor, backColor, Color.BLACK, padding);
        if (clickable)
            highlightedImage = drawTextBox (buttonText, highlightedTextColor, backColor, Color.BLACK, highlightPadding);
        else
            highlightedImage = null;

        if (clickable && mouseOver){
            setImage(highlightedImage);
        } else {
            setImage (myImage);
        }
    }

    /**
     * 
     * Draw a text box.
     * 
     */
    public GreenfootImage drawTextBox (String text, Color color, Color back, Color border, int pad){
        int height = font.getSize() + (3 * pad);
        int width;
        int textWidth = getStringWidth(font, text);
        if (fixedWidthEnabled){           
            width = fixedWidth;
        } else {
            width = textWidth + (pad * 2);//(font.getSize() * text.length()) + (pad * 2);
        }
        // Ensure an even width to keep text properly centered
        if (width % 2 == 1){
            width++;
        }

        // generate an image of the appropriate size
        GreenfootImage resultImage = new GreenfootImage(width, height);
        resultImage.setFont(font);

        // fill in the background colour
        resultImage.setColor (back);
        resultImage.fill();

        // Draw the border
        resultImage.setColor (border); 
        resultImage.drawRect (0, 0, width-1, height-1);

        // Draw the text
        resultImage.setColor (color);
        resultImage.drawString(text, (width - textWidth)/2, resultImage.getHeight() - pad * 2);//pad + font.getSize() + 1); // in y: +1 because border
        
        // Done!
        return resultImage;
    }

}
