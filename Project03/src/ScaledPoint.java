// Programmer:  Nathan Tisdale-Dollah, Anthony Zuluaga, Erik Reyes, Mark Mazur
// Assignment:  Project 3
// Date:        November 9, 2015
// Description: The ScaledPoint class is used to ensure that each of the graphical equivalents of
//              the DoublyLinkedList class and the Node class are correctly resized when the
//              window containing them is resized.

public class ScaledPoint extends GUI
{
    private double xVal;              // X Coordinate as a percentage based on width of the applet
    private double yVal;              // Y coordinate as a percentage based on height of the applet
    
    public ScaledPoint()
    // POST: Creates a default ScaledPoint object with xVal and yVal both set to 0.0
    {
        xVal = 0.0;
        yVal = 0.0;
    }

    public ScaledPoint(double x, double y)
    //  PRE: x and y are initialized as doubles, x >= 0 and x <= 1.0, y >= 0 and y <= 1.0
    // POST: Creates an initialized ScaledPoint object with xVal == x and yVal == y
    {
        xVal = x;
        yVal = y;
    }

    public void setXCoord(int x, int appletWidth)
    //  PRE: x is initialized as an int, x >= 0 and x <= width of the applet and
    //       appletWidth == the current width of the applet
    // POST: xVal is set equal to (x / appletWidth)
    {
        xVal = x / appletWidth;
    }
    
    public void setYCoord(int y, int appletHeight)
    //  PRE: y is initialized as an int, y >= 0 and y <= height of the applet and
    //       appletHeight == the current height of the applet
    // POST: yVal is set equal to (y / appletHeight)
    {
        yVal = y / appletHeight;
    }

    public int getXCoord(int x)
    //  PRE: x is initialized as an int, x >= 0 and x <= width of the applet
    // POST: FCTVAL == an integer equal to (x * xVal) 
    {
        return (int)(x * xVal);
    }

    public int getYCoord(int y)
    //  PRE: y is initialized as an int, y >= 0 and y <= height of the applet
    // POST: FCTVAL == an integer equal to (y * yVal)
    {
        return (int)(y * yVal);
    }
}
