// Programmer:  Nathan Tisdale-Dollah, Anthony Zuluaga, Erik Reyes, Mark Mazur
// Assignment:  Project 3
// Date:        November 9, 2015
// Description: The Node class is modeled after Linked List nodes, containing the value of the node,
//              the node connected before it, if any, and the node connected after it, if any.

public class Node extends DoublyLinkedList
{
    protected int value;                         // Integer value of the node
    protected Node nextNode;                     // Node after this node in the list, if any
    protected Node previousNode;                 // Node before this node in the list, if any
    
    public Node()
    // POST: A default Node object is created with value == 0, next == NULL and prev == NULL
    //       because no nodes are connected to it
    {
        value = 0;
        nextNode = null;
        previousNode = null;
    }
    
    public Node(int value)
    //  PRE: value is an initialized int
    // POST: A default Node object is created with its value set to the specified value in the
    //       parameter, next == NULL and prev == NULL because no nodes are connected to it
    {
        this(value, null, null);
    }
    
    public Node(int initValue, Node next, Node prev)
    //  PRE: value is an initialized int and next and prev are both an initialized Node
    // POST: A default Node object is created with a value given by the user
    //   and a valid node before and after it, connected to it
    {
        value = initValue;
        nextNode = next;
        previousNode = prev;
    }
    
    public void setValue(int givenValue)
    //  PRE: givenNode is a valid Node that can be reached by at least one other node
    // POST: sets the value of the current node
    {
        value = givenValue;
    }
    
    public void setPrevNode(Node givenNode)
    //  PRE: givenNode is a valid Node that can be reached by at least one other node
    // POST: Sets prevNode equal to givenNode, the previous node before the current node
    {
        previousNode = givenNode;
    }
    
    public void setNextNode(Node givenNode)
    //  PRE: givenNode is a valid Node that can be reached by at least one other node
    // POST: Sets nextNode equal to givenNode, the next node after the current node
    {
        nextNode = givenNode;
    }
    
    public int getNodeValue()
    // POST: FCTVAL == value, the value of the current node
    {
        return value;
    }
    
    public Node getPrevNode()
    // POST: FCTVAL == prevNode, the previous node before the current node
    {
        return previousNode;
    }
    
    public Node getNextNode()
    // POST: FCTVAL == nextNode, the next node after the current node
    {
        return nextNode;
    }
}
