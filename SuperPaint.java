// SuperPaint.java

// Import modules that will be used 
import java.awt.Dimension;
import java.awt.BorderLayout;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.awt.Color;
import java.awt.GradientPaint;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.ItemListener;
import java.awt.event.ItemEvent;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JButton;
import javax.swing.ImageIcon;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JColorChooser;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JInternalFrame;
import javax.swing.JDesktopPane;
import javax.swing.event.InternalFrameAdapter;
import javax.swing.event.InternalFrameEvent;
import java.beans.PropertyVetoException;
import java.lang.NumberFormatException;

/*
 * This class is the main program that incorporates the DrawPanel program into
 * a screen with menus (so users can select options, i.e. change shape type)
 * and a label that displays current mouse co-ordinates. The program has handlers
 * which are used for various user inteactions. The main program an save user prefereces
 * and incorporates many other classes including ones for storing and retrieving preferences.
 * 
 * Date: June 1, 2012
 */
public class SuperPaint extends JFrame
{
    // Create instance variables
    
    // Needed to place JInternalFrames in
    private JDesktopPane desktop;
    
    // Used to get user preferences
    private ReadPreferences read;
    
    // Puts paint program into internal frames
    private DrawFrame frame;
    
    // Used for GUIs
    private GridBagConstraints constraints;    
    private ImageIcon icon;
    
    // Used to keep track of number of internal frames on screen
    private int framesCount;
    
    // Integers that will be used later
    private int strokeWidth;
    private int dashLength;
    private int tempMeasurement;
    
    // Keep track of various internal frame attribute states
    private boolean state;
    
    // For menus bar (one at the very top of the screen)
    JMenuBar menuBar;
    JMenu fileMenu, editMenu;
    JMenuItem about, prefs, exit, create, maximize, minimize, close;
    
    // Main program layout
    private BorderLayout mainLayout;
    
    // Layout for the menu
    private GridBagLayout menuLayout;
    
    // Menu panel
    private JPanel menuPanel;
    
    // Buttons
    private JButton undoButton;
    private JButton redoButton;
    private JButton clearButton;
    private JButton colourButton;
    private JButton colour2Button;
    
    // Text fields
    private JTextField strokeEnter;   
    private JTextField dashEnter;
    
    private JComboBox shapeSelect;
    
    // Check Boxes
    private JCheckBox isGradientSelect;
    private JCheckBox isDashedSelect;
    private JCheckBox isFilledSelect;
    
    // Labels
    private JLabel statusLabel;
    private JLabel strokeLabel;
    private JLabel dashLabel;
    
    // Needed for colour-related operations
    private Object colourVerify;
    private Color colour;
    private Color colour2;
    private JColorChooser colourChooser;
    
    // Scroll Pane for when the window gets too small to fit self-made menu bar
    private JScrollPane scroller = new JScrollPane();
    
    // Array of shape names used for the shape selection list
    private final String shapeNames[] = {"Line", "Rectangle", "Oval"};    
    
    // Constructor
    public SuperPaint()
    {
        // Call parent class's constructor
        super("SuperPaint!");
        
        // File menu related items
        menuBar = new JMenuBar();
        fileMenu = new JMenu("File");        
        about = new JMenuItem("About");
        prefs = new JMenuItem("Prefs");        
        exit = new JMenuItem("Exit");
        
        // Edit menu related items
        editMenu = new JMenu("Edit");
        create = new JMenuItem("Create New Frame");
        minimize = new JMenuItem("Un/minimize All");
        maximize = new JMenuItem("Un/maximize All");
        close = new JMenuItem("Close All");
        
        // Adds components to menu bar
        menuBar.add(fileMenu);
        menuBar.add(editMenu);
        fileMenu.add(about);
        fileMenu.add(prefs);
        fileMenu.add(exit);
        editMenu.add(create);
        editMenu.add(minimize);
        editMenu.add(maximize);
        editMenu.add(close);
        setJMenuBar(menuBar);
        
        // Menu event handler for file menu
        FileMenuHandler fileMenuHandler = new FileMenuHandler(); 
        about.addActionListener(fileMenuHandler);
        prefs.addActionListener(fileMenuHandler);
        exit.addActionListener(fileMenuHandler);
        
        // Menu event handler for edit menu
        EditMenuHandler editMenuHandler = new EditMenuHandler();        
        create.addActionListener(editMenuHandler);
        minimize.addActionListener(editMenuHandler);
        maximize.addActionListener(editMenuHandler);
        close.addActionListener(editMenuHandler);
        
        // Creates main program layout and sets it
        mainLayout = new BorderLayout(5,5);
        setLayout(mainLayout);
        
        // Creates self-made menu bar (basically a JPanel with components on it) and its layout
        menuPanel = new JPanel();        
        menuLayout = new GridBagLayout(); 
        menuPanel.setLayout(menuLayout);
        
        // Used for GUIs
        constraints = new GridBagConstraints();
        constraints.anchor = GridBagConstraints.LINE_START; 
        constraints.fill = GridBagConstraints.HORIZONTAL;
        constraints.weightx = 1.0; 
        
        // Initialize variables
        
        // Create colour related variables
        colour = new Color(0,0,0);
        colour2 = new Color(0,0,0);
        colourChooser = new JColorChooser(Color.black);
        
        // Creates buttons
        undoButton = new JButton("Undo");    
        redoButton = new JButton("Redo");
        clearButton = new JButton("Clear");
        
        // Background colours are set for these buttons
        colourButton = new JButton("Colour #1");
        colourButton.setBackground(colour);
        colour2Button = new JButton("Colour #2");
        colour2Button.setBackground(colour2);
        
        // Creates check box
        isGradientSelect = new JCheckBox("Gradient");
        
        // Creates label with specified size
        strokeLabel = new JLabel("Stroke Width:");
        strokeLabel.setPreferredSize(new Dimension(55,25));
        
        // Creates text field (entering empty spaces is easier than using a method to set size)
        strokeEnter = new JTextField("     ");
        
        // Set initial value for stroke width and sets that as text in the text fiekd
        strokeWidth = 1;
        strokeEnter.setText(Integer.toString(strokeWidth));
        
        // Creates check box
        isDashedSelect = new JCheckBox("Dashed");
        
        // Same as with stroke related items, except for dash length
        dashLabel = new JLabel("Dash Length:");
        dashLabel.setPreferredSize(new Dimension(50,25));
        
        dashEnter  = new JTextField("     ");
        dashLength = 1;
        dashEnter.setText(Integer.toString(dashLength));
        
        // Creates shape selection list
        shapeSelect = new JComboBox(shapeNames);
        
        // Initializes selection to start at
        shapeSelect.setSelectedIndex(0);
        
        // Creates check box
        isFilledSelect = new JCheckBox("Filled");
        
        // Creates mouse co-ordinate label
        statusLabel = new JLabel();
        
        // Creates scroll pane for menu bar
        scroller = new JScrollPane(menuPanel);        
        
        // Adds buttons, lists, checkboxes, text fields, and labels to menu bar
        // Constraints used for appearance (GUI) purposes        
        constraints.insets = new Insets(0,0,0,5);
        menuPanel.add(undoButton, constraints);        
        constraints.insets = new Insets(0,5,0,5);
        menuPanel.add(redoButton, constraints);
        menuPanel.add(clearButton, constraints);
        menuPanel.add(colourButton, constraints);
        menuPanel.add(colour2Button, constraints);
        menuPanel.add(isGradientSelect, constraints);        
        constraints.insets = new Insets(0,5,0,0); 
        menuPanel.add(strokeLabel, constraints);        
        constraints.insets = new Insets(0,0,0,5); 
        menuPanel.add(strokeEnter, constraints);        
        constraints.insets = new Insets(0,5,0,5); 
        menuPanel.add(isDashedSelect, constraints);        
        constraints.insets = new Insets(0,5,0,0); 
        menuPanel.add(dashLabel, constraints);        
        constraints.insets = new Insets(0,0,0,5); 
        menuPanel.add(dashEnter, constraints);        
        constraints.insets = new Insets(0,5,0,5); 
        menuPanel.add(shapeSelect, constraints);
        menuPanel.add(isFilledSelect, constraints); 
        
        // Creates button handlers for buttons
        ButtonHandler buttonHandler = new ButtonHandler();
        ColourButtonHandler colourButtonHandler = new ColourButtonHandler();        
        
        // Adds button handlers to buttons
        undoButton.addActionListener(buttonHandler);
        redoButton.addActionListener(buttonHandler);
        clearButton.addActionListener(buttonHandler);
        colourButton.addActionListener(colourButtonHandler);
        colour2Button.addActionListener(colourButtonHandler);
        
        // Same as button handlers except for text fields
        TextFieldHandler textFieldHandler = new TextFieldHandler();
        strokeEnter.addActionListener(textFieldHandler);
        dashEnter.addActionListener(textFieldHandler);
        
        // Same as button handlers except for check boxes
        CheckBoxHandler checkBoxHandler = new CheckBoxHandler();
        isFilledSelect.addItemListener(checkBoxHandler);
        isGradientSelect.addItemListener(checkBoxHandler);
        isDashedSelect.addItemListener(checkBoxHandler);
        
        // Same as button handlers except for list (Combo Box)
        ComboBoxHandler comboBoxHandler = new ComboBoxHandler();
        shapeSelect.addItemListener(comboBoxHandler);        
        
        // Create image icon that will be in internal frame title bars
        icon = new ImageIcon("painticon.png");
        
        // Creates area for internal frames
        desktop = new JDesktopPane();
        
        // Used to keep track of number of frames (and name them according to number)
        // **NOTE** There may be a repeat of a name of a frame due to this
        // e.g. two frames both called "Frame #4"
        framesCount = 0;
        
        // Adds menu bar (inside scroll pane), desktop pane, and staus label to main window
        add(scroller, BorderLayout.NORTH);
        add(desktop, BorderLayout.CENTER);
        add(statusLabel, BorderLayout.SOUTH);
        
        
        // Used so the whole internal frame will be displayed while moving it around
        desktop.setDragMode(JDesktopPane.LIVE_DRAG_MODE);
        
        // Created to parse user preferences that might have been saved in a text file
        // Created here so program can start with user preferences (if avaliable)
        read = new ReadPreferences();
        
        // If the file is valid (exists and can be parsed without errors)
        // Set all preferences that the user chose (basically  sets every option in the self-made menu bar)
        // E.g. colours, gradients, shapes, etc.
        if (read.isValidFile())
        {
            colour = read.getColour();
            colourButton.setBackground(colour);
            
            colour2 = read.getColour2();
            colour2Button.setBackground(colour2);
            
            isGradientSelect.setSelected(read.getIsGradient());
            
            strokeWidth = read.getStrokeWidth();
            strokeEnter.setText(Integer.toString(strokeWidth));
            
            isDashedSelect.setSelected(read.getIsDashed());
            
            dashLength = read.getDashLength();
            dashEnter.setText(Integer.toString(dashLength));
            
            shapeSelect.setSelectedIndex(read.getCurrentShapeType());
            
            isFilledSelect.setSelected(read.getIsFilled());
        }
    }
    
    // This method is used to make JInternalFrames (which contain paint program)
    private void makeInternalFrame()
    {
        // Internal frame
        frame = new DrawFrame(statusLabel);
        
        // Sets values for the paint program based on variables that can be modified by the user
        frame.setCurrentShapeType(shapeSelect.getSelectedIndex());
        frame.setCurrentShapeColor(colour, colour2);
        frame.setCurrentShapeFilled(isFilledSelect.isSelected());
        frame.setDashed(isDashedSelect.isSelected());
        frame.setGradient(isGradientSelect.isSelected());
        frame.setStrokeWidth(strokeWidth);
        frame.setDashLength(dashLength);
        
        // Sets title using the frame count
        frame.setTitle("Frame # " + (framesCount + 1));
        
        // Sets an icon image in the title bar
        frame.setFrameIcon(icon);
        
        // Add event handler to internal frame
        frame.addInternalFrameListener(new FramesHandler());
        
        // Sets size of frame that will be created
        frame.setSize(175,100);
        
        // Set location in the container to display internal frame
        frame.setLocation(framesCount*10,framesCount*10);
        
        // Make frame visible
        frame.setVisible(true);
        
        // Add frame to container
        desktop.add(frame);
        
        // Must use exception handling or some methods won't work
        try
        {
            frame.setSelected(true);
            frame.setMaximum(true);
        }
        
        // Empty catch statement as this error may be thrown
        // It will not in this program as attribute states are set to internal frames once
        // they are created. Therefore, non-existant attributes will not be called (which gives this error)
        // E.g trying to minimize the window when the attribute to minimize window does not exist
        // **NOTE** whenever you see this, it is just being used for JInternalFrames methods as it is mandatory
        // therefore all catch statements involving the PropertyVetoException will be EMPTY
        catch (PropertyVetoException e){}
        
        // Increase frame count
        framesCount++;
        
        // Has to be created every time to check for possible updates to preferences
        read = new ReadPreferences();
        
        // Same as before but applies settings directly into newly created internal frames
        // so they start with user preferences (if avaliable)
        if (read.isValidFile())
        {
            colour = read.getColour();
            colourButton.setBackground(colour);
            
            colour2 = read.getColour2();
            colour2Button.setBackground(colour2);
            
            frame.setCurrentShapeColor(colour, colour2);
            
            frame.setGradient(read.getIsGradient());
            isGradientSelect.setSelected(read.getIsGradient());
            
            strokeWidth = read.getStrokeWidth();
            frame.setStrokeWidth(strokeWidth);
            strokeEnter.setText(Integer.toString(strokeWidth));
            
            frame.setDashed(read.getIsDashed());
            isDashedSelect.setSelected(read.getIsDashed());
            
            dashLength = read.getDashLength();
            frame.setDashLength(dashLength);
            dashEnter.setText(Integer.toString(dashLength));
            
            frame.setCurrentShapeType(read.getCurrentShapeType());
            shapeSelect.setSelectedIndex(read.getCurrentShapeType());
            
            frame.setCurrentShapeFilled(read.getIsFilled());
            isFilledSelect.setSelected(read.getIsFilled());
        }
    }
    
    // Menu handler for events in the file menu
    private class FileMenuHandler implements ActionListener
    {
        public void actionPerformed (ActionEvent event)
        {          
            // If about is selected, a window about the program will be created (using About class)
            // Window has a default size and cannot be resizes
            // Set visible must be set to true or window will be invisible
            if (event.getSource() == about)
            {
                About aboutWindow = new About();
                aboutWindow.setVisible(true);
                aboutWindow.setResizable(false);
                aboutWindow.setSize(200,200);
            }
            
            // Creates user preferences window (using Preferences class)
            // Window has a default size and cannot be resizes
            // Set visible must be set to true or window will be invisible
            else if (event.getSource() == prefs)
            {
                Preferences prefWindow = new Preferences();
                prefWindow.setVisible(true);
                prefWindow.setResizable(false);
                prefWindow.setSize(500,400);
            }
            
            // Terminate the program if exit button is selected
            else if (event.getSource() == exit)
                
                // Using System.exit(0) as it terminates the program
                System.exit(0); 
        }
    } // End class FileMenuHandler
    
    // Menu handler for events in the edit menu
    private class EditMenuHandler implements ActionListener
    {
        public void actionPerformed (ActionEvent event)
        {
            // The getAllFrames() method returns a JInternalFrame array with all
            // the internal frames within your container
            JInternalFrame frames[] = desktop.getAllFrames();
            
            // Creates internal frames
            if (event.getSource() == create)
                makeInternalFrame();
            
            // Un/minimize ALL windows
            else if (event.getSource() == minimize)
            {
                state = false;
                
                for (int counter = 0; counter < frames.length; counter++ )
                {
                    // Check if any item is minimized
                    if (!frames[counter].isIcon())
                    {
                        state = true;
                        break;
                    }
                }
                
                for (int counter = 0; counter < frames.length; counter++ )
                {
                    try
                    {                    
                        // This is method is used to un/minimize ALL windows
                        // depending on boolean value passed
                        frames[counter].setIcon(state);
                    }
                    
                    // See above explanation
                    catch (PropertyVetoException e){}
                }
            }
            
            // Un/maximize all windows
            else if (event.getSource() == maximize)
            {
                state = false;
                
                for (int counter = 0; counter < frames.length; counter++ )
                {
                    // Check if any item is maximized
                    if (!frames[counter].isMaximum())
                    {
                        state = true;
                        break;
                    }
                }
                
                for (int counter = 0; counter < frames.length; counter++ )
                {                    
                    try
                    {                        
                        // This is method is used to un/maximize windows
                        // depending on boolean value passed
                        frames[counter].setMaximum(state);
                    }
                    
                    // See above explanation
                    catch (PropertyVetoException e){}
                }
            }
            
            // Closes all windows (no need to specify event source as this menu handler is only for the edit menu)
            else
            {
                for (int counter = 0; counter < frames.length; counter++ )
                {
                    try
                    {
                        // This is method is used to close windoes
                        frames[counter].setClosed(true);
                    }
                    
                    // See above explanation
                    catch (PropertyVetoException e){}
                }
            }
        }
    } // End class EditMenuHandler
    
    // Handler for JInternalFrames using an adapter so not all methods will need to be implemented
    public class FramesHandler extends InternalFrameAdapter
    {
        
        // Checks to see if the frame closed
        public void internalFrameClosed(InternalFrameEvent e)
        {
            // Decrease frame count
            framesCount--;
        }
    } // End class FramesHandler
    
    // This event handler class is used to get events from buttons
    private class ButtonHandler implements ActionListener
    {
        
        // Checks to see if the button was pressed
        public void actionPerformed (ActionEvent event)
        {
            frame = (DrawFrame) desktop.getSelectedFrame();
            
            // Checks to see if the button press is associated with a frame
            // If not, the button press does nothing
            if (frame != null)
            {
                // If the undo button was pressed
                if (event.getSource() == undoButton)
                    
                    // Clear last shape drawn
                    frame.clearLastShape();
                
                // If the redo button was pressed
                else if (event.getSource() == redoButton)
                    
                    // Redraw last shape deleted ("undone")
                    frame.undoClearLastShape();
                
                // If the clear button was pressed
                else if (event.getSource() == clearButton)
                    
                    // Clear all shapes drawn
                    frame.clearDrawing();
            }
        }
    } // End class ButtonHandler
    
    // Event handler for the two buttons that determine colour
    private class ColourButtonHandler implements ActionListener
    {
        public void actionPerformed (ActionEvent event)
        {
            // If the first colour button was pressed
            if (event.getSource() == colourButton)
            {
                // Show the colour chooser instance which includes detailed colour selection options
                colourVerify = colourChooser.showDialog(colourButton, "Colour #1", colour);
                
                // Prevents error from happening when "null" is returned (from pressing cancel or the exit button)
                // If there are no problems, set colour variable and background colour for button
                if (colourVerify != null)
                {
                    colour = (Color) colourVerify;
                    colourButton.setBackground(colour);
                }
            }
            
            // If the second colour button was pressed
            // Same as previous except for the second colour button
            else if (event.getSource() == colour2Button)
            {
                colourVerify = colourChooser.showDialog(colourButton, "Colour #2", colour);
                if (colourVerify != null)
                {
                    colour2 = (Color) colourVerify;
                    colour2Button.setBackground(colour2);
                }
            }
            
            // If there is a frame associable, set the colour of shapes to-be-created
            if (frame != null)
                frame.setCurrentShapeColor(colour, colour2);
        }
    } // End class ColourButtonHandler
    
    // This event handler class is used to get events from check boxes
    private class CheckBoxHandler implements ItemListener
    {
        
        // Checks to see if the check box was checked or unchecked
        public void itemStateChanged( ItemEvent event )
        {
            frame = (DrawFrame) desktop.getSelectedFrame();            
            
            // Makes sure events can be used on an internal frame (paint program)
            if (frame != null)
            {
                
                // Used to determine whether to fill in shapes or not
                if (event.getSource() == isFilledSelect)
                {
                    // If checked
                    if (isFilledSelect.isSelected())
                        
                        // Set the fill shape variable in draw panel to true
                        frame.setCurrentShapeFilled(true);
                    
                    // If not checked
                    else                        
                        // Set the fill shape variable in draw panel to false
                        frame.setCurrentShapeFilled(false);
                }
                
                // Used to determine whether to use gradient or solid colours
                // Same as event for check box to fill in shapes except for gradients
                else if (event.getSource() == isGradientSelect)
                {
                    if(isGradientSelect.isSelected())                        
                        frame.setGradient(true);
                    
                    else
                        frame.setGradient(false);
                }
                
                // Used to determine whether to create solid or dashed lines
                // Same as event for check box to fill in shapes except for gradients
                else if (event.getSource() == isDashedSelect)
                {
                    if(isDashedSelect.isSelected()) 
                        frame.setDashed(true);
                    
                    else
                        frame.setDashed(false);
                }
            }            
        }
    } // End class CheckBoxHandler
    
    // This event handler class is used to get events from combo boxes (lists)
    private class ComboBoxHandler implements ItemListener
    {
        
        // Checks to see if the list selection was changed
        public void itemStateChanged( ItemEvent event )
        {
            frame = (DrawFrame) desktop.getSelectedFrame();
            
            if (frame != null)
            {
                // Change the shape type that will be drawn
                frame.setCurrentShapeType(shapeSelect.getSelectedIndex());
            }
        }
    } // End class ComboBoxHandler
    
    // Event handler for text boxes
    private class TextFieldHandler implements ActionListener
    {
        public void actionPerformed (ActionEvent event)
        {
            // If the user enter input into the text box for stroke width (and pressed enter)
            if (event.getSource() == strokeEnter)
            {                
                // Used to prevent exceptions from trying to parse integers from non-integer input
                try
                {
                    
                    // Makes sure value is valid (interger must be greater than 0)
                    if (Integer.parseInt(event.getActionCommand()) > 0)      
                    {                        
                        // If valid, assign new value to variable
                        strokeWidth = Integer.parseInt(event.getActionCommand());
                        
                        // If there is a frame associated with event, set the paint program's stroke width
                        if (frame != null)
                            frame.setStrokeWidth(strokeWidth);
                    }
                    
                    // If the input was not an integer greater than 0
                    else
                        
                        // Reset text to the current valid value
                        strokeEnter.setText(Integer.toString(strokeWidth));
                    
                }
                
                // If input entered was not an integer
                catch (NumberFormatException e)
                {                    
                    // Reset text to the current valid value
                    strokeEnter.setText(Integer.toString(strokeWidth));
                }
            }
            
            // Same as previous event except for dash length
            else if (event.getSource() == dashEnter)
            {
                try
                {
                    if (Integer.parseInt(event.getActionCommand()) > 0)
                    {
                        dashLength = Integer.parseInt(event.getActionCommand());
                        
                        if (frame != null)
                            frame.setDashLength(dashLength);
                    }
                    
                    else
                        dashEnter.setText(Integer.toString(dashLength));
                }
                
                catch (NumberFormatException e)
                {
                    dashEnter.setText(Integer.toString(dashLength));
                }
            }
        }
    } // End class TextFieldHandler
} // End class SuperPaint
