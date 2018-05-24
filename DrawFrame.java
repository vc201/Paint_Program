// DrawFrame.java

// Imports modules that will be used
import java.awt.BorderLayout;
import java.awt.Color;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;

/*
 * This class is is used to put the paint program into internal frames.
 * Methods here are directly linked to the paint program's methods.
 * 
 * Date: June 1, 2012
 */
public class DrawFrame extends JInternalFrame
{
    //Instance Variable
    
    // Paint program
    DrawPanel panel;
    
    // Constructor
    public DrawFrame(JLabel statusLabel)
    {
        // Initializes parent class's constructor (used to create program title)
        super("", true, true, true, true);
        
        // Creates main program layout and sets it
        setLayout(new BorderLayout(5,5));
        
        panel = new DrawPanel(statusLabel);
        
        add(panel, BorderLayout.CENTER);
    } // End DrawFrame constructor
    
    // Links to DrawPanel's method with the exact same name and parameters
    public void setCurrentShapeType(int type) 
    {
        panel.setCurrentShapeType(type);
    }
    
    // Links to DrawPanel's method with the exact same name and parameters
    public void setCurrentShapeColor(Color colour, Color colour2)
    {
        panel.setCurrentShapeColor(colour, colour2);
    }
    
    // Links to DrawPanel's method with the exact same name and parameters
    public void setCurrentShapeFilled(boolean isFilled)
    {
        panel.setCurrentShapeFilled(isFilled);
    }
    
    // Links to DrawPanel's method with the exact same name and parameters
    public void setStrokeWidth(int width)
    {
        panel.setStrokeWidth(width);
    }
    
    // Links to DrawPanel's method with the exact same name and parameters
    public void setDashLength(int length)
    {
        panel.setDashLength(length);
    }
    
    // Links to DrawPanel's method with the exact same name and parameters
    public void setDashed(boolean dashed)
    {
        panel.setDashed(dashed);
    }
    
    // Links to DrawPanel's method with the exact same name and parameters
    public void setGradient(boolean gradient)
    {
        panel.setGradient(gradient);
    }
    
    // Links to DrawPanel's method with the exact same name and parameters
    public void clearLastShape()
    {
        panel.clearLastShape();
    }
    
    // Links to DrawPanel's method with the exact same name and parameters
    public void undoClearLastShape()
    {
        panel.undoClearLastShape();
    }
    
    // Links to DrawPanel's method with the exact same name and parameters
    public void clearDrawing()
    {
        panel.clearDrawing();
    }
} // End class DrawFrame
