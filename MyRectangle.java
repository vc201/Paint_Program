// MyRectange.java

// Imports modules that will be used
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.BasicStroke;
import java.awt.GradientPaint;
import java.awt.geom.Rectangle2D;

public class MyRectangle extends MyBoundedShape
{
    private static int numberOfRectangles = 0; // Number of objects in memory
    
    // constructor with input values
    public MyRectangle( int x1, int y1, int x2, int y2, int strokeWidth, boolean dashed, int dashLength, GradientPaint colour, boolean filled )
    {
        
        // Constructor for MyShape superclass that initializes values
        super(x1, y1, x2, y2, strokeWidth, dashed, dashLength, colour, filled);
        
        // increases count when shape object is created
        numberOfRectangles++;
    } // end MyRectangle constructor
    
    // empty constructor
    public MyRectangle()
    {
        
        // empty MyShape constructor
        super();
        
        // increases count when shape object is created
        numberOfRectangles++;
    } // end MyRectangle constructor  
    
    public static int countShapes()
    {
        return numberOfRectangles; 
    }
    
    // Actually draws the rectangle
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
        if (getFilled())
            g.fill(new Rectangle2D.Double( getUpperLeftX(), getUpperLeftY(), getWidth(), getHeight() ));      
        else
            g.draw(new Rectangle2D.Double( getUpperLeftX(), getUpperLeftY(), getWidth(), getHeight() ));
        
    } // end method draw
} // end class MyRectangle