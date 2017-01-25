// Programmer:  Nathan Tisdale-Dollah, Anthony Zuluaga, Erik Reyes, Mark Mazur
// Assignment:  Project 3
// Date:        November 9, 2015
// Description: The GUI class is the graphical display using JApplet and the ScaledPoint, Node and 
//              DoublyLinkedList classes and their methods to create a graphical display for the
//              user. Together, it is a program that allows a user to create a doubly linked
//              list and see how its structured and functions illustrated using graphics.

import java.awt.event.*;
import java.awt.*;
import javax.swing.*;

public class GUI extends JApplet implements ActionListener
{
    private DoublyLinkedList dll;                    // Doubly Linked List used by the program
    private BorderLayout layout;                     // Border Layout for the applet
    private JButton createButton;                    // Button to create the doubly linked list
    private JButton insertButton;                    // Button to insert a value into the list
    private JButton deleteButton;                    // Button to delete a value from the list
    private JButton searchButton;                    // Button to search for a value in the list
    private JCheckBox enableAnimations;
    private JLabel eventLogLabel;                    // Label for the Event Log text area
    private JPanel controlPanel;                     // Panel to hold the program's controls
    private JPanel eventLogPanel;                    // Panel to hold the Event Log
    private JScrollBar eventLogScrollBar;            // Scroll Bar for the Event Log
    private JScrollPane eventLogScrollPane;          // Scroll Pane for the Event Log text area
    private JTextArea eventLog;                      // Text Area to specify events happening in
                                                     // in the program
    private JTextField userInput;                    // Text Field to allow user to input a number
  
    private boolean isDoublyLinkedListCreated;       // Boolean to check if the doubly linked list
                                                     // has been created
    private boolean isInsertNode;                    // Boolean to check if the insert node
                                                     // animation should be drawn
    private boolean isSearchNode;                    // Boolean to check if the search node
                                                     // animation should be drawn
    private boolean isDeleteNode;                    // Boolean to check if the delete node
                                                     // animation should be drawn
    private boolean isSearchFail;

    private int appletWidth;                         // Width of the applet
    private int appletHeight;                        // Height of the applet
    private int integerInput;                        // User input as an integer
    private String stringInput;                      // User input as a string
    private Color backgroundColor;                   // Color of the main area of the applet
    
    public void init()
    {
        // Set border layout for the applet
        layout = new BorderLayout(10, 10);
        setLayout(layout);
        
        // Initialize backgroundColor and set the background color of the main area of the applet
        backgroundColor = new Color(116, 150, 242);
        getContentPane().setBackground(backgroundColor);
        
        // Get the applet's current width and height
        appletWidth = getWidth();
        appletHeight = getHeight();
        
        // Set booleans
        isDoublyLinkedListCreated = false;
        isInsertNode = false;
        isSearchNode = false;
        isSearchFail = false;
        
        // Create controls
        createButton = new JButton("Create Doubly Linked List");
        insertButton = new JButton("Insert Value");
        deleteButton = new JButton("Delete Value");
        searchButton = new JButton("Search For Value");
        userInput = new JTextField(10);
        enableAnimations = new JCheckBox("Enable Animations");
        
        // Add action listeners to controls
        createButton.addActionListener(this);
        insertButton.addActionListener(this);
        deleteButton.addActionListener(this);
        searchButton.addActionListener(this);
        
        // Create panel for controls, add controls to the panel
        // and add the panel to the NORTH section of the border layout
        controlPanel = new JPanel();
        controlPanel.add(createButton);
        controlPanel.add(new JLabel("Enter Integer: "));
        controlPanel.add(userInput);
        controlPanel.add(insertButton);
        controlPanel.add(deleteButton);
        controlPanel.add(searchButton);
        controlPanel.add(enableAnimations);
        add(controlPanel, BorderLayout.NORTH);
        
        // Create the Event Log
        eventLogLabel = new JLabel("Event Log");
        eventLog = new JTextArea(8, 50);
        eventLog.setEditable(false);
        eventLogScrollPane = new JScrollPane(eventLog);
        
        // Create panel for the Event Log, add the Event Log to the panel 
        // and add the panel to the SOUTH section
        eventLogPanel = new JPanel();
        eventLogPanel.add(eventLogLabel);
        //eventLogPanel.add(eventLog);
        eventLogPanel.add(eventLogScrollPane);
        add(eventLogPanel, BorderLayout.SOUTH);
        eventLog.append(" Applet started. The event log will notify you of what is happening " + 
                        "and of any errors that occur.");
        
        // Initially disable controls because the doubly linked list has not been created
        userInput.setEditable(false);
        insertButton.setEnabled(false);
        deleteButton.setEnabled(false);
        searchButton.setEnabled(false);
    }
    
    public void paint(Graphics g)
    {
        Font stringFont;                    // Font to draw welcome strings at start of the program
        ScaledPoint welcomeStringLoc;       // Location of the first welcome string 
        ScaledPoint welcomeString2Loc;      // Location of the second welcome string
        
        stringFont = new Font("SansSerif", Font.BOLD, 14);        
        welcomeStringLoc = new ScaledPoint(0.35, 0.4);
        welcomeString2Loc = new ScaledPoint(0.315, 0.45);
        
        super.paint(g);
        
        appletWidth = getWidth();
        appletHeight = getHeight();
        
        // If the doubly linked last has not been created, draw the welcome strings
        // to welcome the user and tell them how to begin
        if (isDoublyLinkedListCreated == false)
        {
            g.setFont(stringFont);
            g.drawString("Welcome to our Doubly Linked List program.", 
                         welcomeStringLoc.getXCoord(appletWidth), 
                         welcomeStringLoc.getYCoord(appletHeight));
            g.drawString("Press the \"Create Doubly Linked List\" button to begin.", 
                         welcomeString2Loc.getXCoord(appletWidth), 
                         welcomeString2Loc.getYCoord(appletHeight));
        }
        
        // Set the Event Log to the bottom position to display newest messages
        eventLogScrollBar = eventLogScrollPane.getVerticalScrollBar();
        eventLogScrollBar.setValue(eventLogScrollBar.getMaximum());
        
        // If the doubly linked list has been created, draw it
        if (isDoublyLinkedListCreated == true)
        {
            drawDoublyLinkedList(g);
        }
        
        // If there is at least 1 node in the doubly linked list, draw the nodes
        if (dll.getSize() > 0)
        {
            drawDoublyLinkedListNodes(g);
        }
        
        // If the "Insert Value" button was pressed, draw the animation of inserting the node
        if (isInsertNode == true)
        {
            drawInsertNode(g);
        }
        
        // If the "Search Value" button was pressed, draw the animation of searching for the node
        if (isSearchNode == true)
        {
            colorNode(g, integerInput);
        }
        // If the "Delete Value" button was pressed, draw the animation of deleting the node
        if (isDeleteNode == true)
        {
            drawDeleteNode(g);
        }
        
        // Set the Event Log to the bottom position to display newest messages
        eventLogScrollBar.setValue(eventLogScrollBar.getMaximum());
    }
    
    @Override
    public void actionPerformed(ActionEvent e)
    {
        // If the "Create Doubly Linked List" button was pressed,
        // create the list, enable the controls and notify the user
        if (e.getSource() == createButton)
        {
            // Enable controls
            createButton.setEnabled(false);
            userInput.setEditable(true);
            insertButton.setEnabled(true);
            deleteButton.setEnabled(true);
            searchButton.setEnabled(true);
            
            // Create list
            dll = new DoublyLinkedList();
            
            // Update isDoublyLinkedListCreated
            isDoublyLinkedListCreated = true;
            
            // Notify user in the Event Log
            eventLog.append("\n Doubly Linked List has been created.");
        }
        
        // If the "Insert Value" button is pressed, insert the value in the list
        if (e.getSource() == insertButton)
        {
            // Store user input as a string
            stringInput = userInput.getText();
             
            if (stringInput.length() == 0) // Notify the user if they did not enter a value
                eventLog.append("\n Error: Invalid input. Input must be a valid integer.");
            else if (dll.getSize() == 10) // Notify user if list has the maximum number of nodes
                eventLog.append("\n Error: " + stringInput + " was not inserted because the " +
                                "linked list is at maximum capacity (10 nodes).");
            else // Insert the value in the list and notify the user
            {
                // Store user input as an integer
                integerInput = Integer.parseInt(stringInput);
                
                // If the "Enable Animations" checkbox is checked,
                // set boolean to true to enable drawing the insert node animation
                if (enableAnimations.isSelected() == true)
                {
                    // Enable drawing the insert node animation
                    isInsertNode = true;
                    
                    // Notify the user the element is being added
                    eventLog.append("\n " + stringInput + " is being added to the list.");
                }
                else // Insert the element with no animation
                {          
                    dll.addElement(integerInput); // Insert element
                    
                    // Notify the user
                    eventLog.append("\n " + stringInput + " has been added to the list.");
                }      
            }
        }
        
        // If the "Delete Value" button was pressed,
        // delete the value from the list if it exists or notify the user if it is not in the list
        if (e.getSource() == deleteButton)
        {
            // Store user input as a string
            stringInput = userInput.getText();
            
            if (stringInput.length() == 0) // Notify the user if they did not enter a value
                eventLog.append("\n Error: Invalid input. Input must be a valid integer.");
            else // Delete the value from the list if it exists, or notify user if it's not in list
            {
                // Store user input as an integer
                integerInput = Integer.parseInt(stringInput);
                
                // If a node with this value is in the list, delete it
                if (dll.isIntegerInList(integerInput) == true)
                {
                    // Set boolean to enable drawing the delete node animation
                    if (enableAnimations.isSelected() == true)
                    {
                        // Notify the user the first node with that element is being deleted
                        eventLog.append("\n The first node with integer " + stringInput + 
                                        " is being removed from the list.");
                         
                        // Enable drawing the delete node animation
                        isDeleteNode = true; 
                    }
                    else // Delete the node with no animation
                    {
                        // Remove the first node with the user's input from the list
                        dll.removeElement(integerInput);
                        
                        // Notify the user
                        eventLog.append("\n The first node with integer " + stringInput 
                                            + " has been removed from the list.");
                    }
                }
                else // The value is not in the list, notify the user
                {
                    eventLog.append("\n Error: Cannot delete value because it is not in the list.");
                }
            }
        }
        
        // If the "Search Value" button was pressed, search for the value in the list
        if (e.getSource() == searchButton)
        {
            // Store user input as a string
            stringInput = userInput.getText();
            
            if (stringInput.length() == 0) // Notify the user if they did not enter a value
                eventLog.append("\n Error: Invalid input. Input must be a valid integer.");
            else // Search for the value in the list
            {
                // Store user input as an integer
                integerInput = Integer.parseInt(stringInput);
                
                if (enableAnimations.isSelected() == true)
                {
                    isSearchNode = true; // Enable drawing the search for value animation
                    
                    // Notify the user
                    eventLog.append("\n Searching for " + stringInput + " in the list."); 

                }
                else // Search for the value with no animation
                {
                    if (dll.isIntegerInList(integerInput) == true) // Notify user if it's in list
                    {
                        eventLog.append("\n " + stringInput + " is in the list, in node " + 
                                        dll.getIndexOf(integerInput) + "."); 
                    }
                    else // The value is not in the list, notify the user
                    {
                        eventLog.append("\n " + stringInput + " is not in the list.");
                    }
                }
            }
        }
        
        repaint();
    }
    
    public void drawDoublyLinkedList(Graphics g)
    //  PRE: g is an initialized Graphics object
    // POST: Draws only the Head and Tail of dll, the doubly linked list (does not the nodes),
    //       and the arrows pointing from Head and Tail to the head and tail nodes, if they exist
    {
        ScaledPoint dllHeadArrowStartLoc;        // Start location for drawing line 1 in Head arrow
        ScaledPoint dllHeadArrowEndLoc;          // End location for drawing line 1 in Head arrow
        ScaledPoint dllHeadArrow2EndLoc;         // Location for drawing line 2 in the Head arrow
        ScaledPoint dllHeadArrow3EndLoc;         // Location for drawing line 3 in the Head arrow
        ScaledPoint dllHeadArrowPointEndLoc;     // Location for drawing line 4 in the Head arrow
        ScaledPoint dllHeadArrowPoint2EndLoc;    // Location for drawing line 5 in the Head arrow
        ScaledPoint dllTailArrowStartLoc;        // Start location for drawing line 1 in Tail arrow
        ScaledPoint dllTailArrowEndLoc;          // End location for drawing line 1 in Tail arrow
        ScaledPoint dllTailArrow2EndLoc;         // Location for drawing line 2 in the Tail arrow
        ScaledPoint dllTailArrow3EndLoc;         // Location for drawing line 3 in the Tail arrow
        ScaledPoint dllTailArrowPointEndLoc;     // Location for drawing line 4 in the Tail arrow
        ScaledPoint dllTailArrowPoint2EndLoc;    // Location for drawing line 5 in the Tail arrow 
        ScaledPoint dllLoc;                      // Location to draw the doubly linked list
        ScaledPoint dllSize;                     // Size of drawing the doubly linked list
        ScaledPoint dllLabel;                    // Location of the "Doubly Linked List" label
        Font dllStringFont;                      // Font for drawing Head, Tail and (NULL) strings
        Font dllLabelFont;                       // Font for the "Doubly Linked List" string
        
        dllLoc = new ScaledPoint(0.01, 0.15);
        dllSize = new ScaledPoint(0.15, 0.10);
        dllLabel = new ScaledPoint(0.01, 0.13);
        dllStringFont = new Font("SansSerif", Font.PLAIN, 14);
        dllLabelFont = new Font("SansSerif", Font.BOLD, 14);
        
        // Draw a white rectangle with a black outline and line down the center to represent
        // the doubly linked list, first draw the white rectangle
        g.setColor(Color.WHITE);
        g.fillRect(dllLoc.getXCoord(appletWidth), dllLoc.getYCoord(appletHeight),
                   dllSize.getXCoord(appletWidth), dllSize.getYCoord(appletHeight));
        
        // Draw black outline for the white rectangle
        g.setColor(Color.BLACK);
        g.drawRect(dllLoc.getXCoord(appletWidth), dllLoc.getYCoord(appletHeight),
                   dllSize.getXCoord(appletWidth), dllSize.getYCoord(appletHeight));
        
        // Draw line down the center
        g.drawLine(dllLoc.getXCoord(appletWidth) + (int)(0.5 * dllSize.getXCoord(appletWidth)),
                   dllLoc.getYCoord(appletHeight),
                   dllLoc.getXCoord(appletWidth) + (int)(0.5 * dllSize.getXCoord(appletWidth)),
                   dllLoc.getYCoord(appletHeight) + dllSize.getYCoord(appletHeight));
        
        // Set the label font and draw the "Doubly Linked List" label
        g.setFont(dllLabelFont);
        g.drawString("Doubly Linked List", dllLabel.getXCoord(appletWidth), 
                     dllLabel.getYCoord(appletHeight));
        
        // Set the string font and draw the "Head" string
        g.setFont(dllStringFont);
        g.drawString("Head", dllLoc.getXCoord(appletWidth) + 
                     (int)(0.05 * dllSize.getXCoord(appletWidth)),
                     dllLoc.getYCoord(appletHeight) + (int)(0.4 * dllSize.getYCoord(appletHeight)));
        
        // Draw the "Tail" string
        g.drawString("Tail", dllLoc.getXCoord(appletWidth) + 
                     (int)(0.55 * dllSize.getXCoord(appletWidth)),
                     dllLoc.getYCoord(appletHeight) + (int)(0.4 * dllSize.getYCoord(appletHeight)));
        
        // If the doubly linked list is empty, draw the "(NULL)" strings
        if (dll.getSize() == 0)
        {
            // Draw the "(NULL)" string for the Head
            g.drawString("(NULL)", dllLoc.getXCoord(appletWidth) + 
                         (int)(0.05 * dllSize.getXCoord(appletWidth)),
                         dllLoc.getYCoord(appletHeight) + 
                         (int)(0.7 * dllSize.getYCoord(appletHeight)));
            
            // Draw the "(NULL)" string for the Tail
            g.drawString("(NULL)", dllLoc.getXCoord(appletWidth) + 
                         (int)(0.55 * dllSize.getXCoord(appletWidth)),
                         dllLoc.getYCoord(appletHeight) + 
                         (int)(0.7 * dllSize.getYCoord(appletHeight)));
        }
        
        // Starting and ending locations of the lines used to draw the arrow pointing from Head
        // in Doubly Linked List to the head node, the arrow is made up of 5 lines
        // |       (line 1)
        // |--.    (line 2)
        //   \|/   (lines 3, 4 and 5)
        dllHeadArrowStartLoc = new ScaledPoint(0.03125, 0.25);
        dllHeadArrowEndLoc = new ScaledPoint(0.03125, 0.35);
        dllHeadArrow2EndLoc = new ScaledPoint(0.045 , 0.35);
        dllHeadArrow3EndLoc = new ScaledPoint(0.045 , 0.4);
        dllHeadArrowPointEndLoc = new ScaledPoint(0.045 - 0.005, 0.39);
        dllHeadArrowPoint2EndLoc = new ScaledPoint(0.045 + 0.005, 0.39);
        
        // Starting and ending locations of the lines used to draw the arrow pointing from Tail
        // in Doubly linked list to the tail node, the arrow is made up of 5 lines
        // |       (line 1)
        // |--.    (line 2)
        //   \|/   (lines 3, 4 and 5)
        dllTailArrowStartLoc = new ScaledPoint(0.11, 0.25);
        dllTailArrowEndLoc = new ScaledPoint(0.11, 0.325);
        dllTailArrow2EndLoc = new ScaledPoint(0.06 + (dll.getSize() - 1) * 0.095, 0.325);
        dllTailArrow3EndLoc = new ScaledPoint(0.06 + (dll.getSize() - 1) * 0.095, 0.4);
        dllTailArrowPointEndLoc = new ScaledPoint(0.06 + 
                                                 (dll.getSize() - 1) * 0.095 - 0.005, 0.39);
        dllTailArrowPoint2EndLoc = new ScaledPoint(0.06 + 
                                                 (dll.getSize() - 1) * 0.095 + 0.005, 0.39);
        
        // If there's at least one node in the list, draw an arrow
        // pointing from Head to the head node and an arrow pointing from Tail to the tail node
        if (dll.getSize() > 0)
        {
            // Draw arrow pointing from Head to the head node
            g.drawLine(dllHeadArrowStartLoc.getXCoord(appletWidth), 
                       dllHeadArrowStartLoc.getYCoord(appletHeight),
                       dllHeadArrowEndLoc.getXCoord(appletWidth), 
                       dllHeadArrowEndLoc.getYCoord(appletHeight));
            
            g.drawLine(dllHeadArrowEndLoc.getXCoord(appletWidth), 
                       dllHeadArrowEndLoc.getYCoord(appletHeight),
                       dllHeadArrow2EndLoc.getXCoord(appletWidth), 
                       dllHeadArrow2EndLoc.getYCoord(appletHeight));
            
            g.drawLine(dllHeadArrow2EndLoc.getXCoord(appletWidth), 
                       dllHeadArrow2EndLoc.getYCoord(appletHeight),
                       dllHeadArrow3EndLoc.getXCoord(appletWidth), 
                       dllHeadArrow3EndLoc.getYCoord(appletHeight));
            
            g.drawLine(dllHeadArrow3EndLoc.getXCoord(appletWidth), 
                       dllHeadArrow3EndLoc.getYCoord(appletHeight),
                       dllHeadArrowPointEndLoc.getXCoord(appletWidth), 
                       dllHeadArrowPointEndLoc.getYCoord(appletHeight));
            
            g.drawLine(dllHeadArrow3EndLoc.getXCoord(appletWidth), 
                       dllHeadArrow3EndLoc.getYCoord(appletHeight),
                       dllHeadArrowPoint2EndLoc.getXCoord(appletWidth), 
                       dllHeadArrowPoint2EndLoc.getYCoord(appletHeight));
            
            // Draw arrow pointing from Tail to the tail node
            g.drawLine(dllTailArrowStartLoc.getXCoord(appletWidth), 
                       dllTailArrowStartLoc.getYCoord(appletHeight),
                       dllTailArrowEndLoc.getXCoord(appletWidth), 
                       dllTailArrowEndLoc.getYCoord(appletHeight));
            
            g.drawLine(dllTailArrowEndLoc.getXCoord(appletWidth), 
                       dllTailArrowEndLoc.getYCoord(appletHeight),
                       dllTailArrow2EndLoc.getXCoord(appletWidth), 
                       dllTailArrow2EndLoc.getYCoord(appletHeight));
            
            g.drawLine(dllTailArrow2EndLoc.getXCoord(appletWidth), 
                       dllTailArrow2EndLoc.getYCoord(appletHeight),
                       dllTailArrow3EndLoc.getXCoord(appletWidth), 
                       dllTailArrow3EndLoc.getYCoord(appletHeight));
            
            g.drawLine(dllTailArrow3EndLoc.getXCoord(appletWidth), 
                       dllTailArrow3EndLoc.getYCoord(appletHeight),
                       dllTailArrowPointEndLoc.getXCoord(appletWidth), 
                       dllTailArrowPointEndLoc.getYCoord(appletHeight));
            
            g.drawLine(dllTailArrow3EndLoc.getXCoord(appletWidth), 
                       dllTailArrow3EndLoc.getYCoord(appletHeight),
                       dllTailArrowPoint2EndLoc.getXCoord(appletWidth), 
                       dllTailArrowPoint2EndLoc.getYCoord(appletHeight));
        }
    }
    
    public void drawDoublyLinkedListNodes(Graphics g)
    //  PRE: g is an initialized Graphics object
    // POST: Draws all the nodes of the doubly linked list and the arrows representing the pointers
    {
        ScaledPoint nextArrowStartLoc;          // Location for drawing a line in the next arrow
        ScaledPoint nextArrowEndLoc;            // Location for drawing a line in the next arrow
        ScaledPoint nextArrPointStartLoc;       // Location for drawing a line in the next arrow
        ScaledPoint nextArrPointEndLoc;         // Location for drawing a line in the next arrow
        ScaledPoint nextArrPoint2EndLoc;        // Location for drawing a line in the next arrow
        ScaledPoint prevArrowStartLoc;          // Location for drawing a line in the prev arrow
        ScaledPoint prevArrowEndLoc;            // Location for drawing a line in the prev arrow
        ScaledPoint prevArrPointStartLoc;       // Location for drawing a line in the prev arrow
        ScaledPoint prevArrPointEndLoc;         // Location for drawing a line in the prev arrow
        ScaledPoint prevArrPoint2EndLoc;        // Location for drawing a line in the prev arrow
        Font nodeStringFont;                    // Font for drawing strings of the node
        
        nodeStringFont = new Font("SansSerif", Font.PLAIN, 11);
        
        // Draw each node in the list
        for (int i = 1; i <= dll.getSize(); i++)
        {
            drawNode(g, i, 0); // Draw node
            
            // Set locations to draw the arrow pointing from the previous node to the next node
            nextArrowStartLoc = new ScaledPoint((i * 0.095), 0.415);
            nextArrowEndLoc = new ScaledPoint(0.01 + (i * 0.095), 0.415);
            nextArrPointStartLoc = new ScaledPoint(0.01 + (i * 0.095), 0.415);
            nextArrPointEndLoc = new ScaledPoint(0.01 + (i * 0.095) - 0.005, 0.41);
            nextArrPoint2EndLoc = new ScaledPoint(0.01 + (i * 0.095) - 0.005, 0.42);
            
            // Set locations to draw the arrow pointing from the next node to the previous node
            prevArrowStartLoc = new ScaledPoint((i * 0.095), 0.435);
            prevArrowEndLoc = new ScaledPoint(0.01 + (i * 0.095), 0.435);
            prevArrPointStartLoc = new ScaledPoint((i * 0.095), 0.435);
            prevArrPointEndLoc = new ScaledPoint((i * 0.095) + 0.005, 0.43);
            prevArrPoint2EndLoc = new ScaledPoint((i * 0.095) + 0.005, 0.44);
            
            // If not drawing the last node, draw the arrow pointing from the previous node to
            // the next node, and draw the arrow pointing from the next node to the previous node
            if (i != dll.getSize())
            {
                g.drawLine(nextArrowStartLoc.getXCoord(appletWidth), 
                           nextArrowStartLoc.getYCoord(appletHeight),
                           nextArrowEndLoc.getXCoord(appletWidth), 
                           nextArrowEndLoc.getYCoord(appletHeight));
                
                g.drawLine(prevArrowStartLoc.getXCoord(appletWidth), 
                           prevArrowStartLoc.getYCoord(appletHeight),
                           prevArrowEndLoc.getXCoord(appletWidth), 
                           prevArrowEndLoc.getYCoord(appletHeight));
                
                g.drawLine(nextArrPointStartLoc.getXCoord(appletWidth), 
                           nextArrPointStartLoc.getYCoord(appletHeight),
                           nextArrPointEndLoc.getXCoord(appletWidth), 
                           nextArrPointEndLoc.getYCoord(appletHeight));
                
                g.drawLine(nextArrPointStartLoc.getXCoord(appletWidth), 
                           nextArrPointStartLoc.getYCoord(appletHeight),
                           nextArrPoint2EndLoc.getXCoord(appletWidth), 
                           nextArrPoint2EndLoc.getYCoord(appletHeight));
                
                g.drawLine(prevArrPointStartLoc.getXCoord(appletWidth), 
                           prevArrPointStartLoc.getYCoord(appletHeight),
                           prevArrPointEndLoc.getXCoord(appletWidth), 
                           prevArrPointEndLoc.getYCoord(appletHeight));
                
                g.drawLine(prevArrPointStartLoc.getXCoord(appletWidth), 
                           prevArrPointStartLoc.getYCoord(appletHeight),
                           prevArrPoint2EndLoc.getXCoord(appletWidth), 
                           prevArrPoint2EndLoc.getYCoord(appletHeight));
            }
        }
    }
    
    // drawOption == 0 is the default option to draw the node accurately using its actual values
    // drawOption == 1 is used by drawInsertNode(), in the animation for inserting a node, to
    // draw a newly created node with Next and Prev set to (NULL)
    public void drawNode(Graphics g, int nthNode, int drawOption)
    //  PRE: g is an initialized Graphics object, nodeNumber is an initialized int,
    //       nodeNumber >= 1 and nodeNumber <= 10, drawOption is an initialized int,
    //       and drawOption is equal to 0 or 1 to specify how to draw the node
    // POST: Draws the nth node in the list equal to nthNode. If drawOption == 0, the node is
    //       drawn normally. If drawOption == 1, the node is drawn with (NULL) under Next and Prev
    {
        ScaledPoint nodeLoc;                       // Location of drawing the node
        ScaledPoint nodeSize;                      // Size of drawing the node
        Font nodeStringFont;                       // Font for drawing strings of the node
        
        if (nthNode == 1) // If drawing the first node, set it to a specific location
        {
            nodeLoc = new ScaledPoint(0.01 , 0.4);
        }
        else // Not drawing the first node, set its location based on where it is in the list
        {
            nodeLoc = new ScaledPoint(0.01 + ((nthNode - 1) * 0.095), 0.4);
        }
        
        nodeSize = new ScaledPoint(0.085, 0.10);   // Set size for drawing the node
        
        // Draw the node, beginning with the white rectangle background
        g.setColor(Color.WHITE);
        g.fillRect(nodeLoc.getXCoord(appletWidth), nodeLoc.getYCoord(appletHeight),
                   nodeSize.getXCoord(appletWidth), nodeSize.getYCoord(appletHeight));
        
        // Draw the black outline of the node
        g.setColor(Color.BLACK);
        g.drawRect(nodeLoc.getXCoord(appletWidth), nodeLoc.getYCoord(appletHeight),
                   nodeSize.getXCoord(appletWidth), nodeSize.getYCoord(appletHeight));
        
        // Draw horizontal line to create the area for the node's value
        g.drawLine(nodeLoc.getXCoord(appletWidth),
                   nodeLoc.getYCoord(appletHeight) + (int)(0.35 * nodeSize.getYCoord(appletHeight)),
                   nodeLoc.getXCoord(appletWidth) + nodeSize.getXCoord(appletWidth),
                   nodeLoc.getYCoord(appletHeight) + 
                   (int)(0.35 * nodeSize.getYCoord(appletHeight)));
        
        // Draw vertical line line to create the two areas for the node's Next and Prev
        g.drawLine(nodeLoc.getXCoord(appletWidth) + (int)(0.50 * nodeSize.getXCoord(appletWidth)),
                   nodeLoc.getYCoord(appletHeight) + (int)(0.35 * nodeSize.getYCoord(appletHeight)),
                   nodeLoc.getXCoord(appletWidth) + (int)(0.50 * nodeSize.getXCoord(appletWidth)),
                   nodeLoc.getYCoord(appletHeight) + nodeSize.getYCoord(appletHeight));
        
        // Initialize and set font to draw strings of the node
        nodeStringFont = new Font("SansSerif", Font.PLAIN, 11);
        g.setFont(nodeStringFont);
        
        // Draw Prev string
        g.drawString("Prev", nodeLoc.getXCoord(appletWidth) + 
                     (int)(0.05 * nodeSize.getXCoord(appletWidth)),
                     nodeLoc.getYCoord(appletHeight) + 
                     (int)(0.55 * nodeSize.getYCoord(appletHeight)));
        
        // Draw the node's value
        g.drawString(Integer.toString(dll.getNthElement(nthNode)),
                     nodeLoc.getXCoord(appletWidth) + 
                     (int)(0.1 * nodeSize.getXCoord(appletWidth)),
                     nodeLoc.getYCoord(appletHeight) + 
                     (int)(0.22 * nodeSize.getYCoord(appletHeight)));
        
        // Draw Next string
        g.drawString("Next", nodeLoc.getXCoord(appletWidth) + 
                     (int)(0.55 * nodeSize.getXCoord(appletWidth)),
                     nodeLoc.getYCoord(appletHeight) + 
                     (int)(0.55 * nodeSize.getYCoord(appletHeight)));
        
        if (drawOption == 0) // Draw the linked list normally
        {
            if (nthNode == 1) // If drawing the first node, draw (NULL) under the Prev label
            {
                g.drawString("(NULL)", nodeLoc.getXCoord(appletWidth) + 
                             (int)(0.05 * nodeSize.getXCoord(appletWidth)),
                             nodeLoc.getYCoord(appletHeight) + 
                             (int)(0.85 * nodeSize.getYCoord(appletHeight)));
            }
            
            if (nthNode == dll.getSize()) // Drawing the last node, draw (NULL) under the Next label
            {
                g.drawString("(NULL)", nodeLoc.getXCoord(appletWidth) + 
                             (int)(0.55 * nodeSize.getXCoord(appletWidth)),
                             nodeLoc.getYCoord(appletHeight) + 
                             (int)(0.85 * nodeSize.getYCoord(appletHeight)));
            }
        }
        
        if (drawOption == 1) // Draw (NULL) under the Next and Prev strings
        {
            g.drawString("(NULL)", nodeLoc.getXCoord(appletWidth) + 
                         (int)(0.05 * nodeSize.getXCoord(appletWidth)),
                         nodeLoc.getYCoord(appletHeight) + 
                         (int)(0.85 * nodeSize.getYCoord(appletHeight)));
            
            g.drawString("(NULL)", nodeLoc.getXCoord(appletWidth) + 
                         (int)(0.55 * nodeSize.getXCoord(appletWidth)),
                         nodeLoc.getYCoord(appletHeight) + 
                         (int)(0.85 * nodeSize.getYCoord(appletHeight)));
        }
    }
    
    public void drawInsertNode(Graphics g)
    //  PRE: g is an initialized Graphics object
    // POST: Draws an animation of the process of inserting a node
    {
        Thread thread;                          // Thread to allow the use of sleep() to animate
        Font stringFont;                        // Font of drawing the strings
        ScaledPoint instructionStringLoc;       // Location of the instruction string in animation
        ScaledPoint fillRectStringLoc;          // Location of drawing a filled rectangle to remove
                                                // the instruction string for the next frame
        ScaledPoint fillRectStringSize;         // Size of drawing the filled rectangle
                                                // for removing the instruction string
        ScaledPoint fillRectArrowsLoc;          // Location of drawing a filled rectangle to remove
                                                // the arrows drawn for the next frame
        ScaledPoint fillRectArrowsSize;         // Size of drawing the filled rectangle
                                                // for removing the drawn arrows
        
        thread = new Thread(); 
        stringFont = new Font("SansSerif", Font.BOLD, 14);
        instructionStringLoc = new ScaledPoint(0.005, 0.6);
        fillRectStringLoc = new ScaledPoint(0.005, 0.55);
        fillRectStringSize = new ScaledPoint(0.5, 0.1);
        fillRectArrowsLoc = new ScaledPoint(0.005, 0.15);
        fillRectArrowsSize = new ScaledPoint(0.8, 0.5);
        
        dll.addElement(integerInput); // Add the integer the user gives to the list
        
        // Add instruction string
        g.setFont(stringFont);
        g.drawString("First, the node with the value is created.",
                     instructionStringLoc.getXCoord(appletWidth),
                     instructionStringLoc.getYCoord(appletHeight));
        
        // Sleep for 2 seconds
        try
        {
            thread.sleep(2000);
        }
        catch(InterruptedException e)
        {
            
        }
        
        // Draw the newly created node
        drawNode(g, dll.getSize(), 1);
        
        // Sleep for 2 seconds
        try
        {
            thread.sleep(2000);
        }
        catch(InterruptedException e)
        {
           
        }
        
        // Draw a filled rectangle to clear the previous instruction string
        g.setColor(backgroundColor);
        g.fillRect(fillRectStringLoc.getXCoord(appletWidth),
                   fillRectStringLoc.getYCoord(appletHeight),
                   fillRectStringSize.getXCoord(appletWidth),
                   fillRectStringSize.getYCoord(appletHeight));
        
        // Draw the new instruction string
        g.setFont(stringFont);
        g.setColor(Color.BLACK);
        g.drawString("Then, the pointers are set.", instructionStringLoc.getXCoord(appletWidth),
                     instructionStringLoc.getYCoord(appletHeight));
        
        // Sleep for 2 seconds
        try
        {
            thread.sleep(2000);
        }
        catch(InterruptedException e)
        {
            
        }
        
        // Draw a filled rectangle to clear the previous instruction string
        g.setColor(backgroundColor);
        g.fillRect(fillRectStringLoc.getXCoord(appletWidth),
                   fillRectStringLoc.getYCoord(appletHeight),
                   fillRectStringSize.getXCoord(appletWidth),
                   fillRectStringSize.getYCoord(appletHeight));
        
        // Draw a filled rectangle to clear the arrows that were drawn
        g.fillRect(fillRectArrowsLoc.getXCoord(appletWidth),
                   fillRectArrowsLoc.getYCoord(appletHeight),
                   fillRectArrowsSize.getXCoord(appletWidth),
                   fillRectArrowsSize.getYCoord(appletHeight));
        
        // Draw the new instruction string
        g.setFont(stringFont);
        g.setColor(Color.BLACK);
        g.drawString("Inserting the node is finished.", instructionStringLoc.getXCoord(appletWidth),
                     instructionStringLoc.getYCoord(appletHeight));
        
        // Draw completed doubly linked list with the new node inserted
        drawDoublyLinkedList(g);
        drawDoublyLinkedListNodes(g);
        
        // Sleep for 2 seconds
        try
        {
            thread.sleep(2000);
        }
        catch(InterruptedException e)
        {
            
        }
        
        // Draw a filled rectangle to clear the previous instruction string
        g.setColor(backgroundColor);
        g.fillRect(fillRectStringLoc.getXCoord(appletWidth),
                   fillRectStringLoc.getYCoord(appletHeight),
                   fillRectStringSize.getXCoord(appletWidth),
                   fillRectStringSize.getYCoord(appletHeight));
        
        // Notify the user the element has been added to the list
        eventLog.append("\n " + stringInput + " has been added to the list.");
        
        // Set the Event Log to the bottom position to display newest messages
        eventLogScrollBar.setValue(eventLogScrollBar.getMaximum());
        
        isInsertNode = false; // Set boolean to false to disable animation
    }
    
    public void colorNode(Graphics g, int integerInput)
    //  PRE: g is an initialized Graphics object,     
    // POST: Colors in the node that is selected
    {
        Thread thread;                          // Thread to allow the use of sleep() to animate
        Font stringFont;                        // Font of drawing the strings
        ScaledPoint instructionStringLoc;       // Location of the instruction string in animation
        ScaledPoint nodeLoc;                    // Location of drawing the node
        ScaledPoint nodeSize;                   // Size of drawing the node
        ScaledPoint fillRectStringLoc;          // Location of drawing a filled rectangle to remove
                                                // the instruction string for the next frame
        ScaledPoint fillRectStringSize;         // Size of drawing the filled rectangle
                                                // for removing the instruction string
        int count;                              // Int to keep a count during a loop
        
        // Initialized variables
        thread = new Thread();
        stringFont = new Font("SansSerif", Font.BOLD, 14);
        instructionStringLoc = new ScaledPoint(0.005, 0.6);
        nodeLoc = new ScaledPoint(0.01, 0.4);
        nodeSize = new ScaledPoint(0.085, 0.10);
        fillRectStringLoc = new ScaledPoint(0.005, 0.55);
        fillRectStringSize = new ScaledPoint(0.5, 0.1);
        count = 0;
        
        if (dll.isEmpty() == true) // If the list is empty, notify user and return
        {
            eventLog.append("\n " + stringInput + " is not in the list.");
            return;
        }
        
        // Add instruction string
        g.setFont(stringFont);
        g.setColor(Color.BLACK);
        g.drawString("To begin, we start with the first node.",
                     instructionStringLoc.getXCoord(appletWidth),
                     instructionStringLoc.getYCoord(appletHeight));
        
        // Highlight the current node with red color
        g.setColor(Color.RED);
        g.drawRect(nodeLoc.getXCoord(appletWidth), nodeLoc.getYCoord(appletHeight),
                   nodeSize.getXCoord(appletWidth), nodeSize.getYCoord(appletHeight));
        
        // Sleep for 2 seconds
        try
        {
            thread.sleep(2000);
        }
        catch(InterruptedException e)
        {
            
        }
        
        // Draw a filled rectangle to clear the previous instruction string
        g.setColor(backgroundColor);
        g.fillRect(fillRectStringLoc.getXCoord(appletWidth),
                   fillRectStringLoc.getYCoord(appletHeight),
                   fillRectStringSize.getXCoord(appletWidth),
                   fillRectStringSize.getYCoord(appletHeight));
        
        while ((count + 1) <= dll.getSize()) // Loop through each node in the doubly linked list
        {
            if (dll.getNthElement(count + 1) == integerInput)
            {
                // Remove red highlight from previous node
                g.setColor(Color.BLACK);
                g.drawRect(nodeLoc.getXCoord(appletWidth), nodeLoc.getYCoord(appletHeight),
                           nodeSize.getXCoord(appletWidth), nodeSize.getYCoord(appletHeight));                
                
                // Set node location
                nodeLoc = new ScaledPoint(0.01 + ((count)* 0.095), 0.4);
                
                // Highlight the current node with red
                g.setColor(Color.RED);
                g.drawRect(nodeLoc.getXCoord(appletWidth), nodeLoc.getYCoord(appletHeight),
                           nodeSize.getXCoord(appletWidth), nodeSize.getYCoord(appletHeight));
                
                // Draw the new instruction string
                g.setFont(stringFont);
                g.setColor(Color.BLACK);
                g.drawString("Found the Node with the value.",
                             instructionStringLoc.getXCoord(appletWidth),
                             instructionStringLoc.getYCoord(appletHeight));
                
                // Sleep for 2 seconds
                try {
                    thread.sleep(2000);
                }
                catch(InterruptedException e)
                {
                    
                }
                
                // Draw a filled rectangle to clear the previous instruction string
                g.setColor(backgroundColor);
                g.fillRect(fillRectStringLoc.getXCoord(appletWidth),
                           fillRectStringLoc.getYCoord(appletHeight),
                           fillRectStringSize.getXCoord(appletWidth),
                           fillRectStringSize.getYCoord(appletHeight));
                
                break;
            }
            
            count++;
            
            // Remove red highlight from previous node
            g.setColor(Color.BLACK);
            g.drawRect(nodeLoc.getXCoord(appletWidth), nodeLoc.getYCoord(appletHeight),
                       nodeSize.getXCoord(appletWidth), nodeSize.getYCoord(appletHeight));
            
            // Update the current index
            nodeLoc = new ScaledPoint(0.01 + ((count - 1) * 0.095), 0.4);
            
            // Highlight with red for the current node
            g.setColor(Color.RED);
            g.drawRect(nodeLoc.getXCoord(appletWidth), nodeLoc.getYCoord(appletHeight),
                       nodeSize.getXCoord(appletWidth), nodeSize.getYCoord(appletHeight));
            
            // Draw the new instruction string
            g.setFont(stringFont);
            g.setColor(Color.BLACK);
            g.drawString("The value is not in this node, move on to the next node.", 
                         instructionStringLoc.getXCoord(appletWidth),
                         instructionStringLoc.getYCoord(appletHeight));
            
            // Sleep for 2 seconds
            try
            {
                thread.sleep(2000);
            }
            catch(InterruptedException e)
            {
                
            }
            
            // Draw a filled rectangle to clear the previous instruction string
            g.setColor(backgroundColor);
            g.fillRect(fillRectStringLoc.getXCoord(appletWidth),
                       fillRectStringLoc.getYCoord(appletHeight),
                       fillRectStringSize.getXCoord(appletWidth),
                       fillRectStringSize.getYCoord(appletHeight));
            
        }      
        
        if (dll.isIntegerInList(integerInput) == true) // Notify user if the element is in the list
        {
            eventLog.append("\n " + stringInput + " is in the list, in node " + (count + 1) + ".");
        }
        else // Notify the user the element is not in the list
        {
            eventLog.append("\n " + stringInput + "is not in the list.");
        }
        
        // Set the Event Log to the bottom position to display newest messages
        eventLogScrollBar.setValue(eventLogScrollBar.getMaximum());
        
        isSearchNode = false; // Set boolean to false to disable animation
    }

    public void searchFail(Graphics g, int integerInput)
    // Animates the search failure
    {
        Thread thread = new Thread(); // Thread to allow use of sleep()
        Font stringFont = new Font("SansSerif", Font.BOLD, 14);
        ScaledPoint instructionStringLoc = new ScaledPoint(0.005, 0.6); // Location of instruction string
        ScaledPoint fillRectStringLoc = new ScaledPoint(0.005, 0.55); // location of rectangle to erase text
        ScaledPoint fillRectStringSize = new ScaledPoint(0.5, 0.1); // rectangle to erase string
        Graphics2D g2 = (Graphics2D) g; // graphics object to draw
        int borderWidth = 3;  //border thickness
        int count = 0;  // count

        // Set the location of where the node will be drawn based on where it is in the linked list
        // Set the size of drawing the node
        ScaledPoint nodeLoc = new ScaledPoint(0.01, 0.4);
        ScaledPoint nodeSize = new ScaledPoint(0.085, 0.10);

        //set border thickness
        g2.setStroke(new BasicStroke(borderWidth));

        // Add instruction string
        g.setFont(stringFont);
        g.setColor(Color.BLACK);
        g.drawString("First, we start with the first Node.",
                instructionStringLoc.getXCoord(appletWidth),
                instructionStringLoc.getYCoord(appletHeight));

        //set border color
        g2.setColor(Color.RED);
        g2.drawRect(nodeLoc.getXCoord(appletWidth), nodeLoc.getYCoord(appletHeight),
                nodeSize.getXCoord(appletWidth), nodeSize.getYCoord(appletHeight));

        // Sleep for 2 seconds
        try
        {
            thread.sleep(2000);
        }
        catch(InterruptedException e)
        {

        }

        // Draw fillRect to clear previous instruction string
        g.setColor(backgroundColor);
        g.fillRect(fillRectStringLoc.getXCoord(appletWidth),
                fillRectStringLoc.getYCoord(appletHeight),
                fillRectStringSize.getXCoord(appletWidth),
                fillRectStringSize.getYCoord(appletHeight));

        while (count < (dll.getSize()-1))
        {
            count++;

            // update the current index
            nodeLoc.setXCoord((0.01 + (count * 0.095)), 1);
            nodeLoc.setYCoord(nodeLoc.getYCoord(appletHeight), appletHeight);

            // highlight with red color for the current node
            g2.setColor(Color.RED);
            g2.drawRect(nodeLoc.getXCoord(appletWidth), nodeLoc.getYCoord(appletHeight),
                    nodeSize.getXCoord(appletWidth), nodeSize.getYCoord(appletHeight));

            // Draw new instruction string
            g.setFont(stringFont);
            g.setColor(Color.BLACK);
            g.drawString("Not this Node, move on to the next Node.",
                    instructionStringLoc.getXCoord(appletWidth),
                    instructionStringLoc.getYCoord(appletHeight));

            // Sleep for 2 second
            try
            {
                thread.sleep(2000);
            }
            catch(InterruptedException e)
            {

            }

            // Draw fillRect to clear previous instruction string
            g.setColor(backgroundColor);
            g.fillRect(fillRectStringLoc.getXCoord(appletWidth),
                    fillRectStringLoc.getYCoord(appletHeight),
                    fillRectStringSize.getXCoord(appletWidth),
                    fillRectStringSize.getYCoord(appletHeight));

        }

        // Draw new instruction string
        g.setFont(stringFont);
        g.setColor(Color.BLACK);
        g.drawString("Could not find Node in list.",
                instructionStringLoc.getXCoord(appletWidth),
                instructionStringLoc.getYCoord(appletHeight));

        // Sleep for 2 second
        try
        {
            thread.sleep(2000);
        }
        catch(InterruptedException e)
        {

        }

        // Draw fillRect to clear previous instruction string
        g.setColor(backgroundColor);
        g.fillRect(fillRectStringLoc.getXCoord(appletWidth),
                fillRectStringLoc.getYCoord(appletHeight),
                fillRectStringSize.getXCoord(appletWidth),
                fillRectStringSize.getYCoord(appletHeight));

        isSearchFail = false;
    }

    private void drawDeleteNode(Graphics g)
    // creates the animation for deleting a node
    {
        Thread thread = new Thread(); // Thread to allow use of sleep()
        Font stringFont = new Font("SansSerif", Font.BOLD, 14);


    private void drawDeleteNode(Graphics g)
    //  PRE: g is an initialized Graphics object
    // POST: Draws the animation of the process of deleting a node
    {
        Thread thread;                          // Thread to allow the use of sleep() to animate
        Font stringFont;                        // Font of drawing the strings
        ScaledPoint instructionStringLoc;       // Location of the instruction string in animation
        ScaledPoint fillRectStringLoc;          // Location of drawing a filled rectangle to remove
                                                // the instruction string for the next frame
        ScaledPoint fillRectStringSize;         // Size of drawing the filled rectangle
                                                // for removing the instruction string
        ScaledPoint fillRectArrowsLoc;          // Location of drawing a filled rectangle to remove
                                                // the arrows drawn for the next frame
        ScaledPoint fillRectArrowsSize;         // Size of drawing the filled rectangle
                                                // for removing the drawn arrows
        
        // Initialize variables
        thread = new Thread();
        stringFont = new Font("SansSerif", Font.BOLD, 14);
        instructionStringLoc = new ScaledPoint(0.005, 0.6);
        fillRectStringLoc = new ScaledPoint(0.005, 0.55);
        fillRectStringSize = new ScaledPoint(0.5, 0.1);
        fillRectArrowsLoc = new ScaledPoint(0.005, 0.15);
        fillRectArrowsSize = new ScaledPoint(0.97, 0.5);
        
        // Add instruction string
        g.setFont(stringFont);
        g.drawString("First, the node with the value is deleted.",
                     instructionStringLoc.getXCoord(appletWidth),
                     instructionStringLoc.getYCoord(appletHeight));
        
        // Sleep for 2 seconds
        try
        {
            thread.sleep(2000);
        }
        catch(InterruptedException e)
        {
            
        }
        
        // Remove the wanted node.
        removeNode(g);
        
        // Sleep for 2 seconds
        try
        {
            thread.sleep(2000);
        }
        catch(InterruptedException e)
        {
            
        }
        
        // Draw a filled rectangle to clear the previous instruction string
        g.setColor(backgroundColor);
        g.fillRect(fillRectStringLoc.getXCoord(appletWidth),
                   fillRectStringLoc.getYCoord(appletHeight),
                   fillRectStringSize.getXCoord(appletWidth),
                   fillRectStringSize.getYCoord(appletHeight));
        
        // Draw the new instruction string
        g.setFont(stringFont);
        g.setColor(Color.BLACK);
        g.drawString("Then, the pointers are set.", instructionStringLoc.getXCoord(appletWidth),
                     instructionStringLoc.getYCoord(appletHeight));
        
        // Sleep for 2 seconds
        try
        {
            thread.sleep(2000);
        }
        catch(InterruptedException e)
        {
            
        }
        
        // Draw a filled rectangle to clear the previous instruction string
        g.setColor(backgroundColor);
        g.fillRect(fillRectStringLoc.getXCoord(appletWidth),
                   fillRectStringLoc.getYCoord(appletHeight),
                   fillRectStringSize.getXCoord(appletWidth),
                   fillRectStringSize.getYCoord(appletHeight));
        
        // Draw a filled rectangle to clear the arrows that were drawn
        g.fillRect(fillRectArrowsLoc.getXCoord(appletWidth),
                   fillRectArrowsLoc.getYCoord(appletHeight),
                   fillRectArrowsSize.getXCoord(appletWidth),
                   fillRectArrowsSize.getYCoord(appletHeight));
        
        // Draw the new instruction string
        g.setFont(stringFont);
        g.setColor(Color.BLACK);
        g.drawString("Deleting the node is finished.", instructionStringLoc.getXCoord(appletWidth),
                     instructionStringLoc.getYCoord(appletHeight));
        
        // Draw doubly linked list
        drawDoublyLinkedList(g);
        drawDoublyLinkedListNodes(g);
        
        // Sleep for 2 seconds
        try
        {
            thread.sleep(2000);
        }
        catch(InterruptedException e)
        {
            
        }
        
        // Draw a filled rectangle to clear the previous instruction string
        g.setColor(backgroundColor);
        g.fillRect(fillRectStringLoc.getXCoord(appletWidth),
                   fillRectStringLoc.getYCoord(appletHeight),
                   fillRectStringSize.getXCoord(appletWidth),
                   fillRectStringSize.getYCoord(appletHeight));
        
        // Notify user
        eventLog.append("\n The first node with integer " + stringInput 
                            + " has been removed from the list.");
        
        // Set the Event Log to the bottom position to display newest messages
        eventLogScrollBar.setValue(eventLogScrollBar.getMaximum());
        
        isDeleteNode = false; // Set boolean to false to disable animation
    }
    
    private void removeNode(Graphics g)
    //  PRE: g is an initialized Graphics object
    // POST: Draws over a specific node to remove it during the animation for deleting a node
    {
        ScaledPoint nodeLoc;                         // Location to draw the node
        ScaledPoint nodeSize;                        // Size of drawing the node
        
        // Set location of the node to remove
        if (dll.getIndexOf(integerInput) == 1) // If removing the first node, set its location
        {
            nodeLoc = new ScaledPoint(0.01 , 0.4);
        }
        else // Else removing any other node, set its location based on where it is in the list
        {
            nodeLoc = new ScaledPoint(0.01 + ((dll.getIndexOf(integerInput) - 1) * 0.095), 0.4);
        }
        
        nodeSize = new ScaledPoint(0.086, 0.11); // Set node size
        
        // Color over node to create the illusion of the node being deleted
        g.setColor(backgroundColor);
        g.fillRect(nodeLoc.getXCoord(appletWidth), nodeLoc.getYCoord(appletHeight),
                   nodeSize.getXCoord(appletWidth), nodeSize.getYCoord(appletHeight));
        
        // Draw horizontal line
        g.drawLine(nodeLoc.getXCoord(appletWidth),
                   nodeLoc.getYCoord(appletHeight) + (int)(0.35 * nodeSize.getYCoord(appletHeight)),
                   nodeLoc.getXCoord(appletWidth) + nodeSize.getXCoord(appletWidth),
                   nodeLoc.getYCoord(appletHeight) + 
                   (int)(0.35 * nodeSize.getYCoord(appletHeight)));
        
        // Draw vertical line
        g.drawLine(nodeLoc.getXCoord(appletWidth) + (int)(0.50 * nodeSize.getXCoord(appletWidth)),
                   nodeLoc.getYCoord(appletHeight) + (int)(0.35 * nodeSize.getYCoord(appletHeight)),
                   nodeLoc.getXCoord(appletWidth) + (int)(0.50 * nodeSize.getXCoord(appletWidth)),
                   nodeLoc.getYCoord(appletHeight) + nodeSize.getYCoord(appletHeight));
        
        dll.removeElement(integerInput); // Delete the integer the user gives from the list
    }
}