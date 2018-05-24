// DrawPanel.java

// Imports modules that will be used
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GradientPaint;
import java.awt.event.MouseListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import java.lang.NullPointerException;

/*
 * This class inherits from JPanel and is used to "draw" the objects on the screen.
 * It also uses an event handler to get user mouse input to get the co-ordinates used
 * for the shapes being drawn. This class uses the MyShape class and all of its 
 * subclasses to create the shapes.
 * 
 * Date: May 2, 2012
 */
public class DrawPanel extends JPanel
{
    // Instance Variables
    
    private Graphics2D g2d;
    
    // Counter used to determine how many shapes to draw
    private int numberOfShapes;
    
    // Used to determine type of shape to create  (line, rectangle, oval)
    private int currentShapeType;
    
    private int strokeWidth;
    private int dashLength;
    
    // Array used to hold the shapes created from subclasses of MyShape
    private DynamicStack shapeObjects;
    private DynamicStack shapeObjectsCopy;
    
    // Holds the current shape that is being drawn
    private MyShape currentShapeObject;
    private MyShape shape;
    private Object[] shapes;
    
    // Determines what colour to make the current shape
    private GradientPaint currentShapeColor;
    
    // Determines whether to fill in a shape or not (rectangles and ovals)
    private boolean currentShapeFilled;
    
    // JLabel that display current mouse co-ordinates
    private JLabel statusLabel;
    
    private boolean creating;
    private boolean isDashed;
    private boolean isGradient;
    
    // Constants for shape type
    private final int LINE = 0; 
    private final int RECTANGLE = 1; 
    private final int OVAL = 2; 
    
    private Color colour;
    private Color colour2;
    
    // Constructor with JLabel parameter
    public DrawPanel(JLabel label)
    {
        // Initializes array that will store the various shapes (up to 100)
        shapeObjects = new DynamicStack();    
        
        // Initialize number of shape counter, shape type variable, and current shape colour
        numberOfShapes = 0;
        currentShapeType = LINE; 
        creating = false;
        strokeWidth = 1;
        dashLength = 1;
        isDashed = false;
        isGradient = false;
        colour = Color.black;
        colour2 = Color.black;
        currentShapeColor = new GradientPaint(0,0,colour,1,1,colour2,true);
        
        
        // Initializes a label (for mouse co-ordinate display) using the parameter
        statusLabel = label;
        
        // Set JPanel background colour to white
        setBackground(Color.WHITE);
        
        // Creates a mouse handler object so mouse events can be detected
        MouseInputHandler mouseInputHandler = new MouseInputHandler();
        
        // Adds mouse handler for clicks and motion to JPanel
        addMouseListener(mouseInputHandler);
        addMouseMotionListener(mouseInputHandler);
    }
    
    // Overloaded paint component method from JPanel
    public void paintComponent( Graphics g )
    {
        // Call superclass's paintComponent
        super.paintComponent( g ); 
        
        g2d = (Graphics2D) g;
        
        if (isGradient)
            currentShapeColor = new GradientPaint(0,0,colour,50,50,colour2,true);
        else
            currentShapeColor = new GradientPaint(0,0,colour,50,50,colour,true);
        
        // For loop used to call all created shapes' (stored in MyShape array)
        // draw methods so they can be displayed on the screen
        // This uses polymorphism as all shapes created from MyShape have
        // a draw method
        
        shapes = shapeObjects.getShapes(numberOfShapes);
        for(int counter = 0; counter < numberOfShapes; counter++)
        {
            shape = (MyShape)shapes[counter];
            shape.draw(g2d);
        }
        
        // Draws the current shape if it exists
        if (currentShapeObject != null)
            currentShapeObject.draw(g2d);   
    } 
    
    // Accessor and Mutator Methods
    
    // Sets the current shape type (Line, Rectangle, Oval)
    public void setCurrentShapeType(int type) 
    {
        currentShapeType = type;
    }
    
    // Sets the current shape's colour
    public void setCurrentShapeColor(Color colour, Color colour2)
    {           
        this.colour = colour;
        this.colour2 = colour2;
        repaint();
    }
    
    // Set line stroke width
    public void setStrokeWidth(int width)
    {
        strokeWidth = width;
    }
    
    // Set line dash length
    public void setDashLength(int length)
    {
        dashLength = length;
    }
    
    // Set boolean value used to determine whether to dash lines or not
    public void setDashed(boolean dashed)
    {
        isDashed = dashed;
    }
    
    // Set boolean value used to determine whether to use gradients or solid colours
    public void setGradient(boolean gradient)
    {
        isGradient = gradient;
        repaint();
    }
    
    // Sets the value of the variable used to determine whether or not
    // to fill in the shape (for rectangles and ovals)
    public void setCurrentShapeFilled(boolean isFilled)
    {
        currentShapeFilled = isFilled;
    }
    
    // Method that "deletes" the last shape created
    public void clearLastShape()
    {
        // Checks to see if there is a shape that can be deleted
        if (numberOfShapes > 0)
        {
            numberOfShapes--;
            shapeObjects.pop();
        }
        
        // Redraws screen after removing shape
        repaint();
    }
    
    public void undoClearLastShape()
    {
        if (shapeObjects.undoMove() != null)
            numberOfShapes++;     
        
        repaint();
    }
    
    // Method that "clears" the screen of all shapes
    public void clearDrawing()
    {       
        numberOfShapes = 0;
        shapeObjects.makeEmpty();
        
        // Redraws screen after clearing the shape
        repaint();
    } // End DrawPanel constructor
    
    //This event handler class is used to get user mouse input and calls the methods in the DrawPanel class.
    private class MouseInputHandler extends MouseAdapter implements MouseListener
    {
        
        // Checks to for a mouse press
        public void mousePressed(MouseEvent event)
        {         
            // If you can still draw
            if (!creating)
            {
                // create the current shape depending on shape type selected
                if (currentShapeType == LINE)
                    currentShapeObject = new MyLine(event.getX(), event.getY(), event.getX(), event.getY(), strokeWidth, isDashed, dashLength, currentShapeColor);
                
                else if (currentShapeType == RECTANGLE)
                    currentShapeObject = new MyRectangle(event.getX(), event.getY(), event.getX(), event.getY(), strokeWidth, isDashed, dashLength, currentShapeColor, currentShapeFilled);
                
                else
                    currentShapeObject = new MyOval(event.getX(), event.getY(), event.getX(), event.getY(), strokeWidth, isDashed, dashLength, currentShapeColor, currentShapeFilled);
                
                creating = true;
            }
        }
        
        // Checks to see if mouse button was released
        public void mouseReleased(MouseEvent event)
        {
            // Prevents exception from unknown mouse source (more than one button released at once)
            if (currentShapeObject != null)
            {
                // Sets the new final co-ordinates for the shape
                currentShapeObject.setX2(event.getX());
                currentShapeObject.setY2(event.getY());
                
                // Adds shape to the shape array (since shape is finished being created)
                shapeObjects.push(currentShapeObject);
                
                // Sets current shape to null (as shape was done being drawn)
                currentShapeObject = null;
                
                // Increases number of shapes counter
                numberOfShapes++;
                
                creating = false;
                
                // Redraws screen after everything has been updated
                repaint();
            }            
        }
        
        // Checks to see if the mouse moved
        public void mouseMoved(MouseEvent event)
        {
            // Updates the JLabel text to display current mouse co-ordinates
            statusLabel.setText("(" + event.getX() + "," + event.getY() + ")");
        }
        
        // Checks to see if the mouse is being dragged
        public void mouseDragged(MouseEvent event)
        {
            // Prevents exception from unknown mouse source (more than one button dragged at once)
            if (currentShapeObject != null)
            {
                // Sets the new final co-ordinates for the shape
                currentShapeObject.setX2(event.getX());
                currentShapeObject.setY2(event.getY());
                
                // Updates the JLabel text to display current mouse co-ordinates
                statusLabel.setText("(" + event.getX() + "," + event.getY() + ")");
                
                // Redraws screen after final co-ordinates in current shape have been changed
                repaint();
            }            
        }
    } // End class MouseInputHandler
} // End class DrawPanel
