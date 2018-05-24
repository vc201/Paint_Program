// ReadPreferences.java

// Imports modules that will be used
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.InputMismatchException;
import java.awt.Color;
import java.lang.NumberFormatException;

/*
 * This class is used to parse user preferences from a file if valid and avaliable
 * 
 * Date: June 1, 2012
 */
public class ReadPreferences
{    
    // Instance variables used to store parsed preferences
    // These variables hold values that are the same as those used by the main paint program
    
    private int strokeWidth;
    private int dashLength;
    private int currentShapeType;
    
    private Color colour;
    private Color colour2;
    
    private boolean isGradient;
    private boolean isDashed;
    private boolean isFilled;
    private boolean isValidFile;
    
    // Constructor
    public ReadPreferences()
    {
        // Initially sets file to valid but may change to false later
        isValidFile = true;
        
        // Used to parse all preferences from the file
        try
        {
            // Scanner object used to parse file
            Scanner file = new Scanner(new FileInputStream("prefs.txt"));
            
            // Colour prefences
            colour = new Color(file.nextInt(), file.nextInt(), file.nextInt());
            colour2 = new Color(file.nextInt(), file.nextInt(), file.nextInt());
            
            // For gradient
            if (file.nextInt() == 1)
                isGradient = true;
            else
                isGradient = false;
            
            // For stroke width
            strokeWidth = file.nextInt();
            
            // For dashed lines
            if (file.nextInt() == 1)
                isDashed = true;
            else
                isDashed = false;
            
            // For dash length
            dashLength = file.nextInt();
            
            // For shape type
            currentShapeType = file.nextInt();
            
            // For shape fill
            if (file.nextInt() == 1)
                isFilled = true;
            else
                isFilled = false;
            
            // Close the file
            file.close();            
        }
        
        // If file is not found
        catch (FileNotFoundException e)
        {
            
            // Set file state to invalid
            isValidFile = false;
        }
        
        // If error reading required input from file
        catch (InputMismatchException e)
        {
            // Set file state to invalid
            isValidFile = false;
        }
    }
    
    // Accessor methods all used to return user preferences and to validate the preferences file
    
    // Preference file validation
    public boolean isValidFile()
    {
        return isValidFile;
    }
    
    // Rest of the methods are used just to return user preferences so they can be used
    
    // First colour
    public Color getColour()
    {
        return colour;
    }
    
    // Second colour
    public Color getColour2()
    {
        return colour2;
    }
    
    // Gradient
    public boolean getIsGradient()
    {
        return isGradient;
    }
    
    // Stroke width
    public int getStrokeWidth()
    {
        return strokeWidth;
    }
    
    // Dashes
    public boolean getIsDashed()
    {
        return isDashed;
    }
    
    // Dash length
    public int getDashLength()
    {
        return dashLength;
    }
    
    // Shape type
    public int getCurrentShapeType()
    {
        return currentShapeType;
    }
    
    // Fill attribute
    public boolean getIsFilled()
    {
        return isFilled;
    }
} // end class ReadPreferences
        
