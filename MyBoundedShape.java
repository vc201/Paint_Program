// MyBoundedShape.java

// Imports modules that will be used
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GradientPaint;

/* creates an abstract class which inherits from MyShape and will be used to as a direct superclass for
 * MyOval and MyRectangle classes. This class contains protected methods which both the MyOval and
 * MyRectangle classes will use. 
 */
abstract class MyBoundedShape extends MyShape
{
   private boolean filled; // private variable to determine whether to fill in a shape or not
   
   // constructor initializes the variables that will be used in MyOval and MyRectangle
   public MyBoundedShape(int x1, int y1, int x2, int y2, int strokeWidth, boolean dashed, int dashLength, GradientPaint colour, Boolean filled)
   {
     
    // Constructor for MyShape superclass that initializes values
    super(x1, y1, x2, y2, strokeWidth, dashLength, dashed, colour);
    setFilled(filled); // sets the fill shape value
   }
   
   // empty constructor
   public MyBoundedShape()
   {
     
    // Empty MyShape constructor
    super();
    setFilled(false); // sets the filled value to not filled    
   }
   
   // mutator and accessor methods for coordinates, colour, and filled state
   
   // coordinates for ovals and rectangles are a little different from that of lines
   // so when they are created, the coordinates that will be used are from here and
   // not the ones from myShape
   
   // Sets variable that determines whether to draw a filled shape or not
   protected void setFilled(boolean filled)
   {
    this.filled = filled; 
   }
   
   // Gets variable that determines whether to draw a filled shape or not
   protected boolean getFilled()
   {
    return filled; 
   }
   
   // Gets first x co-ordinate
   protected int getUpperLeftX()
   {
     return Math.min(getX1(),getX2());
   }
   
   // Gets first y co-ordinate
   protected int getUpperLeftY()
   {
     return Math.min(getY1(),getY2());
   }
   
   // Gets second x co-ordinate (width)
   protected int getWidth()
   {
     return Math.abs(getX1()-getX2());
   }
   
   // Gets second y co-ordinate (height)
   protected int getHeight()
   {
     return Math.abs(getY1()-getY2());
   }
   
   // Abstract method required in all subclasses to draw the shape
   public abstract void draw(Graphics2D g);
} // End class MyBoundedShape