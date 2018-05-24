// About.java

// Import modules that will be used
import java.awt.BorderLayout;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JLabel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

/*
 * This class is used to give users basic information about the main paint program.
 * Information includes, program name, creator name, and date last modified.
 * 
 * Date: June 1, 2012
 */
public class About extends JFrame
{
    // Instance variables
    
    // Used to get reference of any instantiated verisons of this class
    private About reference;
    
    // JPanel where all components will be placed
    private JPanel mainPanel;
    
    // Used to modify position and loop of components in JPane
    private GridBagConstraints constraints;
    
    // Labels that will display information
    private JLabel title;
    private JLabel name;
    private JLabel lastModified;
    
    // Button
    private JButton ok;
    
    // Constructor used to create everything in the frame
    public About()
    {
        
        // Reference to instantiated About class
        reference = this;
        
        // Set main frame layout
        setLayout(new BorderLayout());
            
        // Create panel where components will be stored and sets the layout
        mainPanel = new JPanel();
        mainPanel.setLayout(new GridBagLayout());
        
        // Create information displaying labels
        title = new JLabel("SuperPaint!");
        name = new JLabel("Created by:");
        lastModified = new JLabel("Last Modified: June 1, 2012");
        
        // Create exit button
        ok = new JButton("OK");        
        
        // Constraints used to define position of components (labels and button) within JPanel
        constraints = new GridBagConstraints();
        constraints.insets = new Insets(10,10,10,10); 
        constraints.weightx = 1.0; 
        constraints.fill = GridBagConstraints.BOTH;
        constraints.gridwidth = GridBagConstraints.REMAINDER;
        
        // Adds components to panel
        mainPanel.add(title, constraints);
        mainPanel.add(name, constraints);
        mainPanel.add(lastModified, constraints);
        mainPanel.add(ok);
        
        // Add panel to main frame
        add(mainPanel);
        
        // Create button handler and assign it to button
        ButtonHandler buttonHandler = new ButtonHandler();
        ok.addActionListener(buttonHandler);
    }   
    
    // Button handler for the ok button
    private class ButtonHandler implements ActionListener
    {
        public void actionPerformed (ActionEvent event)
        {
            // Reference to created About instance is used to close the frame
            reference.dispose();
        }
    } // end class ButtonHandler
} // end class About
