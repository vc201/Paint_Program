// MyLine.java

// Imports modules that will be used
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.BasicStroke;
import java.awt.GradientPaint;
import java.awt.geom.Line2D;

// creates an abstract class which inherits from MyShape. This class is used to create lines. 
public class MyLine extends MyShape
{   
    private static int numberOfLines = 0; // Number of objects in memory
    
    // constructor with input values
    public MyLine( int x1, int y1, int x2, int y2, int strokeWidth, boolean dashed, int dashLength, GradientPaint colour )
    {
        
        // Constructor for MyShape superclass that initializes values
        super(x1, y1, x2, y2, strokeWidth, dashLength, dashed, colour);
        
        // increases count when shape object is created
        numberOfLines++;
    }
    
    // empty constructor
    public MyLine()
    {
        
        // empty MyShape constructor
        super(); 
        
        // increases count when shape object is created
        numberOfLines++;
    }
    
    // Method that returns the total number of lines created
    public static int countShapes()
    {
        return numberOfLines; 
    }
    
    // Actually draws the line
    public void draw( Graphics2D g )
    {
        // Determines draw style based on dashed boolean value from MyShape   
        if (getDashed())
        {
            float dashes[] = { getDashLength() };
            g.setStroke( new BasicStroke( getStrokeWidth(), BasicStroke.CAP_ROUND,
                                         BasicStroke.JOIN_ROUND, 10, dashes , 0 ) ); 
        }
        
        else
            g.setStroke( new BasicStroke( getStrokeWidth(), BasicStroke.CAP_ROUND,
                                         BasicStroke.JOIN_ROUND) ); 
        
        // Sets the colour for the object to be drawn
        g.setPaint( getColour() );
        g.draw(new Line2D.Double( getX1(), getY1(), getX2(), getY2() ));
    }
} // end class MyLine