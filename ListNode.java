// ListNode.java

class ListNode {
    private Object value;
    private ListNode next;
    private ListNode previous;
     
    // Constructor
    public ListNode( Object nodeValue ) {
        this( nodeValue, null, null);
    }
 
    // Constructor
    public ListNode( Object nodeValue, ListNode nodeNext, ListNode nodePrev ) {
        value = nodeValue;
        next = nodeNext;
        previous = nodePrev;
    }
     
    // Accessor: Return the value for current node object
    public Object getValue() {
        return value;
    }
     
    // Accessor: Return reference to next node object
    public ListNode getNext() {
        return next;
    }
    
    // Accessor: Return reference to previous node object
    public ListNode getPrevious() {
        return previous;
    }
     
    // Mutator: Set new value for current node object
    public void setValue( Object newValue ) {
        value = newValue;
    }     
     
    // Mutator: Set new reference to the next node object
    public void setNext( ListNode newNext ) {
        next = newNext;
    }
    
    // Mutator: Set new reference to the previous node object
    public void setPrevious( ListNode newPrev ) {
        previous = newPrev;
    }
} // end ListNode