//////////////// FILE HEADER (INCLUDE IN EVERY FILE) //////////////////////////
//
// Title:    Order Priority Queue Method
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
import java.util.Arrays;
import java.util.NoSuchElementException;
import java.util.Queue;

/**
 * A max-heap implementation of a priority queue for Orders, where the Order with the LONGEST prep
 * time is returned first instead of the strict first-in-first-out queue as in P08.
 * <p>
 * TODO: Make sure Order implements Comparable<Order> so that this class can implement the
 * PriorityQueueADT without error!
 */
public class OrderPriorityQueue implements PriorityQueueADT<Order> {

    // Data fields; do not modify
    private Order[] queueHeap;
    private int size;

    /**
     * Constructs a PriorityQueue for Orders with the given capacity
     *
     * @param capacity the initial capacity for the queue
     * @throws IllegalArgumentException if the given capacity is 0 or negative
     */
    public OrderPriorityQueue(int capacity) {
        if (capacity < 0) {
            throw new IllegalArgumentException("Error: capacity must be greater than 0.");
        }
        // TODO initialize data fields appropriately
        queueHeap = new Order[capacity];
        size = 0;

    }

    /**
     * Inserts a new Order into the queue in the appropriate position using a heap's add logic.
     *
     * @param newOrder the Order to be added to the queue
     */
    @Override public void insert(Order newOrder) {
        // queue is empty, insert the new order at the root of the heap
        if (this.isEmpty()) {
            //insert new order at root
            queueHeap[0] = newOrder;
            size++;
        }
        // -> HINT: use Arrays.copyOf(), copying arrays is not the point of this assignment
        else {
            //queue is FULL, create a new Order array of double the current heap's size,
            // copy all elements of the current heap over and update the queueHeap reference
            if (queueHeap[queueHeap.length - 1] != null) {
                //order array twice the size and copy old contents over
                Order[] biggerQHeap = Arrays.copyOf(queueHeap, (size * 2));
                //update queueHeap reference to new array
                queueHeap = biggerQHeap;
            }
            //insert and heapify
            queueHeap[size] = newOrder;

            int current = size;

            //percolate up; swapping for longest prep times
            percolateUp(queueHeap, current);
            size++;
        }
    }

    /**
     * A utility method to percolate Order values UP through the heap; see figure 13.3.1 in zyBooks
     * for a pseudocode algorithm.
     *
     * @param heap       an array containing the Order values to be percolated into a valid heap
     * @param orderIndex the index of the Order to be percolated up
     */
    protected static void percolateUp(Order[] heap, int orderIndex) {
        while (heap[orderIndex].getPrepTime() > heap[(orderIndex - 1) / 2].getPrepTime()) {
            //swap current with parent
            Order temp = heap[(orderIndex - 1) / 2];
            heap[(orderIndex - 1) / 2] = heap[orderIndex];
            heap[orderIndex] = temp;

            //update pos of current order
            orderIndex = (orderIndex - 1) / 2;
        }
    }

    /**
     * Return the Order with the longest prep time from the queue and adjust the queue accordingly
     *
     * @return the Order with the current longest prep time from the queue
     * @throws new NoSuchElementException if the queue is empty
     */
    @Override public Order removeBest() {
        if (this.isEmpty()) {
            throw new NoSuchElementException(
                "Error: queue is empty, cannot remove any more elements");
        }
        if (size == 1) {
            Order returnVal = queueHeap[0];
            queueHeap[0] = null;
            size--;
            return returnVal;
        } else {
            //largest (root) to be returned
            Order returnVal = queueHeap[0];

            //replace with farthest right node
            queueHeap[0] = queueHeap[size - 1];
            queueHeap[size - 1] = null;
            size--; //decrease size for deleted node

            percolateDown(queueHeap, 0, size); //heapify remaining

            return returnVal; // value deleted
        }
    }

    /**
     * A utility method to percolate Order values DOWN through the heap; see figure 13.3.2 in zyBooks
     * for a pseudocode algorithm.
     *
     * @param heap       an array containing the Order values to be percolated into a valid heap
     * @param orderIndex the index of the Order to be percolated down
     * @param size       the number of initialized elements in the heap
     */
    protected static void percolateDown(Order[] heap, int orderIndex, int size) {
        //starting child index
        int childIndex = 2 * orderIndex + 1;
        Order value = heap[orderIndex];

        while(childIndex < size){
            Order maxChild = value;
            int maxIndex = -1;
            //find largest child node to swap
            for(int i = 0; i < 2 && i + childIndex < size; i ++){
                if(heap[i + childIndex].getPrepTime() > maxChild.getPrepTime()){
                    maxChild = heap[i + childIndex];
                    maxIndex = i + childIndex;
                }
            }
            //if biggest val already
            if( maxChild == value){
                return;
            }
            else{
                //swap with larger node
                Order temp = heap[maxIndex];
                heap[maxIndex] = heap[orderIndex];
                heap[orderIndex] = temp;

                //set target index for next sweep
                orderIndex = maxIndex;
                childIndex = 2 * orderIndex + 1;


            }
        }

    }

    /**
     * Return the Order with the highest prep time from the queue without altering the queue
     *
     * @return the Order with the current longest prep time from the queue
     * @throws new NoSuchElementException if the queue is empty
     */
    @Override public Order peekBest() {

        if (this.isEmpty() == true) {
            throw new NoSuchElementException("Error: queue is empty, cannot peek at order.");
        }
        //max heap so order at root has to be the largest prep
        return queueHeap[0]; // included to prevent compiler errors
    }

    /**
     * Returns true if the queue contains no Orders, false otherwise
     *
     * @return true if the queue contains no Orders, false otherwise
     */
    @Override public boolean isEmpty() {
        if (queueHeap[0] == null && size == 0) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * Returns the number of elements currently in the queue
     *
     * @return the number of elements currently in the queue
     */
    public int size() {
        return size;
    }

    /**
     * Creates a String representation of this PriorityQueue. Do not modify this implementation; this
     * is the version that will be used by all provided OrderPriorityQueue implementations that your
     * tester code will be run against.
     *
     * @return the String representation of this PriorityQueue, primarily for testing purposes
     */
    public String toString() {
        String toReturn = "";
        for (int i = 0; i < this.size; i++) {
            toReturn += queueHeap[i].getID() + "(" + queueHeap[i].getPrepTime() + ")";
            if (i < this.size - 1)
                toReturn += ", ";
        }
        return toReturn;
    }

}
