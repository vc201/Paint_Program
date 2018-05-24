// MyShape.java

// Imports modules that will be used
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GradientPaint;

/*
 * This abstract class is the direct superclass for MyLine and MyBoundedShape. It is also the indirect
 * superclass for MyOval and MyLine. This class contains protected methods that initialize coordinate
 * and colour values, whih are common to every shape (lines, rectangles, and ovals).
 */
abstract class MyShape
{
  
  // private variables that all shapes have in common
  private int x1;
  private int y1;
  private int x2;
  private int y2;
  private int strokeWidth;
  private boolean dashed;
  private int dashLength;
  private GradientPaint colour; 
  
  // empty constructor
  public MyShape()
  {
    
    // sets all coordinates to 0 and the shape colour to black
    this (0,0,0,0,1,1, false, new GradientPaint(0,0,Color.black,50,50, Color.black, true));
  }
  
  // constructor with inputs
  public MyShape (int x1, int y1, int x2, int y2, int strokeWidth, int dashLength, boolean dashed, GradientPaint colour)
  {
    
    // sets values for coordinates, colour, and line draw stype
    setX1(x1);
    setY1(y1);
    setX2(x2);
    setY2(y2);
    setDashed(dashed);
    setDashLength(dashLength);
    setStrokeWidth(strokeWidth);
    setColour(colour);
  }
  
  // mutator and accessor methods for coordinates and colour
  
  // Sets the first x co-ordinate of the shape
  protected void setX1(int x1)
  {
    this.x1 = x1; 
  }
  
  // Gets the first x co-ordinate of the shape
  protected int getX1()
  {
    return x1; 
  }
  
  // Sets the first y co-ordinate of the shape
  protected void setY1(int y1)
  {
    this.y1 = y1; 
  }
  
  // Gets the first y co-ordinate of the shape
  protected int getY1()
  {
    return y1; 
  }
  
  // Sets the final x co-ordinate of the shape
  protected void setX2(int x2)
  {
    this.x2 = x2; 
  }
  
  // Gets the final x co-ordinate of the shape
  protected int getX2()
  {
    return x2; 
  }
  
  // Sets the final y co-ordinate of the shape
  protected void setY2(int y2)
  {
    this.y2 = y2; 
  }
  
  // Gets the final y co-ordinate of the shape
  protected int getY2()
  {
    return y2; 
  }
  
  // Sets the shape colour
  protected void setColour(GradientPaint colour)
  {
    this.colour = colour; 
  }
  
  // Gets the shape colour
  protected GradientPaint getColour()
  {
    return colour; 
  }
  
  // Sets the stroke width
  protected void setStrokeWidth(int width)
  {
      strokeWidth = width;
  }
  
  // Gets the stroke width
  protected int getStrokeWidth()
  {
      return strokeWidth;
  }
  
  // Sets the dash length
  protected void setDashLength(int length)
  {
      dashLength = length;
  }
  
  // Gets the dash length
  protected int getDashLength()
  {
      return dashLength;
  }
  
  // Sets the variable to determine wheter to dash lines or not
  protected void setDashed(boolean dashed)
  {
      this.dashed = dashed;
  }
  
  // Gets the variable to determine wheter to dash lines or not
  protected boolean getDashed()
  {
      return dashed;
  }
  
  // abstract method required in all subclasses to draw the shape
  public abstract void draw(Graphics2D g);
} // end class MyShape