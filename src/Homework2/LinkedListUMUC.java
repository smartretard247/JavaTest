/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Homework2;

import Interfaces.ILinkedList;

/**
 *
 * @author Jeezy
 * @param <T>
 */
public class LinkedListUMUC<T> implements ILinkedList {
    private int n; //number of elements
    private NodeUMUC dummy; //first node
    
    public LinkedListUMUC() {
        n = 0;
        dummy = new NodeUMUC();
        dummy.prev = dummy;
        dummy.next = dummy;
    }
    
    public int getN() { return n; }

    @Override
    public boolean isFull() {
        return false; //linkedlist is never full...
    }
    
    @Override
    public boolean isEmpty() {
        return n == 0;
    }
    
    @Override
    public Object insertHead(Object item) {
        NodeUMUC newHead = new NodeUMUC();
        NodeUMUC oldHead = (NodeUMUC)dummy.next;
        newHead.a = (T)item;
        newHead.prev = oldHead.prev;
        newHead.next = oldHead;
        newHead.next.prev = newHead;
        newHead.prev.next = newHead;
        n++;
        return newHead;
    }

    @Override
    public Object insertTail(Object item) {
        NodeUMUC newTail = new NodeUMUC();
        NodeUMUC oldTail = (NodeUMUC)dummy.prev;
        newTail.a = (T)item;
        newTail.prev = oldTail;
        newTail.next = oldTail.next;
        newTail.next.prev = newTail;
        newTail.prev.next = newTail;
        n++;
        return newTail;
    }

    @Override
    public Object removeHead() throws LinkedListException {
        if(isEmpty()) throw new LinkedListException("Linked List is Empty");
        NodeUMUC x = (NodeUMUC)dummy.next;
        x.prev.next = x.next;
        x.next.prev = x.prev;
        n--;
        return x.a;
    }

    @Override
    public Object removeTail() throws LinkedListException {
        if(isEmpty()) throw new LinkedListException("Linked List is Empty");
        NodeUMUC x = (NodeUMUC)dummy.prev;
        x.prev.next = x.next;
        x.next.prev = x.prev;
        n--;
        return x.a;
    }

    @Override
    public Object removeElementAt(int i) throws LinkedListException {
        if(isEmpty()) throw new LinkedListException("Linked List is Empty");
        NodeUMUC x = (NodeUMUC)peekElementAt(i);
        x.prev.next = x.next;
        x.next.prev = x.prev;
        n--;
        return x.a;
    }

    @Override
    public Object peekHead() throws LinkedListException {
        if(isEmpty()) throw new LinkedListException("Linked List is Empty");
        return dummy.next;
    }

    @Override
    public Object peekTail() throws LinkedListException {
        if(isEmpty()) throw new LinkedListException("Linked List is Empty");
        return dummy.prev;
    }

    @Override
    public Object peekElementAt(int i) throws LinkedListException {
        if(isEmpty()) throw new LinkedListException("Linked List is Empty");
        NodeUMUC x = null;
        if(i < n/2) {
            x = (NodeUMUC)dummy.next;
            for(int j = 0; j < i; j++) { x = (NodeUMUC)x.next; }
        } else {
            x = (NodeUMUC)dummy;
            for(int j = n; j > i; j--) { x = (NodeUMUC)x.prev; }
        }
        
        return x;
    }
    
    @Override
    public void empty() {
        n = 0;
        dummy.next = dummy;
        dummy.prev = dummy;
    }
    
    @Override
    public String toString() {
        String theStackString = "";
        
        //if not empty, iterate through all elements and display their contents
        NodeUMUC x = (NodeUMUC)dummy.next;
        for(int i = 0; i < n; i++) {
            theStackString += x.a.toString() + "\n";
            x = (NodeUMUC)x.next;
        }
        
        theStackString += String.format ("\nNumber of elements: %5d", n);
        return theStackString;
    }
}
