//////////////// FILE HEADER (INCLUDE IN EVERY FILE) //////////////////////////
//
// Title:    Order Priority Queue Tester Method
// Course:   CS 300 Spring 2021
//
// Author:   Leah Theusch
// Email:    ltheusch@wisc.edu
// Lecturer: Mouna Kacem
//
///////////////////////// ALWAYS CREDIT OUTSIDE HELP //////////////////////////
//
// Persons:         None
// Online Sources:  None
//
///////////////////////////////////////////////////////////////////////////////


import java.lang.reflect.Array;
import java.util.NoSuchElementException;

/**
 * This class checks the correctness of the implementation of the methods defined in the class
 * OrderPriorityQueue.
 * 
 * You MAY add additional public static boolean methods to this class if you like, and any private
 * static helper methods you need.
 */
public class OrderPriorityQueueTester {
  
  /**
   * Checks the correctness of the isEmpty method of OrderPriorityQueue.
   * 
   * You should, at least:
   * (1) create a new OrderPriorityQueue and verify that it is empty
   * (2) add a new Order to the queue and verify that it is NOT empty
   * (3) remove that Order from the queue and verify that it is empty again
   * 
   * @return true if and only if ALL tests pass
   */
  public static boolean testIsEmpty() {
    Order.resetIDGenerator();

    OrderPriorityQueue queue = new OrderPriorityQueue(10); //create new queue
    //ensure new queue is empty when created
    if(!queue.isEmpty()){
      System.out.println("Error: new OrderPriorityQueue should be empty.");
      return false;
    }
    //add first order
    Order firstOrder = new Order("Apple Pie", 45);
    queue.insert(firstOrder); //insert into queue
    //ensure queue is not empty
    if(queue.isEmpty()){
      System.out.println("Error: first order added to queue; should not be empty.");
      return false;
    }
    //remove order
    queue.removeBest();
    //ensure queue is again empty
    if(!queue.isEmpty()){
      System.out.println("Error: first order removed from queue; should be empty.");
      return false;
    }
    
    return true;
  }
  
  /**
   * Checks the correctness of the insert method of OrderPriorityQueue.
   * 
   * You should, at least:
   * (1) create a new OrderPriorityQueue and add a single order with a large prepTime to it
   * (2) use the OrderPriorityQueue toString method to verify that the queue's internal structure
   *     is a valid heap
   * (3) add at least three more orders with DECREASING prepTimes to the queue and repeat step 2.
   * 
   * @return true if and only if ALL tests pass
   */
  public static boolean testInsertBasic() {
    Order.resetIDGenerator();
    

    OrderPriorityQueue queue = new OrderPriorityQueue(10); //new queue
    Order firstOrder = new Order("Fish", 60); //first order
    queue.insert(firstOrder); //add order

    //ensure order inserted correctly
    if(!queue.toString().equalsIgnoreCase("1001(60)")){
      System.out.println("Error: insert() method incorrectly adding new order.");
      return false;
    }

    //extra, decreasing orders
    Order secondOrder = new Order("Steak", 45);
    queue.insert(secondOrder);
    Order thirdOrder = new Order("Burger", 30);
    queue.insert(thirdOrder);
    Order fourthOrder = new Order("Salad", 15);
    queue.insert(fourthOrder);

    //test inserted in correct order
    if(!queue.toString().equalsIgnoreCase("1001(60), 1002(45), 1003(30), 1004(15)")){
      System.out.println("Error: insert() method failing to add orders in decreasing manner.");
      return false;
    }

    return true;
  }
  
  /**
   * Checks the correctness of the insert method of OrderPriorityQueue.
   * 
   * You should, at least:
   * (1) create an array of at least four Orders that represents a valid heap
   * (2) add a fifth order at the next available index that is NOT in a valid heap position
   * (3) pass this array to OrderPriorityQueue.percolateUp()
   * (4) verify that the resulting array is a valid heap
   * 
   * @return true if and only if ALL tests pass
   */
  public static boolean testPercolateUp() {
    Order.resetIDGenerator();

    //create new queue with orders (in max heap)
    Order[] queue = new Order[10];
    Order firstOrder = new Order("Smoothie", 61); //1001
    queue[0] = firstOrder;
    Order secondOrder = new Order("Pie", 40); //1002
    queue[1] = secondOrder;
    Order thirdOrder = new Order("Cheesecake", 20); //1003
    queue[2] = thirdOrder;
    Order fourthOrder = new Order("Cupcakes", 15); //1004
    queue[3] = fourthOrder;

    //invalid heap position - percolate to middle
    Order fifthOrder = new Order("Brownies", 45); //1005
    queue[4] = fifthOrder;

    OrderPriorityQueue.percolateUp(queue, 4);

    OrderPriorityQueue newQueue = new OrderPriorityQueue(10);
    int i = 0;
    while(queue[i] != null){
      newQueue.insert(queue[i]);
      i++;
    }

    if(!newQueue.toString().equalsIgnoreCase("1001(61), 1005(45), 1003(20), 1004(15), "
        + "1002(40)")){
      System.out.println("Error: invalid heap; element 1005 should be inserted at index 1. ");
      return false;

    }

    //percolate all the way to root test
    Order sixthOrder = new Order("Cake", 65); //1006
    newQueue.insert(sixthOrder);

    if(!newQueue.toString().equalsIgnoreCase("1006(65), 1005(45), 1001(61), 1004(15),"
        + " 1002(40), 1003(20)")){
      System.out.println("Error: invalid heap, element 1006 should be inserted at index 0 (root).");
      return false;
    }

    //equal values found
    Order seventhOrder = new Order("Fish", 61); //1007
    newQueue.insert(seventhOrder);
    if(!newQueue.toString().equalsIgnoreCase("1006(65), 1005(45), 1001(61), 1004(15), "
        + "1002(40), 1003(20), 1007(61)")){
      System.out.println("Error: invalid heap, equal values do not swap.");
      return false;
    }
    //go over capacity when inserting, creates new array double the size
    Order eighthOrder = new Order("Spaghetti", 5); //1008
    newQueue.insert(eighthOrder);
    if(!newQueue.toString().equalsIgnoreCase("1006(65), 1005(45), 1001(61), 1004(15),"
        + " 1002(40), 1003(20), 1007(61), 1008(5)")){
      System.out.println("Error: Queue supposed to transfer to new queue of double size.");
      return false;
    }

    return true;
    }
  
  /**
   * Checks the correctness of the insert method of OrderPriorityQueue.
   * 
   * You should, at least:
   * (1) create a new OrderPriorityQueue with at least 6 orders of varying prepTimes, adding them
   *     to the queue OUT of order
   * (2) use the OrderPriorityQueue toString method to verify that the queue's internal structure
   *     is a valid heap
   * 
   * @return true if and only if ALL tests pass
   */
  public static boolean testInsertAdvanced() {
    Order.resetIDGenerator();

    //new order queue
    OrderPriorityQueue queue = new OrderPriorityQueue(10);
    Order firstOrder = new Order("Quesadilla", 10); //1001
    queue.insert(firstOrder);
    Order secondOrder = new Order("Salad", 20); //1002
    queue.insert(secondOrder);
    Order thirdOrder = new Order("Hot Dog", 15); //1003
    queue.insert(thirdOrder);
    Order fourthOrder = new Order("Pizza", 30); //1004
    queue.insert(fourthOrder);
    Order fifthOrder = new Order("Burger", 16); //1005
    queue.insert(fifthOrder);
    Order sixthOrder = new Order("Pasta", 25); //1006
    queue.insert(sixthOrder);

    //check if heapify correctly
    if(!queue.toString().equalsIgnoreCase("1004(30), 1002(20), 1006(25), 1001(10),"
        + " 1005(16), 1003(15)")){
      System.out.println("Error: random queue is not arranged to max heap.");
      return false;
    }

    return true;
  }
  
  /**
   * Checks the correctness of the insert method of OrderPriorityQueue.
   * 
   * You should, at least:
   * (1) create an array of at least five Orders where the Order at index 0 is NOT in valid heap
   *     position
   * (2) pass this array to OrderPriorityQueue.percolateDown()
   * (3) verify that the resulting array is a valid heap
   * 
   * @return true if and only if ALL tests pass
   */
  public static boolean testPercolateDown() {
    Order.resetIDGenerator();

    //invalid order array with six orders
    Order[] queue = new Order[10];
    Order firstOrder = new Order("Smoothie", 10);
    queue[0] = firstOrder;
    Order secondOrder = new Order("Pie", 40);
    queue[1] = secondOrder;
    Order thirdOrder = new Order("Cheesecake", 50);
    queue[2] = thirdOrder;
    Order fourthOrder = new Order("Cupcakes", 30);
    queue[3] = fourthOrder;
    Order fifthOrder = new Order("Ice Cream", 5);
    queue[4] = fifthOrder;
    Order sixthOrder = new Order("Brownies", 20);
    queue[5] = sixthOrder;

    //call downward perculate method
    OrderPriorityQueue.percolateDown(queue, 0, 6);

    //put into OPQ to check with toString
    OrderPriorityQueue newQueue = new OrderPriorityQueue(10);
    int i = 0;
    while(queue[i] != null){
      newQueue.insert(queue[i]);
      i++;
    }

    //ensure correct order
   if(!newQueue.toString().equalsIgnoreCase("1003(50), 1002(40), 1006(20), 1004(30), "
       + "1005(5), 1001(10)")){
     System.out.println("Error: percolateDown() method failed.");
     return false;

   }
    return true;
  }
  
  /**
   * Checks the correctness of the removeBest and peekBest methods of OrderPriorityQueue.
   * 
   * You should, at least:
   * (1) create a new OrderPriorityQueue with at least 6 orders of varying prepTimes, adding them
   *     to the queue in whatever order you like
   * (2) remove all but one of the orders, verifying that each order has a SHORTER prepTime than
   *     the previously-removed order
   * (3) peek to see that the only order left is the one with the SHORTEST prepTime
   * (4) check isEmpty to verify that the queue has NOT been emptied
   * (5) remove the last order and check isEmpty to verify that the queue HAS been emptied
   * 
   * @return true if and only if ALL tests pass
   */
  public static boolean testPeekRemove() {
    Order.resetIDGenerator();
    
    // TODO implement this method, then go write the peek and dequeue methods in your
    // OrderPriorityQueue class so that they pass your tests

    //new queue for five orders
    OrderPriorityQueue queue = new OrderPriorityQueue(10);
    Order firstOrder = new Order("Burger", 35);
    queue.insert(firstOrder);
    Order secondOrder = new Order("Sandwich", 20);
    queue.insert(secondOrder);
    Order thirdOrder = new Order("Soup", 40);
    queue.insert(thirdOrder);
    Order fourthOrder = new Order("Salad", 15);
    queue.insert(fourthOrder);
    Order fifthOrder = new Order("Lasagna", 50);
    queue.insert(fifthOrder);

    //ensure peek method returns correct val
    if(!queue.peekBest().equals(fifthOrder)){
      System.out.println("Error: method should return root order in queue.");
      return false;
    }



    Order prevRemovedOne = queue.removeBest();
    Order prevRemovedTwo = queue.removeBest();

    //first two removed correctly
    if(prevRemovedOne.getPrepTime() < prevRemovedTwo.getPrepTime()){
      System.out.println("Error: first removed order smaller than second removed order.");
      return false;
    }

    //protects against peek accidentally removing while returning
    queue.peekBest();
    if( !queue.toString().equalsIgnoreCase("1001(35), 1002(20), 1004(15)")){
      System.out.println("Error: peekBest removed order instead of just returning front of line.");
      return false;
    }

    prevRemovedOne  = queue.removeBest();
    prevRemovedTwo = queue.removeBest();

    //second two removed correctly
    if(prevRemovedOne.getPrepTime() < prevRemovedTwo.getPrepTime()){
      System.out.println("Error: first removed order smaller than second removed order.");
      return false;
    }
    //ensure queue is not empty and smallest is still left
    if(!queue.peekBest().equals(fourthOrder) && !queue.isEmpty() ){
      System.out.println("Error: remove best or peek best or isEmpty problem.");
      return false;
    }

    queue.removeBest();
    if(!queue.isEmpty()){
      System.out.println("Error: all orders removed, should be empty.");
      return false;
    }
    return true;
  }
  
  /**
   * Checks the correctness of the removeBest and peekBest methods, as well as the constructor of 
   * the OrderPriorityQueue class for erroneous inputs and/or states
   * 
   * You should, at least:
   * (1) create a new OrderPriorityQueue with an invalid capacity argument, and verify that the 
   *     correct exception is thrown
   * (2) call peekBest() on an OrderPriorityQueue with an invalid state for peeking, and verify that
   *     the correct exception is thrown
   * (3) call removeBest() on an OrderPriorityQueue with an invalid state for removing, and verify
   *     that the correct exception is thrown
   * 
   * @return true if and only if ALL tests pass
   */
  public static boolean testErrors() {
    Order.resetIDGenerator();
    
    // TODO implement this method, then go modify the relevant methods in your
    // OrderPriorityQueue class so that they pass your tests

    //verify exception thrown for invalid capacity
    try {
      OrderPriorityQueue queue = new OrderPriorityQueue(-9); //neg capacity
      System.out.println("Error: should throw IllegalArgumentException for invalid (negative) " + "capacity.");
      return false;
    }catch (IllegalArgumentException e){
      System.out.println("Correct Message: " + e.getMessage());
    }catch(Exception e){
      System.out.println("Threw error, but not one expected.");
      return false;
    }
    //verify exception thrown for empty queue with peekBest()
    try{
      OrderPriorityQueue queue = new OrderPriorityQueue(1);
      queue.peekBest();
      System.out.println("Error: should throw a NoSuchElementException for calling peekBest() on "
          + "an empty queue.");
      return false;
    }catch(NoSuchElementException e){
      System.out.println("Correct Message: " + e.getMessage());
    }catch(Exception e){
      System.out.println("Threw exception, just not one that was expected,");
      return false;
    }
    //verify exception thrown for empty queue with removeBest()
    try{
      OrderPriorityQueue queue = new OrderPriorityQueue(1);
      queue.removeBest();
      System.out.println("Error: should throw NoSuchElementException for calling removeBest() on"
          + " an empty queue.");
      return false;
    }catch (NoSuchElementException e){
      System.out.println("Correct Message: " + e.getMessage());
    }catch(Exception e){
      System.out.println("Threw exception, just not one that was expected.");
      return false;
    }

    return true;
  }
  
  /**
   * Calls the test methods individually and displays their output
   * @param args
   */
  public static void main(String[] args) {
    System.out.println("isEmpty: "+testIsEmpty());
    System.out.println("insert basic: "+testInsertBasic());
    System.out.println("percolate UP: "+testPercolateUp());
    System.out.println("insert advanced: "+testInsertAdvanced());
    System.out.println("percolate DOWN: "+testPercolateDown());
    System.out.println("peek/remove valid: "+testPeekRemove());
    System.out.println("error: "+testErrors());
  }

}
