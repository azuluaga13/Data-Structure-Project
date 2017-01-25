// Programmer:  Nathan Tisdale-Dollah, Anthony Zuluaga, Erik Reyes, Mark Mazur
// Assignment:  Project 3
// Date:        November 9, 2015
// Description: The DoublyLinkedList class is modeled after a Doubly Linked List, containing
//              two nodes when initialized and allowing the user to add, remove and search nodes.

public class DoublyLinkedList 
{
    private Node first;                       // First node in the doubly linked list
    private Node last;                        // Last node in the doubly linked list
    private int index;                        // Index used to get the index of a node
    
    public DoublyLinkedList() 
    // POST: A default, empty DoublyLinkedList object is created with first == null, last == null
    //       and index == 0 because it has no nodes
    {
        first = null;
        last = null;
        int index = 0;
    }
    
    public boolean isEmpty()
    // POST: FCTVAL == returns true if the doubly linked list is empty, else false if it has at
    //       least one node
    {
        return first == null;
    }
    
    public boolean isIntegerInList(int value)
    //  PRE: value is an initialized int
    // POST: FCTVAL == returns true if value is in the doubly linked list, else false if it is not
    {
        Node temp = first;
        
        while (temp != null) // Check the value of each node in the list
        {
            if (temp.value == value) // If a node has the value return true, else search next node
                return true;         
            
            temp = temp.getNextNode(); // Set temp to the next node in the list
        }
        
        return false; // Return false if value is not in the list
    }

    public int getIndexOf(int value)
    //  PRE: value is an initialized int
    // POST: FCTVAL == the index of the node with the specified value
    {
        Node temp = first;
        int indexOfNode = 1;

        while (temp!= null) // Check each node in the list
        {
            if(temp.getNodeValue() == value) // If the value is found return the index
            {
                return indexOfNode;
            }
            
            // Increment index and check the next node
            indexOfNode++;
            temp = temp.getNextNode();
        }

        return -1; // Index not found
    }
    
    public int getSize()
    // POST: FCTVAL == an integer equal to the number of nodes in the list
    {
        int count = 0;
        Node temp = first;
        
        while (temp != null) // While the current node isn't null, continue counting nodes
        {
            count++;
            temp = temp.getNextNode();
        }
        
        return count; // Return the total number of nodes
    }
    
    public int getNthElement(int n)
    //  PRE: n is an initialized int, n >= 1 and n <= 10
    // POST: FCTVAL == the value of the nth node
    {
        int count = 1;
        Node temp = first;
        
        while (temp != null) // While the current node isn't null, continue looking for nth element
        {
            if (count == n) // If at the nth node, return its value
            {
                return temp.getNodeValue();
            }
            
            count++;
            temp = temp.getNextNode();
        }
        
        return count; // Returns size of the list if it was not long enough
        
    }
    
    public void addElement(int value)
    //  PRE: value is an initialized int
    // POST: Adds a node with the specified value to the doubly linked list
    {
        if (isEmpty()) // If the list is empty, add a node with value to the list
        {
            last = new Node(value);
            first = last;
        }
        else // Else the list is not empty, add the element to the last node
        {
            Node temp = new Node(value, null, last);
            last.setNextNode(temp);
            last = last.getNextNode();
        }
    }
    
    public void removeElement(int value)
    //  PRE: value is an initialized int
    // POST: Removes the first node with this value from the list
    {
        Node current;                                // Current node being looked at
        Node next;                                   // Next node to be looked at
        
        if (isEmpty()) // If the doubly linked list is empty, do nothing and return
        {
            return;
        }
        else if (getSize() == 1) // If the doubly linked list has only one node
        {
            if (first.getNodeValue() == value) // If the single node has this, remove the node
            {    
                first = null;
                last = null;
            }
        }
        else if (getSize() > 1) // If the list has more than one node, iterate through the list
        {
            if (first.getNodeValue() == value) // If the first node is the specified node, remove it
            {
                first = first.getNextNode();
            }
            else // Else go through each node in the list
            {
                current = first;
                next = first.getNextNode();
                
                while (next != null) // Continue looking through the list
                {
                    if (next.getNodeValue() == value) // If the the values are equal, remove node
                    {
                        if (last == next) // If next node is the last node, set current node to last
                        {
                            current.setNextNode(null);
                            last = current;
                            break;
                        }
                        else  // Remove the element from the list
                        {                                                
                            current.setNextNode(next.getNextNode());
                            break;
                        }
                    }
                    
                    current = current.getNextNode(); // Set current node to the next node
                    next = current.getNextNode(); // Set next node to current's next node
                }
            }
        }
    }
}
