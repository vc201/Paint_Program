// SuperPaintTest.java

// Imports modules that will be used
import java.awt.Toolkit;
import java.awt.Dimension;
import javax.swing.JFrame;

/*
 * Test application to display main paint program.
 * 
 * Date: June 1, 2012
 */
public class SuperPaintTest
{
    public static void main( String args[] )
    {
        
        // Main program
        SuperPaint paint = new SuperPaint();
        
        // Used to get screen size
        Toolkit toolkit = Toolkit.getDefaultToolkit();
        Dimension dimension = toolkit.getScreenSize();
        
        paint.setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );
        
        //Uses screen size to determine size of program window
        paint.setSize((int)dimension.getWidth(), (int)dimension.getHeight() - 50);
        paint.setVisible( true ); // display frame
    } // end main
} // end class InternalFrameTest
