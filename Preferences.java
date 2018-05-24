// Preferences.java

// Imports modules that will be used
import java.io.File;
import java.io.IOException;
import java.util.Formatter;
import java.awt.Dimension;
import java.awt.BorderLayout;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.awt.Color;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.ItemListener;
import java.awt.event.ItemEvent;
import java.lang.NumberFormatException;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JColorChooser;

/*
 * This class creates a user interface for users to enter preference choices. After the users 
 * click "Done", the program will create a text file storing all the user's preference choices
 * so they can be saved in between sessions of using the paint program. This class is similar to
 * the main paint program as it contains all the components the main program has in the menu bar
 * so users can set preferences.
 * 
 * Date: June 1, 2012
 */
public class Preferences extends JFrame
{    
    // Used to get a reference of window that will be created
    private Preferences reference;
    
    // Used to check for existing preferences
    private ReadPreferences read;
    
    // Used solely for GUI purposes (look of components window i.e. position)
    private GridBagConstraints constraints;
    
    // Variables used to store user preferences
    private int strokeWidth;
    private int dashLength;
    private int currentShapeType;
    
    private boolean isGradient;
    private boolean isDashed;
    private boolean isFilled;
    
    // Layout for the menu
    private GridBagLayout mainLayout;
    
    // Main panel
    private JPanel mainPanel;
    
    // Buttons
    private JButton colourButton;
    private JButton colour2Button;
    private JButton done;
    private JButton cancel;
    
    // Check boxes
    private JCheckBox isGradientSelect;
    private JCheckBox isDashedSelect;
    private JCheckBox isFilledSelect;
    
    // Text boxes
    private JTextField strokeEnter;  
    private JTextField dashEnter;
    
    // List (Combo Box)
    private JComboBox shapeSelect;

    
    // Labels
    private JLabel strokeLabel;
    private JLabel dashLabel;
    private JLabel colourLabel;
    private JLabel linesLabel;
    private JLabel shapesLabel;
    
    // Used for colours
    private Object colourVerify;
    private Color colour;
    private Color colour2;
    private JColorChooser colourChooser;
    
    // Array of shape names used for the shape selection list
    private final String shapeNames[] = {"Line", "Rectangle", "Oval"};    
    
    // Constructor 
    // Very similar to SuperPaint constructor, aside from internal frames
    public Preferences()
    {
        // Call parent class's constructor
        super("Preferences");
        
        // Creates reference to any window being created
        reference = this;
        
        // Set layout for main window
        setLayout(new BorderLayout());
        
        // Creates layout for JPanel
        mainLayout = new GridBagLayout();        
        mainPanel = new JPanel();
        mainPanel.setLayout(mainLayout);
        
        // Constraints used for graphical purposes
        constraints = new GridBagConstraints();
        constraints.insets = new Insets(10,10,10,10); 
        constraints.weightx = 1.0; 
        constraints.fill = GridBagConstraints.BOTH;
        
        // Used to choose and store colours
        colour = new Color(0,0,0);
        colour2 = new Color(0,0,0);
        colourChooser = new JColorChooser(Color.black);
        
        // Create buttons and set background colour
        colourButton = new JButton("Colour #1");
        colourButton.setBackground(colour);
        colour2Button = new JButton("Colour #2");
        colour2Button.setBackground(colour2);
        
        // More buttons
        done = new JButton("Done");
        cancel = new JButton("Cancel");
        
        // Creates check box
        isGradientSelect = new JCheckBox("Gradient");
        
        // Creates labels
        strokeLabel = new JLabel("Stroke Width:");
        
        // Creates text fields
        strokeEnter = new JTextField("     ");
        strokeWidth = 1;
        strokeEnter.setText(Integer.toString(strokeWidth));
        
        // Create check box
        isDashedSelect = new JCheckBox("Dashed");
        
        // Create label
        dashLabel = new JLabel("Dash Length:");
        
        // Create and initialize text for text box
        dashEnter  = new JTextField("     ");
        dashLength = 1;
        dashEnter.setText(Integer.toString(dashLength));
        
        // Creates shape selection list
        shapeSelect = new JComboBox(shapeNames);
        
        // Initializes selection to start at
        shapeSelect.setSelectedIndex(0);
        
        // Creates check box
        isFilledSelect = new JCheckBox("Filled");
        
        // Create lables
        colourLabel = new JLabel("Colour Options:");
        linesLabel = new JLabel("Line Options:");
        shapesLabel = new JLabel("Shape Options:");
        
        // Adds all that was created (buttons, labels, text fields, check boxes, lists to panel)
        // Constraints used for positionaing
        constraints.gridx = 1;
        constraints.gridy = 0;
        mainPanel.add(colourLabel, constraints);  
        constraints.gridx = 0;
        constraints.gridy = 1;
        mainPanel.add(colourButton, constraints);
        constraints.gridx = 1;
        constraints.gridy = 1;
        mainPanel.add(colour2Button, constraints);
        constraints.gridx = 2;
        constraints.gridy = 1;
        mainPanel.add(isGradientSelect, constraints);
        constraints.gridx = 1;
        constraints.gridy = 2;
        mainPanel.add(linesLabel, constraints);
        constraints.gridx = 0;
        constraints.gridy = 3;
        constraints.insets = new Insets(10,10,10,0); 
        mainPanel.add(strokeLabel, constraints);
        constraints.gridx = 1;
        constraints.gridy = 3;
        constraints.insets = new Insets(10,0,10,10); 
        mainPanel.add(strokeEnter, constraints);
        constraints.gridx = 2;
        constraints.gridy = 3;
        constraints.insets = new Insets(10,10,10,10); 
        mainPanel.add(isDashedSelect, constraints);
        constraints.gridx = 3;
        constraints.gridy = 3;
        constraints.insets = new Insets(10,10,10,0); 
        mainPanel.add(dashLabel, constraints);
        constraints.gridx = 4;
        constraints.gridy = 3;
        constraints.insets = new Insets(10,0,10,10); 
        mainPanel.add(dashEnter, constraints);
        constraints.gridx = 1;
        constraints.gridy = 4;
        constraints.insets = new Insets(10,10,10,10); 
        mainPanel.add(shapesLabel, constraints);
        constraints.gridx = 0;
        constraints.gridy = 5;
        mainPanel.add(shapeSelect, constraints);
        constraints.gridx = 1;
        constraints.gridy = 5;
        mainPanel.add(isFilledSelect, constraints); 
        constraints.gridx = 2;
        constraints.gridy = 6;
        mainPanel.add(done, constraints); 
        constraints.gridx = 3;
        constraints.gridy = 6;
        mainPanel.add(cancel, constraints); 
        
        
        // Creates a button handlers and assigns them to buttons
        FinishButtonHandler finishButtonHandler = new FinishButtonHandler();
        ColourButtonHandler colourButtonHandler = new ColourButtonHandler();
        colourButton.addActionListener(colourButtonHandler);
        colour2Button.addActionListener(colourButtonHandler);
        done.addActionListener(finishButtonHandler);
        cancel.addActionListener(finishButtonHandler);
        
        // Same as button handlers except for text fields
        TextFieldHandler textFieldHandler = new TextFieldHandler();
        strokeEnter.addActionListener(textFieldHandler);
        dashEnter.addActionListener(textFieldHandler);
        
        // Same as button handlers except for check boxes
        CheckBoxHandler checkBoxHandler = new CheckBoxHandler();
        isFilledSelect.addItemListener(checkBoxHandler);
        isGradientSelect.addItemListener(checkBoxHandler);
        isDashedSelect.addItemListener(checkBoxHandler);
        
        // Same as button handlers except for lists (Combo Boxs)
        ComboBoxHandler comboBoxHandler = new ComboBoxHandler();
        shapeSelect.addItemListener(comboBoxHandler);     
        
        // Adds panel (containing everything) to the main JFrame (window)
        add(mainPanel);
        
        // Just like in the main program, used to check if there are already user preferences
        read = new ReadPreferences();
        
        // If so, the values are used to display the current user preferences
        if (read.isValidFile())
        {
            colour = read.getColour();
            colourButton.setBackground(colour);
            
            colour2 = read.getColour2();
            colour2Button.setBackground(colour2);
            
            isGradient = read.getIsGradient();
            isGradientSelect.setSelected(isGradient);
            
            strokeWidth = read.getStrokeWidth();
            strokeEnter.setText(Integer.toString(strokeWidth));
                
            isDashed = read.getIsDashed();
            isDashedSelect.setSelected(isDashed);
                
            dashLength = read.getDashLength();
            dashEnter.setText(Integer.toString(dashLength));
            
            currentShapeType = read.getCurrentShapeType();
            shapeSelect.setSelectedIndex(currentShapeType);
            
            isFilled = read.getIsFilled();
            isFilledSelect.setSelected(isFilled);
        }
    }
    
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
        }
    } // End class ColourButtonHandler
    
    // Button handler for the buttons that finish are used to finish user selection
    private class FinishButtonHandler implements ActionListener
    {
        public void actionPerformed (ActionEvent event)
        {
            // If the done button was pressed
            if (event.getSource() == done)
            {
                
                // Converts preferences into integers (for easy parsing) to be stored in a text file called "prefs.txt"
                try
                {
                Formatter file = new Formatter (new File ("prefs.txt"));
                
                // Store values for colours
                file.format("%d %d %d ", colour.getRed(), colour.getGreen(), colour.getBlue());  
                file.format("%d %d %d ", colour2.getRed(), colour2.getGreen(), colour2.getBlue());
                
                // Store value for gradient boolean value
                if (isGradient)
                    file.format("%d ", 1);
                else
                    file.format("%d ", 0);
                
                // Store value for stroke width
                file.format("%d ", strokeWidth);
                
                // Store value for dashed boolean value
                if (isDashed)
                    file.format("%d ", 1);
                else
                    file.format("%d ", 0);
                
                // Store value for dash length
                file.format("%d ", dashLength);
                
                // Store value for shape type
                file.format("%d ", currentShapeType);
                
                // Store value for filled boolean value
                if (isFilled)
                    file.format("%d ", 1);
                else
                    file.format("%d ", 0);
                
                // Close file
                file.close();
                
                }
                
                // Empty catch statement
                // This exception is thrown by invalid input/ output
                // In the case of my program, only very simple file handling is used so this error will not occur
                // Therefore there is no need to put anything inside the catch statement
                catch (IOException e) {}
                
                // Calls the reference's dispose method (inherited from a parent class high up the class heirarchy)
                // Used to close the frame without exiting the main program (like System.exit(0))
                reference.dispose();
            }
            
            // If the cancel button was pressed
            else if (event.getSource() == cancel)                
                // Close the frame without saving user preferences
                reference.dispose();
        }
    } // end class FinishButtonHandler
    
// This event handler class is used to get events from check boxes
    private class CheckBoxHandler implements ItemListener
    {
        
        // Checks to see if the check box was checked or unchecked
        public void itemStateChanged( ItemEvent event )
        {
            // Used to determine whether to fill in shapes or not
            if (event.getSource() == isFilledSelect)
            {                
                // If checked
                if (isFilledSelect.isSelected())
                    
                    // Set the fill shape variable to true
                    isFilled = true;
                
                // If not checked
                else                    
                    // Set the fill shape variable to false
                    isFilled = false;
                
            }
            
            // Used to determine whether to use gradient or solid colours
            // Same as event for check box to fill in shapes except for gradients
            else if (event.getSource() == isGradientSelect)
            {
                if(isGradientSelect.isSelected())
                    isGradient = true;
                
                else
                    isGradient = false;
            }
            
            // Used to determine whether to create solid or dashed lines
            // Same as event for check box to fill in shapes except for gradients
            else if (event.getSource() == isDashedSelect)
            {
                if(isDashedSelect.isSelected()) 
                    isDashed = true;
                
                else
                    isDashed = false;
            }
        }        
    } // end class CheckBoxHandler
    
// This event handler class is used to get events from combo boxes (lists)
    private class ComboBoxHandler implements ItemListener
    {
        
        // Checks to see if the list selection was changed
        public void itemStateChanged( ItemEvent event )
        {
            currentShapeType = shapeSelect.getSelectedIndex();
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
                        
                        // If valid, assign new value to variable
                        strokeWidth = Integer.parseInt(event.getActionCommand());
                    
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
                        dashLength = Integer.parseInt(event.getActionCommand());
                    
                    else
                        dashEnter.setText(Integer.toString(dashLength));
                }
                
                catch (NumberFormatException e)
                {
                    dashEnter.setText(Integer.toString(dashLength));
                }
            }
        }
    } // end class TextFieldHandler
} // end class Preferences
