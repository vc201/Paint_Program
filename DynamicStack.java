// DynamicStack.java

class DynamicStack {
  private LinkedList stackList = new LinkedList();
  
  // Check if stack is empty
  public boolean isEmpty() {
    return stackList.isEmpty();
  }
  
  // push an item on top of stack
  public void push(Object item) {
      stackList.addFront(item);
  }
  
  // pop an item from top of stack
  // returns null if the stack is empty
  public Object pop() {
    return stackList.removeFront();
  }
  
  // Put back an item that was popped
  public Object undoMove() {
      return stackList.undoMove();
  }
  
  // peek at top of stack
  // returns null if the stack is empty
  public Object peek() {
      return stackList.peek();
  }
  
  // Get objcets in stack
  public Object[] getShapes(int total) {
      return stackList.getShapes(total);
  }
  
  // Empty stack
  public void makeEmpty(){
      stackList.makeEmpty();
  }
}  // end class DynamicStack