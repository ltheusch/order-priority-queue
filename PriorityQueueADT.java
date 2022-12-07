//////////////// FILE HEADER (INCLUDE IN EVERY FILE) //////////////////////////
//
// Title:    Priority Queue ADT
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

/**
 * The required operations for a PriorityQueue implementation.
 * 
 * @param <T> The type of item contained in the PriorityQueue; must implement the Comparable<T>
 * interface.
 */
public interface PriorityQueueADT <T extends Comparable<T>> {

  /**
   * Insert item newData after all equal or higher priority items
   * @param newData the item to be inserted
   */
  public void insert(T newData);
  
  /**
   * Remove the highest priority item from the queue and adjust the queue accordingly
   * @return the current highest priority item from the queue
   */
  public T removeBest();
  
  /** 
   * Return the highest priority item from the queue without altering the queue
   * @return the current highest priority item from the queue
   */
  public T peekBest();
  
  /**
   * Returns true if the queue contains no items, false otherwise
   * @return true if the queue contains no items, false otherwise
   */
  public boolean isEmpty();
  
}
