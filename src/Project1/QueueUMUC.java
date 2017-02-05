/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Project1;

import Classes.LinkedListNode; //add my interfaces
import Interfaces.IQueue;

/**
 *
 * @author Jeezy
 * @param <T>
 */
public class QueueUMUC<T> implements IQueue {
    private int n; //number of elements

    private LinkedListNode head, tail; //pointers to the head and tail of the queue
    
    public QueueUMUC() {
        n = 0;
    }

    @Override
    public boolean isFull() {
        return false; //queue is never full...
    }
    
    @Override
    public boolean isEmpty() {
        return n == 0;
    }
    
    @Override
    public void put(Object item) {
        LinkedListNode current = tail;
        tail = new LinkedListNode();
        tail.a = (T)item;

        if (n++ == 0) { //if first object
            head = tail; //put in head
        } else {
            current.next = tail; //else put in next
        }
    }

    @Override
    public T peek() {
        //if(isEmpty()) { throw new QueueException("Stack is Empty"); }
        if(isEmpty()) { return null; }
        return (T)head.a;
    }
    
    @Override
    public T get() {
        //if(isEmpty()) { throw new QueueException("Stack is Empty"); }
        if(isEmpty()) { return null; }
        
        T x = (T)head.a; //save object to return
        head = head.next; //shift pointers
        n--; //decrease number of elements
        
        return x;
    }
    
    @Override
    public void empty() {
        n = 0;
        head = null;
        tail = null;
    }
    
    @Override
    public String toString() {
        String theStackString = "";
        
        //if not empty, iterate through all elements and display their contents
        if(!isEmpty()) {
            LinkedListNode x = head;
            theStackString += x.a.toString() + "\n";
            
            while(x.hasNext()) {
                theStackString += x.next.a.toString() + "\n";
                x = x.next;
            }
        }
        
        theStackString += String.format ("\nNumber of elements: %5d", n);
        return theStackString;
    }
}
