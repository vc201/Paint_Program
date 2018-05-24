// LinkedList.java

class LinkedList {
    private ListNode head;
    
    // Used to prevent total deletion of linked list 
    // If all cleared it will still have reference to previous objects
    private ListNode temp;
    
    // Constructor
    public LinkedList() {
        head = null;
        temp = null;
    }
     
    // Check if the list is empty
    public boolean isEmpty() {
        return (head == null);
    }
     
    // Delete all list nodes
    public void makeEmpty() {
        head = null;
        temp = null;
    }
    
    // Add a node to the head of the list
    public void addFront( Object item ) {
        ListNode prevHead = head;
        head = new ListNode( item, head, null);
        
        if (prevHead != null)
            prevHead.setPrevious(head);
    }
 
    // Remove a node from the head of the list and return a reference to its value
    // return null if the list is empty
    public Object removeFront() {
        ListNode tempHead;
         
        if (isEmpty())
            return null;
        
        tempHead = head;
        head = head.getNext();
        if (head != null)
            head.setPrevious(tempHead);
        
        else
            temp = tempHead;

        return tempHead.getValue();
    }
    
    // Sets the reference of head to previous node object
    public Object undoMove() {
        
        if (head != null && head.getPrevious() != null)
        {
            head = head.getPrevious();        
            return head;
        }
        
        else if (head == null && temp != null)
        {
            head = temp;
            return head;
        }
        
        return null;
    }
     
    // return reference of item on top of list
    public Object peek()
    {
        if (isEmpty())
            return null;
        else
            return head.getValue();
    }
    
    // Return list of objects in list from bottom to top
    public Object[] getShapes(int total){
        Object shapes[] = new Object[total];
        ListNode tempHead = head;
        int shapesCount = 0;
        
        while (tempHead != null)
        {           
            shapesCount++;
            shapes[total - shapesCount] = tempHead.getValue();
            tempHead = tempHead.getNext();
        }
        return shapes;
    }
} // end LinkedList